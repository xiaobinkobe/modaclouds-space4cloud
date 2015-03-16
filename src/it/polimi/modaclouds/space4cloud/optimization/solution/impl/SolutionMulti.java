package it.polimi.modaclouds.space4cloud.optimization.solution.impl;

import it.polimi.modaclouds.qos_models.schema.CostType;
import it.polimi.modaclouds.qos_models.schema.Costs;
import it.polimi.modaclouds.qos_models.schema.Costs.Providers;
import it.polimi.modaclouds.qos_models.schema.HourPriceType;
import it.polimi.modaclouds.qos_models.schema.MultiCloudExtension;
import it.polimi.modaclouds.qos_models.schema.MultiCloudExtensions;
import it.polimi.modaclouds.qos_models.schema.ObjectFactory;
import it.polimi.modaclouds.qos_models.schema.Provider;
import it.polimi.modaclouds.qos_models.schema.ResourceContainer;
import it.polimi.modaclouds.qos_models.schema.ResourceModelExtension;
import it.polimi.modaclouds.qos_models.schema.WorkloadPartition;
import it.polimi.modaclouds.qos_models.util.XMLHelper;
import it.polimi.modaclouds.space4cloud.contractor4cloud.Contractor;
import it.polimi.modaclouds.space4cloud.db.DataHandler;
import it.polimi.modaclouds.space4cloud.db.DataHandlerFactory;
import it.polimi.modaclouds.space4cloud.optimization.bursting.PrivateCloud;
import it.polimi.modaclouds.space4cloud.utils.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class should handle a multi-provider solution, or also, in the
 * particular case, that of a single solution.
 * 
 */
public class SolutionMulti implements Cloneable, Serializable {

	private static final long serialVersionUID = -9050926347950168327L;
	private static final Logger logger = LoggerFactory.getLogger(SolutionMulti.class);

	private int generationIteration = 0;	
	private long generationTime =0;

	public boolean usesPaaS() {
		for (Solution s : solutions.values())
			if (s.usesPaaS())
				return true;
		return false;
	}

	public static double getCost(File solution) {
		double cost = Double.MAX_VALUE;

		if (solution != null && solution.exists())
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(solution);
				doc.getDocumentElement().normalize();

				{
					Element root = (Element) doc.getElementsByTagName(
							"SolutionMultiResult").item(0);


					cost = Double.parseDouble(root
							.getAttribute("cost"));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		return cost;
	}

	public static boolean isEmpty(File solution) {
		if (solution != null && solution.exists())
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(solution);
				doc.getDocumentElement().normalize();

				{
					NodeList nl = doc.getElementsByTagName("HourAllocation");
					return (nl.getLength() == 0);
				}
			} catch (Exception e) {
				logger.error("Error in checking if the solution is empty",e);
			}
		return true;
	}

	/**
	 * if the solution has been evaluated or not.
	 */
	private boolean evaluated = false;

	/** if the solution is feasible or not. */
	private boolean feasible = false;

	/** The Cost. */
	private double cost = 0.0;

	private HashMap<String, Solution> solutions;

	public SolutionMulti() {
		this.solutions = new HashMap<String, Solution>();
	}

	public void add(Solution s) {
		String provider = s.getProvider();
		if (!provider.equals("Error")) {
			this.solutions.put(provider, s);
			updateEvaluation();
		} else
			logger.error("Error! The provider isn't defined in the solution!");
	}

	
	public void remove(Solution s) {
		String provider = s.getProvider();
		if (this.solutions.remove(provider) != null) {
			updateEvaluation();
		} else
			logger.error("Error! The provider isn't defined in the solution!");
	}
	
	public void removeUselessSolutions() {
		for (Solution s : getAll()) {
			if (!s.hasAtLeastOneReplicaInOneHour())
				remove(s);
		}
	}

	/**
	 * Change values of a certain resource far all the solutions.
	 * 
	 * @param resId
	 *            the res id
	 * @param propertyNames
	 *            the property names
	 * @param propertyValues
	 *            the property values
	 */
	public void changeValues(String resId, ArrayList<String> propertyNames,
			ArrayList<Object> propertyValues) {

		for (Solution s : getAll())
			s.changeValues(resId, propertyNames, propertyValues);

		updateEvaluation();

	}

	@Override
	public SolutionMulti clone() {
		SolutionMulti clone;
		try {
			clone = (SolutionMulti) super.clone();
		} catch (CloneNotSupportedException e) {
			clone = new SolutionMulti();
		}

		clone.solutions = new HashMap<String, Solution>();
		for (Solution s : getAll())
			clone.add(s.clone());

		return clone;
	}

	public void exportCSV(Path filePath) {
		String text = "";


		text += "total cost: " + costFormatter.format(getCost()) + "\n";

		text += "number of providers: " + solutions.size() + "\n\n";

		for (Solution s : getAll()) {
			ArrayList<Instance> hourApplication = s.getApplications();

			text += "cost: " + s.getCost() + "\n";
			text += "provider: " + s.getProvider() + "\n";

			for (Tier t : hourApplication.get(0).getTiers())
				text += t.getId() + ",";
			text += "\n";
			for (Instance i : hourApplication) {
				for (Tier t : i.getTiers())
					 text += s.getReplicas(t) + ",";
				text += "\n";
			}

			for (Tier t : hourApplication.get(0).getTiers())
				for (Component c : t.getComponents())
					text += c.getId() + ",";
			text += "\n";

			for (Instance i : hourApplication) {
				for (Tier t : i.getTiers())
					for (Component c : t.getComponents())
						text += c.getResponseTime() + ",";

				text += "\n";
			}

			text += "\n";

		}

		try {
			PrintWriter outFile = new PrintWriter(filePath.toFile());
			outFile.println(text);
			outFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportLight(Path filePath) {
		if (!isEvaluated()) {
			System.err
					.println("Trying to export a solution that has not been evaluated!");
			return;
		}

	

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("SolutionMultiResult");
			doc.appendChild(rootElement);
			
			rootElement.setAttribute("generationTime", Long.toString(getGenerationTime()));
			rootElement.setAttribute("generationIteration", Integer.toString(getGenerationIteration()));
			// set cost
			rootElement.setAttribute("cost", "" + costFormatter.format(getCost()));
			// set feasibility
			rootElement.setAttribute("feasibility", "" + isFeasible());

			for (Solution sol : getAll()) {
				Element solution = doc.createElement("Solution");
				rootElement.appendChild(solution);


				solution.setAttribute("provider", sol.getProvider());
				// set cost
				solution.setAttribute("cost", Double.toString(sol.getCost()));
				// set generation time
				solution.setAttribute("generationTime", Long.toString(sol.getGenerationTime()));
				// set generation iteration
				solution.setAttribute("generationIteration",  Integer.toString(sol.getGenerationIteration()));
				// set feasibility
				solution.setAttribute("feasibility",Boolean.toString(sol.isFeasible()));

				ArrayList<Instance> hourApplication = sol.getApplications();

		


				// create tier container element
				Element tiers = doc.createElement("Tiers");
				solution.appendChild(tiers);
				for (Tier t : hourApplication.get(0).getTiers()) {
					// create the tier
					Element tier = doc.createElement("Tier");
					tiers.appendChild(tier);

					// set id, name, provider name, service name, resource name,
					// service type
					tier.setAttribute("id", t.getId());
					tier.setAttribute("name", t.getName());

					CloudService cs = t.getCloudService();
					tier.setAttribute("providerName", cs.getProvider());
					tier.setAttribute("serviceName", cs.getServiceName());
					tier.setAttribute("resourceName", cs.getResourceName());
					tier.setAttribute("serviceType", cs.getServiceType());

					if(cs instanceof IaaS || (cs instanceof PaaS && ((PaaS)cs).areReplicasChangeable())){
						for (int i = 0; i < 24; i++) {
							// create the allocation element
							Element hourAllocation = doc.createElement("HourAllocation");
							tier.appendChild(hourAllocation);
							hourAllocation.setAttribute("hour", "" + i);
							hourAllocation.setAttribute("allocation", ""
									+ sol.getReplicas(t));
							if (sol.getProvider().indexOf(PrivateCloud.BASE_PROVIDER_NAME) > -1)
                                hourAllocation.setAttribute("hosts", "" + PrivateCloud.getInstance().getUsedHostsForTier(i, t.getId()).size());
						}
					}
				}


				// create the element with the response times
				Element functionalities = doc.createElement("functionalities");
				solution.appendChild(functionalities);


				HashMap<String, Functionality> funcList = new HashMap<>();
				for (Tier t : hourApplication.get(0).getTiers())
					for (Component c : t.getComponents())
						for (Functionality f : c.getFunctionalities())
							funcList.put(f.getId(), f);

				for (String id : funcList.keySet()) {
					// create the tier
					Element functionality = doc.createElement("Functionality");

					solution.appendChild(functionality);


					// set id, name, provider name, service name, resource name,
					// service type
					functionality.setAttribute("id", id);
					functionality.setAttribute("name", funcList.get(id)
							.getName());

					for (int i = 0; i < 24; i++) {
						// create the allocation element
						Element hourlyRT = doc.createElement("HourlyRT");
						functionality.appendChild(hourlyRT);
						hourlyRT.setAttribute("hour", "" + i);
						for (Tier t : hourApplication.get(i).getTiers())
							for (Component c : t.getComponents())
								for (Functionality fun : c.getFunctionalities())
									if (fun.getId().equals(id))
										hourlyRT.setAttribute("responseTime",
												"" + fun.getResponseTime());

					}
				}

			}

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File file = filePath.toFile();
			StreamResult result = new StreamResult(file);
			logger.info("Exported in: " + file.getAbsolutePath());

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			exportLightNew(Paths.get(filePath.getParent().toString(), "mce.xml").toString());

		} catch (ParserConfigurationException | TransformerException e) {
			logger.error("Error exporting the solution statistics",e);

		}

	}

	public void exportLightNew(String filename) {
		if (!isEvaluated()) {
			System.err
					.println("Trying to export a solution that has not been evaluated!");
			return;
		}

		MultiCloudExtensions mces = new MultiCloudExtensions();
		MultiCloudExtension mce = new MultiCloudExtension();
		mces.setMultiCloudExtensions(mce);

		mce.setId(hashCode() + "");

		for (Solution sol : getAll()) {

			Provider p = new Provider();
			mce.getProvider().add(p);

			p.setName(sol.getProvider());

			for (int i = 0; i < 24; i++) {
				WorkloadPartition wp = new WorkloadPartition();
				p.getWorkloadPartition().add(wp);

				wp.setHour(i);
				wp.setValue((int) (sol.getPercentageWorkload(i) * 100));
			}
		}

		try {
			XMLHelper.serialize(mces, MultiCloudExtensions.class,
					new FileOutputStream(new File(filename)));
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
	}

	public Solution get(int i) {
		if (i < 0 || i >= size())
			return null;

		Collection<Solution> sols = getAll();
		int k = 0;
		for (Solution s : sols) {
			if (k == i)
				return s;
			++k;
		}
		return null;
	}

	public Solution get(String provider) {
		return solutions.get(provider);
	}

	public Collection<Solution> getAll() {
		return solutions.values();
	}


	public double getCost() {
		return cost;
	}
	
	public static DecimalFormat costFormatter = null;
	static {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');
		costFormatter = new DecimalFormat("0.00", otherSymbols);
	}



	public boolean greaterThan(Solution sol) {
		String provider = sol.getProvider();
		Solution s = get(provider);
		if (s == null)
			return false;

		return s.greaterThan(sol);
	}

	public boolean greaterThan(SolutionMulti sol) {
		boolean greater = true;

		for (Solution s : getAll()) {
			Solution other = sol.get(s.getProvider());
			if (other == null)
				return false;
			greater = greater && (s.greaterThan(other));
		}

		return greater;
	}

	public boolean isEvaluated() {
		return evaluated;
	}

	public boolean isFeasible() {
		return feasible;
	}


	public boolean setFrom(File initialSolution, File initialMce) {

		
		boolean res = false;
		
		if (initialSolution != null) {
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(initialSolution);
				doc.getDocumentElement().normalize();
	
				{
					Element root = (Element) doc.getElementsByTagName(
							"SolutionMultiResult").item(0);
	
					cost = (int) Math.round(Double.parseDouble(root
							.getAttribute("cost")));
				}
	
				NodeList tiers = doc.getElementsByTagName("Tier");
	
				for (int i = 0; i < tiers.getLength(); ++i) {
					Node n = tiers.item(i);
	
					if (n.getNodeType() != Node.ELEMENT_NODE)
						continue;
	
					Element tier = (Element) n;
					String provider = tier.getAttribute("providerName");
					String tierId = tier.getAttribute("id");
					String resourceName = tier.getAttribute("resourceName");
					String serviceName = tier.getAttribute("serviceName");
	
					Solution solution = get(provider);
					if (solution == null)
						continue;
	
					DataHandler dataHandler = DataHandlerFactory.getHandler();
	
					double speed = dataHandler.getProcessingRate(provider,
							serviceName, resourceName);
					int ram = dataHandler.getAmountMemory(provider, serviceName,
							resourceName);
					int numberOfCores = dataHandler.getNumberOfReplicas(provider,
							serviceName, resourceName);
	
					// System.out.printf("DEBUG: %s, %s, %s <-> %f, %d, %d.\n",
					// provider, serviceName, resourceName, (float)speed, ram,
					// numberOfCores);
	
					NodeList hourAllocations = tier
							.getElementsByTagName("HourAllocation");
	
					for (int j = 0; j < hourAllocations.getLength(); ++j) {
						Node m = hourAllocations.item(j);
	
						if (m.getNodeType() != Node.ELEMENT_NODE)
							continue;
	
						Element hourAllocation = (Element) m;
						int hour = Integer.parseInt(hourAllocation
								.getAttribute("hour"));
						int allocation = Integer.parseInt(hourAllocation
								.getAttribute("allocation"));
	
						Instance app = solution.getApplication(hour);
	
						ArrayList<String> propertyNames = new ArrayList<String>();
						ArrayList<Object> propertyValues = new ArrayList<Object>();
	
						propertyNames.add("replicas");
						propertyValues.add(allocation);
	
						propertyNames.add("resourceName");
						propertyNames.add("speed");
						propertyNames.add("ram");
						propertyNames.add("numberOfCores");
						propertyValues.add(resourceName);
						propertyValues.add(speed);
						propertyValues.add(ram);
						propertyValues.add(numberOfCores);
	
						app.changeValues(tierId, propertyNames, propertyValues);
						
						app.setEvaluated(false);
					}
				}
				
				res = true;
				
//				for (Solution s : getAll()) {
//					System.out.println("DEBUG prima: " + s.getProvider());
//					for (int i = 0; i < 24; ++i)
//						System.out.printf("%d (%d) ", s.getApplication(i)
//								.getWorkload(), (int) (s
//								.getPercentageWorkload(i) * 100));
//					System.out.println();
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		if (initialMce != null) {
			try {
				MultiCloudExtensions mces = XMLHelper.deserialize(initialMce
						.toURI().toURL(), MultiCloudExtensions.class);

				MultiCloudExtension mce = mces.getMultiCloudExtensions();

				for (Provider p : mce.getProvider()) {
					Solution s = get(p.getName());

					if (s == null)
						continue;

					int diff = 0;

					for (WorkloadPartition wp : p.getWorkloadPartition()) {
						Instance app = s.getApplication(wp.getHour());

						ArrayList<String> propertyNames = new ArrayList<String>();
						ArrayList<Object> propertyValues = new ArrayList<Object>();

						int value = wp.getValue();
						int minimumValue = 0;
						if (value < minimumValue) {
							diff = minimumValue - value;
							value = minimumValue;
						}

						propertyNames.add("workload");
						propertyValues.add((int) Math.ceil((app
								.getWorkload() / s.getPercentageWorkload(wp
								.getHour()))
								* value / 100));

						s.setPercentageWorkload(wp.getHour(), value / 100.0);
						app.changeValues(null, propertyNames, propertyValues);

						if (diff > 0) {
							for (Provider p2 : mce.getProvider()) {
								if (!p2.getName().equals(p.getName())) {
									Solution s2 = get(p2.getName());

									WorkloadPartition wp2 = p2
											.getWorkloadPartition().get(
													wp.getHour());

									Instance app2 = s2.getApplication(wp2
											.getHour());

									int value2 = wp2.getValue();
									if (value2 < (10 + diff)) {
										continue;
									} else {
										value2 -= diff;
										diff = 0;
									}

									ArrayList<String> propertyNames2 = new ArrayList<String>();
									ArrayList<Object> propertyValues2 = new ArrayList<Object>();

									propertyNames2.add("workload");
									propertyValues2
											.add((int) Math.ceil((app2
													.getWorkload() / s2
													.getPercentageWorkload(wp2
															.getHour()))
													* value2 / 100));

									s2.setPercentageWorkload(wp2.getHour(),
											value2 / 100.0);
									app2.changeValues(null, propertyNames2,
											propertyValues2);
								}
							}
						}
					}

				}
			
				res = true;

//				for (Solution s : getAll()) {
//					System.out.println("DEBUG dopo: " + s.getProvider());
//					for (int i = 0; i < 24; ++i)
//						System.out.printf("%d (%d) ", s.getApplication(i)
//								.getWorkload(), (int) (s
//								.getPercentageWorkload(i) * 100));
//					System.out.println();
//				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		updateEvaluation();
		
		return res;
	}
	
	public boolean addFrom(File initialSolution) {

		
		boolean res = false;
		
		if (initialSolution != null) {
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(initialSolution);
				doc.getDocumentElement().normalize();
	
				{
					Element root = (Element) doc.getElementsByTagName(
							"SolutionMultiResult").item(0);
	
					cost = (int) Math.round(Double.parseDouble(root
							.getAttribute("cost")));
				}
	
				NodeList tiers = doc.getElementsByTagName("Tier");
	
				for (int i = 0; i < tiers.getLength(); ++i) {
					Node n = tiers.item(i);
	
					if (n.getNodeType() != Node.ELEMENT_NODE)
						continue;
	
					Element tier = (Element) n;
					String provider = tier.getAttribute("providerName");
					String tierId = tier.getAttribute("id");
					String resourceName = tier.getAttribute("resourceName");
					String serviceName = tier.getAttribute("serviceName");
	
					Solution solution = get(provider);
					if (solution == null)
						continue;
	
					DataHandler dataHandler = DataHandlerFactory.getHandler();
	
					double speed = dataHandler.getProcessingRate(provider,
							serviceName, resourceName);
					int ram = dataHandler.getAmountMemory(provider, serviceName,
							resourceName);
					int numberOfCores = dataHandler.getNumberOfReplicas(provider,
							serviceName, resourceName);
	
					// System.out.printf("DEBUG: %s, %s, %s <-> %f, %d, %d.\n",
					// provider, serviceName, resourceName, (float)speed, ram,
					// numberOfCores);
	
					NodeList hourAllocations = tier
							.getElementsByTagName("HourAllocation");
	
					for (int j = 0; j < hourAllocations.getLength(); ++j) {
						Node m = hourAllocations.item(j);
	
						if (m.getNodeType() != Node.ELEMENT_NODE)
							continue;
	
						Element hourAllocation = (Element) m;
						int hour = Integer.parseInt(hourAllocation
								.getAttribute("hour"));
						int allocation = Integer.parseInt(hourAllocation
								.getAttribute("allocation"));
	
						Instance app = solution.getApplication(hour);
	
						ArrayList<String> propertyNames = new ArrayList<String>();
						ArrayList<Object> propertyValues = new ArrayList<Object>();
	
						propertyNames.add("replicas");
						propertyValues.add(allocation + solution.getReplicas(app.getTierById(tierId)));
	
						propertyNames.add("resourceName");
						propertyNames.add("speed");
						propertyNames.add("ram");
						propertyNames.add("numberOfCores");
						propertyValues.add(resourceName);
						propertyValues.add(speed);
						propertyValues.add(ram);
						propertyValues.add(numberOfCores);
	
						app.changeValues(tierId, propertyNames, propertyValues);
						
						app.setEvaluated(false);
					}
				}
				
				res = true;
				
//				for (Solution s : getAll()) {
//					System.out.println("DEBUG prima: " + s.getProvider());
//					for (int i = 0; i < 24; ++i)
//						System.out.printf("%d (%d) ", s.getApplication(i)
//								.getWorkload(), (int) (s
//								.getPercentageWorkload(i) * 100));
//					System.out.println();
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		updateEvaluation();
		
		return res;
	}

	/**
	 * Show status.
	 */
	public String showStatus() {
		String result = "SolutionMulti Status\n";

		result += "Total cost: " + costFormatter.format(getCost());
		result += "\tEvaluated: " + isEvaluated();
		result += "\tFeasible: " + isFeasible();
		result += "\tProviders: " + size();

		int i = 0;
		for (Solution s : getAll()) {
			result += "\n" + ++i + ")\n";
			result += s.showStatus();
		}
		return result;
	}

	public int size() {
		return solutions.size();
	}

	@Override
	public String toString() {
		String result = "SolutionMulti@"
				+ Integer.toHexString(super.hashCode());
		result += "[Cost: " + costFormatter.format(getCost());
		result += ", Providers: " + size();
		result += ", Evaluated: " + isEvaluated();
		result += ", Feasible: " + isFeasible();
		result += "]";
		return result;
	}

	/**
	 * Updates the feasibility of the solution
	 */
	public void updateEvaluation() {
		boolean evaluated = true, feasible = true;
		// int previousCost = cost;
		cost = 0;

		for (Solution s : getAll()) {
			evaluated = evaluated && s.isEvaluated();
			feasible = feasible && s.isFeasible();
			cost += s.getCost();
		}

		this.evaluated = evaluated;
		this.feasible = feasible;

		// System.out.printf("DEBUG: Cost updated from %d to %d.\n",
		// previousCost, cost);
	}
	
	public boolean isUsingPrivateCloud() {
		for (String provider : solutions.keySet())
			if (provider.indexOf(PrivateCloud.BASE_PROVIDER_NAME) > -1)
				return true;
		return false;

	}
	
	public String showWorkloadPercentages() {
		String result = "SolutionMulti workload percentages";
		
		int size = Integer.MIN_VALUE;
		for (Solution s : getAll()) {
			if (size < s.getProvider().length())
				size = s.getProvider().length();
		}

		for (Solution s : getAll()) {
			result += "\n" + String.format("%1$"+ size + "s", s.getProvider()) + ":";
			for (int h = 0; h < 24; ++h)
				result += " " + s.getPercentageWorkload(h);
		}
		return result;
	}


	public int getGenerationIteration() {
		return generationIteration;
	}

	public void setGenerationIteration(int generationIteration) {
		this.generationIteration = generationIteration;
	}

	public long getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(long generationTime) {
		this.generationTime = generationTime;
	}

	public static List<String> getAllProviders(File solution) {
		List<String> res = new ArrayList<String>();
		
		if (solution != null && solution.exists())
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(solution);
				doc.getDocumentElement().normalize();
				
				NodeList tiers = doc.getElementsByTagName("Tier");
				
				for (int i = 0; i < tiers.getLength(); ++i) {
					Node n = tiers.item(i);
	
					if (n.getNodeType() != Node.ELEMENT_NODE)
						continue;
	
					Element tier = (Element) n;
					String provider = tier.getAttribute("providerName");
					
					boolean alreadyIn = false;
					for (int j = 0; j < res.size() && !alreadyIn; ++j) {
						if (res.get(j).equals(provider))
							alreadyIn = true;
					}
					if (!alreadyIn)
						res.add(provider);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return res;

	}
	
	public ResourceModelExtension getAsExtension() {
		//Build the objects
		ObjectFactory factory = new ObjectFactory();
		ResourceModelExtension extension = factory.createResourceModelExtension();
		List<ResourceContainer> resourceContainers = extension.getResourceContainer();
		
		for (Solution s : getAll()) {
			ResourceModelExtension ext = s.getAsExtension();
			List<ResourceContainer> rcs = ext.getResourceContainer();
			
			for (ResourceContainer rc : rcs)
				resourceContainers.add(rc);
		}
		
		return extension;
	}
	
	/**
	 * Export the solution in the format of the extension used as input for space4cloud
	 */
	public void exportAsExtension(Path fileName){
		ResourceModelExtension extension = getAsExtension();
		//serialize them		
		try {			
			XMLHelper.serialize(extension, ResourceModelExtension.class, new FileOutputStream(fileName.toFile()));
		} catch (JAXBException e) {
			logger.error("The generated solution is not valid",e);
		} catch (FileNotFoundException e) {
			logger.error("Error exporting the solution",e);
		}
		
	}
	
	public Costs getCostsAsExtension() {
		Costs costs = new Costs();
		
		costs.setSolutionID(hashCode() + "");
		
		CostType ct = new CostType();
		
		for (int h = 0; h < 24; ++h) {
			double cost = 0;
			for (Solution s : getAll())
				cost += s.getCost(h);
			
			HourPriceType hour = new HourPriceType();
			hour.setHour(h);
			hour.setCost((float)cost);
			
			ct.getHourPrice().add(hour);
		}
		
		ct.setTotalCost((float)cost);
		costs.setCost(ct);
		
		HashMap<String, Providers> providersMap = new HashMap<String, Providers>();
		
		for (Solution s : getAll()) {
			for (Tier t : s.getApplication(0).getTiers()) {
				String provider = s.getProvider();
				String serviceName = t.getCloudService().getServiceName();
				
				Providers p = providersMap.get(provider + "@" + serviceName);
				CostType ctp;
				if (p == null) {
					p = new Providers();
					p.setName(provider);
					p.setServiceName(serviceName);
					providersMap.put(provider + "@" + serviceName, p);
					
					costs.getProviders().add(p);
					
					ctp = new CostType();
					
					double totalCost = 0.0;
					
					for (int h = 0; h < 24; ++h) {
						HourPriceType hour = new HourPriceType();
						hour.setHour(h);
						double cost = t.getCost(); 
						totalCost += cost;
						hour.setCost(cost);
						ctp.getHourPrice().add(hour);
					}
					
					p.setCost(ctp);
					ctp.setTotalCost((float) totalCost);
				} else {
					ctp = p.getCost();
					
					double totalCost = ctp.getTotalCost();
					
					for (int h = 0; h < 24; ++h) {
						HourPriceType hour = new HourPriceType();
						hour.setHour(h);
						double cost = t.getCost(); 
						totalCost += cost;
						hour.setCost(cost);
						ctp.getHourPrice().add(hour);
					}
					
					ctp.setTotalCost((float) totalCost);
				}
				
				
				
				
			}
			
//			Providers p = new Providers();
//			p.setName(s.getProvider());
//			p.setServiceName(s.getApplication(0).getTiers().get(0).getCloudService().getServiceName());
//			
//			CostType ctp = new CostType();
//			
//			for (int h = 0; h < 24; ++h) {
//				HourPriceType hour = new HourPriceType();
//				hour.setHour(h);
//				hour.setCost((float)s.getCost(h));
//				
//				ctp.getHourPrice().add(hour);
//			}
//			
//			ctp.setTotalCost((float)s.getCost());
//			p.setCost(ctp);
//			
//			costs.getProviders().add(p);
//			
//			// TODO: add the contracts
		}
		
		return costs;
	}
	
	public void exportCostsAsExtension(Path fileName){
		Costs costs = getCostsAsExtension();
		//serialize them
		try {
			XMLHelper.serialize(costs, Costs.class, new FileOutputStream(fileName.toFile()));
		} catch (JAXBException e) {
			logger.error("The generated solution is not valid",e);
		} catch (FileNotFoundException e) {
			logger.error("Error exporting the solution",e);
		}
		
	}
	
	public File generateOptimizedCosts() {
		boolean doIt = false;
		for (Solution s : getAll()) {
			if (s.getProvider().equals("Amazon")) {
				doIt = true;
			}
		}
		
		if (!doIt)
			return null;
		
		logger.info("Exporting the optimized costs for Amazon...");
		
		int daysConsidered = Configuration.ROBUSTNESS_H;
		double percentageOfS = Configuration.ROBUSTNESS_Q;
		double m = 1000.0; // TODO: maybe Configuration.ROBUSTNESS_G ?
		
		String configuration = null;
		try {
			configuration = Files.createTempFile("space4cloud", ".properties").toString();
		} catch (IOException e) {
			logger.error("Error creating a new temporary file", e);
			return null;
		}
		try {
			Configuration.saveConfiguration(configuration);
		} catch (IOException e) {
			logger.error("Error exporting the configuration", e);
			return null;
		}
		
		String solution = null;
		try {
			solution = Files.createTempFile("solution", ".xml").toString();
		} catch (IOException e) {
			logger.error("Error creating a new temporary file", e);
			return null;
		}
		exportAsExtension(Paths.get(solution));
		
		Contractor.removeTempFiles = true;
		
		File f = Contractor.perform(configuration, solution, daysConsidered, percentageOfS, m);
		if (f != null && f.exists())
			logger.debug("Optimized costs: " + f.getAbsolutePath());
		
		return f;
	}

}

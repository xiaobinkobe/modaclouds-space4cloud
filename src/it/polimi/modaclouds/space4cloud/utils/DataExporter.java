package it.polimi.modaclouds.space4cloud.utils;

import it.polimi.modaclouds.qos_models.schema.CloudService;
import it.polimi.modaclouds.qos_models.schema.Replica;
import it.polimi.modaclouds.qos_models.schema.ReplicaElement;
import it.polimi.modaclouds.qos_models.schema.ResourceContainer;
import it.polimi.modaclouds.qos_models.schema.ResourceModelExtension;
import it.polimi.modaclouds.qos_models.util.XMLHelper;
import it.polimi.modaclouds.space4cloud.db.DatabaseConnector;
import it.polimi.modaclouds.space4cloud.evaluationresult.EvaluationResult;
import it.polimi.modaclouds.space4cloud.evaluationresult.EvaluationResult.Problem;
import it.polimi.modaclouds.space4cloud.evaluationresult.EvaluationResult.Result;
import it.polimi.modaclouds.space4cloud.evaluationresult.EvaluationResult.Result.WorstRealization;
import it.polimi.modaclouds.space4cloud.evaluationresult.EvaluationResult.Result.X;
import it.polimi.modaclouds.space4cloud.evaluationresult.Instance;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.SolutionMulti;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataExporter {
	
	public static final int DEFAULT_T = 24;
//	public static final int DEFAULT_C = 6;
	
	private static final Logger logger = LoggerFactory.getLogger(DataExporter.class);
	private Path sourcesBasePath;
	
	public static List<File> perform(Path sourcesBasePath) {
		DataExporter data = new DataExporter(sourcesBasePath);
		return data.exportAll();
	}
	
	public static List<File> evaluate(Path sourcesBasePath) {
		List<File> res = evaluate(perform(sourcesBasePath));
		return res;
	}
	
	public static List<File> perform(Path sourcesBasePath, int size) {
		DataExporter data = new DataExporter(sourcesBasePath);
		return data.export(size);
	}
	
	public static List<File> perform(Path sourcesBasePath, int size, int variability, int g) {
		DataExporter data = new DataExporter(sourcesBasePath);
		return data.export(size, variability, g);
	}
	
	public static List<File> evaluate(Path sourcesBasePath, int size) {
		List<File> res = evaluate(perform(sourcesBasePath, size));
		return res;
	}
	
	public static List<File> evaluate(Path sourcesBasePath, int size, int variability, int g) {
		List<File> res = evaluate(perform(sourcesBasePath, size, variability, g), variability, g);
		return res;
	}
	
	public static List<File> getAllGeneratedFiles(Path sourcesBasePath) {
		List<File> res = new ArrayList<File>();
		
		if (!sourcesBasePath.toFile().isDirectory())
			return res;
		
		for (File f : sourcesBasePath.toFile().listFiles())
			if (f.getName().indexOf(BASE_FILE_NAME) > -1)
				res.add(f);
		
		return res;
	}
	
	public DataExporter(Path sourcesBasePath) {
		this.sourcesBasePath = sourcesBasePath;
	}
	
	public List<File> exportAll() {
		List<File> res = new ArrayList<File>();
		
		for (int size = Configuration.ROBUSTNESS_PEAK_FROM; size <= Configuration.ROBUSTNESS_PEAK_TO; size += Configuration.ROBUSTNESS_STEP_SIZE)
			res.addAll(export(size));
		
		return res;
	}
	
	public List<File> export(int size) {
		return export(size, Configuration.ROBUSTNESS_VARIABILITY, Configuration.ROBUSTNESS_G);
	}
	
	public List<File> export(int size, int variability, int g) {
		if (variability <= 0)
			return new ArrayList<File>();
		
		File nominal = Paths.get(sourcesBasePath.toString(), Configuration.SOLUTION_FILE_NAME + "-" + size + Configuration.SOLUTION_FILE_EXTENSION).toFile();
		File lower = Paths.get(sourcesBasePath.toString(), Configuration.SOLUTION_FILE_NAME + "-" + size + "-" + (size / 100 * (100 - variability)) + Configuration.SOLUTION_FILE_EXTENSION).toFile();
		File upper = Paths.get(sourcesBasePath.toString(), Configuration.SOLUTION_FILE_NAME + "-" + size + "-" + (size / 100 * (100 + variability)) + Configuration.SOLUTION_FILE_EXTENSION).toFile();
			
		if (nominal.exists() && lower.exists() && upper.exists())
			return export(nominal, lower, upper, size, variability, g);
		
		return new ArrayList<File>();
	}
	
	private static int vmPeak(Integer[] replicas) {
		int vm = 0;
		for (int i : replicas) {
			if (i > vm)
				vm = i;
		}
		return vm;
	}
	private static Map<String, Integer> vmPeaks = new HashMap<String, Integer>();
	
	private List<File> export(File nominal, File lower, File upper, int nominalSize, int variability, int g) {
		List<File> res = new ArrayList<File>();
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat form = new DecimalFormat("0.#####", otherSymbols);
		
		Map<String, Integer[]> nominalReplicasMap = getReplicas(nominal);
		Map<String, Integer[]> lowerReplicasMap = getReplicas(lower);
		Map<String, Integer[]> upperReplicasMap = getReplicas(upper);
		
		ResourceEnvironmentExtensionParser resourceEnvParser = null;
	
			try {
				resourceEnvParser = new ResourceEnvironmentExtensionParser();
			} catch (ResourceEnvironmentLoadingException e) {
				logger.error("Error exporting the solution",e);
			}
	
		
		List<String> totalKeyset = new ArrayList<String>(nominalReplicasMap.keySet());
		for (String s : lowerReplicasMap.keySet())
			if (!totalKeyset.contains(s))
				totalKeyset.add(s);
		for (String s : upperReplicasMap.keySet())
			if (!totalKeyset.contains(s))
				totalKeyset.add(s);
		
		for (String key : totalKeyset) {
			Integer[] nominalReplicas = nominalReplicasMap.get(key);
			Integer[] lowerReplicas = lowerReplicasMap.get(key);
			Integer[] upperReplicas = upperReplicasMap.get(key);
			
			if (nominalReplicas == null) {
				nominalReplicas = new Integer[24];
				for (int i = 0; i < nominalReplicas.length; ++i)
					nominalReplicas[i] = 0;
			}
			if (lowerReplicas == null) {
				lowerReplicas = new Integer[24];
				for (int i = 0; i < lowerReplicas.length; ++i)
					lowerReplicas[i] = 0;
			}
			if (upperReplicas == null) {
				upperReplicas = new Integer[24];
				for (int i = 0; i < upperReplicas.length; ++i)
					upperReplicas[i] = 0;
			}
			
			try {
//				StringWriter sw = new StringWriter();
//				PrintWriter out = new PrintWriter(sw);
				
				String resource = null;
				String provider = null;
				String region = null;
				
				{
					String[] ss = key.split("@");
					resource = ss[0];
					provider = ss[1];
					region = resourceEnvParser.getRegion(provider);
				}
				
				vmPeaks.put(nominalSize + "-" + (resource.replace('.', '_')).replaceAll("_", ""), vmPeak(nominalReplicas));
				
				// For now we can only handle Amazon :(
				if (!provider.equals("Amazon"))
					continue;
				

				Path path = Paths.get(sourcesBasePath.toString(), "costs-" + nominalSize + "-" + (resource.replace('.', '_')).replaceAll("_", "") + "-" + variability + "-" + g + ".txt");
				
				PrintWriter out = new PrintWriter(new FileWriter(path.toFile()));
				
//				System.out.println("============================\n" + path.toString() + "\n============================");
				
				Map<String, Map<String, Double>> costs = getCosts(provider, resource, region);
				
				out.printf("T %d\r\n", DEFAULT_T);
				out.printf("C %d\r\n", costs.size() - 2);
				out.printf("Q %s\r\n", form.format(Configuration.ROBUSTNESS_Q));
				out.printf("G %d\r\n", g);
				
				double onDemand = costs.get("On-Demand").get("hourly"); // 0.154;
				
				out.printf("D %s\r\n", form.format(onDemand));
				out.printf("H %d\r\n", Configuration.ROBUSTNESS_H);
				
				out.println("\r\nN");
				
				for (int i = 0; i < DEFAULT_T; ++i) {
					int diffA = Math.abs(nominalReplicas[i] - lowerReplicas[i]);
					int diffB = Math.abs(nominalReplicas[i] - upperReplicas[i]);
					
					out.printf("%d %d\r\n", nominalReplicas[i], diffA > diffB ? diffA : diffB);
				}
				
				out.println("\r\nS");
				
				double spot = costs.get("Spot").get("hourly");
				
				for (int i = 0; i < DEFAULT_T; ++i)
					out.printf("%s\r\n", form.format(spot));
				
				out.println("\r\nF");
				
				for (String s : costs.keySet()) {
					if (s.indexOf("Reserved") == -1)
						continue;
					
					out.printf("%s\r\n", form.format(costs.get(s).get("initial")));
				}
				
				out.println("\r\nR");
				
				for (String s : costs.keySet()) {
					if (s.indexOf("Reserved") == -1)
						continue;
					
					out.printf("%s\r\n", form.format(costs.get(s).get("hourly")));
				}
				
				out.flush();
				out.close();
				
				res.add(path.toFile());
//				System.out.println(sw.toString());
				
			} catch (Exception e) {
				logger.error("Error while producing the file.", e);
				return null;
			}
			
		}
		
		return res;
	}
	
	private static Map<String, Map<String, Map<String, Double>>> costs = new HashMap<String, Map<String, Map<String, Double>>>();
	
	private Map<String, Map<String, Double>> getCosts(String provider, String resource, String region) {
		if (DataExporter.costs.containsKey(resource + "@" + provider)) {
			return DataExporter.costs.get(resource + "@" + provider);
		}
		
		Map<String, Map<String, Double>> costs = new LinkedHashMap<String, Map<String, Double>>();
		
		if (resource == null)
			return costs;
		
		String query =
				"SELECT 'On-Demand' as 'contract type', AVG(value) as 'cost', unit, 0 as 'initial cost'\n" +
				"FROM cost WHERE description LIKE 'On-Demand XXXXXXXXXX%' AND region = 'YYYYYYYYYY'\n" +
				"UNION\n" +
				"SELECT 'Spot', AVG(value), unit, 0\n" +
				"FROM cost WHERE description LIKE 'Spot ZZZZZZZZZZ%'\n" +
				"UNION\n" +
				"SELECT 'Reserved 1year light', AVG(value), unit,\n" +
				"(SELECT AVG(value) FROM cost WHERE description LIKE 'Reserved 1year light XXXXXXXXXX%' AND region = 'YYYYYYYYYY')\n" +
				"FROM cost WHERE description LIKE 'Reserved light XXXXXXXXXX%' AND region = 'YYYYYYYYYY' \n" +
				"AND value IN (SELECT MAX(value) FROM cost WHERE description LIKE 'Reserved light XXXXXXXXXX%' AND region = 'YYYYYYYYYY' GROUP BY description)\n" +
				"UNION\n" +
				"SELECT 'Reserved 3year light', AVG(value), unit,\n" +
				"(SELECT AVG(value) FROM cost WHERE description LIKE 'Reserved 3year light XXXXXXXXXX%' AND region = 'YYYYYYYYYY')\n" +
				"FROM cost WHERE description LIKE 'Reserved light XXXXXXXXXX%' AND region = 'YYYYYYYYYY' \n" +
				"AND value IN (SELECT MIN(value) FROM cost WHERE description LIKE 'Reserved light XXXXXXXXXX%' AND region = 'YYYYYYYYYY' GROUP BY description)\n" +
				"UNION\n" +
				"SELECT 'Reserved 1year medium', AVG(value), unit,\n" +
				"(SELECT AVG(value) FROM cost WHERE description LIKE 'Reserved 1year medium XXXXXXXXXX%' AND region = 'YYYYYYYYYY')\n" +
				"FROM cost WHERE description LIKE 'Reserved medium XXXXXXXXXX%' AND region = 'YYYYYYYYYY' \n" +
				"AND value IN (SELECT MAX(value) FROM cost WHERE description LIKE 'Reserved medium XXXXXXXXXX%' AND region = 'YYYYYYYYYY' GROUP BY description)\n" +
				"UNION\n" +
				"SELECT 'Reserved 3year medium', AVG(value), unit,\n" +
				"(SELECT AVG(value) FROM cost WHERE description LIKE 'Reserved 3year medium XXXXXXXXXX%' AND region = 'YYYYYYYYYY')\n" +
				"FROM cost WHERE description LIKE 'Reserved medium XXXXXXXXXX%' AND region = 'YYYYYYYYYY' \n" +
				"AND value IN (SELECT MIN(value) FROM cost WHERE description LIKE 'Reserved medium XXXXXXXXXX%' AND region = 'YYYYYYYYYY' GROUP BY description)\n" +
				"UNION\n" +
				"SELECT 'Reserved 1year heavy', AVG(value), unit,\n" +
				"(SELECT AVG(value) FROM cost WHERE description LIKE 'Reserved 1year heavy XXXXXXXXXX%' AND region = 'YYYYYYYYYY')\n" +
				"FROM cost WHERE description LIKE 'Reserved heavy XXXXXXXXXX%' AND region = 'YYYYYYYYYY' \n" +
				"AND value IN (SELECT MAX(value) FROM cost WHERE description LIKE 'Reserved heavy XXXXXXXXXX%' AND region = 'YYYYYYYYYY' GROUP BY description)\n" +
				"UNION\n" +
				"SELECT 'Reserved 3year heavy', AVG(value), unit,\n" +
				"(SELECT AVG(value) FROM cost WHERE description LIKE 'Reserved 3year heavy XXXXXXXXXX%' AND region = 'YYYYYYYYYY')\n" +
				"FROM cost WHERE description LIKE 'Reserved heavy XXXXXXXXXX%' AND region = 'YYYYYYYYYY' \n" +
				"AND value IN (SELECT MIN(value) FROM cost WHERE description LIKE 'Reserved heavy XXXXXXXXXX%' AND region = 'YYYYYYYYYY' GROUP BY description);";
		
		String resourceBis = "Medium";
		if (resource.indexOf("xlarge") > -1)
			resourceBis = "Extra Large";
		else if (resource.indexOf("large") > -1)
			resourceBis = "Large";
		else if (resource.indexOf("medium") > -1)
			resourceBis = "Medium";
		else if (resource.indexOf("small") > -1)
			resourceBis = "Small";
		else if (resource.indexOf("micro") > -1)
			resourceBis = "Micro";
		
		query = query.replaceAll("XXXXXXXXXX", resource).replaceAll("ZZZZZZZZZZ", resourceBis);
		
		if (region == null)
			query = query.replaceAll(" AND region = 'YYYYYYYYYY'", "");
		else
			query = query.replaceAll("YYYYYYYYYY", region);
		
		try {
			Connection db = DatabaseConnector.getConnection();
			ResultSet rs = db.createStatement().executeQuery(query);
			
			while (rs.next()) {
				String contract = rs.getString(1);
				Double cost = rs.getDouble(2);
				Double initialCost = rs.getDouble(4);
				
				Map<String, Double> costInstance = new HashMap<String, Double>();
				costInstance.put("hourly", cost);
				costInstance.put("initial", initialCost);
				
				costs.put(contract, costInstance);
			}
			
			
		} catch (Exception e) {
			logger.error("Error while getting data from the database.", e);
			costs.clear();
		}
		
		DataExporter.costs.put(resource + "@" + provider, costs);
		
		return costs;
	}
	
	private static Map<String, Integer[]> getReplicas(File solution) {
		if (SolutionMulti.isResourceModelExtension(solution))
			return getReplicasFromResourceModelExtension(solution);
		else
			return getReplicasFromFileSolution(solution);
	}
	
	@Deprecated
	private static Map<String, Integer[]> getReplicasFromFileSolution(File solution) {
		Map<String, Integer[]> replicasMap = new HashMap<String, Integer[]>();
		
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
				String resourceName = tier.getAttribute("resourceName");

				NodeList hourAllocations = tier
						.getElementsByTagName("HourAllocation");
				
				Integer replicas[] = replicasMap.get(resourceName + "@" + provider);
				if (replicas == null) {
					replicas = new Integer[24];
					for (int h = 0; h < replicas.length; ++h)
						replicas[h] = 0;
				}

				for (int j = 0; j < hourAllocations.getLength(); ++j) {
					Node m = hourAllocations.item(j);

					if (m.getNodeType() != Node.ELEMENT_NODE)
						continue;

					Element hourAllocation = (Element) m;
					int hour = Integer.parseInt(hourAllocation
							.getAttribute("hour"));
					int allocation = Integer.parseInt(hourAllocation
							.getAttribute("allocation"));
					
					replicas[hour] += allocation;
				}
				
				replicasMap.put(resourceName + "@" + provider, replicas);
			}
		} catch (Exception e) {
			logger.error("Error while reading the file solution.", e);
			replicasMap.clear();
		}
		
		return replicasMap;
	}
	
	private static Map<String, Integer[]> getReplicasFromResourceModelExtension(File solution) {
		Map<String, Integer[]> replicasMap = new HashMap<String, Integer[]>();
		
		try {
			ResourceModelExtension rme = XMLHelper.deserialize(solution
					.toURI().toURL(), ResourceModelExtension.class);

			for (ResourceContainer rc : rme.getResourceContainer()) {
				CloudService cs = rc.getCloudElement();
				
				String provider = rc.getProvider();
				String resourceName = cs.getResourceSizeID();

				Replica r = cs.getReplicas();
				if (r == null)
					continue;
				
				List<ReplicaElement> hourAllocations = r.getReplicaElement();
				
				Integer replicas[] = replicasMap.get(resourceName + "@" + provider);
				if (replicas == null) {
					replicas = new Integer[24];
					for (int h = 0; h < replicas.length; ++h)
						replicas[h] = 0;
				}

				for (ReplicaElement hourAllocation : hourAllocations) {
					int hour = hourAllocation.getHour();
					int allocation = hourAllocation.getValue();
					
					replicas[hour] += allocation;
				}
				
				replicasMap.put(resourceName + "@" + provider, replicas);
			}
		} catch (Exception e) {
			logger.error("Error while reading the file solution.", e);
			replicasMap.clear();
		}
		
		return replicasMap;
	}
	
	private static final String EVALUATE_COMMAND = "/usr/optimization/costiSaraMattia/main";
	private static final String EVALUATE_FOLDER = "/tmp/sara";
	
	public static final String BASE_FILE_NAME = "generated-evaluation-";
	
	public static List<File> evaluate(List<File> formattedSolutions) {
		return evaluate(formattedSolutions, -1, -1);
	}

	public static List<File> evaluate(List<File> formattedSolutions, int variability, int g) {
		String suffix = "";
		if (variability > -1 && g > -1)
			suffix = String.format("-%d-%d", variability, g);
		List<File> res = new ArrayList<File>();
		for (File f : formattedSolutions) {
			if (f == null || !f.exists() || f.isDirectory() || f.getName().indexOf(".txt") == -1)
				continue;
			try {
				Ssh.exec("mkdir -p " + EVALUATE_FOLDER);
				Ssh.sendFile(f.getAbsolutePath(), EVALUATE_FOLDER + "/" + f.getName());
				List<String> output = Ssh.exec(String.format("%s %s/%s", EVALUATE_COMMAND, EVALUATE_FOLDER, f.getName()));
				String newName = f.getName().replaceAll(".txt", "_sol.txt");
				String parentPath = f.getParentFile().getAbsolutePath();
				Ssh.receiveFile(parentPath + File.separator + newName, EVALUATE_FOLDER + "/" + newName);
				EvaluationResult r = getEvaluationResult(Paths.get(parentPath, newName).toFile(), output, variability, g);
				Path gen = Paths.get(parentPath, BASE_FILE_NAME + r.getProblem().getUserPeak() + "-" + r.getProblem().getMachineType() + suffix + ".xml");
				exportEvaluationResult(gen, r);
				if (gen.toFile() != null && gen.toFile().exists())
					res.add(gen.toFile());
			} catch (Exception e) {
				logger.error("Error while considering a solution file (" + f.getName() + ").", e);
			}
		}
		return res;
	}
	
	private static boolean exportEvaluationResult(Path path, EvaluationResult res) {
		try {
			XMLHelper.serialize(res, EvaluationResult.class,
					new FileOutputStream(path.toFile()));
			return true;
		} catch (FileNotFoundException | JAXBException e) {
			logger.error("Error while exporting the solution.", e);
			return false;
		}
	}
	
	private static EvaluationResult getEvaluationResult(File f, List<String> output, int variability, int g) {
		if (!f.exists() || f.isDirectory() || output.size() == 0)
			return null;
		
		String machineType;
		int userPeak;
		{
			String fileName = f.getName();
			fileName = fileName.substring("costs-".length(), fileName.indexOf("_sol.txt"));
			String[] s = fileName.split("-");
			machineType = s[1];
			userPeak = Integer.parseInt(s[0]);
		}
		
		double alpha = 0.0;
		double cost = 0.0;
		int[] x;
		int[] worstRealization;
		double bestLB = 0.0;
		double bestUB = 0.0;
		int gap = 0;
		int nodesBandB = 0;
		int time = 0;
		
		try (Scanner sc = new Scanner(f)) {
			String line = "";
			
			Map<Integer, Integer> xMap = new HashMap<Integer, Integer>();
			Map<Integer, Integer> worstRealizationMap = new HashMap<Integer, Integer>();
			
			final String strAlfa = "alfa: ";
			final String strCosto = "COSTO X: ";
			final String strX = "x[0-9]+ = .*";
			final String strN = "N[0-9]+: .*";
			
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.indexOf(strAlfa) > -1) {
					alpha = Double.parseDouble(line.substring(line.indexOf(strAlfa) + strAlfa.length()));
				} else if (line.indexOf(strCosto) > -1) {
					cost = Double.parseDouble(line.substring(line.indexOf(strCosto) + strCosto.length()));
				} else if (Pattern.matches(strX, line)) {
					int n = Integer.parseInt(line.substring(1, line.indexOf(' ')));
					double value = Double.parseDouble(line.substring(line.indexOf('=') + 2));
					xMap.put(n, (int)Math.round(value));
				} else if (Pattern.matches(strN, line)) {
					int n = Integer.parseInt(line.substring(1, line.indexOf(':')));
					int value = Integer.parseInt(line.substring(line.indexOf(':') + 2));
					worstRealizationMap.put(n, value);
				}
			}
			
			if (xMap.size() == 0 || worstRealizationMap.size() == 0)
				return null;
			
			x = new int[xMap.size()];
			worstRealization = new int[worstRealizationMap.size()];
			for (int key : xMap.keySet())
				x[key] = xMap.get(key);
			for (int key : worstRealizationMap.keySet())
				worstRealization[key] = worstRealizationMap.get(key);
		} catch (Exception e) {
			logger.error("Error while reading the solution file.", e);
			return null;
		}
		
		{
			final String strBestLB = "best LB: ";
			final String strBestUB = "best UB: ";
			final String strGap = "gap: ";
			final String strNodes = "nodi B&B :";
			final String strTime = "tempo totale: ";
			
			for (String outputLine : output) {
				for (String line : outputLine.split("\n"))
					if (line.indexOf(strBestLB) > -1) {
						bestLB = Double.parseDouble(line.substring(line.indexOf(strBestLB) + strBestLB.length()));
					} else if (line.indexOf(strBestUB) > -1) {
						bestUB = Double.parseDouble(line.substring(line.indexOf(strBestUB) + strBestUB.length()));
					} else if (line.indexOf(strGap) > -1) {
						gap = (int)Math.round(Double.parseDouble(line.substring(line.indexOf(strGap) + strGap.length(), line.indexOf('%'))));
					} else if (line.indexOf(strNodes) > -1) {
						nodesBandB = Integer.parseInt(line.substring(line.indexOf(strNodes) + strNodes.length()));
					} else if (line.indexOf(strTime) > -1) {
						time = Integer.parseInt(line.substring(line.indexOf(strTime) + strTime.length(), line.indexOf(" sec.")) );
					}
			}
		}
		
		EvaluationResult res =  new EvaluationResult();
		
		Problem p = new Problem();
		p.setMachineType(machineType);
		p.setUserPeak(userPeak);
		if (vmPeaks.containsKey(userPeak + "-" + machineType))
			p.setVmPeak(vmPeaks.get(userPeak + "-" + machineType));
		else
			p.setVmPeak(-1);
		p.setVariability(variability);
		p.setG(g);
		p.setH(Configuration.ROBUSTNESS_H);
		p.setQ(Configuration.ROBUSTNESS_Q);
		res.setProblem(p);
		
		Result r = new Result();
		r.setCost(cost);
		r.setTime(time);
		r.setAlpha(alpha);
		r.setBestLB(bestLB);
		r.setBestUB(bestUB);
		r.setGap(gap);
		r.setNodesBandB(nodesBandB);
		
		{
			X xs = new X();
			List<Instance> instances = xs.getInstance();
			for (int i = 0; i < x.length; ++i) {
				Instance instance = new Instance();
				instance.setI(i);
				instance.setValue(x[i]);
				instances.add(instance);
			}
			r.setX(xs);
		}
		
		{
			WorstRealization worstRealizationsEl = new WorstRealization();
			List<Instance> instances = worstRealizationsEl.getInstance();
			for (int i = 0; i < worstRealization.length; ++i) {
				Instance instance = new Instance();
				instance.setI(i);
				instance.setValue(worstRealization[i]);
				instances.add(instance);
			}
			r.setWorstRealization(worstRealizationsEl);
		}
		
		res.setResult(r);
		
		return res;
	}
	
	private static String robustnessTestRow(File nominalSolution, File result, String size) {
		Map<String, Integer[]> nominalReplicas = getReplicas(nominalSolution);
		int[] totNominal = new int[24];
		int[] totResult = new int[24];
		for (int i = 0; i < 24; ++i) {
			totNominal[i] = 0;
			totResult[i] = 0;
		}
		for (String key : nominalReplicas.keySet()) {
			String actualSize = key.substring(0, key.indexOf('@'));
			if (!actualSize.equals(size) && !(key.replace('.', '_')).replaceAll("_", "").equals(size))
				continue;
			Integer[] tier = nominalReplicas.get(key);
			for (int i = 0; i < 24; ++i)
				totNominal[i] += tier[i];
		}
		
		double costS4C = SolutionMulti.getCost(nominalSolution);
		int durationS4C = SolutionMulti.getDuration(nominalSolution);
		
		int variability = 0;
		int gamma = 0;
		
		double costTool = 0.0;
		int durationTool = 0;
		
		try {
			EvaluationResult eval = XMLHelper.deserialize(result
					.toURI().toURL(), EvaluationResult.class);
			
			Problem prob = eval.getProblem();
			variability = prob.getVariability();
			gamma = prob.getG();
			
			Result res = eval.getResult();
			costTool = res.getCost();
			durationTool = res.getTime();
			
			WorstRealization real = res.getWorstRealization();
			List<Instance> insts = real.getInstance();
			for (Instance i : insts) {
				totResult[i.getI()] = i.getValue();
			}
		} catch (Exception e) {
			logger.error("Error while reading the evaluation result file.", e);
			return null;
		}
		
		
		int maxDiffs = 0;
		int maxReplicas = 0;
		int count = 0;
		
		for (int i = 0; i < 24; ++i) {
			if (maxReplicas < totNominal[i])
				maxReplicas = totNominal[i];
			
			if (totNominal[i] != totResult[i]) {
				count++;
//				logger.debug("{}) {}, {}", i, totNominal[i], totResult[i]);
			} else {
				if (maxDiffs < count)
					maxDiffs = count;
				count = 0;
			}
		}
		if (maxDiffs < count)
			maxDiffs = count;
//		logger.debug("Max diffs: {}", maxDiffs);
		
		return String.format(
				"%s,%d,%d,%d,%s,%s,%d,%d,%d",
				size,
				maxReplicas,
				variability,
				gamma,
				SolutionMulti.costFormatter.format(costS4C),
				SolutionMulti.costFormatter.format(costTool),
				durationS4C,
				durationTool,
				maxDiffs
				);
	}
	
	public static final String RESULT_CSV = "robustnessTest.csv";
	
	public static File robustnessTest(File solution, List<File> generatedFiles, File append) throws Exception {
		boolean headline = false;
		
		if (append == null) {
			append = Paths.get(solution.getParent(), RESULT_CSV).toFile();
			if (!append.exists())
				headline = true;
		}
		
		try (
				FileOutputStream fout = new FileOutputStream(append, true);
				PrintWriter out = new PrintWriter(fout)) {
		
			if (headline)
				out.printf(
						"%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
						"vmType",
						"maxReplicas",
						"variability",
						"gamma",
						"costS4C",
						"costTool",
						"durationS4C",
						"durationTool",
						"maxDiffs"
						);
			
			for (File generatedFile : generatedFiles) {
				String fileName = generatedFile.getName();
				fileName = fileName.substring(BASE_FILE_NAME.length() + 1);
				
				String[] ss = fileName.split("-");
//				int peak = Integer.parseInt(ss[0]);
				String size = ss[1];
//				int variability = Integer.parseInt(ss[2]);
//				int gamma = Integer.parseInt(ss[3]);
				
				out.println(robustnessTestRow(
						solution,
						generatedFile,
						size));
			}
		}
		
		return append;
	}
	
	public static File saraMattiaTest() throws Exception {
		final String basePath = "/Users/ft/Downloads/ConstellationSara/";
		
		final String[] strings = {
				"small10:400:m1small:m1.small",
				"small50:1300:m1small:m1.small",
				"small100:3000:m1small:m1.small",
				"medium10:700:m1medium:m1.medium",
				"medium50:2500:m1medium:m1.medium",
				"medium100:5500:m1medium:m1.medium",
				"large10:1600:c3large:c3.large",
				"large50:8200:c3large:c3.large",
				"large100:19000:c3large:c3.large"
				};
		final int[] variabilities = {30, 50, 100};
		
		File f = Paths.get(Configuration.PROJECT_BASE_FOLDER, Configuration.WORKING_DIRECTORY, "saramattia.csv").toFile();
		
		try (PrintWriter out = new PrintWriter(f)) {
		
			out.printf(
					"%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
					"vmType",
					"maxReplicas",
					"variability",
					"gamma",
					"costS4C",
					"costTool",
					"durationS4C",
					"durationTool",
					"maxDiffs"
					);
			
			for (String s : strings) {
				String[] names = s.split(":");
				for (int variability : variabilities)
					for (int gamma = 1; gamma <= 24; ++gamma)
						out.println(robustnessTestRow(
								new File(basePath + names[0] + "/results/solution-" + names[1] + ".xml"),
								new File(basePath + names[0] + "/results/generated-evaluation-" + names[1] + "-" + names[2] + "-" + variability + "-" + gamma + ".xml"),
								names[3]));
			}
		}
		
		return f;
	}
	
	public static void main(String[] args) {
		try {
			Configuration.loadConfiguration("/Users/ft/Development/workspace-s4c-runtime/Constellation/batch.prop-small10-30-24.properties");
//			List<File> res = evaluate(Paths.get("/Users/ft/Development/workspace-s4c-runtime/Constellation/space4cloud/results/"), 400, 100, 20);
			
			saraMattiaTest();
				
			
//			Configuration.loadConfiguration("/Users/ft/Desktop/tmp/Sara Mattia/aaa/conf.properties");
//		
//			List<File> files = new ArrayList<File>();
//			files.add(new File("/Users/ft/Desktop/tmp/Sara Mattia/aaa/costs-1900-m2xlarge.txt"));
//			List<File> res = evaluate(files);
//			for (File f : res)
//				System.out.println("> " + f.getAbsolutePath());
//			
//			res = getAllGeneratedFiles(Paths.get("/Users/ft/Desktop/tmp/Sara Mattia/aaa"));
//			for (File f : res)
//				System.out.println("> " + f.getAbsolutePath());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
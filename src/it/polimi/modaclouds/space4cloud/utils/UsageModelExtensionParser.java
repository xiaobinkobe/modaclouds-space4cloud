package it.polimi.modaclouds.space4cloud.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UsageModelExtensionParser {

	private static final int HOURS = 24;
	protected Map<String, Integer[]> populations = new HashMap<String, Integer[]>();
	protected Map<String, Double[]> thinkTimes = new HashMap<String, Double[]>();
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private File extension;
	private static final Logger logger = LoggerFactory.getLogger(UsageModelExtensionParser.class);

	public UsageModelExtensionParser(File extensionFile)
			throws ParserConfigurationException, SAXException, IOException,
			JAXBException {
		this(extensionFile, true);
	}

	public UsageModelExtensionParser(File extensionFile, boolean parse)
			throws ParserConfigurationException, SAXException, IOException,
			JAXBException {
		this.extension = extensionFile;
		if (parse)
			parse();
	}

	protected File getExtension() {
		return extension;
	}

	public Map<String, Integer[]> getPopulations() {
		return populations;
	}

	public Map<String, Double[]> getThinkTimes() {
		return thinkTimes;
	}

	private void parse() throws ParserConfigurationException, SAXException,
			IOException, JAXBException {

		dbFactory = DocumentBuilderFactory.newInstance();
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(extension);
		doc.getDocumentElement().normalize();

		NodeList usageModelExtList = doc
				.getElementsByTagName("usageModelExtension");
		// Parse usage model extension
		for (int i = 0; i < usageModelExtList.getLength(); i++) {
			// retreive the usage scenario id
			Node usageModelExt = usageModelExtList.item(i);
			String usageScenarioId = usageModelExt.getAttributes()
					.getNamedItem("scenarioId").getNodeValue();
			Element usageModelExtElement = (Element) usageModelExt;
			// if the workload is closed get hour, population and think time
			if (usageModelExtElement.getElementsByTagName("closedWorkload")
					.getLength() == 1) {
				// get all the workload elements inside the closed workload
				NodeList workloadElementList = ((Element) usageModelExtElement
						.getElementsByTagName("closedWorkload").item(0))
						.getElementsByTagName("workloadElement");
				Double[] time = new Double[HOURS];
				Integer[] pop = new Integer[HOURS];
				for (int j = 0; j < workloadElementList.getLength(); j++) {
					Node n = workloadElementList.item(j);
					int hour = Integer.parseInt(n.getAttributes()
							.getNamedItem("hour").getNodeValue()); // - 1;
					time[hour] = Double.parseDouble(n.getAttributes()
							.getNamedItem("thinkTime").getNodeValue());
					pop[hour] = Integer.parseInt(n.getAttributes()
							.getNamedItem("population").getNodeValue());
				}

				thinkTimes.put(usageScenarioId, time);
				populations.put(usageScenarioId, pop);
			} else if (usageModelExtElement
					.getElementsByTagName("openWorkload").getLength() == 1) {
				// get the hour and the population
				NodeList workloadElementList = ((Element) usageModelExtElement
						.getElementsByTagName("openWorkload").item(0))
						.getElementsByTagName("workloadElement");
				Integer[] pop = new Integer[HOURS];
				for (int j = 0; j < workloadElementList.getLength(); j++) {
					Node n = workloadElementList.item(j);
					int hour = Integer.parseInt(n.getAttributes()
							.getNamedItem("hour").getNodeValue());
					pop[hour] = Integer.parseInt(n.getAttributes()
							.getNamedItem("population").getNodeValue());
				}

				populations.put(usageScenarioId, pop);
			} else {
				logger.error("The Usage model extension should specify exactly one open or closed worklaod element");
				return;
			}
		}

	}
}

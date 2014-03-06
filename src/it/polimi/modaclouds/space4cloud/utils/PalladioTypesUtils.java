/*
 * 
 */
package it.polimi.modaclouds.space4cloud.utils;

import it.polimi.modaclouds.space4cloud.types.ProcessingResourceT;
import it.polimi.modaclouds.space4cloud.types.SchedulingT;

import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * Provides utility methods to retrieve the type and the scheduling of Palladio
 * Processing Resources.
 * 
 * @author Davide Franceschelli
 * @see ProcessingResourceT
 * @see SchedulingT
 */
public class PalladioTypesUtils {

	/**
	 * Returns the String representing the Palladio Processing Resource Type by
	 * analyzing the href within the Processing Resource Specification.
	 * 
	 * @param href
	 *            is the String representing the hyper reference to the Palladio
	 *            Processing Resource Type.
	 * @return the Processing Resource Type in the String format.
	 * @see ProcessingResourceT
	 */
	public static String getProcessingResourceTypeStringByHref(String href) {
		if (href.equals(ProcessingResourceT.CPU.getPathmap()))
			return "CPU";
		else if (href.equals(ProcessingResourceT.HDD.getPathmap()))
			return "HDD";
		else if (href.equals(ProcessingResourceT.DELAY.getPathmap()))
			return "DELAY";
		else
			return "ND";
	}

	/**
	 * Returns the Processing Resource Type by analyzing the href attribute.
	 * 
	 * @param href
	 *            is the String representing the hyper reference to the Palladio
	 *            Processing Resource Type.
	 * @return a ProcessingResourceT object.
	 * @see ProcessingResourceT
	 */
	public static ProcessingResourceT getProcessingResourceTypeByHref(
			String href) {
		String s = getProcessingResourceTypeStringByHref(href);
		return getProcessingResourceTypeByName(s);
	}

	/**
	 * Returns the String representing the Palladio Processing Resource
	 * Scheduling Type by analyzing the href within the Processing Resource
	 * Specification.
	 * 
	 * @param href
	 *            is the String representing the hyper reference to the Palladio
	 *            Processing Resource Scheduling Type.
	 * @return the Processing Resource Scheduling Type in the String format.
	 * @see SchedulingT
	 */
	public static String getSchedulingTypeStringByHref(String href) {
		if (href.equals(SchedulingT.PS.getPathmap()))
			return "Processor Sharing";
		else if (href.equals(SchedulingT.FCFS.getPathmap()))
			return "First-Come-First-Served";
		else if (href.equals(SchedulingT.DELAY.getPathmap()))
			return "Delay";
		else
			return "ND";
	}

	/**
	 * Returns the Processing Resource Scheduling Type by analyzing the href
	 * attribute.
	 * 
	 * @param href
	 *            is the String representing the hyper reference to the Palladio
	 *            Processing Resource Scheduling Type.
	 * @return a SchedulingT object.
	 * @see SchedulingT
	 */
	public static SchedulingT getSchedulingTypeByHref(String href) {
		String s = getSchedulingTypeStringByHref(href);
		return getSchedulingTypeByName(s);
	}

	/**
	 * Returns the String representing the Palladio Processing Resource Type by
	 * analyzing the Element representing the Processing Resource.
	 * 
	 * @param e
	 *            is the Element representing the Processing Resource.
	 * @return the Processing Resource Type in the String format.
	 * @see ProcessingResourceT
	 */
	public static String getProcessingResourceStringTypeByElement(Element e) {
		return getProcessingResourceTypeStringByHref(((Element) e
				.getElementsByTagName(
						"activeResourceType_ActiveResourceSpecification").item(
						0)).getAttribute("href"));
	}

	/**
	 * Returns the Processing Resource Type by analyzing the Element
	 * representing the Processing Resource.
	 * 
	 * @param e
	 *            is the Element representing the Processing Resource.
	 * @return a ProcessingResourceT object.
	 * @see ProcessingResourceT
	 */
	public static ProcessingResourceT getProcessingResourceTypeByElement(
			Element e) {
		String s = getProcessingResourceStringTypeByElement(e);
		return getProcessingResourceTypeByName(s);
	}

	/**
	 * Returns the String representing the Palladio Processing Resource
	 * Scheduling Type by analyzing the Element representing the Processing
	 * Resource.
	 * 
	 * @param e
	 *            is the Element representing the Processing Resource.
	 * @return the Processing Resource Scheduling Type in the String format.
	 * @see ProcessingResourceT
	 */
	public static String getSchedulingTypeStringByElement(Element e) {
		return getSchedulingTypeStringByHref(((Element) e.getElementsByTagName(
				"schedulingPolicy").item(0)).getAttribute("href"));
	}

	/**
	 * Returns the Processing Resource Scheduling Type by analyzing the Element
	 * representing the Processing Resource.
	 * 
	 * @param e
	 *            is the Element representing the Processing Resource.
	 * @return a SchedulingT object.
	 * @see SchedulingT
	 */
	public static SchedulingT getSchedulingTypeByElement(Element e) {
		String s = getSchedulingTypeStringByElement(e);
		return getSchedulingTypeByName(s);
	}

	/**
	 * Returns the Processing Resource Type starting from its String
	 * representation.
	 * 
	 * @param name
	 *            is the String representing the Processing Resource Type.
	 * @return the corresponding ProcessingResourceT object
	 * @see ProcessingResourceT
	 */
	public static ProcessingResourceT getProcessingResourceTypeByName(
			String name) {
		if (name.equals(ProcessingResourceT.CPU.getName()))
			return ProcessingResourceT.CPU;
		if (name.equals(ProcessingResourceT.HDD.getName()))
			return ProcessingResourceT.HDD;
		if (name.equals(ProcessingResourceT.DELAY.getName()))
			return ProcessingResourceT.DELAY;
		return ProcessingResourceT.ND;
	}

	/**
	 * Returns the Processing Resource Scheduling Type starting from its String
	 * representation.
	 * 
	 * @param name
	 *            is the String representing the Processing Resource Scheduling
	 *            Type.
	 * @return the corresponding SchedulingT object
	 * @see SchedulingT
	 */
	public static SchedulingT getSchedulingTypeByName(String name) {
		if (name.equals(SchedulingT.PS.getName()))
			return SchedulingT.PS;
		else if (name.equals(SchedulingT.FCFS.getName()))
			return SchedulingT.FCFS;
		else if (name.equals(SchedulingT.DELAY.getName()))
			return SchedulingT.DELAY;
		else
			return SchedulingT.ND;
	}
}

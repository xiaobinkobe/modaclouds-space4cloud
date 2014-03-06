/*
 * 
 */
package it.polimi.modaclouds.space4cloud.types.palladio;

import it.polimi.modaclouds.space4cloud.utils.DOM;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class AllocationSpecification.
 */
public class AllocationSpecification {

	/** The allocation specification element. */
	private Element allocationSpecificationElement;
	
	/** The hour. */
	private int hour = 0;
	
	/** The size. */
	private int size = 1;
	
	/** The doc. */
	private Document doc;
	
	/** The Constant MIN_SIZE. */
	private final static int MIN_SIZE = 0;

	/**
	 * Instantiates a new allocation specification.
	 *
	 * @param e the e
	 */
	public AllocationSpecification(Element e) {
		doc = DOM.getDocument();
		Element x = (Element) doc.importNode(e, true);
		doc.appendChild(x);
		initialize(x);
	}

	/**
	 * Instantiates a new allocation specification.
	 *
	 * @param hour the hour
	 * @param size the size
	 */
	public AllocationSpecification(int hour, int size) {
		doc = DOM.getDocument();
		Element x = doc.createElement("Allocation_Specification");
		if (!isValidHour(hour))
			hour = 0;
		if (!isValidSize(size))
			size = 1;
		x.setAttribute("hour", "" + hour);
		x.setAttribute("size", "" + size);
		doc.appendChild(x);
		initialize(x);
	}

	/**
	 * Initialize.
	 *
	 * @param x the x
	 */
	private void initialize(Element x) {
		allocationSpecificationElement = x;
		try {
			hour = Integer.parseInt(x.getAttribute("hour"));
			size = Integer.parseInt(x.getAttribute("size"));
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Gets the allocation specification element.
	 *
	 * @return the allocation specification element
	 */
	public Element getAllocationSpecificationElement() {
		return allocationSpecificationElement;
	}

	/**
	 * Sets the allocation specification element.
	 *
	 * @param allocationSpecificationElement the new allocation specification element
	 */
	public void setAllocationSpecificationElement(
			Element allocationSpecificationElement) {
		initialize((Element) doc.importNode(allocationSpecificationElement,
				true));
	}

	/**
	 * Gets the hour.
	 *
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Sets the hour.
	 *
	 * @param hour the new hour
	 */
	public void setHour(int hour) {
		if (isValidHour(hour)) {
			this.hour = hour;
			allocationSpecificationElement.setAttribute("hour", "" + hour);
		}
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(int size) {
		if (isValidSize(size)) {
			this.size = size;
			allocationSpecificationElement.setAttribute("size", "" + size);
		}
	}

	/**
	 * Checks if is valid hour.
	 *
	 * @param hour the hour
	 * @return true, if is valid hour
	 */
	private boolean isValidHour(int hour) {
		return hour >= 0 && hour <= 23;
	}

	/**
	 * Checks if is valid size.
	 *
	 * @param size the size
	 * @return true, if is valid size
	 */
	private boolean isValidSize(int size) {
		return size >= MIN_SIZE;
	}
}

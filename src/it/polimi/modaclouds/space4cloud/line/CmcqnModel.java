//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.09 at 08:30:05 AM CEST 
//


package it.polimi.modaclouds.space4cloud.line;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.CopyStrategy;
import org.jvnet.jaxb2_commons.lang.CopyTo;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBMergeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.MergeFrom;
import org.jvnet.jaxb2_commons.lang.MergeStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.modaclouds.eu/xsd/2013/6/lineResult}processor" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.modaclouds.eu/xsd/2013/6/lineResult}workload" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.modaclouds.eu/xsd/2013/6/lineResult}SEFF" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "processor",
    "workload",
    "seff"
})
@XmlRootElement(name = "cmcqn-model")
public class CmcqnModel
    implements Cloneable, CopyTo, Equals, HashCode, MergeFrom, ToString
{

    @XmlElement(required = true)
    protected List<Processor> processor;
    @XmlElement(required = true)
    protected List<Workload> workload;
    @XmlElement(name = "SEFF", required = true)
    protected List<SEFF> seff;
    @XmlAttribute(name = "name")
    @XmlSchemaType(name = "anySimpleType")
    protected String name;

    /**
     * Gets the value of the processor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Processor }
     * 
     * 
     */
    public List<Processor> getProcessor() {
        if (processor == null) {
            processor = new ArrayList<Processor>();
        }
        return this.processor;
    }

    /**
     * Gets the value of the workload property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workload property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkload().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Workload }
     * 
     * 
     */
    public List<Workload> getWorkload() {
        if (workload == null) {
            workload = new ArrayList<Workload>();
        }
        return this.workload;
    }

    /**
     * Gets the value of the seff property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the seff property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSEFF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SEFF }
     * 
     * 
     */
    public List<SEFF> getSEFF() {
        if (seff == null) {
            seff = new ArrayList<SEFF>();
        }
        return this.seff;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            List<Processor> theProcessor;
            theProcessor = (((this.processor!= null)&&(!this.processor.isEmpty()))?this.getProcessor():null);
            strategy.appendField(locator, this, "processor", buffer, theProcessor);
        }
        {
            List<Workload> theWorkload;
            theWorkload = (((this.workload!= null)&&(!this.workload.isEmpty()))?this.getWorkload():null);
            strategy.appendField(locator, this, "workload", buffer, theWorkload);
        }
        {
            List<SEFF> theSEFF;
            theSEFF = (((this.seff!= null)&&(!this.seff.isEmpty()))?this.getSEFF():null);
            strategy.appendField(locator, this, "seff", buffer, theSEFF);
        }
        {
            String theName;
            theName = this.getName();
            strategy.appendField(locator, this, "name", buffer, theName);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof CmcqnModel)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final CmcqnModel that = ((CmcqnModel) object);
        {
            List<Processor> lhsProcessor;
            lhsProcessor = (((this.processor!= null)&&(!this.processor.isEmpty()))?this.getProcessor():null);
            List<Processor> rhsProcessor;
            rhsProcessor = (((that.processor!= null)&&(!that.processor.isEmpty()))?that.getProcessor():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "processor", lhsProcessor), LocatorUtils.property(thatLocator, "processor", rhsProcessor), lhsProcessor, rhsProcessor)) {
                return false;
            }
        }
        {
            List<Workload> lhsWorkload;
            lhsWorkload = (((this.workload!= null)&&(!this.workload.isEmpty()))?this.getWorkload():null);
            List<Workload> rhsWorkload;
            rhsWorkload = (((that.workload!= null)&&(!that.workload.isEmpty()))?that.getWorkload():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "workload", lhsWorkload), LocatorUtils.property(thatLocator, "workload", rhsWorkload), lhsWorkload, rhsWorkload)) {
                return false;
            }
        }
        {
            List<SEFF> lhsSEFF;
            lhsSEFF = (((this.seff!= null)&&(!this.seff.isEmpty()))?this.getSEFF():null);
            List<SEFF> rhsSEFF;
            rhsSEFF = (((that.seff!= null)&&(!that.seff.isEmpty()))?that.getSEFF():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "seff", lhsSEFF), LocatorUtils.property(thatLocator, "seff", rhsSEFF), lhsSEFF, rhsSEFF)) {
                return false;
            }
        }
        {
            String lhsName;
            lhsName = this.getName();
            String rhsName;
            rhsName = that.getName();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "name", lhsName), LocatorUtils.property(thatLocator, "name", rhsName), lhsName, rhsName)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            List<Processor> theProcessor;
            theProcessor = (((this.processor!= null)&&(!this.processor.isEmpty()))?this.getProcessor():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "processor", theProcessor), currentHashCode, theProcessor);
        }
        {
            List<Workload> theWorkload;
            theWorkload = (((this.workload!= null)&&(!this.workload.isEmpty()))?this.getWorkload():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "workload", theWorkload), currentHashCode, theWorkload);
        }
        {
            List<SEFF> theSEFF;
            theSEFF = (((this.seff!= null)&&(!this.seff.isEmpty()))?this.getSEFF():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "seff", theSEFF), currentHashCode, theSEFF);
        }
        {
            String theName;
            theName = this.getName();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "name", theName), currentHashCode, theName);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public Object clone() {
        return copyTo(createNewInstance());
    }

    public Object copyTo(Object target) {
        final CopyStrategy strategy = JAXBCopyStrategy.INSTANCE;
        return copyTo(null, target, strategy);
    }

    public Object copyTo(ObjectLocator locator, Object target, CopyStrategy strategy) {
        final Object draftCopy = ((target == null)?createNewInstance():target);
        if (draftCopy instanceof CmcqnModel) {
            final CmcqnModel copy = ((CmcqnModel) draftCopy);
            if ((this.processor!= null)&&(!this.processor.isEmpty())) {
                List<Processor> sourceProcessor;
                sourceProcessor = (((this.processor!= null)&&(!this.processor.isEmpty()))?this.getProcessor():null);
                @SuppressWarnings("unchecked")
                List<Processor> copyProcessor = ((List<Processor> ) strategy.copy(LocatorUtils.property(locator, "processor", sourceProcessor), sourceProcessor));
                copy.processor = null;
                if (copyProcessor!= null) {
                    List<Processor> uniqueProcessorl = copy.getProcessor();
                    uniqueProcessorl.addAll(copyProcessor);
                }
            } else {
                copy.processor = null;
            }
            if ((this.workload!= null)&&(!this.workload.isEmpty())) {
                List<Workload> sourceWorkload;
                sourceWorkload = (((this.workload!= null)&&(!this.workload.isEmpty()))?this.getWorkload():null);
                @SuppressWarnings("unchecked")
                List<Workload> copyWorkload = ((List<Workload> ) strategy.copy(LocatorUtils.property(locator, "workload", sourceWorkload), sourceWorkload));
                copy.workload = null;
                if (copyWorkload!= null) {
                    List<Workload> uniqueWorkloadl = copy.getWorkload();
                    uniqueWorkloadl.addAll(copyWorkload);
                }
            } else {
                copy.workload = null;
            }
            if ((this.seff!= null)&&(!this.seff.isEmpty())) {
                List<SEFF> sourceSEFF;
                sourceSEFF = (((this.seff!= null)&&(!this.seff.isEmpty()))?this.getSEFF():null);
                @SuppressWarnings("unchecked")
                List<SEFF> copySEFF = ((List<SEFF> ) strategy.copy(LocatorUtils.property(locator, "seff", sourceSEFF), sourceSEFF));
                copy.seff = null;
                if (copySEFF!= null) {
                    List<SEFF> uniqueSEFFl = copy.getSEFF();
                    uniqueSEFFl.addAll(copySEFF);
                }
            } else {
                copy.seff = null;
            }
            if (this.name!= null) {
                String sourceName;
                sourceName = this.getName();
                String copyName = ((String) strategy.copy(LocatorUtils.property(locator, "name", sourceName), sourceName));
                copy.setName(copyName);
            } else {
                copy.name = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new CmcqnModel();
    }

    public void mergeFrom(Object left, Object right) {
        final MergeStrategy strategy = JAXBMergeStrategy.INSTANCE;
        mergeFrom(null, null, left, right, strategy);
    }

    public void mergeFrom(ObjectLocator leftLocator, ObjectLocator rightLocator, Object left, Object right, MergeStrategy strategy) {
        if (right instanceof CmcqnModel) {
            final CmcqnModel target = this;
            final CmcqnModel leftObject = ((CmcqnModel) left);
            final CmcqnModel rightObject = ((CmcqnModel) right);
            {
                List<Processor> lhsProcessor;
                lhsProcessor = (((leftObject.processor!= null)&&(!leftObject.processor.isEmpty()))?leftObject.getProcessor():null);
                List<Processor> rhsProcessor;
                rhsProcessor = (((rightObject.processor!= null)&&(!rightObject.processor.isEmpty()))?rightObject.getProcessor():null);
                List<Processor> mergedProcessor = ((List<Processor> ) strategy.merge(LocatorUtils.property(leftLocator, "processor", lhsProcessor), LocatorUtils.property(rightLocator, "processor", rhsProcessor), lhsProcessor, rhsProcessor));
                target.processor = null;
                if (mergedProcessor!= null) {
                    List<Processor> uniqueProcessorl = target.getProcessor();
                    uniqueProcessorl.addAll(mergedProcessor);
                }
            }
            {
                List<Workload> lhsWorkload;
                lhsWorkload = (((leftObject.workload!= null)&&(!leftObject.workload.isEmpty()))?leftObject.getWorkload():null);
                List<Workload> rhsWorkload;
                rhsWorkload = (((rightObject.workload!= null)&&(!rightObject.workload.isEmpty()))?rightObject.getWorkload():null);
                List<Workload> mergedWorkload = ((List<Workload> ) strategy.merge(LocatorUtils.property(leftLocator, "workload", lhsWorkload), LocatorUtils.property(rightLocator, "workload", rhsWorkload), lhsWorkload, rhsWorkload));
                target.workload = null;
                if (mergedWorkload!= null) {
                    List<Workload> uniqueWorkloadl = target.getWorkload();
                    uniqueWorkloadl.addAll(mergedWorkload);
                }
            }
            {
                List<SEFF> lhsSEFF;
                lhsSEFF = (((leftObject.seff!= null)&&(!leftObject.seff.isEmpty()))?leftObject.getSEFF():null);
                List<SEFF> rhsSEFF;
                rhsSEFF = (((rightObject.seff!= null)&&(!rightObject.seff.isEmpty()))?rightObject.getSEFF():null);
                List<SEFF> mergedSEFF = ((List<SEFF> ) strategy.merge(LocatorUtils.property(leftLocator, "seff", lhsSEFF), LocatorUtils.property(rightLocator, "seff", rhsSEFF), lhsSEFF, rhsSEFF));
                target.seff = null;
                if (mergedSEFF!= null) {
                    List<SEFF> uniqueSEFFl = target.getSEFF();
                    uniqueSEFFl.addAll(mergedSEFF);
                }
            }
            {
                String lhsName;
                lhsName = leftObject.getName();
                String rhsName;
                rhsName = rightObject.getName();
                String mergedName = ((String) strategy.merge(LocatorUtils.property(leftLocator, "name", lhsName), LocatorUtils.property(rightLocator, "name", rhsName), lhsName, rhsName));
                target.setName(mergedName);
            }
        }
    }

}
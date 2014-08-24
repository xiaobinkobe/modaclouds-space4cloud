//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.24 at 04:26:11 PM CEST 
//


package it.polimi.modaclouds.space4cloud.line;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://www.modaclouds.eu/xsd/2013/6/lineResult}responseTimeDistribution" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="responseTime" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "responseTimeDistribution"
})
@XmlRootElement(name = "SEFF")
public class SEFF
    implements Cloneable, CopyTo, Equals, HashCode, MergeFrom, ToString
{

    protected ResponseTimeDistribution responseTimeDistribution;
    @XmlAttribute(name = "name")
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute(name = "responseTime")
    protected Double responseTime;

    /**
     * Gets the value of the responseTimeDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseTimeDistribution }
     *     
     */
    public ResponseTimeDistribution getResponseTimeDistribution() {
        return responseTimeDistribution;
    }

    /**
     * Sets the value of the responseTimeDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseTimeDistribution }
     *     
     */
    public void setResponseTimeDistribution(ResponseTimeDistribution value) {
        this.responseTimeDistribution = value;
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

    /**
     * Gets the value of the responseTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the value of the responseTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setResponseTime(Double value) {
        this.responseTime = value;
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
            ResponseTimeDistribution theResponseTimeDistribution;
            theResponseTimeDistribution = this.getResponseTimeDistribution();
            strategy.appendField(locator, this, "responseTimeDistribution", buffer, theResponseTimeDistribution);
        }
        {
            String theName;
            theName = this.getName();
            strategy.appendField(locator, this, "name", buffer, theName);
        }
        {
            Double theResponseTime;
            theResponseTime = this.getResponseTime();
            strategy.appendField(locator, this, "responseTime", buffer, theResponseTime);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof SEFF)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final SEFF that = ((SEFF) object);
        {
            ResponseTimeDistribution lhsResponseTimeDistribution;
            lhsResponseTimeDistribution = this.getResponseTimeDistribution();
            ResponseTimeDistribution rhsResponseTimeDistribution;
            rhsResponseTimeDistribution = that.getResponseTimeDistribution();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "responseTimeDistribution", lhsResponseTimeDistribution), LocatorUtils.property(thatLocator, "responseTimeDistribution", rhsResponseTimeDistribution), lhsResponseTimeDistribution, rhsResponseTimeDistribution)) {
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
        {
            Double lhsResponseTime;
            lhsResponseTime = this.getResponseTime();
            Double rhsResponseTime;
            rhsResponseTime = that.getResponseTime();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "responseTime", lhsResponseTime), LocatorUtils.property(thatLocator, "responseTime", rhsResponseTime), lhsResponseTime, rhsResponseTime)) {
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
            ResponseTimeDistribution theResponseTimeDistribution;
            theResponseTimeDistribution = this.getResponseTimeDistribution();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "responseTimeDistribution", theResponseTimeDistribution), currentHashCode, theResponseTimeDistribution);
        }
        {
            String theName;
            theName = this.getName();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "name", theName), currentHashCode, theName);
        }
        {
            Double theResponseTime;
            theResponseTime = this.getResponseTime();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "responseTime", theResponseTime), currentHashCode, theResponseTime);
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
        if (draftCopy instanceof SEFF) {
            final SEFF copy = ((SEFF) draftCopy);
            if (this.responseTimeDistribution!= null) {
                ResponseTimeDistribution sourceResponseTimeDistribution;
                sourceResponseTimeDistribution = this.getResponseTimeDistribution();
                ResponseTimeDistribution copyResponseTimeDistribution = ((ResponseTimeDistribution) strategy.copy(LocatorUtils.property(locator, "responseTimeDistribution", sourceResponseTimeDistribution), sourceResponseTimeDistribution));
                copy.setResponseTimeDistribution(copyResponseTimeDistribution);
            } else {
                copy.responseTimeDistribution = null;
            }
            if (this.name!= null) {
                String sourceName;
                sourceName = this.getName();
                String copyName = ((String) strategy.copy(LocatorUtils.property(locator, "name", sourceName), sourceName));
                copy.setName(copyName);
            } else {
                copy.name = null;
            }
            if (this.responseTime!= null) {
                Double sourceResponseTime;
                sourceResponseTime = this.getResponseTime();
                Double copyResponseTime = ((Double) strategy.copy(LocatorUtils.property(locator, "responseTime", sourceResponseTime), sourceResponseTime));
                copy.setResponseTime(copyResponseTime);
            } else {
                copy.responseTime = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new SEFF();
    }

    public void mergeFrom(Object left, Object right) {
        final MergeStrategy strategy = JAXBMergeStrategy.INSTANCE;
        mergeFrom(null, null, left, right, strategy);
    }

    public void mergeFrom(ObjectLocator leftLocator, ObjectLocator rightLocator, Object left, Object right, MergeStrategy strategy) {
        if (right instanceof SEFF) {
            final SEFF target = this;
            final SEFF leftObject = ((SEFF) left);
            final SEFF rightObject = ((SEFF) right);
            {
                ResponseTimeDistribution lhsResponseTimeDistribution;
                lhsResponseTimeDistribution = leftObject.getResponseTimeDistribution();
                ResponseTimeDistribution rhsResponseTimeDistribution;
                rhsResponseTimeDistribution = rightObject.getResponseTimeDistribution();
                ResponseTimeDistribution mergedResponseTimeDistribution = ((ResponseTimeDistribution) strategy.merge(LocatorUtils.property(leftLocator, "responseTimeDistribution", lhsResponseTimeDistribution), LocatorUtils.property(rightLocator, "responseTimeDistribution", rhsResponseTimeDistribution), lhsResponseTimeDistribution, rhsResponseTimeDistribution));
                target.setResponseTimeDistribution(mergedResponseTimeDistribution);
            }
            {
                String lhsName;
                lhsName = leftObject.getName();
                String rhsName;
                rhsName = rightObject.getName();
                String mergedName = ((String) strategy.merge(LocatorUtils.property(leftLocator, "name", lhsName), LocatorUtils.property(rightLocator, "name", rhsName), lhsName, rhsName));
                target.setName(mergedName);
            }
            {
                Double lhsResponseTime;
                lhsResponseTime = leftObject.getResponseTime();
                Double rhsResponseTime;
                rhsResponseTime = rightObject.getResponseTime();
                Double mergedResponseTime = ((Double) strategy.merge(LocatorUtils.property(leftLocator, "responseTime", lhsResponseTime), LocatorUtils.property(rightLocator, "responseTime", rhsResponseTime), lhsResponseTime, rhsResponseTime));
                target.setResponseTime(mergedResponseTime);
            }
        }
    }

}

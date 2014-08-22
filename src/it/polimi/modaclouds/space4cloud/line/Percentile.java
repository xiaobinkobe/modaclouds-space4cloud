//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.22 at 11:04:31 AM CEST 
//


package it.polimi.modaclouds.space4cloud.line;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
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
 *       &lt;attribute name="level" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "percentile")
public class Percentile
    implements Cloneable, CopyTo, Equals, HashCode, MergeFrom, ToString
{

    @XmlAttribute(name = "level", required = true)
    protected double level;
    @XmlAttribute(name = "value", required = true)
    protected double value;

    /**
     * Gets the value of the level property.
     * 
     */
    public double getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     */
    public void setLevel(double value) {
        this.level = value;
    }

    /**
     * Gets the value of the value property.
     * 
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(double value) {
        this.value = value;
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
            double theLevel;
            theLevel = (true?this.getLevel(): 0.0D);
            strategy.appendField(locator, this, "level", buffer, theLevel);
        }
        {
            double theValue;
            theValue = (true?this.getValue(): 0.0D);
            strategy.appendField(locator, this, "value", buffer, theValue);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof Percentile)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final Percentile that = ((Percentile) object);
        {
            double lhsLevel;
            lhsLevel = (true?this.getLevel(): 0.0D);
            double rhsLevel;
            rhsLevel = (true?that.getLevel(): 0.0D);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "level", lhsLevel), LocatorUtils.property(thatLocator, "level", rhsLevel), lhsLevel, rhsLevel)) {
                return false;
            }
        }
        {
            double lhsValue;
            lhsValue = (true?this.getValue(): 0.0D);
            double rhsValue;
            rhsValue = (true?that.getValue(): 0.0D);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "value", lhsValue), LocatorUtils.property(thatLocator, "value", rhsValue), lhsValue, rhsValue)) {
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
            double theLevel;
            theLevel = (true?this.getLevel(): 0.0D);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "level", theLevel), currentHashCode, theLevel);
        }
        {
            double theValue;
            theValue = (true?this.getValue(): 0.0D);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "value", theValue), currentHashCode, theValue);
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
        if (draftCopy instanceof Percentile) {
            final Percentile copy = ((Percentile) draftCopy);
            double sourceLevel;
            sourceLevel = (true?this.getLevel(): 0.0D);
            double copyLevel = strategy.copy(LocatorUtils.property(locator, "level", sourceLevel), sourceLevel);
            copy.setLevel(copyLevel);
            double sourceValue;
            sourceValue = (true?this.getValue(): 0.0D);
            double copyValue = strategy.copy(LocatorUtils.property(locator, "value", sourceValue), sourceValue);
            copy.setValue(copyValue);
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new Percentile();
    }

    public void mergeFrom(Object left, Object right) {
        final MergeStrategy strategy = JAXBMergeStrategy.INSTANCE;
        mergeFrom(null, null, left, right, strategy);
    }

    public void mergeFrom(ObjectLocator leftLocator, ObjectLocator rightLocator, Object left, Object right, MergeStrategy strategy) {
        if (right instanceof Percentile) {
            final Percentile target = this;
            final Percentile leftObject = ((Percentile) left);
            final Percentile rightObject = ((Percentile) right);
            {
                double lhsLevel;
                lhsLevel = (true?leftObject.getLevel(): 0.0D);
                double rhsLevel;
                rhsLevel = (true?rightObject.getLevel(): 0.0D);
                double mergedLevel = ((double) strategy.merge(LocatorUtils.property(leftLocator, "level", lhsLevel), LocatorUtils.property(rightLocator, "level", rhsLevel), lhsLevel, rhsLevel));
                target.setLevel(mergedLevel);
            }
            {
                double lhsValue;
                lhsValue = (true?leftObject.getValue(): 0.0D);
                double rhsValue;
                rhsValue = (true?rightObject.getValue(): 0.0D);
                double mergedValue = ((double) strategy.merge(LocatorUtils.property(leftLocator, "value", lhsValue), LocatorUtils.property(rightLocator, "value", rhsValue), lhsValue, rhsValue));
                target.setValue(mergedValue);
            }
        }
    }

}

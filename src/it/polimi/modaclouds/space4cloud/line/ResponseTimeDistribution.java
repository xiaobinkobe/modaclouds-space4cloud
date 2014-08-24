//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.24 at 05:53:52 PM CEST 
//


package it.polimi.modaclouds.space4cloud.line;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.modaclouds.eu/xsd/2013/6/lineResult}percentile" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "percentile"
})
@XmlRootElement(name = "responseTimeDistribution")
public class ResponseTimeDistribution
    implements Cloneable, CopyTo, Equals, HashCode, MergeFrom, ToString
{

    @XmlElement(required = true)
    protected List<Percentile> percentile;

    /**
     * Gets the value of the percentile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the percentile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPercentile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Percentile }
     * 
     * 
     */
    public List<Percentile> getPercentile() {
        if (percentile == null) {
            percentile = new ArrayList<Percentile>();
        }
        return this.percentile;
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
            List<Percentile> thePercentile;
            thePercentile = (((this.percentile!= null)&&(!this.percentile.isEmpty()))?this.getPercentile():null);
            strategy.appendField(locator, this, "percentile", buffer, thePercentile);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof ResponseTimeDistribution)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ResponseTimeDistribution that = ((ResponseTimeDistribution) object);
        {
            List<Percentile> lhsPercentile;
            lhsPercentile = (((this.percentile!= null)&&(!this.percentile.isEmpty()))?this.getPercentile():null);
            List<Percentile> rhsPercentile;
            rhsPercentile = (((that.percentile!= null)&&(!that.percentile.isEmpty()))?that.getPercentile():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "percentile", lhsPercentile), LocatorUtils.property(thatLocator, "percentile", rhsPercentile), lhsPercentile, rhsPercentile)) {
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
            List<Percentile> thePercentile;
            thePercentile = (((this.percentile!= null)&&(!this.percentile.isEmpty()))?this.getPercentile():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "percentile", thePercentile), currentHashCode, thePercentile);
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
        if (draftCopy instanceof ResponseTimeDistribution) {
            final ResponseTimeDistribution copy = ((ResponseTimeDistribution) draftCopy);
            if ((this.percentile!= null)&&(!this.percentile.isEmpty())) {
                List<Percentile> sourcePercentile;
                sourcePercentile = (((this.percentile!= null)&&(!this.percentile.isEmpty()))?this.getPercentile():null);
                @SuppressWarnings("unchecked")
                List<Percentile> copyPercentile = ((List<Percentile> ) strategy.copy(LocatorUtils.property(locator, "percentile", sourcePercentile), sourcePercentile));
                copy.percentile = null;
                if (copyPercentile!= null) {
                    List<Percentile> uniquePercentilel = copy.getPercentile();
                    uniquePercentilel.addAll(copyPercentile);
                }
            } else {
                copy.percentile = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new ResponseTimeDistribution();
    }

    public void mergeFrom(Object left, Object right) {
        final MergeStrategy strategy = JAXBMergeStrategy.INSTANCE;
        mergeFrom(null, null, left, right, strategy);
    }

    public void mergeFrom(ObjectLocator leftLocator, ObjectLocator rightLocator, Object left, Object right, MergeStrategy strategy) {
        if (right instanceof ResponseTimeDistribution) {
            final ResponseTimeDistribution target = this;
            final ResponseTimeDistribution leftObject = ((ResponseTimeDistribution) left);
            final ResponseTimeDistribution rightObject = ((ResponseTimeDistribution) right);
            {
                List<Percentile> lhsPercentile;
                lhsPercentile = (((leftObject.percentile!= null)&&(!leftObject.percentile.isEmpty()))?leftObject.getPercentile():null);
                List<Percentile> rhsPercentile;
                rhsPercentile = (((rightObject.percentile!= null)&&(!rightObject.percentile.isEmpty()))?rightObject.getPercentile():null);
                List<Percentile> mergedPercentile = ((List<Percentile> ) strategy.merge(LocatorUtils.property(leftLocator, "percentile", lhsPercentile), LocatorUtils.property(rightLocator, "percentile", rhsPercentile), lhsPercentile, rhsPercentile));
                target.percentile = null;
                if (mergedPercentile!= null) {
                    List<Percentile> uniquePercentilel = target.getPercentile();
                    uniquePercentilel.addAll(mergedPercentile);
                }
            }
        }
    }

}

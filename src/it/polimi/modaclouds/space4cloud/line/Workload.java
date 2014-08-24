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
 *         &lt;element ref="{http://www.modaclouds.eu/xsd/2013/6/lineResult}station" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.modaclouds.eu/xsd/2013/6/lineResult}responseTimeDistribution" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="responseTime" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="throughput" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "station",
    "responseTimeDistribution"
})
@XmlRootElement(name = "workload")
public class Workload
    implements Cloneable, CopyTo, Equals, HashCode, MergeFrom, ToString
{

    @XmlElement(required = true)
    protected List<Station> station;
    protected ResponseTimeDistribution responseTimeDistribution;
    @XmlAttribute(name = "name")
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute(name = "responseTime")
    protected Double responseTime;
    @XmlAttribute(name = "throughput")
    protected Double throughput;

    /**
     * Gets the value of the station property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the station property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Station }
     * 
     * 
     */
    public List<Station> getStation() {
        if (station == null) {
            station = new ArrayList<Station>();
        }
        return this.station;
    }

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

    /**
     * Gets the value of the throughput property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThroughput() {
        return throughput;
    }

    /**
     * Sets the value of the throughput property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThroughput(Double value) {
        this.throughput = value;
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
            List<Station> theStation;
            theStation = (((this.station!= null)&&(!this.station.isEmpty()))?this.getStation():null);
            strategy.appendField(locator, this, "station", buffer, theStation);
        }
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
        {
            Double theThroughput;
            theThroughput = this.getThroughput();
            strategy.appendField(locator, this, "throughput", buffer, theThroughput);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof Workload)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final Workload that = ((Workload) object);
        {
            List<Station> lhsStation;
            lhsStation = (((this.station!= null)&&(!this.station.isEmpty()))?this.getStation():null);
            List<Station> rhsStation;
            rhsStation = (((that.station!= null)&&(!that.station.isEmpty()))?that.getStation():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "station", lhsStation), LocatorUtils.property(thatLocator, "station", rhsStation), lhsStation, rhsStation)) {
                return false;
            }
        }
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
        {
            Double lhsThroughput;
            lhsThroughput = this.getThroughput();
            Double rhsThroughput;
            rhsThroughput = that.getThroughput();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "throughput", lhsThroughput), LocatorUtils.property(thatLocator, "throughput", rhsThroughput), lhsThroughput, rhsThroughput)) {
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
            List<Station> theStation;
            theStation = (((this.station!= null)&&(!this.station.isEmpty()))?this.getStation():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "station", theStation), currentHashCode, theStation);
        }
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
        {
            Double theThroughput;
            theThroughput = this.getThroughput();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "throughput", theThroughput), currentHashCode, theThroughput);
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
        if (draftCopy instanceof Workload) {
            final Workload copy = ((Workload) draftCopy);
            if ((this.station!= null)&&(!this.station.isEmpty())) {
                List<Station> sourceStation;
                sourceStation = (((this.station!= null)&&(!this.station.isEmpty()))?this.getStation():null);
                @SuppressWarnings("unchecked")
                List<Station> copyStation = ((List<Station> ) strategy.copy(LocatorUtils.property(locator, "station", sourceStation), sourceStation));
                copy.station = null;
                if (copyStation!= null) {
                    List<Station> uniqueStationl = copy.getStation();
                    uniqueStationl.addAll(copyStation);
                }
            } else {
                copy.station = null;
            }
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
            if (this.throughput!= null) {
                Double sourceThroughput;
                sourceThroughput = this.getThroughput();
                Double copyThroughput = ((Double) strategy.copy(LocatorUtils.property(locator, "throughput", sourceThroughput), sourceThroughput));
                copy.setThroughput(copyThroughput);
            } else {
                copy.throughput = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new Workload();
    }

    public void mergeFrom(Object left, Object right) {
        final MergeStrategy strategy = JAXBMergeStrategy.INSTANCE;
        mergeFrom(null, null, left, right, strategy);
    }

    public void mergeFrom(ObjectLocator leftLocator, ObjectLocator rightLocator, Object left, Object right, MergeStrategy strategy) {
        if (right instanceof Workload) {
            final Workload target = this;
            final Workload leftObject = ((Workload) left);
            final Workload rightObject = ((Workload) right);
            {
                List<Station> lhsStation;
                lhsStation = (((leftObject.station!= null)&&(!leftObject.station.isEmpty()))?leftObject.getStation():null);
                List<Station> rhsStation;
                rhsStation = (((rightObject.station!= null)&&(!rightObject.station.isEmpty()))?rightObject.getStation():null);
                List<Station> mergedStation = ((List<Station> ) strategy.merge(LocatorUtils.property(leftLocator, "station", lhsStation), LocatorUtils.property(rightLocator, "station", rhsStation), lhsStation, rhsStation));
                target.station = null;
                if (mergedStation!= null) {
                    List<Station> uniqueStationl = target.getStation();
                    uniqueStationl.addAll(mergedStation);
                }
            }
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
            {
                Double lhsThroughput;
                lhsThroughput = leftObject.getThroughput();
                Double rhsThroughput;
                rhsThroughput = rightObject.getThroughput();
                Double mergedThroughput = ((Double) strategy.merge(LocatorUtils.property(leftLocator, "throughput", lhsThroughput), LocatorUtils.property(rightLocator, "throughput", rhsThroughput), lhsThroughput, rhsThroughput));
                target.setThroughput(mergedThroughput);
            }
        }
    }

}

/*******************************************************************************
 * Copyright 2014 Giovanni Paolo Gibilisco
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package it.polimi.modaclouds.space4cloud.optimization.solution.impl;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import it.polimi.modaclouds.space4cloud.lqn.LqnResultParser;


/**
 * The Class Compute.
 * @author Giovanni Gibilisco, Michele Ciavotta
 *
 */
public class Compute extends IaaS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4304176863215083824L;

	/** The Constant StandardSpeed. */
	public static final double StandardSpeed = 1000;

	/** The Constant XMLTag. */
	public static final String XMLTag = "Compute"; 

	/** The speed. */
	private double speed;

	/** The ram. */
	private int ram;

	/** The number of cores. */
	private int numberOfCores;

	/** The utilization. */
	private double utilization;


	/* (non-Javadoc)
	 * @see it.polimi.modaclouds.space4cloud.optimization.solution.impl.CloudService#clone()
	 */
	public Compute clone(){

		Compute compute;
		try {
			compute = (Compute) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			compute = new Compute(new String(this.getName()),
					new String(this.getId()),
					new String(this.getProvider()),
					new String(this.getServiceType()),
					new String(this.getServiceName()),
					new String(this.getResourceName()),
					this.getReplicas(),
					this.getNumberOfCores(),
					this.getSpeed(),
					this.getRam());
		}
		return compute;
	}

	/**
	 * Instantiates a new compute.
	 *
	 * @param name the name
	 * @param id the id
	 * @param provider the provider
	 * @param serviceType the service type
	 * @param serviceName the service name
	 * @param resourceName the resource name
	 * @param replicas the replicas
	 * @param numberOfCores the number of cores
	 * @param speed the speed
	 * @param ram the ram
	 */
	public Compute(String name, String id, String provider, String serviceType,String serviceName,String resourceName,
			int replicas, int numberOfCores, double speed, int ram) {
		super(name ,id, provider, serviceType,serviceName,resourceName);
		this.replicas = replicas;
		this.speed = speed;
		this.ram = ram;
		this.numberOfCores = numberOfCores;
		setLQNPropertyTAG("speedFactor");
	}	


	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(!(obj instanceof Compute))
			return false;
				
		Compute comp = (Compute) obj;
		
		return new EqualsBuilder().append(replicas,comp.replicas).
				append(speed,comp.speed).
				append(ram,comp.ram).
				append(numberOfCores, comp.numberOfCores).isEquals();
	}

	
	public int hashCode() {
		return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
				// if deriving: appendSuper(super.hashCode()).
				append(replicas).
				append(speed).
				append(ram).
				append(numberOfCores).
				toHashCode();

	}

	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Gets the ram.
	 *
	 * @return the ram
	 */
	public int getRam() {
		return ram;
	}

	/**
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Sets the ram.
	 *
	 * @param ram the new ram
	 */
	public void setRam(int ram) {
		this.ram = ram;
	}


	/**
	 * Gets the number of cores.
	 *
	 * @return the number of cores
	 */
	public int getNumberOfCores() {
		return numberOfCores;
	}

	/**
	 * Sets the number of cores.
	 *
	 * @param numberOfCores the new number of cores
	 */
	public void setNumberOfCores(int numberOfCores) {
		this.numberOfCores = numberOfCores;
	}	


	/* (non-Javadoc)
	 * @see it.polimi.modaclouds.space4cloud.optimization.solution.impl.CloudService#showStatus(java.lang.String)
	 */
	public String showStatus(String prefix) {
		//super.showStatus(prefix);
		return prefix+"replicas: "+getReplicas()+" type: "+getResourceName();
		//System.out.println(prefix+ "replicas: "+getReplicas()+" speed:_"+speed+"\t ram:_"+ram+" Utilization: "+utilization);
	}


	/**
	 * Gets the speed factor.
	 *
	 * @return the speed factor
	 */
	public double getSpeedFactor() {		
		return getSpeed()/StandardSpeed;
	}


	/* (non-Javadoc)
	 * @see it.polimi.modaclouds.space4cloud.optimization.solution.IConstrainable#update(it.polimi.modaclouds.space4cloud.lqn.LqnResultParser)
	 */
	@Override
	public void update(LqnResultParser results) {
		//update the utilization
		if(results.getUtilization(getName())> 0)			
			utilization = results.getUtilization(getName());		
		else
			utilization = -1;
		//System.err.println("Processor name "+getName()+" not found in the results");

	}


	/* (non-Javadoc)
	 * @see it.polimi.modaclouds.space4cloud.optimization.solution.IUtilizationConstrainable#getUtilization()
	 */
	public double getUtilization() {
		return utilization;
	}


	/**
	 * Sets the utilization.
	 *
	 * @param utilization the new utilization
	 */
	public void setUtilization(double utilization) {
		this.utilization = utilization;
	}


}


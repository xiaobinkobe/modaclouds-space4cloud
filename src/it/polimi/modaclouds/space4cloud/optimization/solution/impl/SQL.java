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

import it.polimi.modaclouds.space4cloud.lqn.LqnResultParser;

// TODO: Auto-generated Javadoc
/**
 * The Class SQL.
 */
public class SQL extends Database {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4717640652547301426L;

	/**
	 * Instantiates a new sql.
	 * 
	 * @param name
	 *            the name
	 * @param id
	 *            the id
	 * @param provider
	 *            the provider
	 * @param serviceType
	 *            the service type
	 * @param serviceName
	 *            the service name
	 * @param resourceName
	 *            the resource name
	 */
	public SQL(String provider, String serviceType,
			String serviceName, String resourceName, String technology,
			boolean ssdOptimized, int storage, int maxConnections, int maxRollbackHours,
			int replicas, boolean multiAzReplicas, Compute compute) {
		super(provider, serviceType, serviceName, resourceName, DatabaseType.Relational, ssdOptimized, storage, replicas, multiAzReplicas, compute);
		
		this.technology = technology;
		this.maxConnections = maxConnections;
		this.maxRollbackHours = maxRollbackHours;
	}
	
	private String technology;
	
	private int maxConnections;
	
	private int maxRollbackHours;
	
	public static final int DEFAULT_MAX_CONNECTIONS = Integer.MAX_VALUE;
	public static final int DEFAULT_MAX_ROLLBACK_HOURS = 0;

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getMaxRollbackHours() {
		return maxRollbackHours;
	}

	public void setMaxRollbackHours(int maxRollbackHours) {
		this.maxRollbackHours = maxRollbackHours;
	}
	
	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.modaclouds.space4cloud.optimization.solution.impl.CloudService
	 * #clone()
	 */
	@Override
	public SQL clone() {
		SQL sql = new SQL(new String(this.getProvider()), new String(
						this.getServiceType()), new String(
						this.getServiceName()), new String(
						this.getResourceName()),
						new String(this.getTechnology()),
						this.isSsdOptimized(),
						this.getStorage(),
						this.getMaxConnections(),
						this.getMaxRollbackHours(),
						this.getReplicas(),
						this.isMultiAzReplicas(),
						this.getCompute().clone()
				
				);

		return sql;
	}

	@Override
	public double getResponseTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.modaclouds.space4cloud.optimization.solution.IConstrainable
	 * #update(it.polimi.modaclouds.space4cloud.lqn.LqnResultParser)
	 */
	@Override
	public void update(LqnResultParser parser) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}

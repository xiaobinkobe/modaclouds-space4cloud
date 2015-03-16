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

public abstract class Database extends PaaS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2762220847804198922L;

	public Database(String provider,
			String serviceType, String serviceName, String resourceName, DatabaseType type,
			boolean ssdOptimized, int storage, int dataReplicas, boolean multiAzReplicas, Compute compute) {
		super(provider, serviceType, serviceName, resourceName, Database.DEFAULT_REPLICAS, dataReplicas,
				Database.DEFAULT_REPLICAS_CHANGEABLE, Database.DEFAULT_REPLICAS_PAYED_SINGULARLY);
		
		this.type = type;
		this.ssdOptimized = ssdOptimized;
		this.multiAzReplicas = multiAzReplicas;
		this.compute = compute;
		this.storage = storage;
	}

	private boolean ssdOptimized;
	
	private boolean multiAzReplicas;
	
	private int storage;
	
	private Compute compute;
	
	private DatabaseType type;
	
	public static final boolean DEFAULT_SSD_OPTIMIZED = false;
	public static final int DEFAULT_REPLICAS = 1;
	public static final int DEFAULT_DATA_REPLICAS = 1;
	public static final boolean DEFAULT_MULTI_AZ_REPLICAS = false;
	public static final int DEFAULT_STORAGE = Integer.MAX_VALUE;
	public static final boolean DEFAULT_REPLICAS_CHANGEABLE = false;
	public static final boolean DEFAULT_REPLICAS_PAYED_SINGULARLY = false;

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public DatabaseType getType() {
		return type;
	}

	public void setType(DatabaseType type) {
		this.type = type;
	}
	
	public boolean isSsdOptimized() {
		return ssdOptimized;
	}

	public void setSsdOptimized(boolean ssdOptimized) {
		this.ssdOptimized = ssdOptimized;
	}

	public boolean isMultiAzReplicas() {
		return multiAzReplicas;
	}

	public void setMultiAzReplicas(boolean multiAzReplicas) {
		this.multiAzReplicas = multiAzReplicas;
	}

	public Compute getCompute() {
		return compute;
	}

	public void setCompute(Compute compute) {
		this.compute = compute;
	}

	@Override
	public CloudService clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(LqnResultParser parser) {
		// TODO Auto-generated method stub

	}
	
	public static enum DatabaseType {
		NoSQL("nosql"), Relational("relational"), TableDatastore("table"), BlobDatastore("document");
		
		private String name;
		
		private DatabaseType(String name) {
			this.name = name;
		}
		
		public static DatabaseType getById(int id) {
			DatabaseType[] values = DatabaseType.values();
			if (id < 0)
				id = 0;
			else if (id >= values.length)
				id = values.length - 1;
			return values[id];
		}
		
		public static DatabaseType getByName(String name) {
			DatabaseType[] values = DatabaseType.values();
			for (DatabaseType pt : values) {
				if (pt.name.equals(name))
					return pt;
			}
			return null;
		}

		public static int size() {
			return DatabaseType.values().length;			
		}
		
		public String getName() {
			return name;
		}
	}

}

package it.polimi.modaclouds.space4cloud.optimization.solution.impl;

import it.polimi.modaclouds.space4cloud.lqn.LqnResultParser;

// TODO: Auto-generated Javadoc
/**
 * The Class SQL.
 */
public class SQL extends Database {

	
	/* (non-Javadoc)
	 * @see it.polimi.modaclouds.space4cloud.optimization.solution.impl.CloudService#clone()
	 */
	public SQL clone(){
		
		SQL sql = new SQL(new String(this.getName()),
						  new String(this.getId()),
						  new String(this.getProvider()),
						  new String(this.getServiceType()),
						  new String(this.getServiceName()),
						  new String(this.getResourceName()));
		
		return sql;
	}
	
	/**
	 * Instantiates a new sql.
	 *
	 * @param name the name
	 * @param id the id
	 * @param provider the provider
	 * @param serviceType the service type
	 * @param serviceName the service name
	 * @param resourceName the resource name
	 */
	public SQL(String name, String id, String provider, String serviceType, String serviceName,String resourceName) {
		super( name, id, provider,serviceType,serviceName,resourceName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see it.polimi.modaclouds.space4cloud.optimization.solution.IConstrainable#update(it.polimi.modaclouds.space4cloud.lqn.LqnResultParser)
	 */
	@Override
	public void update(LqnResultParser parser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getResponseTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}

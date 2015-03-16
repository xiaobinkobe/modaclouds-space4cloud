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
/**
 * 
 */
package it.polimi.modaclouds.space4cloud.db;

import it.polimi.modaclouds.resourcemodel.cloud.CloudElement;
import it.polimi.modaclouds.resourcemodel.cloud.CloudFactory;
import it.polimi.modaclouds.resourcemodel.cloud.CloudPlatform;
import it.polimi.modaclouds.resourcemodel.cloud.CloudPlatformProperty;
import it.polimi.modaclouds.resourcemodel.cloud.CloudPlatformPropertyName;
import it.polimi.modaclouds.resourcemodel.cloud.CloudResource;
import it.polimi.modaclouds.resourcemodel.cloud.CloudResourceType;
import it.polimi.modaclouds.resourcemodel.cloud.Cost;
import it.polimi.modaclouds.resourcemodel.cloud.Database;
import it.polimi.modaclouds.resourcemodel.cloud.IaaS_Service;
import it.polimi.modaclouds.resourcemodel.cloud.PaaS_Service;
import it.polimi.modaclouds.resourcemodel.cloud.V_Memory;
import it.polimi.modaclouds.resourcemodel.cloud.V_Storage;
import it.polimi.modaclouds.resourcemodel.cloud.VirtualHWResource;
import it.polimi.modaclouds.resourcemodel.cloud.VirtualHWResourceType;
import it.polimi.modaclouds.space4cloud.optimization.bursting.PrivateCloud;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Backend;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.BlobDatastore;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Cache;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.CloudService;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Compute;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Database.DatabaseType;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Frontend;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.IaaS;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.PaaS;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.PaaS.PaaSType;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Queue;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.SQL;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.TableDatastore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michele Ciavotta the aim of this class is to provide an object able
 *         to retrieve the data related to a certain provider and service
 */
public class DataHandler {
	//	private static final Logger logger = LoggerHelper.getLogger(DataHandler.class);

	private static final Logger logger=LoggerFactory.getLogger(DataHandler.class);
	private CloudProvidersDictionary cloudProviders;

	/**
	 * Instantiates a new data handler. it also charges data from the database
	 * 
	 * @param provider
	 *            the provider
	 * @throws SQLException
	 */
	public DataHandler() throws SQLException {
		//connect to the database
		cloudProviders = new CloudProvidersDictionary();
	}
	public static void main(String[] args) {
		DataHandler handler = null; 

		try {
			DatabaseConnector.initConnection(null);
			handler = DataHandlerFactory.getHandler();
		} catch (DatabaseConnectionFailureExteption | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String provider="Amazon";
//		Set<String> providers = handler.getCloudProviders();
//		for(String provider:providers){
			logger.info("Provider: "+provider);
			
			String[] servicesTypes = new String[] {
//					"Compute",
//					"Frontend",
//					"Backend",
//					"Middleware",
					"Queue",
//					"Cache",
					"NoSQL_DB",
//					"RelationalDB",
					"Storage"
				};
			
			for (String s : servicesTypes) {
				List<String> services = handler.getServices(provider, s);
				for(String service:services){
					logger.info("> "+service + " (" + s +")");
					
					List<String> sizes = handler.getCloudElementSizes(provider, service);
					for(String size:sizes){
						logger.info("> > "+size);
						
						CloudElement ce = handler.getCloudElement(provider, service, size);
						if (ce != null) {
							logger.info("> > > " + ce.toString());
							if (ce instanceof CloudPlatform) {
								EList<CloudResource> resources = handler.getCloudPlatformRunOnResources(provider, service, size);
								
								for(CloudResource cr:resources)
									logger.info("> > > > "+cr.toString());
							}
						}
					}
				}
			}
//		}

		logger.info("End");


	}
	
	/**
	 * Gets the amount of memory of the the cloud resource.
	 * 
	 * @param provider
	 *            the provider
	 * @param serviceName
	 *            the iass service name
	 * @param resourceName
	 *            the resource name
	 * @return the amount of ram
	 */
	public Integer getAmountMemory(String provider, String serviceName,
			String resourceName) {
//		CloudResource cr = getCloudResource(provider, serviceName, resourceName);
//
//		for (VirtualHWResource i : cr.getComposedOf()) {
//			if (i.getType() == VirtualHWResourceType.MEMORY) {
//				/* a cast to V_Memory interface is needed */
//				return ((V_Memory) i).getSize();
//			}
//		}
//		/* In case of errors */
//		return -1;
		
		CloudElement ce = getCloudElement(provider, serviceName, resourceName);
		
		if (ce != null)
			if (ce instanceof CloudResource)
				return getAmountMemory((CloudResource)ce);
			else if (ce instanceof CloudPlatform)
				return getAmountMemory((CloudPlatform)ce);
		
		/* In case of errors */
		return -1;

	}
	
	private Integer getAmountMemory(CloudResource cr) {
		if (cr != null)
			for (VirtualHWResource i : cr.getComposedOf()) {
				if (i.getType() == VirtualHWResourceType.MEMORY) {
					/* a cast to V_Memory interface is needed */
					return ((V_Memory) i).getSize();
				}
			}
		/* In case of errors */
		return -1;
	}

	public Set<String> getCloudProviders() {
		return cloudProviders.getProviderDBConnectors().keySet();
	}
	
	public static final int MAX_ATTEMPTS = 2;
	
	public static final int MAX_TOTAL_RESETS = 10;
	
	private int resets = 0;
	
	private void resetDatabase() {
		if (resets < MAX_TOTAL_RESETS) {
			try {
				cloudProviders = new CloudProvidersDictionary();
				logger.debug("Database resetted!");
				resets++;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public CloudResource getCloudResource(String provider, String serviceName,
			String resourceName) {
		
		for (int attempt = 1; attempt <= MAX_ATTEMPTS; ++attempt) {
			
			CloudResource res = getCloudResourceInternal(provider, serviceName, resourceName, true);
			if (res != null)
				return res;
		
			resetDatabase();
		}
		
		return null;
	}
	
	private CloudResource getCloudResourceInternal(String provider, String serviceName,
			String resourceName, boolean firstWithValidCost) {
		
		ProviderDBConnector pdb = cloudProviders
				.getProviderDBConnectors().get(provider); // provider
		
		IaaS_Service service = pdb
				.getIaaSServicesHashMap().get(serviceName); // service
		
		if (service == null)
			return null;
		
		List<CloudResource> cloudResourceList = service.getComposedOf();

		// TODO: Controllare questa cosa :(
		for (CloudResource cr : cloudResourceList) {
			if (cr.getName().equals(resourceName))
				if (!firstWithValidCost || (cr.getHasCost() != null && cr.getHasCost().size() > 0))
					return cr;
		}
		
		return null;
	}
	
	public List<String> getCloudResourceSizes(String provider,
			String serviceName) {
		
		for (int attempt = 1; attempt <= MAX_ATTEMPTS; ++attempt) {
			
			List<String> res = getCloudResourceSizesInternal(provider, serviceName);
			if (res != null && res.size() > 0)
				return res;
		
			resetDatabase();
		}
		
		return new ArrayList<>();
	}

	private List<String> getCloudResourceSizesInternal(String provider,
			String serviceName) {
		
		IaaS_Service service = cloudProviders
				.getProviderDBConnectors().get(provider) // provider
				.getIaaSServicesHashMap().get(serviceName); // service
		
		if (service == null)
			return null;
		
		List<CloudResource> cloudResourceList = service.getComposedOf();

		List<String> names = new ArrayList<String>(cloudResourceList.size());
		for (CloudResource tmp : cloudResourceList) {
			names.add(tmp.getName());
		}
		return names;
	}
	
	public List<String> getCloudElementSizes(String provider,
			String serviceName) {
		List<String> res = getCloudResourceSizesInternal(provider, serviceName);
		if (res != null)
			return res;
		res = getCloudPlatformSizes(provider, serviceName);
		if (res != null)
			return res;
		
		return new ArrayList<String>();
	}

	/**
	 * Gets the cost specification of the resource.
	 * 
	 * @param provider
	 *            the provider
	 * @param serviceName
	 *            the iass service name
	 * @param resourceName
	 *            the resource name
	 * @return the cost
	 */
	public EList<Cost> getCost(String provider, String serviceName,
			String resourceName) {
//		CloudResource cr = getCloudResource(provider, serviceName, resourceName);
//		return cr.getHasCost();
		
		CloudElement ce = getCloudElement(provider, serviceName, resourceName);
		return ce.getHasCost();
	}

	/**
	 * Build a IaaS resource from a cloud resource, currently supports only
	 * Compute instances
	 * 
	 * @param service
	 * @param cr
	 * @return IaaS representation of the cloud resource object
	 */
	private IaaS getIaaSfromCloudResource(CloudService service, CloudResource cr) {
		return getIaaSfromCloudResource(
				service.getProvider(), service.getServiceType(), service.getServiceName(),
				service.getResourceName(), ((IaaS) service).getReplicas(), cr);
	}
	
	public CloudService getCloudService(String provider, String serviceType, String serviceName, String resourceName, int replicas) {
		CloudElement ce = getCloudElement(provider, serviceName, resourceName);
		if (ce instanceof CloudResource)
			return getIaaSfromCloudResource(provider, serviceType, serviceName, resourceName, replicas, (CloudResource)ce);
		else if (ce instanceof CloudPlatform)
			return getPaaSfromCloudPlatform(provider, serviceType, serviceName, resourceName, replicas, (CloudPlatform)ce);
		return null;
	}
	
	private IaaS getIaaSfromCloudResource(String provider, String serviceType, String serviceName, String resourceName, int replicas, CloudResource cr) {

		double speed = 0;
		int numberOfCores = 0;
		int ram = 0;

		EList<VirtualHWResource> hw = cr.getComposedOf();
		for (VirtualHWResource vhw : hw) {
			if (vhw.getType() == VirtualHWResourceType.CPU) {
				speed = vhw.getProcessingRate(); // speed
				numberOfCores = vhw.getNumberOfReplicas();// number
			} else if (vhw.getType() == VirtualHWResourceType.MEMORY) {
				ram = ((V_Memory) vhw).getSize();
			}

		}
		return new Compute(	provider, serviceType,
				serviceName, cr.getName(),
				replicas, numberOfCores, speed, ram);
	}
	
	/**
	 * Gets the number of replicas.
	 * 
	 * @param provider
	 *            the provider
	 * @param iassServiceName
	 *            the iass service name
	 * @param resourceName
	 *            the resource name
	 * @return the number of replicas
	 */
	public Integer getNumberOfReplicas(String provider, String serviceName,
			String resourceName) {
//		CloudResource cr = getCloudResource(provider, serviceName, resourceName);
//		for (VirtualHWResource i : cr.getComposedOf()) {
//			if (i.getType() == VirtualHWResourceType.CPU) {
//				return i.getNumberOfReplicas();
//			}
//		}
//		/* In case of errors */
//		return -1;
		
		CloudElement ce = getCloudElement(provider, serviceName, resourceName);
		
		if (ce != null)
			if (ce instanceof CloudResource)
				return getNumberOfReplicas((CloudResource)ce);
			else if (ce instanceof CloudPlatform)
				return getNumberOfReplicas((CloudPlatform)ce);
		
		/* In case of errors */
		return -1;

	}
	
	private Integer getNumberOfReplicas(CloudResource cr) {
		if (cr != null)
			for (VirtualHWResource i : cr.getComposedOf()) {
				if (i.getType() == VirtualHWResourceType.CPU) {
					return i.getNumberOfReplicas();
				}
			}
		/* In case of errors */
		return -1;
	}
	
	public Double getProcessingRate(String provider, String serviceName,
			String resourceName) {
		
		for (int attempt = 1; attempt <= MAX_ATTEMPTS; ++attempt) {
			
			double res = getProcessingRateInternal(provider, serviceName, resourceName);
			if (res > -1.0)
				return res;
		
			resetDatabase();
		}
		
		return -1.0;
	}
	
	/**
	 * Gets the processing rate of the cpus.
	 * 
	 * @param provider
	 *            the id provider
	 * @param serviceName
	 *            the id iass service
	 * @param resourceName
	 *            the id resource
	 * @return the speed
	 */
	private double getProcessingRateInternal(String provider, String serviceName,
			String resourceName) {
		
		CloudPlatform cp = getCloudPlatform(provider, serviceName, resourceName);
		if (cp != null)
			return getProcessingRate(cp);
		
		CloudResource cr = getCloudResourceInternal(provider, serviceName, resourceName, false);
		
//		List<CloudResource> cloudResourceList = cloudProviders
//				.getProviderDBConnectors().get(provider) // provider
//				.getIaaSServicesHashMap().get(serviceName) // service
//				.getComposedOf();
//
//		CloudResource cr = null;
//		// here we lose the OS type distinction
//		for (CloudResource tmp : cloudResourceList) {
//			if (tmp.getName().equals(resourceName)) {
//				cr = tmp;
//			}
//		}

		return getProcessingRate(cr);

	}
	
	private Double getProcessingRate(CloudResource cr) {
		if (cr != null)
			for (VirtualHWResource i : cr.getComposedOf()) {
				if (i.getType() == VirtualHWResourceType.CPU) {
					return i.getProcessingRate();
				}
			}
		return -1.0;
	}
	
	private Integer getStorage(CloudResource cr) {
		if (cr != null)
			for (VirtualHWResource i : cr.getComposedOf()) {
				if (i.getType() == VirtualHWResourceType.STORAGE) {
					return ((V_Storage) i).getSize();
				}
			}
		return -1;
	}
	
	public List<IaaS> getSameServiceResource(CloudService service, String region) {
		
		for (int attempt = 1; attempt <= MAX_ATTEMPTS; ++attempt) {
			
			List<IaaS> res = getSameServiceResourceInternal(service, region);
			if (res != null && res.size() > 0)
				return res;
		
			resetDatabase();
		}
		
		return new ArrayList<>();
	}

	/**
	 * returns a list of IaaS services with the provider and sarvice name equals
	 * to those of provided CloudService
	 * 
	 * @param service
	 *            - the original cloud service
	 * @param region
	 *            - the region
	 * @return a list of IaaS services
	 */
	private List<IaaS> getSameServiceResourceInternal(CloudService service, String region) {
		List<IaaS> resources = new ArrayList<>();
		for (CloudResource cr : cloudProviders.getProviderDBConnectors()
				.get(service.getProvider()) // provider
				.getIaaSServicesHashMap().get(service.getServiceName()) // service
				.getComposedOf()) {

			IaaS iaas = getIaaSfromCloudResource(service, cr);
			for (Cost cost : cr.getHasCost()) {
				// if the iaas has not been already inserted AND (the region has
				// not been specified OR it has the same region)
				if (!resources.contains(iaas)
						&& (region == null || cost.getRegion() == null || cost
						.getRegion().equals(region))) {
					resources.add(iaas);
				}
			}
		}
		return resources;
	}
	
	public List<String> getServices(String provider, String serviceType) {
		
		for (int attempt = 1; attempt <= MAX_ATTEMPTS; ++attempt) {
			
			List<String> res = getServicesInternal(provider, serviceType);
			if (res != null && res.size() > 0)
				return res;
		
			resetDatabase();
		}
		
		return new ArrayList<>();
	}

	private List<String> getServicesInternal(String provider, String serviceType) {

		CloudElement expectedResource = null;
		switch (serviceType) {
		case "Compute":
			expectedResource = CloudFactory.eINSTANCE.createCompute();
			break;
		case "Frontend":
			expectedResource = CloudFactory.eINSTANCE.createFrontend();
			break;
		case "Backend":
			expectedResource = CloudFactory.eINSTANCE.createBackend();
			break;
		case "Queue":
			expectedResource = CloudFactory.eINSTANCE.createQueue();
			break;
		case "Cache":
			expectedResource = CloudFactory.eINSTANCE.createCache();
			break;
		case "NoSQL_DB":
			expectedResource = CloudFactory.eINSTANCE.createNoSQL_DB();
			break;
		case "RelationalDB":
			expectedResource = CloudFactory.eINSTANCE.createRelationalDB();
			break;
		default:
			break;
		}
		
		List<String> filteredServices = new ArrayList<String>();
		
		if (expectedResource instanceof CloudResource) {
			List<IaaS_Service> iaasServices = cloudProviders
					.getProviderDBConnectors().get(provider).getIaaSServices();
			logger.trace("getting services of type "+serviceType+" from "+provider);
			logger.trace("found "+iaasServices.size()+" services");
			for (IaaS_Service service : iaasServices) 
				logger.trace("service: "+service.getName()+" class: "+service.getClass());
			for (IaaS_Service service : iaasServices) {
				List<CloudResource> resources = service.getComposedOf();
				// if there is any resource and it is of a subtype of the expected
				// one add the service to the list of filtered services
				// (we assume here that each service provide resources of the same
				// type
				logger.trace("Empty resources: "+resources.isEmpty());
				if (!resources.isEmpty()){
					logger.trace("fisrt element class: "+resources.get(0).getClass());
					logger.trace("Expected Class: "+expectedResource.getClass());
					logger.trace("is assignable: "+resources.get(0).getClass()
							.isAssignableFrom(expectedResource.getClass()));
				}
				if (!resources.isEmpty()
						&& resources.get(0).getClass()
						.isAssignableFrom(expectedResource.getClass())) {
					logger.trace("adding service: "+service.getName());
					filteredServices.add(service.getName());
				}
			}
		} else if (expectedResource instanceof CloudPlatform) {
			List<PaaS_Service> paasServices = cloudProviders
					.getProviderDBConnectors().get(provider).getPaaSServices();
			logger.trace("getting services of type "+serviceType+" from "+provider);
			logger.trace("found "+paasServices.size()+" services");
			for (PaaS_Service service : paasServices) 
				logger.trace("service: "+service.getName()+" class: "+service.getClass());
			for (PaaS_Service service : paasServices) {
				List<CloudPlatform> platforms = service.getComposedOf();
				// if there is any resource and it is of a subtype of the expected
				// one add the service to the list of filtered services
				// (we assume here that each service provide resources of the same
				// type
				logger.trace("Empty platforms: "+platforms.isEmpty());
				if (!platforms.isEmpty()){
					logger.trace("fisrt element class: "+platforms.get(0).getClass());
					logger.trace("Expected Class: "+expectedResource.getClass());
					logger.trace("is assignable: "+platforms.get(0).getClass()
							.isAssignableFrom(expectedResource.getClass()));
				}
				if (!platforms.isEmpty()
						&& platforms.get(0).getClass()
						.isAssignableFrom(expectedResource.getClass())) {
					logger.trace("adding service: "+service.getName());
					filteredServices.add(service.getName());
				}
			}
		}
		
		return filteredServices;
	}
	
	public double getAvailability(String provider) {
		if (provider.indexOf(PrivateCloud.BASE_PROVIDER_NAME) > -1)
			return 0.95;
		
		ProviderDBConnector pdb = cloudProviders
				.getProviderDBConnectors().get(provider); // provider
		
		return pdb.getAvailability();
	}
	
	// Methods for PaaSs
	
	public CloudElement getCloudElement(String provider, String serviceName,
			String resourceName) {
		
		CloudPlatform cp = getCloudPlatform(provider, serviceName, resourceName);
		if (cp != null)
			return cp;
		
		CloudResource cr = getCloudResource(provider, serviceName, resourceName);
		if (cr != null)
			return cr;
		
		return null;
	}
	
	private Integer getAmountMemory(CloudPlatform cp) {
		CloudResource cr = runOn(cp, CloudResourceType.COMPUTE);
		return getAmountMemory(cr);
	}
	
	private Integer getNumberOfReplicas(CloudPlatform cp) {
		CloudResource cr = runOn(cp, CloudResourceType.COMPUTE);
		return getNumberOfReplicas(cr);
	}
	
	private Double getProcessingRate(CloudPlatform cp) {
		CloudResource cr = runOn(cp, CloudResourceType.COMPUTE);
		return getProcessingRate(cr);
	}
	
	@SuppressWarnings("unused")
	private Integer getStorage(CloudPlatform cp) {
		CloudResource cr = runOn(cp, CloudResourceType.CLOUDSTORAGE);
		return getStorage(cr);
	}
	
	public CloudPlatform getCloudPlatform(String provider, String serviceName,
			String resourceName) {
		
		ProviderDBConnector pdb = cloudProviders
				.getProviderDBConnectors().get(provider); // provider
		
		PaaS_Service service = pdb
				.getPaaSServicesHashMap().get(serviceName); // service;
		
		if (service == null)
			return null;
		
		List<CloudPlatform> cloudPlatformList = service.getComposedOf();

		for (CloudPlatform cp : cloudPlatformList) {
			if (cp.getName().equals(resourceName)) {
				if ((cp.getHasCost() != null)
						&& (cp.getHasCost().size() > 0))
					return cp;
				
				EList<CloudResource> crs = cp.getRunsOnCloudResource();
				
				for (CloudResource cr : crs) {
					if ((cr.getHasCost() != null)
							&& (cr.getHasCost().size() > 0))
						return cp;
				}
			}
					
		}
		
		return null;
	}
	
	public List<String> getCloudPlatformSizes(String provider,
			String serviceName) {
		
		PaaS_Service service = cloudProviders
				.getProviderDBConnectors().get(provider) 	// provider
				.getPaaSServicesHashMap().get(serviceName); // service
		
		if (service == null)
			return null;
		
		List<CloudPlatform> cloudPlatformList = service.getComposedOf();

		List<String> names = new ArrayList<String>(cloudPlatformList.size());
		for (CloudPlatform tmp : cloudPlatformList) {
			names.add(tmp.getName());
		}
		return names;
	}
	
	public EList<CloudResource> getCloudPlatformRunOnResources(String provider,
			String serviceName, String resourceName) {
		
		CloudPlatform cp = getCloudPlatform(provider, serviceName, resourceName);
		
		return cp.getRunsOnCloudResource();
	}
	
	private PaaS getPaaSfromCloudPlatform(CloudService service, CloudPlatform cp) {
		return getPaaSfromCloudPlatform(
				service.getProvider(), service.getServiceType(), service.getServiceName(),
				service.getResourceName(), 1, cp);
	}
	
	private PaaS getPaaSfromCloudPlatform(String provider, String serviceType, String serviceName, String resourceName, int replicasUh, CloudPlatform cp) {

//		double speed = 0;
//		int numberOfCores = 0;
//		int ram = 0;
		
		PaaSType pt = PaaSType.getByName(cp.getPlatformType().getLiteral());
		if (pt == null)
			return null;
		
		PaaS p = null;
		
		ArrayList<String> languages = new ArrayList<String>();
		{
			String lang = cp.getLanguage();
			if (lang != null)
				for (String s : lang.split(";"))
					languages.add(s);
		}
		
		CloudResource cr = runOn(cp, CloudResourceType.COMPUTE);
		CloudResource st = runOn(cp, CloudResourceType.CLOUDSTORAGE);
		Compute c = null;
		int replicas = 1;
		int dataReplicas = 1;
		int storage = 0;
		if (st != null)
			storage = getStorage(st);
		
		switch (pt) {
		case Frontend:
			replicas = getPropertyValue(cp, CloudPlatformPropertyName.REPLICAS, Frontend.DEFAULT_REPLICAS);
			dataReplicas = getPropertyValue(cp, CloudPlatformPropertyName.DATA_REPLICAS, Frontend.DEFAULT_DATA_REPLICAS);
			c = (Compute)getIaaSfromCloudResource(provider, serviceType, serviceName,
					resourceName, replicas, cr);
			p = new Frontend(provider, serviceType, serviceName,
					resourceName,
					replicas,
					dataReplicas,
					getPropertyValue(cp, CloudPlatformPropertyName.MULTI_AZ_REPLICAS, Frontend.DEFAULT_MULTI_AZ_REPLICAS),
					languages,
					c,
					storage > -1 ? storage : getPropertyValue(cp, CloudPlatformPropertyName.STORAGE, Frontend.DEFAULT_STORAGE),
					getPropertyValue(cp, CloudPlatformPropertyName.MAX_CONNECTIONS, Frontend.DEFAULT_MAX_CONNECTIONS),
					getPropertyValue(cp, CloudPlatformPropertyName.REPLICAS_CHANGEABLE, Frontend.DEFAULT_REPLICAS_CHANGEABLE),
					getPropertyValue(cp, CloudPlatformPropertyName.REPLICAS_PAYED_SINGULARLY, Frontend.DEFAULT_REPLICAS_PAYED_SINGULARLY));
			break;
		case Backend:
			replicas = getPropertyValue(cp, CloudPlatformPropertyName.REPLICAS, Backend.DEFAULT_REPLICAS);
			dataReplicas = getPropertyValue(cp, CloudPlatformPropertyName.DATA_REPLICAS, Backend.DEFAULT_DATA_REPLICAS);
			c = (Compute)getIaaSfromCloudResource(provider, serviceType, serviceName,
					resourceName, replicas, cr);
			p = new Backend(provider, serviceType, serviceName,
					resourceName,
					replicas,
					dataReplicas,
					getPropertyValue(cp, CloudPlatformPropertyName.MULTI_AZ_REPLICAS, Backend.DEFAULT_MULTI_AZ_REPLICAS),
					languages,
					c,
					storage > -1 ? storage : getPropertyValue(cp, CloudPlatformPropertyName.STORAGE, Backend.DEFAULT_STORAGE),
					getPropertyValue(cp, CloudPlatformPropertyName.MAX_CONNECTIONS, Backend.DEFAULT_MAX_CONNECTIONS),
					getPropertyValue(cp, CloudPlatformPropertyName.REPLICAS_CHANGEABLE, Backend.DEFAULT_REPLICAS_CHANGEABLE),
					getPropertyValue(cp, CloudPlatformPropertyName.REPLICAS_PAYED_SINGULARLY, Backend.DEFAULT_REPLICAS_PAYED_SINGULARLY));
			break;
		case Queue:
			replicas = 1;
			dataReplicas = getPropertyValue(cp, CloudPlatformPropertyName.DATA_REPLICAS, Queue.DEFAULT_DATA_REPLICAS);
			c = (Compute)getIaaSfromCloudResource(provider, serviceType, serviceName,
					resourceName, replicas, cr);
			p = new Queue(provider, serviceType, serviceName,
					resourceName,
					getPropertyValue(cp, CloudPlatformPropertyName.REQUEST_SIZE, Queue.DEFAULT_REQUEST_SIZE),
					getPropertyValue(cp, CloudPlatformPropertyName.ORDER_PRESERVING, Queue.DEFAULT_ORDER_PRESERVING),
					getPropertyValue(cp, CloudPlatformPropertyName.MAX_CONNECTIONS, Queue.DEFAULT_MAX_CONNECTIONS),
					dataReplicas,
					getPropertyValue(cp, CloudPlatformPropertyName.MULTI_AZ_REPLICAS, Queue.DEFAULT_MULTI_AZ_REPLICAS),
					getPropertyValue(cp, CloudPlatformPropertyName.MAX_REQUESTS, Queue.DEFAULT_MAX_REQUESTS),
					getPropertyValue(cp, CloudPlatformPropertyName.MULTIPLYING_FACTOR, Queue.DEFAULT_MULTIPLYING_FACTOR),
					getPropertyValue(cp, CloudPlatformPropertyName.DELAY, Queue.DEFAULT_DELAY),
					c);
			break;
		case Cache:
			replicas = 1;
			dataReplicas = getPropertyValue(cp, CloudPlatformPropertyName.DATA_REPLICAS, Cache.DEFAULT_DATA_REPLICAS);
			c = (Compute)getIaaSfromCloudResource(provider, serviceType, serviceName,
					resourceName, replicas, cr);
			p = new Cache(provider, serviceType, serviceName,
					resourceName,
					cp.getTechnology(),
					dataReplicas,
					getPropertyValue(cp, CloudPlatformPropertyName.MULTI_AZ_REPLICAS, Cache.DEFAULT_MULTI_AZ_REPLICAS),
					getPropertyValue(cp, CloudPlatformPropertyName.MAX_CONNECTIONS, Cache.DEFAULT_MAX_CONNECTIONS),
					storage > -1 ? storage : getPropertyValue(cp, CloudPlatformPropertyName.STORAGE, Cache.DEFAULT_STORAGE),
					c);
			break;
		case DataBase:
			DatabaseType dbt = DatabaseType.getByName(((Database)cp).getDBType().getLiteral());
			if (dbt == DatabaseType.NoSQL)
				dbt = DatabaseType.getByName(cp.getTechnology());
			
			switch (dbt) {
			case Relational:
				replicas = 1;
				dataReplicas = getPropertyValue(cp, CloudPlatformPropertyName.DATA_REPLICAS, SQL.DEFAULT_DATA_REPLICAS);
				c = (Compute)getIaaSfromCloudResource(provider, serviceType, serviceName,
						resourceName, replicas, cr);
				p = new SQL(provider, serviceType, serviceName,
						resourceName, cp.getTechnology(),
						getPropertyValue(cp, CloudPlatformPropertyName.SSD_OPTIMIZED, SQL.DEFAULT_SSD_OPTIMIZED),
						storage > -1 ? storage : getPropertyValue(cp, CloudPlatformPropertyName.STORAGE, SQL.DEFAULT_STORAGE),
						getPropertyValue(cp, CloudPlatformPropertyName.MAX_CONNECTIONS, SQL.DEFAULT_MAX_CONNECTIONS),
						getPropertyValue(cp, CloudPlatformPropertyName.MAX_ROLLBACK_HOURS, SQL.DEFAULT_MAX_ROLLBACK_HOURS),
						dataReplicas,
						getPropertyValue(cp, CloudPlatformPropertyName.MULTI_AZ_REPLICAS, SQL.DEFAULT_MULTI_AZ_REPLICAS),
						c);
				break;
			case TableDatastore:
				replicas = 1;
				dataReplicas = getPropertyValue(cp, CloudPlatformPropertyName.DATA_REPLICAS, TableDatastore.DEFAULT_DATA_REPLICAS);
				c = (Compute)getIaaSfromCloudResource(provider, serviceType, serviceName,
						resourceName, replicas, cr);
				p = new TableDatastore(provider, serviceType, serviceName,
						resourceName,
						getPropertyValue(cp, CloudPlatformPropertyName.SSD_OPTIMIZED, TableDatastore.DEFAULT_SSD_OPTIMIZED),
						storage > -1 ? storage : getPropertyValue(cp, CloudPlatformPropertyName.STORAGE, TableDatastore.DEFAULT_STORAGE),
						dataReplicas,
						getPropertyValue(cp, CloudPlatformPropertyName.MULTI_AZ_REPLICAS, TableDatastore.DEFAULT_MULTI_AZ_REPLICAS),
						getPropertyValue(cp, CloudPlatformPropertyName.MAX_ENTRY_SIZE, TableDatastore.DEFAULT_MAX_ENTRY_SIZE),
						c);
				break;
			case BlobDatastore:
				replicas = 1;
				dataReplicas = getPropertyValue(cp, CloudPlatformPropertyName.DATA_REPLICAS, BlobDatastore.DEFAULT_DATA_REPLICAS);
				c = (Compute)getIaaSfromCloudResource(provider, serviceType, serviceName,
						resourceName, replicas, cr);
				p = new BlobDatastore(provider, serviceType, serviceName,
						resourceName,
						getPropertyValue(cp, CloudPlatformPropertyName.SSD_OPTIMIZED, BlobDatastore.DEFAULT_SSD_OPTIMIZED),
						storage > -1 ? storage : getPropertyValue(cp, CloudPlatformPropertyName.STORAGE, BlobDatastore.DEFAULT_STORAGE),
						dataReplicas,
						getPropertyValue(cp, CloudPlatformPropertyName.MULTI_AZ_REPLICAS, BlobDatastore.DEFAULT_MULTI_AZ_REPLICAS),
						getPropertyValue(cp, CloudPlatformPropertyName.MAX_ENTRY_SIZE, BlobDatastore.DEFAULT_MAX_ENTRY_SIZE),
						c);
				break;
			default:
				return null;
			}
			break;
		default:
			logger.debug("Case not considered at the moment.");
			return null;
		}
		
		return p;
	}
	
	private double getPropertyValue(CloudPlatform cp, CloudPlatformPropertyName name, double defaultValue) {
		double res = defaultValue;
		try {
			res = Double.parseDouble(getPropertyValue(cp, name));
		} catch (Exception e) {
			String tmp = getPropertyValue(cp, name);
			if (tmp != null && tmp.equals("inf"))
				res = Double.MAX_VALUE;
		}
		return res;
	}
	
	private int getPropertyValue(CloudPlatform cp, CloudPlatformPropertyName name, int defaultValue) {
		int res = defaultValue;
		try {
			res = Integer.parseInt(getPropertyValue(cp, name));
		} catch (Exception e) {
			String tmp = getPropertyValue(cp, name);
			if (tmp != null && tmp.equals("inf"))
				res = Integer.MAX_VALUE;
		}
		return res;
	}
	
	private boolean getPropertyValue(CloudPlatform cp, CloudPlatformPropertyName name, boolean defaultValue) {
		String res = getPropertyValue(cp, name);
		if (res == null)
			return defaultValue;
		else
			return Boolean.parseBoolean(res);
	}
	
	private String getPropertyValue(CloudPlatform cp, CloudPlatformPropertyName name) {
		if (cp == null || name == null)
			return null;
		
		EList<CloudPlatformProperty> cpps = cp.getProperties();
		
		for (CloudPlatformProperty cpp : cpps)
			if (cpp.getName() == name)
				return cpp.getValue();
		
		return null;
	}
	
	private CloudResource runOn(CloudPlatform cp, CloudResourceType resourceType) {
		if (cp == null || resourceType == null)
			return null;
		
		EList<CloudResource> crs = cp.getRunsOnCloudResource();
		
		for (CloudResource cr : crs) {
			if (cr.getResourceType().equals(resourceType))
				return cr;
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private List<PaaS> getSameServicePlatform(CloudService service, String region) {
		List<PaaS> resources = new ArrayList<>();
		for (CloudPlatform cp : cloudProviders.getProviderDBConnectors()
				.get(service.getProvider()) // provider
				.getPaaSServicesHashMap().get(service.getServiceName()) // service
				.getComposedOf()) {

			PaaS paas = getPaaSfromCloudPlatform(service, cp);
			for (Cost cost : cp.getHasCost()) {
				// if the paas has not been already inserted AND (the region has
				// not been specified OR it has the same region)
				if (!resources.contains(paas)
						&& (region == null || cost.getRegion() == null || cost
						.getRegion().equals(region))) {
					resources.add(paas);
				}
			}
		}
		return resources;
	}
	
}

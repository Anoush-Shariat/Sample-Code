package com.anoush.t1bill.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import static java.util.stream.Collectors.toList;

import com.anoush.t1bill.repositories.ServicesRepository;
import com.anoush.t1bill.T1BillService;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author Anoush
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/services")
public class ServicesController {

	private ServicesRepository serviceRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ServicesController(ServicesRepository servicesRepository) {
			this.serviceRepository = servicesRepository;
			
	}
	
	/*
	 * services that are on offer to be assigned to a customer
	 * @returns List<T1BillService> a list of services available to customers and subscribers. 
	 */
	@RequestMapping(value = "/{offered}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<T1BillService> fetchOfferedServices() {	
	    logger.debug("fetchOfferedServices() has been called.");
		List<T1BillService> offeredServices = serviceRepository.fetchOfferedServices();
		return offeredServices;
	}
	
	/*
	 *@return List<T1BillService> list of services that have to be provisioned i.e. status = "P"
	 */
	@RequestMapping(value = "/provision", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<T1BillService> fetchProvisionableServices() {
		
		logger.debug("fetchProvisionableServices has been called");
	    return serviceRepository.fetchProvisionableServices();
	}
	
	/*
	 * delete services from the database, this should be secured to the Admin Roles only
	 * @return List<T1BillService> list of remaining services
	 * @param  List<T1BillService> list of services to be removed
	 */
	@RequestMapping(value = "/delete", 
			        method = RequestMethod.POST, 
			        produces = "application/json",
			        consumes = "application/json")
	public @ResponseBody List<T1BillService> deleteServices(@RequestBody List<T1BillService> deleteUs) {
		
		logger.debug(String.format("deleteServices() with %d services \"%s\"\n", 
				                                                 deleteUs.size(), deleteUs.toString()));
		
		List<T1BillService> list = serviceRepository.deleteServices(deleteUs);
		
		logger.debug(String.format("After deleteServices() services are: - \"%s\"\n", list.toString()));
	    
		return list;
	}
	
	/*
	 * Adds services to the database, this should be secured to the Admin Roles only
	 * @return List<T1BillService> list of remaining services
	 * @param  List<T1BillService> list of services to be added
	 */
	@RequestMapping(value = "/add", 
			        method = RequestMethod.POST, 
			        produces = "application/json",
			        consumes = "application/json")
	public @ResponseBody List<T1BillService> addServices(@RequestBody List<T1BillService> addUs) {
		
		logger.debug(String.format("addServices() with %d services: \"%s\"\n", addUs.size(),addUs.toString()));
		
		List<T1BillService> list = serviceRepository.addServices(addUs);
		
		logger.debug(String.format("After addServices() services are: - \"%s\"\n", list.toString()));
		
		return list;
	}
	
	
	/*
	 * Suspend services in the database, this should be secured to the Admin Roles only
	 * @return List<T1BillService> list of services with their new status
	 * @param  List<T1BillService> list of services to be suspended
	 */
	@RequestMapping(value = "/suspend", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<T1BillService> suspendServices(@RequestBody List<T1BillService> suspendUs) {
		
		logger.debug(String.format("suspendServices() %d services \"%s\" \n", suspendUs.size(), suspendUs.toString()));
		
		List<T1BillService> list = serviceRepository.suspendServices(suspendUs);
		
		logger.debug(String.format("After suspendService() services are: - \"%s\" \n", list.toString()));
		
		return list;
	}
	
	/*
	 * Update status of services in the database
	 * @return List<T1BillService> list of services with their new status
	 * @param  List<T1BillService> list of services to be suspended
	 * @param String status 
	 */
	@RequestMapping(value = "/update/{status}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<T1BillService> updateServices(@PathVariable(value = "status") String status, 
			                                                @RequestBody List<T1BillService> updateUs) {
		
		logger.debug(String.format("update() %d services to status: \"%s\"\n", updateUs.size(),status));
				                                  		
		List<T1BillService> list = serviceRepository.updateServices(updateUs, status);
		
		logger.debug(String.format("After update() services are - \"%s\"\n", list.toString()));
		
		return list;
	}
	
	// Customer related calls
	
	/* 
	 * return services to which a customer is subscribed. 
	 * @param long customerID
	 * @return List<T1BillService>
	 */
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces ="application/json")
	public @ResponseBody List<T1BillService> fetchCustomerServices(@PathVariable(value = "id") long customerID) {
	    
		logger.debug(String.format("fetchCustomerServices(%d) was called", customerID));
		
        List<T1BillService> list = serviceRepository.fetchCustomerServices(customerID);

        logger.debug(String.format("Customer %d has %d services: - \"%s\"\n", 
        		                                                   customerID, list.size(), list.toString()));
        return list;
	}
	
	/* 
	 * return services to which a customer is subscribed after deletion. 
	 * @param long customerID
	 * @return List<T1BillService>
	 */
	@RequestMapping(value = "/customer/delete/{id}", method = RequestMethod.POST, produces ="application/json")
	public @ResponseBody List<T1BillService> deleteCustomerServices(@PathVariable(value = "id") long customerID,
			                                                        @RequestBody List<T1BillService> deleteUs) {
	     logger.debug(String.format("deleteCustomerServices(%d) with %d services: \"%s\"\n",
	    		                                             customerID, deleteUs.size(), deleteUs.toString()));
	                                                                                            
	    List <T1BillService> list = serviceRepository.deleteCustomerServices(deleteUs, customerID);
	    
	    logger.debug(String.format("Customer %d now has %d services(s): - \"%s\"\n", 
	    															customerID, list.size(), list.toString()));
	    return list;
	}
	
	/* 
	 * return services to which a customer is subscribed after deletion. 
	 * @param long customerID
	 * @return List<T1BillService>
	 * 
	 */
	@CrossOrigin
	@RequestMapping(value = "/customer/add/{id}", method = RequestMethod.POST, produces ="application/json",
			                                                                   consumes ="application/json")

	public @ResponseBody List<T1BillService> addCustomerServices(@PathVariable(value = "id") long customerID,
			                                                     @RequestBody List<T1BillService> addUs) {
		
	    logger.debug(String.format("addCustomerServices(%d) is about to add %d service(s): \"%s\"\n",
	    		                                                   customerID, addUs.size(), addUs.toString()));
	                                                                                           
		List<T1BillService> list = serviceRepository.addCustomerServices(addUs, customerID);
		
		logger.debug(String.format("Customer %d now has %d services(s): - \"%s\"\n",
				                                                    customerID, list.size(), list.toString()));;
		return list;
	}
	
	/* 
	 * Suspend a list of services belonging to a given customer
	 * @param List<T1BillService> 
	 * @param long customerID
	 * @return List<T1BillService>
	 */
	@RequestMapping(value = "/customer/suspend/{id}", method = RequestMethod.POST, produces ="application/json")
	public @ResponseBody List<T1BillService> suspendCustomerServices(@PathVariable(value = "id") long customerID,
			                                                         @RequestBody List<T1BillService> suspendUs) {
	    
		System.out.println(String.format("suspendCustomerServices(%d) with services: \"%s\"",
				                                                           customerID, suspendUs.toString()));
	                                                                                            
	    return serviceRepository.suspendCustomerServices(suspendUs, customerID);
	}
	
	/* 
	 * Update status of a list of services belonging to a given customer, i.e. change from Active to
	 * Deactive for example
	 * e.g. /customer/update/1000005/{new_status}
	 * @param List<T1BillService> 
	 * @param long customerID
	 * @param String status
	 * @return List<T1BillService> updated services belonging to the customer
	 * 
	 */
	@RequestMapping(value = "/customer/update/{id}/{status}", method = RequestMethod.POST, produces ="application/json")
	public @ResponseBody List<T1BillService> updateCustomerServices(@PathVariable(value = "id") long customerID,
			                                                        @PathVariable(value = "status") String status,
			                                                        @RequestBody List<T1BillService> updateUs) {
	    
		logger.debug(String.format("updateCustomerServices(%d) with %d service(s) to status \"%s\": \"%s\"\n",
				                                   customerID, updateUs.size(), status, updateUs.toString()));
	    
		List<T1BillService> list = serviceRepository.updateCustomerServices(updateUs, status, customerID);
		
		logger.debug(String.format("After updateCustomerServices(): %d customer services: \"%s\"\n",
				                                                                  customerID, list.toString()));
		return list;
	}
	
	//For preliminary testing should not be taken seriously
	private List<T1BillService> createServices() {
		/*
		 * private String code;
		   private String description;  //
	       private String status;       // "A", "D", "S"
	       private String cost;         // 
 	       private String type;         // P for typeable vs. Recurring
	       private String startDate;
	       private String endDate;
	
		 */
		T1BillService t1s = new T1BillService("SRV1000", "Modem", "A", "10", "P", "01-01-2016", "01-01-2017");
		T1BillService t2s = new T1BillService("SRV2000", "Voice", "A", "10", "P", "01-01-2016", "01-01-2017");
		T1BillService t3s = new T1BillService("SRV3000", "Text", "A", "100", "R", "01-01-2016", "01-01-2017");
		T1BillService t4s = new T1BillService("SRV4000", "Data", "A", "9.99", "R", "01-01-2016", "01-01-2017");
		List<T1BillService> services = new ArrayList<>();
		services.add(t4s);
		services.add(t3s);
		services.add(t2s);
		services.add(t1s);
		return services;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

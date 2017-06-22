package com.anoush.t1bill.repositories;

import java.util.List;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

import com.anoush.t1bill.T1BillService;

public interface ServicesRepository {

	List<T1BillService> fetchOfferedServices();

	List<T1BillService> fetchCustomerServices(long subscriberID);

	List<T1BillService> fetchProvisionableServices();

	List<T1BillService> deleteServices(List<T1BillService> deleteUs);

	List<T1BillService> deleteCustomerServices(List<T1BillService> deleteUs, long customerID);
	
	List<T1BillService> addServices(List<T1BillService> addUs);
	
	List<T1BillService> addCustomerServices(List<T1BillService> addUs, long customerID);
	
	List<T1BillService> suspendServices(List<T1BillService> suspendUs);

	List<T1BillService> suspendCustomerServices(List<T1BillService> suspendUs, long customerID);

	List<T1BillService> updateServices(List<T1BillService> updateUs, String status);
	
	List<T1BillService> updateCustomerServices(List<T1BillService> updateUs, String status, long customerID);
}
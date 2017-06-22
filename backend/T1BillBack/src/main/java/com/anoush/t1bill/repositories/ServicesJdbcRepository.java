package com.anoush.t1bill.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.anoush.t1bill.T1BillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServicesJdbcRepository implements ServicesRepository {

	private final String DELETE_CUSTOMER_SERVICE = 
			"delete from customer_services CS where CS.customer_id = ? and CS.service_id = " +
	                                                         "(select service_id from services where code = ?)";
	
	private final String DELETE_SERVICES = "delete from services where code = ?";
	
	private final String ADD_SERVICES = 
			"insert into services (code, status, type, cost, description, start_date, end_date) " +
	                              "values (?, ?, ?, ?, ?, ?, ?)";
	
	private final String ADD_CUSTOMER_SERVICES =
			"insert into customer_services (customer_id, service_id, status) " +
	            "values(?, (select service_id from services where code = ?), 'T') ";
	          
	
	private final String FETCH_SERVICES = 
			"select code, status, type, cost, description, start_date, end_date from services";
	
	private final String FETCH_CUSTOMER_SERVICES =
			"select S.code, CS.status, S.type, S.cost, S.description, S.start_date, S.end_date from " +
			   "services S inner join customer_services CS ON S.service_id = CS.service_id " +
					"and CS.customer_id = ?";

	private final String UPDATE_SERVICES =
			"update services set status = ? where code = ?";
	
	private final String UPDATE_CUSTOMER_SERVICES =
			"update customer_services set status = ? where customer_id = ? and service_id =" +
	            " (select service_id from services where code = ?)";
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ServicesJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * @return List<T1BillService> contains all services created and available to customers
	 */
	
	@Override
	public List<T1BillService> fetchOfferedServices() {
		List<T1BillService> list = new ArrayList<>();
		
		list = jdbcTemplate.query(FETCH_SERVICES, (resultSet, rowNum) ->
                    								new T1BillService(
                    										resultSet.getString("code"),
                    										resultSet.getString("status"),
                    										resultSet.getString("type"),
                    										resultSet.getString("cost"),
                    										resultSet.getString("description"),
                    										resultSet.getString("start_date"),
                    										resultSet.getString("end_date"))
                    			);

		return list; 	
	}

	/*
	 * @return List<T1BillService> contains all services assigned to a given customer
	 * This early version does not consider start_date and end_date of service nor it
	 * does consider the status of service
	 * 
	 * @param long customerID
	 * @return List<T1BillService>
	 * 
	 */
	@Override
	public List<T1BillService> fetchCustomerServices(long subscriberID) {
		
		List<T1BillService> list = new ArrayList<>();
		
		Object[] params = new Object[] { subscriberID };
		
		list = jdbcTemplate.query(FETCH_CUSTOMER_SERVICES, params, (resultSet, rowNum) ->
													new T1BillService(resultSet.getString("code"),
																	  resultSet.getString("status"),
																	  resultSet.getString("type"),
																	  resultSet.getString("cost"),
																	  resultSet.getString("description"),
																	  resultSet.getString("start_date"),
																	  resultSet.getString("end_date"))
								);
	
		return list; 
	}

	@Override
	public List<T1BillService> fetchProvisionableServices() {
		List<T1BillService> allServices = fetchOfferedServices();
		List<T1BillService> provServices = new ArrayList<>();
		//TODO convert this to Java 8 stream
		for (T1BillService srvc : allServices) {
			if (srvc.getType().equals("P")) {
				provServices.add(srvc);
			}
		}
		return provServices;
	}

	@Override
	public List<T1BillService> deleteServices(List<T1BillService> deleteUs) {
		if (deleteUs.isEmpty()) {
			throw new RuntimeException("empty set was not deleted from services ...");
		}
		
		List<Object[]> serviceCodes = new ArrayList<>();
		for (T1BillService service : deleteUs) {
			Object[] objects = new Object[1];
			objects[0] = service.getCode();
			serviceCodes.add(objects);
		}
		
		jdbcTemplate.batchUpdate(DELETE_SERVICES, serviceCodes);
		
		return fetchOfferedServices();
	}

	
	@Override
	public List<T1BillService> deleteCustomerServices(List<T1BillService> deleteUs, long customerID) {
		if (deleteUs.isEmpty()) {
			throw new RuntimeException("empty set was not deleted from services ...");
		}
		
		List<Object[]> idAndCode = new ArrayList<>();
		for (T1BillService service : deleteUs) {
			Object[] objects = new Object[2];
			objects[0] = customerID;
			objects[1] = service.getCode();
			idAndCode.add(objects);
		}
		
		jdbcTemplate.batchUpdate(DELETE_CUSTOMER_SERVICE, idAndCode);
		
		return fetchCustomerServices(customerID);
	}

	@Override
	public List<T1BillService> addServices(List<T1BillService> addUs) {
		
		if (addUs.size() == 0) {
			return fetchOfferedServices();
		}
		
		List<Object[]> params = new ArrayList<>();
		for (T1BillService service : addUs) {
			Object[] objects = new Object[] {
					service.getCode(),
					service.getStatus(),
					service.getType(),
					service.getPrice(),
					service.getDescription(),
					service.getStartDate(),
					service.getEndDate()
			};
			params.add(objects);
		}
		
		jdbcTemplate.batchUpdate(ADD_SERVICES, params);
		
		return fetchOfferedServices();
	}

	@Override
	public List<T1BillService> addCustomerServices(List<T1BillService> addUs, long customerID) {
		if (addUs.size() == 0) {
		    return Collections.emptyList();
		} 
		
		List<Object[]> params = new ArrayList<>();
		for (T1BillService service : addUs) {
			Object[] objects = new Object[] { customerID, service.getCode() };
			params.add(objects);
		}
		
		jdbcTemplate.batchUpdate(ADD_CUSTOMER_SERVICES, params);
		
		return fetchCustomerServices(customerID);
	}

	@Override
	public List<T1BillService> suspendServices(List<T1BillService> suspendUs) {
		return updateServices(suspendUs, "S");
	}

	@Override
	public List<T1BillService> updateServices(List<T1BillService> updateUs, String status) {
		
		if (updateUs.size() == 0) {
		    return Collections.emptyList();
		}
		
		List<Object[]> params = new ArrayList<>();
		
		long sTime = System.nanoTime();
		
		for (T1BillService service : updateUs) {
			Object[] objects = new Object[] { status, service.getCode() };
			params.add(objects);
		}
		long eTime = System.nanoTime();
		
		System.out.println("setting up params took: " + (eTime - sTime)/1.0e6 + " milliseconds.");
		
		sTime = System.nanoTime();
		jdbcTemplate.batchUpdate(UPDATE_SERVICES, params);
		eTime = System.nanoTime();
		
		System.out.println("batchUpdate() took: " + (eTime - sTime)/1.0e6 + " milliseconds.");
		
		return fetchOfferedServices();
	}

	/*
	 * (non-Javadoc)
	 * @see com.anoush.t1bill.repositories.ServicesRepository#updateCustomerServices(java.util.List, java.lang.String, long)
	 */
	@Override
	public List<T1BillService> updateCustomerServices(List<T1BillService> updateUs, String status, long customerID) {
		if (updateUs.size() == 0) {
		    return Collections.emptyList();
		}
		
		if (status.length() > 1) {
			return Collections.emptyList();
		}
		
		List<Object[]> params = new ArrayList<>();
		
		long sTime = System.nanoTime();
		
		for (T1BillService service : updateUs) {
			Object[] objects = new Object[] { status, customerID, service.getCode() };
			params.add(objects);
		}
		long eTime = System.nanoTime();
		
		System.out.println("setting up params took: " + (eTime - sTime)/1.0e6 + " milliseconds.");
		
		sTime = System.nanoTime();
		jdbcTemplate.batchUpdate(UPDATE_CUSTOMER_SERVICES, params);
		eTime = System.nanoTime();
		
		System.out.println("batchUpdate() took: " + (eTime - sTime)/1.0e6 + " milliseconds.");
		
		return fetchCustomerServices(customerID);
	}

	@Override
	public List<T1BillService> suspendCustomerServices(List<T1BillService> suspendUs, long customerID) {
	    return this.updateCustomerServices(suspendUs, "S", customerID);	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

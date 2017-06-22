package com.anoush.t1bill.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anoush.t1bill.Customer;
import com.anoush.t1bill.repositories.CustomerRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomersController {

	private CustomerRepository customerRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CustomersController(CustomerRepository hibernate5CustomerRepository) {
		this.customerRepository = hibernate5CustomerRepository;
	}
	
	
	@RequestMapping(value = "/save", 
			        method = RequestMethod.POST, 
			        consumes = " application/json",
			        produces ="application/json")
	public @ResponseBody Customer saveCustomer(@RequestBody Customer customer) {
	    
		logger.debug(String.format("saveCustomer() has been called for %s, %s\n", 
				                                         customer.getFirstName(), customer.getLastName()));
	    
		Customer newcust = customerRepository.save(customer);
		
		logger.debug(String.format("After saveCustomer: \"%s\"\n", newcust.toString()));
		
		return newcust;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces ="application/json")
	public @ResponseBody Customer findOneById(@PathVariable(value = "id") long customerID) {
	    
		logger.debug(String.format("findOneById(%d) has been called\n", customerID));
	    
		Customer customer = customerRepository.findOne(customerID);
		
		logger.debug(String.format("After findOneById: \"%s\"\n", customer.toString()));
		
		return customer;
	}
	
	@RequestMapping(value = "/{fname}/{lname}", method = RequestMethod.GET, produces ="application/json")
	public @ResponseBody List<Customer> getCustomerByName(@PathVariable(value = "fname") String fname,
			                                              @PathVariable(value = "lname") String lname) {
			                                                        
	    
		logger.debug(String.format("getCustomerByName(%s, %s) has been called.\n", fname, lname));
				                                                  	    
		List<Customer> list = customerRepository.findByUsername(fname, lname);
		
		logger.debug(String.format("Found Customer: \"%s\"\n", list.toString()));
		
		return list;
	}
	
	/*
	 * {address:.+} is used so .com of emails would not be truncated. 
	 * See https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
	 * for other solutions.
	 * 
	 */
	@RequestMapping(value = "/email/{address:.+}", method = RequestMethod.GET, produces ="application/json")
	public @ResponseBody List<Customer> getCustomerByEmailAddress(
			                                              @PathVariable(value = "address") String address) {
			                                                                                                    
	    
		logger.debug(String.format("getCustomerByEmailAddress(%s) has been called.\n", address));
				                                                  	    
		List<Customer> list = customerRepository.findByEmailAddress(address);
		
		logger.debug(String.format("Found Customer: \"%s\"\n", list.toString()));
		
		return list;
	}
	
	@RequestMapping(value = "/phone/{number}", method = RequestMethod.GET, produces ="application/json")
	public @ResponseBody List<Customer> getCustomerByPhone(
			                                            @PathVariable(value = "number") String number) {
			                                                                                                    
	    logger.debug(String.format("getCustomerByPhone(%s) has been called.\n", number));
				                                                  	    
		List<Customer> list = customerRepository.findByPhone(number);
		
		logger.debug(String.format("Found Customer: \"%s\"\n", list.toString()));
		
		return list;
	}
	//More commands later
}

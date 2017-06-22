/**
 * 
 */
package com.anoush.t1bill.repositories;

import java.io.Serializable;
import java.util.List;
import com.anoush.t1bill.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author Anoush
 *
 */
//@Transactional(readOnly = true, rollbackFor = {java.lang.Exception.class})
@Repository
public class Hibernate5CustomerRepository implements CustomerRepository {
	
	private SessionFactory sessionFactory;
	
	
    @Autowired
	public Hibernate5CustomerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        //return sessionFactory.getCurrentSession();
    	return sessionFactory.openSession();
    }

	private Criteria customerCriteria() {
		return currentSession().createCriteria(Customer.class);
	}
	
    public Customer save(Customer customer) {
    	
		Session session = sessionFactory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.save(customer);
    	session.flush();
    	//session.refresh(customer);
    	tx.commit();
    	session.refresh(customer);
    	session.close();
		return customer;
	}
	
	public boolean delete(long id) {
		//TODO: implement this later with cascade of services invoices ... 
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.anoush.t1bill.repositories.CustomerRepository#findOne(long)
	 */
	public Customer findOne(long id) {
		return currentSession().get(Customer.class, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.anoush.t1bill.repositories.CustomerRepository#findByUsername(java.lang.String, java.lang.String)
	 */
	public List<Customer> findByUsername(String firstName, String lastName) {
		@SuppressWarnings("unchecked")
		List<Customer> list =  customerCriteria()
		                       .add(Restrictions.and(
		                                             Restrictions.eq("firstName", firstName),
		                	                         Restrictions.eq("lastName", lastName)
		                	                         ))
		                       .list();
		
		return list;                	   
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.anoush.t1bill.repositories.CustomerRepository#findByEmailAddress(java.lang.String)
	 */
	public List<Customer> findByEmailAddress(String email_address) {
		@SuppressWarnings("unchecked")
		List<Customer> list = customerCriteria()
				              .add(Restrictions.eq("emailAddress", email_address))
				              .list();
		return list;
	}
	
	public List<Customer> findByPhone(String phone_number) {
		@SuppressWarnings("unchecked")
		List<Customer> list = customerCriteria()
				              .add(Restrictions.eq("phoneNumber", phone_number))
				              .list();
		return list;
	}
	
	public List<Customer> findAll() {
		@SuppressWarnings("unchecked")
		List<Customer> list = customerCriteria().list();
		return list;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

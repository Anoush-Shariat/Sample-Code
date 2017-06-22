package com.anoush.t1bill;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

@Entity
@Table(name="customers")
public class Customer {
	@Id
	@GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
	@Column(name="customer_id", insertable=false, updatable=false)
	private Long id;
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;
	@Column(name="LAST_NAME", nullable=false)
	private String lastName;
	@Column(name="MIDDLE_NAME")
	private String middleName;
	@Column(name="COMPANY_NAME")
	private String companyName;
	@Column(name="EMAIL", unique=true, nullable=false)
	private String emailAddress;
	@Column(name="SMS_ADDRESS")
	private String smsAddress;
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	@Column(name="MOBILE_NUMBER")
	private String mobileNumber;

	/* Hibernate needs this */
	public Customer() {
		
	}
	
	public Customer(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = email;
	}
	
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param middleName
	 * @param companyName
	 * @param email
	 * @param smsAddress
	 * @param phoneNumber
	 * @param mobileNumber
	 */
	public Customer(Long id, String firstName, String lastName, String middleName, String companyName, String email,
			String smsAddress, String phoneNumber, String mobileNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.companyName = companyName;
		this.emailAddress = email;
		this.smsAddress = smsAddress;
		this.phoneNumber = phoneNumber;
		this.mobileNumber = mobileNumber;
	}

	/*
	 * //http://www.oracle.com/technetwork/middleware/ias/id-generation-083058.html^M
	 */
	
	/**
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return emailAddress;
	}
	/**
	 * @return the smsAddress
	 */
	public String getSmsAddress() {
		return smsAddress;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param id the id to set
	 */
	/*
	public void setId(long id) {
		this.id = id;
	}
	*/
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @param String the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @param String the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @param String the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @param String the email to set
	 */
	public void setEmail(String email) {
		this.emailAddress = email;
	}
	/**
	 * @param String the smsAddress to set
	 */
	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}
	/**
	 * @param String the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @param String the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	@Override
	public String toString() {
		return "Id: " + id.toString() + " Name: " + firstName + ", " + lastName + " Email: " + emailAddress;
	}
	
	// TODO: need to add all of these customers related attributes
	//Set<Address> addresses
	//Set<Invoices> invoices;
	//
	
}

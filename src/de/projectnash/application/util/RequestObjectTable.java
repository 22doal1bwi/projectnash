package de.projectnash.application.util;

import java.util.Date;

import de.projectnash.entities.Request;

/**
 * This class provides a container that concatenates incoming data in order to build a clean JSON-Object and send it to the frontend.
 * @author Silvio D'Alessandro
 *
 */
public class RequestObjectTable {
	
	private String firstName;
	
	private String lastName;
	
	private String department;
	
	private int personalId;
	
	private String emailAddress;
	
	private Date requestCreationDate;
	
	private RequestStatus status;
	
	/**
	 * Only used to send {@link Request} data to the frontend.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param department
	 * @param personalId
	 * @param emailAddress
	 * @param requestCreationDate
	 * @param status
	 */
	public RequestObjectTable (Date requestCreationDate, String firstName, String lastName, String department, int personalId, String emailAddress, RequestStatus status){
		this.requestCreationDate = requestCreationDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.personalId = personalId;
		this.emailAddress = emailAddress;		
		this.status = status;
	
	}

	@Override
	public String toString() {
		return "First name: " + this.firstName + 
			   " Last name: " + this.lastName + 
			   " Department: " + this.department + 
			   " Personal-ID: " + this.personalId + 
			   " Email-Address: " + this.emailAddress + 
			   " Request-Creation-Date: " + this.requestCreationDate + 
			   " Request-Status: " + this.status;
	}	
}

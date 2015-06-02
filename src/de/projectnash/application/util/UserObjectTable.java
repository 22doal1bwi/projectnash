package de.projectnash.application.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserObjectTable {

private String firstName;
	
	private String lastName;
	
	private String department;
	
	private int personalId;
	
	private String emailAddress;
	
	private String certificateDate;
	
	private CertificateStatus status;
	
	/**
	 * Only used to send {@link User} data to the frontend.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param department
	 * @param personalId
	 * @param emailAddress
	 * @param requestCreationDate
	 * @param status
	 */
	public UserObjectTable (Date certificateDate, String firstName, String lastName, String department, int personalId, String emailAddress, CertificateStatus status) {
		
		DateFormat formatter = new SimpleDateFormat(
				"dd.MM.yyyy, HH:mm", Locale.GERMANY);
		
		this.certificateDate = formatter.format(certificateDate) + " Uhr";
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
			   " Certificate-Creation-Date: " + this.certificateDate + 
			   " Certificate-Status: " + this.status;
	}	
	
}

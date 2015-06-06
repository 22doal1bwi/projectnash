package de.projectnash.application.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class provides a container that concatenates incoming data in order to build a clean JSON-Object and send it to the frontend.
 * 
 * @author Silvio D'Alessandro
 *
 */
public class UserObjectTable {

private String firstName;
	
	private String lastName;
	
	private String department;
	
	private int personalId;
	
	private String emailAddress;
	
	private String certificateDate;
	
	private CertificateStatus status;
	
	private boolean hasSession;
	
	/**
	 * Only used to send {@link User} data to the frontend. Used when a {@link User} has a certificate.
	 * 
	 * @param certificateDate
	 * @param firstName
	 * @param lastName
	 * @param department
	 * @param personalId
	 * @param emailAddress
	 * @param status
	 * @param hasSession
	 */
	public UserObjectTable (Date certificateDate, String firstName, String lastName, String department, int personalId, String emailAddress, CertificateStatus status, boolean hasSession) {
		
		DateFormat formatter = new SimpleDateFormat(
				"dd.MM.yyyy, HH:mm", Locale.GERMANY);
		
		this.certificateDate = formatter.format(certificateDate) + " Uhr";
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.personalId = personalId;
		this.emailAddress = emailAddress;		
		this.status = status;
		this.hasSession = hasSession;
	
	}
	
	/**
	 * Only used to send {@link User} data to the frontend. Used when a {@link User} has no certificate.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param department
	 * @param personalId
	 * @param emailAddress
	 * @param hasSession
	 */
	public UserObjectTable (String firstName, String lastName, String department, int personalId, String emailAddress, boolean hasSession) {
			
		this.certificateDate = "-";
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.personalId = personalId;
		this.emailAddress = emailAddress;		
		this.status = CertificateStatus.NONE;
		this.hasSession = hasSession;	
		
	}

	@Override
	public String toString() {
		return "First name: " + this.firstName + 
			   " Last name: " + this.lastName + 
			   " Department: " + this.department + 
			   " Personal-ID: " + this.personalId + 
			   " Email-Address: " + this.emailAddress + 
			   " Certificate-Creation-Date: " + this.certificateDate + 
			   " Certificate-Status: " + this.status +
			   " Open Session" + this.hasSession;	
	}	
	
}
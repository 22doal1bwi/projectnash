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
	 * @param certificateExpirationDate The {@link Date} that represents expiration date of the {@link Certificate} 
	 * @param firstName The {@link String} that represents the first name of the {@link User}.
	 * @param lastName The {@link String} that represents the last name of the {@link User}.
	 * @param department The {@link String} that represents the department of the {@link User}.
	 * @param personalId The {@link Integer} that represents the personal id of the {@link User}.
	 * @param emailAddress The {@link String} that represents the e-mail address of the {@link User}.
	 * @param status The {@link CertificateStatus} that represents the status of the {@link Certificate}.
	 * @param hasSession The {@link Boolean} that describes if the {@link User} has a {@link Session}.
	 */
	public UserObjectTable (Date certificateExpirationDate, String firstName, String lastName, String department, int personalId, String emailAddress, CertificateStatus status, boolean hasSession) {
		
		DateFormat formatter = new SimpleDateFormat(
				"dd.MM.yyyy, HH:mm", Locale.GERMANY);
		
		this.certificateDate = formatter.format(certificateExpirationDate) + " Uhr";
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
	 * @param firstName The {@link String} that represents the first name of the {@link User}.
	 * @param lastName The {@link String} that represents the last name of the {@link User}.
	 * @param department The {@link String} that represents the department of the {@link User}.
	 * @param personalId The {@link Integer} that represents the personal id of the {@link User}.
	 * @param emailAddress The {@link String} that represents the e-mail address of the {@link User}.
	 * @param hasSession The {@link Boolean} that describes if the {@link User} has a {@link Session}.
	 */
	public UserObjectTable (String firstName, String lastName, String department, int personalId, String emailAddress, boolean hasSession) {
			
		this.certificateDate = "- kein Zertifikat -";
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
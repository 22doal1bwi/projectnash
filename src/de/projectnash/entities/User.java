package de.projectnash.entities;

import de.projectnash.application.util.Constants;

/**
 * This class provides a realistic {@link User} with all its attributes.
 * 
 * @author Silvio D'Alessandro, Marius Boepple
 *
 */
public class User {

	private int personalId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String organzationalUnit;
	private String password;
	private Certificate certificate;

	/**
	 * The constructor for a {@link User} with all necessary attributes.
	 */
	public User(int personalId, String firstName, String lastName, String organzationalUnit, 
			String emailAddress, String password) {
		super();
		this.personalId = personalId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.organzationalUnit = organzationalUnit;
		this.password = password;
	}

	/**
	 * Method which returns the common name
	 * @return first name + last name
	 */
	public String getCommonName() {
		return (firstName + " " + lastName);
	}
	

	
	//GETTERS AND SETTERS
	
	public int getPersonalId() {
		return personalId;
	}

	public void setPersonalId(int personalId) {
		this.personalId = personalId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getOrganzationalUnit() {
		return organzationalUnit;
	}

	public void setOrganzationalUnit(String organzationalUnit) {
		this.organzationalUnit = organzationalUnit;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
}

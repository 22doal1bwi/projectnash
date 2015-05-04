package de.projectnash.entities;

import java.util.ArrayList;

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
	private String countryName;
	private String state;
	private String localityName;
	private String organizationName = Constants.ORG_NAME;
	private String organzationalUnit;
	private String password;
	private ArrayList<Certificate> certificates;

	/**
	 * The constructor for a {@link User} with all necessary attributes.
	 */
	public User(int personalId, String firstName, String lastName,
			String emailAddress, String countryName, String state,
			String localityName, String organzationalUnit, String password) {
		super();
		this.personalId = personalId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.countryName = countryName;
		this.state = state;
		this.localityName = localityName;
		this.organzationalUnit = organzationalUnit;
		this.password = password;
		this.certificates = new ArrayList<Certificate>();
	}	

	/**
	 * Method which returns the common name
	 * @return first name + last name
	 */
	public String getCommonName() {
		return (firstName + " " + lastName);
	}
	
	/**
	 * Method which adds a certificate to the user's list of certificates
	 * @param certificate
	 */
	public void addCertificate(Certificate certificate){
		this.certificates.add(certificate);
	}
	
	public ArrayList<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(ArrayList<Certificate> certificates) {
		this.certificates = certificates;
	}

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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocalityName() {
		return localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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

}

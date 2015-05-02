package de.projectnash.entities;

import java.io.File;

/**
 * This class provides a realistic {@link Certificate} with all its attributes.
 * 
 * @author Marius Boepple
 *
 */
public class Certificate {

	private int certificateId;
	private File certificateFile;
	private String countryName;
	private String state;
	private String localityName;
	private String organizationName;
	private String organizationalUnit;
	private String commonName;
	private String emailAddress;
	private String initializationDate;
	private String expirationDate;

	public Certificate(File certificateFile, String countryName, String state,
			String localityName, String organizationName,
			String organizationalUnit, String commonName, String emailAddress,
			String initializationDate, String expirationDate) {
		super();
		this.certificateFile = certificateFile;
		this.countryName = countryName;
		this.state = state;
		this.localityName = localityName;
		this.organizationName = organizationName;
		this.organizationalUnit = organizationalUnit;
		this.commonName = commonName;
		this.emailAddress = emailAddress;
		this.initializationDate = initializationDate;
		this.expirationDate = expirationDate;

		// get latest certificateId from DB
		this.certificateId = 01;
	}

	public int getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}

	public File getCertificateFile() {
		return certificateFile;
	}

	public void setCertificateFile(File certificateFile) {
		this.certificateFile = certificateFile;
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

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getInitializationDate() {
		return initializationDate;
	}

	public void setInitializationDate(String initializationDate) {
		this.initializationDate = initializationDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

}

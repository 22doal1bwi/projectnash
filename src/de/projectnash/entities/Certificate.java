package de.projectnash.entities;

import java.util.Date;

/**
 * This class provides a realistic {@link Certificate} with all its attributes.
 * 
 * @author Marius Boepple, Jonathan Schlotz
 *
 */
public class Certificate {

	private int certificateId;
	private byte[] certificateFile;
	private String countryName;
	private String state;
	private String localityName;
	private String organizationName;
	private String organizationalUnit;
	private String commonName;
	private String emailAddress;
	private Date initializationDate;
	private Date expirationDate;
	
	public Certificate(int certificateId, byte[] certificateFile,
			String countryName, String state, String localityName,
			String organizationName, String organizationalUnit,
			String commonName, String emailAddress, Date initializationDate,
			Date expirationDate) {
		super();
		this.certificateId = certificateId;
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
	}

	@Override
	public String toString() {
		return "Certificate [certificateId=" + certificateId + ", certificateFile="
				+ certificateFile + ", countryName=" + countryName
				+ ", state=" + state + ", localityName=" + localityName
				+ ", organizationName=" + organizationName
				+ ", organizationalUnit=" + organizationalUnit
				+ ", commonName=" + commonName + ", emailAddress="
				+ emailAddress + ", initializationDate=" + initializationDate.toString()
				+ ", expirationDate=" + expirationDate.toString() + "]";
	}

	public int getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}

	public byte[] getCertificateFile() {
		return certificateFile;
	}

	public void setCertificateFile(byte[] certificateFile) {
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

	public byte[] getCertificate() {
		return certificateFile;
	}

	public void setCertificate(byte[] certificate) {
		this.certificateFile = certificate;
	}

	public Date getInitializationDate() {
		return initializationDate;
	}

	public void setInitializationDate(Date initializationDate) {
		this.initializationDate = initializationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}

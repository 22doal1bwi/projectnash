package de.projectnash.entities;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.projectnash.application.util.CertificateUtility;


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
	
	public Certificate(byte[] certificateFile) throws IOException, ParseException{
		
		this.certificateFile = certificateFile;
		this.countryName = CertificateUtility.getCRTdata(certificateFile, "-subject").split("/")[1].split("=")[1];
		this.state = CertificateUtility.getCRTdata(certificateFile, "-subject").split("/")[2].split("=")[1];
		this.localityName = CertificateUtility.getCRTdata(certificateFile, "-subject").split("/")[3].split("=")[1];
		this.organizationName = CertificateUtility.getCRTdata(certificateFile, "-subject").split("/")[4].split("=")[1];
		this.organizationalUnit = CertificateUtility.getCRTdata(certificateFile, "-subject").split("/")[5].split("=")[1];
		this.commonName = CertificateUtility.getCRTdata(certificateFile, "-subject").split("/")[6].split("=")[1];
		//this.emailAddress = CertificateUtility.getCRTdata(certificate, "-subject").split("/")[7].split("=")[1];
		
		//Format date String to Date() object
		DateFormat formatter = new SimpleDateFormat("MMM dd HH:mm:ss yyyy z", Locale.ENGLISH);	
		
		this.initializationDate = (Date)formatter.parse(CertificateUtility
				.getCRTdata(certificateFile, "-dates")
				.split("notBefore=")[1]
						.split("notAfter=")[0]);
		
		this.expirationDate = formatter.parse(CertificateUtility
				.getCRTdata(certificateFile, "-dates")
				.split("notBefore=")[1]
						.split("notAfter=")[1]);		
		
		// get latest certificateId from DB
		this.certificateId = 01;
		
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

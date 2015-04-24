package de.projectnash.entities;

import java.security.cert.X509Certificate;

public class Certificate {

	private X509Certificate sslCertificate;
	
	private String country;
	
	private String state;
	
	private String locality;
	
	private String organisationName;
	
	private String organisationUnit;
	
	private String commonName;
		

	/**
	 * @param sslCertificate
	 * @param country
	 * @param state
	 * @param locality
	 * @param organisationName
	 * @param organisationUnit
	 * @param commonName
	 */
	public Certificate(X509Certificate sslCertificate, String country,String state, String locality, String organisationName,
			String organisationUnit, String commonName) {
		this.sslCertificate = sslCertificate;
		this.country = country;
		this.state = state;
		this.locality = locality;
		this.organisationName = organisationName;
		this.organisationUnit = organisationUnit;
		this.commonName = commonName;
	}

	/**
	 * @return the sslCertificate
	 */
	public X509Certificate getSslCertificate() {
		return sslCertificate;
	}

	/**
	 * @param sslCertificate the sslCertificate to set
	 */
	public void setSslCertificate(X509Certificate sslCertificate) {
		this.sslCertificate = sslCertificate;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @return the organisationName
	 */
	public String getOrganisationName() {
		return organisationName;
	}

	/**
	 * @param organisationName the organisationName to set
	 */
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	/**
	 * @return the organisationUnit
	 */
	public String getOrganisationUnit() {
		return organisationUnit;
	}

	/**
	 * @param organisationUnit the organisationUnit to set
	 */
	public void setOrganisationUnit(String organisationUnit) {
		this.organisationUnit = organisationUnit;
	}

	/**
	 * @return the commonName
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * @param commonName the commonName to set
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	
	
	
}

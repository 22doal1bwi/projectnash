package de.projectnash.entities;

import java.security.cert.X509Certificate;

/**
 * This class provides a realistic {@link Certificate} with all its attributes.
 * @author Silvio D'Alessandro
 *
 */
public class Certificate {

	private X509Certificate sslCertificate;
	
	private String country;
	
	private String state;
	
	private String locality;
	
	private String organisationName;
	
	private String organisationUnit;
	
	private String commonName;
		

	/**
	 * The constructor for a {@link Certificate} with all necessary attributes.
	 * @param sslCertificate The {@link X509Certificate} that represents the SSL Certificate of the {@link User}.
	 * @param country The {@link String} that represents the country that belongs to the {@link Certificate}.
	 * @param state The {@link String} that represents the state that belongs to the {@link Certificate}.
	 * @param locality The {@link String} that represents the locality that belongs to the {@link Certificate}.
	 * @param organisationName The {@link String} that represents the organization name that belongs to the {@link Certificate}.
	 * @param organisationUnit The {@link String} that represents the organization unit that belongs to the {@link Certificate}.
	 * @param commonName The {@link String} that represents the common name that belongs to the {@link Certificate}.
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

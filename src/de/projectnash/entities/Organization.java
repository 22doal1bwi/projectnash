/**
 * 
 */
package de.projectnash.entities;

/**
 * This class provides all constants in organization context. These constants are used in all {@link Certificate}s.
 * 
 * @author Alexander Dobler
 *
 */
public class Organization {
	
	private String Country = "DE";
	
	private String State = "Baden-Wuerttemberg";
	
	private String Locality = "Stuttgart";
	
	private String Organization = "Nash Inc.";

	public Organization(String country, String state, String locality, String organization) {
		Country = country;
		State = state;
		Locality = locality;
		Organization = organization;
	}

	public Organization() {}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getLocality() {
		return Locality;
	}

	public void setLocality(String locality) {
		Locality = locality;
	}

	public String getOrganization() {
		return Organization;
	}

	public void setOrganization(String organization) {
		Organization = organization;
	}
}
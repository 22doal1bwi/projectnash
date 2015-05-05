/**
 * 
 */
package de.projectnash.entities;

/**
 * @author alexander
 *
 */
public class Organization {
	
	private String Country = "DE";
	
	private String State = "Baden-Wuertemberg";
	
	private String Locality = "Stuttgart";
	
	private String Organization = "Nash Inc.";

	public Organization(String country, String state, String locality,
			String organization) {
		super();
		Country = country;
		State = state;
		Locality = locality;
		Organization = organization;
	}

	public Organization() {
		// empty use predefined values
	}

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

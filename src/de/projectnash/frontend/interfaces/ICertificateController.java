package de.projectnash.frontend.interfaces;

/**
 * This interface provides all methods in {@link Certificate} context.
 * @author Silvio D'Alessandro
 *
 */
public interface ICertificateController {
	
	/**
	 * @return The {@link String} that contains the country of the {@link Certificate}.
	 */
	String getCountryName();
	
	/**
	 * @return The {@link String} that contains the state of the {@link Certificate}.
	 */
	String getState();
	
	/**
	 * @return The {@link String} that contains the locality of the {@link Certificate}.
	 */
	String getLocalityName();
	
	/**
	 * @return The {@link String} that contains the organization name of the {@link Certificate}.
	 */
	String getOrganizationName();
	
	/**
	 * @return The {@link String} that contains the organization unit of the {@link Certificate}.
	 */
	String getOrganizationalUnit();
	
	/**
	 * @return The {@link String} that contains the common name of the {@link Certificate}.
	 */
	String getCommonName();
	
	/**
	 * @return The {@link String} that contains the mail address of the {@link Certificate}.
	 */
	String getEmailAddress();
	
	/**
	 * @return The {@link String} that contains the value of the expiration date of the {@link Certificate}.
	 */
	String getExpirationDate();
	

}

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
	String getCountry();
	
	/**
	 * @return The {@link String} that contains the state of the {@link Certificate}.
	 */
	String getState();
	
	/**
	 * @return The {@link String} that contains the locality of the {@link Certificate}.
	 */
	String getLocality();
	
	/**
	 * @return The {@link String} that contains the organization name of the {@link Certificate}.
	 */
	String getOrganisationName();
	
	/**
	 * @return The {@link String} that contains the organization unit of the {@link Certificate}.
	 */
	String getOrganisationUnit();
	
	/**
	 * @return The {@link String} that contains the common name of the {@link Certificate}.
	 */
	String getCommonName();
	
	/**
	 * @return The {@link String} that contains the mail address of the {@link Certificate}.
	 */
	String getMail();
	
	/**
	 * @return The {@link Boolean} that describes if the request was successful or not.
	 */
	boolean requestCertificate();
	
	/**
	 * @return The {@link Boolean} that describes if the revocation was successful or not.
	 */
	boolean revokeCertificate();
	
	/**
	 * @return The {@link Boolean} that describes if the extension was successful or not.
	 */
	boolean extendCertificate();

	/**
	 * @return The {@link String} that contains the value of the expiration date of the {@link Certificate}.
	 */
	String getExpirationDate();
	

}

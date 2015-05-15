package de.projectnash.frontend.interfaces;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;

/**
 * This interfaces provides all methods in {@link User} context.
 * @author Silvio D'Alessandro
 *
 */
public interface IUserController {
		
	/**
	 * @return The {@link String} that represents the first name of the {@link User}.
	 */
	String getFirstName();
	
	/**
	 * @return The {@link String} that represents the last name of the {@link User}.
	 */
	String getLastName();
	
	/** 
	 * @return The {@link String} that represents the full name of the {@link User}.
	 */
	String getFullName();
	
	/**
	 * @return The {@link String} that represents the common name of the {@link User}.
	 */
	String getCommonName();
	
	/**
	 * @return The {@link String} that represents the mail address of the {@link User}.
	 */
	String getEmailAddress();
	
	/**
	 * @return The {@link String} that represents the department of the {@link User}.
	 */
	String getDepartment();
	
	/**
	 * @return The {@link Integer} that represents the personal ID of the {@link User}.
	 */
	int getPersonalId();
	
	/**
	 * Sets the new password for the {@link User}.
	 * @param oldPassword The {@link String} that represents the old password of the {@link User}.
	 * @param newPassword The {@link String} that represents the new password of the {@link User}.
	 * @return The {@link Boolean} that describes if the new password was successfully set.
	 */
	boolean setPassword(String oldPassword, String newPassword);
	
	/**
	 * @return true if the {@link User} is currently requesting a {@link Certificate}.
	 */
	boolean hasRequest();
	
	/**
	 * @return true if the {@link User} is allowed to download a {@link Certificate}.
	 */
	boolean allowedToDownloadCertificate();
	
	/**
	 * @return true if {@link User} has a {@link Certificate} - no check of validity.
	 */
	boolean hasCertificate();
	
	/**
	 * @return true if {@link User} has a valid {@link Certificate}.
	 */
	boolean hasValidCertificate();
	
	/**
	 * @return The remaining time of the {@link Certificate} in an appropriate unit.
	 * @throws FileNotFoundException 
	 */
	String getRemainingTimeOfCertificate() throws FileNotFoundException;
	
	/**
	 * @param certificate
	 * @param timeUnit
	 * @return The remaining time of the {@link Certificate}.
	 */
	int getRemainingTimeOfCertificate(TimeUnit timeUnit) throws FileNotFoundException;
	
}

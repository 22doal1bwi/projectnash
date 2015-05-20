package de.projectnash.application;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.mail.MessagingException;

import de.projectnash.application.util.EmailSubject;
import de.projectnash.application.util.EmailUtility;
import de.projectnash.databackend.UserPersistenceService;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

/**
 * 
 * This class provides all methods to handle the {@link User}.
 * 
 * @author Marius Boepple, Jonathan Schlotz, Silvio D'Alessandro
 *
 */
public class UserLogic {

	/**
	 * Creates an {@link User} and stores it to the database via {@link UserPersistenceService}.
	 * 
	 * @param personalId The {@link String} that represents the personalId of the {@link User}.
	 * @param firstName The {@link String} that represents the first name of the {@link User}.
	 * @param lastName The {@link String} that represents the last name of the {@link User}.
	 * @param emailAddress The {@link String} that represents the email address of the {@link User}.
	 * @param organizationunit The {@link String} that represents the organization unit of the {@link User}.
	 * @param password The {@link String} that represents the password of the {@link User}.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean createUser(String personalId, String firstName,
			String lastName, String emailAddress, String organizationunit,
			String password) {
		
		try {
			User tempUser = new User(
				Integer.parseInt(personalId),
				firstName,
				lastName,
				organizationunit,
				emailAddress,
				password);

		/** save the user to the database. */
		UserPersistenceService.storeUser(tempUser);
		LogLogic.createLog("Der Benutzer wurde erfolgreich in der Datenbank gespeichert", emailAddress);
		return true;
		
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Der Benutzer konnte nicht in der Datenbank gespeichert werden", emailAddress);
			return false;
		}		
	}
		
	//TODO: see comment
	public static boolean activateCertificateForRequest(User user, String password) {
		try {
			if(hasCertificate(user)){
				//	CertificateUtility.revokeCRT(user.getCertificate().getCertificateFile(), CertificateUtility.extractPrivateKey(user.getCertificate().getCertificateFile()));
				user.setCertificate(null);
			}		
			CertificateLogic.createCertificate(user, password);
			RequestLogic.removeRequest(user);
			checkAndUpdateAllowanceToDownload(user);
			UserPersistenceService.updateUser(user);
			LogLogic.createLog("Der Benutzer ist dazu berechtigt das Zertifikat herunterzuladen", user.getEmailAddress());
			return true;	
		} catch (Exception e) {
			LogLogic.createLog("Der Benutzer konnte nicht berechtigt werden, das Zertifikat herunterzuladen", user.getEmailAddress());
			e.printStackTrace();
			return false;
		}		
	}
	
	public static void checkAndUpdateAllowanceToDownload(User user){
		if (RequestLogic.getRequestStatus(user).equalsIgnoreCase("accepted")) {
			if (!user.isAllowedToDownload()){
				user.setAllowedToDownload(true);
				UserPersistenceService.updateUser(user);
				return;
			}
		}
		if (CertificateLogic.certificateIsValid(user.getCertificate())) {
			if (!user.isAllowedToDownload()) {				
				user.setAllowedToDownload(true);
				UserPersistenceService.updateUser(user);
			}
		} else {
			if (user.isAllowedToDownload()) {
				user.setAllowedToDownload(false);
				UserPersistenceService.updateUser(user);
			}
		}		
	}
	
	/**
	 * Checks if the {@link User} already has a {@link Request}.
	 * 
	 * @param user The {@link User} whose {@link Request} will be checked.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean hasRequest(User user) {
		return RequestLogic.requestExists(user);
	}
	
	public String getRequestStatus(User user) {
		return RequestLogic.getRequestStatus(user);
	}
	
	public static boolean denyRequest(User user) {
		return RequestLogic.denyRequest(RequestLogic.loadRequest(user));
	}
	
	public static boolean confirmRequest(User user) {
		return RequestLogic.confirmRequest(RequestLogic.loadRequest(user));
	}
	
	/**
	 * Changes the password of an {@link User}.
	 * 
	 * @param user The {@link User} whose password will be changed.
	 * @param oldPassword The {@link String} that represents the old password.
	 * @param newPassword The {@link String} that represents the new password.
	 * @return The {@link Boolean} that describes if the password has been changed.
	 */
	public static boolean changePassword(User user, String oldPassword, String newPassword) {
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			UserPersistenceService.updateUser(user);
			LogLogic.createLog("Das Passwort des Benutzers wurde erfolgreich ge�ndert", user.getEmailAddress());
			return true;
		}	
		LogLogic.createLog("Das Passwort des Benutzers konnte nicht ge�ndert werden", user.getEmailAddress());
		return false;
	}

	/**
	 * Resets the password of the specified {@link User}.
	 * 
	 * @param user The {@link User} whose password will be reseted.
	 */
	public static void resetPasswort(User user) throws MessagingException{
		SecureRandom random = new SecureRandom();
		String newPassword = new BigInteger(130, random).toString(32).substring(0, 10);
		
		user.setPassword(newPassword);
		UserPersistenceService.updateUser(user);

		EmailUtility.sendMail(user, EmailSubject.PASSWORD_RESET);
	}
	
	/**
	 * Loads an {@link User} specified by the entered ssnId.
	 * 
	 * @param ssnId The {@link String} that represents the ssnId of the {@link Session}.
	 * @return The {@link User} specified by the ssnId.
	 */
	public static User loadUserBySession(String ssnId){
		return SessionLogic.loadSession(ssnId).getUser();
	}
	
	/**
	 * Loads an {@link User} specified by the entered email address.
	 * 
	 * @param eMailAddress The {@link String} that represents the email address of the {@link User}.
	 * @return The {@link User} specified by the email address.
	 */
	public static User loadUser(String eMailAddress){
		return UserPersistenceService.loadUser(eMailAddress);	
	}
	
	/**
	 * Updates an {@link User} specified by the entered {@link User}.
	 * 
	 * @param user
	 */
	public static void updateUser(User user) {
		UserPersistenceService.updateUser(user);
	}
	
	/**
	 * Method which checks if a user with a specific email address already exists
	 * 
	 * @param emailAddress the email address of the user you want to check for existence
	 * @return true if the user exists
	 */
	public static boolean emailAlreadyExists(String emailAddress) {
		return UserPersistenceService.userExists(emailAddress);
	}

	/**
	 * Method which checks if a user with a specific personal id already exists
	 * 
	 * @param personalId the personal id of the user you want to check for existence
	 * @return true if the user exists
	 */
	public static boolean personalIdAlreadyExists(String personalId) {
		if (personalId != null){
			if (!personalId.isEmpty()){
				return UserPersistenceService.userExists(Integer.parseInt(personalId));	
			}
		}			
	return false;
	}	
		
	public static boolean hasCertificate(User user){
		return user.getCertificate() != null;
	}
	
	public static boolean hasValidCertificate(User user){
		return CertificateLogic.certificateIsValid(user.getCertificate());
	}
	
	/* G E T T E R */
	
	public static String getCommonName(User user){
		return (user.getFirstName() + " " + user.getLastName() + " (" + user.getPersonalId() + ")");
	}
	
	public static String getFullName(User user){
		return (user.getFirstName() + " " + user.getLastName());
	}
	
	public static String getFirstName(User user) {
		return user.getFirstName();
	}

	public static String getLastName(User user) {
		return user.getLastName();
	}

	public static String getEmailAddress(User user) {
		return user.getEmailAddress();
	}

	public static String getDepartment(User user) {
		return user.getDepartment();
	}

	public static int getPersonalId(User user) {
		return user.getPersonalId();
	}

	public static boolean isAllowedToDownload(User user){
		checkAndUpdateAllowanceToDownload(user);
		return user.isAllowedToDownload();
	}
	
	public static boolean isAdmin(User user) {
		return user.isAdmin();
	}
	
}

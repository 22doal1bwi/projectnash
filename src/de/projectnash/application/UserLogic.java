package de.projectnash.application;

import de.projectnash.databackend.UserPersistenceService;
import de.projectnash.entities.User;

/**
 * 
 * This class provides all methods to handle the {@link User}.
 * 
 * @author Marius Boepple, Jonathan Schlotz, Silvio D'Alessandro
 *
 */
public class UserLogic {

	public static boolean createUser(int personalId, String firstName,
			String lastName, String emailAddress, String organzationalUnit,
			String password) {
		
		User tempUser = new User(
				personalId,
				firstName,
				lastName,
				organzationalUnit,
				emailAddress,
				password);

		// save User to Database
		UserPersistenceService.storeUser(tempUser);
		LogLogic.createLog("Benutzer wurde in der Datenbank gespeichert", emailAddress);
		return false;
	}
	
	public static User loadUser(String eMailAddress){
		return UserPersistenceService.loadUser(eMailAddress);	
	}
	
	public static boolean userExists(String emailAddress){
		return UserPersistenceService.userExists(emailAddress);
	}
	
	public static String getCommonName(User user){
		return (user.getFirstName() + " " + user.getLastName() + " (" + user.getPersonalId() + ")");
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
	public static boolean personalIdAlreadyExists(int personalId) {
		return UserPersistenceService.userExists(personalId);
	}
	
	public static boolean hasValidCertificate(User user){
		return CertificateLogic.certificateIsValid(user.getCertificate());
	}
	
	// TODO: implement changePassword method
	public static boolean changePassword() {
		return false;
	}

	// TODO: implement resetPassword method
	public static boolean resetPasswort() {
		return false;
	}
}

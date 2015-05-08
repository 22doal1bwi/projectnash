package de.projectnash.application;

import de.projectnash.databackend.SessionPersistenceService;
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

	public static boolean createUser(String personalId, String firstName,
			String lastName, String emailAddress, String organzationalUnit,
			String password) {
		
		try {
			User tempUser = new User(
				Integer.parseInt(personalId),
				firstName,
				lastName,
				organzationalUnit,
				emailAddress,
				password);

		// save User to database
		UserPersistenceService.storeUser(tempUser);
		LogLogic.createLog("Benutzer wurde erfolgreich in der Datenbank gespeichert", emailAddress);
		return true;
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public static User loadUserBySession(String ssnId){
		return SessionPersistenceService.loadSession(ssnId).getUser();
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
	public static boolean personalIdAlreadyExists(String personalId) {
		if (personalId != null){
			if (!personalId.isEmpty()){
				return UserPersistenceService.userExists(Integer.parseInt(personalId));	
			}
		}			
	return false;
	}
	
	public static boolean hasValidCertificate(User user){
		return CertificateLogic.certificateIsValid(user.getCertificate());
	}
	
	// TODO: implement changePassword method
	public static boolean changePassword(User user, String oldPassword, String newPassword) {
		return false;
	}

	// TODO: implement resetPassword method
	public static boolean resetPasswort() {
		return false;
	}
}

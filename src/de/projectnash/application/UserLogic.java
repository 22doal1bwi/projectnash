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

		return false;
	}
	
	public static User loadUser(String eMailAddress){
		return UserPersistenceService.loadUser(eMailAddress);	
	}
	
	public static void removeSession(String ssnId){
		SessionPersistenceService.removeSession(SessionPersistenceService.loadSession(ssnId));
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
		return UserPersistenceService.loadUser(emailAddress) !=  null;
	}

	/**
	 * Method which checks if a user with a specific personal id already exists
	 * 
	 * @param personalId the personal id of the user you want to check for existence
	 * @return true if the user exists
	 */
	public static boolean personalIdAlreadyExists(int personalId) {
		return UserPersistenceService.loadUser(personalId) != null;
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

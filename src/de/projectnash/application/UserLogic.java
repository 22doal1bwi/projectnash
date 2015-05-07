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
	
	// TODO: implement emailValid check-method
	public static boolean emailIsValid(String emailAddress) {
		return false;
	}

	// TODO: implement personalIdIsValid check-method
	public static boolean personalIdIsValid(int personalId) {
		return false;
	}

	// TODO: implement loginUser method
	public static User loginUser(String name, String password) {

		return null;
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

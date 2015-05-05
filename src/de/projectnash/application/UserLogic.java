package de.projectnash.application;

import de.projectnash.entities.User;

/**
 * 
 * This class provides all methods to handle the {@link User}.
 * 
 * @author Marius Boepple, Jonathan Schlotz
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
				emailAddress,
				organzationalUnit,
				password);

		// TODO: save User to Database

		return false;
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

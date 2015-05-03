package de.projectnash.application;

import de.projectnash.entities.User;

/**
 * 
 * This class provides all methods to handle the {@link User}.
 * 
 * @author Marius Böpple, Jonathan Schlotz
 *
 */
public class UserHandler {

	public static boolean createUser(int personalId, String firstName,
			String lastName, String emailAddress, String countryName,
			String state, String localityName, String organzationalUnit,
			String password) {

		User tempUser = new User(personalId, firstName, lastName, emailAddress,
				countryName, state, localityName, organzationalUnit, password);

		// save to DB

		return false;
	}

	public static boolean emailIsValid(String emailAddress) {
		return false;
	}

	public static boolean personalIdIsValid(int personalId) {
		return false;
	}

	public static User loginUser(String name, String password) {

		return null;
	}

	public static boolean changePassword() {
		return false;
	}

	public static boolean resetPasswort() {
		return false;
	}
}

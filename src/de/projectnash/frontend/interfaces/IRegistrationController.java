package de.projectnash.frontend.interfaces;

import de.projectnash.entities.User;

/**
 * This interface provides all methods in registration context.
 * @author Silvio D'Alessandro
 *
 */
public interface IRegistrationController {
	
	/**
	 * Tries to register a {@link User}.  
	 * @param personalId The {@link Integer} that represents the ID of the {@link User}.
	 * @param firstName The {@link String} that represents the first name of the {@link User}.
	 * @param lastName The {@link String} that represents the last name of the {@link User}.
	 * @param mailAddress The {@link String} that represents the mail address of the {@link User}.
	 * @param department The {@link String} that represents the department of the {@link User}.
	 * @param password The {@link String} that represents the password of the {@link User}.
	 * @return The {@link Boolean} that describes if the {@link User} was successful registered.
	 */
	boolean addUser(int personalId, String firstName, String lastName, String mailAddress, String department, String password);
	
	/**
	 * Checks if the {@link User}'s personal ID is unique.
	 * @param personalId The {@link Integer} that will be checked.
	 * @return The {@link Boolean} that describes if the personal ID is unique or not.
	 */
	boolean checkPersonalId(int personalId);
	
	/**
	 * Checks if the {@link User}'s mail address is unique.
	 * @param mailAddress The {@link String} that will be checked.
	 * @return The {@link Boolean} that describes if the personal ID is unique or not.
	 */
	boolean checkMailAddress(String mailAddress);
}

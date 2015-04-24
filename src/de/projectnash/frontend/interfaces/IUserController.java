package de.projectnash.frontend.interfaces;

/**
 * This interfaces provides all methods in {@link User} context.
 * @author Silvio D'Alessandro
 *
 */
public interface IUserController {
	
	/**
	 * @return The {@link String} that represents the name of the {@link User}.
	 */
	String getUsername();
	
	/**
	 * @return The {@link String} that represents the mail address of the {@link User}.
	 */
	String getMailAddress();
	
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

}

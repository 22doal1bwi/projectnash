package de.projectnash.entities;

/**
 * This class provides a realistic {@link User} with all its attributes.
 * @author Silvio D'Alessandro
 *
 */
public class User {
	
	private int personalId;
	
	private String firstName;
	
	private String lastName;
	
	private String mailAddress;
	
	private String department;
	
	private String password;

	/** The constructor for a {@link User} with all necessary attributes.
	 * @param personalId The {@link Integer} that represents the ID of the {@link User}.
	 * @param firstName The {@link String} that represents the first name of the {@link User}.
	 * @param lastName The {@link String} that represents the last name of the {@link User}.
	 * @param mailAddress The {@link String} that represents the mail address of the {@link User}.
	 * @param department The {@link String} that represents the department of the {@link User}.
	 * @param password The {@link String} that represents the password of the {@link User}.
	 */
	public User(int personalId, String firstName, String lastName, String mailAddress, String department, String password) {
		this.personalId = personalId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailAddress = mailAddress;
		this.department = department;
		this.password = password;
	}

	/**
	 * @return the personalId
	 */
	public int getPersonalId() {
		return personalId;
	}

	/**
	 * @param personalId the personalId to set
	 */
	public void setPersonalId(int personalId) {
		this.personalId = personalId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

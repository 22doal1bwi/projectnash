package de.projectnash.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.projectnash.application.util.PossibleDepartment;

/**
 * This class provides a realistic {@link User} with all its attributes.
 * 
 * @author Silvio D'Alessandro, Marius Boepple, Artur Ermisch
 *
 */
@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name = "QUERY_FIND_USER_BY_EMAIL_ADDRESS", query = "SELECT u FROM User u WHERE u.emailAddress = :emailAddress"),
	@NamedQuery(name = "QUERY_FIND_USER_BY_PERSONAL_ID", query = "SELECT u FROM User u WHERE u.personalId = :personalId"),
	@NamedQuery(name = "QUERY_FIND_ALL_USERS", query = "SELECT u FROM User u"),
	@NamedQuery(name = "CHECK_USER_EXISTS_BY_PERSONAL_ID", query = "SELECT COUNT(u.personalId) FROM User u WHERE u.personalId= :" + "personalId"),
	@NamedQuery(name = "CHECK_USER_EXISTS_BY_MAIL_ADDRESS", query = "SELECT COUNT(u.emailAddress) FROM User u WHERE u.emailAddress= :" + "emailAddress"),
	@NamedQuery(name = "QUERY_REMOVE_USER_BY_USER", query = "DELETE FROM User u WHERE u = :User")})

public class User implements Serializable {
	
	private static final long serialVersionUID = -5634052735211088406L;

	@Id
	@Column(name="usr_id", nullable = false)
	private int personalId;
	
	@Column(name="usr_first_name", nullable = false)
	private String firstName;
	
	@Column(name="usr_last_name", nullable = false)
	private String lastName;
	
	@Column(name="usr_email_address", nullable = false, unique = true)
	private String emailAddress;
	
	@Column(name="usr_department", nullable = false)
	@Enumerated(EnumType.STRING)
	private PossibleDepartment department;
	
	@Column(name="usr_password", nullable = false)
	private String password;
	
	@Column(name="usr_is_admin", columnDefinition="BOOLEAN default 0", nullable = false)
	private boolean isAdmin;
	
	@JoinColumn
	@OneToOne(cascade = CascadeType.ALL)
	private Certificate certificate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="usr_created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable= true, updatable= false)
	private Date creationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="usr_modified_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable= false, updatable= true)
	private Date modificationDate;

	/**
	 * This constructor is only needed for JPA.
	 */
	protected User() {}
	
	/**
	 * The constructor for a {@link User} with all necessary attributes.
	 * 
	 * @param personalId The {@link String} that represents the personalId of the {@link User}.
	 * @param firstName The {@link String} that represents the first name of the {@link User}.
	 * @param lastName The {@link String} that represents the last name of the {@link User}.
	 * @param department The {@link String} that represents the department of the {@link User}.
	 * @param emailAddress The {@link String} that represents the email address of the {@link User}.
	 * @param password The {@link String} that represents the password of the {@link User}.
	 */
	public User(int personalId, String firstName, String lastName, PossibleDepartment department, String emailAddress, String password) {
		this.personalId = personalId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress.toLowerCase();
		this.department = department;
		this.password = password;
		this.isAdmin = false;
	}
	
	/**
	 * This constructor is only needed for JSON notation from the 'AdminRequestServlet' to the frontend 
	 * 
	 * @param firstName The {@link String} that represents the first name of the {@link User}.
	 * @param lastName The {@link String} that represents the last name of the {@link User}.
	 * @param department The {@link String} that represents the department of the {@link User}.
	 * @param personalId The {@link String} that represents the personalId of the {@link User}.
	 * @param emailAddress The {@link String} that represents the email address of the {@link User}.
	 */
	public User(String firstName, String lastName, PossibleDepartment department, int personalId, String emailAddress) {
		this.personalId = personalId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress.toLowerCase();
		this.department = department;
	}
	
	public int getPersonalId() {
		return personalId;
	}

	public void setPersonalId(int personalId) {
		this.personalId = personalId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.toLowerCase();
	}

	public PossibleDepartment getDepartment() {
		return department;
	}

	public void setDepartment(PossibleDepartment department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificateId) {
		this.certificate = certificateId;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Override
	public String toString(){
		return this.lastName + ", " + this.firstName + " (Personal-ID: " + this.personalId + ") from " + this.department + " (E-Mail: " + this.emailAddress + ")"; 
	}
}
package de.projectnash.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class provides a realistic {@link User} with all its attributes.
 * 
 * @author Silvio D'Alessandro, Marius Boepple
 *
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
})

public class User implements Serializable {
	
	private static final long serialVersionUID = -5634052735211088406L;

	@Id
	@Column(name="PERSONAL_ID")
	private int personalId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="MAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name="ORGA_UNIT")
	private String organzationalUnit;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="CERTIFICATE")
	private Certificate certificate;

	/**
	 * This constructor is only needed for JPA.
	 */
	protected User() {
		
	}
	
	/**
	 * The constructor for a {@link User} with all necessary attributes.
	 */
	public User(int personalId, String firstName, String lastName, String organzationalUnit, 
			String emailAddress, String password) {
		super();
		this.personalId = personalId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.organzationalUnit = organzationalUnit;
		this.password = password;
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
		this.emailAddress = emailAddress;
	}

	public String getOrganzationalUnit() {
		return organzationalUnit;
	}

	public void setOrganzationalUnit(String organzationalUnit) {
		this.organzationalUnit = organzationalUnit;
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

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	
	@Override
	public String toString(){
		return this.lastName + ", " + this.firstName + " (Personal-ID: " + this.personalId + ") from " + this.organzationalUnit + " (E-Mail: " + this.emailAddress + ")"; 
	}
}

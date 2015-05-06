package de.projectnash.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class provides a realistic {@link Session} with all its attributes.
 * @author Silvio D'Alessandro
 *
 */
@Entity
@Table(name="sessions")
public class Session {

	@Id
	@Column(name = "ssn_id")
	private String ssnId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@Column(name = "usr_id")
	private int userId;
	
}

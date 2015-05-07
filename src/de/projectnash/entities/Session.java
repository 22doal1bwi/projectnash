package de.projectnash.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class provides a realistic {@link Session} with all its attributes.
 * 
 * @author Silvio D'Alessandro
 *
 */
@Entity
@Table(name = "sessions")
@NamedQueries({
		@NamedQuery(name = "QUERY_FIND_SESSION_BY_ID", query = "SELECT s FROM Session s WHERE s.ssnId = :ssnId"),
		@NamedQuery(name = "QUERY_REMOVE_SESSION_BY_SESSION", query = "DELETE FROM Session s WHERE s = :Session") })
public class Session {

	@Id
	@Column(name = "ssn_id")
	private String ssnId;

	@JoinColumn
	@OneToOne(cascade = CascadeType.MERGE)
	private User user;

	/**
	 * This constructor is only needed for JPA.
	 */
	protected Session() {
	}

	/**
	 * The constructor for the {@link Session} with all its necessary
	 * attributes.
	 * 
	 * @param user
	 *            The {@link User} of the {@link Session}.
	 * @param ssnId
	 *            The {@link String} ssnId of the {@link Session}.
	 */
	public Session(User user, String ssnId) {
		this.user = user;
		this.ssnId = ssnId;
	}

	/**
	 * @return the id of a session
	 */
	public String getSsnId() {
		return ssnId;
	}

	/**
	 * @return the user linked to this session
	 */
	public User getUser() {
		return user;
	}

}

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
 * This class provides a realistic {@link Request} for a certificate with all
 * its attributes.
 * 
 * @author Marius Boepple
 *
 */

@Entity
@Table(name = "requests")
@NamedQueries({
		@NamedQuery(name = "QUERY_FIND_REQUESTS_BY_ID", query = "SELECT r FROM Request r WHERE r.reqId = :reqId"),
		@NamedQuery(name = "QUERY_REMOVE_REQUEST_BY_REQUEST", query = "DELETE FROM Request r WHERE r = :Request"),
		@NamedQuery(name = "CHECK_REQUEST_EXISTS_BY_USER", query = "SELECT COUNT(r.user) FROM Request r WHERE r.user = :User") })
public class Request {

	@Id
	@Column(name = "req_id")
	private String reqId;

	@JoinColumn
	@OneToOne(cascade = CascadeType.MERGE)
	private User user;

	/**
	 * This constructor is only needed for JPA.
	 */
	protected Request() {
	}

	/**
	 * The constructor for the {@link Request} with all its necessary
	 * attributes.
	 * 
	 * @param user
	 *            The {@link User} of the {@link Request}.
	 * @param ssnId
	 *            The {@link String} reqId of the {@link Request}.
	 */
	public Request(User user, String reqId) {
		this.user = user;
		this.reqId = reqId;
	}

	/**
	 * @return the id of a request
	 */
	public String getReqId() {
		return reqId;
	}

	/**
	 * @return the user linked to this request
	 */
	public User getUser() {
		return user;
	}

}

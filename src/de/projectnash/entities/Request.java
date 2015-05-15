package de.projectnash.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class provides a realistic {@link Request} for a {@link Certificate} with all
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "req_created_on", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable= true, updatable= false)
	private Date creationDate;
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
	 * @return The id of the {@link Request}.
	 */
	public String getReqId() {
		return reqId;
	}

	/**
	 * @return The {@link User} linked to this {@link Request}.
	 */
	public User getUser() {
		return user;
	}

}

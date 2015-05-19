package de.projectnash.entities;

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

import de.projectnash.application.util.RequestStatus;

/**
 * This class provides a realistic {@link Request} for a {@link Certificate} with all
 * its attributes.
 * 
 * @author Marius Boepple, Silvio D'Alessandro
 *
 */

@Entity
@Table(name = "requests")
@NamedQueries({
		@NamedQuery(name = "QUERY_FIND_REQUEST_BY_USER", query = "SELECT r FROM Request r WHERE r.user = :User" ),
		@NamedQuery(name = "QUERY_REMOVE_REQUEST_BY_REQUEST", query = "DELETE FROM Request r WHERE r = :Request"),
		@NamedQuery(name = "QUERY_FIND_ALL_REQUESTS", query = "SELECT r FROM Request r"),
		@NamedQuery(name = "CHECK_REQUEST_EXISTS_BY_USER", query = "SELECT COUNT(r.user) FROM Request r WHERE r.user = :User") })
public class Request {

	@Id
	@JoinColumn
	@OneToOne(cascade = CascadeType.MERGE)
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "req_created_on", nullable = false, insertable= true, updatable= false)
	private Date creationDate;
	
	@Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;
	
	/**
	 * This constructor is only needed for JPA.
	 */
	protected Request() {
	}

	/**
	 * This constructor represents a {@link Request} with all its necessary attributes.
	 * 
	 * @param user
	 * @param creationDate
	 */
	public Request(User user, Date creationDate) {
		this.user = user;
		this.creationDate = creationDate;
		this.requestStatus = RequestStatus.WAITING;
	}
	
	/**
	 * @return The {@link User} linked to this {@link Request}.
	 */
	public User getUser() {
		return user;
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public RequestStatus getRequestStatus(){
		return this.requestStatus;
	}
	
	@Override
	public String toString(){
		return this.user.getFirstName() + ", " + this.user.getLastName() + " has created a request on: " + this.creationDate; 
	}

}

package de.projectnash.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class provides a realistic {@link Log} with all its attributes.
 * 
 * @author Artur Ermisch
 *
 */
@Entity
@Table(name = "logs")
@NamedQueries({
	@NamedQuery(name = "QUERY_FIND_LOG_BY_LOG_ID", query = "SELECT u FROM Log u WHERE u.logId = :logId"),
	@NamedQuery(name = "QUERY_FIND_ALL_LOGS", query = "SELECT u FROM Log u")
})

public class Log implements Serializable {

	private static final long serialVersionUID = 6367651307980196733L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id", nullable = false)
	private int logId;
	
	@Column(name = "log_message", nullable = false)
	private String message;
	
	@Column(name = "log_created_by", nullable = false)
	private String creationUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_created_on", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = true, updatable = false)
	private Date creationDate;

	/**
	 * This constructor is only needed for JPA.
	 */
	protected Log() {}
	
	/**
	 * The constructor for a {@link Log} with all necessary attributes.
	 */
	public Log(String message, String creationUser) {
		this.message = message;
		this.creationUser = creationUser;
	}
}
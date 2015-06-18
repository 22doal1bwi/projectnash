package de.projectnash.frontend.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.RequestLogic;
import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.RequestStatus;
import de.projectnash.entities.User;

/**
 * Controller class for the {@link User}.
 * 
 * @author Silvio D'Alessandro
 *
 */
public class UserController {
		
	private User user;
	
	/**
	 * Constructor to initialize the {@link UserController}. 
	 * 
	 * @param sessionId The {@link String} on basis which the {@link UserController} will be initialized.
	 */
	public UserController(String sessionId) {
		user = SessionLogic.loadSession(sessionId).getUser();
	}
	
	/**
	 * Checks if the {@link User} has a waiting {@link Request}.
	 * 
	 * @return The {@link Boolean} that describes if the {@link User} has a waiting {@link Request}s.
	 */
	public boolean hasWaitingRequest() {
		try {
			return RequestLogic.getRequestStatus(user) == RequestStatus.WAITING;
		} catch (Exception e) {
			return false;
		}		
	}
	
	/**
	 * Checks if the {@link User} has an accepted {@link Request}.
	 * 
	 * @return The {@link Boolean} that describes if the {@link User} has an accepted {@link Request}.
	 */
	public boolean hasAcceptedRequest() {
		try {
			if(!RequestLogic.requestExists(user)){
				return false;
			}else {
				return RequestLogic.getRequestStatus(user) == RequestStatus.ACCEPTED;
			}
		} catch (Exception e) {
			return false;
		}		
	}
	
	/**
	 * Checks if the {@link User} has a denied {@link Request}.
	 * 
	 * @return The {@link Boolean} that describes if the {@link User} has an denied {@link Request}.
	 */
	public boolean hasDeniedRequest() {
		try {
			return RequestLogic.getRequestStatus(user) == RequestStatus.DENIED;
		} catch (Exception e) {
			return false;
		}		
	}
	
	/**
	 * Calculates the remaining time of the {@link Certificate} until it expires.
	 * 
	 * @return The {@link String} that represents the time left.
	 */
	public String getRemainingTimeOfCertificate(){
		try {
			return CertificateLogic.getAppropriateTimeLeftForCertificate(user.getCertificate());
		} catch (Exception e) {
			return "[unbekannte Zeit]";
		}		
	}
	
	/**
	 * Calculates the remaining time of the {@link Certificate} until it expires.
	 * 
	 * @param timeUnit The {@link TimeUnit} that represents the format of the time unit result.
	 * @return The {@link Integer} that represents the time left.
	 */
	public int getRemainingTimeOfCertificate(TimeUnit timeUnit){
		try {
			return CertificateLogic.getTimeLeftForCertificate(user.getCertificate(), timeUnit);
		} catch (Exception e) {
			return 0;
		}		
	}
	
	public List<User> getAllUsers(){
		return UserLogic.getAllUsers();
	}

	public String getFirstName() {
		return UserLogic.getFirstName(user);
	}

	public String getLastName() {
		return UserLogic.getLastName(user);
	}

	public String getFullName() {
		return UserLogic.getFullName(user);
	}

	public String getCommonName() {
		return UserLogic.getCommonName(user);
	}
	public String getEmailAddress() {
		return UserLogic.getEmailAddress(user);
	}

	public String getDepartment() {
		return UserLogic.getDepartment(user);
	}

	public int getPersonalId() {
		return UserLogic.getPersonalId(user);
	}

	public boolean setPassword(String oldPassword, String newPassword) {
		return UserLogic.changePassword(user, oldPassword, newPassword);
	}

	public boolean hasRequest() {
		return UserLogic.hasRequest(user);
	}

	public boolean hasValidCertificate() {
		return UserLogic.hasValidCertificate(user);
	}

	public boolean hasCertificate() {
		return UserLogic.hasCertificate(user);
	}

	public boolean isAdmin() {
		return UserLogic.isAdmin(user);
	}
}
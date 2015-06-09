package de.projectnash.frontend.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.RequestLogic;
import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.RequestStatus;
import de.projectnash.entities.User;
import de.projectnash.frontend.interfaces.IUserController;

public class UserController implements IUserController {
	
		
	private User user;
	
	public UserController(String sessionId) {
		user = SessionLogic.loadSession(sessionId).getUser();
	}
	
	public List<User> getAllUsers(){
		return UserLogic.getAllUsers();
	}

	@Override
	public String getFirstName() {
		return UserLogic.getFirstName(user);
	}

	@Override
	public String getLastName() {
		return UserLogic.getLastName(user);
	}

	@Override
	public String getFullName() {
		return UserLogic.getFullName(user);
	}

	@Override
	public String getCommonName() {
		return UserLogic.getCommonName(user);
	}
	@Override
	public String getEmailAddress() {
		return UserLogic.getEmailAddress(user);
	}

	@Override
	public String getDepartment() {
		return UserLogic.getDepartment(user);
	}

	@Override
	public int getPersonalId() {
		return UserLogic.getPersonalId(user);
	}

	@Override
	public boolean setPassword(String oldPassword, String newPassword) {
		return UserLogic.changePassword(user, oldPassword, newPassword);
	}

	@Override
	public boolean hasRequest() {
		return UserLogic.hasRequest(user);
	}

	@Override
	public boolean hasWaitingRequest() {
		try {
			return RequestLogic.getRequestStatus(user) == RequestStatus.WAITING;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
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

	@Override
	public boolean hasDeniedRequest() {
		try {
			return RequestLogic.getRequestStatus(user) == RequestStatus.DENIED;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	public boolean hasValidCertificate() {
		return UserLogic.hasValidCertificate(user);
	}

	@Override
	public boolean hasCertificate() {
		return UserLogic.hasCertificate(user);
	}

	@Override
	public String getRemainingTimeOfCertificate(){
		try {
			return CertificateLogic.getAppropriateTimeLeftForCertificate(user.getCertificate());
		} catch (Exception e) {
			return "[unbekannte Zeit]";
		}		
	}

	@Override
	public int getRemainingTimeOfCertificate(TimeUnit timeUnit){
		try {
			return CertificateLogic.getTimeLeftForCertificate(user.getCertificate(), timeUnit);
		} catch (Exception e) {
			return 0;
		}		
	}

	@Override
	public boolean isAdmin() {
		return UserLogic.isAdmin(user);
	}
	
}

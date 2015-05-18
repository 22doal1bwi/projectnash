package de.projectnash.frontend.controllers;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.RequestLogic;
import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;
import de.projectnash.frontend.interfaces.IUserController;

public class UserController implements IUserController {
	
		
	private User user;
	
	public UserController(String sessionId) {
		user = SessionLogic.loadSession(sessionId).getUser();
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
	public boolean requestCertificate() {
		return RequestLogic.createRequest(user);
	}

	@Override
	public boolean hasRequest() {
		return RequestLogic.hasRequest(user);
	}

	@Override
	public boolean allowedToDownloadCertificate() {
		return UserLogic.isAllowedToDownload(user);
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
	public String getRemainingTimeOfCertificate() throws FileNotFoundException {
		return CertificateLogic.getAppropriateTimeLeftForCertificate(user.getCertificate());
	}

	@Override
	public int getRemainingTimeOfCertificate(TimeUnit timeUnit)
			throws FileNotFoundException {
		return CertificateLogic.getTimeLeftForCertificate(user.getCertificate(), timeUnit);
	}

	@Override
	public boolean isAdmin() {
		return UserLogic.isAdmin(user);
	}

}

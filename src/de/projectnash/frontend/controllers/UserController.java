package de.projectnash.frontend.controllers;

import java.io.FileNotFoundException;

import de.projectnash.application.CertificateLogic;
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
		return user.getFirstName();
	}

	@Override
	public String getLastName() {
		return user.getLastName();
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
		return user.getEmailAddress();
	}

	@Override
	public String getDepartment() {
		return user.getDepartment();
	}

	@Override
	public int getPersonalId() {
		return user.getPersonalId();
	}

	@Override
	public boolean setPassword(String oldPassword, String newPassword) {
		return UserLogic.changePassword(user, oldPassword, newPassword);
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
}

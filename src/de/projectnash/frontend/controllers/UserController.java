package de.projectnash.frontend.controllers;

import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;
import de.projectnash.frontend.interfaces.IUserController;

public class UserController implements IUserController {
	
	private static UserController userController;
	
	private User user;
	
	private UserController(String sessionId) {
		user = SessionLogic.loadSession(sessionId).getUser();
	}
	
	public static UserController loadUserController(String sessionId) {
		if(userController==null){
			userController = new UserController(sessionId);
		} 
		return userController;
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



}

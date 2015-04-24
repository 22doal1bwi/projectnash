package de.projectnash.frontend.interfaces;

public interface IUserController {
	
	String getUsername();
	
	String getMailAddress();
	
	String getDepartment();
	
	int getPersonalId();
	
	boolean setPassword(String oldPassword, String newPassword);

}

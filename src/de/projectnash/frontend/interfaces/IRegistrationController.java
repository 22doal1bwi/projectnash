package de.projectnash.frontend.interfaces;

public interface IRegistrationController {
	
	boolean addUser(int personalId, String firstName, String lastName, String mailAddress, String department, String password);
	
	boolean checkPersonalId(int personalId);
	
	boolean checkMailAddress(String mailAddress);
}

package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.UserLogic;
import de.projectnash.application.util.ServletResponseHandler;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1705632952270495401L;
	
	private static final String E_MAIL_ADDRESS = "emailAddress";
	
	private static final String PERSONAL_ID = "personalId";
	
	private static final String FIRST_NAME = "firstName";
	
	private static final String LAST_NAME = "lastName";
	
	private static final String ORGANIZATION_UNIT = "organizationalUnit";
	
	private static final String PASSWORD = "password";
	
	private static final String PERSONAL_ID_ALREADY_EXISTS = "personalIdAlreadyExists";
	
	private static final String E_MAIL_ADDRESS_ALREADY_EXISTS = "emailAddressAlreadyExists";
	
	private static final String CREATED = "created";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean userCreated = false;
		
		if(!hasMutatedVowels(req.getParameter(E_MAIL_ADDRESS))){
		
		boolean personalIdAlreadyExists = UserLogic.personalIdAlreadyExists(req.getParameter(PERSONAL_ID));
		boolean emailAddressAlreadyExists = UserLogic.emailAlreadyExists(req.getParameter(E_MAIL_ADDRESS));
		
			if (!personalIdAlreadyExists && !emailAddressAlreadyExists) {

			userCreated = UserLogic.createUser(req.getParameter(PERSONAL_ID),
					req.getParameter(FIRST_NAME),
					req.getParameter(LAST_NAME),
					req.getParameter(E_MAIL_ADDRESS),
					req.getParameter(ORGANIZATION_UNIT),
					req.getParameter(PASSWORD));

			} else if (personalIdAlreadyExists) {
				map.put(PERSONAL_ID_ALREADY_EXISTS, true);						
			} else if (emailAddressAlreadyExists){
				map.put(E_MAIL_ADDRESS_ALREADY_EXISTS, true);	
			}
			map.put(CREATED, userCreated);
			ServletResponseHandler.write(resp, map);
	   } else {
		   map.put(CREATED, userCreated);
		   ServletResponseHandler.write(resp, map);
	   }
	}
	
	/**
	 * Checks if the e-mail address contains any mutated vowels.
	 * 
	 * @param emailAddress The {@link String} that represents the entered e-mail address of the {@link User}.
	 * @return The {@link Boolean} that describes if the {@link String} contains any mutated vowels.
	 */
	private boolean hasMutatedVowels(String emailAddress){
		if(emailAddress.contains("ä") || emailAddress.contains("ö") || emailAddress.contains("ü") || emailAddress.contains("ß")){
			return true;
		}
		return false;
	}
}
package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.LogLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.ServletResponseHandler;
import de.projectnash.entities.User;

/**
 * Servlet implementation class ResetPasswordServlet
 */
@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String E_MAIL_ADDRESS_FOR_NEW_PASSWORD = "emailAddressForNewPassword";
	
	private static final String RESET_SUCCESSFUL = "resetSuccessful";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String emailAddress = request.getParameter(E_MAIL_ADDRESS_FOR_NEW_PASSWORD);
		boolean passwordReset = false;
		
	try{
		if(UserLogic.emailAlreadyExists(emailAddress)){
			 User user = UserLogic.loadUser(emailAddress);
			 UserLogic.resetPassword(user);
			 passwordReset = true;
			 map.put(RESET_SUCCESSFUL, passwordReset);
			 LogLogic.createLog("Das Passwort wurde erfolgreich zurückgesetzt", user.getEmailAddress());
		} else {
			map.put(RESET_SUCCESSFUL, passwordReset);
		}
		ServletResponseHandler.write(response, map);
	} catch (MessagingException me){
		me.printStackTrace();
	}
	}
}

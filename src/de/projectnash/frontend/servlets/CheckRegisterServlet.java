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
 * Servlet implementation class CheckRegisterServlet
 */
@WebServlet("/CheckRegisterServlet")
public class CheckRegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PERSONAL_ID = "personalId";
	
	private static final String E_MAIL_ADDRESS = "emailAddress";
	
	private static final String ALREADY_EXISTS = "alreadyExists";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String personalId = request.getParameter(PERSONAL_ID);
		String emailAddress = request.getParameter(E_MAIL_ADDRESS);
		boolean inputAlreadyExists = false;

		if (personalId != null && !personalId.isEmpty()) {
			inputAlreadyExists = UserLogic.personalIdAlreadyExists(personalId);
		} else if (emailAddress != null && !emailAddress.isEmpty()) {
			inputAlreadyExists = UserLogic.emailAlreadyExists(emailAddress);
		}
		map.put(ALREADY_EXISTS, inputAlreadyExists);
		ServletResponseHandler.write(response, map);
	}
}
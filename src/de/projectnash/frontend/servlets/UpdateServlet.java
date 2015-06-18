package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.ServletResponseHandler;
import de.projectnash.entities.User;
import de.projectnash.frontend.controllers.SessionController;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PASSWORD_CHANGE_SUCCESSFUL = "pwChangeSuccessful";
	
	private static final String PASSWORD_CURRENT = "password_current";
	
	private static final String PASSWORD_NEW = "password_new";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionIdStatus = SessionController.checkForSessionId(request,response);
		User user = SessionLogic.loadSession(sessionIdStatus).getUser();
		String passwordCurrent = "";
		String passwordNew = "";
		boolean changeSuccessful = false;

		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {				
			if (entry.getKey().equals(PASSWORD_CURRENT)) {
				passwordCurrent = entry.getValue()[0].toString();
				
			} else if (entry.getKey().equals(PASSWORD_NEW)) {
				passwordNew = entry.getValue()[0].toString();
			}
		}
		changeSuccessful = UserLogic.changePassword(user, passwordCurrent, passwordNew);
		map.put(PASSWORD_CHANGE_SUCCESSFUL, changeSuccessful);
		ServletResponseHandler.write(response, map);
	}
}
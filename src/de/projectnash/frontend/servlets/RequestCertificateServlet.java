package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.RequestLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.ServletResponseHandler;
import de.projectnash.entities.User;
import de.projectnash.frontend.controllers.SessionController;

/**
 * Servlet implementation class Certificate Servlet
 */
@WebServlet("/RequestCertificateServlet")
public class RequestCertificateServlet extends HttpServlet {

	private static final long serialVersionUID = 4192643567772796818L;
	
	private static final String CREATED_REQUEST = "createdRequest";
	
	private static final String VALID_SESSION = "validSession";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionIdStatus = SessionController.checkForSessionId(request, response);
		User user = UserLogic.loadUserBySession(sessionIdStatus);
		
		switch (sessionIdStatus) {
		default:
			boolean createdRequest = RequestLogic.createRequest(user);
				if (createdRequest) {
					map.put(CREATED_REQUEST, true);
				} else {
					map.put(CREATED_REQUEST, false);
				}
					map.put(VALID_SESSION, true);
			break;
		case "0":
			map.put(VALID_SESSION, false);
			break;

		case "-1":
			map.put(VALID_SESSION, false);
			break;
		}
		ServletResponseHandler.write(response, map);
	}
}
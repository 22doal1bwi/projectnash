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
import de.projectnash.entities.User;
import de.projectnash.frontend.controllers.SessionController;

/**
 * Servlet implementation class ActivateCertificateServlet
 */
@WebServlet("/ActivateCertificateServlet")
public class ActivateCertificateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivateCertificateServlet() {}
    
	private static final String PASSWORD = "password";
	
	private static final String ACTIVATED_CERTIFICATE = "activatedCertificate";
	
	private static final String VALID_SESSION = "validSession";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionIdStatus = SessionController.checkForSessionId(request, response);
		
		/** get request parameters for userID and password */
		String password = request.getParameter(PASSWORD);
		User user = UserLogic.loadUserBySession(sessionIdStatus);
		
		switch (sessionIdStatus) {
		default:
			try {
				boolean activateCertificate = UserLogic.assignCertificate(user, password);
				if (activateCertificate) {
					map.put(ACTIVATED_CERTIFICATE, true);
				} else {
					map.put(ACTIVATED_CERTIFICATE, false);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				map.put(ACTIVATED_CERTIFICATE, false);
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
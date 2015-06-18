package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.ServletResponseHandler;
import de.projectnash.entities.User;
import de.projectnash.frontend.controllers.SessionController;

/**
 * Servlet implementation class RevokeCertificateServlet
 */
@WebServlet("/RevokeCertificateServlet")
public class RevokeCertificateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String REASON = "revokeReason";
	
	private static final String REVOKED_CERTIFICATE = "revokedCertificate";
	
	private static final String VALID_SESSION = "validSession";
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionIdStatus = SessionController.checkForSessionId(request, response);
		String reason = request.getParameter(REASON);
		User user = UserLogic.loadUserBySession(sessionIdStatus);
		
		switch (sessionIdStatus) {
		default:
			boolean revokedCertificate = CertificateLogic.revokeCertificate(user, reason);
			
				if (revokedCertificate) {
					map.put(REVOKED_CERTIFICATE, true);

				} else {
					map.put(REVOKED_CERTIFICATE, false);
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

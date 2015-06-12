package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;
import de.projectnash.frontend.controllers.SessionController;

/**
 * Servlet implementation class RevokeCertificateServlet
 */
@WebServlet("/RevokeCertificateServlet")
public class RevokeCertificateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public RevokeCertificateServlet() {}
	
	private static final String REASON = "revokeReason";
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionIdStatus = SessionController.checkForSessionId(request,
				response);
		
		String reason = request.getParameter(REASON);
		
		User user = UserLogic.loadUserBySession(sessionIdStatus);
		
		switch (sessionIdStatus) {
		default:
			boolean revokedCertificate = CertificateLogic.revokeCertificate(user, reason);
			
				if (revokedCertificate) {
					map.put("revokedCertificate", true);

				} else {
					map.put("revokedCertificate", false);
				}
					map.put("validSession", true);
		break;

		case "0":
			map.put("validSession", false);
			break;

		case "-1":
			map.put("validSession", false);
			break;
		}
		write(response, map);
	}

	private void write(HttpServletResponse resp, Map<String, Object> map)
			throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}
}

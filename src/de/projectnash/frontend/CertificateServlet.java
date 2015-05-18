package de.projectnash.frontend;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.LogLogic;
import de.projectnash.application.RequestLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.OpenSSLException;
import de.projectnash.entities.Log;
import de.projectnash.entities.User;

/**
 * Servlet implementation class Certificate Servlet
 */
@WebServlet("/CertificateServlet")
public class CertificateServlet extends HttpServlet {

	private static final long serialVersionUID = 4192643567772796818L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionIdStatus = SessionController.checkForSessionId(request,
				response);
		
		User user = UserLogic.loadUserBySession(sessionIdStatus);
		switch (sessionIdStatus) {
		default:
			try {
				boolean createdCertificate = CertificateLogic
						.createCertificate(user);
				if (createdCertificate) {
					map.put("createdCertificate", true);
					RequestLogic.createRequest(user);
					LogLogic.createLog("Eine Anfrage zur Beantragung eines Zertifikats wurde gestellt.", user.getEmailAddress());
				} else {
					map.put("createdCertificate", false);
				}

			} catch (ParseException | OpenSSLException | InterruptedException e) {
				e.printStackTrace();
				map.put("createdCertificate", false);
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
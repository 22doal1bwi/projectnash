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

/**
 * Servlet implementation class AdminRequestServlet
 */
@WebServlet("/AdminUpdateUsersServlet")
public class AdminUpdateUsersServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String E_MAIL_ADDRESS = "emailAddress";
	
	private static final String ACTION = "action";
	
	private static final String DELETED_USER = "deletedUser";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUpdateUsersServlet() {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String action = request.getParameter(ACTION);
		String emailAddress = request.getParameter(E_MAIL_ADDRESS);
		User user = UserLogic.loadUser(emailAddress);

		switch (action) {
		case "deleteUser":
			boolean deletedUser = UserLogic.removeUser(user);
			map.put(DELETED_USER, deletedUser);
			ServletResponseHandler.write(response, map);
			break;
		case "revokeCertificate":
			boolean revokedCertificate = CertificateLogic.revokeCertificate(user, "Administrator - Zertifikat wurde von Administrator widerrufen");
			response.getWriter().print(revokedCertificate);
			break;
		}
	}
}
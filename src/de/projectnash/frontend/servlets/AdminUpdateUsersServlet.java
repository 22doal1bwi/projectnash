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
import com.sun.org.apache.bcel.internal.generic.NEW;

import de.projectnash.application.CertificateLogic;
import de.projectnash.application.RequestLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;
import de.projectnash.entities.Request;

/**
 * Servlet implementation class AdminRequestServlet
 */
@WebServlet("/AdminUpdateUsersServlet")
public class AdminUpdateUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUpdateUsersServlet() {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		Map<String, Object> map = new HashMap<String, Object>();		
		String action = request.getParameter("action");
		String emailAddress = request.getParameter("emailAddress");

		User user = UserLogic.loadUser(emailAddress);

		switch (action) {
		case "deleteUser":
			boolean deletedUser = UserLogic.removeUser(user);
			map.put("deletedUser", deletedUser);
			break;
		case "revokeCertificate":
			boolean revokedCertificate = CertificateLogic.revokeCertificate(user, "revoked_by_admin");			
			map.put("revokedCertificate", revokedCertificate);
			break;
		}
		write(response, map);
	}

	private void write(HttpServletResponse resp, Map<String, Object> map) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}
}
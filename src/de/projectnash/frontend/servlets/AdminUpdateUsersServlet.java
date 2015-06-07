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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String emailAddress = request.getParameter("emailAddress");

		User user = UserLogic.loadUser(emailAddress);

		switch (action) {
		case "deleteUser":
			Map<String, Object> map = new HashMap<String, Object>();
			boolean deletedUser = UserLogic.removeUser(user);
			map.put("deletedUser", deletedUser);
			write(response, map);
			break;
		case "revokeCertificate":
			boolean revokedCertificate = CertificateLogic
					.revokeCertificate(user,
							"Administrator - Zertifikat wurde von Administrator widerrufen");
			response.getWriter().print(revokedCertificate);
			break;
		}

	}

	private void write(HttpServletResponse resp, Map<String, Object> map)
			throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}
}
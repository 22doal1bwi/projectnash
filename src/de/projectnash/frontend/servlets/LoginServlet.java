package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4192643567772796818L;

	private static final String MAIL_ADDRESS = "emailAddress";
	private static final String PASSWORD = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Map<String, Object> map = new HashMap<String, Object>();

		/** get request parameters for userID and password */
		String emailAddress = request.getParameter(MAIL_ADDRESS);
		String password = request.getParameter(PASSWORD);

		/** try to load user from database */
		User loadedUser = UserLogic.loadUser(emailAddress);

		/** check if a user was received and password is correct */
		if (loadedUser != null && loadedUser.getPassword().equals(password)) {

			HttpSession httpSession = request.getSession();
			httpSession
					.setAttribute(MAIL_ADDRESS, loadedUser.getEmailAddress());

			/** setting session to expire in 30 minutes */
			httpSession.setMaxInactiveInterval(30 * 60);
			httpSession.getId();

			SessionLogic.createSession(loadedUser, httpSession.getId());

			Cookie userName = new Cookie(MAIL_ADDRESS, emailAddress);
			userName.setMaxAge(30 * 60);
			response.addCookie(userName);
			map.put("loginFailed", false);
			write(response, map);
//			response.sendRedirect("intern/index.html");

		} else {
			/** user not present or password wrong */
			map.put("loginFailed", true);
			write(response, map);
			// RequestDispatcher rd =
			// getServletContext().getRequestDispatcher("/login.html");
			// PrintWriter out= response.getWriter();
			// out.println("<font color=red>Your E-Mail-Address or password is incorrect.</font>");
			// rd.include(request, response);
		}
	}

	private void write(HttpServletResponse resp, Map<String, Object> map)
			throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}

}

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

import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.application.util.ServletResponseHandler;
import de.projectnash.entities.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4192643567772796818L;

	private static final String E_MAIL_ADDRESS = "emailAddress";
	
	private static final String PASSWORD = "password";

	private static final String LOGIN_FAILED = "loginFailed";
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String emailAddress = request.getParameter(E_MAIL_ADDRESS);
		String password = request.getParameter(PASSWORD);
		User loadedUser = UserLogic.loadUser(emailAddress);

		/** check if a user was received and password is correct */
		if (loadedUser != null && loadedUser.getPassword().equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute(E_MAIL_ADDRESS, loadedUser.getEmailAddress());
			session.setMaxInactiveInterval(30 * 60);
			
			SessionLogic.createSession(loadedUser, session.getId());

			Cookie userName = new Cookie(E_MAIL_ADDRESS, emailAddress);
			userName.setMaxAge(30 * 60);
			response.addCookie(userName);
			
			map.put(LOGIN_FAILED, false);
			ServletResponseHandler.write(response, map);
		} else {
			map.put(LOGIN_FAILED, true);
			ServletResponseHandler.write(response, map);
		}
	}
}
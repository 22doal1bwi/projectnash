package de.projectnash.frontend.servlets;

import java.io.IOException;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.projectnash.application.SessionLogic;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		String actualCookie = "";
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					
					System.out.println("JSESSIONID=" + cookie.getValue());
					actualCookie = cookie.getValue();
					break;
				}
			}
		}
		
		// invalidate the session if exists
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		
		try{
			SessionLogic.removeSession(actualCookie);
		}catch (NoResultException e){
			e.printStackTrace();
		}
		
		response.sendRedirect("certificates/login.jsp");
	}

}
package de.projectnash.frontend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.UserLogic;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1705632952270495401L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String organizationalUnit = req.getParameter("organizationalUnit");
		String personalId = req.getParameter("personalId");
		String emailAddress = req.getParameter("emailAddress");
		String password = req.getParameter("password");
		
		UserLogic.createUser(Integer.parseInt(personalId), firstName, lastName, emailAddress, organizationalUnit, password);
		
		resp.sendRedirect("login.html");
		
	}
	
}

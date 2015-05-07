package de.projectnash.frontend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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
		int personalId = Integer.parseInt(req.getParameter("personalId"));
		String emailAddress = req.getParameter("emailAddress");
		String password = req.getParameter("password");
		
		boolean emailAlreadyExists = UserLogic.emailAlreadyExists(emailAddress);
		boolean personalIdAlreadyExists = UserLogic.personalIdAlreadyExists(personalId);

		if (emailAlreadyExists) {

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/register.html");
			PrintWriter out = resp.getWriter();
			out.println("<font color=red>Dieser Benutzer existiert bereits. Bitte überprüfen Sie Ihre E-Mail-Adresse.</font>");
			rd.include(req, resp);

		} else if (personalIdAlreadyExists) {

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/register.html");
			PrintWriter out = resp.getWriter();
			out.println("<font color=red>Dieser Benutzer existiert bereits. Bitte überprüfen Sie Ihre Personalnummer.</font>");
			rd.include(req, resp);

		} else {

			UserLogic.createUser(personalId, firstName, lastName, emailAddress,
					organizationalUnit, password);
			resp.sendRedirect("login.html");

		}

	}

}

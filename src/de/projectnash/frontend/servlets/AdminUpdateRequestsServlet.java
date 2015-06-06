package de.projectnash.frontend.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.RequestLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;
import de.projectnash.entities.Request;

/**
 * Servlet implementation class AdminRequestServlet
 */
@WebServlet("/AdminUpdateRequestsServlet")
public class AdminUpdateRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUpdateRequestsServlet() {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		String status = request.getParameter("value");
		String emailAddress = request.getParameter("emailAddress");
		
		User user = UserLogic.loadUser(emailAddress);
		Request requestObject = RequestLogic.loadRequest(user);

		switch (status) {
		case "WAITING":
			RequestLogic.postponeRequest(requestObject);
			break;
		case "ACCEPTED":
			RequestLogic.confirmRequest(requestObject);
			break;
		case "DENIED":
			RequestLogic.denyRequest(requestObject);
			break;
		}		
		response.getWriter().print(status);
	}
}

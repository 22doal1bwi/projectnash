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

import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;

/**
 * Servlet implementation class ResetPasswordServlet
 */
@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 boolean passwordResettet = false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPasswordServlet() {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String emailAddress = request.getParameter("emailAddressForNewPassword");
		
		if(UserLogic.emailAlreadyExists(emailAddress)){
			 User user = UserLogic.loadUser(emailAddress);
			 UserLogic.resetPasswort(user);
			 passwordResettet = true;
			 map.put("resetSuccessful", passwordResettet);
		} else {
			map.put("resetSuccessful", passwordResettet);
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

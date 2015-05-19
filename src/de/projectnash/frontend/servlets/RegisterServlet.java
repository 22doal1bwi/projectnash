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

/*
 * 
 * gson Jar File https://code.google.com/p/google-gson/
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1705632952270495401L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map <String, Object> map = new HashMap<String, Object>();
	
		/** Creates a user and saves the result of the operation into a variable. */
		boolean userCreated = UserLogic.createUser(
				req.getParameter("personalId"), 
				req.getParameter("firstName"), 
				req.getParameter("lastName"),
				req.getParameter("emailAddress"),
				req.getParameter("organizationalUnit"),
			    req.getParameter("password"));
		
			map.put("created", userCreated);
			write(resp, map);
	};
	
	private void write(HttpServletResponse resp, Map<String, Object> map) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}

}

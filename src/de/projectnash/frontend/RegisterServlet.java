package de.projectnash.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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
		boolean emailAlreadyExists;
		boolean personalIdAlreadyExists; 
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String organizationalUnit = req.getParameter("organizationalUnit");
		System.out.println("TEST" + req.getParameter("personalId"));
		String personalId = req.getParameter("personalId");
		String emailAddress = req.getParameter("emailAddress");
		String password = req.getParameter("password");
		
		emailAlreadyExists = UserLogic.emailAlreadyExists(emailAddress);
		personalIdAlreadyExists = UserLogic.personalIdAlreadyExists(personalId);
		System.out.println(emailAlreadyExists);
		System.out.println(personalIdAlreadyExists);

		if (emailAlreadyExists) {
			map.put("emailAlreadyExists", emailAlreadyExists);
		
			write(resp, map);

		} else if (personalIdAlreadyExists) {	
			map.put("personalIdAlreadyExists", personalIdAlreadyExists);
			write(resp, map);

		} else {
			map.put("emailAlreadyExists", emailAlreadyExists);
			map.put("personalIdAlreadyExists", personalIdAlreadyExists);	
			write(resp, map);
//			UserLogic.createUser(personalId, firstName, lastName, emailAddress,
//					organizationalUnit, password);
			
//			resp.sendRedirect("login.html");

		}

	};
	
	private void write(HttpServletResponse resp, Map<String, Object> map) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}

}

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
 * Servlet for register.jsp
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1705632952270495401L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean userCreated = false;
		if(!hasMutatedVowels(req.getParameter("emailAddress"))){
		
		boolean personalIdAlreadyExists = UserLogic.personalIdAlreadyExists(req.getParameter("personalId"));
		boolean emailAddressAlreadyExists = UserLogic.emailAlreadyExists(req.getParameter("emailAddress"));
		
			if (!personalIdAlreadyExists && !emailAddressAlreadyExists) {
			/* Creates a user and saves the result of the operation into a variable. */
			userCreated = UserLogic.createUser(req.getParameter("personalId"),
					req.getParameter("firstName"),
					req.getParameter("lastName"),
					req.getParameter("emailAddress"),
					req.getParameter("organizationalUnit"),
					req.getParameter("password"));

			} else if (personalIdAlreadyExists) {
				map.put("personalIdAlreadyExists", true);						
			} else if (emailAddressAlreadyExists){
				map.put("emailAddressAlreadyExists", true);	
			}
			map.put("created", userCreated);
			write(resp, map);
	   } else {
		   map.put("created", userCreated);
		   write(resp, map);
	   }
	}

	private void write(HttpServletResponse resp, Map<String, Object> map) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}
	
	/**
	 * Checks if the e-mail address contains any mutated vowels.
	 * @param emailAddress The {@link String} that represents the entered e-mail address of the {@link User}.
	 * @return The {@link Boolean} that describes if the {@link String} contains any mutated vowels.
	 */
	private boolean hasMutatedVowels(String emailAddress){
		if(emailAddress.contains("ä") || emailAddress.contains("ö") || emailAddress.contains("ü") || emailAddress.contains("ß")){
			return true;
		}
		return false;
	}
}
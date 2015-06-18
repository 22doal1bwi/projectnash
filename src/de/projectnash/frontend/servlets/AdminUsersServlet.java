package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.projectnash.application.UserLogic;
import de.projectnash.application.util.UserObjectTable;
import de.projectnash.entities.User;

/**
 * Servlet implementation class AdminUserServlet
 */
@WebServlet("/AdminUsersServlet")
public class AdminUsersServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonArray allUsersInJson = new JsonArray();		
		List<User> allUsers = UserLogic.getAllUsers();	
		JsonObject jsonResponse = new JsonObject();		
		
		allUsers.forEach(user -> {
			if (!user.isAdmin()) {
				if(user.getCertificate() != null) {
					UserObjectTable distinctUserData = new UserObjectTable(
					user.getCertificate().getExpirationDate(),
					user.getFirstName(), 
					user.getLastName(), 
					user.getDepartment(), 
					user.getPersonalId(), 
					user.getEmailAddress(),					
					user.getCertificate().getCertificateStatus(),
					UserLogic.hasSession(user));
					
					JsonObject distinctUserDataInJson = (JsonObject) new Gson().toJsonTree(distinctUserData);
					allUsersInJson.add(distinctUserDataInJson);
				} else {
					UserObjectTable distinctUserData = new UserObjectTable(
					user.getFirstName(), 
					user.getLastName(), 
					user.getDepartment(), 
					user.getPersonalId(), 
					user.getEmailAddress(),
					UserLogic.hasSession(user));
					
					JsonObject distinctUserDataInJson = (JsonObject) new Gson().toJsonTree(distinctUserData);
					allUsersInJson.add(distinctUserDataInJson);
				}
			}
		});
		
		jsonResponse.add("data", allUsersInJson);
		jsonResponse.addProperty("sEcho", 1);
		jsonResponse.addProperty("iTotalRecords", allUsers.size());
		jsonResponse.addProperty("iTotalDisplayRecords", allUsers.size());
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
}
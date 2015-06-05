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
@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserServlet() {
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JsonArray userObjects = new JsonArray();		
		List<User> userList = UserLogic.getAllUsers();			
		JsonObject JsonResponse = new JsonObject();		
		
		userList.forEach(userObject -> {
			UserObjectTable uot = new UserObjectTable(
					userObject.getCertificate().getExpirationDate(),
					userObject.getFirstName(), 
					userObject.getLastName(), 
					userObject.getDepartment(), 
					userObject.getPersonalId(), 
					userObject.getEmailAddress(),					
					userObject.getCertificate().getCertificateStatus());
			JsonObject jsonUot = (JsonObject) new Gson().toJsonTree(uot);
			userObjects.add(jsonUot);
		});
		
		JsonResponse.add("data", userObjects);
		JsonResponse.addProperty("sEcho", 1);
		JsonResponse.addProperty("iTotalRecords", userList.size());
		JsonResponse.addProperty("iTotalDisplayRecords", userList.size());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JsonResponse.toString());
	}
}

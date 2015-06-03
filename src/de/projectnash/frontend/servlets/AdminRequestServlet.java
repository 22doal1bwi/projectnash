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

import de.projectnash.application.RequestLogic;
import de.projectnash.application.util.RequestObjectTable;
import de.projectnash.entities.Request;

/**
 * Servlet implementation class AdminRequestServlet
 */
@WebServlet("/AdminRequestServlet")
public class AdminRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRequestServlet() {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JsonArray requestObjects = new JsonArray();		
		List<Request> requestList = RequestLogic.loadAllRequests();				
		JsonObject JsonResponse = new JsonObject();		
		
		requestList.forEach(requestObject -> {
			RequestObjectTable rot = new RequestObjectTable(
					requestObject.getCreationDate(),
					requestObject.getUser().getFirstName(), 
					requestObject.getUser().getLastName(), 
					requestObject.getUser().getDepartment(), 
					requestObject.getUser().getPersonalId(), 
					requestObject.getUser().getEmailAddress(),					
					requestObject.getRequestStatus());
			JsonObject jsonRot = (JsonObject) new Gson().toJsonTree(rot);
			requestObjects.add(jsonRot);
		});
		JsonResponse.add("data", requestObjects);
		JsonResponse.addProperty("sEcho", 1);
		JsonResponse.addProperty("iTotalRecords", requestList.size());
		JsonResponse.addProperty("iTotalDisplayRecords", requestList.size());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JsonResponse.toString());
	}
}
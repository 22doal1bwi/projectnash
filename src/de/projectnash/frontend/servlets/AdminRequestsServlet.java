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
@WebServlet("/AdminRequestsServlet")
public class AdminRequestsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRequestsServlet() {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JsonArray allRequestsInJson = new JsonArray();		
		List<Request> allRequests = RequestLogic.loadAllRequests();				
		JsonObject jsonResponse = new JsonObject();		
		
		allRequests.forEach(certificateRequest -> {
			RequestObjectTable distinctRequestData = new RequestObjectTable(
					certificateRequest.getCreationDate(),
					certificateRequest.getUser().getFirstName(), 
					certificateRequest.getUser().getLastName(), 
					certificateRequest.getUser().getDepartment(), 
					certificateRequest.getUser().getPersonalId(), 
					certificateRequest.getUser().getEmailAddress(),					
					certificateRequest.getRequestStatus());
			JsonObject distinctRequestDataInJson = (JsonObject) new Gson().toJsonTree(distinctRequestData);
			allRequestsInJson.add(distinctRequestDataInJson);
		});
		
		jsonResponse.add("data", allRequestsInJson);
		jsonResponse.addProperty("sEcho", 1);
		jsonResponse.addProperty("iTotalRecords", allRequests.size());
		jsonResponse.addProperty("iTotalDisplayRecords", allRequests.size());
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
}
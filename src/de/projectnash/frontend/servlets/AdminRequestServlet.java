package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;


import de.projectnash.application.RequestLogic;
import de.projectnash.application.util.RequestObjectTable;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

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
		
	//	String sessionIdStatus = SessionController.checkForSessionId(request,
	//			response);
		
    //  User user = SessionLogic.loadSession(sessionIdStatus).getUser();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<RequestObjectTable> requestObjects = new ArrayList<>();
		
		List<Request> requestList = RequestLogic.loadAllRequests();
		
		requestList.forEach(requestObject -> {
//			User requestedUser = new User(
//					requestObject.getUser().getFirstName(),
//					requestObject.getUser().getLastName(),
//					requestObject.getUser().getDepartment(),
//					requestObject.getUser().getPersonalId(),
//					requestObject.getUser().getEmailAddress());
//
//			Request toMap = new Request (requestedUser, requestObject.getCreationDate());	
//					
//			map.put("" + requestObject.getUser().getPersonalId(), toMap);
			
			RequestObjectTable rot = new RequestObjectTable(
					  requestObject.getUser().getFirstName(), 
					  requestObject.getUser().getLastName(), 
					  requestObject.getUser().getDepartment(), 
					  requestObject.getUser().getPersonalId(), 
					  requestObject.getUser().getEmailAddress(), 
					  requestObject.getCreationDate(), 
					  requestObject.getRequestStatus());
			requestObjects.add(rot);
		});
		
		write(response, requestObjects);
		
	}

	private void write(HttpServletResponse resp, List<RequestObjectTable> requestObjectTable)
			throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(requestObjectTable));
	}
}

package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.projectnash.application.RequestLogic;
import de.projectnash.application.util.RequestObjectTable;
import de.projectnash.application.util.RequestStatus;
import de.projectnash.entities.Request;

/**
 * Servlet implementation class AdminRequestServlet
 */
@WebServlet("/AdminUpdateServlet")
public class AdminUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUpdateServlet() {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("personalId"));
		int columnPosition = Integer.parseInt(request.getParameter("columnPosition"));
		String value = request.getParameter("value");
		
		
		List<Request> requestList = RequestLogic.loadAllRequests();		
		
		
		for(Request Request : requestList){
			Request requestObject = Request;
			if (requestObject.getUser().getPersonalId() == id){
			
			switch(columnPosition)
			{			case 0:
							requestObject.getUser().setFirstName(value);
							break;
						
						case 1:
							requestObject.getUser().setLastName(value);
							break;
							
						case 2:
							requestObject.getUser().setDepartment(value);
							break;
						
						case 3:
							break;
							
						case 4:
							requestObject.getUser().setEmailAddress(value);
							break;
							
						case 5:
							break;
							
						case 6:
							if(value == "WAITING"){
							requestObject.setRequestStatus(RequestStatus.WAITING);
							break;}
							else if(value == "DENIED"){
								requestObject.setRequestStatus(RequestStatus.DENIED);
								break;}
							else if(value == "ACCEPTED"){
								requestObject.setRequestStatus(RequestStatus.ACCEPTED);
								break;}
							else
							break;}
					
		} response.getWriter().print(value);
			}}}
			
			
		

package de.projectnash.frontend.controllers;

import java.util.List;

import de.projectnash.application.RequestLogic;
import de.projectnash.application.SessionLogic;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

/**
 * Controller for {@link Request}s.
 * 
 * @author Marius Boepple
 *
 */
public class RequestController {
	
	private Request request;
	private User user;

	public RequestController(String sessionId) {
		user = SessionLogic.loadSession(sessionId).getUser();
		request = RequestLogic.loadRequest(user);
	}
	
	public List<Request> getAllRequests(){
		return RequestLogic.loadAllRequests();
	}

	public String getRequestStatus() {
		return RequestLogic.getRequestStatus(user);
	}
	
	public boolean denyRequest() {
		return RequestLogic.denyRequest(request);
	}
	
	public boolean confirmRequest() {
		return RequestLogic.confirmRequest(request);
	}
	
	
}

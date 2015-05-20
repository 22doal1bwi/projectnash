package de.projectnash.frontend.controllers;

import java.util.List;

import de.projectnash.application.RequestLogic;
import de.projectnash.application.SessionLogic;
import de.projectnash.entities.Request;

/**
 * Controller for {@link Request}s in administrator section.
 * 
 * @author Marius Boepple
 *
 */
public class RequestController {
	
	private Request request;

	public RequestController(String sessionId) {
		request = RequestLogic.loadRequest(SessionLogic.loadSession(sessionId).getUser());
	}
	
	public List<Request> getAllRequests(){
		return RequestLogic.loadAllRequests();
	}
	
}

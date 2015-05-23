package de.projectnash.frontend.controllers;

import java.util.List;

import de.projectnash.application.RequestLogic;
import de.projectnash.entities.Request;

/**
 * Controller for {@link Request}s.
 * 
 * @author Marius Boepple
 *
 */
public class RequestController {

	public RequestController() {
	}
	
	public List<Request> getAllRequests(){
		return RequestLogic.loadAllRequests();
	}
	
	public int getNumberOfRequests() {
		return RequestLogic.getNumberOfRequests();
	}
	
	public int getNumberOfWaitingRequests() {
		return RequestLogic.getNumberOfWaitingRequests();
	}
	
}

package de.projectnash.application;

import java.util.List;

import de.projectnash.databackend.RequestPersistenceService;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

public class RequestLogic {

	public static boolean createRequest(User user) {
		try {
			Request request = new Request(user);
			RequestPersistenceService.storeRequest(request);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeRequest(User user) {
		try {
			RequestPersistenceService.removeRequest(RequestPersistenceService
					.loadRequest(user));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Request loadRequest(User user) {
		return RequestPersistenceService.loadRequest(user);
	}

	public static List<Request> loadAllRequests(){
		return RequestPersistenceService.loadAllRequests();
	}
	
	public static boolean hasRequest(User user) {
		return RequestPersistenceService.requestExists(user);
	}

	public static boolean confirmRequest(User user) {
		user.setAllowedToDownload(true);
		return removeRequest(user);
	}

	public static boolean denyRequest(User user) {
		return removeRequest(user);
	}

}

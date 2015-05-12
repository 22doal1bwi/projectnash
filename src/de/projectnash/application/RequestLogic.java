package de.projectnash.application;

import de.projectnash.databackend.RequestPersistenceService;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

public class RequestLogic {

	public static boolean createRequest(User user, String reqId) {
		try {
			Request request = new Request(user, reqId);
			RequestPersistenceService.storeRequest(request);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeRequest(String reqId) {
		try {
			RequestPersistenceService.removeRequest(RequestPersistenceService
					.loadRequest(reqId));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Request loadRequest(String reqId) {
		return RequestPersistenceService.loadRequest(reqId);
	}

	public static boolean checkRequest(User user) {
		return RequestPersistenceService.requestExists(user);
	}

	public static boolean confirmRequest(String reqId) {
		return removeRequest(reqId);
	}

	public static boolean denyRequest(String reqId) {
		return removeRequest(reqId);
	}

}

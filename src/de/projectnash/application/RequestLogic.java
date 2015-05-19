package de.projectnash.application;

import java.util.Date;
import java.util.List;

import de.projectnash.databackend.RequestPersistenceService;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

/**
 * This class provides all logic methods in {@link Request} context.
 * @author Marius Boepple, Silvio D'Alessandro
 *
 */
public class RequestLogic {

	/**
	 * Creates an {@link User} via {@link RequestPersistenceService}.
	 * @param user The {@link User} that the {@link Request} belongs to. 
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean createRequest(User user) {
		try {
			Request request = new Request(user, new Date());
			RequestPersistenceService.storeRequest(request);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes an {@link User} via {@link RequestPersistenceService}.
	 * @param user The {@link User} that the {@link Request} belongs to.
	 * @return The {@link Boolean} that describes if the process was succesful.
	 */
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

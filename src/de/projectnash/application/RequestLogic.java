package de.projectnash.application;

import java.util.Date;
import java.util.List;

import de.projectnash.application.util.RequestStatus;
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
	 * 
	 * @param user The {@link User} that the {@link Request} belongs to. 
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean createRequest(User user) {
		try {
			Request request = new Request(user, new Date());
			RequestPersistenceService.storeRequest(request);
			LogLogic.createLog("Antrag wurde erfolgreich erstellt", user.getEmailAddress());
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Antrag konnte nicht erstellt werden", user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Removes an {@link User} via {@link RequestPersistenceService}.
	 * 
	 * @param user The {@link User} that the {@link Request} belongs to.
	 * @return The {@link Boolean} that describes if the process was succesful.
	 */
	public static boolean removeRequest(User user) {
		try {
			RequestPersistenceService.removeRequest(RequestPersistenceService
					.loadRequest(user));
			LogLogic.createLog("Antrag wurde erfolgreich aus der Datenbank entfernt", user.getEmailAddress());
			return true;
		} catch (Exception e) {
			LogLogic.createLog("Antrag konnte nicht aus der Datenbank entfernt werden", user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Loads a {@link Request} via {@link RequestPersistenceService} from the database.
	 * 
	 * @param user The {@link User} that belongs to the {@link Request}.
	 * @return The {@link Request} for the specified {@link User}.
	 */
	public static Request loadRequest(User user) {
		return RequestPersistenceService.loadRequest(user);
	}

	/**
	 * Loads all {@link Request}s via {@link RequestPersistenceService} from the database.
	 * 
	 * @return A {@link List} that contains all {@link Request}s.
	 */
	public static List<Request> loadAllRequests(){
		return RequestPersistenceService.loadAllRequests();
	}
	
	public static boolean requestExists(User user) {
		return RequestPersistenceService.requestExists(user);
	}
	
	public static String getRequestStatus(User user) {
		try {
			return RequestPersistenceService.loadRequest(user).getRequestStatus().name();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Allows the {@link User} to download his {@link Certificate} and removes the {@link Request} afterwards.
	 *
	 * @param request The {@link Request} that should be confirmed.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean confirmRequest(Request request) {
		try {
			request.setRequestStatus(RequestStatus.ACCEPTED);
			RequestPersistenceService.updateRequest(request);
			LogLogic.createLog("Antrag wurde bestätigt", request.getUser().getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Antrag konnte nicht bestätigt werden", request.getUser().getEmailAddress());
			return false;
		}		
	}

	/**
	 * Denies the {@link Request} ask by the {@link User}.
	 * 
	 * @param request The {@link Request} that should be denied.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean denyRequest(Request request) {
		try {
			request.setRequestStatus(RequestStatus.DENIED);
			RequestPersistenceService.updateRequest(request);
			LogLogic.createLog("Antrag wurde abgelehnt", request.getUser().getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Antrag konnte nicht abgelehnt werden", request.getUser().getEmailAddress());
			return false;
		}
	}

}

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
			LogLogic.createLog("Antrag konnte nicht aus der Datenbank entfertn werden", user.getEmailAddress());
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
	
	/**
	 * Checks if the {@link User} already has a {@link Request}.
	 * 
	 * @param user The {@link User} whose {@link Request} will be checked.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean hasRequest(User user) {
		return RequestPersistenceService.requestExists(user);
	}

	/**
	 * Allows the {@link User} to download his {@link Certificate} and removes the {@link Request} afterwards.
	 * 
	 * @param user The {@link User} that will be allowed.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean confirmRequest(User user) {
		user.setAllowedToDownload(true);
		return removeRequest(user);
	}

	/**
	 * Denies the {@link Request} ask by the {@link User}.
	 * 
	 * @param user The {@link User} whose {@link Request} will be denied.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean denyRequest(User user) {
		return removeRequest(user);
	}

}

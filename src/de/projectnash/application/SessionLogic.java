package de.projectnash.application;

import de.projectnash.databackend.SessionPersistenceService;
import de.projectnash.entities.Session;
import de.projectnash.entities.User;

/**
 * This class provides all methods to handle the {@link Session}.
 * 
 * @author Marius Boepple, Silvio D'Alessandro
 *
 */
public class SessionLogic {

	/**
	 * Creates a {@link Session} and saves in to the database via {@link SessionPersistenceService}.
	 * 
	 * @param user The {@link User} that represents the {@link User} of the {@link Session}.
	 * @param ssnId The {@link String} that represents the ssnId of the {@link Session}.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean createSession(User user, String ssnId) {
		try {
			Session session = new Session(user, ssnId);
			SessionPersistenceService.storeSession(session);
			LogLogic.createLog("Die Session wurde erfolgreich in der Datenbank gespeichert", user.getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Die Session konnte nicht in der Datenbank gespeichert werden", user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Removes a {@link Session} from the database via {@link SessionPersistenceService}.
	 * 
	 * @param ssnId The {@link String} that represents the ssnId of the {@link Session}.
	 */
	public static void removeSession(String ssnId) {
		SessionPersistenceService.removeSession(SessionPersistenceService.loadSession(ssnId));
	}

	/**
	 * Loads a {@link Session} specified by the ssnId.
	 * 
	 * @param ssnId The {@link String} on basis which the {@link Session} will be loaded.
	 * @return The {@link Session} specified by the ssnId.
	 */
	public static Session loadSession(String ssnId) {
		return SessionPersistenceService.loadSession(ssnId);
	}

	/**
	 * Checks if the {@link Session} already exists in the database via {@link SessionPersistenceService}.
	 * 
	 * @param ssnId The {@link Session}ID.
	 * @return True if the {@link Session} exists.
	 */
	public static boolean checkSession(String ssnId) {
		return SessionPersistenceService.sessionExists(ssnId);
	}

	/**
	 * Checks if a {@link Session} for a specific {@link User} exists in the database via {@link SessionPersistenceService}.
	 * 
	 * @param user The {@link User} which will be checked.
	 * @return True if the {@link Session} exists.
	 */
	public static boolean hasSession(User user) {
		return SessionPersistenceService.sessionExists(user);
	}
}

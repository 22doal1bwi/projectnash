package de.projectnash.application;

import de.projectnash.databackend.SessionPersistenceService;
import de.projectnash.entities.Session;
import de.projectnash.entities.User;

/**
 * This class provides all methods to handle the {@link Session}.
 * 
 * @author Marius Boepple
 *
 */
public class SessionLogic {
	
	public static boolean createSession(User user, String ssnId) {
		
		Session session = new Session(user, ssnId);
		SessionPersistenceService.storeSession(session);
		
		return false;
	}

	public static void removeSession(String ssnId){
		SessionPersistenceService.removeSession(SessionPersistenceService.loadSession(ssnId));
	}
	
	public static Session loadSession(String ssnId){
		return SessionPersistenceService.loadSession(ssnId);
	}
	
	public static boolean checkSession(String ssnId){
		return SessionPersistenceService.sessionExists(ssnId);
	}
	
}

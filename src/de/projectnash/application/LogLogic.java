package de.projectnash.application;

import de.projectnash.databackend.LogPersistenceService;
import de.projectnash.entities.Log;

/**
 * 
 * This class provides all methods to handle the {@link Log}.
 * 
 * @author Artur Ermisch, Silvio D'Alessandro
 *
 */
public class LogLogic {

	/**
	 * Creates a {@link Log} and saves in to the database via {@link LogPersistenceService}.
	 * 
	 * @param message The {@link String} that represents the message of the {@link Log}.
	 * @param userEmailAddress The {@link String} that represents the email address of the {@link User}.
	 */
	public static void createLog(String message, String userEmailAddress) {
		Log tempLog = new Log(message, userEmailAddress);
		LogPersistenceService.storeLog(tempLog);
	}
	
	/**
	 * Loads a specified {@link Log} via {@link LogPersistenceService}.
	 * 
	 * @param logId The {@link Integer} on basis which the {@link Log} will be loaded.
	 * @return The {@link Log} with the specified logId.
	 */
	public static Log loadLog(int logId){
		return LogPersistenceService.loadLog(logId);	
	}
}

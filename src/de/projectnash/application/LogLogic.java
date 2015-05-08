package de.projectnash.application;

import de.projectnash.databackend.LogPersistenceService;
import de.projectnash.entities.Log;

/**
 * 
 * This class provides all methods to handle the {@link Log}.
 * 
 * @author Artur Ermisch
 *
 */
public class LogLogic {

	public static void createLog(String message, String userEmailAddress) {
		
		Log tempLog = new Log(message, userEmailAddress);

		LogPersistenceService.storeLog(tempLog);
	}
	
	public static Log loadLog(int logId){
		return LogPersistenceService.loadLog(logId);	
	}
	
}

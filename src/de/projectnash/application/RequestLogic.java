package de.projectnash.application;

import java.util.Date;
import java.util.List;

import de.projectnash.application.util.EmailSubject;
import de.projectnash.application.util.EmailUtility;
import de.projectnash.application.util.RequestStatus;
import de.projectnash.databackend.RequestPersistenceService;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

/**
 * This class provides all logic methods in {@link Request} context.
 * 
 * @author Marius Boepple, Silvio D'Alessandro
 *
 */
public class RequestLogic {

	/**
	 * Creates an {@link Request} via {@link RequestPersistenceService}.
	 * 
	 * @param user
	 *            The {@link User} that the {@link Request} belongs to.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean createRequest(User user) {
		try {
			Request request = new Request(user, new Date());
			RequestPersistenceService.storeRequest(request);
			LogLogic.createLog("Antrag wurde erfolgreich erstellt",
					user.getEmailAddress());
			EmailUtility.sendMail(user, EmailSubject.REQUEST_CREATE);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Antrag konnte nicht erstellt werden",
					user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Removes an {@link User} via {@link RequestPersistenceService}.
	 * 
	 * @param user
	 *            The {@link User} that the {@link Request} belongs to.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean removeRequest(User user) {
		try {
			RequestPersistenceService.removeRequest(RequestPersistenceService
					.loadRequest(user));
			LogLogic.createLog(
					"Antrag wurde erfolgreich aus der Datenbank entfernt",
					user.getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog(
					"Antrag konnte nicht aus der Datenbank entfernt werden",
					user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Loads a {@link Request} via {@link RequestPersistenceService}.
	 * 
	 * @param user
	 *            The {@link User} that belongs to the {@link Request}.
	 * @return The {@link Request} for the specified {@link User}.
	 */
	public static Request loadRequest(User user) {
		return RequestPersistenceService.loadRequest(user);
	}

	/**
	 * Updates a {@link Request} via {@link RequestPersistenceService}.
	 * 
	 * @param request
	 *            The {@link Request} that should be updated.
	 */
	public static void updateRequest(Request request) {
		RequestPersistenceService.updateRequest(request);
	}

	/**
	 * Loads all {@link Request}s via {@link RequestPersistenceService}.
	 * 
	 * @return A {@link List} that contains all {@link Request}s.
	 */
	public static List<Request> loadAllRequests() {
		return RequestPersistenceService.loadAllRequests();
	}

	/**
	 * Method which checks if a {@link User} has a {@link Request}.
	 * 
	 * @param user
	 *            The {@link User} whose {@link Request} will be checked.
	 * @return True if the {@link User} has a {@link Request}.
	 */
	public static boolean requestExists(User user) {
		return RequestPersistenceService.requestExists(user);
	}

	/**
	 * Method which returns the {@link RequestStatus} of the {@link Request}.
	 * 
	 * @param user
	 *            The {@link User} that belongs to the {@link Request}.
	 * @return The {@link RequestStatus}.
	 */
	public static RequestStatus getRequestStatus(User user) {
		return RequestPersistenceService.loadRequest(user)
				.getRequestStatus();
	}

	/**
	 * Allows the {@link User} to download his {@link Certificate} and removes
	 * the {@link Request} afterwards.
	 *
	 * @param request
	 *            The {@link Request} that should be confirmed.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean confirmRequest(Request request) {
		try {
			request.setRequestStatus(RequestStatus.ACCEPTED);
			RequestPersistenceService.updateRequest(request);
			LogLogic.createLog("Antrag wurde bestätigt", request.getUser()
					.getEmailAddress());
			EmailUtility.sendMail(request.getUser(),
					EmailSubject.REQUEST_ACCEPT);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Antrag konnte nicht bestätigt werden", request
					.getUser().getEmailAddress());
			return false;
		}
	}

	/**
	 * Denies the {@link Request} asked by the {@link User}.
	 * 
	 * @param request
	 *            The {@link Request} that should be denied.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean denyRequest(Request request) {
		try {
			request.setRequestStatus(RequestStatus.DENIED);
			RequestPersistenceService.updateRequest(request);
			LogLogic.createLog("Antrag wurde abgelehnt", request.getUser()
					.getEmailAddress());
			EmailUtility.sendMail(request.getUser(), EmailSubject.REQUEST_DENY);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Antrag konnte nicht abgelehnt werden", request
					.getUser().getEmailAddress());
			return false;
		}
	}

	/**
	 * Sets the {@link Request}s {@link RequestStatus} on 'waiting'.
	 * 
	 * @param request
	 *            The {@link Request} that should be changed.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean postponeRequest(Request request) {
		if (request.getRequestStatus() != RequestStatus.WAITING) {
			try {
				request.setRequestStatus(RequestStatus.WAITING);
				RequestPersistenceService.updateRequest(request);
				LogLogic.createLog("Antrag wurde auf 'wartend' gesetzt",
						request.getUser().getEmailAddress());
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				LogLogic.createLog(
						"Antrag konnte nicht auf 'wartend'gesetzt werden",
						request.getUser().getEmailAddress());
				return false;
			}
		}
		return true;
	}

	/**
	 * Method which returns the overall number of existing {@link Request}s in
	 * the database.
	 * 
	 * @return Number of {@link Request}s.
	 */
	public static int getNumberOfRequests() {
		return RequestPersistenceService.loadAllRequests().size();
	}
	
	/**
	 * Method which returns the overall number of {@link Request}s in
	 * the database with a certain {@link CertificateStatus}.
	 * 
	 * @param RequestStatus
	 * @return Number of {@link Request}s with certain {@link CertificateStatus}.
	 */
	public static int getNumberOfRequests(RequestStatus RequestStatus) {
		return (int) RequestPersistenceService
				.loadAllRequests()
				.stream()
				.filter(request -> request.getRequestStatus() == RequestStatus)
				.count();
	}

}

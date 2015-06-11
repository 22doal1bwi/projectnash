package de.projectnash.application;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.mail.MessagingException;

import de.projectnash.application.util.EmailSubject;
import de.projectnash.application.util.EmailUtility;
import de.projectnash.databackend.UserPersistenceService;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.Request;
import de.projectnash.entities.User;

/**
 * 
 * This class provides all methods to handle the {@link User}.
 * 
 * @author Marius Boepple, Jonathan Schlotz, Silvio D'Alessandro
 *
 */
public class UserLogic {

	/**
	 * Creates an {@link User} and stores it to the database via
	 * {@link UserPersistenceService}.
	 * 
	 * @param personalId
	 *            The {@link String} that represents the personalId of the
	 *            {@link User}.
	 * @param firstName
	 *            The {@link String} that represents the first name of the
	 *            {@link User}.
	 * @param lastName
	 *            The {@link String} that represents the last name of the
	 *            {@link User}.
	 * @param emailAddress
	 *            The {@link String} that represents the email address of the
	 *            {@link User}.
	 * @param organizationunit
	 *            The {@link String} that represents the organization unit of
	 *            the {@link User}.
	 * @param password
	 *            The {@link String} that represents the password of the
	 *            {@link User}.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean createUser(String personalId, String firstName,
			String lastName, String emailAddress, String organizationunit,
			String password) {

		try {
			User tempUser = new User(Integer.parseInt(personalId), firstName,
					lastName, organizationunit, emailAddress, password);

			/** save the user to the database. */
			UserPersistenceService.storeUser(tempUser);
			LogLogic.createLog(
					"Der Benutzer wurde erfolgreich in der Datenbank gespeichert",
					emailAddress);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog(
					"Der Benutzer konnte nicht in der Datenbank gespeichert werden",
					emailAddress);
			return false;
		}
	}

	/**
	 * Creates a {@link Certificate} for the {@link User} which has an accepted
	 * {@link Request} revoking the old {@link Certificate} and removing the {@link Request}.
	 * 
	 * @param user
	 *            The {@link User} whose {@link Request} was accepted.
	 * @param password
	 *            The password for the {@link Certificate}.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean assignCertificate(User user, String password) {
		try {
			if (hasCertificate(user)) {
				if (UserLogic.hasValidCertificate(user)) {
					CertificateLogic
							.revokeCertificate(user,
									"Verlängerung - Zertifikat wurde durch neues ersetzt");
				}
			}

			boolean createdCertificateSuccessful = CertificateLogic
					.createCertificate(user, password);
			boolean removedRequestSuccessful = RequestLogic.removeRequest(user);

			if (!createdCertificateSuccessful || !removedRequestSuccessful) {
				LogLogic.createLog(
						"Dem Benutzer konnte kein Zertifikat erstellt und zugewiesen werden",
						user.getEmailAddress());
				return false;
			}

			LogLogic.createLog(
					"Dem Benutzer wurde ein Zertifikat erstellt und zugewiesen ",
					user.getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog(
					"Dem Benutzer konnte kein Zertifikat erstellt und zugewiesen werden",
					user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Checks if the {@link User} already has a {@link Request}.
	 * 
	 * @param user
	 *            The {@link User} whose {@link Request} will be checked.
	 * @return True if the {@link User} has a {@link Request}.
	 */
	public static boolean hasRequest(User user) {
		return RequestLogic.requestExists(user);
	}

	/**
	 * Checks if the {@link User} has a {@link Session}.
	 * 
	 * @param user
	 *            The {@link User} which will be checked.
	 * @return True if the {@link User} has a {@link Session}.
	 */
	public static boolean hasSession(User user) {
		return SessionLogic.hasSession(user);
	}

	/**
	 * Changes the password of an {@link User}.
	 * 
	 * @param user
	 *            The {@link User} whose password will be changed.
	 * @param oldPassword
	 *            The {@link String} that represents the old password.
	 * @param newPassword
	 *            The {@link String} that represents the new password.
	 * @return The {@link Boolean} that describes if the password has been
	 *         changed.
	 */
	public static boolean changePassword(User user, String oldPassword,
			String newPassword) {
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			UserPersistenceService.updateUser(user);
			LogLogic.createLog(
					"Das Passwort des Benutzers wurde erfolgreich geändert",
					user.getEmailAddress());
			return true;
		}
		LogLogic.createLog(
				"Das Passwort des Benutzers konnte nicht geändert werden",
				user.getEmailAddress());
		return false;
	}

	/**
	 * Resets the password of the specified {@link User}.
	 * 
	 * @param user
	 *            The {@link User} whose password will be reseted.
	 */
	public static void resetPasswort(User user) throws MessagingException {
		SecureRandom random = new SecureRandom();
		String newPassword = new BigInteger(130, random).toString(32)
				.substring(0, 10);

		user.setPassword(newPassword);
		UserPersistenceService.updateUser(user);

		EmailUtility.sendMail(user, EmailSubject.PASSWORD_RESET);
	}

	/**
	 * Loads an {@link User} specified by the entered {@link Session}s ssnId.
	 * 
	 * @param ssnId
	 *            The {@link String} that represents the ssnId of the
	 *            {@link Session}.
	 * @return The {@link User} specified by the ssnId.
	 */
	public static User loadUserBySession(String ssnId) {
		return SessionLogic.loadSession(ssnId).getUser();
	}

	/**
	 * Loads an {@link User} specified by the entered email address.
	 * 
	 * @param eMailAddress
	 *            The {@link String} that represents the email address of the
	 *            {@link User}.
	 * @return The {@link User} specified by the email address.
	 */
	public static User loadUser(String eMailAddress) {
		return UserPersistenceService.loadUser(eMailAddress);
	}

	/**
	 * Loads all {@link User}s via {@link UserPersistenceService}.
	 * 
	 * @return A {@link List} with all {@link User}s in the database.
	 */
	public static List<User> getAllUsers() {
		return UserPersistenceService.loadAllUsers();
	}

	/**
	 * Updates an {@link User} via {@link UserPersistenceService}.
	 * 
	 * @param user
	 *            The {@link User} which should be updated.
	 */
	public static void updateUser(User user) {
		UserPersistenceService.updateUser(user);
	}

	/**
	 * Removes a {@link User} via {@link UserPersistenceService}.
	 * 
	 * @param user
	 *            The {@link User} which should be removed.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean removeUser(User user) {
		try {
			boolean removeRequestSuccessful = true;
			boolean removeUsersCertificatesSuccessful = true;

			if (UserLogic.hasRequest(user)) {
				removeRequestSuccessful = RequestLogic.removeRequest(user);
			}

			UserPersistenceService.removeUser(user);

			if (UserLogic.hasCertificate(user)) {
				removeUsersCertificatesSuccessful = removeUsersCertificates(user);
			} 

			if (!removeRequestSuccessful || !removeUsersCertificatesSuccessful) {
				LogLogic.createLog(
						"User konnte nicht aus der Datenbank entfernt werden",
						user.getEmailAddress());
				return false;
			}

			LogLogic.createLog(
					"User wurde erfolgreich aus der Datenbank entfernt",
					user.getEmailAddress());
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog(
					"User konnte nicht aus der Datenbank entfernt werden",
					user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Removes all {@link Certificate}s of {@link User}.
	 * 
	 * @param user
	 *            {@link User} which {@link Certificate}s should be removed.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean removeUsersCertificates(User user) {
		return CertificateLogic.removeAllCertificatesOfUser(user
				.getEmailAddress());
	}

	/**
	 * Method which checks if a {@link User} with a specific email address
	 * already exists.
	 * 
	 * @param emailAddress
	 *            The email address of the {@link User} you want to check for
	 *            existence.
	 * @return True if the {@link User}/email address already exists.
	 */
	public static boolean emailAlreadyExists(String emailAddress) {
		return UserPersistenceService.userExists(emailAddress);
	}

	/**
	 * Method which checks if a {@link User} with a specific personal id already
	 * exists.
	 * 
	 * @param personalId
	 *            The personal id of the {@link User} you want to check for
	 *            existence.
	 * @return True if the {@link User}/personal id already exists.
	 */
	public static boolean personalIdAlreadyExists(String personalId) {
		if (personalId != null) {
			if (!personalId.isEmpty()) {
				return UserPersistenceService.userExists(Integer
						.parseInt(personalId));
			}
		}
		return false;
	}

	/**
	 * Method which checks if a {@link User} has a {@link Certificate} (no
	 * matter which {@link CertificateStatus}).
	 * 
	 * @param user
	 *            The {@link User} which should be checked.
	 * @return True if the {@link User} has a {@link Certificate} (no matter
	 *         which {@link CertificateStatus}).
	 */
	public static boolean hasCertificate(User user) {
		return user.getCertificate() != null;
	}

	/**
	 * Method which checks if a {@link User} has a valid {@link Certificate}.
	 * 
	 * @param user
	 *            The {@link User} which should be checked.
	 * @return True if the {@link User} has a valid {@link Certificate}.
	 */
	public static boolean hasValidCertificate(User user) {
		return CertificateLogic.certificateIsValid(user.getCertificate());
	}

	/* G E T T E R */

	public static String getCommonName(User user) {
		return (user.getFirstName() + " " + user.getLastName() + " ("
				+ user.getPersonalId() + ")");
	}

	public static String getFullName(User user) {
		return (user.getFirstName() + " " + user.getLastName());
	}

	public static String getFirstName(User user) {
		return user.getFirstName();
	}

	public static String getLastName(User user) {
		return user.getLastName();
	}

	public static String getEmailAddress(User user) {
		return user.getEmailAddress();
	}

	public static String getDepartment(User user) {
		return user.getDepartment();
	}

	public static int getPersonalId(User user) {
		return user.getPersonalId();
	}

	public static boolean isAdmin(User user) {
		return user.isAdmin();
	}

}

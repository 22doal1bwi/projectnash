package de.projectnash.application;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.projectnash.application.util.CertificateStatus;
import de.projectnash.application.util.CertificateUtility;
import de.projectnash.databackend.CertificatePersistenceService;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.Organization;
import de.projectnash.entities.User;
import de.projectnash.application.util.OpenSSLException;

/**
 * This class provides all methods to handle the {@link Certificate} request,
 * creation, extension and revocation for an specific {@link User}.
 * 
 * @author Marius Boepple
 *
 */
public class CertificateLogic {

	public static void main(String[] args) {

	}

	/**
	 * Creates a {@link Certificate} for the specified {@link User}.
	 * 
	 * @param user
	 *            The {@link User} the {@link Certificate} is created for.
	 * @return The {@link Boolean} which describes if the process was
	 *         successful.
	 * @throws ParseException
	 * @throws OpenSSLException
	 * @throws InterruptedException
	 */
	public static boolean createCertificate(User user, String password) {

		Organization organization = new Organization();

		/** create certificate with utility class. */
		try {
			byte[] keyData = CertificateUtility.generatePrivateKey();
			byte[] csrData = CertificateUtility.generateCSR(
					organization.getCountry(), organization.getState(),
					organization.getLocality(), organization.getOrganization(),
					user.getDepartment(), UserLogic.getCommonName(user),
					user.getEmailAddress(), keyData);
			byte[] crtData = CertificateUtility.generateCRT(csrData);
			byte[] p12Data = CertificateUtility.generatePKCS12(crtData,
					keyData, password);

			String subjectData = CertificateUtility.getCRTdata(crtData,
					"-subject");
			String datesData = CertificateUtility.getCRTdata(crtData, "-dates");

			/** Format date String to Date() object. */
			DateFormat formatter = new SimpleDateFormat(
					"MMM dd HH:mm:ss yyyy z", Locale.ENGLISH);

			user.setCertificate(new Certificate(p12Data, crtData,
					subjectData.split("/")[1].split("=")[1],
					subjectData.split("/")[2].split("=")[1],
					subjectData.split("/")[3].split("=")[1],
					subjectData.split("/")[4].split("=")[1],
					subjectData.split("/")[5].split("=")[1],
					UserLogic.getCommonName(user), // utf8 workaround
					// common name from csr (not utf8):
					// subjectData.split("/")[6].split("=")[1],
					subjectData.split("/")[7].split("=")[1].replace("\n", ""),
					formatter.parse(datesData.split("notBefore=")[1]
							.split("notAfter=")[0]), formatter.parse(datesData
							.split("notBefore=")[1].split("notAfter=")[1]),
					CertificateStatus.ACTIVE, false));

			/** save certificate to database. */
			UserLogic.updateUser(user);
			LogLogic.createLog(
					"Zertifikat wurde erfolgreich in der Datenbank gespeichert",
					user.getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog(
					"Zertifikat konnte nicht in der Datenbank gespeichert werden",
					user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Loads a {@link Certificate} of a {@link User} over {@link Session}ID.
	 * 
	 * @param ssnId
	 *            {@link Session}ID
	 * @return The {@link Certificate}.
	 */
	public static Certificate loadCertificate(String ssnId) {
		return SessionLogic.loadSession(ssnId).getUser().getCertificate();
	}

	/**
	 * Updates an {@link Certificate} via {@link CertificatePersistenceService}.
	 * 
	 * @param certificate
	 *            The {@link Certificate} which should be updated.
	 */
	public static void updateCertificate(Certificate certificate) {
		CertificatePersistenceService.updateCertificate(certificate);
	}

	/**
	 * Removes an {@link Certificate} via {@link CertificatePersistenceService}.
	 * 
	 * @param certificate The {@link Certificate} which should be deleted.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean removeCertificate(Certificate certificate) {
		try {
			CertificatePersistenceService.removeCertificate(certificate);
			LogLogic.createLog(
					"Zertifikat wurde erfolgreich aus der Datenbank entfernt",
					certificate.getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog(
					"Zertifikat konnte nicht aus der Datenbank entfernt werden",
					certificate.getEmailAddress());
			return false;
		}
	}
	
	/**
	 * Removes all {@link Certificate}s of {@link User}.
	 * 
	 * @param emailAddress E-Mail address of {@link User}.
	 * @return The {@link Boolean} that describes if the process was successful.
	 */
	public static boolean removeAllCertificatesOfUser(String emailAddress){
		try {
			CertificatePersistenceService.removeAllCertificatesOfUser(emailAddress);
			LogLogic.createLog(
					"Zertifikate des Users wurden erfolgreich aus der Datenbank entfernt",
					emailAddress);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog(
					"Zertifikate des Users konnten nicht aus der Datenbank entfernt werden",
					emailAddress);
			return false;
		}
	}

	/**
	 * Revokes a {@link Certificate}.
	 * 
	 * @param user
	 *            The {@link User} whose {@link Certificate} should be revoked.
	 * @param revokeReason
	 *            The reason of the revokation.
	 * @return The {@link Boolean} which describes if the process was
	 *         successful.
	 */
	public static boolean revokeCertificate(User user, String revokeReason) {
		try {
			Certificate certificate = user.getCertificate();
			CertificateUtility.revokeCRT(certificate.getCertificateCRT());
			certificate.setCertificateStatus(CertificateStatus.REVOKED);
			certificate.setRevokeReason(revokeReason);
			updateCertificate(certificate);

			LogLogic.createLog("Das Zertifikat wurde widerrufen",
					user.getEmailAddress());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Das Zertifikat konnte nicht widerrufen werden",
					user.getEmailAddress());
			return false;
		}
	}

	/**
	 * Method which checks if the {@link Certificate} is valid.
	 * 
	 * @param certificate
	 *            The {@link Certificate} which should be checked.
	 * @return boolean True if the {@link Certificate} is valid.
	 */
	public static boolean certificateIsValid(Certificate certificate) {
		if (certificate != null) {
			if (certificate.getCertificateStatus() != CertificateStatus.REVOKED
					&& certificate.getExpirationDate().compareTo(new Date()) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method which returns time left for {@link Certificate} in appropriate
	 * time unit.
	 * 
	 * @param certificate
	 *            The {@link Certificate} which should be checked.
	 * @return String The Time which is left in appropriate time unit.
	 * @throws FileNotFoundException
	 */
	public static String getAppropriateTimeLeftForCertificate(
			Certificate certificate) throws FileNotFoundException {
		if (certificate != null) {
			if (getTimeLeftForCertificate(certificate, TimeUnit.DAYS) < 1) {
				if (getTimeLeftForCertificate(certificate, TimeUnit.HOURS) < 1) {
					return getTimeLeftForCertificate(certificate,
							TimeUnit.MINUTES) + " Minuten";
				}
				return getTimeLeftForCertificate(certificate, TimeUnit.HOURS)
						+ " Stunden";
			}
			return getTimeLeftForCertificate(certificate, TimeUnit.DAYS)
					+ " Tage";
		}
		throw new FileNotFoundException("Noch kein Zertifikat vorhanden");
	};

	/**
	 * Method which returns time left for {@link Certificate}.
	 * 
	 * @param certificate
	 *            The {@link Certificate} which should be checked.
	 * @param timeUnit
	 *            TimeUnit.DAYS, TimeUnit.HOURS or TimeUnit.MINUTES
	 * @return number of days, hours or minutes
	 * @throws FileNotFoundException
	 */
	public static int getTimeLeftForCertificate(Certificate certificate,
			TimeUnit timeUnit) throws FileNotFoundException {
		if (certificate != null) {
			long diffInMillies = certificate.getExpirationDate().getTime()
					- new Date().getTime();
			return (int) timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
		}
		throw new FileNotFoundException("Noch kein Zertifikat vorhanden");
	}

	/**
	 * Method which returns the expiration date in appropriate format.
	 * 
	 * @param certificate
	 *            The {@link Certificate} which expiration date should be
	 *            displayed.
	 * @return The expiration date in appropriate format.
	 */
	public static String getExpirationDateForUI(Certificate certificate) {
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm",
				Locale.GERMANY);

		return formatter
				.format(CertificateLogic.getExpirationDate(certificate))
				+ " Uhr";
	}

	/**
	 * Method which returns a {@link List} of all {@link Certificate}s.
	 * 
	 * @return A {@link List} of all {@link Certificate}s.
	 */
	public static List<Certificate> getAllCertificates() {
		return CertificatePersistenceService.loadAllCertificates();
	}

	/**
	 * Method which returns a {@link List} of all {@link Certificate}s with
	 * certain {@link CertificateStatus}.
	 * 
	 * @param CertificateStatus
	 *            {@link CertificateStatus}.
	 * @return A {@link List} of all {@link Certificate}s with this
	 *         {@link CertificateStatus} in the database.
	 */
	public static List<Certificate> getAllCertificates(
			CertificateStatus CertificateStatus) {
		List<Certificate> allCertificates = getAllCertificates();
		List<Certificate> certificates = new ArrayList<Certificate>();
		for (Certificate c : allCertificates) {
			if (c.getCertificateStatus().equals(CertificateStatus)) {
				certificates.add(c);
			}
		}
		return certificates;
	}

	/* G E T T E R */

	public static String getOrganizationalUnit(Certificate certificate) {
		return certificate.getOrganizationalUnit();
	}

	public static String getEmailAddress(Certificate certificate) {
		return certificate.getEmailAddress();
	}

	public static String getOrganizationName(Certificate certificate) {
		return certificate.getOrganizationName();
	}

	public static String getLocalityName(Certificate certificate) {
		return certificate.getLocalityName();
	}

	public static Object getExpirationDate(Certificate certificate) {
		return certificate.getExpirationDate();
	}

	public static String getCommonName(Certificate certificate) {
		return certificate.getCommonName();
	}

	public static String getState(Certificate certificate) {
		return certificate.getState();
	}

	public static String getCountryName(Certificate certificate) {
		return certificate.getCountryName();
	}
}

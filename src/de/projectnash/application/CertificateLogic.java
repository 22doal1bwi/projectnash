package de.projectnash.application;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

		User tempUser = new User(0002, "Artur", "Ermisch", "CI",
				"artur.ermisch@simpleCert.com", "Eierkuchen2");
		try {
			createCertificate(tempUser, "");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a {@link Certificate} for the specified {@link User}.
	 * 
	 * @param user The {@link User} the {@link Certificate} is created for.
	 * @return The {@link Boolean} that describes if the process was successful.
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
			byte[] p12Data = CertificateUtility.generatePKCS12(crtData, keyData, password);
			
			String subjectData = CertificateUtility.getCRTdata(crtData,
					"-subject");
			String datesData = CertificateUtility.getCRTdata(crtData, "-dates");

			/** Format date String to Date() object. */
			DateFormat formatter = new SimpleDateFormat(
					"MMM dd HH:mm:ss yyyy z", Locale.ENGLISH);

			user.setCertificate(
					new Certificate(
					p12Data,
					crtData,
					subjectData.split("/")[1].split("=")[1], subjectData
							.split("/")[2].split("=")[1], subjectData
							.split("/")[3].split("=")[1], subjectData
							.split("/")[4].split("=")[1], subjectData
							.split("/")[5].split("=")[1],
							UserLogic.getCommonName(user), //utf8 workaround
							//common name from csr (not utf8): subjectData.split("/")[6].split("=")[1],
							subjectData
							.split("/")[7].split("=")[1], formatter
							.parse(datesData.split("notBefore=")[1]
									.split("notAfter=")[0]), formatter
							.parse(datesData.split("notBefore=")[1]
									.split("notAfter=")[1]),
							CertificateStatus.ACTIVE));

			/** save certificate to database. */
			UserLogic.updateUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			LogLogic.createLog("Zertifikat konnte nicht in der Datenbank gespeichert werden", user.getEmailAddress());
			return false;
		}
		LogLogic.createLog(
				"Zertifikat wurde erfolgreich in der Datenbank gespeichert", user.getEmailAddress());
		return true;
	}

	// TODO: implement revokeCertificate method
	public static boolean revokeCertificate(User user, String revokeReason) {
		try {
			Certificate certificate = user.getCertificate();
			CertificateUtility.revokeCRT(certificate.getCertificateCRT());
			certificate.setCertificateStatus(CertificateStatus.REVOKED);
			certificate.setRevokeReason(revokeReason);
			CertificatePersistenceService.updateCertificate(certificate);
			
			user.setCertificate(null);
			UserLogic.updateUser(user);
			LogLogic.createLog("Das Zertifikat wurde widerrufen", user.getEmailAddress());
			return true;
		} catch (Exception e) {
			LogLogic.createLog("Das Zertifikat konnte nicht widerrufen werden", user.getEmailAddress());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Method which returns true if {@link Certificate} is valid.
	 * 
	 * @param certificate
	 * @return boolean
	 * @author Marius Boepple
	 */
	public static boolean certificateIsValid(Certificate certificate) {
		if (certificate != null) {
			if (certificate.getExpirationDate().compareTo(new Date()) >= 0) {
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
	 * @return String
	 * @author Marius Boepple, Jonathan Schlotz
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
	 * @param timeUnit
	 *            TimeUnit.DAYS, TimeUnit.HOURS or TimeUnit.MINUTES
	 * @return number of days, hours or minutes
	 * @author Marius Boepple, Jonathan Schlotz
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

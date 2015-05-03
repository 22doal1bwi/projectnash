package de.projectnash.application;

import java.io.IOException;
import de.projectnash.application.util.CertificateUtility;
import de.projectnash.application.util.CertificateUtility.FilePattern;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;

/**
 * This class provides all methods to handle the {@link Certificate} request, creation, extension and revocation for an specific {@link User}.
 * 
 * @author Marius Boepple
 *
 */
public class CertificateHandler {

	/**
	 * Test Utility Class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		User tempUser = new User(0001, "Tobias", "Burger",
				"tobias.burger@simpleCert.com", "DE", "Baden-Wuerttemberg",
				"Stuttgart", "CI", "Eierkuchen4");

		createCertificate(tempUser);
	}

	public static void createCertificate(User user) {

		// get data of user from back-end

		// create Certificate with utility class
		try {
			byte[] keyData = CertificateUtility.generatePrivateKey();
			byte[] csrData = CertificateUtility.generateCSR(
					user.getCountryName(),
					user.getState(),
					user.getLocalityName(),
					user.getOrganizationName(),
					user.getOrganzationalUnit(),
					user.getCommonName(),
					keyData);
			byte[] crtData = CertificateUtility.generateCRT(csrData);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// save Certificate to back-end
	}

	public static void revokeCertificate(User user) {

	}

	public static void extendCertificate(User user) {

	}

	public static Certificate getCertificate(User user) {
		return user.getCertificate();
	}
}

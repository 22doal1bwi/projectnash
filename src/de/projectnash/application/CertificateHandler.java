package de.projectnash.application;

import java.io.IOException;
import java.text.ParseException;

import de.projectnash.application.util.CertificateUtility;
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
	 * @throws ParseException 
	 * TODO: delete method when ready with testing
	 */
	public static void main(String[] args) throws ParseException {
		User tempUser = new User(0001, "Tobias", "Burger",
				"tobias.burger@simpleCert.com", "DE", "Baden-Wuerttemberg",
				"Stuttgart", "CI", "Eierkuchen4");

		createCertificate(tempUser);
	}

	public static void createCertificate(User user) throws ParseException {

		// TODO: get data of User from Database

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
			
			user.addCertificate(new Certificate(crtData));
			
			System.out.println(user.getCertificates());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO: save Certificate to Database
	}

	// TODO: implement revokeCertificate method
	public static void revokeCertificate(User user) {

	}

	// TODO: implement extendCertificate method
	public static void extendCertificate(User user) {

	}

	// TODO: implement getCertificate method
	public static Certificate getCertificate(User user) {
		return user.getCertificates().get(0);
	}
}

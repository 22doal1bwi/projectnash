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
				"DE", "Bayern",	"Stuttgart",
				"CI", "tobias.burger@simpleCert.com", "Eierkuchen4");

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
					user.getEmailAddress(),
					keyData);
			byte[] crtData = CertificateUtility.generateCRT(csrData);
			
			user.setCertificate(new Certificate(crtData));
			
			System.out.println(user.getCertificate());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO: save Certificate to Database
	}
	
	// TODO: implement certificateValid method
	public static boolean certificateValid(Certificate certificate){
		if (certificate == null) {
			return false;
		}
		return true;
	}
	
	// TODO: implement daysLeftForCertificate method
	public static int daysLeftForCertificate(Certificate certificate){
		return 0;
	}

	// TODO: implement revokeCertificate method
	public static void revokeCertificate(User user) {

	}

	// TODO: implement extendCertificate method
	public static void extendCertificate(User user) {

	}

	public static Certificate getCertificate(User user) {
		return user.getCertificate();
	}
}

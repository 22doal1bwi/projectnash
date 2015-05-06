package de.projectnash.application;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.application.util.CertificateUtility;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.Organization;
import de.projectnash.entities.User;

/**
 * This class provides all methods to handle the {@link Certificate} request, creation, extension and revocation for an specific {@link User}.
 * 
 * @author Marius Boepple
 *
 */
public class CertificateLogic {

	/**
	 * Test Utility Class
	 * 
	 * @param args
	 * @throws ParseException 
	 * TODO: delete method when ready with testing
	 */
	public static void main(String[] args) throws ParseException {
		User tempUser = new User(0001, "Tobias", "Burger",
				"CI", "tobias.burger@simpleCert.com", "Eierkuchen4");

		createCertificate(tempUser);
		
		/**
		 * Tests the connection to the database.
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("simpleCertPU");
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("unchecked")
		TypedQuery<User> query =  (TypedQuery<User>) em.createNativeQuery("Select * from Users", User.class);
		
		query.getResultList().forEach(user -> System.out.println(user.toString()));
	}

	public static void createCertificate(User user) throws ParseException {

		// TODO: get data of User from Database
				
		Organization organization = new Organization();
		
		// create Certificate with utility class
		try {
			byte[] keyData = CertificateUtility.generatePrivateKey();
			byte[] csrData = CertificateUtility.generateCSR(
					organization.getCountry(),
					organization.getState(),
					organization.getLocality(),
					organization.getOrganization(),
					user.getOrganzationalUnit(),
					(user.getFirstName() + " " + user.getLastName() + " (" + user.getPersonalId() + ")"),
					user.getEmailAddress(),
					keyData);
			byte[] crtData = CertificateUtility.generateCRT(csrData);
			
			String subjectData = CertificateUtility.getCRTdata(crtData, "-subject");
			String datesData = CertificateUtility.getCRTdata(crtData, "-dates");
			
			//Format date String to Date() object
			DateFormat formatter = new SimpleDateFormat("MMM dd HH:mm:ss yyyy z", Locale.ENGLISH);	
			
			user.setCertificate(
					new Certificate(
							1, //TODO: get latest certificateID from Database for new certificateID
							crtData,
							subjectData.split("/")[1].split("=")[1],
							subjectData.split("/")[2].split("=")[1],
							subjectData.split("/")[3].split("=")[1],
							subjectData.split("/")[4].split("=")[1],
							subjectData.split("/")[5].split("=")[1],
							subjectData.split("/")[6].split("=")[1],
							subjectData.split("/")[7].split("=")[1],
							formatter.parse(datesData
									.split("notBefore=")[1]
											.split("notAfter=")[0]),
							formatter.parse(datesData
									.split("notBefore=")[1]
											.split("notAfter=")[1])));
			
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

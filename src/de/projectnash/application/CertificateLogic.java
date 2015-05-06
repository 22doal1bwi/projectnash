package de.projectnash.application;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.application.util.CertificateUtility;
import de.projectnash.databackend.CertificatePersistenceService;
import de.projectnash.databackend.UserPersistenceService;
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
		User tempUser2 = new User(0002, "Silvio", "D'Alessandro", "Coder", "silvio.dalessandro@simpleCert111.com", "Eierkuchen5");
		
		
		createCertificate(tempUser2);
		
		
		/**
		 * Tests the connection to the database.
		 */
		System.out.println(UserPersistenceService.loadAllUsers());
		
		
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
					user.getDepartment(),
					UserLogic.getCommonName(user),
					user.getEmailAddress(),
					keyData);
			byte[] crtData = CertificateUtility.generateCRT(csrData);
			
			String subjectData = CertificateUtility.getCRTdata(crtData, "-subject");
			String datesData = CertificateUtility.getCRTdata(crtData, "-dates");
			
			//Format date String to Date() object
			DateFormat formatter = new SimpleDateFormat("MMM dd HH:mm:ss yyyy z", Locale.ENGLISH);	
			
			 //TODO: get latest certificateID from Database for new certificateID
			user.setCertificate(new Certificate(
					crtData,
					subjectData.split("/")[1].split("=")[1],
					subjectData.split("/")[2].split("=")[1],
					subjectData.split("/")[3].split("=")[1],
					subjectData.split("/")[4].split("=")[1],
					subjectData.split("/")[5].split("=")[1],
					subjectData.split("/")[6].split("=")[1],
					subjectData.split("/")[7].split("=")[1],
					formatter.parse(datesData
							.split("notBefore=")[1].split("notAfter=")[0]),
					formatter.parse(datesData
							.split("notBefore=")[1].split("notAfter=")[1])));
			UserPersistenceService.updateUser(user);
					
			
			System.out.println(user.getCertificate());
			
			System.out.println("valid?            " + certificateValid(user.getCertificate()));			
			System.out.println("valid in days:    " + getTimeLeftForCertificate(user.getCertificate(), TimeUnit.DAYS));
			System.out.println("valid in hours:   " + getTimeLeftForCertificate(user.getCertificate(), TimeUnit.HOURS));
			System.out.println("valid in minutes: " + getTimeLeftForCertificate(user.getCertificate(), TimeUnit.MINUTES));
			System.out.println(getAppropriateTimeLeftForCertificate(user.getCertificate()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO: save Certificate to Database
	}
	
	/**
	 * Method which returns true if {@link Certificate} is valid.
	 * 
	 * @param certificate
	 * @return boolean
	 * @author Marius Boepple
	 */
	public static boolean certificateValid(Certificate certificate){
		if (certificate != null) {
			if (certificate.getExpirationDate().compareTo(new Date()) >= 0 ){
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * Method which returns time left for {@link Certificate} in appropriate time unit.
	 * 
	 * @param certificate
	 * @return String
	 * @author Marius Boepple, Jonathan Schlotz
	 */
	public static String getAppropriateTimeLeftForCertificate(Certificate certificate){
		if (getTimeLeftForCertificate(certificate, TimeUnit.DAYS) < 1) {
			if (getTimeLeftForCertificate(certificate, TimeUnit.HOURS) < 1) {
				return getTimeLeftForCertificate(certificate, TimeUnit.MINUTES) + " Minuten";
			}
			return getTimeLeftForCertificate(certificate, TimeUnit.HOURS) + " Stunden";
		}
		return getTimeLeftForCertificate(certificate, TimeUnit.DAYS) + " Tage";
	}
	
	/**
	 * Method which returns time left for {@link Certificate}.
	 * 
	 * @param certificate
	 * @param timeUnit TimeUnit.DAYS, TimeUnit.HOURS or TimeUnit.MINUTES
	 * @return number of days, hours or minutes
	 * @author Marius Boepple, Jonathan Schlotz
	 */
	public static int getTimeLeftForCertificate(Certificate certificate, TimeUnit timeUnit){
		long diffInMillies = certificate.getExpirationDate().getTime() - new Date().getTime();
	    return (int) timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
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

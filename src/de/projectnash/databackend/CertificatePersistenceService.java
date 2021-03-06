package de.projectnash.databackend;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.entities.Certificate;

/**
 * This class provides all methods to store, load, remove and update {@link Certificate}s.
 * 
 * @author Silvio D'Alessandro
 *
 */
public class CertificatePersistenceService {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("simpleCertPU");
	
	private static EntityManager em = emf.createEntityManager();

	/**
	 * Stores a new {@link Certificate} in the database.
	 * 
	 * @param certificate The {@link Certificate} that will be stored.
	 */
	public static void storeCertificate (Certificate certificate) {
		em.getTransaction().begin();
		em.persist(certificate);
		em.getTransaction().commit();
	}
	
	/**
     * Loads a {@link Certificate} from the database.
     * 
     * @param certificateId The {@link Certificate}'s id on basis which the {@link Certificate} will be loaded.
     * @return The specific {@link Certificate}.
     */
    public static Certificate loadCertificate (int certificateId) {
    	TypedQuery<Certificate> query = em.createNamedQuery("QUERY_FIND_CERTIFICATE_BY_CERTIFICATE_ID", Certificate.class);
    	query.setParameter("certificateId", certificateId);
    	return query.getSingleResult();
    }
    
    /**
     * Loads all {@link Certificate}s from the database.
     * 
     * @return A {@link List} of all {@link Certificate}s in the database.
     */
    public static List<Certificate> loadAllCertificates(){
    	TypedQuery<Certificate> query = em.createNamedQuery("QUERY_FIND_ALL_CERTIFICATES", Certificate.class);
    	return query.getResultList();
    }
    
    /**
     * Removes all {@link Certificate}s of {@link User} from the database. 
     * 
     * @param emailAddress E-Mail address of the {@link User}.
     */
    public static void removeAllCertificatesOfUser(String emailAddress){
    	em.getTransaction().begin();
    	TypedQuery<Certificate> query = em.createNamedQuery("QUERY_REMOVE_ALL_CERTIFICATES_BY_USER", Certificate.class);
    	query.setParameter("emailAddress", emailAddress);
    	query.executeUpdate();
    	em.getTransaction().commit();
    }
    
    /**
     * Updates a {@link Certificate} in the database.
     * 
     * @param certificateToUpdate The {@link Certificate} that will be updated.
     */
    public static void updateCertificate(Certificate certificateToUpdate){
    	em.getTransaction().begin();
    	em.merge(certificateToUpdate);
    	em.getTransaction().commit();
    }
    
    /**
     * Removes a {@link Certificate} from the database.
     * 
     * @param certificateToRemove The {@link Certificate} that will be removed.
     */
    public static void removeCertificate (Certificate certificateToRemove){
    	em.getTransaction().begin();
    	TypedQuery<Certificate> query = em.createNamedQuery("QUERY_REMOVE_CERTIFICATE_BY_CERTIFICATE", Certificate.class);
    	query.setParameter("Certificate", certificateToRemove);
    	query.executeUpdate();
    	em.getTransaction().commit();
    }	
}
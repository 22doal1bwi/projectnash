package de.projectnash.databackend;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.entities.Certificate;

/**
 * This class provides all methods to store, load, remove and update {@link Certificate}s.
 * @author Silvio D'Alessandro
 *
 */
public class CertificatePersistenceService {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("simpleCertPU");
	
	private static EntityManager em = emf.createEntityManager();

	/**
	 * Stores a new {@link Certificate} in the database.
	 * @param certificate The {@link Certificate} that will be stored.
	 */
	public static void storeCertificate (Certificate certificate) {
		em.getTransaction().begin();
		em.persist(certificate);
		em.getTransaction().commit();
	}
	
	/**
     * Loads a {@link Certificate} from the database.
     * @param certificateId The {@link Certificate}'s id on basis which the {@link Certificate} will be loaded.
     * @return The specific {@link Certificate}.
     */
    public static Certificate loadCertificate (int certificateId) {
    	TypedQuery<Certificate> query = em.createNamedQuery("QUERY_FIND_CERTIFICATE_BY_CERTIFICATE_ID", Certificate.class);
    	query.setParameter("certificateId", certificateId);
    	return query.getSingleResult();
    }
    
    /**
     * Updates a {@link Certificate}.
     * @param certificateToUpdate The {@link Certificate} that will be updated.
     */
    public static void updateCertificate(Certificate certificateToUpdate){
    	em.getTransaction().begin();
    	em.merge(certificateToUpdate);
    	em.getTransaction().commit();
    }
    
    /**
     * Removes a {@link Certificate} from the database.
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

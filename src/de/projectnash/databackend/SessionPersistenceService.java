package de.projectnash.databackend;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.entities.Session;
import de.projectnash.entities.User;

/**
 * This class provides all methods to store, load, remove and update {@link Session}s.
 * @author Silvio D'Alessandro
 *
 */
public class SessionPersistenceService {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("simpleCertPU");
	
	private static EntityManager em = emf.createEntityManager();
	
	/**
	 * Stores the {@link Session} in the database.
	 * @param sessionToStore The {@link Session} that will be stored.
	 */
	public static void storeSession(Session sessionToStore){
		em.getTransaction().begin();
		em.persist(sessionToStore);
		em.getTransaction().commit();
	}
	
	/**
	 * Loads a {@link Session} from the database.
	 * @param ssnId The {@link Session}'s id on basis which the {@link Session} will be loaded.
	 * @return The specific {@link Session}.
	 */
	public static Session loadSession(String ssnId){
		TypedQuery<Session> query = em.createNamedQuery("QUERY_FIND_SESSION_BY_ID", Session.class);
		query.setParameter("ssnId", ssnId);
		return query.getSingleResult();
	}
	
	/**
     * Removes a {@link Session} from the database.
     * @param sessionToRemove The {@link Session} that will be removed.
     */
	public static void removeSession(Session sessionToRemove){
		em.getTransaction().begin();
    	TypedQuery<Session> query = em.createNamedQuery("QUERY_REMOVE_SESSION_BY_SESSION", Session.class);
    	query.setParameter("Session", sessionToRemove);
    	query.executeUpdate();
    	em.getTransaction().commit();
	}
}

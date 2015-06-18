package de.projectnash.databackend;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.entities.Log;

/**
 * This class provides all methods to store and load {@link Log}s.
 * 
 * @author Artur Ermisch
 *
 */
public final class LogPersistenceService {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("simpleCertPU");
	
	private static EntityManager em = emf.createEntityManager();
	
	/**
	 * Stores a new {@link Log} in the database.
	 * 
	 * @param logToStore The {@link Log} that will be stored.
	 */
    public static void storeLog (Log logToStore) {
    	em.getTransaction().begin();
    	em.persist(logToStore);	
    	em.getTransaction().commit();
    }
    
    /**
     * Loads a {@link Log} from the database.
     * 
     * @param logId The {@link Log}'s id on basis which the {@link Log} will be loaded.
     * @return The specific {@link Log}.
     */
    public static Log loadLog (int logId) {
    	try{
    		TypedQuery<Log> query = em.createNamedQuery("QUERY_FIND_LOG_BY_LOG_ID", Log.class);
    		query.setParameter("logId", logId);
    		return query.getSingleResult();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * Loads all {@link Log}s from the database.
     * 
     * @return A {@link List} of all {@link Log}s in the database.
     */
    public static List<Log> loadAllLogs(){
    	TypedQuery<Log> query = em.createNamedQuery("QUERY_FIND_ALL_LOGS", Log.class);
    	return query.getResultList();
    }
    
    public EntityManagerFactory getEmf() {
		return emf;
	}

	public EntityManager getEm() {
		return em;
	}
}
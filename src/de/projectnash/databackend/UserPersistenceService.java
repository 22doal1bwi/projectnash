package de.projectnash.databackend;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;

/**
 * This class provides all methods to store, load, remove and update {@link User}s.
 * @author Silvio D'Alessandro
 *
 */
public final class UserPersistenceService {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("simpleCertPU");;
	
	private static EntityManager em = emf.createEntityManager();

	public UserPersistenceService(){
		
	}
	
	/**
	 * Stores a new {@link User} in the database.
	 * @param userToStore The {@link User} that will be stored.
	 */
    public static void storeUser (User userToStore) {
    	em.getTransaction().begin();
    	em.persist(userToStore);	
    	em.getTransaction().commit();
    }
    
    /**
     * Loads a {@link User} from the database.
     * @param personalId The {@link User}'s personal id on basis which the {@link User} will be loaded.
     * @return The specific {@link User}.
     */
    public static User loadUser (int personalId) {
    	TypedQuery<User> query = em.createNamedQuery("QUERY_FIND_USER_BY_PERSONAL_ID", User.class);
    	query.setParameter("personalId", personalId);
    	return query.getSingleResult();
    }
    
    /**
     * Loads all {@link User}s from the database.
     * @return A {@link List} of all {@link User}s in the database.
     */
    public static List<User> loadAllUsers(){
    	TypedQuery<User> query = em.createNamedQuery("QUERY_FIND_ALL_USERS", User.class);
    	return query.getResultList();
    }
    
    /**
     * Updates a {@link User}.
     * @param userToUpdate The {@link User} that will be updated.
     */
    public static void updateUser(User userToUpdate){
    	em.getTransaction().begin();
    	em.merge(userToUpdate);
    	em.getTransaction().commit();
    }
    
    /**
     * Removes a {@link User} from the database.
     * @param userToRemove The {@link User} that will be removed.
     */
    public static void removeUser(User userToRemove){
    	em.getTransaction().begin();
    	TypedQuery<User> query = em.createNamedQuery("QUERY_REMOVE_USER_BY_USER", User.class);
    	query.setParameter("User", userToRemove);
    	query.executeUpdate();
    	em.getTransaction().commit();
    }
    
    public static Certificate loadCertificateForUser(int certificateId){
    	TypedQuery<Certificate> query = em.createNamedQuery("QUERY_FIND_CERTIFICATE_FOR_USER", Certificate.class);
    	query.setParameter("certificateId", certificateId);
    	return query.getSingleResult();
    }
    
    public EntityManagerFactory getEmf() {
		return emf;
	}

	public EntityManager getEm() {
		return em;
	}
}

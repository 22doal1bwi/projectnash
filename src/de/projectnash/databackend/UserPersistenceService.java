package de.projectnash.databackend;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.entities.User;

/**
 * This class provides all methods to store, load, remove and update {@link User}s.
 * @author Silvio D'Alessandro, Artur Ermisch
 *
 */
public final class UserPersistenceService {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("simpleCertPU");
	
	private static EntityManager em = emf.createEntityManager();
	
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
     * @param emailAddress The {@link User}'s email address on basis which the {@link User} will be loaded.
     * @return The specific {@link User}.
     */
    public static User loadUser (String emailAddress) {
    	
    	if(userExists(emailAddress)){
    		emailAddress = emailAddress.toLowerCase();
        	TypedQuery<User> query = em.createNamedQuery("QUERY_FIND_USER_BY_EMAIL_ADDRESS", User.class);
        	query.setParameter("emailAddress", emailAddress);
        	return query.getSingleResult();
    	}
    	return null;
    }
    
    /**
     * Loads a {@link User} from the database.
     * @param personalId The {@link User}'s personal id on basis which the {@link User} will be loaded.
     * @return The specific {@link User}.
     */
    public static User loadUser (int personalId) {
    	if(userExists(personalId)){
    		TypedQuery<User> query = em.createNamedQuery("QUERY_FIND_USER_BY_PERSONAL_ID", User.class);
        	query.setParameter("personalId", personalId);
        	return query.getSingleResult();
    	}
    	return null;
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
     * Checks if the {@link User} exists in the database.
     * @param personalId The {@link Integer} on basis which the {@link User} will be checked.
     * @return A flag that describes if the {@link User} exists.
     */
    public static boolean userExists(int personalId){
    	try{
    		TypedQuery<Long> query = em.createNamedQuery("CHECK_USER_EXISTS_BY_PERSONAL_ID", Long.class);
        	query.setParameter("personalId", personalId);
        	if(query.getSingleResult() == 0){
        		return false;
        	} else{
        		return true;
        	}	
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return true;
    }
    
    /**
     * Checks if the {@link User} exists in the database.
     * @param eMailAddress The {@link String} on basis which the {@link User} will be checked.
     * @return A flag that describes if the {@link User} exists.
     */
    public static boolean userExists(String eMailAddress){
    	try{
    		TypedQuery<Long> query = em.createNamedQuery("CHECK_USER_EXISTS_BY_MAIL_ADDRESS", Long.class);
        	query.setParameter("emailAddress", eMailAddress);
        	if(query.getSingleResult() == 0){
        		return false;
        	} else{
        		return true;
        	}	
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return true;	
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
    
    public EntityManagerFactory getEmf() {
		return emf;
	}

	public EntityManager getEm() {
		return em;
	}
}

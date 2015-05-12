package de.projectnash.databackend;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.projectnash.entities.Request;
import de.projectnash.entities.User;

public class RequestPersistenceService {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("simpleCertPU");

	private static EntityManager em = emf.createEntityManager();

	/**
	 * Stores the {@link Request} in the database.
	 * 
	 * @param requestToStore
	 *            The {@link Request} that will be stored.
	 */
	public static void storeRequest(Request requestToStore) {
		em.getTransaction().begin();
		em.persist(requestToStore);
		em.getTransaction().commit();
	}

	/**
	 * Loads a {@link Request} from the database.
	 * 
	 * @param reqId
	 *            The {@link Request}'s id on basis which the {@link Request}
	 *            will be loaded.
	 * @return The specific {@link Request}.
	 */
	public static Request loadRequest(String reqId) {
		TypedQuery<Request> query = em.createNamedQuery(
				"QUERY_FIND_REQUEST_BY_ID", Request.class);
		query.setParameter("reqId", reqId);
		return query.getSingleResult();
	}

	/**
	 * Checks if the {@link Request} exists in the database.
	 * 
	 * @param personalId
	 *            The {@link Integer} on basis which the {@link User} will be
	 *            checked.
	 * @return A flag that describes if the {@link User} exists.
	 */
	public static boolean requestExists(User user) {
		try {
			TypedQuery<Long> query = em.createNamedQuery(
					"CHECK_REQUEST_EXISTS_BY_USER", Long.class);
			query.setParameter("User", user);
			return query.getSingleResult() != 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes a {@link Request} from the database.
	 * 
	 * @param request
	 *            The {@link Request} that will be removed.
	 */
	public static void removeRequest(Request request) {		
			em.getTransaction().begin();
			TypedQuery<Request> query = em.createNamedQuery(
					"QUERY_REMOVE_REQUEST_BY_REQUEST", Request.class);
			query.setParameter("Request", request);
			query.executeUpdate();
			em.getTransaction().commit();
			
	}
}

package cfg;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public final class ConfigBanco {

	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ensalamento");
		
	public final static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}

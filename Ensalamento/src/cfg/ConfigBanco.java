package cfg;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import cfg.Server;

public final class ConfigBanco {

	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory(cfg.Server.DB_NAME);
		
	public final static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}

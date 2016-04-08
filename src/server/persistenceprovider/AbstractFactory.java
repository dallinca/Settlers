package server.persistenceprovider;

import server.database.DatabaseAccess;
import server.facade.ServerFacade;
import server.persistenceprovider.plugins.PersistenceProvider;


public class AbstractFactory {
	
	private DatabaseAccess DA;
	
	private static AbstractFactory SINGLETON = null;
	
	private AbstractFactory() {	
		
		
	}
	
	public static AbstractFactory getInstance() {
		if(SINGLETON == null){
			SINGLETON = new AbstractFactory();
		}
		return SINGLETON;
	}

	public PersistenceProviderInterface getPersistenceProvider(){
		
		PersistenceProvider pp = new PersistenceProvider(DA);
		return pp;		
	}
	
	void specifyPlugin(){
		
	}
}

package server.persistenceprovider;

import server.database.DatabaseAccess;
import server.database.DatabaseException;
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
		
		System.out.println("ABSTRACT FACTORY: GETTING PERSISTENCE PROVIDER.");
		//DA = DatabaseAccess.getInstance();
		
		
		
		try {
			DatabaseAccess.initialize();
			System.out.println("ABSTRACT FACTORY: SETUP.");
			
			
				
			
			DatabaseAccess.getInstance().setupDatabase();
			
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ABSTRACT FACTORY: RETURNING PERSISTENCE PROVIDER.");
		PersistenceProvider pp = new PersistenceProvider();
		return pp;		
	}
	
	void specifyPlugin(){
		
	}
}

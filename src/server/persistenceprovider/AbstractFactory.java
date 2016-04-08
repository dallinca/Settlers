package server.persistenceprovider;

import server.facade.ServerFacade;


public class AbstractFactory {
	
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
		
		return null;		
	}
	
	void specifyPlugin(){
		
	}
}

package server.commands;

import server.facade.IServerFacade;
import server.facade.ServerFacade;

/**
 * Command pattern object which is extended by all subtypes. 
 * Consists of an execute function, and shared information between all commands.
 * 
 * 
 *
 */
public interface Command {
	IServerFacade facade = ServerFacade.getInstance();
	public int gameID = -1;
	public int userID = -1;
		
	void execute();
	

}

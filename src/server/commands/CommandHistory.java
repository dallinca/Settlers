package server.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps a listing of all the commands for a given game. 
 * 
 * @author Dallin
 *
 */
public class CommandHistory {
	private List<Command> history = new ArrayList<Command>();
	
	/**
	 * Calls for the execution of the given command and stores the command in the game history.
	 * 
	 * @pre command != null
	 * @pre command execute() preconditions are satisified
	 * @post command will be executed on the game model and stored for later reference
	 * @param command
	 */
	public void storeAndExecute(Command command) {
		this.history.add(command);
		command.execute();
	}

}

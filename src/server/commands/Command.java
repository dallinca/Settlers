package server.commands;

/**
 * Command pattern object which is extended by all subtypes. 
 * Consists of an execute function, and shared information between all commands.
 * 
 * 
 *
 */
public interface Command {
	void execute();
}

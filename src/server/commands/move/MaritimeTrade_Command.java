package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Maritime Trade action on the server facade.
 * 
 * @author Dallin
 *
 */
public class MaritimeTrade_Command implements Command {
	private IServerFacade facade;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public MaritimeTrade_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public MaritimeTrade_Command(IServerFacade facade) {
		this.facade = facade;
	}

	/**
	 * Issues the Maritime Trade action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Maritime Trade action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * For use coupled with the non-standard initialization of the command.
	 * Allows for one and only one setting of the facade for which the command is to execute.
	 * 
	 * @pre this.facade == null && facade != null
	 * @post this.facade = facade
	 * @param facade
	 */
	public void setGame(IServerFacade facade) {
		if(this.facade == null) {
			this.facade = facade;
		}
	}

}
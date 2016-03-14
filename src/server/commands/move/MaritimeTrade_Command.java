package server.commands.move;

import server.commands.Command;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Maritime Trade action on the server game model.
 * 
 * @author Dallin
 *
 */
public class MaritimeTrade_Command implements Command {
	private Game game;

	/**
	 * Non-standard command pattern constructor instantiation without the game model.
	 * The game model will be determined after original command instantiation.
	 * 
	 */
	public MaritimeTrade_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the game model
	 * 
	 * @param game
	 */
	public MaritimeTrade_Command(Game game) {
		this.game = game;
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

}
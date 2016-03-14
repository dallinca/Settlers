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
	 * TODO -- javadoc
	 * 
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
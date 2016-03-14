package server.commands.move;

import server.commands.Command;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Offer Trade action on the server game model.
 * 
 * @author Dallin
 *
 */
public class OfferTrade_Command implements Command {
	private Game game;

	/**
	 * Non-standard command pattern constructor instantiation without the game model.
	 * The game model will be determined after original command instantiation.
	 * 
	 */
	public OfferTrade_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the game model
	 * 
	 * @param game
	 */
	public OfferTrade_Command(Game game) {
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
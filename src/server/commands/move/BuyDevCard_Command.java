package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuyDevCard_Params;
import shared.communication.results.move.BuyDevCard_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Buy Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuyDevCard_Command implements Command {
	
	private IServerFacade facade;
	
	private BuyDevCard_Params params;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public BuyDevCard_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public BuyDevCard_Command(IServerFacade facade) {
		this.facade = facade;
	}
	
	public BuyDevCard_Command(BuyDevCard_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Buy Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Buy Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		game = facade.buyDevCard(params);
		BuyDevCard_Result result = new BuyDevCard_Result();
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
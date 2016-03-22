package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.AcceptTrade_Params;
import shared.communication.results.move.AcceptTrade_Result;
import shared.definitions.DevCardType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Accept Trade action on the server facade.
 * 
 * @author Dallin
 *
 */
public class AcceptTrade_Command implements Command {
	private IServerFacade facade;
	
	private AcceptTrade_Params params;
	private int gameID, userID;
	private AcceptTrade_Result result;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public AcceptTrade_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public AcceptTrade_Command(IServerFacade facade) {
		this.facade = facade;
	}
	
	public AcceptTrade_Command(AcceptTrade_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Accept Trade action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Accept Trade action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		//Will fix in a second
		game = facade.canDoAcceptTrade(params);
		result = new AcceptTrade_Result();
		
		if (game != null) {
			try {
				
				
			} catch (Exception e) {
				System.out.println("");
				e.printStackTrace();
				return;
			}
		}
		//this.facade.acceptTrade(params);
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

	public AcceptTrade_Result getResult() {
		return result;
	}

}

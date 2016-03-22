package server.commands.move;

import server.commands.Command;
import shared.communication.params.move.OfferTrade_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.OfferTrade_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Offer Trade action on the server facade.
 * 
 * @author Dallin
 *
 */
public class OfferTrade_Command implements Command {
	
	private OfferTrade_Params params;
	private OfferTrade_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public OfferTrade_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public OfferTrade_Command(OfferTrade_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Offer Trade action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Offer Trade action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		game = facade.canDoOfferTrade(params, gameID, userID);
		result = new OfferTrade_Result();
		
		if (game==null){
			return;
		}
		
		int[] p1resources = null;
		int[] p2resources = null;
		
		try {
			game.doDomesticTrade(userID, p1resources, params.getReceiver(), p2resources);
		} catch (Exception e) {	
			e.printStackTrace();
			return;
		}

		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
	}
	
	public 	OfferTrade_Result getResult(){
		return result;
	}
}
package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.MaritimeTrade_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.MaritimeTrade_Result;
import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Maritime Trade action on the server facade.
 * 
 * @author Dallin
 *
 */
public class MaritimeTrade_Command implements Command {
	
	private MaritimeTrade_Params params;
	private MaritimeTrade_Result result;
	private int gameID, userID;
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
	public MaritimeTrade_Command(MaritimeTrade_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
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
		Game game = null;
		
		//convert from string to enum
		ResourceType tradeIn = ResourceType.valueOf(params.getOutputResource() );
		ResourceType receive = ResourceType.valueOf(params.getInputResource() );
		
		game = facade.canDoMaritimeTrade(params, gameID, tradeIn, receive );
		result = new MaritimeTrade_Result();
		
		if (game==null){
			return;
		}
		
		try {
			game.doMaritimeTrade(tradeIn, receive);
		} catch (Exception e) {
			new MaritimeTrade_Result();
			e.printStackTrace();
			return;
		}

		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
	}
	
	public MaritimeTrade_Result getResult(){
		return result;
	}
}
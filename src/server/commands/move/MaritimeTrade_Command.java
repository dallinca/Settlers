package server.commands.move;

import server.commands.Command;
import shared.communication.params.move.MaritimeTrade_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.MaritimeTrade_Result;
import shared.definitions.PortType;
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
		System.out.println("MaritimeTrade_Command");
		Game game = null;
		
		//convert from string to enum
		System.out.println("MaritimeTrade_Command1");
		
		
		System.out.println(params.getOutputResource().toUpperCase());
		
		ResourceType tradeIn = convert(params.getInputResource()); 
		ResourceType receive = convert(params.getOutputResource()); 
		
		game = facade.canDoMaritimeTrade(params, gameID, tradeIn, receive );
		result = new MaritimeTrade_Result();

		System.out.println("MaritimeTrade_Command2");
		if (game==null){
			System.out.println("MaritimeTrade_Command3");
			return;
		}
		
		try {
			System.out.println("MaritimeTrade_Command4");
			game.doMaritimeTrade(tradeIn, receive);
		} catch (Exception e) {
			new MaritimeTrade_Result();
			e.printStackTrace();
			return;
		}

		System.out.println("MaritimeTrade_Command5");
		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		facade.storeCommand(gameID, this);
		
		System.out.println("MaritimeTrade_Command6");
		result.setModel(cm);
		
	}
	
	public MaritimeTrade_Result getResult(){
		return result;
	}
	
	private ResourceType convert(String type){
		
		if(type.equals("wood")) {
			return ResourceType.WOOD;
		} else if(type.equals("brick")) {
			return ResourceType.BRICK;
		} else if(type.equals("sheep")) {
			return ResourceType.SHEEP;
		} else if(type.equals("wheat")) {
			return ResourceType.WHEAT;
		} else if(type.equals("ore")) {
			return ResourceType.ORE;
		}
		return null;
		
		
	}
}
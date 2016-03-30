package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.DiscardCards_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.DiscardCards_Result;
import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Discard action on the server facade.
 * 
 * @author Dallin
 *
 */
public class DiscardCards_Command implements Command {
	//private IServerFacade facade;
	private DiscardCards_Params params;
	private DiscardCards_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public DiscardCards_Command() {}
	
	public DiscardCards_Command(DiscardCards_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Discard Cards action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Discard Cards action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("DiscardCards_Command");
		Game game = null;
		game = facade.canDoDiscardCards(params, gameID, userID);
		result = new DiscardCards_Result();

		System.out.println("DiscardCards_Command1");
		if (game==null){
			System.out.println("DiscardCards_Command2");
			return;
		}
		
		try {
			System.out.println("DiscardCards_Command3");
			game.discardNumberOfResourceType(userID, params.getDiscardedCards().getBrick(), ResourceType.BRICK);
			game.discardNumberOfResourceType(userID, params.getDiscardedCards().getOre(), ResourceType.ORE);
			game.discardNumberOfResourceType(userID, params.getDiscardedCards().getSheep(), ResourceType.SHEEP);
			game.discardNumberOfResourceType(userID, params.getDiscardedCards().getWheat(), ResourceType.WHEAT);
			game.discardNumberOfResourceType(userID, params.getDiscardedCards().getWood(), ResourceType.WOOD);
			game.setVersionNumber(game.getVersionNumber() + 1);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		System.out.println("DiscardCards_Command4");
		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		System.out.println("DiscardCards_Command5");
		result.setModel(cm);
		System.out.println("Status: " + game.getStatus());
	}
	
	public DiscardCards_Result getResult(){
		return result;
	}

}
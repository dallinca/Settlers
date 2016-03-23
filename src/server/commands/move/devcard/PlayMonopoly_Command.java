package server.commands.move.devcard;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.devcard.PlayMonopoly_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.devcard.PlayMonopoly_Result;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Monopoly Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayMonopoly_Command implements Command {
	private PlayMonopoly_Result result;
	private PlayMonopoly_Params params;
	private int gameID, userID;
	
	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayMonopoly_Command(PlayMonopoly_Params theParams, int gameID, int userID) {
		this.params = theParams;
		this.gameID = gameID;
		this.userID = userID;
	}
	
	public PlayMonopoly_Result getResult() {
		return result;
	}

	public void setResult(PlayMonopoly_Result result) {
		this.result = result;
	}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @deprecated
	 * @param game
	 */
	public PlayMonopoly_Command(IServerFacade facade) {
		//this.facade = facade;
	}

	/**
	 * Issues the Play Monopoly Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Monopoly Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		
		//Ask the server facade if that action can happen
		//If it is true, it will return a game object then call the appropriate commands on the game object
		
		String resourceName = params.getResource();
		ResourceType[] resourceType = new ResourceType[1];
		
		if (resourceName.equals("wheat")) {
			resourceType[0] = ResourceType.WHEAT;
		} else if (resourceName.equals("wood")) {
			resourceType[0] = ResourceType.WOOD;
		} else if (resourceName.equals("brick")) {
			resourceType[0] = ResourceType.BRICK;
		} else if (resourceName.equals("ore")) {
			resourceType[0] = ResourceType.ORE;
		} else if (resourceName.equals("sheep")) {
			resourceType[0] = ResourceType.SHEEP;
		}
		
		Game game = facade.canDoPlayMonopoly(params, gameID, userID);
		result = new PlayMonopoly_Result();
		
		if (game != null) {
			if (game.canDoPlayerUseDevelopmentCard(userID, DevCardType.MONOPOLY)) {
				try {	
					game.useDevelopmentCard(userID, DevCardType.MONOPOLY, resourceType);
				} catch (Exception e) {
					System.out.println("");
					e.printStackTrace();
					return;
				}
			}
		} else {
			return;
		}
		
		//Create a result object with the appropriate information (it contains the newly modified game object)
		//Should this happen in the handler because that is where it would be serialized? The Handler has the gameID, so it can retrieve the appropriate modified game after this method is through executing.
		
		result.setValid(true);
	
		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
	
	}

	/**
	 * For use coupled with the non-standard initialization of the command.
	 * Allows for one and only one setting of the facade for which the command is to execute.
	 * 
	 * @pre this.facade == null && facade != null
	 * @post this.facade = facade
	 * @param facade
	 * @deprecated
	 */
	//According to Woodfield, I believe the facade is set up in Command.java and so each command knows the facade upon creation.
	public void setGame(IServerFacade facade) {
	/*	if(this.facade == null) {
			this.facade = facade;
		}*/
	}

}

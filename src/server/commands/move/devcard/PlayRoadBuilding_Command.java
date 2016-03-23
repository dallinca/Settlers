package server.commands.move.devcard;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.devcard.PlayRoadBuilding_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.devcard.PlayRoadBuilding_Result;
import shared.definitions.DevCardType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Road Building Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayRoadBuilding_Command implements Command {
	private PlayRoadBuilding_Params params;
	private PlayRoadBuilding_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayRoadBuilding_Command() {}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public PlayRoadBuilding_Command(PlayRoadBuilding_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Play Road Building Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Road Building Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		game = facade.canDoPlayRoadBuilding(params, gameID, userID);
		result = new PlayRoadBuilding_Result();

		if (game != null) {
			try {
				//Check on this because the game needs to know where the roads will be built, so it needs to grab that out of the params
				//I will check on and fix this upon completeing the other tasks...
				game.useDevelopmentCard(userID, DevCardType.ROAD_BUILD);
			} catch (Exception e) {
				new PlayRoadBuilding_Result();
				e.printStackTrace();
				return;
			} 
		} else {
			return;
		}


		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);

	}


	public PlayRoadBuilding_Result getResult() {
		return result;
	}

}

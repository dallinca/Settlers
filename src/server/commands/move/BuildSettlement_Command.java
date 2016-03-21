package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuildSettlement_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.BuildSettlement_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Build Settlement action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuildSettlement_Command implements Command {
	private IServerFacade facade;

	private BuildSettlement_Params params;
	private BuildSettlement_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public BuildSettlement_Command() {}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public BuildSettlement_Command(IServerFacade facade) {
		this.facade = facade;
	}

	public BuildSettlement_Command(BuildSettlement_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Build Settlement action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Build Settlement action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		game = facade.buildSettlement(params, gameID, userID);
		result = new BuildSettlement_Result();

		if (game==null){
			return;
		}

		try {
			game.placeSettlementOnVertex(userID, params.getCmdVertLocation());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
	}

	public BuildSettlement_Result getResult(){
		return result;
	}
}
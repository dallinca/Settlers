package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuildRoad_Params;
import shared.communication.results.move.BuildRoad_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Build Road action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuildRoad_Command implements Command {
	private IServerFacade facade;
	
	private BuildRoad_Params params;
	private BuildRoad_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public BuildRoad_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public BuildRoad_Command(IServerFacade facade) {
		this.facade = facade;
	}
	
	public BuildRoad_Command(BuildRoad_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}
	
	/**
	 * Issues the Build Road action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Build Road action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		game = facade.buildRoad(params, gameID, userID);
		
		try {
			game.placeRoadOnEdge(userID, params.getCmdEdgeLocation() );
		} catch (Exception e) {
			new BuildRoad_Result();
			e.printStackTrace();
		}
		result = new BuildRoad_Result(game);
	}
	
	public BuildRoad_Result getResult(){
		return result;
	}
}
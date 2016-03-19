package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuildCity_Params;
import shared.communication.results.move.BuildCity_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Build City action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuildCity_Command implements Command {
	private IServerFacade facade;
	
	private BuildCity_Params params;
	private BuildCity_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public BuildCity_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public BuildCity_Command(IServerFacade facade) {
		this.facade = facade;
	}
	
	public BuildCity_Command(BuildCity_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}
	
	/**
	 * Issues the Build City action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Build City Trade action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		game = facade.buildCity(params, gameID, userID);

		try {
			game.placeCityOnVertex(params.getCmdVertLocation());
		} catch (Exception e) {
			new BuildCity_Result();
			e.printStackTrace();
		}
		result = new BuildCity_Result(game);
	}
	
	public BuildCity_Result getResult(){
		return result;
	}
}
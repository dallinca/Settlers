package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuildCity_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.BuildCity_Result;
import shared.model.Game;
import shared.model.Game.Line;

/**
 * Concrete command implementing the Command interface.
 * Issues the Build City action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuildCity_Command implements Command {
	//private IServerFacade facade;

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
	/*public BuildCity_Command(IServerFacade facade) {
		this.facade = facade;
	}*/

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
		System.out.println("BuildCity_Command");
		Game game = null;
		game = facade.canDoBuildCity(params, gameID, userID);
		result = new BuildCity_Result();

		System.out.println("BuildCity_Command1");
		if (game != null) {
			System.out.println("BuildCity_Command2");
			try {
				System.out.println("BuildCity_Command3");
				game.placeCityOnVertex(params.getCmdVertLocation());
				Game.Line[] history = game.getHistory();
				Game.Line[] newHistory = new Game.Line[history.length+1];

				for (int i = 0; i < history.length; i++) {
					newHistory[i] = history[i];
				}
				
				//Just a round-about way to create an object of type Game.Line without too much difficulty
				Game.Line newEntry = game.new Line();
				newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + " built a city");
				newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
				newHistory[history.length] = newEntry;
				
				game.setHistory(newHistory);
			} catch (Exception e) {
				new BuildCity_Result();
				e.printStackTrace();
				System.out.println(e.getMessage());
				return;
			}
		} else {
			return;
		}

		System.out.println("BuildCity_Command4");
		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		System.out.println("BuildCity_Command5");
		
		facade.storeCommand(gameID, this);
		
		result.setModel(cm);		
	}

	public BuildCity_Result getResult(){
		return result;
	}
}
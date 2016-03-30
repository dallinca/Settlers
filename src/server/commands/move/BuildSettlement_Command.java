package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuildSettlement_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.BuildSettlement_Result;
import shared.model.Game;
import shared.model.Game.Line;

/**
 * Concrete command implementing the Command interface.
 * Issues the Build Settlement action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuildSettlement_Command implements Command {
	//private IServerFacade facade;

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
	 * @deprecated 
	 * @param game
	 */
	public BuildSettlement_Command(IServerFacade facade) {
		//	this.facade = facade;
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
		System.out.println("BuildSettlement_Command");
		Game game = null;
		game = facade.canDoBuildSettlement(params, gameID, userID);
		result = new BuildSettlement_Result();

		System.out.println("BuildSettlement_Command1");
		if (game==null){
			System.out.println("BuildSettlement_Command2");
			return;
		}

		try {
			System.out.println("BuildSettlement_Command3");
			game.placeSettlementOnVertex(userID, params.getCmdVertLocation());
			Game.Line[] history = game.getHistory();

			if (history == null) {
				history = new Game.Line[1];
				Game.Line firstLine = game.new Line();
				firstLine.setMessage(game.getPlayerByID(userID).getPlayerName() + " built a settlement");
				firstLine.setSource(game.getPlayerByID(userID).getPlayerName());
				history[0] = firstLine;
				game.setHistory(history);

			} else {

				Game.Line[] newHistory = new Game.Line[history.length+1];

				for (int i = 0; i < history.length; i++) {
					newHistory[i] = history[i];
				}

				//Just a round-about way to create an object of type Game.Line without too much difficulty
				Game.Line newEntry = game.new Line();
				newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + " built a settlement");
				newEntry.setSource(game.getPlayerByID(userID).getPlayerName());

				newHistory[history.length] = newEntry;

				game.setHistory(newHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		System.out.println("BuildSettlement_Command4");
		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		System.out.println("BuildSettlement_Command5");
		result.setModel(cm);
	}

	public BuildSettlement_Result getResult(){
		return result;
	}
}
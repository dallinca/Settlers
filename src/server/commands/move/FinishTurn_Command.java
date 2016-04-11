package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.FinishTurn_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.FinishTurn_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Finish Turn action on the server facade.
 * 
 * @author Dallin
 *
 */
public class FinishTurn_Command implements Command {
	
	//private IServerFacade facade;
	private FinishTurn_Result result;
	private FinishTurn_Params params;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public FinishTurn_Command() {}

	public FinishTurn_Command(FinishTurn_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Finish Turn action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Finish Turn action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("FinishTurn_Command");
		Game game = null;
		game = facade.canDoFinishTurn(params, gameID, userID);
	
		result = new FinishTurn_Result();
		System.out.println("FinishTurn_Command1");
		
		if (game==null){
			System.out.println("FinishTurn_Command2");
			return;
		}
		//IS THERE AN END TURN FUNCTION ON GAME?????
		
		//Well at the very least we need to move to the next player! But we should probably come up with the action associated with this as it gets sent back across
		//For the game history controller at least
		game.incrementPlayer();
		Game.Line[] history = game.getHistory();
		Game.Line[] newHistory = new Game.Line[history.length+1];
		
		for (int i = 0; i < history.length; i++) {
			newHistory[i] = history[i];
		}
		
		//Just a round-about way to create an object of type Game.Line without too much difficulty
		Game.Line newEntry = game.new Line();
		newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + "'s turn just ended.");
		newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
		newHistory[history.length] = newEntry;
		
		game.setHistory(newHistory);
	
		result.setValid(true);
		result.setGame(game);
		System.out.println("FinishTurn_Command3");

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		facade.storeCommand(gameID, this);
		
		System.out.println("FinishTurn_Command4");
		result.setModel(cm);
	
	}
	
	public FinishTurn_Result getResult(){
		return result;
	}

}
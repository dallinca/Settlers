package server.commands.move;

import server.commands.Command;
import shared.communication.params.move.RollNumber_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.RollNumber_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Roll Number action on the server facade.
 * 
 * @author Dallin
 *
 */
public class RollNumber_Command implements Command {
	
	private RollNumber_Params params;
	private RollNumber_Result result;
	private int gameID;
	private int userID;
	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public RollNumber_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public RollNumber_Command(RollNumber_Params params, int ID, int userID) {
		this.params = params; 
		this.gameID = ID;
		this.userID = userID;
	}

	/**
	 * Issues the Roll Number action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Roll Number action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("RollNumber_Command");
		Game game = null;
		//Call facade to check if can do operation
		game = facade.canDoRollNumber(params, gameID, userID);
		result = new RollNumber_Result();

		System.out.println("RollNumber_Command1");
		if (game==null){
			System.out.println("RollNumber_Command2");
			return;
		}
		
		try {
			System.out.println("RollNumber_Command3");
			game.setRollDice(userID, params.getNumber());
			
			Game.Line[] history = game.getHistory();
			Game.Line[] newHistory = new Game.Line[history.length+1];
			
			for (int i = 0; i < history.length; i++) {
				newHistory[i] = history[i];
			}
			
			//Just a round-about way to create an object of type Game.Line without too much difficulty
			Game.Line newEntry = game.new Line();
			newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + " rolled a " + params.getNumber());
			newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
			newHistory[history.length] = newEntry;
			
			game.setHistory(newHistory);
			
		} catch (Exception e) {
			System.out.println("RollNumber_Command4");
			e.printStackTrace();
			return;
		}
		
		result.setValid(true);

		System.out.println("RollNumber_Command5");
		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		System.out.println("RollNumber_Command6");
		result.setModel(cm);
		facade.storeCommand(gameID, this);
		
	}
	
	public RollNumber_Result getResult(){
		return result;
	}
}
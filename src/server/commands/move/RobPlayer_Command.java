package server.commands.move;

import server.commands.Command;
import shared.communication.params.move.RobPlayer_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.RobPlayer_Result;
import shared.model.Game;
import shared.model.Game.Line;

/**
 * Concrete command implementing the Command interface.
 * Issues the Rob Player action on the server facade.
 * 
 * @author Dallin
 *
 */
public class RobPlayer_Command implements Command {

	private RobPlayer_Params params;
	private RobPlayer_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public RobPlayer_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public RobPlayer_Command(RobPlayer_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Rob Player action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Rob Player action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("RobPlayer_Command");
		Game game = null;
		game = facade.canDoRobPlayer(params, gameID, userID);
		result = new RobPlayer_Result();

		System.out.println("RobPlayer_Command1");
		if (game==null){
			System.out.println("RobPlayer_Command2");
			return;
		}
		
		try {
			Game.Line[] history = game.getHistory();
			Game.Line[] newHistory = new Game.Line[history.length+1];
			Game.Line newEntry = game.new Line();
			
			System.out.println("RobPlayer_Command3");
			if(params.getVictimIndex() != -1) {
				game.stealPlayerResource(userID, params.getVictimIndex());
				// Add Player Robbed history
				newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + " moved the robber like a boss and robbed " + game.getAllPlayers()[params.getVictimIndex()].getPlayerName());
				newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
				newHistory[history.length] = newEntry;
				
			} else {
				newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + " moved the robber.");
				newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
				newHistory[history.length] = newEntry;
			}
			
			for (int i = 0; i < history.length; i++) {
				newHistory[i] = history[i];
			}
			
			game.setHistory(newHistory);
			
		} catch (Exception e) {
			System.out.println("RobPlayer_Command4");
			e.printStackTrace();
			return;
		}
		
		result.setValid(true);
		game.setVersionNumber(game.getVersionNumber() + 1);
		System.out.println("RobPlayer_Command5");
		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);
		
		System.out.println("RobPlayer_Command6");
		result.setModel(cm);
	}
	public RobPlayer_Result getResult(){
		return result;
	}
}
package server.commands.move.devcard;

import server.commands.Command;
import shared.communication.params.move.devcard.PlayMonument_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.devcard.PlayMonument_Result;
import shared.definitions.DevCardType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Monument Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayMonument_Command implements Command {
	private PlayMonument_Params params;
	private PlayMonument_Result result; 
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayMonument_Command(PlayMonument_Params theParams, int gameID) {
		this.params = theParams;
		this.gameID = gameID;}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public PlayMonument_Command( PlayMonument_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Play Monument Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Monument Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {

		//int userID = params.getPlayerIndex();
		result = new PlayMonument_Result();
		//Ask the server facade if that action can happen
		//If it is true, it will return a game object then call the appropriate commands on the game object
		
		System.out.println("PlayMonument_Command beginning");

		Game game = facade.canDoPlayMonument(gameID, userID);
		System.out.println("PlayMonument_Command asked facade for a game");

		
		if (game != null) {
			try {
				game.useMonumentCard(userID, DevCardType.MONUMENT);
				Game.Line[] history = game.getHistory();
				Game.Line[] newHistory = new Game.Line[history.length+1];
				
				for (int i = 0; i < history.length; i++) {
					newHistory[i] = history[i];
				}
				
				//Just a round-about way to create an object of type Game.Line without too much difficulty
				Game.Line newEntry = history[history.length-1];
				newEntry.setMessage("played a monument card.");
				newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
				newHistory[history.length] = newEntry;
				
				game.setHistory(newHistory);
				System.out.println("PlayMonopoly_Command just operated on the game");
				
				System.out.println("PlayMonument_Command operated on game");
			} catch (Exception e) {
				System.out.println("");
				e.printStackTrace();
				return;
			}
		}

		result.setValid(true);
		

		game.setVersionNumber(game.getVersionNumber()+1);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
		System.out.println("PlayMonument_Command end of execute");
		
	}
	
	public PlayMonument_Result getResult(){
		return result;
	}
}

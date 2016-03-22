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
		Game game = facade.canDoPlayMonument(gameID, userID);
		
		if (game != null) {
			try {
				game.useDevelopmentCard(userID, DevCardType.MONUMENT);
			} catch (Exception e) {
				System.out.println("");
				e.printStackTrace();
				return;
			}
		}

		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
		
		
	}
	
	public PlayMonument_Result getResult(){
		return result;
	}
}

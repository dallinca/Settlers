package server.commands.move.devcard;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.devcard.PlayYearOfPlenty_Params;
import shared.communication.results.move.devcard.PlayYearOfPlenty_Result;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Year of Plenty Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayYearOfPlenty_Command implements Command {
	private PlayYearOfPlenty_Params params;
	private PlayYearOfPlenty_Result result; 
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayYearOfPlenty_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public PlayYearOfPlenty_Command(PlayYearOfPlenty_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Play Year Of Plenty Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Year Of Plenty Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
	
		game = facade.playYearOfPlenty(params, gameID, userID);
		try {
			game.useDevelopmentCard(userID, DevCardType.YEAR_OF_PLENTY);
		} catch (Exception e) {
			new PlayYearOfPlenty_Result();
			e.printStackTrace();
		}
		result = new PlayYearOfPlenty_Result(game);
	}
	
	public PlayYearOfPlenty_Result getResult(){
		return result;
	}
}

package server.commands.move.devcard;

import server.commands.Command;
import server.facade.IServerFacade;
<<<<<<< HEAD
import shared.communication.params.move.devcard.PlayMonopoly_Params;
import shared.communication.params.move.devcard.PlayMonument_Params;
import shared.communication.results.move.devcard.PlayMonopoly_Result;
import shared.communication.results.move.devcard.PlayMonument_Result;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
=======
import shared.communication.params.move.devcard.PlayMonument_Params;
import shared.communication.results.move.devcard.PlayMonument_Result;
import shared.definitions.DevCardType;
>>>>>>> 91c65baf303864037cbd743d5900d5e82be82076
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Monument Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayMonument_Command implements Command {
<<<<<<< HEAD
	private IServerFacade facade;
	private boolean isValid = false;
	private PlayMonument_Result theResult;
	private PlayMonument_Params theParams;
	private int gameID;
=======

	private PlayMonument_Params params;
	private PlayMonument_Result result; 
	private int gameID, userID;
>>>>>>> 91c65baf303864037cbd743d5900d5e82be82076

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayMonument_Command(PlayMonument_Params theParams, int gameID) {
		this.theParams = theParams;
		this.gameID = gameID;}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
<<<<<<< HEAD
	public PlayMonument_Command(IServerFacade facade) {
		this.facade = facade;
		this.theParams = theParams;
		this.gameID = gameID;
=======
	public PlayMonument_Command( PlayMonument_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
>>>>>>> 91c65baf303864037cbd743d5900d5e82be82076
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

		int userID = theParams.getPlayerIndex();
		
		//Ask the server facade if that action can happen
		//If it is true, it will return a game object then call the appropriate commands on the game object
		Game game = facade.canDoPlayMonument(gameID, userID);
		
		if (game != null) {
			try {
				game.useDevelopmentCard(userID, DevCardType.MONUMENT);
			} catch (Exception e) {
				System.out.println("");
				e.printStackTrace();
			}
		}

		game = facade.playMonument(gameID, userID);
		
		try {
			game.useDevelopmentCard(userID, DevCardType.MONUMENT);
		} catch (Exception e) {
			new PlayMonument_Result();
			e.printStackTrace();
		}
		result = new PlayMonument_Result(game);
	}
	
	public PlayMonument_Result getResult(){
		return result;
	}
}

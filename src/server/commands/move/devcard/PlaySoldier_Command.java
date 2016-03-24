package server.commands.move.devcard;

import server.commands.Command;
import shared.communication.params.move.devcard.PlaySoldier_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.devcard.PlaySoldier_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Soldier Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlaySoldier_Command implements Command {

	private PlaySoldier_Params params;
	private PlaySoldier_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlaySoldier_Command() {}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public PlaySoldier_Command(PlaySoldier_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Play Soldier Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Soldier Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		
		System.out.println("PlaySoldier_command beginning");
		game = facade.canDoPlaySoldier(params, gameID, userID);
		System.out.println("PlaySoldier_command got game");
		
		/*
		 * Things to use: 
		 * private int playerIndex;
		 * private int victimIndex;
		 * private HexLocation location;	
		 */


		result = new PlaySoldier_Result();

		if (game != null) {
			try {
				game.useSoldierCard(userID, params);
				System.out.println("PlaySoldier_command operated on the game");
				
			} catch (Exception e) {
				new PlaySoldier_Result();
				e.printStackTrace();
				return;
			}
		} else {
			return;
		}

		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
		System.out.println("PlaySoldier_command end of execute");

	}

	public PlaySoldier_Result getResult(){
		return result;
	}
}

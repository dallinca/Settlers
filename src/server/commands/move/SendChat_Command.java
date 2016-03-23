package server.commands.move;

import server.commands.Command;
import shared.communication.params.move.SendChat_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.SendChat_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Send Chat action on the server facade.
 * 
 * @author Dallin
 *
 */
public class SendChat_Command implements Command {
	
	private SendChat_Params params;
	private SendChat_Result result;
	private int gameID, userID;
	
	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public SendChat_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public SendChat_Command(SendChat_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the send chat action on the given game server game model.
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
		Game game = null;
		game = facade.canDoSendChat(params, gameID, userID);
		result = new SendChat_Result();
		
		if (game==null){
			return;
		}
		
		game.setChat(game.getChat() );
		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
	}
	
	public SendChat_Result getResult(){
		return result;
	}
}
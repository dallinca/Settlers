package move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.RollNumber_Params;
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
		Game game = null;
		//Call facade to check if can do operation
		game = facade.rollNumber(params, gameID, userID);

		try {
			game.RollDice(userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = new RollNumber_Result(game);
	}
	
	public RollNumber_Result getResult(){
		return result;
	}
}
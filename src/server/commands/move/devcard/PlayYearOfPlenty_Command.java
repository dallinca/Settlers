package server.commands.move.devcard;

import server.commands.Command;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Year of Plenty Dev Card action on the server game model.
 * 
 * @author Dallin
 *
 */
public class PlayYearOfPlenty_Command implements Command {
	private Game game;

	/**
	 * Non-standard command pattern constructor instantiation without the game model.
	 * The game model will be determined after original command instantiation.
	 * 
	 */
	public PlayYearOfPlenty_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the game model
	 * 
	 * @param game
	 */
	public PlayYearOfPlenty_Command(Game game) {
		this.game = game;
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
		// TODO Auto-generated method stub
		
	}

	/**
	 * For use coupled with the non-standard initialization of the command.
	 * Allows for one and only one setting of the game for which the command is to execute.
	 * 
	 * @pre this.game == null && game != null
	 * @post this.game = game
	 * @param game
	 */
	public void setGame(Game game) {
		if(this.game == null) {
			this.game = game;
		}
	}
	
	

}

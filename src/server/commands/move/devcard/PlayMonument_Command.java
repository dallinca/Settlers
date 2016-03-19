package server.commands.move.devcard;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.devcard.PlayMonopoly_Params;
import shared.communication.params.move.devcard.PlayMonument_Params;
import shared.communication.results.move.devcard.PlayMonopoly_Result;
import shared.communication.results.move.devcard.PlayMonument_Result;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Monument Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayMonument_Command implements Command {
	private IServerFacade facade;
	private boolean isValid = false;
	private PlayMonument_Result theResult;
	private PlayMonument_Params theParams;
	private int gameID;

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
	public PlayMonument_Command(IServerFacade facade) {
		this.facade = facade;
		this.theParams = theParams;
		this.gameID = gameID;
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
			if (game.canDoPlayerUseDevelopmentCard(userID, DevCardType.MONUMENT)) {
				try {
					game.useDevelopmentCard(userID, DevCardType.MONUMENT);
				} catch (Exception e) {
					System.out.println("");
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * For use coupled with the non-standard initialization of the command.
	 * Allows for one and only one setting of the facade for which the command is to execute.
	 * 
	 * @pre this.facade == null && facade != null
	 * @post this.facade = facade
	 * @param facade
	 */
	public void setGame(IServerFacade facade) {
		if(this.facade == null) {
			this.facade = facade;
		}
	}

}

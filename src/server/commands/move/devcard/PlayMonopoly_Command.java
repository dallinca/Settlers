package move.devcard;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.devcard.PlayMonopoly_Params;
import shared.communication.results.move.devcard.PlayMonopoly_Result;
import shared.definitions.DevCardType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Monopoly Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayMonopoly_Command implements Command {
	private IServerFacade facade;
	private boolean isValid = false;
	private PlayMonopoly_Result theResult;
	private PlayMonopoly_Params theParams;
	private int gameID;
	
	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayMonopoly_Command(PlayMonopoly_Params theParams, int gameID) {
		this.theParams = theParams;
		this.gameID = gameID;
	}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public PlayMonopoly_Command(IServerFacade facade) {
		this.facade = facade;
	}

	/**
	 * Issues the Play Monopoly Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Monopoly Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		int userID = theParams.getPlayerIndex();
		
		//Ask the server facade if that action can happen
		Game game = facade.canDoPlayMonopoly(gameID, userID);
		
		if (game != null) {
			if (game.canDoPlayerUseDevelopmentCard(userID, DevCardType.MONOPOLY)) {
				try {
					game.useDevelopmentCard(userID, DevCardType.MONOPOLY);
				} catch (Exception e) {
					System.out.println("");
					e.printStackTrace();
				}
			}
		}
		//If it is true, it will return a game object then call the appropriate commands on the game object
		//Create a result object with the appropriate information (it contains the newly modified game object)
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

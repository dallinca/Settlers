package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuyDevCard_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.BuyDevCard_Result;
import shared.model.Game;
import shared.model.Game.Line;
import shared.model.player.exceptions.CannotBuyException;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;

/**
 * Concrete command implementing the Command interface.
 * Issues the Buy Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuyDevCard_Command implements Command {
	
	//private IServerFacade facade;
	
	private BuyDevCard_Params params;
	private BuyDevCard_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public BuyDevCard_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	/*public BuyDevCard_Command(IServerFacade facade) {
		this.facade = facade;
	}*/
	
	public BuyDevCard_Command(BuyDevCard_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Buy Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Buy Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("BuyDevCard_Command");
		Game game = null;
		game = facade.canDoBuyDevCard(params, gameID, userID);
		result = new BuyDevCard_Result();

		System.out.println("BuyDevCard_Command1");
		if (game==null){
			System.out.println("BuyDevCard_Command2");
			return;
		}
		try {
			System.out.println("BuyDevCard_Command3");
			game.buyDevelopmentCard();
			Game.Line[] history = game.getHistory();
			Game.Line[] newHistory = new Game.Line[history.length+1];
			
			for (int i = 0; i < history.length; i++) {
				newHistory[i] = history[i];
			}
			
			//Just a round-about way to create an object of type Game.Line without too much difficulty
			Game.Line newEntry = game.new Line();
			newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + " bought a development card");
			newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
			newHistory[history.length] = newEntry;
			
			game.setHistory(newHistory);
		} catch (CannotBuyException e) {
			e.printStackTrace();
			return;
		} catch (InsufficientPlayerResourcesException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("BuyDevCard_Command4");
		result.setValid(true);
		
		JsonConverter converter = new JsonConverter();
		
		ClientModel cm = converter.toClientModel(game);

		facade.storeCommand(gameID, this);
		
		System.out.println("BuyDevCard_Command5");
		result.setModel(cm);
		
	}
	
	public BuyDevCard_Result getResult(){
		return result;
	}
}
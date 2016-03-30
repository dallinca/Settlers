package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.AcceptTrade_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.ClientModel.ResourceList;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.AcceptTrade_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Accept Trade action on the server facade.
 * 
 * @author Dallin
 *
 */
public class AcceptTrade_Command implements Command {
	//private IServerFacade facade;

	private AcceptTrade_Params params;
	private int gameID, userID;
	private AcceptTrade_Result result;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public AcceptTrade_Command() {}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	/*public AcceptTrade_Command(IServerFacade facade) {
		this.facade = facade;
	}*/

	public AcceptTrade_Command(AcceptTrade_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Accept Trade action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Accept Trade action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("AcceptTrade_Command");
		Game game = null;

		//Will fix in a second
		game = facade.canDoAcceptTrade(params);

		result = new AcceptTrade_Result();

		System.out.println("AcceptTrade_Command1");
		if (game != null) {
			System.out.println("AcceptTrade_Command2");
			try {

				System.out.println("AcceptTrade_Command3");
				//Check and see if they declined or accepted:
				if (params.isWillAccept()) {

					//The Order
					//Brick
					//Wood
					//Wheat
					//Ore
					//Sheep

					System.out.println("AcceptTrade_Command4");
					ResourceList o = game.getTradeOffer().getOffer();
					int[] offer = new int[5];
					int[] receive = new int[5];

					if (o.getBrick() >= 0) {
						offer[0] = o.getBrick();
						receive[0] = 0;
					} else {
						receive[0] = o.getBrick();
						offer[0] = 0;
					}

					if (o.getWood() >= 0) {
						offer[1] = o.getWood();
						receive[1] = 0;
					} else {
						receive[1] = o.getWood();
						offer[1] = 0;
					}

					if (o.getWheat() >= 0) {
						offer[2] = o.getWheat();
						receive[2] = 0;
					} else {
						receive[2] = o.getWheat();
						offer[2] = 0;
					}

					if (o.getOre() >= 0) {
						offer[3] = o.getOre();
						receive[3] = 0;
					} else {
						receive[3] = o.getOre();
						offer[3] = 0;
					}

					if (o.getBrick() >= 0) {
						offer[4] = o.getSheep();
						receive[4] = 0;
					} else {
						receive[4] = o.getSheep();
						offer[4] = 0;
					}

					if (game.canDoPlayerDoDomesticTrade(game.getTradeOffer().getSender(), offer, userID, receive)) {
						System.out.println("AcceptTrade_Command5");
						
						//Now we operate to perform the trade
						game.doDomesticTrade(game.getTradeOffer().getSender(), offer, userID, receive);
						
						Game.Line[] history = game.getHistory();
						Game.Line[] newHistory = new Game.Line[history.length+1];
						
						for (int i = 0; i < history.length; i++) {
							newHistory[i] = history[i];
						}
						
						//Just a round-about way to create an object of type Game.Line without too much difficulty
						Game.Line newEntry = game.new Line();
						newEntry.setMessage(game.getPlayerByID(game.getTradeOffer().getSender()).getPlayerName() + " traded with " + game.getPlayerByID(game.getTradeOffer().getSender()).getPlayerName());
						newEntry.setSource(game.getPlayerByID(game.getTradeOffer().getSender()).getPlayerName());
						newHistory[history.length] = newEntry;
						
						game.setHistory(newHistory);
						
						result.setValid(true);
						game.setTradeOffer(null);
						
						JsonConverter converter = new JsonConverter();
						ClientModel cm = converter.toClientModel(game);

						result.setModel(cm);
					} else {
						System.out.println("AcceptTrade_Command6");
				
						result.setValid(false);
						game.setTradeOffer(null);
						JsonConverter converter = new JsonConverter();
						ClientModel cm = converter.toClientModel(game);

						result.setModel(cm);
					}
				} else {

					Game.Line[] history = game.getHistory();
					Game.Line[] newHistory = new Game.Line[history.length+1];
					
					for (int i = 0; i < history.length; i++) {
						newHistory[i] = history[i];
					}
					
					//Just a round-about way to create an object of type Game.Line without too much difficulty
					Game.Line newEntry = history[history.length-1];
					newEntry.setMessage(" failed to trade with " + game.getPlayerByID(game.getTradeOffer().getSender()).getPlayerName());
					newEntry.setSource(game.getPlayerByID(game.getTradeOffer().getSender()).getPlayerName());
					newHistory[history.length] = newEntry;
					
					game.setHistory(newHistory);
					
					result.setValid(true);
					game.setTradeOffer(null);
					
					System.out.println("AcceptTrade_Command7");
					result.setValid(false);
					game.setTradeOffer(null);
					JsonConverter converter = new JsonConverter();
					ClientModel cm = converter.toClientModel(game);

					result.setModel(cm);
				}
			} catch (Exception e) {
				System.out.println("AcceptTrade_Command8");
				System.out.println("");
				e.printStackTrace();
				
				return;
			}

			JsonConverter converter = new JsonConverter();
			ClientModel cm = converter.toClientModel(game);

			System.out.println("AcceptTrade_Command9");
			result.setModel(cm);
		} else {
			System.out.println("AcceptTrade_Command10");
			result.setValid(false);
			return;
		}
		System.out.println("AcceptTrade_Command11");
		//this.facade.acceptTrade(params);
	}

	/**
	 * For use coupled with the non-standard initialization of the command.
	 * Allows for one and only one setting of the facade for which the command is to execute.
	 * 
	 * @pre this.facade == null && facade != null
	 * @post this.facade = facade
	 * @param facade
	 */
	/*public void setGame(IServerFacade facade) {
		if(this.facade == null) {
			this.facade = facade;
		}
	}*/

	public AcceptTrade_Result getResult() {
		return result;
	}

}

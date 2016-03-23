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
	private IServerFacade facade;

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
	public AcceptTrade_Command(IServerFacade facade) {
		this.facade = facade;
	}

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
		Game game = null;

		//Will fix in a second
		game = facade.canDoAcceptTrade(params);

		result = new AcceptTrade_Result();

		if (game != null) {
			try {

				//Check and see if they declined or accepted:
				if (params.isWillAccept()) {

					//The Order
					//Brick
					//Wood
					//Wheat
					//Ore
					//Sheep

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
						
						//Now we operate to perform the trade
						game.doDomesticTrade(game.getTradeOffer().getSender(), offer, userID, receive);
						
						result.setValid(true);
						game.setTradeOffer(null);
						
						JsonConverter converter = new JsonConverter();
						ClientModel cm = converter.toClientModel(game);

						result.setModel(cm);
					} else {
				
						result.setValid(false);
						game.setTradeOffer(null);
						JsonConverter converter = new JsonConverter();
						ClientModel cm = converter.toClientModel(game);

						result.setModel(cm);
					}
				} else {
					
					result.setValid(false);
					game.setTradeOffer(null);
					JsonConverter converter = new JsonConverter();
					ClientModel cm = converter.toClientModel(game);

					result.setModel(cm);
				}
			} catch (Exception e) {
				System.out.println("");
				e.printStackTrace();
				
				return;
			}

			JsonConverter converter = new JsonConverter();
			ClientModel cm = converter.toClientModel(game);

			result.setModel(cm);
		} else {
			result.setValid(false);
			return;
		}
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
	public void setGame(IServerFacade facade) {
		if(this.facade == null) {
			this.facade = facade;
		}
	}

	public AcceptTrade_Result getResult() {
		return result;
	}

}

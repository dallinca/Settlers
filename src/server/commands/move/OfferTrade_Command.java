package server.commands.move;

import client.data.TradeInfo;
import server.commands.Command;
import shared.communication.params.move.OfferTrade_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.ClientModel.ResourceList;
import shared.communication.results.move.OfferTrade_Result;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Offer Trade action on the server facade.
 * 
 * @author Dallin
 *
 */
public class OfferTrade_Command implements Command {
	
	private OfferTrade_Params params;
	private OfferTrade_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public OfferTrade_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public OfferTrade_Command(OfferTrade_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Offer Trade action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Offer Trade action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		game = facade.canDoOfferTrade(params, gameID, userID);
		result = new OfferTrade_Result();
		
		if (game==null){
			return;
		}
		
		ClientModel clientModel = new ClientModel();
		ClientModel.ResourceList resourceList = clientModel.new ResourceList();
		resourceList.brick = params.getOffer().getBrick();
		resourceList.wood = params.getOffer().getWood();
		resourceList.wheat = params.getOffer().getWheat();
		resourceList.ore = params.getOffer().getOre();
		resourceList.sheep = params.getOffer().getSheep();
		
		
		TradeInfo tradeInfo = new TradeInfo(params.getPlayerIndex(), params.getReceiver(), resourceList);
		
		ResourceList o = tradeInfo.getOffer();
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
		
		try {			
			
			game.setTradeOffer(tradeInfo);
	
		} catch (Exception e) {	
			e.printStackTrace();
			return;
		}

		result.setValid(true);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
	}
	
	public 	OfferTrade_Result getResult(){
		return result;
	}
}
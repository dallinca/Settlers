package shared.model.turn;

import client.Client;
import shared.model.Game;


/**
 * Action interface object for performing actions on one's turn while participating in a game.
 * @author jchrisw
 *
 */
public class ActionManager {
	
	Game game;
	Client client;
	
	public ActionManager(Game g, Client c){
		game = g;
		client = c;
	}
	
	/**
	 * Performs an action of given type for the player.
	 * @param action
	 * 
	 * @pre None
	 * @post action will be performed.
	 */
	public void doAction(ActionType action){
		
		int i = game.getVersionNumber();
		game.setVersionNumber(i++);		
		
		if (action.getCategory() == ActionType.PURCHASE) doPurchase(action);
		else if (action.getCategory() == ActionType.TRADE) doTrade(action);
		else if (action.getCategory() == ActionType.PLAYCARD) playDevelopmentCard(action);	
		
		client.notifyAll();
	}

	
	/**
	 * Performs a purchase of given type for the player.
	 * @param action
	 * 
	 * @pre action must be of 'purchase' action category.
	 * @post action will be performed.
	 */
	public static void doPurchase(ActionType action){

		if (action == ActionType.PURCHASE_CITY) Purchase.purchaseCity();
		else if (action == ActionType.PURCHASE_DEVELOPMENT) Purchase.purchaseDevelopmentCard();
		else if (action == ActionType.PURCHASE_ROAD) Purchase.purchaseRoad();
		else if (action == ActionType.PURCHASE_SETTLEMENT)	Purchase.purchaseSettlement();
	}	
	
	/**
	 * Initiates a trade of given type for the player.
	 * @param action
	 * 
	 * @pre action must be of 'trade' action category.
	 * @post action will be performed.
	 */

	public static void doTrade(ActionType action){
		
		if (action == ActionType.TRADE_BANK) Trade.tradeWithBank();
		else if (action == ActionType.TRADE_PLAYER)	Trade.tradeWithPlayer();		
	}	
	
	/**
	 * Performs a play card action of given type for the player.
	 * @param action
	 * 
	 * @pre action must be of 'playcard' action category.
	 * @post action will be performed.
	 */

	public static void playDevelopmentCard(ActionType action){
		
		if (action == ActionType.PLAYCARD_BUILDROADS) PlayCard.playBuildRoads();
		else if (action == ActionType.PLAYCARD_KNIGHT) PlayCard.playKnight();
		else if (action == ActionType.PLAYCARD_MONOPOLY) PlayCard.playMonopoly();
		else if (action == ActionType.PLAYCARD_YEAROFPLENTY) PlayCard.playYearOfPlenty();
		
	}
	
}

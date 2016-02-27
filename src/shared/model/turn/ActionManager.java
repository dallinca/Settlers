package shared.model.turn;

import client.Client;
import shared.model.Game;


/**
 * Action interface object for performing actions on one's turn while participating in a game.
 * @author jchrisw
 *
 */
public class ActionManager {

	Client client;
	private Trade TRADE = null;
	private Purchase PURCHASE = null;
	private PlayCard PLAYCARD = null;
	private Dice DICE = null;

	public ActionManager( Client c ){
		client = c;
		TRADE = new Trade(c);
		PURCHASE = new Purchase(c);
		PLAYCARD = new PlayCard(c);
		DICE = new Dice(c);
	}

	/**
	 * Performs an action of given type for the player.
	 * @param action
	 * 
	 * @pre None
	 * @post action will be performed.
	 */
	public void doAction(ActionType action){

		int i = client.getGame().getVersionNumber();
		client.getGame().setVersionNumber(i++);		

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
	public void canDoPurchase(ActionType action){
		if (action == ActionType.PURCHASE_DEVELOPMENT) PURCHASE.canDoPurchaseDevelopmentCard();
	}	


	/**
	 * Performs a purchase of given type for the player.
	 * @param action
	 * 
	 * @pre action must be of 'purchase' action category.
	 * @post action will be performed.
	 */
	public void doPurchase(ActionType action){
		if (action == ActionType.PURCHASE_DEVELOPMENT) PURCHASE.purchaseDevelopmentCard();
	}	
	
	/**
	 * TODO javadoc
	 * 
	 * @param action
	 * @param location
	 */
	public void canDoBuild(ActionType action){
		if (action == ActionType.PURCHASE_CITY) PURCHASE.canDoPurchaseCity();
		else if (action == ActionType.PURCHASE_ROAD) PURCHASE.canDoPurchaseRoad();
		else if (action == ActionType.PURCHASE_SETTLEMENT)	PURCHASE.canDoPurchaseSettlement();


	}
	/**
	 * TODO javadoc
	 * 
	 * @param action
	 * @param location
	 */
	public void doBuild(ActionType action, Object location){
		if (action == ActionType.PURCHASE_CITY) PURCHASE.purchaseCity(location);
		else if (action == ActionType.PURCHASE_ROAD) PURCHASE.purchaseRoad(location);
		else if (action == ActionType.PURCHASE_SETTLEMENT)	PURCHASE.purchaseSettlement(location);


	}

	/**
	 * Initiates a trade of given type for the player.
	 * @param action
	 * 
	 * @pre action must be of 'trade' action category.
	 * @post action will be performed.
	 */

	public void doTrade(ActionType action){

		if (action == ActionType.TRADE_BANK) TRADE.tradeWithBank();
		else if (action == ActionType.TRADE_PLAYER)	TRADE.tradeWithPlayer();		
	}	

	/**
	 * Performs a play card action of given type for the player.
	 * @param action
	 * 
	 * @pre action must be of 'playcard' action category.
	 * @post action will be performed.
	 */

	public void playDevelopmentCard(ActionType action){

		if (action == ActionType.PLAYCARD_BUILDROADS) PLAYCARD.playBuildRoads();
		else if (action == ActionType.PLAYCARD_KNIGHT) PLAYCARD.playKnight();
		else if (action == ActionType.PLAYCARD_MONOPOLY) PLAYCARD.playMonopoly();
		else if (action == ActionType.PLAYCARD_YEAROFPLENTY) PLAYCARD.playYearOfPlenty();

	}

}

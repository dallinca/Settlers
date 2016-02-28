package shared.model.turn;

import client.Client;
import shared.definitions.ResourceType;
import shared.model.Game;


/**
 * Action interface object for performing actions on one's turn while participating in a game.
 * @author jchrisw
 *
 */
public class ActionManager {
	
	private static ActionManager SINGLETON = null;


	private Trade TRADE = null;
	private Purchase PURCHASE = null;
	private PlayCard PLAYCARD = null;
	private Dice DICE = null;

	protected ActionManager(){
		TRADE = new Trade();
		PURCHASE = new Purchase();
		PLAYCARD = new PlayCard();
		DICE = new Dice();
	}
	
	public static ActionManager getInstance() {
		if(SINGLETON == null) {
			SINGLETON = new ActionManager();
		}
		return SINGLETON;
	}

	/**
	 * Performs an action of given type for the player.
	 * @param action
	 * 
	 * @pre None
	 * @post action will be performed.
	 */
	public void doAction(ActionType action){

		if (action.getCategory() == ActionType.PURCHASE) doPurchase(action);
		else if (action.getCategory() == ActionType.TRADE) doTrade(action);
		else if (action.getCategory() == ActionType.PLAYCARD) playDevelopmentCard(action);	

		Client.getInstance().notifyAll();
	}

	/**
	 * Performs a purchase of given type for the player.
	 * @param action
	 * 
	 * @pre action must be of 'purchase' action category.
	 * @post action will be performed.
	 */
	public boolean canDoPurchase(ActionType action){
		if (action == ActionType.PURCHASE_DEVELOPMENT) 
			return PURCHASE.canDoPurchaseDevelopmentCard();
		return false;
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
	public boolean canDoBuild(ActionType action){
		if (action == ActionType.PURCHASE_CITY) return PURCHASE.canDoPurchaseCity();
		else if (action == ActionType.PURCHASE_ROAD) return PURCHASE.canDoPurchaseRoad();
		else if (action == ActionType.PURCHASE_SETTLEMENT) return PURCHASE.canDoPurchaseSettlement();

		return false;

	}
	
	
	public boolean canDoPlay(ActionType action, ResourceType[] toPassIn) {
		if (action == ActionType.PLAYCARD_MONOPOLY) return PLAYCARD.canDoPlayMonopoly(toPassIn);
		else if (action == ActionType.PLAYCARD_YEAROFPLENTY) return PLAYCARD.canDoPlayYearOfPlenty(toPassIn);
		
		return false;
	}
	
	
	public boolean canDoPlay(ActionType action) {
		if (action == ActionType.PLAYCARD_BUILDROADS) return PLAYCARD.canDoPlayBuildRoads();
		else if (action == ActionType.PLAYCARD_MONUMENT) return PLAYCARD.canDoPlayMonument();
		else if (action == ActionType.PLAYCARD_KNIGHT) return PLAYCARD.canDoPlayKnight();

		return false;
	}
	
	/**
	 * TODO javadoc
	 * 
	 * @param action
	 * @param location
	 */
	public void doBuild(ActionType action, Object location){
		if (canDoBuild(action)) {	
			if (action == ActionType.PURCHASE_CITY) PURCHASE.purchaseCity(location);
			else if (action == ActionType.PURCHASE_ROAD) PURCHASE.purchaseRoad(location);
			else if (action == ActionType.PURCHASE_SETTLEMENT)	PURCHASE.purchaseSettlement(location);
		} 
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
	
	public void playDevelopmentCard(ActionType action, ResourceType[] toPassIn) {
		if (action == ActionType.PLAYCARD_MONOPOLY) PLAYCARD.playMonopoly(toPassIn);
		else if (action == ActionType.PLAYCARD_YEAROFPLENTY) PLAYCARD.playYearOfPlenty(toPassIn);
	}
	
	public void playDevelopmentCard(ActionType action){

		if (action == ActionType.PLAYCARD_BUILDROADS) PLAYCARD.playBuildRoads();
		else if (action == ActionType.PLAYCARD_KNIGHT) PLAYCARD.playKnight();
	}

}


























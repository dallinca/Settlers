package shared.model.turn;

import client.Client;
import client.ClientFacade;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
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
		System.out.println("ActionManager ActionType()");
		TRADE = new Trade();
		PURCHASE = new Purchase();
		PLAYCARD = new PlayCard();
		DICE = new Dice();
	}

	public static ActionManager getInstance() {
		System.out.println("ActionManager getInstance()");
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
		System.out.println("ActionManager doAction()");

		if (action.getCategory() == ActionType.PURCHASE) doPurchase(action);
		else if (action.getCategory() == ActionType.TRADE)
			try {
				doTrade(action, null, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if (action.getCategory() == ActionType.PLAYCARD)
			try {
				playDevelopmentCard(action);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

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
		System.out.println("ActionManager canDoPurchase()");
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
		System.out.println("ActionManager doPurchase()");
		if (action == ActionType.PURCHASE_DEVELOPMENT && canDoPurchase(action)) PURCHASE.purchaseDevelopmentCard();
	}	

	/**
	 * TODO javadoc
	 * 
	 * @param action
	 * @param location
	 */
	public boolean canDoBuild(ActionType action, Object location){
		System.out.println("ActionManager canDoBuild()");
		if (action == ActionType.PURCHASE_CITY) return PURCHASE.canDoPurchaseCity(location);
		else if (action == ActionType.PURCHASE_ROAD) return PURCHASE.canDoPurchaseRoad(location);
		else if (action == ActionType.PURCHASE_SETTLEMENT) return PURCHASE.canDoPurchaseSettlement(location);

		return false;

	}

	/**
	 * TODO javadoc
	 * 
	 * @param action
	 * @param location
	 * @throws Exception 
	 */
	public void doBuild(ActionType action, Object location) throws Exception{
		System.out.println("ActionManager doBuild()");
		//if (canDoBuild(action)) {	
		if (action == ActionType.PURCHASE_CITY) PURCHASE.purchaseCity(location);
		else if (action == ActionType.PURCHASE_ROAD) PURCHASE.purchaseRoad(location);
		else if (action == ActionType.PURCHASE_SETTLEMENT)	PURCHASE.purchaseSettlement(location);
		else throw new Exception("Should not have been able to call doBuild method");
		//} 
	}


	public boolean canDoPlay(ActionType action, ResourceType[] toPassIn) {
		System.out.println("ActionManager canDoPlay()");
		if (action == ActionType.PLAYCARD_MONOPOLY) return PLAYCARD.canDoPlayMonopoly(toPassIn);
		else if (action == ActionType.PLAYCARD_YEAROFPLENTY) return PLAYCARD.canDoPlayYearOfPlenty(toPassIn);

		return false;
	}


	public boolean canDoPlay(ActionType action) {
		System.out.println("ActionManager canDoPlay()");
		if (action == ActionType.PLAYCARD_BUILDROADS) return PLAYCARD.canDoPlayBuildRoads();
		else if (action == ActionType.PLAYCARD_MONUMENT) return PLAYCARD.canDoPlayMonument();
		else if (action == ActionType.PLAYCARD_KNIGHT) {
			System.out.println("Attempting to play a knight!");
			return PLAYCARD.canDoPlayKnight();
		}

		return false;
	}

	/**
	 * Checks to see if the trade can even happen
	 * @pre valid action type, and that the trade is happening on the correct turn (current player has to be trading with somebody)
	 * 
	 * @param action
	 * @return
	 */
	public boolean canDoTrade(ActionType action) {
		System.out.println("ActionManager canDoTrade()");
		if (action == ActionType.TRADE_BANK) return TRADE.canDoTradeWithBank(null, null);
		else if (action == ActionType.TRADE_PLAYER) return TRADE.canDoTradeWithPlayer();

		return false;
	}



	/**
	 * Initiates a trade of given type for the player.
	 * @param action
	 * @throws Exception 
	 * 
	 * @pre action must be of 'trade' action category.
	 * @post action will be performed.
	 */
	public void doTrade(ActionType action, ResourceType resourceIn, ResourceType resourceOut) throws Exception{
		System.out.println("ActionManager doTrade()");
		if (canDoTrade(action)) {
			try {
				if (action == ActionType.TRADE_BANK) TRADE.tradeWithBank(resourceIn, resourceOut);
				else if (action == ActionType.TRADE_PLAYER)	TRADE.tradeWithPlayer();
			} catch(Exception e) {
				System.out.println("Should not have been able to trade");
			}
		} else throw new Exception("Should not have been able to call doTrade method");
	}	

	/**
	 * Performs a play card action of given type for the player.
	 * @param action
	 * @throws Exception 
	 * 
	 * @pre action must be of 'playcard' action category.
	 * @post action will be performed.
	 */

	public void playDevelopmentCard(ActionType action, ResourceType[] toPassIn) throws Exception {
		System.out.println("ActionManager playDevelopmentCard()");
		if (canDoPlay(action, toPassIn)) {
			try {
				if (action == ActionType.PLAYCARD_MONOPOLY) PLAYCARD.playMonopoly(toPassIn);
				else if (action == ActionType.PLAYCARD_YEAROFPLENTY) PLAYCARD.playYearOfPlenty(toPassIn);
			} catch (Exception e) {
				System.out.println("Something went wrong when trying to play either your Monopoly or Year of Plenty card.");
			}
		} else throw new Exception("Should not have been able to call the playDevelopmentCard (for monopoly and year of Plenty) method");
	}

	public void playDevelopmentCard(ActionType action) throws Exception{
		System.out.println("ActionManager playDevelopmentCard()");
		if (canDoPlay(action)) {
			try {
				//Fix parameters as they are passed in from above coming from a view generated by the implementation of the state pattern.
				if (action == ActionType.PLAYCARD_BUILDROADS) PLAYCARD.playBuildRoads(null, null);
				else if (action == ActionType.PLAYCARD_KNIGHT) PLAYCARD.playKnight(null, 0);
			} catch (Exception e) {
				System.out.println("Something went wrong when trying to play either your Soldier or Road Builder card.");
			}
		} else throw new Exception("Should not have been able to call the playDevelopmentCard method");
	}

	
/*	private void placeRobber(ActionType robberPlace, HexLocation hexLoc) {
		ClientFacade.getInstance().robPlayer(hexLoc, victimIndex)
		
	}*/
	
}


























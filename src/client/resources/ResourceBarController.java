package client.resources;

import java.util.*;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Game;
import shared.model.turn.ActionManager;
import client.Client;
import client.ClientFacade;
import client.base.*;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		System.out.println("ResourceBarController ResourceBarController()");
		elementActions = new HashMap<ResourceBarElement, IAction>();
		
		Client.getInstance().addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		System.out.println("ResourceBarController getView()");
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be execute
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {
		System.out.println("ResourceBarController setElementAction()");
		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		System.out.println("ResourceBarController buildRoad()");
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		System.out.println("ResourceBarController buildSettlement()");
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		System.out.println("ResourceBarController buildCity()");
		executeElementAction(ResourceBarElement.CITY);
		
	}
	
	/** Checks to see if a Player can buy a card and enables or disables the card
	 * 
	 *  @pre none
	 *  @post player can buy a development card or he can't
	 */
	@Override
	public void buyCard() {
		System.out.println("ResourceBarController buyCard()");
		
		executeElementAction(ResourceBarElement.BUY_CARD);
		
		boolean isPlayersTurn = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).isPlayersTurn();
		if (isPlayersTurn) {
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
		} else {
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}
	}
	
	/** Player can play a card
	 * 
	 *  @pre player has a development card to play
	 *  @post player plays development card
	 */
	@Override
	public void playCard() {
		System.out.println("ResourceBarController playCard()");
		getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
		executeElementAction(ResourceBarElement.PLAY_CARD);		
	}
	
	private void executeElementAction(ResourceBarElement element) {
		System.out.println("ResourceBarController executeElementAction()");
		
		if (elementActions.containsKey(element)) {
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	/**
	 * Updates the ResourceBarController to reflect current situation in game object.
	 * Allows the player to build a road, settlement, city. Buy a development card
	 * 
	 * @pre Game state has changed
	 * @post build road, settlement,city. buy development card. finish turn.
	 * 
	 * @param Observable o 
	 * @param Object arg 
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("ResourceBarController update()");
		
		// If the game is null just return
		if(Client.getInstance().getGame() == null) {
			return;
		}

		/*
		Game game = Client.getInstance().getGame();
		System.out.println("Client.getInstance().getUserId(): " + Client.getInstance().getUserId());
		System.out.println("Client.getInstance().getGame(): " + Client.getInstance().getGame());
		System.out.println("Client.getInstance().getGame(): " + Client.getInstance().getGame());*/
		
		enablePurchases();
		updateAmounts();
		
		/*
		System.out.println("This is the STATUS! " + Client.getInstance().getGame().getStatus());
		System.out.println("status: " + Client.getInstance().getGame().getStatus());
		System.out.println("turnNumber: " + Client.getInstance().getGame().getTurnNumber());
		System.out.println("canDoPlayerBuildRoad: " + Client.getInstance().getGame().canDoPlayerBuildRoad(Client.getInstance().getUserId()));*/
		
		if(Client.getInstance().getGame().getTurnNumber() < 2 && Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getUserId())) {
			System.out.println("\n\nWe should put up the Building Modal!\n\n");
			if(Client.getInstance().getGame().canDoPlayerBuildRoad(Client.getInstance().getUserId())) {
				buildRoad();
			} else if(Client.getInstance().getGame().canDoPlayerBuildSettlement(Client.getInstance().getUserId())) {
				buildSettlement();
			} else {
				// We must end the turn!
				ClientFacade.getInstance().finishTurn();
			}
		}
	}
	
	/**
	 * To be called from the update function. Enables or disables purchase buttons from model.
	 * 
	 */
	private void enablePurchases() {
		boolean canBuildRoad = Client.getInstance().getGame().canDoPlayerBuildRoad(Client.getInstance().getUserId());
		
		if(canBuildRoad){
			getView().setElementEnabled(ResourceBarElement.ROAD, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.ROAD, false);
		}
		
		boolean canBuildSettlement = Client.getInstance().getGame().canDoPlayerBuildSettlement(Client.getInstance().getUserId());
		if(canBuildSettlement){
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
		}

		boolean canBuildCity = Client.getInstance().getGame().canDoPlayerBuildCity(Client.getInstance().getUserId());
		if(canBuildCity){
			getView().setElementEnabled(ResourceBarElement.CITY, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.CITY, false);
		}
		
		boolean canBuyCard = Client.getInstance().getGame().canDoPlayerBuyDevelopmentCard(Client.getInstance().getUserId());
		if(canBuyCard){
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}
	}
	
	/**
	 * To be called from the update function. Sets the proper amounts of items from the model.
	 * 
	 */
	private void updateAmounts() {
		// Set resource amounts
		getView().setElementAmount(ResourceBarElement.WOOD, Client.getInstance().getGame().getNumberResourcesOfType(Client.getInstance().getUserId(), ResourceType.WOOD));
		getView().setElementAmount(ResourceBarElement.BRICK, Client.getInstance().getGame().getNumberResourcesOfType(Client.getInstance().getUserId(), ResourceType.BRICK));
		getView().setElementAmount(ResourceBarElement.ORE, Client.getInstance().getGame().getNumberResourcesOfType(Client.getInstance().getUserId(), ResourceType.ORE));
		getView().setElementAmount(ResourceBarElement.SHEEP, Client.getInstance().getGame().getNumberResourcesOfType(Client.getInstance().getUserId(), ResourceType.SHEEP));
		getView().setElementAmount(ResourceBarElement.WHEAT, Client.getInstance().getGame().getNumberResourcesOfType(Client.getInstance().getUserId(), ResourceType.WHEAT));
		
		// Set piece amounts
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, Client.getInstance().getGame().getNumberUnplayedSettlements(Client.getInstance().getUserId()));
		getView().setElementAmount(ResourceBarElement.CITY, Client.getInstance().getGame().getNumberUnplayedCities(Client.getInstance().getUserId()));
		getView().setElementAmount(ResourceBarElement.ROAD, Client.getInstance().getGame().getNumberUnplayedRoads(Client.getInstance().getUserId()));
	
		// Set number of soldiers played
		getView().setElementAmount(ResourceBarElement.SOLDIERS, Client.getInstance().getGame().getNumberOfSoldiersPlayed((Client.getInstance().getUserId())));
	
	}

}


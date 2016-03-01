package client.resources;

import java.util.*;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.Client;
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
		//elementActions.put(ResourceBarElement.ROAD, )
	}

	@Override
	public void buildSettlement() {
		System.out.println("ResourceBarController buildSettlement()");
		executeElementAction(ResourceBarElement.SETTLEMENT);
		//System.out.println(elementActions);
	}

	@Override
	public void buildCity() {
		System.out.println("ResourceBarController buildCity()");
		executeElementAction(ResourceBarElement.CITY);
		
	}

	@Override
	public void buyCard() {
		System.out.println("ResourceBarController buyCard()");
		
		executeElementAction(ResourceBarElement.BUY_CARD);
		
		boolean isPlayersTurn = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).isPlayersTurn();
		if (isPlayersTurn) {
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
			executeElementAction(ResourceBarElement.BUY_CARD);
		} else {
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}
	}

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

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("ResourceBarController update()");
		
		if (Client.getInstance().getGame() != null) {
		boolean canBuildRoad = Client.getInstance().getGame().canDoCurrentPlayerBuildRoad(Client.getInstance().getUserId());
		if(canBuildRoad){
			getView().setElementEnabled(ResourceBarElement.ROAD, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.ROAD, false);
		}

		boolean canBuildSettlement = Client.getInstance().getGame().canDoCurrentPlayerBuildSettlement(Client.getInstance().getUserId());
		if(canBuildSettlement){
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
		}

		boolean canBuildCity = Client.getInstance().getGame().canDoCurrentPlayerBuildCity(Client.getInstance().getUserId());
		if(canBuildCity){
			getView().setElementEnabled(ResourceBarElement.CITY, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.CITY, false);
		}
		
		boolean canBuyCard = Client.getInstance().getGame().canDoCurrentPlayerBuyDevelopmentCard(Client.getInstance().getUserId());
		if(canBuyCard){
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		}else{
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}
		
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
		}
	}

}


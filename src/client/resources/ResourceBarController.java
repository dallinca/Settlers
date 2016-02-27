package client.resources;

import java.util.*;

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
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {
		System.out.println("ResourceBarController setElementAction()");
		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		System.out.println("ResourceBarController buildRoad()");
		
		boolean canBuildRoad = Client.getInstance().getGame().canDoCurrentPlayerBuildRoad(Client.getInstance().getUserId());
		if(canBuildRoad){
			//elementActions.get(ResourceBarElement.ROAD).;
			getView().setElementEnabled(ResourceBarElement.ROAD, true);
			executeElementAction(ResourceBarElement.ROAD);
		}else{
			getView().setElementEnabled(ResourceBarElement.ROAD, false);
		}
	}

	@Override
	public void buildSettlement() {
		System.out.println("ResourceBarController buildSettlement()");
		
		boolean canBuildSettlement = Client.getInstance().getGame().canDoCurrentPlayerBuildSettlement(Client.getInstance().getUserId());
		if(canBuildSettlement){
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
			executeElementAction(ResourceBarElement.SETTLEMENT);
		}else{
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
		}
	}

	@Override
	public void buildCity() {
		System.out.println("ResourceBarController buildCity()");
		
		boolean canBuildCity = Client.getInstance().getGame().canDoCurrentPlayerBuildCity(Client.getInstance().getUserId());
		if(canBuildCity){
			getView().setElementEnabled(ResourceBarElement.CITY, true);
			executeElementAction(ResourceBarElement.CITY);
		}else{
			getView().setElementEnabled(ResourceBarElement.CITY, false);
		}
	}

	@Override
	public void buyCard() {
		System.out.println("ResourceBarController buyCard()");
		
		boolean canBuyCard = Client.getInstance().getGame().canDoCurrentPlayerBuyDevelopmentCard(Client.getInstance().getUserId());
		if(canBuyCard){
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
			executeElementAction(ResourceBarElement.BUY_CARD);
		}else{
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}
	}

	@Override
	public void playCard() {
		System.out.println("ResourceBarController playCard()");
	
		boolean canPlayCard = Client.getInstance().getGame().canDoCurrentPlayerUseDevelopmentCard(devCardType) 
		if(canPlayCard){
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
			executeElementAction(ResourceBarElement.PLAY_CARD);
		}else{
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		}
		
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
		// TODO Auto-generated method stub
		
	}

}


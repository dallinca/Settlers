package client.resources;

import java.util.*;

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
		//elementActions.get(ResourceBarElement.ROAD).;
		getView().setElementEnabled(ResourceBarElement.ROAD, false);
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

	@Override
	public void buyCard() {
		System.out.println("ResourceBarController buyCard()");
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		System.out.println("ResourceBarController playCard()");
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
		// TODO Auto-generated method stub
		
	}

}


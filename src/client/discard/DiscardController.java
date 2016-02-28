package client.discard;

import shared.definitions.*;

import java.util.*;

import client.Client;
import client.base.*;
import client.misc.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	private int amountToDiscard, totalToDiscard;
	private ArrayList<ResourceType> wood, sheep, ore, brick, wheat;
	
	private int numWood, numWheat, numSheep, numOre, numBrick; 
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
		System.out.println("DiscardController DiscardController()");
		
		 wood = new ArrayList<ResourceType>();
		 sheep = new ArrayList<ResourceType>();
		 ore = new ArrayList<ResourceType>();
		 brick = new ArrayList<ResourceType>();
		 wheat = new ArrayList<ResourceType>();
	}

	public IDiscardView getDiscardView() {
		System.out.println("DiscardController getDiscardView()");
		return (IDiscardView)super.getView();
		
	}
	
	public IWaitView getWaitView() {
		System.out.println("DiscardController getWaitView()");
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		System.out.println("DiscardController increaseAmount()");
		
		amountToDiscard++;
		
		//numWood, numWheat, numSheep, numOre, numBrick
		if( resource == ResourceType.BRICK){
			brick.add(resource);
			if(brick.size() >= numWood){
				getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			}
		}
		else if(resource == ResourceType.ORE){
			ore.add(resource);
			if(ore.size() >= numOre){
				getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			}
		}
		else if(resource == ResourceType.SHEEP){
			sheep.add(resource);
			if(sheep.size() >= numSheep){
				
			}
		}
		else if(resource == ResourceType.WHEAT){
			tradeRate = tradeWheat;
		}
		else if(resource == ResourceType.WOOD){
			tradeRate = tradeWood;
		}
		
		if(amountToDiscard >= totalToDiscard){

			getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			getDiscardView().setDiscardButtonEnabled(true);
		}else{
			getDiscardView().setDiscardButtonEnabled(false);
		}

	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		System.out.println("DiscardController decreaseAmount()");
		amountToDiscard--;
		
		if( resource == ResourceType.BRICK){
			//remove the last element
			brick.remove(brick.size() - 1);
			if(brick.size() <= numWood){
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}
		}
		
		
		
		if(amountToDiscard >= totalToDiscard){
			getDiscardView().setDiscardButtonEnabled(true);
		}else{
			getDiscardView().setDiscardButtonEnabled(false);
		}
	}

	@Override
	public void discard() {
		System.out.println("DiscardController discard()");
		
		numWood = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WOOD);
		numWheat = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WHEAT);
		numSheep = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.SHEEP);
		numOre = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.ORE);
		numBrick = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.BRICK);
		
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, numWood);
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, numWheat);
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, numSheep);
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, numOre);
		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, numBrick);
			
		totalToDiscard = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getResourceCardHandSize();
		getDiscardView().setStateMessage(amountToDiscard+"/"+totalToDiscard);
		
		if(amountToDiscard == totalToDiscard){
			//getDiscardView().closeModal();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DiscardController update()");
		// TODO Auto-generated method stub
	}

}


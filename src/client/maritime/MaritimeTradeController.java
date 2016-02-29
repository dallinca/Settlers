package client.maritime;

import shared.definitions.*;
import shared.model.player.ResourceCardHand;
import shared.model.turn.ActionManager;
import shared.model.turn.ActionType;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.Client;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	private int tradeWood, tradeWheat, tradeSheep, tradeOre, tradeBrick;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);
		System.out.println("MaritimeTradeController MaritimeTradeController()");

		setTradeOverlay(tradeOverlay);
		Client.getInstance().addObserver(this);
	}

	/**
	 * TODO
	 * 
	 */
	public IMaritimeTradeView getTradeView() {
		System.out.println("MaritimeTradeController getTradeView()");
		
		return (IMaritimeTradeView)super.getView();
	}

	/**
	 * TODO
	 * 
	 */
	public IMaritimeTradeOverlay getTradeOverlay() {
		System.out.println("MaritimeTradeController getTradeOverlay()");		
		return tradeOverlay;
	}

	/**
	 * TODO
	 * 
	 */
	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		System.out.println("MaritimeTradeController setTradeOverlay()");
		this.tradeOverlay = tradeOverlay;
	}

	/**
	 * Checks if it is the players turn and if he has enough resources to do a trade
	 * 
	 */
	@Override
	public void startTrade() {
		System.out.println("MaritimeTradeController startTrade()");
		
		boolean canTrade = ActionManager.getInstance().canDoPurchase(ActionType.TRADE_BANK);
		boolean isPlayersTurn = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).isPlayersTurn();
		
		if(isPlayersTurn && canTrade){
			
			ArrayList<ResourceType> resourceArray = new ArrayList<ResourceType>();
			
			int numWood = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WOOD);
			int numWheat = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WHEAT);
			int numSheep = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.SHEEP);
			int numOre = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.ORE);
			int numBrick = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.BRICK);
			
			tradeWood = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.WOOD);
			tradeWheat = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.WHEAT);
			tradeSheep = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.SHEEP);
			tradeOre = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.ORE);
			tradeBrick = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.BRICK);
			
			if(numWood >= tradeWood || numWood > 3){
				resourceArray.add(ResourceType.WOOD);
			}
			if(numWheat >= tradeWheat || numWheat > 3){
				resourceArray.add(ResourceType.WHEAT);
			}
			if(numSheep >= tradeSheep || numSheep > 3){
				resourceArray.add(ResourceType.SHEEP);
			}
			if(numOre >= tradeOre || numOre > 3){
				resourceArray.add(ResourceType.ORE);
			}
			if(numBrick >= tradeBrick || numBrick > 3){
				resourceArray.add(ResourceType.BRICK);
			}
			if(resourceArray.size() > 0){
				ResourceType [] resourceType = new ResourceType[resourceArray.size()];
				resourceArray.toArray(resourceType);
				getTradeOverlay().showGiveOptions(resourceType);
				getTradeOverlay().setStateMessage("choose what to give up");
				getTradeOverlay().setTradeEnabled(true);
			}else{
				getTradeOverlay().setStateMessage("you don't have enough resources");
				getTradeOverlay().setTradeEnabled(false);
			}
		}else{
			getTradeOverlay().setStateMessage("not your turn");
			getTradeOverlay().setTradeEnabled(false);
		}
		getTradeOverlay().showModal();
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void makeTrade() {
		System.out.println("MaritimeTradeController makeTrade()");
		//Client.getInstance().getGame().doMaritimeTrade(tradeIn, receive);
		
		
		getTradeOverlay().closeModal();
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void cancelTrade() {
		System.out.println("MaritimeTradeController cancelTrade()");
		getTradeOverlay().closeModal();
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void setGetResource(ResourceType resource) {
		System.out.println("MaritimeTradeController setGetResource()");
		getTradeOverlay().setStateMessage("choose what to get");
		/*
		Client.getInstance().getGame()
		showGetOptions(ResourceType[] enabledResources);
		getTradeOverlay().selectGetOption(selectedResource, amount);
		tradeGet = resource;*/
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void setGiveResource(ResourceType resource) {
		System.out.println("MaritimeTradeController setGiveResource()");
		
		int tradeRate = 4;
		if( resource == ResourceType.BRICK){
			tradeRate = tradeBrick;
		}
		else if(resource == ResourceType.ORE){
			tradeRate = tradeOre;
		}
		else if(resource == ResourceType.SHEEP){
			tradeRate = tradeSheep;
		}
		else if(resource == ResourceType.WHEAT){
			tradeRate = tradeWheat;
		}
		else if(resource == ResourceType.WOOD){
			tradeRate = tradeWood;
		}
		getTradeOverlay().selectGiveOption(resource, tradeRate);
		//tradein = resource;
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void unsetGetValue() {
		System.out.println("MaritimeTradeController unsetGetValue()");
		getTradeOverlay().hideGetOptions();
	}

	/**
	 * TODO
	 * WHAT IS HAPPENING AHHHHHHHH! 
	 */
	@Override
	public void unsetGiveValue() {
		System.out.println("MaritimeTradeController unsetGiveValue()");
		getTradeOverlay().hideGiveOptions();
	}

	/**
	 * TODO
	 * 
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("MaritimeTradeController update()");
		// TODO Auto-generated method stub
		
	}

}


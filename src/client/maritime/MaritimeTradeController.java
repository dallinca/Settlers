package client.maritime;

import shared.definitions.*;
import shared.model.player.ResourceCardHand;
import shared.model.turn.ActionManager;
import shared.model.turn.ActionType;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.Client;
import client.ClientException;
import client.ClientFacade;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	private int tradeWood, tradeWheat, tradeSheep, tradeOre, tradeBrick;
	private ResourceType getType;
	private ResourceType giveType;
	ResourceType[] array = null;
	
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
		boolean isPlayersTurn = Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getUserId());
		ArrayList<ResourceType> resourceArray = new ArrayList<ResourceType>();
		
		if(isPlayersTurn){
			//This used to check the canDoTrade by calling the Action Manager, but that applies to the get options, in the selectGetType, which has been delegated to
			//The Client to check. So that's why it is not here.
			
			int numWood = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.WOOD);
			int numWheat = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.WHEAT);
			int numSheep = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.SHEEP);
			int numOre = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.ORE);
			int numBrick = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.BRICK);
			
			tradeWood = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getTradeRate(ResourceType.WOOD);
			tradeWheat = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getTradeRate(ResourceType.WHEAT);
			tradeSheep = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getTradeRate(ResourceType.SHEEP);
			tradeOre = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getTradeRate(ResourceType.ORE);
			tradeBrick = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getTradeRate(ResourceType.BRICK);
			
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
				getTradeOverlay().setStateMessage("Choose what to give up");
				getTradeOverlay().setTradeEnabled(true);
				//getTradeOverlay().showGetOptions(enabledResources);
			}
			else {
				//set message
				getTradeOverlay().setStateMessage("You don't have enough resources");
				ResourceType [] resourceType = new ResourceType[resourceArray.size()];
				resourceArray.toArray(resourceType);
				getTradeOverlay().showGiveOptions(resourceType);
				
				//this disables everything because the array is empty at this point, but not null because it was defined earlier.
				getTradeOverlay().showGiveOptions(resourceType);
				getTradeOverlay().setTradeEnabled(false);
				
			}
		} else {
			
			ArrayList<ResourceType> disableEverything = new ArrayList<ResourceType>();
			
			//Convert to array
			ResourceType [] resourceType = new ResourceType[disableEverything.size()];
			disableEverything.toArray(resourceType);
			
			//this disables everything because the array is empty at this point, but not null because it was defined earlier.
			getTradeOverlay().showGiveOptions(resourceType);
			
			getTradeOverlay().showGetOptions(resourceType);			
			getTradeOverlay().setStateMessage("Not your turn");
			//getTradeOverlay().hideGiveOptions();
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().hideGetOptions();
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
		int tradeRate = 4;
		
		switch(giveType) {
			
			case WHEAT: 
				tradeRate = tradeWheat = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.WHEAT);
				break;
			case WOOD: 
				tradeRate = tradeWood = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.WOOD);
				break;
			case ORE:
				tradeRate = tradeOre = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.ORE);
				break;
			case BRICK:
				tradeRate = tradeBrick = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.BRICK);
				break;
			case SHEEP:
				tradeRate = tradeSheep = Client.getInstance().getGame().getCurrentPlayer().getTradeRate(ResourceType.SHEEP);
				break;
			default:
				tradeRate = 4;
				break;
		}
		System.out.println("You found me!");
		//try {
			ClientFacade.getInstance().maritimeTrade(tradeRate, giveType, getType);
		//} catch (ClientException e) {
			//e.printStackTrace();
			//System.out.println("Something went wrong with the trade.");
		//}
		
		getTradeOverlay().closeModal();
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void cancelTrade() {
		System.out.println("MaritimeTradeController cancelTrade()");
		giveType = null;
		getType = null;
		getTradeOverlay().closeModal();
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void setGetResource(ResourceType resource) {
		System.out.println("MaritimeTradeController setGetResource()");
		
		
		getType = resource;
		getTradeOverlay().selectGetOption(resource, 1);
		
		//getTradeOverlay().selectGetOption(selectedResource, amount)
		
		
		/*Client.getInstance().getGame();
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
		
		if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getPlayerIndex())) {	
			
			System.out.println("MaritimeTradeController setGiveResource()");
			getTradeOverlay().setStateMessage("Choose what to get");
					
			giveType = resource;
			
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
			ResourceType[] checking = {ResourceType.BRICK, ResourceType.WHEAT, ResourceType.SHEEP, ResourceType.WOOD, ResourceType.ORE};
			ArrayList<ResourceType> enabledResources = new ArrayList<ResourceType>();
			
			for (int i = 0; i < checking.length; i++) {
				 if( Client.getInstance().getGame().canDoPlayerDoMaritimeTrade(giveType, checking[i]) == true) {
					 enabledResources.add(checking[i]);
				 }
			}
			
			
			array = new ResourceType[enabledResources.size()];
			enabledResources.toArray(array);
			
			getTradeOverlay().showGetOptions(enabledResources.toArray(array));
			//tradein = resource;
		} else {
			ArrayList<ResourceType> enabledResources = new ArrayList<ResourceType>();
			
			ResourceType [] resourceType = new ResourceType[enabledResources.size()];
			enabledResources.toArray(resourceType);
			
			getTradeOverlay().showGetOptions(resourceType);
		}
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void unsetGetValue() {
		if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getPlayerIndex())) {
			System.out.println("MaritimeTradeController unsetGetValue()");
			getType = null;
			getTradeOverlay().showGetOptions(array);
		}
	}

	/**
	 * TODO
	 * WHAT IS HAPPENING AHHHHHHHH! 
	 */
	@Override
	public void unsetGiveValue() {
		if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getPlayerIndex())) {
			System.out.println("MaritimeTradeController unsetGiveValue()");
			giveType = null;
			//getTradeOverlay().showGiveOptions();
			getTradeOverlay().closeModal();
			startTrade();
			//getTradeOverlay().hideGiveOptions();
		}
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
		
		// If the game is null just return
		if(Client.getInstance().getGame() == null) {
			return;
		} else if (Client.getInstance().getGame().getStatus().equals("Robbing")) {
			return;
		}
		
		if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getPlayerIndex())) {

				getTradeView().enableMaritimeTrade(true);
		} else {
			//getTradeView().enableMaritimeTrade(false);
			
			ArrayList<ResourceType> disableEverything = new ArrayList<ResourceType>();
			
			//Convert to array
			ResourceType [] resourceType = new ResourceType[disableEverything.size()];
			disableEverything.toArray(resourceType);
			
			//this disables everything because the array is empty at this point, but not null because it was defined earlier.
			getTradeOverlay().showGiveOptions(resourceType);
			getTradeOverlay().hideGetOptions();
				
		}
		
	
	}

}


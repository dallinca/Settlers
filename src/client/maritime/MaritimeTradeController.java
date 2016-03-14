package client.maritime;

import shared.definitions.*;
import shared.model.Game;
import shared.model.player.Player;
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
	 * Gets the trade view
	 * @pre tradeview not null
	 */
	public IMaritimeTradeView getTradeView() {
		System.out.println("MaritimeTradeController getTradeView()");
		
		return (IMaritimeTradeView)super.getView();
	}

	/**
	 * Gets the trade overlay
	 * @pre the trade overlay is not null
	 */
	public IMaritimeTradeOverlay getTradeOverlay() {
		System.out.println("MaritimeTradeController getTradeOverlay()");		
		return tradeOverlay;
	}

	/**
	 * Sets your trade overlay
	 * @pre you are not setting it to something that is null
	 */
	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		System.out.println("MaritimeTradeController setTradeOverlay()");
		this.tradeOverlay = tradeOverlay;
	}

	/**
	 * Checks if it is the players turn and if he has enough resources to do a trade
	 * @pre you are doing this on your turn
	 */
	@Override
	public void startTrade() {
		System.out.println("MaritimeTradeController startTrade()");
		boolean isPlayersTurn = Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getUserId());
		ArrayList<ResourceType> resourceArray = new ArrayList<ResourceType>();
		
		if(isPlayersTurn){
			//This used to check the canDoTrade by calling the Action Manager, but that applies to the get options, in the selectGetType, which has been delegated to
			//The Client to check. So that's why it is not here.
			Game game = Client.getInstance().getGame();
			Client c = Client.getInstance();
			Player p = game.getPlayerByID(c.getUserId());
			
			int numWood = p.getNumberResourcesOfType(ResourceType.WOOD);
			int numWheat = p.getNumberResourcesOfType(ResourceType.WHEAT);
			int numSheep = p.getNumberResourcesOfType(ResourceType.SHEEP);
			int numOre = p.getNumberResourcesOfType(ResourceType.ORE);
			int numBrick = p.getNumberResourcesOfType(ResourceType.BRICK);
			
			tradeWood = p.getTradeRate(ResourceType.WOOD);
			tradeWheat = p.getTradeRate(ResourceType.WHEAT);
			tradeSheep = p.getTradeRate(ResourceType.SHEEP);
			tradeOre = p.getTradeRate(ResourceType.ORE);
			tradeBrick = p.getTradeRate(ResourceType.BRICK);
			
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
	 * This method calculates the rate at which you can trade, and then makes the necessary trade based on that number
	 * @pre you have been verified that you can trade.
	 */
	@Override
	public void makeTrade() {
		System.out.println("MaritimeTradeController makeTrade()");
		//Client.getInstance().getGame().doMaritimeTrade(tradeIn, receive);
		int tradeRate = 4;
		
		Game game = Client.getInstance().getGame();
		Player p = game.getCurrentPlayer();
		
		switch(giveType) {
			
			case WHEAT: 
				tradeRate = tradeWheat = p.getTradeRate(ResourceType.WHEAT);
				break;
			case WOOD: 
				tradeRate = tradeWood = p.getTradeRate(ResourceType.WOOD);
				break;
			case ORE:
				tradeRate = tradeOre = p.getTradeRate(ResourceType.ORE);
				break;
			case BRICK:
				tradeRate = tradeBrick = p.getTradeRate(ResourceType.BRICK);
				break;
			case SHEEP:
				tradeRate = tradeSheep = p.getTradeRate(ResourceType.SHEEP);
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
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().hideGiveOptions();
		getTradeOverlay().closeModal();
	}

	/**
	 * You can click cancel and it will close the modal by calling this function
	 * @pre the modal was already open upon calling this method
	 */
	@Override
	public void cancelTrade() {
		System.out.println("MaritimeTradeController cancelTrade()");
		giveType = null;
		getType = null;
		getTradeOverlay().closeModal();
	}

	/**
	 * When you click on a resource to receive from the trade, this method is called.
	 * @pre the modal was up and you clicked a valid resource type
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
	 * When you click on what you want to give up, this method is called.
	 * @pre you have the resources you are trying to give up. 
	 */
	@Override
	public void setGiveResource(ResourceType resource) {
		System.out.println("MaritimeTradeController setGiveResource()");
		
		if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getUserId())) {	
			
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
			
			getTradeOverlay().showGetOptions(array);
			//tradein = resource;
		} else {
			ArrayList<ResourceType> enabledResources = new ArrayList<ResourceType>();
			
			ResourceType [] resourceType = new ResourceType[enabledResources.size()];
			enabledResources.toArray(resourceType);
			
			getTradeOverlay().showGetOptions(resourceType);
		}
	}

	/**
	 * When you click the circle arrow, this method is called which clears your previous choice of a resource to get
	 * @pre it is your turn, and the modal is actually open
	 */
	@Override
	public void unsetGetValue() {
		if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getUserId())) {
			System.out.println("MaritimeTradeController unsetGetValue()");
			getType = null;
			getTradeOverlay().showGetOptions(array);
		}
	}

	/**
	 * This method is called when you click the reset button and then it returns you to previous view.
	 * The turn thing is technically not needed, but I believe it adds security to our code. Less breaks this way if something goes awry 
	 * @pre the modal to be open and it needs to be your turn for this symbol to even show up to be clicked on 
	 */
	@Override
	public void unsetGiveValue() {
		if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getUserId())) {
			System.out.println("MaritimeTradeController unsetGiveValue()");
			giveType = null;
			//getTradeOverlay().showGiveOptions();
			getTradeOverlay().closeModal();
			startTrade();
			//getTradeOverlay().hideGiveOptions();
		}
	}

	/**
	 * When the observable calls it's notify method, this method is called to ensure that this controller is up to date. However, most if not all of what this controller needs to know loads when a user clicks on a button rather than 
	 * from the Client Facade via this function.
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
		} 
		
		//This used to do more but it got moved to up above, so it technically makes the code just prior to this deprecated but it may be of use later
		/*if (Client.getInstance().getGame().getTurnNumber() < 2) {
			getTradeView().enableMaritimeTrade(false);
		}*/
		
	
	}

}


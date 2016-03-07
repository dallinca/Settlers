package client.discard;

import shared.definitions.*;
import shared.model.Game;
import shared.model.player.Player;

import java.util.*;

import client.Client;
import client.ClientFacade;
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
		Client.getInstance().addObserver(this);
		//int woodCount, sheepCount, oreCount, brickCount, wheatCount;

	}

	public IDiscardView getDiscardView() {
		System.out.println("DiscardController getDiscardView()");
		return (IDiscardView)super.getView();

	}

	public IWaitView getWaitView() {
		System.out.println("DiscardController getWaitView()");
		return waitView;
	}

	/**
	 * increases the resource amount to discard. Disables up arrow of a resource if discard amount is
	 * greater than player has in hand. Disables all up arrows if  total discard amount is reached
	 */
	@Override
	public void increaseAmount(ResourceType resource) {
		System.out.println("DiscardController increaseAmount()");
		
		IDiscardView view = getDiscardView();

		amountToDiscard++;
		view.setStateMessage(amountToDiscard+"/"+totalToDiscard);

		if( resource == ResourceType.BRICK){
			
			brick.add(resource);
			view.setResourceDiscardAmount(resource, brick.size());
			if(brick.size() >= numBrick){
				view.setResourceAmountChangeEnabled(resource, false, true);
			}else{
				view.setResourceAmountChangeEnabled(resource, true, true);
			}
			
		}
		
		else if(resource == ResourceType.ORE){
			
			ore.add(resource);
			view.setResourceDiscardAmount(resource, ore.size());
			if(ore.size() >= numOre){
				view.setResourceAmountChangeEnabled(resource, false, true);
			}else{
				view.setResourceAmountChangeEnabled(resource, true, true);
			}
			
		}
		
		else if(resource == ResourceType.SHEEP){
			
			sheep.add(resource);
			view.setResourceDiscardAmount(resource, sheep.size());
			if(sheep.size() >= numSheep){
				view.setResourceAmountChangeEnabled(resource, false, true);
			}else{
				view.setResourceAmountChangeEnabled(resource, true, true);
			}
			
		}
		
		else if(resource == ResourceType.WHEAT){
			
			wheat.add(resource);
			view.setResourceDiscardAmount(resource, wheat.size());
			if(wheat.size() >= numWheat){
				view.setResourceAmountChangeEnabled(resource, false, true);
			}else{
				view.setResourceAmountChangeEnabled(resource, true, true);
			}
			
		}
		
		else if(resource == ResourceType.WOOD){
			
			wood.add(resource);
			view.setResourceDiscardAmount(resource, wood.size());
			if(wood.size() >= numWood){
				view.setResourceAmountChangeEnabled(resource, false, true);
			}else{
				view.setResourceAmountChangeEnabled(resource, true, true);
			}
			
		}

		if(amountToDiscard == totalToDiscard){
			
			view.setResourceAmountChangeEnabled(ResourceType.BRICK, false, !brick.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.ORE, false, !ore.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, !sheep.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, !wheat.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.WOOD, false, !wood.isEmpty());
			view.setDiscardButtonEnabled(true);
			
		} else{

			view.setDiscardButtonEnabled(false);
		}

	}

	/**
	 * Decrease the amount of the specified resource which will be discarded.
	 * Controls buttons so that amount cannot be decreased below zero.
	 * 
	 */
	@Override
	public void decreaseAmount(ResourceType resource) {
		System.out.println("DiscardController decreaseAmount()");
		amountToDiscard--;
		IDiscardView view = getDiscardView();
		view.setStateMessage(amountToDiscard+"/"+totalToDiscard);

		if( resource == ResourceType.BRICK){
			//remove the last element
			brick.remove(brick.size() - 1);
			view.setResourceDiscardAmount(resource, brick.size());
		}
		else if( resource == ResourceType.ORE){
			//remove the last element
			ore.remove(ore.size() - 1);
			view.setResourceDiscardAmount(resource, ore.size());
		}
		else if( resource == ResourceType.SHEEP){
			//remove the last element
			sheep.remove(sheep.size() - 1);
			view.setResourceDiscardAmount(resource, sheep.size());
		}
		else if( resource == ResourceType.WHEAT){
			//remove the last element
			wheat.remove(wheat.size() - 1);
			view.setResourceDiscardAmount(resource, wheat.size());
		}
		else if(resource == ResourceType.WOOD){
			wood.remove(wood.size() - 1);
			view.setResourceDiscardAmount(resource, wood.size()); 
		}

		if(amountToDiscard <= totalToDiscard){
			view.setDiscardButtonEnabled(false);
		}else{
			view.setDiscardButtonEnabled(true);
		}

		if (amountToDiscard < totalToDiscard){
			boolean bbrick=false, bore=false,bsheep=false,bwheat=false,bwood=false;
			if(brick.size()<numBrick)bbrick=true;
			if(ore.size()<numOre)bore=true;
			if(sheep.size()<numSheep)bsheep=true;
			if(wheat.size()<numWheat)bwheat=true;
			if(wood.size()<numWood)bwood=true;

			view.setResourceAmountChangeEnabled(ResourceType.BRICK, bbrick, !brick.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.ORE, bore, !ore.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, bsheep, !sheep.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, bwheat, !wheat.isEmpty());
			view.setResourceAmountChangeEnabled(ResourceType.WOOD, bwood, !wood.isEmpty());
			view.setDiscardButtonEnabled(false);

		}
	}

	/**
	 * Initializes the discard window with necessary information to ensure
	 * proper discarding procedures and amounts are followed.
	 * 
	 * 
	 */
	public void init(){
		
		Game game = Client.getInstance().getGame();
		Player p = game.getPlayerByID(Client.getInstance().getUserId());
		IDiscardView view = getDiscardView();			
		
		brick.clear();
		ore.clear();
		sheep.clear();
		wheat.clear();
		wood.clear();
		
		numWood = p.getNumberResourcesOfType(ResourceType.WOOD);
		numWheat = p.getNumberResourcesOfType(ResourceType.WHEAT);
		numSheep = p.getNumberResourcesOfType(ResourceType.SHEEP);
		numOre = p.getNumberResourcesOfType(ResourceType.ORE);
		numBrick = p.getNumberResourcesOfType(ResourceType.BRICK);

		view.setResourceMaxAmount(ResourceType.WOOD, numWood);
		view.setResourceMaxAmount(ResourceType.WHEAT, numWheat);
		view.setResourceMaxAmount(ResourceType.SHEEP, numSheep);
		view.setResourceMaxAmount(ResourceType.ORE, numOre);
		view.setResourceMaxAmount(ResourceType.BRICK, numBrick);

		view.setResourceDiscardAmount(ResourceType.WOOD, 0);
		view.setResourceDiscardAmount(ResourceType.WHEAT, 0);
		view.setResourceDiscardAmount(ResourceType.SHEEP, 0);
		view.setResourceDiscardAmount(ResourceType.ORE, 0);
		view.setResourceDiscardAmount(ResourceType.BRICK, 0);

		if(numWood > 0)
			view.setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
		else
			view.setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		if(numWheat > 0)
			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
		else
			view.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		if(numSheep > 0)
			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
		else
			view.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		if(numOre > 0)
			view.setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
		else
			view.setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		if(numBrick > 0)
			view.setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
		else 
			view.setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);

		int handSize = p.getResourceCardHandSize();
		totalToDiscard = handSize/2;
		view.setStateMessage(amountToDiscard+"/"+totalToDiscard);

		view.setDiscardButtonEnabled(false);
	}

	/**
	 * Sends cards to be discarded to the client facade and closes the discard modal.
	 * 
	 */
	@Override
	public void discard() {
		System.out.println("DiscardController discard()");

		if(amountToDiscard == totalToDiscard){
			getDiscardView().closeModal();
			ClientFacade.getInstance().discardCards(brick.size(), ore.size(), sheep.size(), wheat.size(), wood.size());
		}
	}

	/**
	 * Triggers the discard functionality when game is updated and status becomes 'discarding'.
	 * --Status must be 'discarding'
	 * --If player has more than 7 cards, a window appears which forces them
	 * to discard half their hand.
	 * --If player does not have more than 7 cards or has already discarded, they are shown
	 * a wait screen which informs them other players are currently discarding.
	 * --When discarding status ends, the wait window is closed.
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DiscardController update()");
		// If the game is null just return
		Game game = Client.getInstance().getGame();

		if(game == null) {
			return;
		}else if (game.getStatus().equals("Discarding")){

			Player cp = game.getPlayerByID(Client.getInstance().getUserId());

			System.out.println("Status discarding--------------------------------------");
			//getWaitView().showModal();
			if (cp.getResourceCardHandSize()>7&&!cp.isHasDiscarded()){
				System.out.println("Player target for discard.");
				init();			
				getDiscardView().showModal();	
			} else{
				System.out.println("Showing wait modal for discarding cards.");
				getWaitView().showModal();
			}
		}
		else{
			if (getWaitView().isModalShowing()){
				getWaitView().closeModal();
			}
		}
	}

}


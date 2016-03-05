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
	
	/***
	 * increases the resource amount to discard. Disables up arrow of a resource if discard amount is
	 * greater than player has in hand. Disables all up arrows if  total discard amount is reached
	 */
	@Override
	public void increaseAmount(ResourceType resource) {
		System.out.println("DiscardController increaseAmount()");
		
		amountToDiscard++;
		getDiscardView().setStateMessage(amountToDiscard+"/"+totalToDiscard);
		
		if( resource == ResourceType.BRICK){
			brick.add(resource);
			getDiscardView().setResourceDiscardAmount(resource, brick.size());
			if(brick.size() >= numBrick){
				getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}
		}
		else if(resource == ResourceType.ORE){
			ore.add(resource);
			getDiscardView().setResourceDiscardAmount(resource, ore.size());
			if(ore.size() >= numOre){
				getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}
		}
		else if(resource == ResourceType.SHEEP){
			sheep.add(resource);
			getDiscardView().setResourceDiscardAmount(resource, sheep.size());
			if(sheep.size() >= numSheep){
				getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}
		}
		else if(resource == ResourceType.WHEAT){
			wheat.add(resource);
			getDiscardView().setResourceDiscardAmount(resource, wheat.size());
			if(wheat.size() >= numWheat){
				getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}
		}
		else if(resource == ResourceType.WOOD){
			wood.add(resource);
			getDiscardView().setResourceDiscardAmount(resource, wood.size());
			if(wood.size() >= numWood){
				getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}
		}
		
		if(amountToDiscard == totalToDiscard){
			getDiscardView().setResourceAmountChangeEnabled(resource.BRICK, false, !brick.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.ORE, false, !ore.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.SHEEP, false, !sheep.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.WHEAT, false, !wheat.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.WOOD, false, !wood.isEmpty());
			getDiscardView().setDiscardButtonEnabled(true);
		}else{
			
			getDiscardView().setDiscardButtonEnabled(false);
		}

	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		System.out.println("DiscardController decreaseAmount()");
		amountToDiscard--;
		getDiscardView().setStateMessage(amountToDiscard+"/"+totalToDiscard);
		 
		if( resource == ResourceType.BRICK){
			//remove the last element
			brick.remove(brick.size() - 1);
			getDiscardView().setResourceDiscardAmount(resource, brick.size());/*
			if(brick.size() < 1){
				getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}*/
		}
		else if( resource == ResourceType.ORE){
			//remove the last element
			ore.remove(ore.size() - 1);
			getDiscardView().setResourceDiscardAmount(resource, ore.size());/*
			if(ore.size() < 1){
				getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}*/
		}
		else if( resource == ResourceType.SHEEP){
			//remove the last element
			sheep.remove(sheep.size() - 1);
			getDiscardView().setResourceDiscardAmount(resource, sheep.size());/*
			if(sheep.size() < 1){
				getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}*/
		}
		else if( resource == ResourceType.WHEAT){
			//remove the last element
			wheat.remove(wheat.size() - 1);
			getDiscardView().setResourceDiscardAmount(resource, wheat.size());/*
			if(wheat.size() < 1){
				getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}*/
		}
		else if(resource == ResourceType.WOOD){
			wood.remove(wood.size() - 1);
			getDiscardView().setResourceDiscardAmount(resource, wood.size()); /*
			if(wood.size() < 1){
				getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			}else{
				getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			}*/
		}
		
		if(amountToDiscard <= totalToDiscard){
			getDiscardView().setDiscardButtonEnabled(false);
		}else{
			getDiscardView().setDiscardButtonEnabled(true);
		}
		
		if (amountToDiscard < totalToDiscard){
			boolean bbrick=false, bore=false,bsheep=false,bwheat=false,bwood=false;
			if(brick.size()<numBrick)bbrick=true;
			if(ore.size()<numOre)bore=true;
			if(sheep.size()<numSheep)bsheep=true;
			if(wheat.size()<numWheat)bwheat=true;
			if(wood.size()<numWood)bwood=true;
			
			
			getDiscardView().setResourceAmountChangeEnabled(resource.BRICK, bbrick, !brick.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.ORE, bore, !ore.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.SHEEP, bsheep, !sheep.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.WHEAT, bwheat, !wheat.isEmpty());
			getDiscardView().setResourceAmountChangeEnabled(resource.WOOD, bwood, !wood.isEmpty());
			getDiscardView().setDiscardButtonEnabled(false);
			
		}
	}
	
	public void init(){
		Game game = Client.getInstance().getGame();

		numWood = game.getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.WOOD);
		numWheat = game.getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.WHEAT);
		numSheep = game.getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.SHEEP);
		numOre = game.getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.ORE);
		numBrick = game.getPlayerByID(Client.getInstance().getUserId()).getNumberResourcesOfType(ResourceType.BRICK);
		
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, numWood);
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, numWheat);
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, numSheep);
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, numOre);
		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, numBrick);
		
		if(numWood > 0)
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
		else
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		if(numWheat > 0)
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
		else
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		if(numSheep > 0)
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
		else
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		if(numOre > 0)
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
		else
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		if(numBrick > 0)
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
		else 
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		
		 int handSize = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getResourceCardHandSize();
		 totalToDiscard = handSize/2;
		 getDiscardView().setStateMessage(amountToDiscard+"/"+totalToDiscard);
		 
		 getDiscardView().setDiscardButtonEnabled(false);
	}
	
	@Override
	public void discard() {
		System.out.println("DiscardController discard()");
		
		//init();
		
		if(amountToDiscard == totalToDiscard){
			getDiscardView().closeModal();
			ClientFacade.getInstance().discardCards(brick.size(), ore.size(), sheep.size(), wheat.size(), wood.size());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DiscardController update()");
		// If the game is null just return
		Game game = Client.getInstance().getGame();
		
		if(game == null) {
			return;
		}else if (game.getStatus().equals("Discarding")){
			
			Player cp = game.getPlayerByID(Client.getInstance().getUserId());
			
			System.out.println("Satus discarded");
			
			if (cp.isHasDiscarded()){
				getWaitView().showModal();
			} else if (cp.getResourceCardHandSize()>7){
				System.out.println("Player target for discard.");
				init();			
				getDiscardView().showModal();	
			}				
		}
		else{
			getWaitView().closeModal();
		}
	}

}


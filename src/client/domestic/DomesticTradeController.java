package client.domestic;

import shared.definitions.*;
import shared.model.Game;
import shared.model.player.Player;

import java.util.*;

import client.Client;
import client.ClientFacade;
import client.base.*;
import client.data.PlayerInfo;
import client.data.TradeInfo;
import client.misc.*;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private boolean init, chooseSend, choosePlayer;
	private int totalWood, totalSheep, totalOre, totalWheat, totalBrick;
	private int tradeIndex;
	private TradeInfo tradeinfo;
	private Game game;
	private boolean acceptButtonEnabled;

	private int numWood, numWheat, numSheep, numOre, numBrick;
	
	
	
	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
			IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);

		System.out.println("DomesticTradeController DomesticTradeController()");

		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);

		init = true;
	}

	public IDomesticTradeView getTradeView() {
		System.out.println("DomesticTradeController getTradeView()");
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		System.out.println("DomesticTradeController getTradeOverlay()");
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		System.out.println("DomesticTradeController setTradeOverlay()");
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		System.out.println("DomesticTradeController getWaitOverlay()");
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		System.out.println("DomesticTradeController setWaitOverlay()");
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		System.out.println("DomesticTradeController getAcceptOverlay()");
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		System.out.println("DomesticTradeController acceptOverlay()");
		this.acceptOverlay = acceptOverlay;
	}
	/***
	 * This method sets up the buttons with each players name to be traded with
	 */
	private void initPlayers(){

		List<PlayerInfo> pl = Client.getInstance().getGameInfo().getPlayers();
		ArrayList<PlayerInfo> temppl = new ArrayList<PlayerInfo>();


		for(int i = 0; i < pl.size(); i++){
			if(pl.get(i).getId() == Client.getInstance().getGame().getCurrentPlayer().getPlayerId()){}
			else{
				temppl.add(pl.get(i));
			}
		}

		PlayerInfo [] playerinfo = new PlayerInfo[temppl.size()];
		temppl.toArray(playerinfo);
		getTradeOverlay().setPlayers(playerinfo);
		init = false;
	}

	@Override
	public void startTrade() {
		System.out.println("DomesticTradeController startTrade()");
		
		int userID = Client.getInstance().getUserId();
		this.game = Client.getInstance().getGame();
		
		
		if(game.isPlayersTurn(userID)){
			System.out.println("Legitimate trade initiated.");
		
			
			if(init)
				initPlayers();
			
			//How much of each resource the player has
			numWood = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WOOD);
			numWheat = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WHEAT);
			numSheep = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.SHEEP);
			numOre = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.ORE);
			numBrick = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.BRICK);
			
			//keep track as a player increases/decreases so we can compare with the players hand
			totalWood = 0;
			totalSheep = 0;
			totalOre = 0;
			totalWheat = 0;
			totalBrick = 0;
			
			getTradeOverlay().setPlayerSelectionEnabled(true);
			getTradeOverlay().setResourceSelectionEnabled(true);
			getTradeOverlay().setStateMessage("set the trade you want");
			getTradeOverlay().showModal();
		
		}else{
			System.out.println("Trade initiated when it is not player's turn.");
			getTradeOverlay().setPlayerSelectionEnabled(false);
			getTradeOverlay().setResourceSelectionEnabled(false);
			getTradeOverlay().setStateMessage("it's not your turn");
			getTradeOverlay().showModal();
		}
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		System.out.println("DomesticTradeController decreaseResourceAmount()");

	}
	/***
	 * the "send" button, A player can only increase a resource type if he has greater than or equal to amount of increase
	 */
	@Override
	public void increaseResourceAmount(ResourceType resource) {

		System.out.println("DomesticTradeController increaseResourceAmount");

		if(!choosePlayer)
			getTradeOverlay().setStateMessage("who do you want to trade with");

		chooseSend = true;
		if(chooseSend && choosePlayer){
			getTradeOverlay().setStateMessage("Trade!");
			getTradeOverlay().setTradeEnabled(true);
		}
	}

	/***
	 * A player may only send offers on his turn
	 * 
	 */
	@Override
	public void sendTradeOffer() {
		System.out.println("DomesticTradeController sendTradeOffer()");

		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
		getWaitOverlay().setMessage("trade transaction in progress...");

		ClientFacade.getInstance().offerTrade(totalBrick, totalOre, totalSheep, totalWheat, totalWood, tradeIndex);
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		System.out.println("DomesticTradeController setPlayerToTradeWith()");
		
		if (playerIndex == -1){
			choosePlayer=false;
			if(!chooseSend)
				getTradeOverlay().setStateMessage("set the trade you want");
			else
				getTradeOverlay().setStateMessage("who do you want to trade with");
			getTradeOverlay().setTradeEnabled(false);
				
		}else if ((playerIndex > -1) && (playerIndex < 4)){
			tradeIndex = playerIndex;
			choosePlayer=true;
		}
		if(chooseSend && choosePlayer){
			getTradeOverlay().setStateMessage("Trade!");
			getTradeOverlay().setTradeEnabled(true);
		}
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		System.out.println("DomesticTradeController setResourceToReceive()");
		
		switch(resource){
		case WOOD: 
			totalWood--;
			if (numWood > totalWood && totalWood > 0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,true);
			}
			else if(numWood > totalWood && totalWood == 0)
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,false);
			break;
		case BRICK:
			totalBrick--;
			if (numBrick > totalBrick){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,true);
			}
			else if(numWood > totalWood && totalWood == 0)
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,false);
			break;	
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		System.out.println("DomesticTradeController setResourceToSend()");
		
		switch(resource){
			//if players has at least 1 or more of the type of this resource.
			case WOOD: if (numWood >= (totalWood+1)){ 
				totalWood++;
				if (numWood == totalWood){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, false,true);
				}
				}else{
					//player doesn't have any of the type of this resource. disable increase/decrease
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
				}
				break;		
			case SHEEP: if (numSheep >= (totalSheep+1)){ 
				totalSheep++;
				if (numSheep == totalSheep){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, false,true);
				}
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
				}
				break;		
			case ORE: if (numOre >= (totalOre+1)){ 
				totalOre++;
				if (numOre == totalOre){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, false,true);
				}
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
				}
				break;		
			case WHEAT: if (numWheat >= (totalWheat+1)){ 
				totalWheat++;
				if (numWheat ==totalWheat){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, false,true);
				}
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
				}
				break;		
			case BRICK: if (numBrick >= (totalBrick+1)){ 
				totalBrick++;
				if (numBrick == totalBrick){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, false,true);
				}
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
				}
				break;						
		}
	}

	@Override
	public void unsetResource(ResourceType resource) {
		System.out.println("DomesticTradeController unsetResource()");
		getTradeOverlay().setStateMessage("set the trade you want");
		getTradeOverlay().setTradeEnabled(false);
		chooseSend = false; 
	}

	@Override
	public void cancelTrade() {
		System.out.println("DomesticTradeController cancelTrade()");
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		System.out.println("DomesticTradeController willAccept()");	
		getAcceptOverlay().closeModal();
		ClientFacade.getInstance().acceptTrade(willAccept);
	}
	
	private void offer(ResourceType resource, int offer ){
		//The resource wasn't offered
		if(offer == 0)
			return;
		//the resources getting
		else if(offer > 0)
			getAcceptOverlay().addGetResource(resource, offer);
		//the resources giving
		else if(offer < 0)
			getAcceptOverlay().addGiveResource(resource, offer);
	}
	
	private void buttonEnabled(int offer, int receiverResource){
		if(receiverResource >= offer){
			getAcceptOverlay().setAcceptEnabled(true);
			acceptButtonEnabled = true;
		}
		else if(!acceptButtonEnabled)
			getAcceptOverlay().setAcceptEnabled(false);
	}
	
	private void acceptTradeWindow(){
		
		Player sender = game.getAllPlayers()[tradeinfo.getSender()];
		Player receiver = game.getAllPlayers()[tradeinfo.getReceiver()];
		
		//set the name of the player offering the trade
		getAcceptOverlay().setPlayerName(sender.getPlayerName());
		
		int brickOffer = tradeinfo.getOffer().getBrick();
		int oreOffer = tradeinfo.getOffer().getOre();
		int sheepOffer = tradeinfo.getOffer().getSheep();
		int wheatOffer = tradeinfo.getOffer().getWheat();
		int woodOffer = tradeinfo.getOffer().getWood();
		
		//display what the player is offering
		offer(ResourceType.BRICK, brickOffer);
		offer(ResourceType.ORE, oreOffer);
		offer(ResourceType.SHEEP, sheepOffer);
		offer(ResourceType.WHEAT, wheatOffer);
		offer(ResourceType.WOOD, woodOffer );
		
		int recieverBrick = receiver.getNumberResourcesOfType(ResourceType.BRICK);
		int recieverOre = receiver.getNumberResourcesOfType(ResourceType.ORE);
		int recieverSheep = receiver.getNumberResourcesOfType(ResourceType.SHEEP);
		int recieverWheat = receiver.getNumberResourcesOfType(ResourceType.WHEAT);
		int recieverWood = receiver.getNumberResourcesOfType(ResourceType.WOOD);
		
		/*Each resource calls buttonEnabled method. This boolean prevents the overlay 
		from disabling the button once it has been set to true.*/
		acceptButtonEnabled = false;
		//Does the receiver have enough resource cards to accept the offer 
		buttonEnabled(brickOffer, recieverBrick);
		buttonEnabled(oreOffer, recieverOre);
		buttonEnabled(sheepOffer, recieverSheep);
		buttonEnabled(wheatOffer, recieverWheat);
		buttonEnabled(woodOffer, recieverWood);
		
		getAcceptOverlay().showModal();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("DomesticTradeController update()");
		
		int userID = Client.getInstance().getUserId();
		this.game = Client.getInstance().getGame();
		
		// If the game is null just return
		if(game == null) {
			return;
		}
		
		if(game.getTradeOffer() != null){
			this.tradeinfo = game.getTradeOffer();
			if(game.getPlayerByID(userID).getPlayerIndex() == tradeinfo.getReceiver()){
				acceptTradeWindow();
			}
		}
		//Is it the players turn?
		
	}

}



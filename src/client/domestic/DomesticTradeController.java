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
	private boolean acceptButtonEnabled;
	private boolean toSend, toRecieve;
	private boolean receiveTosendWood,receiveTosendSheep, receiveTosendOre, receiveTosendWheat, receiveTosendBrick;
	
	private int decreaseWood, decreaseSheep, decreaseOre, decreaseWheat, decreaseBrick;
	private int woodToSend, sheepToSend, oreToSend, wheatToSend, brickToSend;
	private int playersWood, playersWheat, playersSheep, playersOre, playersBrick;
	
	private int tradeIndex;
	private TradeInfo tradeinfo;
	private Game game;
	
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
		Client.getInstance().addObserver(this);
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
			if(init)
				initPlayers();
			
			//How much of each resource the player has
			playersWood = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WOOD);
			playersWheat = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.WHEAT);
			playersSheep = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.SHEEP);
			playersOre = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.ORE);
			playersBrick = Client.getInstance().getGame().getCurrentPlayer().getNumberResourcesOfType(ResourceType.BRICK);
			
			/*keep track as a player increases/decreases so we can compare with the players hand so 
			  that he can't send more than he has*/
			woodToSend = 0;
			sheepToSend = 0;
			oreToSend = 0;
			wheatToSend = 0;
			brickToSend = 0;
			
			/*keep track as a player increases/decreases such that a resource cannot be decreased beyond 0.
			  used to enable and disable down arrow.*/
			decreaseWood = 0;
			decreaseSheep = 0;
			decreaseOre = 0;
			decreaseWheat = 0;
			decreaseBrick = 0;
			
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

	
	
	private void increaseResourceAmountSend(ResourceType resource){
		switch(resource){
			//if players has at least 1 or more of the type of this resource.
			case WOOD: if (playersWood >= (woodToSend+1)){ 
				woodToSend++;
				//you can't send more wood than you have
				if (playersWood == woodToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, false,true);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,true);
				}
			}
			break;		
			case SHEEP: if (playersSheep >= (sheepToSend+1)){ 
				sheepToSend++;
				if (playersSheep == sheepToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, false,true);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,true);
				}
			}
			break;		
			case ORE: if (playersOre >= (oreToSend+1)){ 
				oreToSend++;
				if (playersOre == oreToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, false,true);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,true);
				}
			}
			break;		
			case WHEAT: if (playersWheat >= (wheatToSend+1)){ 
				wheatToSend++;
				if (playersWheat == wheatToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, false,true);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,true);
				}
			}
			break;		
			case BRICK: if (playersBrick >= (wheatToSend+1)){ 
				wheatToSend++;
				if (playersBrick == wheatToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, false,true);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,true);
				}
			}
			break;						
		}
	}
	
	private void decreaseResourceAmountSend(ResourceType resource){
		System.out.println("    sw  "+woodToSend + "    ss   " + sheepToSend 
				+ "    so  "+oreToSend+ "   swh  "+ wheatToSend+ "    sb " + brickToSend);
		switch(resource){
			case WOOD: 
				woodToSend--;
				//you can't decrease less than 0
				if (0 == woodToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
				}
			break;		
			case SHEEP:
				sheepToSend--;
				if (0 == sheepToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,false);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
				}
			break;		
			case ORE:
				oreToSend--;
				if (0 == oreToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,false);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
				}
			
			break;		
			case WHEAT: 
				wheatToSend--;
				if (0 == wheatToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,false);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
				}
			break;		
			case BRICK: 
				wheatToSend--;
				if (0 == wheatToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,false);
				}else{
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
				}
			break;						
		}
	}
	
	private void increaseResourceAmountRecieve(ResourceType resource){
		switch(resource){
			case WOOD: 
				decreaseWood++;
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
			break;		
			case SHEEP:
				decreaseSheep++;
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
			break;		
			case ORE:
				decreaseOre++;
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
			break;		
			case WHEAT: 
				decreaseWheat++;
			    tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
			break;		
			case BRICK: 
				decreaseBrick++;
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
			break;						
		}
	}
	
	private void decreaseResourceAmountRecieve(ResourceType resource){
		switch(resource){
			case WOOD: 
				decreaseWood--;
				//you can't decrease less than 0
				if (0 == decreaseWood){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
				}
			break;		
			case SHEEP:
				decreaseSheep--;
				if (0 == decreaseSheep){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
				}
			break;		
			case ORE:
				decreaseOre--;
				if (0 == decreaseOre){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
				}
			break;		
			case WHEAT: 
				decreaseWheat--;
				if (0 == decreaseWheat){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
				}
			break;		
			case BRICK: 
				decreaseBrick--;
				if (0 == decreaseBrick){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
				}
			break;						
		}
	}
	
	/***
	 * the "send" button, A player can only increase a resource type if he has greater than or equal to amount of increase
	 */
	@Override
	public void increaseResourceAmount(ResourceType resource) {

		System.out.println("DomesticTradeController increaseResourceAmount");
		
		if(toSend)
			increaseResourceAmountSend(resource);
		else
			increaseResourceAmountRecieve(resource);
			
		if(!choosePlayer)
			getTradeOverlay().setStateMessage("who do you want to trade with");
		
		if(woodToSend > 0 || sheepToSend > 0 || oreToSend > 0 || wheatToSend > 0 || brickToSend > 0)
			chooseSend = true;
		else{
			chooseSend = false;
			getTradeOverlay().setStateMessage("set the trade you want");
			getTradeOverlay().setTradeEnabled(false);
		}
		
		if(chooseSend && choosePlayer){
			getTradeOverlay().setStateMessage("Trade!");
			getTradeOverlay().setTradeEnabled(true);
		}
	}
	
	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		System.out.println("DomesticTradeController decreaseResourceAmount()");
		
		if(toSend)
			decreaseResourceAmountSend(resource);
		else
			decreaseResourceAmountRecieve(resource);
		//check if he is sending a resource, this is a special case.
		if(woodToSend > 0 || sheepToSend > 0 || oreToSend > 0 || wheatToSend > 0 || brickToSend > 0)
			chooseSend = true;
		else{
			chooseSend = false;
			getTradeOverlay().setStateMessage("set the trade you want");
			getTradeOverlay().setTradeEnabled(false);
		}
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
		
		//these booleans are needed in the decrease and increase function
		toSend = false;
		
		switch(resource){
			case WOOD: 
				//if resource is at 0, disable decrease button
				if (0 == decreaseWood){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,false);
				}
				//prevents errors: if you click on receive and then later click on send.
				receiveTosendWood = true; 
			break;		
			case SHEEP: 
				if (0 == decreaseSheep){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,false);
				}
				receiveTosendSheep = true; 
			break;		
			case ORE:
				if (0 == decreaseOre){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,false);
				}
				receiveTosendOre = true; 
			break;		
			case WHEAT: 
				if (0 == decreaseWheat){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,false);
				}
				receiveTosendWheat = true; 
			break;		
			case BRICK: 
				if (0 == decreaseBrick){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,false);
				}
				receiveTosendBrick = true;
			break;						
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		System.out.println("DomesticTradeController setResourceToSend()");
		
		toSend = true;
		
		System.out.println("    pw  "+playersWood + "    ps   " + playersSheep 
				+ "    po  "+playersOre+ "   pwh  "+ playersWheat+ "    pb " + playersBrick);
		
		System.out.println("    sw  "+woodToSend + "    ss   " + sheepToSend 
				+ "    so  "+oreToSend+ "   swh  "+ wheatToSend+ "    sb " + brickToSend);
		
		switch(resource){
			//if players has at least 1 or more of the type of this resource.
			case WOOD: if (playersWood >= (woodToSend+1)){ 
				//if resource is at 0, disable decrease button
				if (0 == woodToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,false);
				}
				//if a player switches from the receive button to the send button
				if(receiveTosendWood){
					
					//if the amount they want to receive is higher than their wood, set to max cards in their hand
					if(decreaseWood > playersWood){
						woodToSend = playersWood;
						String pw = Integer.toString(playersWood);
						tradeOverlay.setResourceAmount(ResourceType.WOOD,pw);
					}else{
						woodToSend = decreaseWood;
					}
					//reset receive parameters
					receiveTosendWood = false; 
					decreaseWood = 0;
				}
			//player doesn't have any of the type of this resource. disable increase/decrease
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
			}
			break;		
			case SHEEP: if (playersSheep >= (sheepToSend+1)){ 
				if (0 == sheepToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,false);
				}
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
			}
			break;		
			case ORE: if (playersOre >= (oreToSend+1)){ 
				if (0 == oreToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,false);
				}
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
			}
			break;		
			case WHEAT: if (playersWheat >= (wheatToSend+1)){ 
				if (0 == wheatToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,false);
				}
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
			}
			break;		
			case BRICK: if (playersBrick >= (brickToSend+1)){ 
				decreaseBrick++;
				if (0 == brickToSend){
					tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
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
		
		//reset resource
		switch(resource){
			case WOOD: 
				woodToSend = 0;
				decreaseWood = 0;
			break;		
			case SHEEP: 
				sheepToSend = 0;
				decreaseSheep = 0;
			break;		
			case ORE: 
				oreToSend = 0;
				decreaseOre = 0;
			break;		
			case WHEAT: 
				wheatToSend = 0;
				decreaseWheat = 0;
			break;		
			case BRICK: 
				brickToSend = 0;
				decreaseBrick = 0;
			break;						
		}
		//check if he is sending a resource
		if(woodToSend > 0 || sheepToSend > 0 || oreToSend > 0 || wheatToSend > 0 || brickToSend > 0)
			return;
		//He is sending nothing, so disable trade
		else{
			getTradeOverlay().setStateMessage("set the trade you want");
			getTradeOverlay().setTradeEnabled(false);
			chooseSend = false; 
		}
	}
	
	/***
	 * A player may only send offers on his turn
	 * 
	 */
	@Override
	public void sendTradeOffer() {
		System.out.println("DomesticTradeController sendTradeOffer()");

		getWaitOverlay().showModal();
		getWaitOverlay().setMessage("trade transaction in progress...");

		ClientFacade.getInstance().offerTrade(brickToSend, oreToSend, sheepToSend, wheatToSend, woodToSend, tradeIndex);
		getTradeOverlay().closeModal();
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
		System.out.println("DomesticTradeController offer()");
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
		System.out.println("DomesticTradeController buttonEnabled()");
		if(receiverResource >= offer && offer > 0){
			getAcceptOverlay().setAcceptEnabled(true);
			acceptButtonEnabled = true;
		}
		else if(!acceptButtonEnabled)
			getAcceptOverlay().setAcceptEnabled(false);
	}
	
	private void acceptTradeWindow(){
		System.out.println("DomesticTradeController acceptTradeWindow()");
		
		Player sender = game.getAllPlayers()[tradeinfo.getSender()];
		Player receiver = game.getAllPlayers()[tradeinfo.getReceiver()];
		
		//set the name of the player offering the trade
		getAcceptOverlay().setPlayerName(sender.getPlayerName());
		
		int brickOffer = tradeinfo.getOffer().getBrick();
		int oreOffer = tradeinfo.getOffer().getOre();
		int sheepOffer = tradeinfo.getOffer().getSheep();
		int wheatOffer = tradeinfo.getOffer().getWheat();
		int woodOffer = tradeinfo.getOffer().getWood();
		
		System.out.println(" brickO"+brickOffer+" oreO"+oreOffer+" sheepO"+sheepOffer+" wheatO"+wheatOffer+" woodOffer"+woodOffer);
		
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
		if(Client.getInstance().getGame() == null) {
			return;
		}
		else {
			if (Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getPlayerIndex())) {
				//getTradeView().enableDomesticTrade(true);
				//startTrade();
				
				
			} else {
				//We need to set the values all to disabled inside of the trade
				//getTradeView().enableDomesticTrade(false);
				getTradeOverlay().setPlayerSelectionEnabled(false);
				getTradeOverlay().setResourceSelectionEnabled(false);
				getTradeOverlay().setStateMessage("it's not your turn");
			}
		}
		
		if(game.getTradeOffer() != null){
			this.tradeinfo = game.getTradeOffer();
			//if a player has an offer
			if(game.getPlayerByID(userID).getPlayerIndex() == tradeinfo.getReceiver()){
				acceptTradeWindow();
			}
		}

	}

}



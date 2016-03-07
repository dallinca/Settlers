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

	//private int decreaseWood, decreaseSheep, decreaseOre, decreaseWheat, decreaseBrick;
	//private int woodToSend, sheepToSend, oreToSend, wheatToSend, brickToSend;
	private int playersWood, playersWheat, playersSheep, playersOre, playersBrick;
	private int wood, sheep, ore, wheat, brick;

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

			wood = 0;
			sheep = 0;
			ore = 0;
			wheat = 0;
			brick = 0;

			getTradeOverlay().setPlayerSelectionEnabled(true);
			getTradeOverlay().setResourceSelectionEnabled(true);
			getTradeOverlay().setStateMessage("Set your trade.");
			getTradeOverlay().showModal();

		}else{
			System.out.println("Trade initiated when it is not player's turn.");
			getTradeOverlay().setPlayerSelectionEnabled(false);
			getTradeOverlay().setResourceSelectionEnabled(false);
			getTradeOverlay().setStateMessage("It is not your turn.");
			getTradeOverlay().showModal();
		}
	}

	private void increaseResourceAmountSend(ResourceType resource){

		switch(resource){

		//if players has at least 1 or more of the type of this resource.
		case WOOD: 
			System.out.println("Wood count: "+ wood);

			wood++;
			tradeOverlay.setResourceAmount(ResourceType.WOOD, Integer.toString(wood));
			if (playersWood>wood+1){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,true);
			}else if (playersWood<=wood){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, false,true);
			}

			break;

		case SHEEP: 

			sheep++;
			tradeOverlay.setResourceAmount(ResourceType.SHEEP, Integer.toString(sheep));
			if (playersSheep>sheep+1){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,true);
			}else if (playersSheep<=sheep){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, false,true);
			}


			break;	

		case ORE: 

			ore++;
			tradeOverlay.setResourceAmount(ResourceType.ORE, Integer.toString(ore));
			if (playersOre>ore+1){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,true);
			}else if (playersOre<=ore){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, false,true);
			}


			break;		

		case WHEAT: 

			wheat++;
			tradeOverlay.setResourceAmount(ResourceType.WHEAT, Integer.toString(wheat));
			if (playersWheat>wheat+1){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,true);
			}else if (playersWheat<=wheat){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, false,true);
			}

			break;		

		case BRICK: 

			brick++;
			tradeOverlay.setResourceAmount(ResourceType.BRICK, Integer.toString(brick));
			if (playersBrick>brick+1){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,true);
			}else if (playersBrick<=brick){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, false,true);
			}

			break;						
		}
	}

	private void decreaseResourceAmountSend(ResourceType resource){

		switch(resource){

		case WOOD: 
		
			wood--;
			tradeOverlay.setResourceAmount(ResourceType.WOOD, Integer.toString(wood));
			if (wood==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,false);
			}

			break;		

		case SHEEP:

			sheep--;
			tradeOverlay.setResourceAmount(ResourceType.SHEEP, Integer.toString(sheep));
			if (sheep==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,false);
			}

			break;		

		case ORE:

			ore--;
			tradeOverlay.setResourceAmount(ResourceType.ORE, Integer.toString(ore));
			if (ore==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,false);
			}


			break;		

		case WHEAT: 
			
			wheat--;
			tradeOverlay.setResourceAmount(ResourceType.WHEAT, Integer.toString(wheat));
			if (wheat==0){
				System.out.println("INSIDE");
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,false);
			}


			break;	

		case BRICK: 

			brick--;
			tradeOverlay.setResourceAmount(ResourceType.BRICK, Integer.toString(brick));
			if (brick==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,false);
			}				

			break;						
		}
	}

	private void increaseResourceAmountRecieve(ResourceType resource){
		switch(resource){

		case WOOD: 

			wood--;
			tradeOverlay.setResourceAmount(ResourceType.WOOD, Integer.toString(-wood));
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,true);

			break;		

		case SHEEP:

			sheep--;
			tradeOverlay.setResourceAmount(ResourceType.SHEEP, Integer.toString(-sheep));
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,true);

			break;		
		case ORE:

			ore--;
			tradeOverlay.setResourceAmount(ResourceType.ORE, Integer.toString(-ore));
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,true);

			break;		
		case WHEAT:

			wheat--;
			tradeOverlay.setResourceAmount(ResourceType.WHEAT, Integer.toString(-wheat));
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,true);

			break;		
		case BRICK: 

			brick--;
			tradeOverlay.setResourceAmount(ResourceType.BRICK, Integer.toString(-brick));
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,true);

			break;						
		}
	}

	private void decreaseResourceAmountRecieve(ResourceType resource){
		switch(resource){

		case WOOD: 

			wood++;
			tradeOverlay.setResourceAmount(ResourceType.WOOD, Integer.toString(-wood));
			if (wood==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,false);
			}

			break;	

		case SHEEP:

			sheep++;
			tradeOverlay.setResourceAmount(ResourceType.SHEEP, Integer.toString(-sheep));
			if (sheep==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,false);
			}

			break;		

		case ORE:

			ore++;
			tradeOverlay.setResourceAmount(ResourceType.ORE, Integer.toString(-ore));
			if (ore==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,false);
			}

			break;	

		case WHEAT: 

			wheat++;
			tradeOverlay.setResourceAmount(ResourceType.WHEAT, Integer.toString(-wheat));
			if (wheat==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,false);
			}

			break;		
		case BRICK: 

			brick++;
			tradeOverlay.setResourceAmount(ResourceType.BRICK, Integer.toString(-brick));
			if (brick==0){
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,false);
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
			getTradeOverlay().setStateMessage("Select your trade partner.");

		System.out.println("Wheat: "+wood+" Ore: "+" Sheep: "+sheep+" Ore: "+ore+" Wheat: "+wheat);
		//He has to be sending a resource and he has to be receiving a resource 
		if((wood > 0 || sheep > 0 || ore > 0 || wheat > 0 || brick > 0)
				&& (wood < 0 || sheep < 0 || ore < 0 || wheat < 0 || brick < 0))
			chooseSend = true;
		else{
			chooseSend = false;
			getTradeOverlay().setStateMessage("Set your trade.");
			getTradeOverlay().setTradeEnabled(false);
		}
		System.out.println("Choose send is: "+chooseSend);
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
		if((wood > 0 || sheep > 0 || ore > 0 || wheat > 0 || brick > 0)
				&& (wood < 0 || sheep < 0 || ore < 0 || wheat < 0 || brick < 0))
			chooseSend = true;
		else{
			chooseSend = false;
			getTradeOverlay().setStateMessage("Set your trade.");
			getTradeOverlay().setTradeEnabled(false);
		}
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		System.out.println("DomesticTradeController setPlayerToTradeWith()");

		if (playerIndex == -1){
			choosePlayer=false;
			if(!chooseSend)
				getTradeOverlay().setStateMessage("Set your trade.");
			else
				getTradeOverlay().setStateMessage("Select your trade partner.");
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
		
		toSend = false;
		switch(resource){
		case WOOD: 

			wood = 0;
			tradeOverlay.setResourceAmount(ResourceType.WOOD, "0");
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,false);

			break;		
		case SHEEP: 

			sheep = 0;
			tradeOverlay.setResourceAmount(ResourceType.SHEEP, "0");
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,false);

			break;		
		case ORE:

			ore = 0;
			tradeOverlay.setResourceAmount(ResourceType.ORE, "0");
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,false);


			break;		
		case WHEAT: 

			wheat = 0;
			tradeOverlay.setResourceAmount(ResourceType.WHEAT, "0");
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,false);


			break;		
		case BRICK: 

			brick = 0;
			tradeOverlay.setResourceAmount(ResourceType.BRICK, "0");
			tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,false);


			break;						
		}
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		System.out.println("DomesticTradeController setResourceToSend()");
		toSend = true;
		
		switch(resource){
		//if players has at least 1 or more of the type of this resource.
		case WOOD: 				

			wood = 0;
			tradeOverlay.setResourceAmount(ResourceType.WOOD, "0");
			if (playersWood > 0){ 
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, true,false);
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WOOD, false,false);
			}		

			break;		
		case SHEEP: 

			sheep = 0;
			tradeOverlay.setResourceAmount(ResourceType.SHEEP, "0");
			if (playersSheep > 0){ 
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, true,false);
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.SHEEP, false,false);
			}

			break;		
		case ORE: 

			ore = 0;
			tradeOverlay.setResourceAmount(ResourceType.ORE, "0");
			if (playersOre > 0){ 
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, true,false);
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.ORE, false,false);
			}

			break;		
		case WHEAT: 

			wheat = 0;
			tradeOverlay.setResourceAmount(ResourceType.WHEAT, "0");
			if (playersWheat > 0){ 
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, true,false);
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.WHEAT, false,false);
			}

			break;		
		case BRICK: 

			brick = 0;
			tradeOverlay.setResourceAmount(ResourceType.BRICK, "0");
			if (playersBrick > 0){ 
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, true,false);
			}else{
				tradeOverlay.setResourceAmountChangeEnabled(ResourceType.BRICK, false,false);
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
			wood = 0;
			break;		
		case SHEEP: 
			sheep = 0;
			break;		
		case ORE: 
			ore = 0;
			break;		
		case WHEAT: 
			wheat = 0;
			break;		
		case BRICK: 
			brick = 0;
			break;						
		}
		//check if he is sending a resource
		if(wood > 0 || sheep > 0 || ore > 0 || wheat > 0 || brick > 0)
			return;
		//He is sending nothing, so disable trade
		else{
			getTradeOverlay().setStateMessage("Set your trade.");
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

		getTradeOverlay().closeModal();
		
		System.out.println("Wood: "	+wood+" Sheep: "+ sheep + " Ore: "+ore+ " Wheat: "+wheat+" Brick: "+brick);
		ClientFacade.getInstance().offerTrade(brick, ore, sheep, wheat, wood, tradeIndex);//Trade happens here
		
		wood = 0; 
		brick = 0;
		ore = 0;
		sheep = 0;
		wheat = 0;
		brick = 0;
		
		getWaitOverlay().setMessage("Trade transaction in progress...");
		getWaitOverlay().showModal();
	}

	@Override
	public void cancelTrade() {
		System.out.println("DomesticTradeController cancelTrade()");
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		System.out.println("DomesticTradeController willAccept()");	

		ClientFacade.getInstance().acceptTrade(willAccept);
		getAcceptOverlay().closeModal();
		
		
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
		else if(offer < 0){
			//make the number positive so it displays correctly on the GUI
			offer = -1*offer;
			getAcceptOverlay().addGiveResource(resource, offer);
		}
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
		getAcceptOverlay().reset();

		Player sender = game.getAllPlayers()[tradeinfo.getSender()];
		Player receiver = game.getAllPlayers()[tradeinfo.getReceiver()];

		//set the name of the player offering the trade
		getAcceptOverlay().setPlayerName(sender.getPlayerName());

		int brickOffer = tradeinfo.getOffer().getBrick();
		int oreOffer = tradeinfo.getOffer().getOre();
		int sheepOffer = tradeinfo.getOffer().getSheep();
		int wheatOffer = tradeinfo.getOffer().getWheat();
		int woodOffer = tradeinfo.getOffer().getWood();
		
		System.out.println("Offer: brick: "+brickOffer+" ore: "+oreOffer+" sheep: "+sheepOffer
				+"\nwheat: "+wheatOffer+" wood: "+woodOffer);

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
		else if (!Client.getInstance().getGame().isPlayersTurn(Client.getInstance().getPlayerIndex())){

			//We need to set the values all to disabled inside of the trade
			//getTradeView().enableDomesticTrade(false);
			getTradeOverlay().setPlayerSelectionEnabled(false);
			getTradeOverlay().setResourceSelectionEnabled(false);
			getTradeOverlay().setStateMessage("It's not your turn.");
		}

		if(game.getTradeOffer() != null){
			this.tradeinfo = game.getTradeOffer();
			//if a player has an offer
			if(game.getPlayerByID(userID).getPlayerIndex() == tradeinfo.getReceiver()){
				System.out.println("Accept trade window right here!");
				acceptTradeWindow();		
			}
		}

		if (getWaitOverlay().isModalShowing()){
			if (game.getTradeOffer()==null){
				getWaitOverlay().closeModal();
			}			
		}

	}

}



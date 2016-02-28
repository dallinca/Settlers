package client.domestic;

import shared.definitions.*;
import shared.model.player.Player;

import java.util.*;

import client.Client;
import client.base.*;
import client.data.PlayerInfo;
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
	
	public void initPlayers(){

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
		
		if(init){
			initPlayers();
		}
		boolean isPlayersTurn = Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).isPlayersTurn();
		//TESTING DELETE true sttement
		isPlayersTurn = true;
		if(isPlayersTurn){
			getTradeOverlay().setPlayerSelectionEnabled(true);
			getTradeOverlay().setResourceSelectionEnabled(true);
			getTradeOverlay().setStateMessage("set the trade you want");
		}else{
			getTradeOverlay().setPlayerSelectionEnabled(false);
			//Disabling resource selection is shifting everything over. Something is messed up with GUI??
			getTradeOverlay().setResourceSelectionEnabled(false);
			getTradeOverlay().setStateMessage("it's not your turn");
		}
		
		getTradeOverlay().showModal();
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
		getWaitOverlay().setMessage("trade transaction in progress");
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		System.out.println("DomesticTradeController setPlayerToTradeWith()");
		choosePlayer = true;
		
		if(chooseSend && choosePlayer){
			getTradeOverlay().setStateMessage("Trade!");
			getTradeOverlay().setTradeEnabled(true);
		}
		
		
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		System.out.println("DomesticTradeController setResourceToReceive()");
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		System.out.println("DomesticTradeController setResourceToSend()");
	}

	@Override
	public void unsetResource(ResourceType resource) {
		System.out.println("DomesticTradeController unsetResource()");
		getTradeOverlay().setStateMessage("set the trade you want");
		getTradeOverlay().setTradeEnabled(false);
		chooseSend = false; 
		choosePlayer = false;
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
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("DomesticTradeController update()");
	}

}



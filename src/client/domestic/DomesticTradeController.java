package client.domestic;

import shared.definitions.*;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.misc.*;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

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

	@Override
	public void startTrade() {
		System.out.println("DomesticTradeController startTrade()");
		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		System.out.println("DomesticTradeController decreaseResourceAmount()");
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		System.out.println("DomesticTradeController increaseResourceAmount");
	}

	@Override
	public void sendTradeOffer() {
		System.out.println("DomesticTradeController sendTradeOffer()");

		getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		System.out.println("DomesticTradeController setPlayerToTradeWith()");
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



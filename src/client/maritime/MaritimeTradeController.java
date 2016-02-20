package client.maritime;

import shared.definitions.*;

import java.util.Observable;
import java.util.Observer;

import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);
		System.out.println("MaritimeTradeController MaritimeTradeController()");

		setTradeOverlay(tradeOverlay);
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
	 * TODO
	 * 
	 */
	@Override
	public void startTrade() {
		System.out.println("MaritimeTradeController startTrade()");
		
		getTradeOverlay().showModal();
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void makeTrade() {
		System.out.println("MaritimeTradeController makeTrade()");

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

	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void setGiveResource(ResourceType resource) {
		System.out.println("MaritimeTradeController setGiveResource()");

	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void unsetGetValue() {
		System.out.println("MaritimeTradeController unsetGetValue()");

	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void unsetGiveValue() {
		System.out.println("MaritimeTradeController unsetGiveValue()");

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


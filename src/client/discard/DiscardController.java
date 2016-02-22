package client.discard;

import shared.definitions.*;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.misc.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	
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
	}

	public IDiscardView getDiscardView() {
		System.out.println("DiscardController getDiscardView()");
		return (IDiscardView)super.getView();
		
	}
	
	public IWaitView getWaitView() {
		System.out.println("DiscardController getWaitView()");
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		System.out.println("DiscardController increaseAmount()");
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		System.out.println("DiscardController decreaseAmount()");
	}

	@Override
	public void discard() {
		System.out.println("DiscardController discard()");
		getDiscardView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DiscardController update()");
		// TODO Auto-generated method stub
		
	}

}


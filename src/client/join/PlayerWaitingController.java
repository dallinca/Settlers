package client.join;

import java.util.Observable;
import java.util.Observer;

import client.base.*;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		System.out.println("PlayerWaitingController PlayerWaitingController");
	}

	@Override
	public IPlayerWaitingView getView() {
		System.out.println("PlayerWaitingController IPlayerWaitingView");

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
		System.out.println("PlayerWaitingController start");

		getView().showModal();
	}

	@Override
	public void addAI() {
		System.out.println("PlayerWaitingController addAI");

		// TEMPORARY
		getView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("PlayerWaitingController update");
		// TODO Auto-generated method stub
		
	}

}


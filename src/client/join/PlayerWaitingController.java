package client.join;

import java.util.Observable;
import java.util.Observer;

import shared.communication.results.nonmove.List_Result;
import client.Client;
import client.ClientException;
import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {

	private IPlayerWaitingView view;
	private Client clientInfo;
	
	/**
	 * TODO - Javadoc and Implement
	 * 
	 * 
	 */
	public PlayerWaitingController(IPlayerWaitingView view, Client clientInfo) {

		super(view);
		System.out.println("PlayerWaitingController PlayerWaitingController");
		
		this.view = view;
		this.clientInfo = clientInfo;
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * 
	 */
	@Override
	public IPlayerWaitingView getView() {
		System.out.println("PlayerWaitingController IPlayerWaitingView");

		return (IPlayerWaitingView)super.getView();
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * 
	 */
	@Override
	public void start() {
		System.out.println("PlayerWaitingController start");
		// Get the players from the gameInfo to initialize the waiting screen
		PlayerInfo[] players = new PlayerInfo[clientInfo.getGameInfo().getPlayers().size()];
		view.setPlayers(clientInfo.getGameInfo().getPlayers().toArray(players));

		getView().showModal();
		
		if(getView().isModalShowing() && clientInfo.getGameInfo().getPlayers().size() == 4) {
			getView().closeModal();
		}
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * EXTRA CREDIT.... and stuff :)
	 */
	@Override
	public void addAI() {
		System.out.println("PlayerWaitingController addAI");

		// TEMPORARY
		getView().closeModal();
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("PlayerWaitingController update");
		// TODO Auto-generated method stub
		if(getView().isModalShowing() && clientInfo.getGameInfo().getPlayers().size() == 4) {
			
			getView().closeModal();
		}
	}

}


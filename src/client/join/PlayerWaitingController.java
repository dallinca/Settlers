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
	private Client client;
	private boolean firstTime = true;
	
	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		System.out.println("PlayerWaitingController PlayerWaitingController()");
		
		this.view = view;
		client = Client.getInstance();
		Client.getInstance().addObserver(this);
	}

	@Override
	public IPlayerWaitingView getView() {
		System.out.println("PlayerWaitingController IPlayerWaitingView()");

		return (IPlayerWaitingView)super.getView();
	}

	/**
	 * Get the players from the gameInfo to initialize the waiting screen
	 * 
	 * @pre game in client != null
	 * @post waiting screen initialized from the gameInfo playersinfo
	 * 
	 */
	@Override
	public void start() {
		System.out.println("PlayerWaitingController start()");
		// Get the players from the gameInfo to initialize the waiting screen
		PlayerInfo[] players = new PlayerInfo[client.getGameInfo().getPlayers().size()];
		view.setPlayers(client.getGameInfo().getPlayers().toArray(players));
		// Show the view
		getView().showModal();
		// Start the poller for other people joining updates
		Client.getInstance().getMyServerPoller().start();
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * EXTRA CREDIT.... and stuff :)
	 */
	@Override
	public void addAI() {
		System.out.println("PlayerWaitingController addAI()");

		// TEMPORARY
		getView().closeModal();
	}

	/**
	 * Called when the Game model is updated in the Client.
	 * 
	 * @pre Game model != null
	 * @post the player waiting GUI will be updated to reflect the players in the game thus far.
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("PlayerWaitingController update()");
		PlayerInfo[] players = new PlayerInfo[client.getGameInfo().getPlayers().size()];
		client.getGameInfo().getPlayers().toArray(players);
		view.setPlayers(players);
		if(Client.getInstance().getGame() != null ) {
			System.out.println("going to try and close the modal");
			getView().closeModal();
			firstTime = false;
		}
	}

}


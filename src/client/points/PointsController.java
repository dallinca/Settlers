package client.points;

import java.util.Observable;
import java.util.Observer;

import shared.model.player.Player;
import client.Client;
import client.base.*;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		System.out.println("PointsController PointsController()");
		setFinishedView(finishedView);
		
		initFromModel();
		Client.getInstance().addObserver(this);
	}
	
	public IPointsView getPointsView() {
		System.out.println("PointsController getPointsView()");
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		System.out.println("PointsController getFinishedView()");
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		System.out.println("PointsController setFinishedView()");
		this.finishedView = finishedView;
	}
	
	/***
	 * Each players points should init to 0
	 * 
	 * @pre Nothing
	 * @post players points set to 0
	 */
	private void initFromModel() {
		System.out.println("PointsController initFromModel()");			
		getPointsView().setPoints(0);
	}

	/**
	 * Updates when the game state changes. 
	 * Checks if the game has a winner or updates players points. 
	 * 
	 * @pre Game state has changed
	 * @post The game has a winner and displays finishedView or
	 * each players points are updated for the GUI
	 * 
	 * @param Observable o 
	 * @param Object arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("PointsController update()");
		// If the game is null just return
		if(Client.getInstance().getGame() == null) {
			return;
		}

		if(Client.getInstance().getGame().doWeHaveAWinner()){
			String winnersName = Client.getInstance().getGame().getCurrentPlayer().getPlayerName();
			//player can only win the game on his turn. So all other players will receive false
			finishedView.setWinner(winnersName, true);
			finishedView.showModal();
		}
		Player[] players = Client.getInstance().getGame().getAllPlayers();
		for(int i = 0; i < players.length; i++){
			if(Client.getInstance().getUserId() == players[i].getPlayerId()) {
				getPointsView().setPoints(players[i].getVictoryPoints());
			}
		}
	}
	
}


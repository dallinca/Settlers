package client.turntracker;

import shared.definitions.CatanColor;
import shared.model.Game;
import shared.model.player.Player;

import java.util.Observable;
import java.util.Observer;

import client.Client;
import client.ClientFacade;
import client.base.*;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

	private boolean init;
	public TurnTrackerController(ITurnTrackerView view) {

		super(view);
		System.out.println("TurnTrackerController TurnTrackerController()");
		init = true;
		initFromModel();

		Client.getInstance().addObserver(this);
	}

	@Override
	public ITurnTrackerView getView() {
		System.out.println("TurnTrackerController getView()");
		return (ITurnTrackerView)super.getView();
	}
	/**
	 * A players turn ends
	 * 
	 * @pre it is the players turn
	 * @post the players turn ends 
	 **/
	@Override
	public void endTurn() {
		System.out.println("TurnTrackerController endTurn()");

		ClientFacade.getInstance().finishTurn();
		Client.getInstance().getGame().incrementPlayer();
	}
	/**
	 * initializes the turn tracker GUI element 
	 *
	 * @pre Game initialized with four players 
	 * @post Turn tracker view displayed
	 **/
	private void initFromModel() {
		System.out.println("TurnTrackerController initFromModel()");

		if(Client.getInstance().getGame() == null) {
			return;
		}
		Player[] players = Client.getInstance().getGame().getAllPlayers();
		for(int i = 0; i < players.length && players[i] != null; i++){

			getView().initializePlayer(players[i].getPlayerIndex(), players[i].getPlayerName(), players[i].getPlayerColor());
			/*System.out.println("i: "+i+ " players[i].getPlayerIndex(): "+players[i].getPlayerIndex()+ 
					"players[i].getPlayerName(): "+ players[i].getPlayerName()+" players[i].getPlayerColor(): "+players[i].getPlayerColor());*/

		}
		getView().setLocalPlayerColor(Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getPlayerColor());
	}
	
	/**
	 * Updates when the game state changes. 
	 * Highlights current player, displays longest road, displays largest army. 
	 * Allows player to end turn.
	 * 
	 * @pre Game state has changed
	 * @post view finish turn or view waiting on other players
	 * 
	 * @param Observable o 
	 * @param Object arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("TurnTrackerController update()");

		Game game = Client.getInstance().getGame();

		// If the game is null just return
		if(game == null) {
			return;
		}
		if(init){
			initFromModel();
		}
		if (game != null) {
			Player[] players = game.getAllPlayers();
			for(int i = 0; i < players.length && players[i] != null; i++){

				boolean highlight = false;
				boolean largestArmy = false;
				boolean longestRoad = false;

				if(players[i].isPlayersTurn()){
					highlight = true;
				}
				if(players[i].isHasLargestArmy()){
					largestArmy = true;
					//players[i].setHasLargestArmy(true);
				}
				if(players[i].isHasLongestRoad()){
					longestRoad = true;
					//players[i].setHasLongestRoad(true);
				}
				getView().updatePlayer(players[i].getPlayerIndex(), players[i].getVictoryPoints(), highlight,
						largestArmy, longestRoad);
			}
		}

		//Is it the players turn
		if(game.isPlayersTurn(Client.getInstance().getUserId())){
			//If game state is playing then, button enabled, else disabled
			if(game.getStatus().equals("Playing")){
				getView().updateGameState("Finish Turn", true);
			}
			else{
				getView().updateGameState("Waiting for Other Players", false);
			}
		}
		else
			getView().updateGameState("Waiting for Other Players", false);
		init = false;

	}
}


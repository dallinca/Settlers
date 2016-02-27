package client.turntracker;

import shared.definitions.CatanColor;
import shared.model.player.Player;

import java.util.Observable;
import java.util.Observer;

import client.Client;
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

	@Override
	public void endTurn() {
		System.out.println("TurnTrackerController endTurn()");
	}
	
	private void initFromModel() {
		System.out.println("TurnTrackerController initFromModel()");
		
		if(Client.getInstance().getGame() == null) {
			return;
		}
		Player[] players = Client.getInstance().getGame().getAllPlayers();
		for(int i = 0; i < players.length && players[i] != null; i++){
		
			getView().initializePlayer(players[i].getPlayerIndex(), players[i].getPlayerName(), players[i].getPlayerColor());
			System.out.println("i: "+i+ " players[i].getPlayerIndex(): "+players[i].getPlayerIndex()+ 
					"players[i].getPlayerName(): "+ players[i].getPlayerName()+" players[i].getPlayerColor(): "+players[i].getPlayerColor());

			getView().setLocalPlayerColor(players[i].getPlayerColor());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("TurnTrackerController update()");
		if(init){
			initFromModel();
		}
		else{
			Player[] players = Client.getInstance().getGame().getAllPlayers();
			for(int i = 0; i < players.length && players[i] != null; i++){

				boolean highlight = false;
				boolean largestArmy = false;
				boolean longestRoad = false;
				
				if(players[i].isPlayersTurn()){
					highlight = true;
				}
				if(players[i].isHasLargestArmy()){
					largestArmy = true;
				}
				if(players[i].isHasLongestRoad()){
					longestRoad = true;
				}
		
				getView().updatePlayer(players[i].getPlayerIndex(), players[i].getVictoryPoints(), highlight,
						  largestArmy, longestRoad);
			}
		}
		init = false;
	}

}


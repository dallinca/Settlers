package client;
import java.util.*;

import client.map.MapController;
import client.proxy.IServerProxy;
import client.proxy.MockServerProxy;
import shared.model.Game;

public class Client extends Observable {
	
	private Game game;
	private IServerProxy isp;
	private ClientFacade cf;
	private int loggedInUserId;
	private int playerIndex;
	
	public Client() {
	
	}
	
	public Client(IServerProxy isp){
		
		this.isp = isp;
		
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		//update all controllers by passing each the current game object
		notifyObservers(this.game);
	}
	
	public void addObservers(){
		//A list of controllers that implement observer needs to be added
		/*for (int i =0 ; i < controllerList.size(); i++)
		 * this.addObserver(controllerList[i])*/
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}

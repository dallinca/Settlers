package client;
import java.util.*;

import client.data.GameInfo;
import client.map.MapController;
import client.proxy.IServerProxy;
import client.proxy.MockServerProxy;
import shared.definitions.CatanColor;
import shared.model.Game;

public class Client extends Observable {
	
	private GameInfo gameInfo; // This is a class provided to us. We are using for player waiting modal view.
	private Game game;
	private IServerProxy isp;
	private ClientFacade cf;
	private int UserId;
	private String name;
	private CatanColor color;
	private int playerIndex;
	
	private static Client SINGLETON = null;
	// CONSTRUCTORS
	//////////////////////
	private Client(){ }
	
	public static Client getInstance( ) {
		if(SINGLETON == null) {
			SINGLETON = new Client();
		}
	    return SINGLETON;
	}

	// GETTERS AND SETTERS
	//////////////////////
	
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
		//for (int i =0 ; i < controllerList.size(); i++)
			//this.addObserver(controllerList[i]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CatanColor getColor() {
		return color;
	}

	public void setColor(CatanColor color) {
		this.color = color;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}
	
	
}

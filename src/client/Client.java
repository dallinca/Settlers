package client;
import java.util.*;

import client.data.GameInfo;

import shared.definitions.CatanColor;
import shared.model.Game;

public class Client extends Observable {
	
	private GameInfo gameInfo; // This is a class provided to us. We are using for player waiting modal view.
	private Game game;
	private int UserId;
	private String name;
	private CatanColor color;
	private int playerIndex;
	
	private ServerPoller myServerPoller;
	
	private static Client SINGLETON = null;
	// CONSTRUCTORS
	//////////////////////
	private Client(){
		
		setMyServerPoller(new ServerPoller());
				
	}
	
	
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
		setChanged();
		notifyObservers(this.game);
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


	public ServerPoller getMyServerPoller() {
		return myServerPoller;
	}

	protected void setMyServerPoller(ServerPoller myServerPoller) {
		this.myServerPoller = myServerPoller;
	}
	
	
}

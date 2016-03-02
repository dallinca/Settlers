package client;
import java.util.*;

import client.data.GameInfo;
import client.data.PlayerInfo;
import shared.definitions.CatanColor;
import shared.model.Game;
import shared.model.board.Hex;

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
	
	public synchronized Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		System.out.println("Client setGame()");
		if(game == null) {
			System.out.println("Game given to set is null, not doing that... :-)");
			System.out.println("Number players in game: " + gameInfo.getPlayers().size());
			return;
		}
		this.game = game;
		//update all controllers by passing each the current game object
		setChanged();
		notifyObservers(this.game);
	}

	public String getName() {
		return name;
	}
	
	/**
	 * For use in the player waiting screen. When a new player joins the game, <br>
	 * update the player waiting screen for the rest.
	 * 
	 * @pre player != null
	 * @post all the Controllers in the GUI will be notified of the change in Client information
	 * @param playerInfo
	 */
	public void updatePlayersInGameInfo(ArrayList<PlayerInfo> playerInfo) {
		System.out.println("Client updatePlayersInGameInfo");
		// look at all the players that are currently in the game server side
		for(int servI = 0; servI < playerInfo.size(); servI++) {
			boolean existsInClient = false;
			// compare each one against all the players that are shown client side
			for(int clientI = 0; clientI < gameInfo.getPlayers().size(); clientI++) {
				if(playerInfo.get(servI).getId() == gameInfo.getPlayers().get(clientI).getId()) {
					existsInClient = true;
				}
			}
			// If the player exists server side and not client side, add 'em to the Client GameInfo
			if(existsInClient == false) {
				gameInfo.addPlayer(playerInfo.get(servI));
			}
		}
		setChanged();
		notifyObservers(this.gameInfo);
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

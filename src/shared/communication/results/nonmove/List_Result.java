package shared.communication.results.nonmove;

import java.util.ArrayList;

import client.data.GameInfo;

public class List_Result {
	
	private GameInfo[] games = null;
	
	// CONSTRUCTORS
	//////////////////////
	public List_Result(ArrayList<GameInfo> games) {
		this.games = new GameInfo[games.size()];
		this.games = games.toArray(this.games);
	}

	public List_Result(GameInfo[] games) {
		this.games = games;
	}
	
	public List_Result() {
		
	}

	// GETTERS AND SETTERS
	//////////////////////
	
	public GameInfo[] getGames() {
		return games;
	}

	public void setGames(GameInfo[] games) {
		this.games = games;
	}


	
}

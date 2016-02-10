package shared.communication.results.nonmove;

import com.google.gson.JsonElement;

import shared.model.Game;

public class GetVersion_Result {
	
	Game currentVersion;	
		
	public GetVersion_Result(){
		currentVersion = null;
	}
	
	GetVersion_Result(Game current){
		currentVersion = current;
		
	}

	public GetVersion_Result(JsonElement json) {
		

		//Turn this JsonElement into a game object.
		
		
		
	}

	public Game getGame() {
		return currentVersion;
	}

}

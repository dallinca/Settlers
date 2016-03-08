package shared.communication.params.move;


/**
 * Defines a finish turn command for the server.
 * 
 */
public class FinishTurn_Params {
	
	private final String type = "finishTurn";
	private int playerIndex;

	public FinishTurn_Params(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	
	

}

package shared.communication.params.move.devcard;

/**
 * Defines a player monument card command for the server.
 *
 */
public class PlayMonument_Params {
	
	private final String type = "Monument";
	private int playerIndex;

	
	public PlayMonument_Params(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	
	

}

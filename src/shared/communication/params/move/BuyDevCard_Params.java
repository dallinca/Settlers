package shared.communication.params.move;

/**
 * Defines a buy card command for the server.
 */
public class BuyDevCard_Params {
	
	private final String type = "buyDevCard";
	private int playerIndex;

	public BuyDevCard_Params(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}	

}

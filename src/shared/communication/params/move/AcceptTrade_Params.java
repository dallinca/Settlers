package shared.communication.params.move;

/**
 * 
 * Defines an accept trade command for the server.
 * 
 */
public class AcceptTrade_Params {
	
	private final String type = "acceptTrade";
	private int playerIndex;
	private boolean willAccept;

	public AcceptTrade_Params(int playerIndex, boolean willAccept) {
		this.willAccept = willAccept;
		this.playerIndex = playerIndex;
	}

	public boolean isWillAccept() {
		return willAccept;
	}

	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}

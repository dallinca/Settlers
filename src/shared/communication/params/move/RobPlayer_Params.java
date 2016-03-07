package shared.communication.params.move;

import shared.locations.HexLocation;

/**
 * Defines a rob player command for the server.
 */
public class RobPlayer_Params {
	
	private final String type = "robPlayer";
	private int playerIndex;
	private HexLocation location;
	private int victimIndex;

	public RobPlayer_Params(int playerIndex, HexLocation hex, int victimIndex) {
		this.playerIndex = playerIndex;
		this.location = hex;
		this.victimIndex = victimIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation hex) {
		this.location = hex;
	}

	public int getVictimIndex() {
		return victimIndex;
	}

	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
}

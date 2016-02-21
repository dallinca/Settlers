package shared.communication.params.move;

import shared.locations.HexLocation;

public class RobPlayer_Params {
	
	private int playerIndex;
	private HexLocation hex;
	private int victimIndex;

	public RobPlayer_Params(int playerIndex, HexLocation hex, int victimIndex) {
		this.playerIndex = playerIndex;
		this.hex = hex;
		this.victimIndex = victimIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public HexLocation getHex() {
		return hex;
	}

	public void setHex(HexLocation hex) {
		this.hex = hex;
	}

	public int getVictimIndex() {
		return victimIndex;
	}

	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
}

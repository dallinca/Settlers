package shared.communication.params.move.devcard;

import shared.locations.HexLocation;

public class PlaySoldier_Params {
	
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;	

	public PlaySoldier_Params(int playerIndex, int victimIndex, HexLocation hex) {
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

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public int getVictimIndex() {
		return victimIndex;
	}

	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
	
	

}

package shared.communication.params.move;

import shared.communication.params.move.data.BorderSpot;
import shared.locations.EdgeLocation;

/**
 * Defines a build road command for the server.
 * 
 */
public class BuildRoad_Params {

	private final String type = "buildRoad";
	private int playerIndex;
	private BorderSpot roadLocation;
	private boolean free;
	
	public BuildRoad_Params(int playerIndex, EdgeLocation roadLocation,
			boolean free) {
		this.playerIndex = playerIndex;
		this.roadLocation = new BorderSpot(roadLocation);
		this.free = free;		
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public BorderSpot getRoadLocation() {
		return roadLocation;
	}

	public void setRoadLocation(BorderSpot roadLocation) {
		this.roadLocation = roadLocation;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
	

}

package shared.communication.params.move.devcard;

import shared.communication.params.move.data.BorderSpot;
import shared.locations.EdgeLocation;

public class PlayRoadBuilding_Params {
	
	private final String type = "Road_Building";
	private int playerIndex;
	private BorderSpot spot1;
	private BorderSpot spot2;
	
/**
 * 
 * @param playerIndex
 * @param spot1
 * @param spot2
 */
	public PlayRoadBuilding_Params(int playerIndex, EdgeLocation spot1,
			EdgeLocation spot2) {
		this.playerIndex = playerIndex;
		this.spot1 = new BorderSpot(spot1);
		this.spot2 = new BorderSpot(spot2);
		
	}


	public int getPlayerIndex() {
		return playerIndex;
	}


	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}


	public BorderSpot getSpot1() {
		return spot1;
	}


	public void setSpot1(BorderSpot spot1) {
		this.spot1 = spot1;
	}


	public BorderSpot getSpot2() {
		return spot2;
	}


	public void setSpot2(BorderSpot spot2) {
		this.spot2 = spot2;
	}
	
	

}

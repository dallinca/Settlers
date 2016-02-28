package shared.communication.params.move;

import shared.communication.params.move.data.BuildLocation;
import shared.locations.VertexLocation;

public class BuildSettlement_Params {
	
	private final String type = "buildSettlement";
	private int playerIndex;
	private BuildLocation vertexLocation;
	private boolean free;

	public BuildSettlement_Params(int playerIndex, VertexLocation location,
			boolean free) {
		this.playerIndex = playerIndex;
		this.vertexLocation = new BuildLocation(location);
		this.free = free;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public BuildLocation getVertexLocation() {
		return vertexLocation;
	}

	public void setVertexLocation(BuildLocation vertexLocation) {
		this.vertexLocation = vertexLocation;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
}

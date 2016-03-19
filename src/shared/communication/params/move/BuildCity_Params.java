package shared.communication.params.move;

import shared.communication.params.move.data.BuildLocation;
import shared.locations.VertexLocation;

/**
 * Defines a build city command for the server.
 * 
 */
public class BuildCity_Params {

	private final String type = "buildCity";
	private int playerIndex;
	private BuildLocation vertexLocation;
	
	//for use in BuildCity_Command
	VertexLocation location;

	public BuildCity_Params(int playerIndex, VertexLocation location) {
		this.playerIndex = playerIndex;
		this.vertexLocation = new BuildLocation(location);
		this.location = location;
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
	
	public VertexLocation getCmdVertLocation(){
		return location;
	}
	
}

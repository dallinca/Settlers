package shared.model.board;

import shared.locations.EdgeLocation;
import shared.model.items.Road;

/**
 * The Edge class is used to store whether an edge has a road owned by a player
 * 
 * Operations are provided for placing a road on the edge and detecting if the edge has a
 * road.
 * 
 *
 */
public class Edge {

	Road road;
	EdgeLocation edgeLocation;
	
	public Edge() {}
	
	/**
	 * Returns whether the Edge has a road
	 * 
	 * @pre None
	 * 
	 * @post Return value says whether the edge has a road
	 */
	public boolean hasRoad() {
		if(road == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Assigns the edge a road
	 * 
	 * @pre road == null, this check should happen at the board level
	 * 
	 * @post road = (Road)Object, the edge now has a player's road
	 */
	public void buildRoad(Road road) {
		this.road = road;
	}

	public EdgeLocation getEdgeLocation() {
		return edgeLocation;
	}

	public void setEdgeLocation(EdgeLocation edgeLocation) {
		this.edgeLocation = edgeLocation;
	}
	
	
	
}

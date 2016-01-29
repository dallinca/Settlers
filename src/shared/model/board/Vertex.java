package shared.model.board;

import shared.locations.VertexLocation;
import shared.model.items.Municipal;

/**
 * The Vertex class is used to store whether a vertex has a municipal
 * built on it.
 * 
 * Operations are provided for assigning the vertex a municipal, and
 * checking whether there is a a municipal already assigned.
 * 
 *
 */
public class Vertex {
	
	Municipal municipal;
	TradePort tradePort;
	VertexLocation vertexLocation;
	
	public Vertex() {}
	
	/**
	 * Returns whether the Vertex has a municipal
	 * 
	 * @pre None
	 * 
	 * @post Return value says whether the vertex has a municipal
	 */
	public boolean hasMunicipal() {
		if(municipal == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Assigns the edge a municipality
	 * 
	 * @pre municipal == null, or municipal is a settlement, this check should happen at the board level
	 * 
	 * @post municipal = (Municipal)Object, the edge now has a player's road
	 */
	public void buildMunicipal(Municipal municipal) {
		this.municipal = municipal;
	}

	/**
	 * Returns whether the vertex has a tradePort
	 * 
	 * @pre None
	 * 
	 * @post Return value says whether the vertex has a tradePort
	 */
	public boolean hasTradePort() {
		if(tradePort == null) {
			return false;
		}
		return true;
	}
	
	public TradePort getTradePort() {
		return tradePort;
	}

	public void setTradePort(TradePort tradePort) {
		this.tradePort = tradePort;
	}

	public VertexLocation getVertexLocation() {
		return vertexLocation;
	}

	public void setVertexLocation(VertexLocation vertexLocation) {
		this.vertexLocation = vertexLocation;
	}

	public Municipal getMunicipal() {
		return municipal;
	}

	public void setMunicipal(Municipal municipal) {
		this.municipal = municipal;
	}
	
	
	

}

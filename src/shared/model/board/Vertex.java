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
	
	private Municipal municipal;
	private TradePort tradePort;

	private Board board;
	private int x_coord_ver;
	private int y_coord_ver;
	
	/**
	 * TODO
	 * 
	 * @param board
	 * @param x_coord_ver
	 * @param y_coord_ver
	 */
	public Vertex(Board board, int x_coord_ver, int y_coord_ver) {
		this.board = board;
		this.x_coord_ver = x_coord_ver;
		this.y_coord_ver = y_coord_ver;
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public Vertex[] getAdjacentVertices() {
		// the max number of adjacent Vertices is 3
		Vertex[] adjacentVertices = new Vertex[3];
		
		// If x_coord_ver is odd
		// then vertex has two vertices on the left and one on the right
		if(x_coord_ver % 2 != 0) {
			adjacentVertices[0] = board.getVertex( x_coord_ver - 1, y_coord_ver - 1); // Upper Left Vertex
			adjacentVertices[1] = board.getVertex( x_coord_ver + 1, y_coord_ver - 1); // Right Vertex
			adjacentVertices[2] = board.getVertex( x_coord_ver - 1, y_coord_ver + 1); // Lower Left Vertex
		}
		// else vertex has two vertices on the right and one on the left
		else {
			adjacentVertices[0] = board.getVertex( x_coord_ver - 1, y_coord_ver + 1); // Left Vertex
			adjacentVertices[1] = board.getVertex( x_coord_ver + 1, y_coord_ver - 1); // Upper Right Vertex
			adjacentVertices[2] = board.getVertex( x_coord_ver + 1, y_coord_ver + 1); // Lower Right Vertex
		}
		
		return adjacentVertices;
	}
	
	/**
	 * TODO
	 * 
	 * 
	 */
	public Hex[] getAdjacentHexes() {
		// the max number of adjacent Hexes is 3
		Hex[] adjacentHexes = new Hex[3];
		
		// If x_coord_ver is odd
		// then vertex has two Hexes on the right and one on the left
		if(x_coord_ver % 2 != 0) {
			adjacentHexes[0] = board.getHex( (x_coord_ver - 1)/2, (y_coord_ver + 1)/2); // Left Hex
			adjacentHexes[1] = board.getHex( (x_coord_ver + 1)/2, (y_coord_ver - 1)/2); // Upper Right Hex
			adjacentHexes[2] = board.getHex( (x_coord_ver + 1)/2, (y_coord_ver + 1)/2); // Lower Right Hex
		}
		// else vertex has two Hexes on the left and one on the right
		else {
			adjacentHexes[0] = board.getHex( x_coord_ver / 2, y_coord_ver / 2); // Upper Left Hex
			adjacentHexes[1] = board.getHex( (x_coord_ver + 2)/2, y_coord_ver / 2); // Right Hex
			adjacentHexes[2] = board.getHex( x_coord_ver / 2, (y_coord_ver + 2)/2); // Lower Left Hex
		}
		
		return adjacentHexes;
	}
	
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

	public Municipal getMunicipal() {
		return municipal;
	}

	public void setMunicipal(Municipal municipal) {
		this.municipal = municipal;
	}

	public int getX_coord_ver() {
		return x_coord_ver;
	}

	public int getY_coord_ver() {
		return y_coord_ver;
	}
	
	
	

}

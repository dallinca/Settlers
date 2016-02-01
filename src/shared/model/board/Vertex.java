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
	 * Initialize an Instance of the Vertex Class. Should Only be called from the Board Class.
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
	 * Retrieves the adjacent Edges to this vertex. May have null values if where
	 * the vertex doesn't have a valid adjacent edge. i.e a vertex on the outer edge
	 * will only have 2 adjacent edges on the map.
	 * 
	 * @pre None
	 * 
	 * @post Retrieves the adjacent Vertices to this vertex, Size of 3.
	 */
	public Edge[] getAdjacentEdges() {
		// the max number of adjacent Vertices is 3
		Edge[] adjacentEdges = new Edge[3];
		
		// If x_coord_ver is odd
		// then vertex has two roads on the left and one on the right
		if(x_coord_ver % 2 != 0) {
			adjacentEdges[0] = board.getEdge( (x_coord_ver - 1)/2, (y_coord_ver + 1)/2, ModEdgeDirection.RIGHT); // Upper Left Road
			adjacentEdges[1] = board.getEdge( (x_coord_ver + 1)/2, (y_coord_ver + 1)/2, ModEdgeDirection.UP); // Right Road
			adjacentEdges[2] = board.getEdge( (x_coord_ver + 1)/2, (y_coord_ver + 1)/2, ModEdgeDirection.LEFT); // Lower Left Road
		}
		// else vertex has two roads on the right and one on the left
		else {
			adjacentEdges[0] = board.getEdge( x_coord_ver/2, (y_coord_ver + 2)/2, ModEdgeDirection.UP); // Left Road
			adjacentEdges[1] = board.getEdge( (x_coord_ver + 2)/2, y_coord_ver/2, ModEdgeDirection.LEFT); // Upper Right Road
			adjacentEdges[2] = board.getEdge( x_coord_ver/2, (y_coord_ver + 2)/2, ModEdgeDirection.RIGHT); // Lower Right Road
		}
		
		
		return adjacentEdges;
	}
	
	/**
	 * Retrieves the adjacent Vertices to this vertex. May have null values if where
	 * the vertex doesn't have a valid adjacent vertex. i.e a vertex on the outer edge
	 * will only have 2 adjacent vertexes on the map.
	 * 
	 * @pre None
	 * 
	 * @post Retrieves the adjacent Vertices to this vertex, Size of 3.
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
	 * Retrieves the adjacent Hexes to this vertex. May have null values if where
	 * the vertex doesn't have a valid adjacent Hex. i.e a vertex on the outer edge
	 * will only have 1 or 2 adjacent Hexes on the map.
	 * 
	 * @pre None
	 * 
	 * @post Retrieves the adjacent Hexes to this Vertex, Size of 3
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

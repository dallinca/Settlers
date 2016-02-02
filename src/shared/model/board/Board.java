package shared.model.board;

import shared.model.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.board.exceptions.*;
import shared.model.items.City;

/**
 * The Board Class is used to store all the information about the game board
 * including the map with its hexes, vertices, edges, and tradeports.
 * 
 * Operations allow for independent players to place roads, settlements, and
 * removes settlements when a city is placed. 
 * 
 *
 * Domain:
 * 		ArrayList Hexes: 18 roads
 * 		ArrayList Vertices: 54 vertices
 *		ArrayList Edges: 54 edges
 */
public class Board {

	private Hex hexWithRobber;
	
	private Hex[][] mapHexes = new Hex[6][6];
	private Edge[][] mapULEdges = new Edge[7][7];
	private Edge[][] mapUUEdges = new Edge[7][7];
	private Edge[][] mapUREdges = new Edge[7][7];
	private Vertex[][] mapVertices = new Vertex[12][12];
	
	/**
	 * Create and instance of the Board Class, and initializes all of the Hexes,
	 * Edges, and Vertices of the Map.
	 * 
	 * @pre none
	 * 
	 * @post All of the instances Of Hex, Edge, and Vertex created for the map
	 */
	public Board() {
		// Need to initialize which hex location is starting with the robber (Should be the desert)
		initHexes();
		initBorders();
		initVertices();
	}
	
	
	/**
	 * Determines Whether the Robber can be moved to the given hexLocation
	 * 
	 * @pre hexLocation must not be null, hex must be a land hex
	 * 
	 * @post Return value is whether the Robber can be moved to the given hexLocation
	 * 
	 */
	public boolean canDoMoveRobberToHex(HexLocation hexLocation) {
		if(hexLocation == null) {
			return false;
		}
		
		// Use the hexlocation to find that Hex in our data Structure
		Hex hex = null;
		try {
			hex = getHex(hexLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Check to see if the hex exists in the data structure and if it does
		// Make sure it isn't the hex that currently has the robber
		if(hex == null || hex.equals(hexWithRobber)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Move the robber to a specified hexLocation
	 * @throws Exception 
	 * 
	 * @pre canDoMoveRobberToHex(hexLocation) == true
	 * 
	 * @post hexWithRobber = hex of specified hexLocation, the Robber has been moved to the new hex,
	 * or MoveRobberException thrown
	 */
	public void moveRobberToHex(HexLocation hexLocation) throws Exception {
		if(!canDoMoveRobberToHex(hexLocation)) {
			throw new MoveRobberException("Cannot move robber to given hex");
		}
		// Use the hexlocation to find that Hex in our data Structure
		Hex hex = getHex(hexLocation);
		hexWithRobber.takeRobber();
		hexWithRobber = hex;
		hex.giveRobber();
	}
	
	/**
	 * Determines whether a specified player's road can be placed on a specified edge
	 * This call should be happening after the player has already verified ability to purchase a road
	 * 
	 * @pre player != null, edgeLocation != null
	 * @pre player should be verified as having the resources necessary to buy a road
	 * 
	 * @post Return value is whether an Road can be placed on the specified edgeLocation
	 */
	public boolean canDoPlaceRoadOnEdge(Player player, EdgeLocation edgeLocation) {
		if(edgeLocation == null) {
			return false;
		}
		// Use the edgelocation to find that Edge in our data Structure
		Edge edge = null;
		try {
			edge = getEdge(edgeLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// player not null, edge not null, and does not have a road
		if(player == null || edge == null || edge.hasRoad() == true) {
			return false;
		}
		// edge has an adjacent edge that has a road owned by the player, that does not
		// have an enemy municipality on the converging vertex
		EdgeSide[] sides = edge.getSides();
		for(int i = 0; i < sides.length; i++) {
			// the side either has no Municipal, or the municipal is owned by the player wanting to place a road
			if(sides[i].getVertex().hasMunicipal() == false
					|| sides[i].getVertex().getMunicipal().getPlayer().getPlayerId() == player.getPlayerId()) {
				Edge[] edges = sides[i].getEdges();
				for(int j = 0; j < edges.length; j++) {
					// there is an adjacent edge that has a road owned by the player wanting to place a road
					if(edges[j].hasRoad() && edges[j].getRoad().getPlayer().getPlayerId() == player.getPlayerId()) {
						return true; // We found a case that makes it possible to place a road
					}
				}
			}
		}
		// There was no case found allowing the player to place a road at the edgeLocation in question
		return false;
	}
	
	/**
	 * Places a road on a specified edge
	 * @throws Exception 
	 * 
	 * @pre canDoPlaceRoadOnEdge != false,
	 * 
	 * @post road is placed on the specified edge, or PlaceRoadOnEdgeException thrown
	 */
	public void placeRoadOnEdge(Player player, EdgeLocation edgeLocation) throws Exception {
		if(canDoPlaceRoadOnEdge(player, edgeLocation) == false) {
			throw new PlaceRoadOnEdgeException("canDoPlaceRoadOnEdge = false");
		}
		// Use the edgeLocation to find that Edge in our data Structure
		Edge edge = getEdge(edgeLocation);
		
		// Place the road on the edge
		player.buyRoad(edge);
	}

	/**
	 * Determine whether a specified player's settlement can be placed on a specified vertexLocation
	 * This call should be happening after the player has already verified ability to purchase a settlement
	 * 
	 * @pre vertex != null, settlement != null
	 * @pre player should be verified as having the resources necessary to buy a settlement
	 * 
	 * @post Return value is whether the specified settlement can be placed on the specified vertex
	 */
	public boolean canDoPlaceSettlementOnVertex(Player player, VertexLocation vertexLocation) {
		if(vertexLocation == null) {
			return false;
		}
		// Use the vertexlocation to find that Vertex in our data Structure
		Vertex vertex = null;
		try {
			vertex = getVertex(vertexLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// player not null, vertex not null, and does not have any player's settlement or city
		if(player == null || vertex == null || vertex.hasMunicipal() == true) {
			return false;
		}
		
		// vertex does not have an adjacent vertex with any players settlement or city
		Vertex[] adjacentVertices = vertex.getAdjacentVertices();
		for(int i = 0; i < adjacentVertices.length; i++) {
			// Use null to account for potentially nonvalid bordering vertices
			if(adjacentVertices[i] != null && adjacentVertices[i].hasMunicipal()) {
				return false; // There can't be any municipality on an adjacent vertex
			}
		}
		
		// vertex has an adjacent border that has a road owned by the player of the settlement to place
		Edge[] edges = vertex.getAdjacentEdges();
		for(Edge edge: edges) {
			// If the edge has a road, that is owned by the player in question, then the player has access to the vertex
			if(edge.getRoad() != null && edge.getRoad().getPlayer().getPlayerId() == player.getPlayerId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Places a settlement on a specified vertex
	 * @throws Exception 
	 * @pre canDoPlaceSettlementOnVertex != false,  
	 * 
	 * @post settlement is placed on the specified vertex, or PlaceSettlementOnVertexException thrown
	 */
	public void placeSettlementOnVertex(Player player, VertexLocation vertexLocation) throws Exception {
		if(canDoPlaceSettlementOnVertex(player, vertexLocation) == false) {
			throw new PlaceSettlementOnVertexException("canDoPlaceSettlementOnVertex = false");
		}
		// Use the vertexLocation to find that Vertex in our data Structure
		Vertex vertex = getVertex(vertexLocation);
		
		// Place the settlement on the vertex
		player.buySettlement(vertex);
	}

	/**
	 * Determine whether a specified player's city can be placed on a specified vertex
	 * This call should be happening after the player has already verified ability to purchase a city
	 * 
	 * @pre vertex != null, city != null
	 * @pre player should be verified as having the resources necessary to buy a city
	 * 
	 * @post Return value is whether the city can be placed on the specified vertex
	 */
	public boolean canDoPlaceCityOnVertex(Player player, VertexLocation vertexLocation) {
		if(vertexLocation == null) {
			return false;
		}
		// Use the vertexlocation to find that Vertex in our data Structure
		Vertex vertex = null;
		try {
			vertex = getVertex(vertexLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Check that the player and vertex are not null, that the vertex has a settlement, and that the settlement is owned by the given player
		if(player == null || vertex == null || vertex.hasMunicipal() == false || vertex.getMunicipal() instanceof City 
				|| vertex.getMunicipal().getPlayer().getPlayerId() != player.getPlayerId()) {
			return false;
		}
		return true;
	}

	/**
	 * Places a city on a specified vertex
	 * @throws Exception 
	 * @pre canDoPlaceCityOnVertex != false,  
	 * 
	 * @post city is placed on the specified vertex, or PlaceCityOnVertexException thrown
	 */
	public void placeCityOnVertex(Player player, VertexLocation vertexLocation) throws Exception {
		if(canDoPlaceCityOnVertex(player, vertexLocation) == false) {
			throw new PlaceCityOnVertexException("canDoPlaceCityOnVertex = false");
		}
		// Use the vertexlocation to find that Vertex in our data Structure
		Vertex vertex = getVertex(vertexLocation);
		
		// Place the city on the vertex
		player.buyCity(vertex);
	}
	
	/**
	 * Gets the Hex according to Our defined x,y Hex plane
	 * 
	 * 
	 * @param x_coord_hex
	 * @param y_coord_hex
	 * 
	 * @pre x_coord_hex must be between 0 and 5 
	 * @pre y_coord_hex must be between 0 and 5 
	 * 
	 * @post will return null if it is not a valid hex location, else return the hex at the specified location
	 */
	public Hex getHex(int x_coord_hex, int y_coord_hex) {
		// If the queried hex is beyond the preset array bounds, bail
		if(x_coord_hex < 0 || x_coord_hex > 5 || y_coord_hex < 0 || y_coord_hex > 5) {
			return null;
		}
		// return the Hex queried, may be null if not a valid location
		return mapHexes[x_coord_hex][y_coord_hex];
	}
	
	/**
	 * Gets the hex according to the map-gui defined locations
	 * @throws Exception
	 * 
	 * @param hexLocation
	 * 
	 * @pre HecLocation != null
	 * 
	 * @post Returns the Hex at the specified location
	 */
	public Hex getHex(HexLocation hexLocation) throws Exception {
		if(hexLocation == null) {
			throw new Exception(" Should not call getHex(hexLocation) with a null hexLocation");
		}
		return getHex(hexLocation.getX() + 3, hexLocation.getY() + 3);
	}
	
	/**
	 * Get the Edge according to our defined x, y, direction plane
	 * 
	 * @param x_coord_edg
	 * @param y_coord_edg
	 * @param direction_edg
	 * 
	 * @pre x_coord_edg must be between 0 and 6
	 * @pre y_coord_edg must be between 0 and 6
	 * @pre direction_edg != null
	 * 
	 * @post will return the Edge at the specified location
	 */
	public Edge getEdge(int x_coord_edg, int y_coord_edg, ModEdgeDirection direction_edg) {
		// If the queried hex is beyond the preset array bounds, bail
		if(x_coord_edg < 0 || x_coord_edg > 6 || y_coord_edg < 0 || y_coord_edg > 6 || direction_edg == null) {
			return null;
		}
		// return the Vertex queried, may be null if not a valid location
		if(direction_edg == ModEdgeDirection.LEFT) {
			return mapULEdges[x_coord_edg][y_coord_edg];
		} else if(direction_edg == ModEdgeDirection.UP) {
			return mapUUEdges[x_coord_edg][y_coord_edg];
		} else {
			return mapUREdges[x_coord_edg][y_coord_edg];
		}
	}
	
	/**
	 * Get the Edge according to the map-gui defined locations
	 * 
	 * @param edgeLocation
	 * @throws Exception 
	 * 
	 * @pre edgeLocation != null
	 * 
	 * @post Return the Edge at the specified location
	 */
	public Edge getEdge(EdgeLocation edgeLocation) throws Exception {
		if(edgeLocation == null) {
			throw new Exception("Should not call getEdge(edgeLocation) with a null edgeLocation");
		}
		EdgeLocation normalizedLocation = edgeLocation.getNormalizedLocation();
		if(normalizedLocation.getDir() == EdgeDirection.NorthWest) {
			return getEdge(edgeLocation.getHexLoc().getX() + 3, edgeLocation.getHexLoc().getY() + 3, ModEdgeDirection.LEFT);
		} else if(normalizedLocation.getDir() == EdgeDirection.North) {
			return getEdge(edgeLocation.getHexLoc().getX() + 3, edgeLocation.getHexLoc().getY() + 3, ModEdgeDirection.UP);
		} else {
			return getEdge(edgeLocation.getHexLoc().getX() + 3, edgeLocation.getHexLoc().getY() + 3, ModEdgeDirection.RIGHT);
		}
	}
	
	/**
	 * Get the Edge according to our defined x, y, direction plane
	 * 
	 * @param x_coord_ver
	 * @param y_coord_ver
	 * 
	 * @pre x_coord_ver must be between 0 and 11
	 * @pre y_coord_ver must be between 0 and 11
	 * 
	 * @post will return null if it is not a valid vertex location, else return the vertex at the specified location
	 */
	public Vertex getVertex(int x_coord_ver, int y_coord_ver) {
		// If the queried hex is beyond the preset array bounds, bail
		if(x_coord_ver < 0 || x_coord_ver > 11 || y_coord_ver < 0 || y_coord_ver > 11) {
			return null;
		}
		// return the Vertex queried, may be null if not a valid location
		return mapVertices[x_coord_ver][y_coord_ver];
	}
	
	/**
	 * Get the Vertex according to the map-gui defined locations
	 * 
	 * @param vertexLocation
	 * @throws Exception 
	 * 
	 * @pre vertexLocation != null
	 * 
	 * @post Return the Vertex at the specified location
	 */
	public Vertex getVertex(VertexLocation vertexLocation) throws Exception {
		if(vertexLocation == null) {
			throw new Exception("Should not call getVertex(vertexLocation) with a null vertexLocation");
		}
		// Normalized the location received
		VertexLocation normalizedLocation = vertexLocation.getNormalizedLocation();
		// If we are converting from the North East
		if(normalizedLocation.getDir() == VertexDirection.NorthEast) {
			return getVertex( 2 * (normalizedLocation.getHexLoc().getX() + 3), (2 * (normalizedLocation.getHexLoc().getY() + 3)) - 2 );
		}
		// If we are converting from the North West
		else {
			return getVertex( (2 * (normalizedLocation.getHexLoc().getX() + 3)) - 1, (2 * (normalizedLocation.getHexLoc().getY() + 3)) - 1 );
		}
	}

	private void initHexes() {
		
		//    0  1  2  3  4  5
		// 0 [_][_][_][_][_][_]
		// 1 [_][_][_][X][X][X]
		// 2 [_][_][X][X][X][X]
		// 3 [_][X][X][X][X][X]
		// 4 [_][X][X][X][X][_]
		// 5 [_][X][X][X][_][_]
		
		mapHexes[0][0] = null;
		mapHexes[0][1] = null;
		mapHexes[0][2] = null;
		mapHexes[0][3] = null;
		mapHexes[0][4] = null;
		mapHexes[0][5] = null;

		mapHexes[1][0] = null;
		mapHexes[1][1] = null;
		mapHexes[1][2] = null;
		mapHexes[1][3] = new Hex(this,1,3);
		mapHexes[1][4] = new Hex(this,1,4);
		mapHexes[1][5] = new Hex(this,1,5);

		mapHexes[2][0] = null;
		mapHexes[2][1] = null;
		mapHexes[2][2] = new Hex(this,2,2);
		mapHexes[2][3] = new Hex(this,2,3);
		mapHexes[2][4] = new Hex(this,2,4);
		mapHexes[2][5] = new Hex(this,2,5);

		mapHexes[3][0] = null;
		mapHexes[3][1] = new Hex(this,3,1);
		mapHexes[3][2] = new Hex(this,3,2);
		mapHexes[3][3] = new Hex(this,3,3);
		mapHexes[3][4] = new Hex(this,3,4);
		mapHexes[3][5] = new Hex(this,3,5);

		mapHexes[4][0] = null;
		mapHexes[4][1] = new Hex(this,4,1);
		mapHexes[4][2] = new Hex(this,4,2);
		mapHexes[4][3] = new Hex(this,4,3);
		mapHexes[4][4] = new Hex(this,4,4);
		mapHexes[4][5] = null;

		mapHexes[5][0] = null;
		mapHexes[5][1] = new Hex(this,5,1);
		mapHexes[5][2] = new Hex(this,5,2);
		mapHexes[5][3] = new Hex(this,5,3);
		mapHexes[5][4] = null;
		mapHexes[5][5] = null;
	}

	private void initBorders() {
		initULBorders();
		initUUBorders();
		initURBorders();
	}
	
	private void initULBorders() {
		//    0  1  2  3  4  5  6
		// 0 [_][_][_][_][_][_][_]
		// 1 [_][_][_][X][X][X][X]
		// 2 [_][_][X][X][X][X][X]
		// 3 [_][X][X][X][X][X][X]
		// 4 [_][X][X][X][X][X][_]
		// 5 [_][X][X][X][X][_][_]
		// 6 [_][_][_][_][_][_][_]


		mapULEdges[0][0] = null;
		mapULEdges[0][1] = null;
		mapULEdges[0][2] = null;
		mapULEdges[0][3] = null;
		mapULEdges[0][4] = null;
		mapULEdges[0][5] = null;
		mapULEdges[0][6] = null;

		mapULEdges[1][0] = null;
		mapULEdges[1][1] = null;
		mapULEdges[1][2] = null;
		mapULEdges[1][3] = new Edge(this,1,3,ModEdgeDirection.LEFT);
		mapULEdges[1][4] = new Edge(this,1,4,ModEdgeDirection.LEFT);
		mapULEdges[1][5] = new Edge(this,1,5,ModEdgeDirection.LEFT);
		mapULEdges[1][6] = new Edge(this,1,6,ModEdgeDirection.LEFT);

		mapULEdges[2][0] = null;
		mapULEdges[2][1] = null;
		mapULEdges[2][2] = new Edge(this,2,2,ModEdgeDirection.LEFT);
		mapULEdges[2][3] = new Edge(this,2,3,ModEdgeDirection.LEFT);
		mapULEdges[2][4] = new Edge(this,2,4,ModEdgeDirection.LEFT);
		mapULEdges[2][5] = new Edge(this,2,5,ModEdgeDirection.LEFT);
		mapULEdges[2][6] = new Edge(this,2,6,ModEdgeDirection.LEFT);

		mapULEdges[3][0] = null;
		mapULEdges[3][1] = new Edge(this,3,1,ModEdgeDirection.LEFT);
		mapULEdges[3][2] = new Edge(this,3,2,ModEdgeDirection.LEFT);
		mapULEdges[3][3] = new Edge(this,3,3,ModEdgeDirection.LEFT);
		mapULEdges[3][4] = new Edge(this,3,4,ModEdgeDirection.LEFT);
		mapULEdges[3][5] = new Edge(this,3,5,ModEdgeDirection.LEFT);
		mapULEdges[3][6] = new Edge(this,3,6,ModEdgeDirection.LEFT);

		mapULEdges[4][0] = null;
		mapULEdges[4][1] = new Edge(this,4,1,ModEdgeDirection.LEFT);
		mapULEdges[4][2] = new Edge(this,4,2,ModEdgeDirection.LEFT);
		mapULEdges[4][3] = new Edge(this,4,3,ModEdgeDirection.LEFT);
		mapULEdges[4][4] = new Edge(this,4,4,ModEdgeDirection.LEFT);
		mapULEdges[4][5] = new Edge(this,4,5,ModEdgeDirection.LEFT);
		mapULEdges[4][6] = null;

		mapULEdges[5][0] = null;
		mapULEdges[5][1] = new Edge(this,5,1,ModEdgeDirection.LEFT);
		mapULEdges[5][2] = new Edge(this,5,2,ModEdgeDirection.LEFT);
		mapULEdges[5][3] = new Edge(this,5,3,ModEdgeDirection.LEFT);
		mapULEdges[5][4] = new Edge(this,5,4,ModEdgeDirection.LEFT);
		mapULEdges[5][5] = null;
		mapULEdges[5][6] = null;

		mapULEdges[6][0] = null;
		mapULEdges[6][1] = null;
		mapULEdges[6][2] = null;
		mapULEdges[6][3] = null;
		mapULEdges[6][4] = null;
		mapULEdges[6][5] = null;
		mapULEdges[6][6] = null;
	}
	
	private void initUUBorders() {
		//    0  1  2  3  4  5  6
		// 0 [_][_][_][_][_][_][_]
		// 1 [_][_][_][X][X][X][_]
		// 2 [_][_][X][X][X][X][_]
		// 3 [_][X][X][X][X][X][_]
		// 4 [_][X][X][X][X][X][_]
		// 5 [_][X][X][X][X][_][_]
		// 6 [_][X][X][X][_][_][_]

		mapUUEdges[0][0] = null;
		mapUUEdges[0][1] = null;
		mapUUEdges[0][2] = null;
		mapUUEdges[0][3] = null;
		mapUUEdges[0][4] = null;
		mapUUEdges[0][5] = null;
		mapUUEdges[0][6] = null;

		mapUUEdges[1][0] = null;
		mapUUEdges[1][1] = null;
		mapUUEdges[1][2] = null;
		mapUUEdges[1][3] = new Edge(this,1,3,ModEdgeDirection.UP);
		mapUUEdges[1][4] = new Edge(this,1,4,ModEdgeDirection.UP);
		mapUUEdges[1][5] = new Edge(this,1,5,ModEdgeDirection.UP);
		mapUUEdges[1][6] = null;

		mapUUEdges[2][0] = null;
		mapUUEdges[2][1] = null;
		mapUUEdges[2][2] = new Edge(this,2,2,ModEdgeDirection.UP);
		mapUUEdges[2][3] = new Edge(this,2,3,ModEdgeDirection.UP);
		mapUUEdges[2][4] = new Edge(this,2,4,ModEdgeDirection.UP);
		mapUUEdges[2][5] = new Edge(this,2,5,ModEdgeDirection.UP);
		mapUUEdges[2][6] = null;

		mapUUEdges[3][0] = null;
		mapUUEdges[3][1] = new Edge(this,3,1,ModEdgeDirection.UP);
		mapUUEdges[3][2] = new Edge(this,3,2,ModEdgeDirection.UP);
		mapUUEdges[3][3] = new Edge(this,3,3,ModEdgeDirection.UP);
		mapUUEdges[3][4] = new Edge(this,3,4,ModEdgeDirection.UP);
		mapUUEdges[3][5] = new Edge(this,3,5,ModEdgeDirection.UP);
		mapUUEdges[3][6] = null;

		mapUUEdges[4][0] = null;
		mapUUEdges[4][1] = new Edge(this,4,1,ModEdgeDirection.UP);
		mapUUEdges[4][2] = new Edge(this,4,2,ModEdgeDirection.UP);
		mapUUEdges[4][3] = new Edge(this,4,3,ModEdgeDirection.UP);
		mapUUEdges[4][4] = new Edge(this,4,4,ModEdgeDirection.UP);
		mapUUEdges[4][5] = new Edge(this,4,5,ModEdgeDirection.UP);
		mapUUEdges[4][6] = null;

		mapUUEdges[5][0] = null;
		mapUUEdges[5][1] = new Edge(this,5,1,ModEdgeDirection.UP);
		mapUUEdges[5][2] = new Edge(this,5,2,ModEdgeDirection.UP);
		mapUUEdges[5][3] = new Edge(this,5,3,ModEdgeDirection.UP);
		mapUUEdges[5][4] = new Edge(this,5,4,ModEdgeDirection.UP);
		mapUUEdges[5][5] = null;
		mapUUEdges[5][6] = null;

		mapUUEdges[6][0] = null;
		mapUUEdges[6][1] = new Edge(this,6,1,ModEdgeDirection.UP);
		mapUUEdges[6][2] = new Edge(this,6,2,ModEdgeDirection.UP);
		mapUUEdges[6][3] = new Edge(this,6,3,ModEdgeDirection.UP);
		mapUUEdges[6][4] = null;
		mapUUEdges[6][5] = null;
		mapUUEdges[6][6] = null;
	}
	
	private void initURBorders() {
		//    0  1  2  3  4  5  6
		// 0 [_][_][_][_][_][_][_]
		// 1 [_][_][_][X][X][X][_]
		// 2 [_][_][X][X][X][X][_]
		// 3 [_][X][X][X][X][X][_]
		// 4 [X][X][X][X][X][_][_]
		// 5 [X][X][X][X][_][_][_]
		// 6 [X][X][X][_][_][_][_]

		mapUREdges[0][0] = null;
		mapUREdges[0][1] = null;
		mapUREdges[0][2] = null;
		mapUREdges[0][3] = null;
		mapUREdges[0][4] = null;
		mapUREdges[0][5] = null;
		mapUREdges[0][6] = null;

		mapUREdges[1][0] = null;
		mapUREdges[1][1] = null;
		mapUREdges[1][2] = null;
		mapUREdges[1][3] = new Edge(this,1,3,ModEdgeDirection.RIGHT);
		mapUREdges[1][4] = new Edge(this,1,4,ModEdgeDirection.RIGHT);
		mapUREdges[1][5] = new Edge(this,1,5,ModEdgeDirection.RIGHT);
		mapUREdges[1][6] = null;

		mapUREdges[2][0] = null;
		mapUREdges[2][1] = null;
		mapUREdges[2][2] = new Edge(this,2,2,ModEdgeDirection.RIGHT);
		mapUREdges[2][3] = new Edge(this,2,3,ModEdgeDirection.RIGHT);
		mapUREdges[2][4] = new Edge(this,2,4,ModEdgeDirection.RIGHT);
		mapUREdges[2][5] = new Edge(this,2,5,ModEdgeDirection.RIGHT);
		mapUREdges[2][6] = null;

		mapUREdges[3][0] = null;
		mapUREdges[3][1] = new Edge(this,3,1,ModEdgeDirection.RIGHT);
		mapUREdges[3][2] = new Edge(this,3,2,ModEdgeDirection.RIGHT);
		mapUREdges[3][3] = new Edge(this,3,3,ModEdgeDirection.RIGHT);
		mapUREdges[3][4] = new Edge(this,3,4,ModEdgeDirection.RIGHT);
		mapUREdges[3][5] = new Edge(this,3,5,ModEdgeDirection.RIGHT);
		mapUREdges[3][6] = null;

		mapUREdges[4][0] = new Edge(this,4,0,ModEdgeDirection.RIGHT);
		mapUREdges[4][1] = new Edge(this,4,1,ModEdgeDirection.RIGHT);
		mapUREdges[4][2] = new Edge(this,4,2,ModEdgeDirection.RIGHT);
		mapUREdges[4][3] = new Edge(this,4,3,ModEdgeDirection.RIGHT);
		mapUREdges[4][4] = new Edge(this,4,4,ModEdgeDirection.RIGHT);
		mapUREdges[4][5] = null;
		mapUREdges[4][6] = null;

		mapUREdges[5][0] = new Edge(this,5,0,ModEdgeDirection.RIGHT);
		mapUREdges[5][1] = new Edge(this,5,1,ModEdgeDirection.RIGHT);
		mapUREdges[5][2] = new Edge(this,5,2,ModEdgeDirection.RIGHT);
		mapUREdges[5][3] = new Edge(this,5,3,ModEdgeDirection.RIGHT);
		mapUREdges[5][4] = null;
		mapUREdges[5][5] = null;
		mapUREdges[5][6] = null;

		mapUREdges[6][0] = new Edge(this,6,0,ModEdgeDirection.RIGHT);
		mapUREdges[6][1] = new Edge(this,6,1,ModEdgeDirection.RIGHT);
		mapUREdges[6][2] = new Edge(this,6,2,ModEdgeDirection.RIGHT);
		mapUREdges[6][3] = null;
		mapUREdges[6][4] = null;
		mapUREdges[6][5] = null;
		mapUREdges[6][6] = null;
	}
	
	private void initVertices() {
		//    0  1  2  3  4  5  6  7  8  9 10 11
		// 0 [_][_][_][_][_][_][X][_][X][_][X][_]
		// 1 [_][_][_][_][_][X][_][X][_][X][_][X]
		// 2 [_][_][_][_][X][_][X][_][X][_][X][_]
		// 3 [_][_][_][X][_][X][_][X][_][X][_][X]
		// 4 [_][_][X][_][X][_][X][_][X][_][X][_]
		// 5 [_][X][_][X][_][X][_][X][_][X][_][X]
		// 6 [X][_][X][_][X][_][X][_][X][_][X][_]
		// 7 [_][X][_][X][_][X][_][X][_][X][_][_]
		// 8 [X][_][X][_][X][_][X][_][X][_][_][_]
		// 9 [_][X][_][X][_][X][_][X][_][_][_][_]
		// 10[X][_][X][_][X][_][X][_][_][_][_][_]
		// 11[_][X][_][X][_][X][_][_][_][_][_][_]
		
		mapVertices[0][0] = null;
		mapVertices[0][1] = null;
		mapVertices[0][2] = null;
		mapVertices[0][3] = null;
		mapVertices[0][4] = null;
		mapVertices[0][5] = null;
		mapVertices[0][6] = new Vertex(this,0,6);
		mapVertices[0][7] = null;
		mapVertices[0][8] = new Vertex(this,0,8);
		mapVertices[0][9] = null;
		mapVertices[0][10] = new Vertex(this,0,10);
		mapVertices[0][11] = null;

		mapVertices[1][0] = null;
		mapVertices[1][1] = null;
		mapVertices[1][2] = null;
		mapVertices[1][3] = null;
		mapVertices[1][4] = null;
		mapVertices[1][5] = new Vertex(this,1,5);
		mapVertices[1][6] = null;
		mapVertices[1][7] = new Vertex(this,1,7);
		mapVertices[1][8] = null;
		mapVertices[1][9] = new Vertex(this,1,9);
		mapVertices[1][10] = null;
		mapVertices[1][11] = new Vertex(this,1,11);

		mapVertices[2][0] = null;
		mapVertices[2][1] = null;
		mapVertices[2][2] = null;
		mapVertices[2][3] = null;
		mapVertices[2][4] = new Vertex(this,2,4);
		mapVertices[2][5] = null;
		mapVertices[2][6] = new Vertex(this,2,6);
		mapVertices[2][7] = null;
		mapVertices[2][8] = new Vertex(this,2,8);
		mapVertices[2][9] = null;
		mapVertices[2][10] = new Vertex(this,2,10);
		mapVertices[2][11] = null;

		mapVertices[3][0] = null;
		mapVertices[3][1] = null;
		mapVertices[3][2] = null;
		mapVertices[3][3] = new Vertex(this,3,3);
		mapVertices[3][4] = null;
		mapVertices[3][5] = new Vertex(this,3,5);
		mapVertices[3][6] = null;
		mapVertices[3][7] = new Vertex(this,3,7);
		mapVertices[3][8] = null;
		mapVertices[3][9] = new Vertex(this,3,9);
		mapVertices[3][10] = null;
		mapVertices[3][11] = new Vertex(this,3,11);

		mapVertices[4][0] = null;
		mapVertices[4][1] = null;
		mapVertices[4][2] = new Vertex(this,4,2);
		mapVertices[4][3] = null;
		mapVertices[4][4] = new Vertex(this,4,4);
		mapVertices[4][5] = null;
		mapVertices[4][6] = new Vertex(this,4,6);
		mapVertices[4][7] = null;
		mapVertices[4][8] = new Vertex(this,4,8);
		mapVertices[4][9] = null;
		mapVertices[4][10] = new Vertex(this,4,10);
		mapVertices[4][11] = null;

		mapVertices[5][0] = null;
		mapVertices[5][1] = new Vertex(this,5,1);
		mapVertices[5][2] = null;
		mapVertices[5][3] = new Vertex(this,5,3);
		mapVertices[5][4] = null;
		mapVertices[5][5] = new Vertex(this,5,5);
		mapVertices[5][6] = null;
		mapVertices[5][7] = new Vertex(this,5,7);
		mapVertices[5][8] = null;
		mapVertices[5][9] = new Vertex(this,5,9);
		mapVertices[5][10] = null;
		mapVertices[5][11] = new Vertex(this,5,11);

		mapVertices[6][0] = new Vertex(this,6,0);
		mapVertices[6][1] = null;
		mapVertices[6][2] = new Vertex(this,6,2);
		mapVertices[6][3] = null;
		mapVertices[6][4] = new Vertex(this,6,4);
		mapVertices[6][5] = null;
		mapVertices[6][6] = new Vertex(this,6,6);
		mapVertices[6][7] = null;
		mapVertices[6][8] = new Vertex(this,6,8);
		mapVertices[6][9] = null;
		mapVertices[6][10] = new Vertex(this,6,10);
		mapVertices[6][11] = null;

		mapVertices[7][0] = null;
		mapVertices[7][1] = new Vertex(this,7,1);
		mapVertices[7][2] = null;
		mapVertices[7][3] = new Vertex(this,7,3);
		mapVertices[7][4] = null;
		mapVertices[7][5] = new Vertex(this,7,5);
		mapVertices[7][6] = null;
		mapVertices[7][7] = new Vertex(this,7,7);
		mapVertices[7][8] = null;
		mapVertices[7][9] = new Vertex(this,7,9);
		mapVertices[7][10] = null;
		mapVertices[7][11] = null;

		mapVertices[8][0] = new Vertex(this,8,0);
		mapVertices[8][1] = null;
		mapVertices[8][2] = new Vertex(this,8,2);
		mapVertices[8][3] = null;
		mapVertices[8][4] = new Vertex(this,8,4);
		mapVertices[8][5] = null;
		mapVertices[8][6] = new Vertex(this,8,6);
		mapVertices[8][7] = null;
		mapVertices[8][8] = new Vertex(this,8,8);
		mapVertices[8][9] = null;
		mapVertices[8][10] = null;
		mapVertices[8][11] = null;

		mapVertices[9][0] = null;
		mapVertices[9][1] = new Vertex(this,9,1);
		mapVertices[9][2] = null;
		mapVertices[9][3] = new Vertex(this,9,3);
		mapVertices[9][4] = null;
		mapVertices[9][5] = new Vertex(this,9,5);
		mapVertices[9][6] = null;
		mapVertices[9][7] = new Vertex(this,9,7);
		mapVertices[9][8] = null;
		mapVertices[9][9] = null;
		mapVertices[9][10] = null;
		mapVertices[9][11] = null;

		mapVertices[10][0] = new Vertex(this,10,10);
		mapVertices[10][1] = null;
		mapVertices[10][2] = new Vertex(this,10,2);
		mapVertices[10][3] = null;
		mapVertices[10][4] = new Vertex(this,10,4);
		mapVertices[10][5] = null;
		mapVertices[10][6] = new Vertex(this,10,6);
		mapVertices[10][7] = null;
		mapVertices[10][8] = null;
		mapVertices[10][9] = null;
		mapVertices[10][10] = null;
		mapVertices[10][11] = null;

		mapVertices[11][0] = null;
		mapVertices[11][1] = new Vertex(this,11,1);
		mapVertices[11][2] = null;
		mapVertices[11][3] = new Vertex(this,11,3);
		mapVertices[11][4] = null;
		mapVertices[11][5] = new Vertex(this,11,5);
		mapVertices[11][6] = null;
		mapVertices[11][7] = null;
		mapVertices[11][8] = null;
		mapVertices[11][9] = null;
		mapVertices[11][10] = null;
		mapVertices[11][11] = null;
	}
	
}

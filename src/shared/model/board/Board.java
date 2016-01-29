package shared.model.board;

import shared.model.items.Road;
import shared.model.items.Settlement;
import shared.model.player.Player;
import shared.model.player.exceptions.AllPiecesPlayedException;
import shared.model.player.exceptions.CannotBuyException;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;
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
	
	public Board() {
		// Need to initialize which hex location is starting with the robber (Should be the desert)
	}
	/*Dumb comment test
	 * 
	 * 
	 */
	
	/**
	 * Determines Whether the Robber can be moved to the given hex
	 * 
	 * @pre hex must not be null, hex must be a land hex
	 * 
	 * @post Return value is whether the Robber can be moved to the given hex
	 * 
	 */
	public boolean canDoMoveRobberToHex(Hex hex) {
		if(hex.equals(hexWithRobber)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Move the robber to a specified hex
	 * 
	 * @pre canDoMoveRobber(hex) == true
	 * 
	 * @post hexWithRobber = hex, the Robber has been moved to the new hex,
	 * or MoveRobberException thrown
	 */
	public void moveRobberToHex(Hex hex) throws MoveRobberException {
		if(!canDoMoveRobberToHex(hex)) {
			throw new MoveRobberException("Cannot move robber to given hex");
		}
		hexWithRobber.takeRobber();
		hexWithRobber = hex;
		hex.giveRobber();
	}
	
	/**
	 * Determines whether a specified player's road can be placed on a specified edge
	 * This call should be happening after the player has already verified ability to purchase a road
	 * 
	 * @pre player != null, edge != null
	 * 
	 * @post Return value is whether an edge can be placed on the specified edge
	 */
	public boolean canDoPlaceRoadOnEdge(Player player, Edge edge) {
		// player not null, edge not null, and does not have a road
		if(player == null || edge == null || edge.hasRoad() == true) {
			return false;
		}
		// edge has an adjacent edge that has a road owned by the player, that does not
		// have an enemy municipality on the converging vertex
		// TODO
		return true;
	}
	
	/**
	 * Places a road on a specified edge
	 * @throws AllPiecesPlayedException 
	 * @throws InsufficientPlayerResourcesException 
	 * @throws CannotBuyException 
	 * 
	 * @pre canDoPlaceRoadOnEdge != false,
	 * 
	 * @post road is placed on the specified edge, or PlaceRoadOnEdgeException thrown
	 */
	public void placeRoadOnEdge(Player player, Edge edge) throws PlaceRoadOnEdgeException, CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException {
		if(canDoPlaceRoadOnEdge(player, edge) == false) {
			throw new PlaceRoadOnEdgeException("canDoPlaceRoadOnEdge = false");
		}
		// Place the road on the edge
		player.BuyRoad(edge);
	}

	/**
	 * Determine whether a specified player's settlement can be placed on a specified vertex
	 * This call should be happening after the player has already verified ability to purchase a settlement
	 * 
	 * @pre vertex != null, settlement != null
	 * 
	 * @post Return value is whether the specified settlement can be placed on the specified vertex
	 */
	public boolean canDoPlaceSettlementOnVertex(Player player, Vertex vertex) {
		// player not null, vertex not null, and does not have any player's settlement or city
		if(player == null || vertex == null || vertex.hasMunicipal() == true) {
			return false;
		}
		
		// vertex does not have an adjacent vertex with any players settlement or city
		// TODO
		
		// vertex has an adjacent border that has a road owned by the player of the settlement to place
		// TODO
		
		return true;
	}

	/**
	 * Places a settlement on a specified vertex
	 * @throws AllPiecesPlayedException 
	 * @throws InsufficientPlayerResourcesException 
	 * @throws CannotBuyException 
	 * 
	 * @pre canDoPlaceSettlementOnVertex != false,  
	 * 
	 * @post settlement is placed on the specified vertex, or PlaceSettlementOnVertexException thrown
	 */
	public void placeSettlementOnVertex(Player player, Vertex vertex) throws PlaceSettlementOnVertexException, CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException {
		if(canDoPlaceSettlementOnVertex(player, vertex) == false) {
			throw new PlaceSettlementOnVertexException("canDoPlaceSettlementOnVertex = false");
		}
		// Place the settlement on the vertex
		player.BuySettlement(vertex);
	}
	
	/**
	 * Removes a settlement from it's vertex 
	 * 
	 * @pre A vertex must contain a settlement
	 * 
	 * @post append settlement to ArrayList settlement 
	 * @post settlement removed from vertex
	 */
	   void removeSettlement(){}

	/**
	 * Determine whether a specified player's city can be placed on a specified vertex
	 * This call should be happening after the player has already verified ability to purchase a city
	 * 
	 * @pre vertex != null, city != null
	 * 
	 * @post Return value is whether the city can be placed on the specified vertex
	 */
	public boolean canDoPlaceCityOnVertex(Player player, Vertex vertex) {
		// Check that the player and vertex are not null, that the vertex has a settlement, and that the settlement is owned by the given player
		if(player == null || vertex == null || vertex.hasMunicipal() == false || vertex.getMunicipal() instanceof City 
				|| vertex.getMunicipal().getPlayer().getPlayerId() != player.getPlayerId()) {
			return false;
		}
		return true;
	}

	/**
	 * Places a city on a specified vertex
	 * @throws AllPiecesPlayedException 
	 * @throws InsufficientPlayerResourcesException 
	 * @throws CannotBuyException 
	 * 
	 * @pre canDoPlaceCityOnVertex != false,  
	 * 
	 * @post city is placed on the specified vertex, or PlaceCityOnVertexException thrown
	 */
	public void placeCityOnVertex(Player player, Vertex vertex) throws PlaceCityOnVertexException, CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException {
		if(canDoPlaceCityOnVertex(player, vertex) == false) {
			throw new PlaceCityOnVertexException("canDoPlaceCityOnVertex = false");
		}
		// Place the city on the vertex
		player.BuyCity(vertex);
	}
	
	
}

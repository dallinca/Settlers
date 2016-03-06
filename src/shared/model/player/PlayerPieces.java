package shared.model.player;

import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.board.Edge;
import shared.model.board.EdgeSide;
import shared.model.board.Hex;
import shared.model.board.TradePort;
import shared.model.board.Vertex;
import shared.model.items.*;

import java.util.*;

import shared.model.items.Road;
import shared.model.items.Settlement;
import shared.model.player.exceptions.AllPiecesPlayedException;

/**
 * The PlayerPieces class keeps track of a players settlements and cities. 
 * 
 * It has methods for checking if a player has available settlements, cities,
 * or roads for placement
 *
 * Domain:
 * 		ArrayList roads: each player has an array of 15 roads
 * 		ArrayList settlements: each player has an array of 5 settlements
 *		ArrayList cities: each player has an array of 4 cities
 */

public class PlayerPieces {

	private Player player;
	
	private int maxSettlements = 5;
	private int maxCities = 4;
	private int maxRoads = 15;
	
	private ArrayList<Settlement> settlements;
	private ArrayList<City> cities;
	private ArrayList<Road> roads;
	   
	/**
	 * Initializes Municipal
	 * 
	 * @pre A Player object exists
	 * 
	 */
	   public PlayerPieces(Player player){
		   this.player = player;
		   
		   settlements = new ArrayList<Settlement>();
		   cities = new ArrayList<City>();
		   roads =  new ArrayList<Road>();
		 
		   for(int i = 0; i < maxSettlements; i++) {
			   settlements.add(new Settlement(player));
		   }
		   for(int i = 0; i < maxCities; i++) {
			   cities.add(new City(player));
		   }
		   for(int i = 0; i < maxRoads; i++) {
			   roads.add(new Road(player));
		   }
	   }
	   
	   /**
	    * Get the best trade rate the player has for the specified resourceType
	    * 
	    * @pre resourceType != null
	    * @param resourceType
	    * @return the best trade rate the player has for the specified resourceType
	    */
	   public int getTradeRate(ResourceType resourceType) {
		   if(resourceType == ResourceType.BRICK) {
				return getBestPortDeal(PortType.BRICK);
			} else if(resourceType == ResourceType.ORE) {
				return getBestPortDeal(PortType.ORE);
			} else if(resourceType == ResourceType.SHEEP) {
				return getBestPortDeal(PortType.SHEEP);
			} else if(resourceType == ResourceType.WHEAT) {
				return getBestPortDeal(PortType.WHEAT);
			} else if(resourceType == ResourceType.WOOD) {
				return getBestPortDeal(PortType.WOOD);
			}
		   return 0;
	   }
	   
	   /**
	    * Returns the best Player trade deal for the specified PortType
	    * 
	    * @pre portType != null
	    * @return the best Player trade deal for the specified PortType
	    */
	   private int getBestPortDeal(PortType portType) {
		   // Worst possible deal is 4
		   int bestDeal = 4;
		   // Iterate through all of the settlements
		   for(Settlement settlement: settlements) {
			   Vertex vertex = settlement.getVertex();
			   // check if the settlement has been placed on the map
			   if( vertex != null) {
				   // check if the vertex has a tradeport
				   TradePort tradePort = vertex.getTradePort();
				   if(tradePort != null) {
					   // check portType
					   if(tradePort.getPortType() == portType) {
						   return 2;
					   } else if(tradePort.getPortType() == PortType.THREE) {
						   bestDeal = 3;
					   }
				   }
			   }
		   }
		   // Iterate through all of the cities
		   for(City city: cities) {
			   Vertex vertex = city.getVertex();
			   // check if the settlement has been placed on the map
			   if( vertex != null) {
				   // check if the vertex has a tradeport
				   TradePort tradePort = vertex.getTradePort();
				   if(tradePort != null) {
					   // check portType
					   if(tradePort.getPortType() == portType) {
						   return 2;
					   } else if(tradePort.getPortType() == PortType.THREE) {
						   bestDeal = 3;
					   }
				   }
			   }
		   }
		   
		   return bestDeal;
	   }
	   
	 /**
	  * Collects the resources for all the player's municipalities
	  * 
	  * @param Pass in the resourceCardHand where the cards should be added
	  * @throws Exception 
	  * @pre resourceCardHand != null
	  * @pre rollValue is a valid checked number by the Player class
	  * 
	  * @post The Player will have collected all the resources for his pieces
	  */
	   public void collectResources(ResourceCardHand resourceCardHand, int rollValue, Bank bank) throws Exception {
		   // Iterate through all of the settlements
		   for(Settlement settlement: settlements) {
			   Vertex vertex = settlement.getVertex();
			   // check if the settlement has been placed on the map
			   if( vertex != null) {
				   // retrieve an array of all the bordering hexes and iterate through them
				   Hex[] hexes = vertex.getAdjacentHexes();
				   for(Hex hex: hexes) {
					   // If the hex is a valid hex, and it does not have the robber, we can collect resources from it.
					   if(hex != null && hex.checkIfHasRobber() == false) {
							   ResourceType resourceType = hex.getHexResourceType();
							   if(rollValue == hex.getRollValue() && resourceType != null){
								   moveResourcesFromBankToPlayerHand(resourceType, 1, bank, resourceCardHand);
							   }
					   }
				   }
			   }
		   }
		   // Iterate through all of the cities
		   for(City city: cities) {
			   Vertex vertex = city.getVertex();
			   // check if the settlement has been placed on the map
			   if( vertex != null) {
				   // retrieve an array of all the bordering hexes and iterate through them
				   Hex[] hexes = vertex.getAdjacentHexes();
				   for(Hex hex: hexes) {
					   // If the hex is a valid hex, and it does not have the robber, we can collect resources from it.
					   if(hex != null && hex.checkIfHasRobber() == false) {
						   ResourceType resourceType = hex.getHexResourceType();
						   if(rollValue == hex.getRollValue() && resourceType != null){
							   moveResourcesFromBankToPlayerHand(resourceType, 2, bank, resourceCardHand);
						   }
					   }
				   }
			   }
		   }
	   }
	   
	   /**
	    * @param resourceType
	    * @param numberOfCards
	    * @param bank
	    * @param resourceCardHand
	    */
	   private void moveResourcesFromBankToPlayerHand(ResourceType resourceType, int numberOfCards, Bank bank, ResourceCardHand resourceCardHand) {
		   // Ask the bank 'numberOfCards' times if it can take a card of type 'resourceType'
		   for(int i = 0; i < numberOfCards; i++) {
			   if(bank.canDoPlayerTakeResource(resourceType) == true) {
				   try {
					   resourceCardHand.addCard(bank.playerTakeResource(resourceType));
					} catch (Exception e) {
						System.out.println("Player trying to take a resource from the bank that it doesn't have");
						e.printStackTrace();
					}
			   	}
		   }
		   
	   }
	   
	   
	   /**
	    * Determines whether the player has at at least one valid place on the map 
	    * where that player can place a road.
	    * 
	    * @return
	    */
	   public boolean canPlaceARoadOnTheMap() {
		   // Iterate through every road
		   for(Road road: roads) {
			   // If the road is placed on the map
			   Edge firstEdge = road.getEdge();
			   if(firstEdge != null) {
				   // Look at each side of the road/edge
				   EdgeSide[] sides = firstEdge.getSides();
				   for(EdgeSide side: sides) {
					   // Check to see if the side doesn't have a municipal owned by another player, cutting off this player's road building to that side
					   Vertex vertex = side.getVertex();
					   if(vertex.hasMunicipal() == false || vertex.getMunicipal().getPlayer().getPlayerId() == player.getPlayerId()) {
						   // Check to see if there is an edge that doesn't have any players road on it.
						   Edge[] edges = side.getEdges();
						   for(Edge edge: edges) {
							   // if there is an edge that doesn't have a road on it, then the player can place a road in least one place on the map.
							   if(edge != null && edge.hasRoad() == false) {
								   return true;
							   }
						   }
					   }
				   }
			   }
		   }
		   // There is not a single valid place for the player to place a road on the map, so we probably shouldn't let the person purchase a road.
		   return false;
	   }
	   
	 /**
	  * checks if a player has an available road to purchase
	  *   
	  * @pre None
	  * 
	  * @post Return Value contains whether there is an available road for the player to purchase
	  */
	   public boolean hasAvailableRoad(){
		   for(Road road: roads) { // Iterate through all roads in the arraylist
			   if(road.getEdge() == null) { // Check to see if there is a road that has not been assigned to an edge on the map
				   return true; // Null means it is still available to be placed
			   }
		   }
		   // All of the roads have been assigned an edge
		   return false;
	   }
	   
	   //used for initial setup of game
	   public int getNumberOfRoads(){
		   int count = 0; 
		   for(Road road: roads) { // Iterate through all roads in the arraylist
			   if(road.getEdge() == null) { // Check to see if there is a road that has not been assigned to an edge on the map
				    count++;// Null means it is still available to be placed
			   }
		   }
		   return count; 
	   }
	   
	 /**
	  * TODO javadoc
	  * 
	  * @param edge
	  */
	   public void placeRoad(Edge edge) throws AllPiecesPlayedException {
		   if(hasAvailableRoad() == false) {
			   throw new AllPiecesPlayedException("All the Roads have already been placed");
		   }
		   for(Road road: roads) { // Go through all players roads
			   if(road.getEdge() == null) { // Find the first road that is not on the map
				   road.setEdge(edge); // set the road to the edge
				   edge.buildRoad(road); // set the edge to the road
				   break; // leave for loop
			   }
		   }
	   }

	   /**
	    * Determines whether the player has a legal place to put a settlement on the map
	    * 
	    * @pre None
	    * 
	    * @return whether there is a valid place for the player to put a settlement on the map
	    */
	   public boolean canPlaceASettlementOnTheMap() {
		   // Iterate through every road
		   for(Road road: roads) {
			   // If the road is placed on the map
			   Edge firstEdge = road.getEdge();
			   if(firstEdge != null) {
				   // Look at each side of the road/edge
				   EdgeSide[] sides = firstEdge.getSides();
				   for(EdgeSide side: sides) {
					   // Check to see if this side's vertex doesn't have a municipal
					   Vertex vertex = side.getVertex();
					   if(vertex.hasMunicipal() == false) {
						   // Check to see if the surrounding vertices have municipalities
						   boolean adjacentVertexHasMunicipal = false;
						   Vertex[] adjacentVertices = vertex.getAdjacentVertices();
						   for(Vertex adjacentVertex: adjacentVertices) {
							   if(adjacentVertex != null && adjacentVertex.hasMunicipal() == true) {
								   adjacentVertexHasMunicipal = true; 
							   }
						   }
						   // If there wasn't a single adjacent vertex with a municipal, then this is a vertex where the player can build a settlement
						   if(adjacentVertexHasMunicipal == false) {
							   return true;
						   }
					   }
				   }
			   }
		   }
		   // There is not a single valid place for the player to place a settlement on the map, so we probably shouldn't let the person purchase a settlement.
		   return false;
	   }
	   
	 /**
	  * checks if a player has an available settlement to purchase
	  *   
	  * @pre None
	  * 
	  * @post Return Value contains whether there is an available settlement for the player to purchase
	  */
	   public boolean hasAvailableSettlement(){
		   for(Settlement settlement: settlements) { // Iterate through all settlements in the arraylist
			   if(settlement.getVertex() == null) { // Check to see if there is a settlement that has not been assigned to a vertex on the map
				   return true; // Null means it is still available to be placed
			   }
		   }
		   // All of the settlements have been assigned vertices
		   return false;
	   }
	   
	 /**
	  * 
	  * @param edge
	  */
	   public void placeSettlement(Vertex vertex) throws AllPiecesPlayedException {
		   if(hasAvailableSettlement() == false) {
			   throw new AllPiecesPlayedException("All the settlements have already been placed");
		   }
		   for(Settlement settlement: settlements) { // Go through all players settlements
			   if(settlement.getVertex() == null) { // Find the first settlement that is not on the map
				   settlement.setVertex(vertex); // set the settlement to the vertex
				   vertex.buildMunicipal(settlement); // set the vertex to the settlement
				   break; // leave for loop
			   }
		   }
	   }

	   /**
	    * Determines whether the player has a legal place to put a settlement on the map
	    * 
	    * @pre None
	    * 
	    * @return whether there is a valid place for the player to put a settlement on the map
	    */
	   public boolean canPlaceACityOnTheMap() {
		   // Iterate through all the settlements
		   for(Settlement settlement: settlements) {
			   // If the settlement has been placed on the map
			   if(settlement.getVertex() != null) {
				   // To have a valid place to put a city on the map, all the player needs is to have a city on the map
				   return true;
			   }
		   }
		   // There is not a single valid place for the player to place a city on the map, so we probably shouldn't let the person purchase a city.
		   return false;
	   }
	   
	 /**
	  * checks if a player has an available city to purchase
	  *   
	  * @pre None
	  * 
	  * @post Return Value contains whether there is an available city for the player to purchase
	  */
	   public boolean hasAvailableCity(){
		   for(City city: cities) { // Iterate through all cities in the arraylist
			   if(city.getVertex() == null) { // Check to see if there is a city that has not been assigned to a vertex on the map
				   return true; // Null means it is still available to be placed
			   }
		   }
		   // All of the cities have been assigned vertices
		   return false;
	   }
	   
	 /**
	  * Places A City at the defined vertex, and removes the settlement that was there
	  * 
	  * @pre Should be a verified vertex that has a settlement owned by the player
	  * @pre hasAvailableCity == true
	  * 
	  * @post A City is placed on the map at the specified vertex and the settlement that was there
	  * previous is removed from the map
	  */
	   public void placeCity(Vertex vertex) throws AllPiecesPlayedException {
		   if(hasAvailableCity() == false) {
			   throw new AllPiecesPlayedException("All the cities have already been placed");
		   }
		   for(City city: cities) { // Go through all players settlements
			   if(city.getVertex() == null) { // Find the first city that is not on the map
				   // Remove the settlement from the map
				   vertex.getMunicipal().setVertex(null);
				   // Move the city onto the map
				   city.setVertex(vertex);
				   vertex.buildMunicipal(city); // set the vertex to the city
				   break;
			   }
		   }
	   }
	   
	   /**
	    * Placing a city initialization from the TA server
	    * 
	    * 
	    * @param vertex
	    * @throws AllPiecesPlayedException
	    */
	   public void placeInitialCity(Vertex vertex) throws AllPiecesPlayedException {
		   if(hasAvailableCity() == false) {
			   throw new AllPiecesPlayedException("All the cities have already been placed");
		   }
		   for(City city: cities) { // Go through all players settlements
			   if(city.getVertex() == null) { // Find the first city that is not on the map
				   // Move the city onto the map
				   city.setVertex(vertex);
				   vertex.buildMunicipal(city); // set the vertex to the city
				   break;
			   }
		   }
	   }
	   
	   /**
	    * Returns the number of unplayed roads, for the gui count
	    * 
	    * @return the number of unplayed roads, for the gui count
	    */
	   public int getNumberUnplayedRoads() {
		   int number = 0;
		   for(Road road: roads) {
			   if(road.getEdge() == null) {
				   number++;
			   }
		   }
		   return number;
	   }

	   /**
	    * Returns the number of unplayed cities, for the gui count
	    * 
	    * @return the number of unplayed cities, for the gui count
	    */
	   public int getNumberUnplayedCities() {
		   int number = 0;
		   for(City city: cities) {
			   if(city.getVertex() == null) {
				   number++;
			   }
		   }
		   return number;
	   }	

	   /**
	    * Returns the number of unplayed settlements, for the gui count
	    * 
	    * @return the number of unplayed settlements, for the gui count
	    */
	   public int getNumberUnplayedSettlements() {
		   int number = 0;
		   for(Settlement settlement: settlements) {
			   if(settlement.getVertex() == null) {
				   number++;
			   }
		   }
		   return number;
	   }
	   

	public ArrayList<Settlement> getSettlements() {
		return settlements;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public ArrayList<Road> getRoads() {
		return roads;
	}

	     

	   
}

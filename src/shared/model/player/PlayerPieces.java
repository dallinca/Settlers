package shared.model.player;

import shared.model.board.Edge;
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
	  * Collects the resources for all the player's municipalities
	  * 
	  * @param Pass in the resourceCardHand where the cards should be added
	  * @pre none
	  * 
	  * @post The Player will have collected all the resources for his pieces
	  */
	   public void collectResources(ResourceCardHand resourceCardHand) {
		   // TODO -- iterate through each settlement and each city drilling information from the vertex then the adjacent hexes
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
	   
	 /**
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
	  * 
	  * @param edge
	  */
	   public void placeCity(Vertex vertex) throws AllPiecesPlayedException {
		   if(hasAvailableCity() == false) {
			   throw new AllPiecesPlayedException("All the cities have already been placed");
		   }
		   for(City city: cities) { // Go through all players settlements
			   if(city.getVertex() == null) { // Find the first city that is not on the map
				   city.setVertex(vertex); // set the city to the vertex
				   vertex.buildMunicipal(city); // set the vertex to the city
				   break; // leave for loop
			   }
		   }
	   }
	     

	   
}

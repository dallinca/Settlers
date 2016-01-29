package shared.model.player;

import shared.model.board.Edge;
import shared.model.board.Vertex;
import shared.model.player.exceptions.AllPiecesPlayedException;
import shared.model.player.exceptions.CannotBuyException;
import shared.model.player.exceptions.CollectResourcesException;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;

/**
 * The Player class is used to create a player object
 * 
 * It has methods for checking if a player can place roads or settlements
 *
 * Domain:
 * 		totalVictoryPoints: an integer, initialized to 2
 * 
 */
public class Player {
	
	int playerId;
	int totalVictoryPoints = 0;
	private ResourceCardHand resourceCardHand;
	private PlayerPieces playerPieces;
	private DevelopmentCardHand developmentCardHand;
	
	/**
	 * Initializes Player
	 * 
	 * @pre None
	 * 
	 * @post new color()
	 * @post new totalVictoryPoints() = 2
	 * @post new buildingCostCard()
	 * @post new ResourceCards()
	 * @post new DevelopmentCards()
	 * @post new Municipal()
	 */
	public Player(int playerId){
		this.playerId = playerId;
		resourceCardHand = new ResourceCardHand();
		playerPieces = new PlayerPieces(this);
		developmentCardHand = new DevelopmentCardHand();
	}
	
	/**
	 * adds victory points and returns total victory points
	 * 
	 * @pre None
	 * @post Returns an integer of players total victory points
	 */
	void totalVictoryPoints(int points){}
	
	/**
	 * The color specific building cost card for the player
	 * 
	 * @pre None
	 * @post Returns building cost card
	 */
	void getbuildingCostCard(){}
	

	 /**
	  * checks if a player can buy a road
	  *   
	  * @return boolean
	  */
	   public boolean canDoBuyRoad(){
		   if(resourceCardHand.canDoPayForRoad() == false || playerPieces.hasAvailableRoad() == false) {
			   return false;
		   }
		   // TODO -- Still need to check whether there is a place on the map where the player can place a road
		   return true;
	   }
	   
	 /**
	  * Called by the Board Class to Buy and Place the road on the given Edge provided by the Board
	  * 
	  * @throws InsufficientPlayerResourcesException 
	  * @throws AllPiecesPlayedException 
	  * 
	  * @pre CanDoBuyRoad() == true, Edge verified as clear on the map
	  * @pre Edge is a valid edge for the player to buy a road for as determined by the Board Class
	  * 
	  * @post Will have payed for the Road and Placed it on the Map
	  */
	   public void BuyRoad(Edge edge) throws CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException {
		   if(canDoBuyRoad() == false) {
			   throw new CannotBuyException("Cannot Buy Road, possibly no edge to place a road");
		   }
		   resourceCardHand.payForRoad();
		   playerPieces.placeRoad(edge);
	   }
	   
	 /**
	  * checks if a player can buy a settlement
	  *   
	  * @return boolean
	  */
	   public boolean canDoBuySettlement(){
		   if(resourceCardHand.canDoPayForSettlement() == false || playerPieces.hasAvailableSettlement() == false) {
			   return false;
		   }
		   // TODO -- Still need to check whether there is a place on the map where the player can place a settlement
		   return true;
	   }

	 /**
	  * Called by the Board Class to Buy and Place the settlement on the given Vertex provided by the Board
	  * 
	  * @throws InsufficientPlayerResourcesException 
	  * @throws AllPiecesPlayedException 
	  * 
	  * @pre CanDoBuySettlement() == true, Vertex verified as clear on the map
	  * @pre Vertex is a valid vertex for the player to buy a settlement for as determined by the Board Class
	  * 
	  * @post Will have payed for the Settlement and Placed it on the Map
	  */
	   public void BuySettlement(Vertex vertex) throws CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException{
		   if(canDoBuySettlement() == false) {
			   throw new CannotBuyException("Cannot Buy Settlement, possibly no vertex to place a settlement");
		   }
		   resourceCardHand.payForSettlement();
		   playerPieces.placeSettlement(vertex);
		   // increment victory points
		   totalVictoryPoints++;
	   }
	   
	 /**
	  * checks if a player can buy a city
	  *   
	  * @return boolean
	  */
	   public boolean canDoBuyCity(){
		   if(resourceCardHand.canDoPayForCity() == false || playerPieces.hasAvailableCity() == false) {
			   return false;
		   }
		   // TODO -- Still need to check whether there is a place on the map where the player can place a city
		   return true;
	   }

	 /**
	  * Called by the Board Class to Buy and Place the city on the given Vertex provided by the Board
	  * 
	  * @throws InsufficientPlayerResourcesException 
	  * @throws AllPiecesPlayedException 
	  * 
	  * @pre CanDoBuyCity() == true, Vertex verified as clear on the map
	  * @pre Vertex is a valid vertex for the player to buy a city for as determined by the Board Class
	  * 
	  * @post Will have payed for the City and Placed it on the Map
	  */
	   public void BuyCity(Vertex vertex) throws CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException {
		   if(canDoBuyCity() == false) {
			   throw new CannotBuyException("Cannot Buy City, possibly no vertex to place a city");
		   }
		   resourceCardHand.payForCity();
		   playerPieces.placeCity(vertex);
		   // increment victory points
		   totalVictoryPoints++;
	   }
	   
		/**
		 * Checks whether the player can collect resources
		 * 
		 * @pre rollValue must be between 1-6 or 8-11
		 * 
		 * @post returns whether the Player can collect resources(may collect nothing)
		 */
		public boolean canDoCollectResources(int rollValue) {
			if(rollValue < 1 || rollValue == 7 || rollValue > 11) {
				return false;
			}
			return true;
		}
		
		/**
		 * Has the Player collect the resources based off the given roll value
		 * 
		 * @pre canDoCollectResources() == true
		 * 
		 * @post The Player will have collected the resources based off the given roll value
		 */
		public void collectResources(int rollValue) throws CollectResourcesException {
			if(canDoCollectResources(rollValue) == false) {
				throw new CollectResourcesException("Player cannot currently collect resources, possibly invalid roll value");
			}
			playerPieces.collectResources(resourceCardHand);
		}

		public int getPlayerId() {
			return playerId;
		}

		public void setPlayerId(int playerId) {
			this.playerId = playerId;
		}
	
}

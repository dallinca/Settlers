package shared.model.player;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Bank;
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
	public Player(int playerId, Bank bank){
		this.playerId = playerId;
		resourceCardHand = new ResourceCardHand(bank);
		playerPieces = new PlayerPieces(this);
		developmentCardHand = new DevelopmentCardHand();
	}
	
	/**
	 * adds victory points and returns total victory points
	 * 
	 * @pre None
	 * @post Returns an integer of players total victory points
	 */
	public int getVictoryPoints() {
		return this.totalVictoryPoints;
	}

	/**
	 * increments the Players total victory points by 1
	 * 
	 * @pre None
	 * 
	 * @post the player will have 1 more point than before
	 */
	public void incrementVictoryPoints() {
		this.totalVictoryPoints++;
	}

	/**
	 * decrement the Players total victory points by 1
	 * 
	 * @pre None
	 * 
	 * @post the player will have 1 less point than before
	 */
	public void decrementVictoryPoints() {
		this.totalVictoryPoints++;
	}
	
	/**
	 * The color specific building cost card for the player
	 * 
	 * @pre None
	 * @post Returns building cost card
	 */
	public void getbuildingCostCard(){}
	
	
	/**
	 * Will Check is a Development Card of the specified type may be played
	 * 
	 * @param turnNumber
	 * @param devCardType
	 * @return Whether a Development Card of the specified type may be played
	 */

	public boolean canDoPlayDevelopmentCard(int turnNumber, DevCardType devCardType) {
		return developmentCardHand.canDoPlayDevelopmentCard(turnNumber, devCardType);
	}
	
	/**
	 * Will mark the a Development card of the specified type as played
	 * 
	 * @param turnNumber
	 * @param devCardType
	 * @throws Exception 
	 * 
	 * @pre canDoPlayDevelopmentCard != false
	 * 
	 * @post Player will have played the Development card
	 */
	public void playDevelopmentCard(int turnNumber, DevCardType devCardType) throws Exception {
		if(canDoPlayDevelopmentCard(turnNumber, devCardType) == false) {
			throw new Exception("Cannot play this card");
		}
		developmentCardHand.playDevelopmentCard();
	}
	

	/**
	 * Retrieves the number of unplayed development cards of the specified type
	 * 
	 * @param turnNumber
	 * @param devCardType
	 * @return the number of unplayed development cards of the specified type
	 */
	public int numberUnplayedDevCards(DevCardType devCardType) {
		return developmentCardHand.numberUnplayedDevCards(devCardType);
	}
	
	
	/**
	 * TODO - Javadoc and implement
	 * 
	 * checks if the player can buy a development card, Should be called in tandom with the Bank to
	 * see if there are any Developments Cards left to be bought
	 * 
	 * @pre bank != null 
	 * @pre bank has at least 1 development card to be bought
	 * 
	 * @post Return Value contains whether the Player has resources to purchase A Development Card.
	 */
	public boolean canDoBuyDevelopmentCard(Bank bank) {
		if(bank == null || resourceCardHand.canDoPayForDevelopmentCard() == false) {
			return false;
		}
		//if(bank.numbDevelopmentCards() > 0) {
		//	return false;
		//}
		return true;
	}
	
	/**
	 * Buys the Development Card, retrieves that card from the bank and places it in the players
	 * developmentCardHand
	 * @throws CannotBuyException 
	 * @throws InsufficientPlayerResourcesException 
	 * 
	 * @pre canDoBuyDevelopmentCard == true
	 * 
	 * @post Will have payed for the Development Card, retrieved it from the Bank and placed it in players Development Card Hand
	 * 
	 */
	public void buyDevelopmentCard(Bank bank) throws CannotBuyException, InsufficientPlayerResourcesException {
		if(canDoBuyDevelopmentCard(bank) == false) {
			throw new CannotBuyException("Cannot Buy Development Card, possibly not enough resources");
		}
		resourceCardHand.payForDevelopmentCard();
		developmentCardHand.takeDevelopmentCardFromBank(bank);
	}
	
	
	 /**
	  * checks if the player can buy a road
	  *   
	  * @pre None
	  *   
	  * @post Return value contains whether the player should be allowed to buy a road
	  */
	   public boolean canDoBuyRoad(){
		   // If the player doesn't have resources for a road, or has already played all his roads, he can't buy another
		   if(resourceCardHand.canDoPayForRoad() == false || playerPieces.hasAvailableRoad() == false) {
			   return false;
		   }
		   // If the player has no legal place to put a road on the map, he shouldn't be allowed to buy one.
		   if(playerPieces.canPlaceARoadOnTheMap() == false) {
			   return false;
		   }
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
	   public void buyRoad(Edge edge) throws CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException {
		   if(canDoBuyRoad() == false) {
			   throw new CannotBuyException("Cannot Buy Road, possibly no edge to place a road");
		   }
		   resourceCardHand.payForRoad();
		   playerPieces.placeRoad(edge);
	   }
	   
	 /**
	  * checks if the player can buy a settlement
	  *   
	  * @return boolean
	  */
	   public boolean canDoBuySettlement(){
		   if(resourceCardHand.canDoPayForSettlement() == false || playerPieces.hasAvailableSettlement() == false) {
			   return false;
		   }
		   // If the player does not have a valid place to put a settlement on the map, we won't let the player buy one
		   if(playerPieces.canPlaceASettlementOnTheMap() == false) {
			   return false;
		   }
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
	   public void buySettlement(Vertex vertex) throws CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException{
		   if(canDoBuySettlement() == false) {
			   throw new CannotBuyException("Cannot Buy Settlement, possibly no vertex to place a settlement");
		   }
		   resourceCardHand.payForSettlement();
		   playerPieces.placeSettlement(vertex);
		   // increment victory points
		   totalVictoryPoints++;
	   }
	   
	 /**
	  * checks if the player can buy a city
	  *   
	  * @return boolean
	  */
	   public boolean canDoBuyCity(){
		   if(resourceCardHand.canDoPayForCity() == false || playerPieces.hasAvailableCity() == false) {
			   return false;
		   }
		   // If the player does not have a valid place to put a city on the map, we won't let the player buy a city
		   if(playerPieces.canPlaceACityOnTheMap() == false) {
			   return false;
		   }
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
	   public void buyCity(Vertex vertex) throws CannotBuyException, InsufficientPlayerResourcesException, AllPiecesPlayedException {
		   if(canDoBuyCity() == false) {
			   throw new CannotBuyException("Cannot Buy City, possibly no vertex to place a city");
		   }
		   resourceCardHand.payForCity();
		   playerPieces.placeCity(vertex);
		   // increment victory points
		   totalVictoryPoints++;
	   }
	   
		/**
		 * Checks whether the player can collect resources (if the roll value is valid,
		 * and if the bank object is null
		 * 
		 * @pre None
		 * 
		 * @post returns whether the Player can collect resources(may collect nothing)
		 */
		public boolean canDoCollectResources(int rollValue, Bank bank) {
			if(rollValue < 1 || rollValue == 7 || rollValue > 11) {
				return false;
			}
			if(bank == null) {
				return false;
			}
			return true;
		}
		
		/**
		 * Has the Player collect the resources based off the given roll value
		 * @throws Exception 
		 * 
		 * @pre canDoCollectResources() == true
		 * 
		 * @post The Player will have collected the resources based off the given roll value
		 */
		public void collectResources(int rollValue, Bank bank) throws Exception {
			if(canDoCollectResources(rollValue, bank) == false) {
				throw new CollectResourcesException("Player cannot currently collect resources, possibly invalid roll value, or null bank object");
			}
			playerPieces.collectResources(resourceCardHand, rollValue, bank);
		}
		
		/**
		 * TODO
		 * 
		 * @param bank
		 * 
		 * @post the player will have discarded the specified number of resources of the specified resource type
		 */
		public void discardResourcesOfType(ResourceType resourceType, int numberToDiscard) {
			discardResourcesOfType(resourceType, numberToDiscard);
		}

		/**
		 * Will return the number of the specified resource that the player currently has
		 * 
		 * @pre None
		 * 
		 * @param resourceType
		 * @return the number of the specified resource that the player currently has
		 */
		public int getNumberResourcesOfType(ResourceType resourceType) {
			return resourceCardHand.getNumberResourcesOfType(resourceType);
		}

		public int getPlayerId() {
			return playerId;
		}

		public void setPlayerId(int playerId) {
			this.playerId = playerId;
		}
		
		public PlayerPieces getPlayerPieces(){
			return playerPieces;
		}
	
}

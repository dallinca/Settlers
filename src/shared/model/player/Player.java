package shared.model.player;

import java.util.ArrayList;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.board.Edge;
import shared.model.board.Vertex;
import shared.model.items.DevelopmentCard;
import shared.model.items.ResourceCard;
import shared.model.player.exceptions.AllPiecesPlayedException;
import shared.model.player.exceptions.CannotBuyException;
import shared.model.player.exceptions.CollectResourcesException;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;
import shared.model.player.exceptions.NullCardException;

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
	 * Gets the players current trade rate for the given resource type
	 * 
	 * @pre ResourceType != null
	 * 
	 * @param resourceType
	 * @return the players current trade rate for the given resource type
	 */
	public int getTradeRate(ResourceType resourceType) {
		// TODO
		return 0;
	}
	
	/**
	 * TODO
	 * 
	 * @param resourceType
	 * @return
	 */
	public ResourceCard[] prepareBankTrade(ResourceType resourceType) {
		
		return null;
	}
	
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public boolean canTradeResourcesToBank() {
		
		return false;
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
		developmentCardHand.playDevelopmentCard(turnNumber, devCardType);
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
		if(bank.hasAvailableDevelopmentCards() == false) {
			return false;
		}
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
		DevelopmentCard cardBought = resourceCardHand.payForDevelopmentCard();
		try {
			developmentCardHand.addCard(cardBought);
		} catch (NullCardException e) {
			System.out.println("I think I just bought a null card");
			e.printStackTrace();
		}
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
		   if(playerPieces.getNumberOfRoads() > 13){
			   return true;
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
			if(rollValue < 1 || rollValue == 7 || rollValue > 12) {
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
		 * Checks whether the specified number of the specified ResourceType can be discarded
		 * 
		 * @pre None
		 * @param resourceType
		 * @param numberToDiscard
		 * @return whether the specified number of the specified ResourceType can be discarded
		 */
		public boolean canDoDiscardResourceOfType(ResourceType resourceType, int numberToDiscard) {
			if(resourceType == null || resourceCardHand.getNumberResourcesOfType(resourceType) < numberToDiscard) {
				return false;
			}
			return true;
		}
		
		/**
		 * has the player discard the specified number of resources of the specified resource type
		 * 
		 * @pre canDoDiscardResourceOfType != false
		 * @param resourceType
		 * @param numberToDiscard
		 * @throws Exception 
		 * 
		 * @post the player will have discarded the specified number of resources of the specified resource type
		 */
		public void discardResourcesOfType(ResourceType resourceType, int numberToDiscard) throws Exception {
			if(canDoDiscardResourceOfType(resourceType, numberToDiscard) == false) {
				throw new Exception("Cannot discard the specified amount of specified resource type");
			}
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
		
		/**
		 * Retrieves the number of soldiers that have been played by the player
		 * 
		 * @pre None
		 * 
		 * @return the number of soldiers that have been played by the player
		 */
		public int getNumberOfSoldiersPlayed() {
			return developmentCardHand.getNumberOfSoldiersPlayed();
		}
		
		/**
		 * TODO javadoc and stuff.... :)
		 * 
		 * @param resourceType
		 * @return
		 */
		public ArrayList<ResourceCard> conformToMonopoly(ResourceType resourceType) {
			return resourceCardHand.conformToMonopoly(resourceType);
		}

	   /**
	    * Retrieves the number of cards in the player's resource card hand
	    * 
	    * @pre None
	    * 
	    * @post The Number of cards in the Players Resource Hand
	    */
	   public int getResourceCardHandSize() {
		   return resourceCardHand.getResourceCardHandSize();
	   }
	   
	   /**
	    * Retrieves whether the player can be stolen from (For robber Movement)
	    * 
	    * @pre None
	    * @param player
	    * @return whether the player can be stolen from
	    */
	   public boolean canDoStealPlayerResource(Player victim) {
		   if(victim == null || victim.getResourceCardHandSize() <= 0) {
			   return false;
		   }
		   return true;
	   }
	   
	   /**
	    * Steals a random ResourceCard from the victom (For robber Movement)
	    * 
	    * @pre canDoStealPlayerResource != false
	    * @param victim
	    * @throws Exception 
	    * @post the victim will have given up a random resource to the calling player
	    */
	   public void stealPlayerResource(Player victim) throws Exception {
		   if(canDoStealPlayerResource(victim) == false) {
			   throw new Exception("canDoStealPlayerResource() == false");
		   }
		   resourceCardHand.addCard(victim.giveUpResourceCard());
	   }
	   
	   /**
	    * Has the Player give up a random Resource Card (For robber Movement)
	    * 
	    * @pre this player should have at least 1 Resource Card
	    * @throws Exception 
	    * @return The Random ResourceCard chosen to give up
	    */
	   public ResourceCard giveUpResourceCard() throws Exception {
		   return resourceCardHand.getRandomResourceCard();
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
		
		public ResourceCardHand getResourceCardHand(){
			return resourceCardHand;
		}
	
}

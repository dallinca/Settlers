package shared.model.player;
import java.util.*;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.items.ResourceCard;
import shared.model.player.exceptions.InsufficientPlayerResourcesException;
import shared.model.player.exceptions.NullCardException;

/**
 * The ResourceCards class is used to store and get individual players cards
 * 
 * Operations are for adding and deleting cards from a player, and for checking
 * if the player has the needed resources to buy settlements, cities, and roads.
 *
 * Domain:
 *		Arraylist: player starts with 0 to 3 resource cards. 
 *
 * @invariant 0 <= Arraylist.size() <= 3
 */
public class ResourceCardHand {

	  private ArrayList<ResourceCard> brickCards;
	  private ArrayList<ResourceCard> wheatCards;
	  private ArrayList<ResourceCard> oreCards;
	  private ArrayList<ResourceCard> sheepCards;
	  private ArrayList<ResourceCard> woodCards;
	 
	/**
	 * Initializes ResourceCards
	 * 
	 * @pre A Player object exists
	 * @post new ArrayList resourceCards = 0 - 3
	 */
	  ResourceCardHand(){}
	 
	/**
	 * adds resource cards to player
	 * 
	 * @param cards that need to be added
	 * 
	 * @pre Card != null
	 * @post cards added to ArrayList data structure
	 */
	  public void addCard(ResourceCard card) throws NullCardException {
		  if(card == null) {
			  throw new NullCardException("The card to be added to the players Development Cards is Null");
		  } else if(card.getResourceType() == null) {
			  throw new NullCardException("The card to be added to the players Development Cards is not Null, but the Card type is null");
		  }
		  if(card.getResourceType() == ResourceType.BRICK) {
			  brickCards.add(card);
		  } else if(card.getResourceType() == ResourceType.ORE) {
			  oreCards.add(card);
		  } else if(card.getResourceType() == ResourceType.SHEEP) {
			  sheepCards.add(card);
		  } else if(card.getResourceType() == ResourceType.WHEAT) {
			  wheatCards.add(card);
		  } else if(card.getResourceType() == ResourceType.LUMBER) {
			  woodCards.add(card);
		  }
	  }
	 
	/**
	 * removes resource cards from player
	 * 
	 * @pre None
	 * @post cards deleted from ArrayList data structure
	 */
	  public void playResourceCard(){}
	
	/**
	  * checks if a player has resources to buy a road
	  *   
	  * @pre None
	  *   
	  * @post Return value says whether the player can currently pay for a road
	  */
	   public boolean canDoPayForRoad(){
		   if(woodCards.size() < 1 || brickCards.size() < 1) {
			   return false;
		   }
		   return true;
	   }
	   
	   /**
	    * Resources are spent from the players hand to pay for a Road
	    * 
	    * @pre canDoPayForRoad() == true
	    * 
	    * @post 1 brick card and 1 lumber card are removed from the players Hand
	    */
	   public void payForRoad() throws InsufficientPlayerResourcesException {
		   if(canDoPayForRoad() == false) {
			   throw new InsufficientPlayerResourcesException("Player doesn't have the resources to pay for a road");
		   }
		   woodCards.remove(woodCards.size() - 1);
		   brickCards.remove(brickCards.size() - 1);
	   }
	   
	 /**
	  * checks if a player has resources to buy a settlement
	  *   
	  * @pre None  
	  *   
	  * @post Return value says whether the player can currently pay for a settlement
	  */
	   public boolean canDoPayForSettlement(){
		   if(wheatCards.size() < 1 || sheepCards.size() < 1 || woodCards.size() < 1 || brickCards.size() < 1) {
			   return false;
		   }
		   return true;
	   }
	   
	   /**
	    * Resources are spent from the players hand to pay for a Settlement
	    * 
	    * @pre canDoPayForSettlement() == true
	    * 
	    * @post 1 wheat card, 1 sheep card, 1 lumber card and 1 brick card are removed from the players Hand
	    */
	   public void payForSettlement() throws InsufficientPlayerResourcesException {
		   if(canDoPayForRoad() == false) {
			   throw new InsufficientPlayerResourcesException("Player doesn't have the resources to pay for a settlement");
		   }
		   wheatCards.remove(wheatCards.size() - 1);
		   sheepCards.remove(sheepCards.size() - 1);
		   woodCards.remove(woodCards.size() - 1);
		   brickCards.remove(brickCards.size() - 1);
	   }
	   
	 /**
	  * checks if a player has resources to buy a city
	  *  
	  * @pre None
	  *  
	  * @post Return value says whether the player can currently pay for a city
	  */
	   public boolean canDoPayForCity(){
		   if(wheatCards.size() < 2 || oreCards.size() < 3) {
			   return false;
		   }
		   return true;
	   }
	   
	   /**
	    * Resources are spent from the players hand to pay for a City
	    * 
	    * @pre canDoPayForCity() == true
	    * 
	    * @post 2 wheat cards and 3 ore cards are removed from the players Hand
	    */
	   public void payForCity() throws InsufficientPlayerResourcesException {
		   if(canDoPayForRoad() == false) {
			   throw new InsufficientPlayerResourcesException("Player doesn't have the resources to pay for a city");
		   }
		   wheatCards.remove(wheatCards.size() - 1);
		   wheatCards.remove(wheatCards.size() - 1);
		   oreCards.remove(oreCards.size() - 1);
		   oreCards.remove(oreCards.size() - 1);
		   oreCards.remove(oreCards.size() - 1);
	   }
	   
	   /**
	    * Retrieves the number of cards in the player's resource card hand
	    * 
	    * @pre None
	    * 
	    * @post The Number of cards in the Players Resource Hand
	    */
	   public int getResourceCardHandSize() {
		   return brickCards.size() + wheatCards.size() + oreCards.size() + sheepCards.size() + woodCards.size();
	   }
}

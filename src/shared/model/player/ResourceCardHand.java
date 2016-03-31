package shared.model.player;
import java.util.*;

import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.items.DevelopmentCard;
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

	private Bank bank;
	
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
	ResourceCardHand(Bank bank){
		this.bank = bank;
		brickCards = new ArrayList<ResourceCard>();
		wheatCards = new ArrayList<ResourceCard>();
		oreCards = new ArrayList<ResourceCard>();
		sheepCards = new ArrayList<ResourceCard>();
		woodCards = new ArrayList<ResourceCard>();
		//initializePreGameResources();
	}
		 
	/**
	 * Initializes ResourceCards from the Server Model
	 * 
	 * @pre A Player object exists
	 * @post the ResourcesCardHand will create the specified numbers of
	 * the resource Card Objects and keep them
	 * @post new ArrayList resourceCards = 0 - 3
	 */
	ResourceCardHand(Bank bank, int brick, int wheat, int ore, int sheep, int wood){
		this.bank = bank;
			brickCards = new ArrayList<ResourceCard>();
			wheatCards = new ArrayList<ResourceCard>();
			oreCards = new ArrayList<ResourceCard>();
			sheepCards = new ArrayList<ResourceCard>();
			woodCards = new ArrayList<ResourceCard>();
			for(int i = 0; i < brick; i++) {
				brickCards.add(i, new ResourceCard(ResourceType.BRICK));
			}
			for(int i = 0; i < wheat; i++) {
				wheatCards.add(i, new ResourceCard(ResourceType.WHEAT));
			}
			for(int i = 0; i < ore; i++) {
				oreCards.add(i, new ResourceCard(ResourceType.ORE));
			}
			for(int i = 0; i < sheep; i++) {
				sheepCards.add(i, new ResourceCard(ResourceType.SHEEP));
			}
			for(int i = 0; i < wood; i++) {
				woodCards.add(i, new ResourceCard(ResourceType.WOOD));
			}
	}	 
	/**
	 * @deprecated
	 * 
	 */
	private void initializePreGameResources() {
		try {
			addCard(bank.playerTakeResource(ResourceType.BRICK));
			addCard(bank.playerTakeResource(ResourceType.BRICK));
			addCard(bank.playerTakeResource(ResourceType.BRICK));
			addCard(bank.playerTakeResource(ResourceType.BRICK));
			addCard(bank.playerTakeResource(ResourceType.WOOD));
			addCard(bank.playerTakeResource(ResourceType.WOOD));
			addCard(bank.playerTakeResource(ResourceType.WOOD));
			addCard(bank.playerTakeResource(ResourceType.WOOD));
			addCard(bank.playerTakeResource(ResourceType.SHEEP));
			addCard(bank.playerTakeResource(ResourceType.SHEEP));
			addCard(bank.playerTakeResource(ResourceType.WHEAT));
			addCard(bank.playerTakeResource(ResourceType.WHEAT));
			addCard(bank.playerTakeResource(ResourceType.ORE));
			addCard(bank.playerTakeResource(ResourceType.WHEAT));
			addCard(bank.playerTakeResource(ResourceType.ORE));
			addCard(bank.playerTakeResource(ResourceType.WHEAT));
			addCard(bank.playerTakeResource(ResourceType.ORE));
			addCard(bank.playerTakeResource(ResourceType.ORE));
		} catch (Exception e) {
			System.out.println("YOU HAVE NOT INITIALIZED THE BANK");
			e.printStackTrace();
		}
	}
	  
	/**
	 * Checks if there are any resource cards in the Players hand to steal
	 * 
	 * @pre None
	 * @return whether there are any resource cards in the Players hand to steal
	 */
	public boolean canDoGetRandomResourceCard() {
		if(getResourceCardHandSize() <= 0) {
			return false;
		}
		return true;
	}
	  
	  /**
	   * Retrieves a Random Resource card
	   * 
	   * @throws Exception 
	   * @pre canDoGetRandomResourceCard() != false
	   * @return a Random Resource card
	   */
	  public ResourceCard getRandomResourceCard() throws Exception {
		  if(canDoGetRandomResourceCard() == false) {
			  throw new Exception("canDoGetRandomResourceCard == False, no cards to steal");
		  }
		  ResourceCard card = null;
		  Random ran = new Random();
		  // Proceed to randomly choose the type of card and see if the player has any of the
		  // chosen type to give up, if none exist, choose another random number and try again.
		  while(card == null) {
				int number = ran.nextInt(5);
				if(number == 0) {
					if(brickCards.size() > 0) {
						card = brickCards.get(brickCards.size() - 1);
						brickCards.remove(brickCards.size() - 1);
					}
				} else if(number == 1) {
					if(wheatCards.size() > 0) {
						card = wheatCards.get(wheatCards.size() - 1);
						wheatCards.remove(wheatCards.size() - 1);
					}
				} else if(number == 2) {
					if(oreCards.size() > 0) {
						card = oreCards.get(oreCards.size() - 1);
						oreCards.remove(oreCards.size() - 1);
					}
				} else if(number == 3) {
					if(sheepCards.size() > 0) {
						card = sheepCards.get(sheepCards.size() - 1);
						sheepCards.remove(sheepCards.size() - 1);
					}
				} else {
					if(woodCards.size() > 0) {
						card = woodCards.get(woodCards.size() - 1);
						woodCards.remove(woodCards.size() - 1);
					}
				}
		  }
		  return card;
	  }
	  
	  /**
	   * TODO
	   * 
	   * 
	   * @param resourceType
	   * @return
	   */
		public ArrayList<ResourceCard> conformToMonopoly(ResourceType resourceType) {
			if(resourceType == ResourceType.BRICK) {
				return brickCards;
			} else if(resourceType == ResourceType.ORE) {
				return oreCards;
			} else if(resourceType == ResourceType.SHEEP) {
				return sheepCards;
			} else if(resourceType == ResourceType.WHEAT) {
				return wheatCards;
			} else if(resourceType == ResourceType.WOOD) {
				return woodCards;
			}
			return new ArrayList<ResourceCard>();
		}
	  
	/**
	 * adds resource cards to player
	 * 
	 * @param cards that need to be added
	 * @throws Exception 
	 * 
	 * @pre Card != null
	 * @post cards added to ArrayList data structure
	 */
	  public void addCard(ResourceCard card) throws Exception {
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
		  } else if(card.getResourceType() == ResourceType.WOOD) {
			  woodCards.add(card);
		  } else {
			  throw new Exception("Invalid Resouce Type to add to Hand");
		  }
	  }
	  
	  /**
	   * Checks if a player has resources to buy a development card
	   * 
	   * @pre None
	   * 
	   * @post Return value says whether the player can currently pay for a road
	   */
	  public boolean canDoPayForDevelopmentCard() {
		   if(sheepCards.size() < 1 || wheatCards.size() < 1 || oreCards.size() < 1) {
			   return false;
		   }
		   return true;
	  }
	  
	  
	  /**
	   * Resources are spent from the players hand to pay for a Development card
	   * @throws InsufficientPlayerResourcesException
	   * 
	   * @pre canDoPayForDevelopmentCard == true
	   * 
	   * @post 1 sheep, 1 wheat, 1 ore are removed from the players Hand, and put back into the bank
	   * 
	   */
	  public DevelopmentCard payForDevelopmentCard() throws InsufficientPlayerResourcesException {
		   if(canDoPayForDevelopmentCard() == false) {
			   throw new InsufficientPlayerResourcesException("Player doesn't have the resources to pay for a Devlopment Card");
		   }

		   DevelopmentCard cardBought = null;
		   // Transfer Cards to Bank
		   try {
			   cardBought = bank.buyDevelopmentCard(sheepCards.get(sheepCards.size() - 1), wheatCards.get(wheatCards.size() - 1), oreCards.get(oreCards.size() - 1));
		   } catch (Exception e) {
			   System.out.println("Tried to buy a Development Card, but had an error");
			   e.printStackTrace();
		   }
		   sheepCards.remove(sheepCards.size() - 1);
		   wheatCards.remove(wheatCards.size() - 1);
		   oreCards.remove(oreCards.size() - 1);
		   
		   return cardBought;
		   
	  }
	  
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
	    * @post 1 brick card and 1 lumber card are removed from the players Hand, and put back into the bank
	    */
	   public void payForRoad() throws InsufficientPlayerResourcesException {
		   if(canDoPayForRoad() == false) {
			   throw new InsufficientPlayerResourcesException("Player doesn't have the resources to pay for a road");
		   }
		   ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
		   // Transfer Cards to Bank
		   cards.add(woodCards.get(woodCards.size() - 1));
		   woodCards.remove(woodCards.size() - 1);
		   cards.add(brickCards.get(brickCards.size() - 1));
		   brickCards.remove(brickCards.size() - 1);

		   try {
			   ResourceCard[] resourcecards = new ResourceCard[cards.size()];
			   bank.playerTurnInResources(cards.toArray(resourcecards));
			} catch (Exception e) {
				System.out.println("Somewhere there were duplicated resources ... and stuff");
				e.printStackTrace();
			}
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
	    * @post 1 wheat card, 1 sheep card, 1 lumber card and 1 brick card are removed from the players Hand, and put back into the bank
	    */
	   public void payForSettlement() throws InsufficientPlayerResourcesException {
		   if(canDoPayForRoad() == false) {
			   throw new InsufficientPlayerResourcesException("Player doesn't have the resources to pay for a settlement");
		   }
		   ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
		   // Transfer Cards to Bank
		   cards.add(woodCards.get(woodCards.size() - 1));
		   woodCards.remove(woodCards.size() - 1);
		   cards.add(brickCards.get(brickCards.size() - 1));
		   brickCards.remove(brickCards.size() - 1);
		   cards.add(wheatCards.get(wheatCards.size() - 1));
		   wheatCards.remove(wheatCards.size() - 1);
		   cards.add(sheepCards.get(sheepCards.size() - 1));
		   sheepCards.remove(sheepCards.size() - 1);
		   

		   try {
			   ResourceCard[] resourcecards = new ResourceCard[cards.size()];
			   bank.playerTurnInResources(cards.toArray(resourcecards));
			} catch (Exception e) {
				System.out.println("Somewhere there were duplicated resources ... and stuff");
				e.printStackTrace();
			}
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
			   
			   System.out.println("Wheat: "+ wheatCards.size()+" Ore: "+oreCards.size());
			   
			   return false;
		   }
		   return true;
	   }
	   
	   /**
	    * Resources are spent from the players hand to pay for a City
	    * 
	    * @pre canDoPayForCity() == true
	    * 
	    * @post 2 wheat cards and 3 ore cards are removed from the players Hand, and put back into the bank
	    */
	   public void payForCity() throws InsufficientPlayerResourcesException {
		   if(!canDoPayForCity()) {
			   throw new InsufficientPlayerResourcesException("Player doesn't have the resources to pay for a city");
		   }
		   ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
		   // Transfer Cards to Bank
		   cards.add(wheatCards.get(wheatCards.size() - 1));
		   wheatCards.remove(wheatCards.size() - 1);
		   cards.add(wheatCards.get(wheatCards.size() - 1));
		   wheatCards.remove(wheatCards.size() - 1);
		   cards.add(oreCards.get(oreCards.size() - 1));
		   oreCards.remove(oreCards.size() - 1);
		   cards.add(oreCards.get(oreCards.size() - 1));
		   oreCards.remove(oreCards.size() - 1);
		   cards.add(oreCards.get(oreCards.size() - 1));
		   oreCards.remove(oreCards.size() - 1);
		   

		   try {
			   ResourceCard[] resourcecards = new ResourceCard[cards.size()];
			   bank.playerTurnInResources(cards.toArray(resourcecards));
			} catch (Exception e) {
				System.out.println("Somewhere there were duplicated resources ... and stuff");
				e.printStackTrace();
			}
	   }
	   
		/**
		 * Has the player disacard(back to the bank) the specified number of resources of the specified resource type
		 * @throws Exception
		 * @param bank
		 * 
		 * @pre The player must have at least the number of Resources cards of the specified type to discard
		 * 
		 * @post the player will have discarded(back to the bank) the specified number of resources of the specified resource type
		 */
		public void discardResourcesOfType(ResourceType resourceType, int numberToDiscard) throws Exception {
			if(getNumberResourcesOfType(resourceType) < numberToDiscard) {
				throw new Exception("Player cannot discard more of a resource than they have");
			}
			
			ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
			if(resourceType == ResourceType.BRICK) {
				for(int i = 0; i < numberToDiscard; i++) {
					cards.add(brickCards.get(brickCards.size() - 1));
					brickCards.remove(brickCards.size() - 1);
				}
			} else if(resourceType == ResourceType.ORE) {
				for(int i = 0; i < numberToDiscard; i++) {
					cards.add(oreCards.get(oreCards.size() - 1));
					oreCards.remove(oreCards.size() - 1);
				}
			} else if(resourceType == ResourceType.SHEEP) {
				for(int i = 0; i < numberToDiscard; i++) {
					cards.add(sheepCards.get(sheepCards.size() - 1));
					sheepCards.remove(sheepCards.size() - 1);
				}
			} else if(resourceType == ResourceType.WHEAT) {
				for(int i = 0; i < numberToDiscard; i++) {
					cards.add(wheatCards.get(wheatCards.size() - 1));
					wheatCards.remove(wheatCards.size() - 1);
				}
			} else if(resourceType == ResourceType.WOOD) {
				for(int i = 0; i < numberToDiscard; i++) {
					cards.add(woodCards.get(woodCards.size() - 1));
					woodCards.remove(woodCards.size() - 1);
				}
			}
			ResourceCard[] resourcecards = new ResourceCard[cards.size()];
			bank.playerTurnInResources(cards.toArray(resourcecards));
		}
		

		/**
		 * Retrieves the cards from the players resourceCardHand that will be used to trade with the bank
		 * 
		 * @pre resourceType != null
		 * @param resourceType
		 * @return the cards from the players resourceCardHand that will be used to trade with the bank
		 * @post the player will have lost the number specified of the specified resourceType
		 */
		public ResourceCard[] prepareCardTrade(ResourceType resourceType, int numberToPrepare) throws Exception{
			if(getNumberResourcesOfType(resourceType) < numberToPrepare) {
				throw new Exception("Player cannot trade more of a resource than they have");
			}
			
			ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
			if(resourceType == ResourceType.BRICK) {
				for(int i = 0; i < numberToPrepare; i++) {
					cards.add(brickCards.get(brickCards.size() - 1));
					brickCards.remove(brickCards.size() - 1);
				}
			} else if(resourceType == ResourceType.ORE) {
				for(int i = 0; i < numberToPrepare; i++) {
					cards.add(oreCards.get(oreCards.size() - 1));
					oreCards.remove(oreCards.size() - 1);
				}
			} else if(resourceType == ResourceType.SHEEP) {
				for(int i = 0; i < numberToPrepare; i++) {
					cards.add(sheepCards.get(sheepCards.size() - 1));
					sheepCards.remove(sheepCards.size() - 1);
				}
			} else if(resourceType == ResourceType.WHEAT) {
				for(int i = 0; i < numberToPrepare; i++) {
					cards.add(wheatCards.get(wheatCards.size() - 1));
					wheatCards.remove(wheatCards.size() - 1);
				}
			} else if(resourceType == ResourceType.WOOD) {
				for(int i = 0; i < numberToPrepare; i++) {
					cards.add(woodCards.get(woodCards.size() - 1));
					woodCards.remove(woodCards.size() - 1);
				}
			}
			ResourceCard[] resourcecards = new ResourceCard[cards.size()];
			return cards.toArray(resourcecards);
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
			int numberOfResources = 0;
			if(resourceType == ResourceType.BRICK) {
				numberOfResources = brickCards.size();
			} else if(resourceType == ResourceType.ORE) {
				numberOfResources = oreCards.size();
			} else if(resourceType == ResourceType.SHEEP) {
				numberOfResources = sheepCards.size();
			} else if(resourceType == ResourceType.WHEAT) {
				numberOfResources = wheatCards.size();
			} else if(resourceType == ResourceType.WOOD) {
				numberOfResources = woodCards.size();
			}
			return numberOfResources;
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
	   
	   public Bank getBank(){
		   return bank;
	   }
}

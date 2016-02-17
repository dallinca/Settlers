package shared.model;

import java.util.ArrayList;
import java.util.Random;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.board.TradePort;
import shared.model.items.DevelopmentCard;
import shared.model.items.ResourceCard;
import shared.model.player.Player;


/**
 * The bank class keeps track of all transactions relating to the cards, the players, and results of the robber. 
 */
public class Bank {
	private ArrayList<ResourceCard> wheatDeck = null; 
	private ArrayList<ResourceCard> sheepDeck = null;
	private ArrayList<ResourceCard> lumberDeck = null;
	private ArrayList<ResourceCard> oreDeck = null;
	private ArrayList<ResourceCard> brickDeck = null;
	private ArrayList<DevelopmentCard> developmentDeck = null;
	private static int resourceNumber = 19;
	
	/**
	 * Constructs a new Bank object and initializes the decks of cards
	 */
	public Bank() {
		wheatDeck = new ArrayList();
		brickDeck = new ArrayList();
		lumberDeck = new ArrayList();
		sheepDeck = new ArrayList();
		oreDeck = new ArrayList();
		developmentDeck = new ArrayList();
		initDevCards();
		initResourceCards();		
	}
	
	/**
	 * Inserts the ResourceCard objects into their various decks
	 */
	private void initResourceCards() {
		for (int i=0; i < resourceNumber; i++) {
			// init wheat 
			wheatDeck.add(i, new ResourceCard(ResourceType.WHEAT));
			// init brick 
			brickDeck.add(i, new ResourceCard(ResourceType.BRICK));
			// init lumber 
			lumberDeck.add(i, new ResourceCard(ResourceType.WOOD));
			// init sheep
			sheepDeck.add(i, new ResourceCard(ResourceType.SHEEP));
			// init ore 
			oreDeck.add(i, new ResourceCard(ResourceType.ORE));
		}
	}
	
	/**
	 * Inserts the various development cards into the deck and shuffles them.
	 */
	private void initDevCards() {
		int soldiers = 14;
		int monopoly = 2;
		int yearOfPlenty = 2;
		int roadBuilder = 2;
		int monument = 5;
		
		int totalDevCards = soldiers + monopoly + yearOfPlenty + roadBuilder + monument;
		
		for (int i = 0; i < totalDevCards; i++) {
			if (i < soldiers) {
				developmentDeck.add(i, new DevelopmentCard(DevCardType.SOLDIER));
			} else if (i >= soldiers &&  i < soldiers + monopoly) {
				developmentDeck.add(i, new DevelopmentCard(DevCardType.MONOPOLY));
			} else if (i >= soldiers + monopoly &&  i < soldiers + monopoly + yearOfPlenty) {
				developmentDeck.add(i, new DevelopmentCard(DevCardType.YEAR_OF_PLENTY));
			} else if (i >= soldiers + monopoly + yearOfPlenty &&  i < soldiers + monopoly + yearOfPlenty + roadBuilder) {
				developmentDeck.add(i, new DevelopmentCard(DevCardType.ROAD_BUILD));
			} else if (i >= soldiers + monopoly + yearOfPlenty + roadBuilder) {
				developmentDeck.add(i, new DevelopmentCard(DevCardType.MONUMENT));
			}
		}
		
		shuffleDevCards();
	}
	
	//Done?
	/**
	 * Unless we want to stack the deck, this method is responsible for shuffling the cards so each card has pretty much an equal opportunity of being chosen. 
	 */
	private void shuffleDevCards() {
		Random rgen = new Random();  // Random number generator			
		
		ArrayList<DevelopmentCard> tempList = new ArrayList();
		
		for (int i=0; i< developmentDeck.size(); i++) {
		    int randomPosition = rgen.nextInt(developmentDeck.size());
		    DevelopmentCard temp = developmentDeck.get(i);
		    developmentDeck.set(i, developmentDeck.get(randomPosition));
		    developmentDeck.set(randomPosition, temp);
		}
	}

	/**
	 * This method checks to see if the resource cards you gave it are the same type, ie not trading a brick and wheat for a lumber.
	 * @param theTrade
	 * @param theType
	 * @return
	 */
	private ResourceType areSameType(ResourceCard[] theTrade, ResourceType theType) {
		if (theTrade != null) {
			ResourceCard test = theTrade[0];
			for (int i = 0; i < theTrade.length; i++) {
				if (test.getResourceType() != theTrade[i].getResourceType()) {
					return null;
				}
			}
			return theType;
		}
		else
			return null;
	}
	
	
	/**
	 * An overridden method that based on how many cards you are going to trade in for one card determines your function
	 * @param three resources that are hopefully of the same type
	 * @param resourceType of the type you want back
	 * @param tradePort
	 * @return the card you receive
	 * @throws Exception
	 */
	public ResourceCard playerTrade(ResourceCard one, ResourceCard two, ResourceCard three, ResourceType resourceType, TradePort tradePort) throws Exception {
		
		//I recognize magic number, but this function already assumes the array size will be three, that which in the event of a 3:1 port,
		ResourceCard[] tradingDeck = new ResourceCard[3];
		tradingDeck[0] = one;
		tradingDeck[1] = two;
		tradingDeck[2] = three;
		
		//Call areSametype to verify the trade is all of the same resource
		ResourceType check = areSameType(tradingDeck, resourceType);
		if (check == null) {
			throw new Exception("Your cards are not the same type.");
		} 
		
		//Check the port to see if the port is the right port for the trade, whether it is 3 for one, or a two for one.
		if (tradePort.getPortType().toString() != "THREE") {
			throw new Exception("The port you sent is not a 3:1 port.");
		}
		
		//This may only apply to domestic trade
		if (tradingDeck[0].getResourceType() == check) {
			throw new Exception("You are trying to trade in multiple resources cards for the same card.");
		}
		
		//Does the bank have the desired resource type or is it all out?
		boolean notBankrupt = canDoPlayerTakeResource(check);
		
		if (notBankrupt == true) {
			//Move Cards to the bank
			playerTurnInResources(tradingDeck);
			//Remove Cards from the bank
			playerTakeResource(check);
			//Return the desired card they wanted.
			ResourceCard newCard = new ResourceCard(check);
			return newCard;
		} else {
			//Should this just return null?
			throw new Exception("The bank has no more cards of that type.");
		}
		
	}
	
	
	/**
	 * An overridden method that based on how many cards you are going to trade in for one card determines your function
	 * @param four resources that are hopefully of the same type
	 * @param resourceType of the type you want back
	 * @return the card you receive
	 * @throws Exception
	 */
	public ResourceCard playerTrade(ResourceCard one, ResourceCard two, ResourceCard three, ResourceCard four, ResourceType resourceType) throws Exception {
		//I recognize magic number, but this function already assumes the array size will be four, that you are not using a port.
		ResourceCard[] tradingDeck = new ResourceCard[4];
		tradingDeck[0] = one;
		tradingDeck[1] = two;
		tradingDeck[2] = three;
		tradingDeck[3] = four;
			
		//Call areSametype to verify the trade is all of the same resource
		ResourceType check = areSameType(tradingDeck, resourceType);
		if (check == null) {
			throw new Exception("Your cards are not the same type.");
		} 
					
		//This may only apply to domestic trade
		if (tradingDeck[0].getResourceType() == check) {
			throw new Exception("You are trying to trade in multiple resources cards for the same card.");
		}
		
		//Does the bank have the desired resource type or is it all out?
		boolean notBankrupt = canDoPlayerTakeResource(check);
			
		if (notBankrupt == true) {
			//Move Cards to the bank
			playerTurnInResources(tradingDeck);
			//Remove Cards from the bank
			playerTakeResource(check);
			//Return the desired card they wanted.
			ResourceCard newCard = new ResourceCard(check);
			return newCard;
		} else {
			//Should this just return null?
			throw new Exception("The bank has no more cards of that type.");
		}
	}
	
	/**
	 * An overridden method that based on how many cards you are going to trade in for one card determines your function
	 * @param two resources that are hopefully of the same type
	 * @param resourceType of the type you want back
	 * @param tradePort
	 * @return the card you receive
	 * @throws Exception
	 */
	public ResourceCard playerTrade(ResourceCard one, ResourceCard two, ResourceType resourceType, TradePort tradePort) throws Exception {
		
		//I recognize magic number, but this function already assumes the array size will be three, that which in the event of a 3:1 port,
		ResourceCard[] tradingDeck = new ResourceCard[2];
		tradingDeck[0] = one;
		tradingDeck[1] = two;
				
		//Call areSametype to verify the trade is all of the same resource
		ResourceType check = areSameType(tradingDeck, resourceType);
		if (check == null) {
			throw new Exception("Your cards are not the same type.");
		} 
			
		//Check the port to see if the port is the right port for the trade, whether it is 3 for one, or a two for one.
		if (tradePort.getPortType().toString() != check.toString()) {
			throw new Exception("The port you sent is incompatible with the given resource cards.");
		}
				
		//This may only apply to domestic trade
		if (tradingDeck[0].getResourceType() == check) {
			throw new Exception("You are trying to trade in multiple resources cards for the same card.");
		}
				
			//Does the bank have the desired resource type or is it all out?
		boolean notBankrupt = canDoPlayerTakeResource(check);
				
		if (notBankrupt == true) {
			//Move Cards to the bank
			playerTurnInResources(tradingDeck);
			
			//Remove Cards from the bank
			//Return the desired card they wanted.
			ResourceCard newCard = playerTakeResource(check); 
			return newCard;
		} else {
			//Should this just return null?
			throw new Exception("The bank has no more cards of that type.");
		}
					
	}
		
	/**
	 * This method adjusts the size of the resource decks as a player takes a card.
	 * @pre check is not null but a valid resource type, though this handles it if it is not valid
	 * @param check
	 * @throws Exception
	 * @post Returns the card that the player desired
	 */
	public ResourceCard playerTakeResource(ResourceType check) throws Exception {
			ResourceCard thePrize = null;
		
		switch (check) {
			case BRICK:
				thePrize = brickDeck.get(brickDeck.size()-1);
				brickDeck.remove(brickDeck.size()-1);
				return thePrize;
			case WHEAT:
				thePrize = wheatDeck.get(wheatDeck.size()-1);
				wheatDeck.remove(wheatDeck.size()-1);
				return thePrize;
			case WOOD:
				thePrize = lumberDeck.get(lumberDeck.size()-1);
				lumberDeck.remove(lumberDeck.size()-1);
				return thePrize;
			case ORE:
				thePrize = oreDeck.get(oreDeck.size()-1);
				oreDeck.remove(oreDeck.size()-1);
				return thePrize;
			case SHEEP:
				thePrize = sheepDeck.get(sheepDeck.size()-1);
				sheepDeck.remove(sheepDeck.size()-1);
				return thePrize;
		}
		throw new Exception("Somehow you snuck by with an invalid type.");
	}


	/**
	 * Does the bank have cards of the type you want?
	 * 
	 * @pre Assumes the resourceType is valid and was previously typed
	 * @param resourceType
	 * @post
	 */
	public boolean canDoPlayerTakeResource(ResourceType resourceType) {
		switch (resourceType) {
			case BRICK:
				if (brickDeck.size() >=1)
					return true;
				else
					return false;
			case WHEAT:
				if (wheatDeck.size() >=1)
					return true;
				else
					return false;
			case WOOD:
				if (lumberDeck.size() >=1)
					return true;
				else
					return false;
			case ORE:
				if (oreDeck.size() >=1)
					return true;
				else
					return false;
			case SHEEP:
				if (sheepDeck.size() >=1)
					return true;
				else
					return false;
				
				//case anything else: throw exception?
		}
		
		return false;
	}
	
	
	/**
	 * checks if there are any development cards that can be purchased
	 * 
	 * @pre None
	 * 
	 * @return whether any development cards that can be purchased
	 */
	public boolean hasAvailableDevelopmentCards() {
		if(developmentDeck.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Whenever somebody is taking development cards from the bank, this method is called. 
	 * This method makes the necessary adjustment to the deck, which cards are still in it, and how many total are left.
	 * 
	 * @pre take is positive, less than the amount of remaining development cards in the deck, and that the player object is a real non-bull object
	 * @param int take is a number of the development cards a player is taking
	 * @param Player player is the player purchasing the cards
	 */
	public boolean canDoBuyDevelopmentCard(ResourceCard sheep, ResourceCard wheat, ResourceCard ore) {
		if (sheep.getResourceType() != ResourceType.SHEEP || wheat.getResourceType() != ResourceType.WHEAT || ore.getResourceType() != ResourceType.ORE) {
			return false;
		}
		if (developmentDeck.size() == 0) {
			return false;
		}
		return true;
	}
	
	//buyDevelopmentCard(
	//ask if the size of the development cards is too much
	/**
	 * Purchases a development card with the given resources
	 * @pre all those resources are valid cards of the specified type required to buy it (it checks the type though)
	 * @param sheep
	 * @param wheat
	 * @param ore
	 * @return
	 * @throws Exception
	 */
	public DevelopmentCard buyDevelopmentCard(ResourceCard sheep, ResourceCard wheat, ResourceCard ore) throws Exception {
		if (canDoBuyDevelopmentCard(sheep, wheat, ore) == false) {
			throw new Exception("Cannot buy Development Card, needs more specific info.");
		}
		DevelopmentCard shipment = developmentDeck.get(developmentDeck.size()-1);
		developmentDeck.remove(developmentDeck.size()-1);
		return shipment;
	
	}
	
	/**
	 * This method supposedly can be an independent turnIn function that supports the turn in whether it is from having more than 7 cards on a 7 roll, or a trade with the bank.
	 * It only cares that the resources are valid resources. the trade function should have already assured that they are the same type. Is this correct?
	 * @param resources
	 * @throws Exception 
	 */
	public void playerTurnInResources(ResourceCard[] resources) throws Exception {
		for (int i = 0; i < resources.length; i++) {
			ResourceType check = resources[i].getResourceType();
			
			switch (check) {
				case BRICK:
					if (brickDeck.size() <= resourceNumber) {
						brickDeck.add(resources[i]);
						break;
					} else {
						throw new Exception("The bank cannot accept anymore of this type of card. Something has gone wrong.");
					}
				case WHEAT:
					if (wheatDeck.size() <= resourceNumber) {
						wheatDeck.add(resources[i]);
						break;
					} else {
						throw new Exception("The bank cannot accept anymore of this type of card. Something has gone wrong.");
					}
				case WOOD:
					if (lumberDeck.size() <= resourceNumber) {
						lumberDeck.add(resources[i]);
						break;
					} else {
						throw new Exception("The bank cannot accept anymore of this type of card. Something has gone wrong.");
					}
				case ORE:
					if (oreDeck.size() <= resourceNumber) {
						oreDeck.add(resources[i]);
						break;
					} else {
						throw new Exception("The bank cannot accept anymore of this type of card. Something has gone wrong.");
					}
				case SHEEP:
					if (sheepDeck.size() <= resourceNumber) {
						sheepDeck.add(resources[i]);
						break;
					} else {
						throw new Exception("The bank cannot accept anymore of this type of card. Something has gone wrong.");
					}
				default: 
					throw new Exception("One of your resource Cards you are trying to turn in is somehow not a valid type");
			}
		}
	}
		//playerTurnInResources(wheat);
		//playerTurnInResources(ore);
		

	
	//done canDoPlayerTakeResource(ResourceType resourceType)
	//done playerTakeResource
	//done playerTurnInResource
	//done switch statement inside of turn in that looks at resource type and puts it in appropriate array and checks to see if arraylist is the maximum size to see if error must be thrown
	//done bankExchange
	//done canDoPlayerBuyDevelopmentCard
	//done override the playerTrade method three times
	
	
	
}

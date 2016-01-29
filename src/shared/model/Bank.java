package shared.model;

import java.util.Random;

import shared.definitions.ResourceType;
import shared.model.items.DevelopmentCard;
import shared.model.items.ResourceCard;
import shared.model.player.Player;


/**
 * The bank class keeps track of all transactions relating to the cards, the players, and results of the robber. 
 * 
 *
 */
public class Bank {
	private ResourceCard[] wheatDeck = null; 
	private ResourceCard[] sheepDeck = null;
	private ResourceCard[] lumberDeck = null;
	private ResourceCard[] oreDeck = null;
	private ResourceCard[] brickDeck = null;
	private DevelopmentCard[] developmentDeck = null;
	private static int resourceNumber = 15;
	
	public Bank() {
		wheatDeck = new ResourceCard[resourceNumber];
		brickDeck = new ResourceCard[resourceNumber];
		lumberDeck = new ResourceCard[resourceNumber];
		sheepDeck = new ResourceCard[resourceNumber];
		oreDeck = new ResourceCard[resourceNumber];
		
		initDevCards();
		initResourceCards();		
		
	}
	
	
	private void initResourceCards() {
		for (int i=0; i < resourceNumber; i++) {
			// init wheat 
			wheatDeck[i] = new ResourceCard(ResourceType.WHEAT);
			// init brick 
			brickDeck[i] = new ResourceCard(ResourceType.BRICK);
			// init lumber 
			lumberDeck[i] = new ResourceCard(ResourceType.WOOD);
			// init sheep
			sheepDeck[i] = new ResourceCard(ResourceType.SHEEP);
			// init ore 
			oreDeck[i] = new ResourceCard(ResourceType.ORE);
		}
	}
	
	private void initDevCards() {
		int soldiers = 14;
		int monopoly = 2;
		int yearOfPlenty = 2;
		int roadBuilder = 2;
		int monument = 5;
		
		developmentDeck = new DevelopmentCard[25];
		//Have to create the Development card objects first..... ugh hahahaha
		
		shuffleDevCards();
	}
	
	private void shuffleDevCards() {
		Random rgen = new Random();  // Random number generator			
		 
		for (int i=0; i<developmentDeck.length; i++) {
		    int randomPosition = rgen.nextInt(developmentDeck.length);
		    DevelopmentCard temp = developmentDeck[i];
		    developmentDeck[i] = developmentDeck[randomPosition];
		    developmentDeck[randomPosition] = temp;
		}
	}

	/**
	 * 
	 * Whenever somebody is receiving or taking resources from the bank, this method is called. 
	 * Whether they are trading them in via a port, or paying them because of the robber, or paying for something.
	 * This method makes the necessary adjustment to the decks, which cards are in them and how many are left.
	 * One of these parameters could be null, for example: turning in cards that were taken by the robber.
	 * 
	 * @pre both of the arrays being passed in are not null, have valid entries, and that the player has the necessary cards he/she is turning in, 
	 * and that the bank has enough cards to hand out.
	 * @param an array of the resource cards that are to be added to the decks: toTurnIn.
	 * @param an array of the resource cards that are to be taken away: toTake.
	 */
	public void resourceCardTransaction(Player bankingPlayer, ResourceCard[] toTake, ResourceCard[] toTurnIn) {
		//does the player have a hand? That changes this all. Currently it doesn't, so I cannot proceed with this method.
		
		/*
		 if (toTurnIn != null) { 
			 for (int i = 0; i < bankingPlayer.getHand().length; i++) {
				for (int j = 0; j < toTurnin.length(); j++) {
					if (bankingPlayer.getHand()[i] == toTurnIn[j]) {
						
						Have to somehow figure this part out how to check these details out....
						foreach loop maybe?
						bankingPlayer.getHand()[i].type
						
						Then I would compare it to see which deck it belongs in.
						
						
						bankingPlayer.getHand()[i].remove();
						
					}
				}
			}
		}
		if (toTake != null) {
			for (int k = 0; k < toTake.length(); k++) {
				bankingPlayer.getHand().append(toTake[k]);
			}
		
		*/
	}
	
	
	/**
	 * This method will exchange resources between two players who have agreed to trade. This inserts the correct cards into the arraylists of each player
	 * may return an integer signifying it was successful for testing and error handling.
	 * 
	 * @pre: two players that exist, two arrays of type ResourceCard that are not null.
	 * @post
	 * 
	 */	
	public void tradeTransaction(Player one, ResourceCard[] oneToTrade, Player two, ResourceCard[] twoToTrade) {
	
	}
	
	/**
	 * Whenever somebody is taking development cards from the bank, this method is called. 
	 * This method makes the necessary adjustment to the deck, which cards are still in it, and how many total are left.
	 * 
	 * @pre take is positive, less than the amount of remaining development cards in the deck, and that the player object is a real non-bull object
	 * @param int take is a number of the development cards a player is taking
	 * @param Player player is the player purchasing the cards
	 */
	public void developmentCardTransaction(int take, Player player) {
		
	}

	
	
	
	
	
	
}

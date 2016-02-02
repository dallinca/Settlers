package shared.model.player;

import java.util.*;

import shared.definitions.DevCardType;
import shared.model.Bank;
import shared.model.items.DevelopmentCard;
import shared.model.player.exceptions.NullCardException;

/**
 * The DevelopmentCards class keeps track of a players development cards 
 * It has methods for placing roads, settlements, and cities
 * 
 * Operations for the class include adding and deleting development cards from a player
 * 
 *
 * Domain:
 * 		ArrayList developmentCards: each player has an array of 0 to 25 development cards
 */


public class DevelopmentCardHand {

	private ArrayList<DevelopmentCard> soldierCards;
	private ArrayList<DevelopmentCard> victoryPointCards;
	private ArrayList<DevelopmentCard> roadBuilderCards;
	private ArrayList<DevelopmentCard> monopolyCards;
	private ArrayList<DevelopmentCard> yearOfPlentyCards;
     
    /**
	 * Initializes DevelopmentCards
	 * 
	 * @pre A Player object exists
	 * @post new ArrayList DevelopmentCards = 0 
	 */
	DevelopmentCardHand(){}
    
    /**
	 * adds development cards to player
	 * 
	 * @param cards that need to be added
	 * 
	 * @pre Card != null
	 * @post cards added to ArrayList data structure
	 */
    void addCard(DevelopmentCard card) throws NullCardException {
    	if(card == null) {
    		throw new NullCardException("The card to be added to the players Development Cards is Null");
    	} else if(card.getDevCardType() == null) {
    		throw new NullCardException("The card to be added to the players Development Cards is not Null, but the Card type is null");
    	}
    	if(card.getDevCardType() == DevCardType.SOLDIER) {
    		soldierCards.add(card);
    	} else if(card.getDevCardType() == DevCardType.MONUMENT) {
    		victoryPointCards.add(card);
    	} else if(card.getDevCardType() == DevCardType.ROAD_BUILD) {
    		roadBuilderCards.add(card);
    	} else if(card.getDevCardType() == DevCardType.MONOPOLY) {
    		monopolyCards.add(card);
    	} else if(card.getDevCardType() == DevCardType.YEAR_OF_PLENTY) {
    		yearOfPlentyCards.add(card);
    	}
    }
    
    /**
     * TODO interface with bank
     * 
     * 
     * @param bank
     */
    void takeDevelopmentCardFromBank(Bank bank) {
    	//addCard(bank.takeDevelopmentCard());
    }
    
    /**
     * TODO implement play Card
     * 
	 * removes resource cards from player
	 * 
	 * @pre None
	 * @post deleted from ArrayList data structure
	 */
    void playDevelopmentCard(){}
   
	
	/**
	 * Gets the number of soldier cards that the player has played
	 * 
	 * @pre none
	 * 
	 * @post return value contains number of knights that have been played by the player
	 */
	public int getNumberOfSoldiersPlayed() {
		int numberPlayed = 0;
		// Iterate through all the player owned soldier cards to check, which of these have been played
		for(DevelopmentCard card: soldierCards) {
			if(card.hasBeenPlayed()) {
				numberPlayed++;
			}
		}
		return numberPlayed;
	}
	
}

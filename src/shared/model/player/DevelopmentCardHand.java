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
	public DevelopmentCardHand(){}
    

	/**
	 * Checks to see if there is at least one card of the said type that can be played this turn
	 * 
	 * @pre None
	 * 
	 * @return Whether a development card of the specified type can be played
	 */
	public boolean canDoPlayDevelopmentCard(int turnNumber, DevCardType devCardType) {
		if(devCardType == DevCardType.SOLDIER) {
			for(DevelopmentCard Soldier : soldierCards) {
				if(Soldier.hasBeenPlayed() == false && Soldier.getTurnBought() < turnNumber) {
					return true;
				}
			}
		} else if(devCardType == DevCardType.MONUMENT) {
			for(DevelopmentCard VP : victoryPointCards) {
				if(VP.hasBeenPlayed() == false && VP.getTurnBought() < turnNumber) {
					return true;
				}
			}
		} else if(devCardType == DevCardType.ROAD_BUILD) {
			for(DevelopmentCard RB : roadBuilderCards) {
				if(RB.hasBeenPlayed() == false && RB.getTurnBought() < turnNumber) {
					return true;
				}
			}
		} else if(devCardType == DevCardType.MONOPOLY) {
			for(DevelopmentCard Monopoly : monopolyCards) {
				if(Monopoly.hasBeenPlayed() == false && Monopoly.getTurnBought() < turnNumber) {
					return true;
				}
			}
		} else if(devCardType == DevCardType.YEAR_OF_PLENTY) {
			for(DevelopmentCard YOP : yearOfPlentyCards) {
				if(YOP.hasBeenPlayed() == false && YOP.getTurnBought() < turnNumber) {
					return true;
				}
			}
		}
		return false;
			
	}
	
	
	/**
	 * Marks a devlopment card of the specified type as having been played
	 * 
	 * @param turnNumber
	 * @param devCardType
	 * 
	 * @post a dev card will be marked as having been played
	 */
	public void playDevelopmentCard(int turnNumber, DevCardType devCardType) {
		if(devCardType == DevCardType.SOLDIER) {
			for(DevelopmentCard Soldier : soldierCards) {
				if(Soldier.hasBeenPlayed() == false && Soldier.getTurnBought() < turnNumber) {
					Soldier.setHasBeenPlayed(true);
					return;
				}
			}
		} else if(devCardType == DevCardType.MONUMENT) {
			for(DevelopmentCard VP : victoryPointCards) {
				if(VP.hasBeenPlayed() == false && VP.getTurnBought() < turnNumber) {
					VP.setHasBeenPlayed(true);
					return;
				}
			}
		} else if(devCardType == DevCardType.ROAD_BUILD) {
			for(DevelopmentCard RB : roadBuilderCards) {
				if(RB.hasBeenPlayed() == false && RB.getTurnBought() < turnNumber) {
					RB.setHasBeenPlayed(true);
					return;
				}
			}
		} else if(devCardType == DevCardType.MONOPOLY) {
			for(DevelopmentCard Monopoly : monopolyCards) {
				if(Monopoly.hasBeenPlayed() == false && Monopoly.getTurnBought() < turnNumber) {
					Monopoly.setHasBeenPlayed(true);
					return;
				}
			}
		} else if(devCardType == DevCardType.YEAR_OF_PLENTY) {
			for(DevelopmentCard YOP : yearOfPlentyCards) {
				if(YOP.hasBeenPlayed() == false && YOP.getTurnBought() < turnNumber) {
					YOP.setHasBeenPlayed(true);
					return;
				}
			}
		}
	}
	
	
	/**
	 * Checks to see how many unplayed cards of the said type the player owns
	 * 
	 * @pre None;
	 * 
	 * @return the Number of unplayed dev cards of the specified type
	 */
	public int numberUnplayedDevCards(DevCardType devCardType) {
		int numberAvailable = 0;
		if(devCardType == DevCardType.SOLDIER) {
			for(DevelopmentCard Soldier : soldierCards) {
				if(Soldier.hasBeenPlayed() == false) {
					numberAvailable++;
				}
			}
		} else if(devCardType == DevCardType.MONUMENT) {
			for(DevelopmentCard VP : victoryPointCards) {
				if(VP.hasBeenPlayed() == false) {
					numberAvailable++;
				}
			}
		} else if(devCardType == DevCardType.ROAD_BUILD) {
			for(DevelopmentCard RB : roadBuilderCards) {
				if(RB.hasBeenPlayed() == false) {
					numberAvailable++;
				}
			}
		} else if(devCardType == DevCardType.MONOPOLY) {
			for(DevelopmentCard Monopoly : monopolyCards) {
				if(Monopoly.hasBeenPlayed() == false) {
					numberAvailable++;
				}
			}
		} else if(devCardType == DevCardType.YEAR_OF_PLENTY) {
			for(DevelopmentCard YOP : yearOfPlentyCards) {
				if(YOP.hasBeenPlayed() == false) {
					numberAvailable++;
				}
			}
		}
		return numberAvailable;
	}
	
	
    /**
	 * adds development cards to player
	 * 
	 * @param cards that need to be added
	 * 
	 * @pre Card != null
	 * @post cards added to ArrayList data structure
	 */
    public void addCard(DevelopmentCard card) throws NullCardException {
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

package shared.model.turn;

import client.Client;
import shared.model.Game;


/**
 * All action methods associated with exchanging resource cards.
 *
 */
public class Trade {

	private Client client;
	
	Trade(Client client) {
		this.client = client;
	}
	/**
	 * Trades selected cards from player's hand for requested resources from another player's hand. 
	 * The other player either accepts or rejects the offer.
	 * 
	 * @pre Player must have resource cards to trade.
	 * @post Player and another player either swap proffered cards, or the trade is refused.
	 */
	public void tradeWithPlayer(){
		
		//propose trade
		//offer bundle

	}

	
	/**
	 *Trades selected cards of same type from player's hand to bank in exchange for another specified resource.
	 * 
	 * @pre Player must have 4 of same type of resource card if no port. 3 resource of same type if has general port.
	 * 2 resource of specified type if has access to specified resource port. Resource deck must contain desired resource.
	 * @post Player's cards go back to deck, gains selected resource card.
	 * 
	 */
	public void tradeWithBank(){

	}

}

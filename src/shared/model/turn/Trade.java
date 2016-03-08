package shared.model.turn;

import client.Client;
import shared.definitions.ResourceType;
import shared.model.Game;


/**
 * All action methods associated with exchanging resource cards.
 *
 */
public class Trade {


	
	Trade() {
	}
	
	/**
	 * If you can do domestic trade or not
	 * @pre you have resources to trade
	 * @return whether or not you can trade with players or not
	 */
	public boolean canDoTradeWithPlayer() {
		System.out.println("Trade canDoTradeWithPlayer()");

		return Client.getInstance().getGame().canDoPlayerDoDomesticTrade(0, null, 0, null);
		//return Client.getInstance().getGame().canDoCurrentPlayerDoDomesticTrade(tradeIn, receive);
	}
	
	
	
	/**
	 * Trades selected cards from player's hand for requested resources from another player's hand. 
	 * The other player either accepts or rejects the offer.
	 * @throws Exception 
	 * 
	 * @pre Player must have resource cards to trade.
	 * @post Player and another player either swap proffered cards, or the trade is refused.
	 */
	public void tradeWithPlayer() throws Exception{
		System.out.println("Trade tradeWithPlayer()");
		if (canDoTradeWithPlayer()) {
			try {
				Client.getInstance().getGame().doDomesticTrade(0, null, 0, null);
			} catch(Exception e) {
				System.out.println("Something went wrong with domestic trade");
			}
		} else throw new Exception("You should not have been able to call upon the domestic trade function tradeWithPlayer");
		//propose trade
		//offer bundle

	}

	/**
	 * Checks to see if the bank has the resources you want (the checking of your resources is done again but should have been done prior to this)
	 * @pre you actually own the resources you are trading in
	 * @param tradeIn
	 * @param receive
	 * @return whether or not you can do maritime trade
	 */
	public boolean canDoTradeWithBank(ResourceType tradeIn, ResourceType receive) {
	System.out.println("Trade canDoTradeWithBank()");
		return Client.getInstance().getGame().canDoPlayerDoMaritimeTrade(tradeIn, receive);
	}
	
	/**
	 * Trades selected cards of same type from player's hand to bank in exchange for another specified resource.
	 * @throws Exception 
	 * 
	 * @pre Player must have 4 of same type of resource card if no port. 3 resource of same type if has general port.
	 * 2 resource of specified type if has access to specified resource port. Resource deck must contain desired resource.
	 * @post Player's cards go back to deck, gains selected resource card.
	 * 
	 */
	public void tradeWithBank(ResourceType tradeIn, ResourceType receive) throws Exception{
		System.out.println("Trade tradeWithBank()");
		if (canDoTradeWithPlayer()) {
			try {
				Client.getInstance().getGame().doMaritimeTrade(null, null);
			} catch(Exception e) {
				System.out.println("Something went wrong with maritime trade");
			}
		} else throw new Exception("You should not have been able to call upon the maritime trade function tradeWithBank");
	}

}

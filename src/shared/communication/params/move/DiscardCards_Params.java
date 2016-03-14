package shared.communication.params.move;

import shared.communication.params.move.data.DiscardedCards;

/**
 * Defines a discard card command for the server.
 */
public class DiscardCards_Params {
	
	private final String type = "discardCards";
	private int playerIndex;
	private DiscardedCards discardedCards;
	
	
	public DiscardCards_Params(int playerIndex, int brick, int ore, int sheep,
			int wheat, int wood) {
		this.playerIndex = playerIndex;
		this.discardedCards = new DiscardedCards(brick, ore, sheep, wheat, wood);
	}


	public int getPlayerIndex() {
		return playerIndex;
	}


	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}


	public DiscardedCards getDiscardedCards() {
		return discardedCards;
	}


	public void setDiscardedCards(DiscardedCards discardedCards) {
		this.discardedCards = discardedCards;
	}

	
	
}

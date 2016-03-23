package shared.communication.params.move;

/**
 * Defines an offer trade command for the server.
 */
public class OfferTrade_Params {

	private final String type = "offerTrade";
	private int playerIndex;
	private Offer offer;

	private int receiver;

	public OfferTrade_Params(int playerIndex, int receiver, int brick, int ore,
			int sheep, int wheat, int wood) {
		
		this.playerIndex = playerIndex;		
		setOffer(new Offer(brick, ore, sheep, wheat, wood));
		this.receiver = receiver;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public class Offer{
		private int brick;
		private int ore;
		private int sheep;
		private int wheat;
		private int wood;

		public Offer(int brick, int ore, int sheep, int wheat, int wood) {
			super();
			this.brick = brick;
			this.ore = ore;
			this.sheep = sheep;
			this.wheat = wheat;
			this.wood = wood;
		}

		public int getBrick() {
			return brick;
		}

		public void setBrick(int brick) {
			this.brick = brick;
		}

		public int getSheep() {
			return sheep;
		}

		public void setSheep(int sheep) {
			this.sheep = sheep;
		}

		public int getWheat() {
			return wheat;
		}

		public void setWheat(int wheat) {
			this.wheat = wheat;
		}

		public int getWood() {
			return wood;
		}

		public void setWood(int wood) {
			this.wood = wood;
		}

		public int getOre() {
			return ore;
		}

		public void setOre(int ore) {
			this.ore = ore;
		}

	}

}

package shared.communication.params.move;

public class OfferTrade_Params {
	
	private int playerIndex;
	private int brick;
	private int ore;
	private int sheep;
	private int wheat;
	private int wood;
	private int receiver;

	public OfferTrade_Params(int playerIndex, int receiver, int brick, int ore,
			int sheep, int wheat, int wood) {
		this.playerIndex = playerIndex;
		this.brick = brick;
		this.setOre(ore);
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
		this.receiver = receiver;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
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

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}
	
	

}

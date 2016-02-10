package shared.model.items;

import shared.definitions.DevCardType;

public class DevelopmentCard {
	
	private int turnBought;
	private DevCardType devCardType;
	private boolean hasBeenPlayed = false;

	public DevelopmentCard(DevCardType devCardType) {
		this.devCardType = devCardType;
		this.turnBought = 0;
	}

	public DevCardType getDevCardType() {
		return devCardType;
	}

	public boolean hasBeenPlayed() {
		return hasBeenPlayed;
	}

	public void setHasBeenPlayed(boolean hasBeenPlayed) {
		this.hasBeenPlayed = hasBeenPlayed;
	}

	public int getTurnBought() {
		return turnBought;
	}

	public void setTurnBought(int turnBought) {
		this.turnBought = turnBought;
	}
	
	
}

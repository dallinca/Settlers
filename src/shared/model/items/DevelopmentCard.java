package shared.model.items;

import shared.definitions.DevCardType;

public class DevelopmentCard {
	
	private DevCardType devCardType;
	private boolean hasBeenPlayed;

	public DevelopmentCard(DevCardType devCardType) {
		this.devCardType = devCardType;
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
	
}

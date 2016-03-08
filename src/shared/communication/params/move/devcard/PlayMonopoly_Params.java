package shared.communication.params.move.devcard;

import shared.definitions.ResourceType;

/**
 * Defines a play monopoly card command for the server.
 */
public class PlayMonopoly_Params {

	private final String type = "Monopoly";
	private int playerIndex;
	private String resource;

	/**
	 * 
	 * @param playerIndex
	 * @param type
	 */
	public PlayMonopoly_Params(int playerIndex, ResourceType type) {
		
		this.playerIndex = playerIndex;
		resource = type.toString().toLowerCase();
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}



}

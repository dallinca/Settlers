package shared.communication.params.move.devcard;

import shared.definitions.ResourceType;

/**
 * Defines a play year of plenty card command for the server.
 *
 */
public class PlayYearOfPlenty_Params {

	private final String type = "Year_of_Plenty";
	private int playerIndex;
	private String resource1;
	private String resource2;


/**
 * 
 * @param playerIndex
 * @param type1
 * @param type2
 */
	public PlayYearOfPlenty_Params(int playerIndex, ResourceType type1, ResourceType type2) {
		this.playerIndex = playerIndex;
		resource1 = type1.toString().toLowerCase();
		resource2 = type2.toString().toLowerCase();
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getResource1() {
		return resource1;
	}

	public void setResource1(String resource1) {
		this.resource1 = resource1;
	}

	public String getResource2() {
		return resource2;
	}

	public void setResource2(String resource2) {
		this.resource2 = resource2;
	}
	
	

}

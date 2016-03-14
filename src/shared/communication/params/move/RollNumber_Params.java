package shared.communication.params.move;

/**
 * 
 * Defines a roll number command for the server.
 */
public class RollNumber_Params {
	
	private final String type = "rollNumber";
	private int playerIndex;
	private int number;

	/**
	 * 
	 * @param playerIndex
	 * @param number
	 */
	public RollNumber_Params(int playerIndex, int number) {
		this.setPlayerIndex(playerIndex);
		this.setNumber(number);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}

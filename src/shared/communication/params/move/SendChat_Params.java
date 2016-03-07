package shared.communication.params.move;


/**
 * Defines a send chat command for the server.
 *
 */
public class SendChat_Params {

	private final String type = "sendChat";
	private int playerIndex;
	private String content;
	
	public SendChat_Params(int playerIndex, String content) {
		this.playerIndex = playerIndex;
		//this.content = "\""+content+"\"";
		this.content = content;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}
	
}

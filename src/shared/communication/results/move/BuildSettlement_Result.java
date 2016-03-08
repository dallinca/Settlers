package shared.communication.results.move;

import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.model.Game;

/**
 * Response object for build settlement server command.
 * 
 * @author jchrisw
 *
 */
public class BuildSettlement_Result {
	
	private boolean valid;
	private Game game;
	private ClientModel model;

	public ClientModel getModel() {
		return model;
	}

	public void setModel(ClientModel model) {
		this.model = model;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	public Game getGame(){
		return game;
	}

	public BuildSettlement_Result(String post) {

		if (post==null){
			setValid(false);
		}
		else if (post.equals("\"true\"")){
			setValid(true);
			game = null;
			model = null;
		}
		else{
			setValid(true);
			JsonConverter converter = new JsonConverter();
			game = converter.parseJson(post);
			model = converter.getModel();
		}
	}
	
	public BuildSettlement_Result() {
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}	
}

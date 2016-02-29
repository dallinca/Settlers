package shared.communication.results.move;

import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.model.Game;

public class FinishTurn_Result {
	
	boolean valid;
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

	public FinishTurn_Result(String post) {

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
	
	public FinishTurn_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}

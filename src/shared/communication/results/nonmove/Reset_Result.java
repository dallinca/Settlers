package shared.communication.results.nonmove;

import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.model.Game;

public class Reset_Result {

	private Game game;	
	private boolean valid;
	private boolean upToDate;
	private ClientModel model;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ClientModel getModel() {
		return model;
	}

	public void setModel(ClientModel model) {
		this.model = model;
	}

	public Reset_Result(){
		valid = false;
		game = null;
		model = null;
	}

	public Reset_Result(String post) {

		if (post==null){
			setValid(false);
		}
		else if (post.equals("\"true\"")){
			setUpToDate(true);
			setValid(true);
			game = null;
			model = null;
		}
		else{
			setValid(true);
			setUpToDate(false);
			JsonConverter converter = new JsonConverter();
			game = converter.parseJson(post);
			model = converter.getModel();
		}
	}

	public boolean isValid() {		
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isUpToDate() {
		return upToDate;
	}

	public void setUpToDate(boolean upToDate) {
		this.upToDate = upToDate;
	}
}

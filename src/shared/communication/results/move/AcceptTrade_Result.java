package shared.communication.results.move;

import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.model.Game;

/**
 * Response object from accept trade server command.
 *
 */
public class AcceptTrade_Result {
	
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

	public AcceptTrade_Result(Game game){
		this.game = game;	
		
		if (game == null){
			valid = false;
		}else{
			valid = true;
		}
	}
	
	public AcceptTrade_Result(String post) {

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
	
	public AcceptTrade_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}

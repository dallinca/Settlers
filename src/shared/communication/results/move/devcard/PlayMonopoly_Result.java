package shared.communication.results.move.devcard;

import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.model.Game;

/**
 * Response object for play monopoly server command.
 *
 */
public class PlayMonopoly_Result {

	private final String type = "monopoly";
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

	public PlayMonopoly_Result(Game game){
		this.game = game;	
		
		if (game == null){
			valid = false;
		}else{
			valid = true;
		}
	}
	
	public PlayMonopoly_Result(String post) {

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

	public PlayMonopoly_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}

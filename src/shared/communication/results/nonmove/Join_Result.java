package shared.communication.results.nonmove;

import shared.communication.results.ClientModel;

/**
 * Result object from join command.
 *
 */
public class Join_Result {
	
	private boolean valid;
	
	private ClientModel model;
	private String response;
	private String gameCookie;
	
	public Join_Result(String doPost){
		System.out.println("Join game result string: "+doPost);
		if (doPost == null){
			setResponse(doPost);
			valid = false;
		}
		else if (doPost.equals("Success")){
			setResponse(doPost);
			valid = true;
		}		
	}
	
	public Join_Result(){
		
		valid = false;
		
	}
	
	public void setValid(boolean valid){
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	boolean joined = false;
	
	public Join_Result(boolean joined) {
		this.joined = joined;
	}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	
	public void setGameCookie(String cookie){
		gameCookie = cookie;
	}

	public String getGameCookie() {
		return gameCookie;
	}

	public ClientModel getModel() {
		return model;
	}

	public void setModel(ClientModel model) {
		this.model = model;
	}	
}

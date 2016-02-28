package shared.communication.results.move;

import shared.model.Game;

public class AcceptTrade_Result {
	
	private boolean valid;
	
	public AcceptTrade_Result(String doPost) {
		if (doPost==null){
			valid = false;
		}
		else if (doPost.equals("Success")){
			valid = true;
		}
		else {
			valid = false;
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

	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
}

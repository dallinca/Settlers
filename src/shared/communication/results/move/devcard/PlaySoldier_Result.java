package shared.communication.results.move.devcard;

import shared.model.Game;

public class PlaySoldier_Result {

	private boolean valid;

	public PlaySoldier_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}

	public PlaySoldier_Result(){
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

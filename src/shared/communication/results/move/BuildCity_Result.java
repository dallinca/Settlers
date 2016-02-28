package shared.communication.results.move;

import shared.model.Game;

public class BuildCity_Result {
	
	private boolean valid;

	public BuildCity_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}
	public BuildCity_Result(){
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

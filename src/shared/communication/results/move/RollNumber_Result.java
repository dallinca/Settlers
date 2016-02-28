package shared.communication.results.move;

import shared.model.Game;

public class RollNumber_Result {

	private boolean valid;

	public RollNumber_Result(String doPost) {
		System.out.println(doPost);
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}

	public RollNumber_Result(){
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

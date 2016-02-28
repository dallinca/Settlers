package shared.communication.results.move;

import shared.model.Game;

public class SendChat_Result {
	
	private boolean valid;

	public SendChat_Result(String doPost) {
		System.out.println(doPost);
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}

	public SendChat_Result(){
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

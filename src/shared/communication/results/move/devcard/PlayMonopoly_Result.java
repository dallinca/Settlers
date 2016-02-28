package shared.communication.results.move.devcard;

public class PlayMonopoly_Result {

	private boolean valid;
	
	public PlayMonopoly_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
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

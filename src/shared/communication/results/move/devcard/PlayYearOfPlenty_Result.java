package shared.communication.results.move.devcard;

public class PlayYearOfPlenty_Result {

	private boolean valid;

	public PlayYearOfPlenty_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}

	public PlayYearOfPlenty_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}

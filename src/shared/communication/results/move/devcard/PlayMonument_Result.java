package shared.communication.results.move.devcard;

public class PlayMonument_Result {

	private boolean valid;

	public PlayMonument_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}

	public PlayMonument_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}


}

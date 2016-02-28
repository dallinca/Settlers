package shared.communication.results.move;

public class AcceptTrade_Result {
	private boolean valid;

	

	public AcceptTrade_Result(String doPost) {
		if (doPost.equals("Success")){
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
}

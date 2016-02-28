package shared.communication.results.move;

public class DiscardCards_Result {
	
	private boolean valid;
	
	public DiscardCards_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else {
			valid = false;
		}
	}
	public DiscardCards_Result(){
		valid = false;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	
}

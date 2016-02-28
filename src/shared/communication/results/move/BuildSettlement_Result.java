package shared.communication.results.move;

public class BuildSettlement_Result {
	
	private boolean valid;

	public BuildSettlement_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else {
			valid = false;
		}
	}
	
	public BuildSettlement_Result() {
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}

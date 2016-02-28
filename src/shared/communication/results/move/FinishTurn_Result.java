package shared.communication.results.move;

public class FinishTurn_Result {
	
	boolean valid;

	public FinishTurn_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}
	
	public FinishTurn_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	

}

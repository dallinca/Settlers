package shared.communication.results.move;

public class BuyDevCard_Result {
	
	private boolean valid;

	public BuyDevCard_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}
	public BuyDevCard_Result(){
		valid = false;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	

}

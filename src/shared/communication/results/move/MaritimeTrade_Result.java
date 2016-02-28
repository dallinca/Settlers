package shared.communication.results.move;

public class MaritimeTrade_Result {
	
	private boolean valid;

	public MaritimeTrade_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}
	
	public MaritimeTrade_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	

}

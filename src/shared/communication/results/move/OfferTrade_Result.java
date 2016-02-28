package shared.communication.results.move;

public class OfferTrade_Result {
	
	private boolean valid;
	
	public OfferTrade_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}
	
	public OfferTrade_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}

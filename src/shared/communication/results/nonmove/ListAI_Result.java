package shared.communication.results.nonmove;

public class ListAI_Result {

	private boolean valid;

	public ListAI_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}

	public ListAI_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}

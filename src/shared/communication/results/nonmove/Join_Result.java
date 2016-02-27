package shared.communication.results.nonmove;

public class Join_Result {
	
	private boolean valid;
	private String response;
	
	public Join_Result(){
		
		valid = false;
		
	}
	
	public Join_Result(String doPost){
		System.out.println("Join game result string: "+doPost);
		if (doPost == null){
			setResponse(doPost);
			valid = false;
		}
		else if (doPost.equals("Success")){
			setResponse(doPost);
			valid = true;
		}		
	}
	
	public void setValid(boolean valid){
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}

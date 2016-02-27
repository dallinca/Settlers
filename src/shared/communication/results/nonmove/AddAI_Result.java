package shared.communication.results.nonmove;

public class AddAI_Result {
	
	private boolean valid;

	public AddAI_Result(String doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
	}

}

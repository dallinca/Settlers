package shared.communication.results.move;

public class BuildRoad_Result {
	
	private boolean valid;

	public BuildRoad_Result(Object doPost) {
		if (doPost.equals("Success")){
			valid = true;
		}
		else{
			valid = false;
		}
	}
	
	public BuildRoad_Result(){
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	

}

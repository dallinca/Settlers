package shared.communication.results.nonmove;

public class Join_Result {

	boolean joined = false;
	
	public Join_Result(boolean joined) {
		this.joined = joined;
	}
	
	public Join_Result() {}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	
	
}

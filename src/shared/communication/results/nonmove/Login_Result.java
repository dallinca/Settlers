package shared.communication.results.nonmove;

public class Login_Result {

	private boolean wasLoggedIn = false;
	
	// CONSTRUCTORS
	//////////////////////
	public Login_Result() {}


	// GETTERS AND SETTERS
	//////////////////////
	public boolean isWasLoggedIn() {
		return wasLoggedIn;
	}

	public void setWasLoggedIn(boolean wasLoggedIn) {
		this.wasLoggedIn = wasLoggedIn;
	}
}

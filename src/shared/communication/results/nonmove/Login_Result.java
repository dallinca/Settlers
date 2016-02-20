package shared.communication.results.nonmove;

public class Login_Result {

	private boolean wasLoggedIn = false;
	private int id = -1;
	private String name = "";
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

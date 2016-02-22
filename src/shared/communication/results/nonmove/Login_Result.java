package shared.communication.results.nonmove;

public class Login_Result {

	private boolean valid;
	private int playerID = -1;
	private String name = "";
	
	// CONSTRUCTORS
	//////////////////////
	public Login_Result() {
		valid=false;		
	}


	public Login_Result(String doPost, int playerID, String name) {
		if (doPost.equals("Success")){
			this.valid=true;
			this.playerID=playerID;
			this.name = name;
		}
		else {
			this.valid=false;
		}
	}
	
	public boolean isValid(){
		return valid;
	}

	// GETTERS AND SETTERS
	//////////////////////
	public boolean isWasLoggedIn() {
		return valid;
	}

	public void setWasLoggedIn(boolean wasLoggedIn) {
		this.valid = wasLoggedIn;
	}

	public int getId() {
		return playerID;
	}

	public void setId(int id) {
		this.playerID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

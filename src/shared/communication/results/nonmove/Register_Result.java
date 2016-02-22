package shared.communication.results.nonmove;

public class Register_Result {

	private boolean wasRegistered = false;
	private int id = -1;
	private String name = "";
	private boolean valid;

	// CONSTRUCTORS
	//////////////////////
	public Register_Result(boolean wasRegistered, int id, String name) {
		this.wasRegistered = wasRegistered;
		this.id = id;
		this.name = name;
	}


	public Register_Result() {
		valid = false;
	}

	public Register_Result(String doPost, int playerID, String username) {
		if (doPost.equals("Success")){
			valid = true;
			id = playerID;
			name = username;
		}
		else{
			valid = false;
		}
	}

	// GETTERS AND SETTERS
	//////////////////////

	public boolean isValid() {
		return valid;
	}


	public boolean isWasRegistered() {
		return wasRegistered;
	}

	public void setWasRegistered(boolean wasRegistered) {
		this.wasRegistered = wasRegistered;
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

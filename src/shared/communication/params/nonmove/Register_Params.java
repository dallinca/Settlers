package shared.communication.params.nonmove;

public class Register_Params {
	
	private String username;
	private String password;

	// CONSTRUCTORS
	//////////////////////
	public Register_Params(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// GETTERS AND SETTERS
	//////////////////////
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}

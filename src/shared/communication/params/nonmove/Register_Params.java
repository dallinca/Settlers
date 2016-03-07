package shared.communication.params.nonmove;

/**
 * 
 * Defines a register command for the server.
 */
public class Register_Params {
	
	private String username;
	private String password;

	public Register_Params(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

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

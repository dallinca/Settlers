package shared.communication.params.nonmove;

/**
 * 
 * Defines a login command for the server.
 * 
 *
 */
public class Login_Params {
	
	private String username;
	private String password;

	// CONSTRUCTORS
	//////////////////////
	public Login_Params(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Login_Params() {
	}

	// GETTERS AND SETTERS
	//////////////////////
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}

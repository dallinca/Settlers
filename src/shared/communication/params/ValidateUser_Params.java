package shared.communication.params;

/**
 * Input for validate user.
 * Consists of a password and a username.
 * @author jchrisw
 *
 */

public class ValidateUser_Params {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ValidateUser_Params(String name, String password) {
		super();
		this.username = name;
		this.password = password;
	}
	

}

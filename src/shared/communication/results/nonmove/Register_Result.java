package shared.communication.results.nonmove;

/**
 * 
 * Response object from register command.
 *
 */
public class Register_Result {

	private int id = -1;
	private String name;
	private boolean valid;
	private String userCookie;

	// CONSTRUCTORS
	//////////////////////
	public Register_Result(boolean valid, int id, String name) {
		this.valid = valid;
		this.id = id;
		this.name = name;
	}

	public Register_Result() {
		valid = false;
	}

	public Register_Result(String doPost, int playerID, String username) {
		if (doPost==null){
			valid = false;
			return;
		}
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

	public void setValid(boolean v){
		valid = v;
		return;
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
	
	public void setUserCookie(String cookie){
		userCookie = cookie;
	}

	public String getUserCookie() {
		return userCookie;
	}




}

package shared.communication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * A data storage object for user information.
 *
 */
public class User {
//Encoded cookie value
	private String name;
	private String password;
	private int playerID;
		
	public User(String name, String password, int playerID) {
		super();
		this.name = name;
		this.password = password;
		this.playerID = playerID;
	}
	
	/**
	 * Constructs a user from a catan cookie object.
	 * @param first
	 * @throws UnsupportedEncodingException 
	 */
	/*public User(String userCookie) throws UnsupportedEncodingException {
		Gson gson = new Gson();
		// TODO Auto-generated constructor stub
		
		
		String decodedUserData = URLDecoder.decode(userCookie, "UTF-8");				
		//System.out.println("Decoded Cookie: "+decodedUserData);				
		JsonObject jobj = gson.fromJson(decodedUserData, JsonObject.class);
		
		playerID = Integer.parseInt(jobj.get("playerID").toString());		
		password = jobj.get("password").toString();
		name = jobj.get("name").toString();
		
	}*/

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + playerID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (playerID != other.playerID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", playerID="
				+ playerID + "]";
	}
	
	
}

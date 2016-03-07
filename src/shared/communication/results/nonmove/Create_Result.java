package shared.communication.results.nonmove;

import com.google.gson.JsonObject;

/**
 * Result communication response from server from a create command.
 *
 */

public class Create_Result {
	
	private String title;
	private int ID;
	private boolean valid;

	public Create_Result(JsonObject json) {		
		//{"title":"TestGame","id":3,"players":[{},{},{},{}]}
		
		setTitle(json.get("title").toString());
		setID(json.get("id").getAsInt());		
		valid = true;
	}

	public Create_Result() {
		setValid(false);
	}

	public Create_Result(String doPost) {
		System.out.println("Create game result.");
		System.out.println(doPost);
		
		
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

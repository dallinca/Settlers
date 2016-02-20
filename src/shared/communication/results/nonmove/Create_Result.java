package shared.communication.results.nonmove;

import com.google.gson.JsonElement;

public class Create_Result {
	
	JsonElement json;
	private boolean valid;

	public Create_Result(JsonElement json) {
		this.json = json;
		setValid(true);
	}

	public Create_Result() {
		setValid(false);
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}

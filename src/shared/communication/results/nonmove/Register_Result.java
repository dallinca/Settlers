package shared.communication.results.nonmove;

public class Register_Result {
	
	private boolean wasRegistered = false;
	private int id = -1;
	private String name = "";
	
	// CONSTRUCTORS
	//////////////////////
	public Register_Result(boolean wasRegistered, int id, String name) {
		this.wasRegistered = wasRegistered;
		this.id = id;
		this.name = name;
	}
	
	
	public Register_Result() {}

	
	// GETTERS AND SETTERS
	//////////////////////
	
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

package shared.communication.params.nonmove;

/**
 * Defines a get current game version command for the server.
 */
public class GetVersion_Params {
	
	private int versionNumber;

	public GetVersion_Params(int versionNumber) {
		this.setVersionNumber(versionNumber);
	}
	
	public GetVersion_Params() {
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

}

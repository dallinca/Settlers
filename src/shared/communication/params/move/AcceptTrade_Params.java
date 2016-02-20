package shared.communication.params.move;

public class AcceptTrade_Params {
	
	private boolean willAccept;

	public AcceptTrade_Params(boolean willAccept) {
		this.willAccept = willAccept;
	}

	public boolean isWillAccept() {
		return willAccept;
	}

	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}

}

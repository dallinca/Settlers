package client.map;

public interface MapState {
	
	public void initFromModel();
	public int getTurnNumber();
	public void getLocation();
	public void placePiece();
	public boolean canDoPlacePiece();
	public int getRound();
	public boolean canDoClickButton();
	public MapState getState();

}

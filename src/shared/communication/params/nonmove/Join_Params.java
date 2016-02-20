package shared.communication.params.nonmove;

import shared.definitions.CatanColor;

public class Join_Params {

	private String color;
	private int gameID;
	
	public Join_Params(int gameID, CatanColor color) {
		this.gameID = gameID;
		
		switch (color) {
		
		case ORANGE: this.color = "orange";
		case YELLOW: this.color = "yellow";
		case BLUE: this.color = "blue";
		case GREEN: this.color = "green";
		case PURPLE: this.color = "pruple";
		case PUCE: this.color = "puce";
		case WHITE: this.color = "white";
		case BROWN: this.color = "brown";
		default: //Do nothing, it can't be default.
		
		}
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	

}

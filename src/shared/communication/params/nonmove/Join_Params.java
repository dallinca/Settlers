package shared.communication.params.nonmove;

import shared.definitions.CatanColor;

public class Join_Params {

	private String color;
	private int gameID;

	public Join_Params(int gameID, CatanColor color) {
		this.gameID = gameID;

		switch (color) {

		case ORANGE: this.color = "orange";
		break;
		
		case YELLOW: this.color = "yellow";
		break;
		
		case BLUE: this.color = "blue";
		break;
		
		case GREEN: this.color = "green";
		break;
		
		case PURPLE: this.color = "pruple";
		break;
		
		case PUCE: this.color = "puce";
		break;
		
		case WHITE: this.color = "white";
		break;
		
		case BROWN: this.color = "brown";
		break;
		
		default: //Do nothing, it can't be default.
			break;

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

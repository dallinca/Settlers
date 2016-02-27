package shared.communication.params.nonmove;

import shared.definitions.CatanColor;

public class Join_Params {

	private int id;
	private String color;

	/**
	 * 
	 * @param gameID
	 * @param color
	 */
	public Join_Params(int gameID, CatanColor color) {
		this.id = gameID;

		switch (color) {

		case RED: this.color = "red";
		break;

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

	public Join_Params(){

	}
/*
	public int getID() {
		return id;
	}

	public void setID(int gameID) {
		this.id = gameID;
	}*/

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}



}

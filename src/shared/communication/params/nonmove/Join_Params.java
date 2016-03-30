package shared.communication.params.nonmove;

import shared.definitions.CatanColor;

/**
 * Defines a join game command for the server.
 * 
 */
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

		case PURPLE: this.color = "purple";
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

	public int getGameID() {
		return id;
	}

	public void setGameID(int gameID) {
		this.id = gameID;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public CatanColor convertColor() {
		
		if (color.equals("red")){
			return CatanColor.RED;
		}else if (color.equals("orange")){
			return CatanColor.ORANGE;
		}else if (color.equals("yellow")){
			return CatanColor.YELLOW;
		}
		else if (color.equals("blue")){
			return CatanColor.BLUE;
		}
		else if (color.equals("purple")){
			return CatanColor.PURPLE;
		}
		else if (color.equals("puce")){
			return CatanColor.PUCE;
		}
		else if (color.equals("white")){
			return CatanColor.WHITE;
		}
		else if (color.equals("brown")){
			return CatanColor.BROWN;
		}
		else if (color.equals("green")){
			return CatanColor.GREEN;
		}
		return null;
	}
}

package shared.communication.params.move.data;

import shared.locations.EdgeLocation;

public class BorderSpot {

	private int x;
	private int y;
	private String direction;


	public BorderSpot(EdgeLocation border) {
		this.x = border.getHexLoc().getX();
		this.y = border.getHexLoc().getY();

		border.getDir();

		switch (border.getDir()){
		
		case NorthWest: direction = "\"NW\"";
		break;
		
		case North: direction = "\"N\"";
		break;
		
		case NorthEast: direction = "\"NE\"";
		break;
		
		case SouthEast: direction = "\"SE\"";
		break;
		
		case South: direction = "\"S\"";
		break;
		
		case SouthWest: direction = "\"SW\"";
		break;
		
		default: //Do nothing, doesn't happen.
			break;

		}

		this.direction = border.getDir().toString();
	}


	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}


}

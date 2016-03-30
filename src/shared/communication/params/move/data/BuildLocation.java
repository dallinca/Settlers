package shared.communication.params.move.data;

import shared.locations.VertexLocation;

/**
 * 
 * Defines a vertex location for JSON serialization.
 * 
 */
public class BuildLocation {

	private int x;
	private int y;
	private String direction;

	public BuildLocation(VertexLocation location){

		x = location.getHexLoc().getX();
		y = location.getHexLoc().getY();
		
		switch (location.getDir()){

		case NorthWest: direction = "NW";
		break;

		case NorthEast: direction = "NE";
		break;

		case SouthEast: direction = "SE";
		break;
		case SouthWest: direction = "SW";
		break;

		default: //Do nothing, doesn't happen.
			break;
		}
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

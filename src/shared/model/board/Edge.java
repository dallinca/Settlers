package shared.model.board;

import shared.locations.EdgeDirection;
import shared.model.items.Road;

/**
 * The Edge class is used to store whether an edge has a road owned by a player
 * 
 * Operations are provided for placing a road on the edge and detecting if the edge has a
 * road.
 * 
 *
 */
public class Edge {
	

	Road road;

	private Board board;
	private int x_coord_edg;
	private int y_coord_edg;
	private ModEdgeDirection direction_edg;
	
	/**
	 * Initialize instance of Edge. Should only be called by the Board Class.
	 * 
	 * @param board
	 * @param x_coord_edg
	 * @param y_coord_edg
	 * @param direction_edg
	 * 
	 * @pre board != null
	 * @pre direction_edg != null 
	 * 
	 * @post New edge is instantiated
	 */
	public Edge(Board board, int x_coord_edg, int y_coord_edg, ModEdgeDirection direction_edg) {
		this.board = board;
		this.x_coord_edg = x_coord_edg;
		this.y_coord_edg = y_coord_edg;
		this.direction_edg = direction_edg;
	}
	
	/**
	 * Returns an array of the sides of the Edge as defined by EdgeSide.
	 * 
	 * @pre None
	 * 
	 * @return An array of EdgeSide size 2 with the sides of this instance of Edge
	 */
	public EdgeSide[] getSides() {
		// the number of sides of an Edge is always 2
		EdgeSide[] sides = new EdgeSide[2];
		
		// If We are looking at a Left Edge
		if(direction_edg == ModEdgeDirection.LEFT) {
			// Left Side of Edge
			sides[0] = new EdgeSide(
					board.getVertex( (2 * x_coord_edg) - 2, 2 * y_coord_edg),
					board.getEdge(x_coord_edg - 1, y_coord_edg + 1, ModEdgeDirection.UP),
					board.getEdge(x_coord_edg - 1, y_coord_edg + 1, ModEdgeDirection.RIGHT)
					);
			// Right Side of Edge
			sides[1] = new EdgeSide(
					board.getVertex( (2 * x_coord_edg) - 1, (2 * y_coord_edg) - 1),
					board.getEdge(x_coord_edg, y_coord_edg, ModEdgeDirection.UP),
					board.getEdge(x_coord_edg - 1, y_coord_edg, ModEdgeDirection.RIGHT)
					);
		}
		// If We are looking at an Upper Edge
		else if(direction_edg == ModEdgeDirection.UP) {
			// Left Side of Edge
			sides[0] = new EdgeSide(
					board.getVertex( (2 * x_coord_edg) - 1, (2 * y_coord_edg) - 1),
					board.getEdge(x_coord_edg, y_coord_edg, ModEdgeDirection.LEFT),
					board.getEdge(x_coord_edg - 1, y_coord_edg, ModEdgeDirection.RIGHT)
					);
			// Right Side of Edge
			sides[1] = new EdgeSide(
					board.getVertex( 2 * x_coord_edg, (2 * y_coord_edg) - 2),
					board.getEdge(x_coord_edg, y_coord_edg, ModEdgeDirection.RIGHT),
					board.getEdge(x_coord_edg + 1, y_coord_edg - 1, ModEdgeDirection.LEFT)
					);
		}
		// If we are looking at a Right Edge
		else {
			// Left Side of Edge
			sides[0] = new EdgeSide(
					board.getVertex( 2 * x_coord_edg, (2 * y_coord_edg) - 2),
					board.getEdge(x_coord_edg, y_coord_edg, ModEdgeDirection.UP),
					board.getEdge(x_coord_edg + 1, y_coord_edg - 1, ModEdgeDirection.LEFT)
					);
			// Right Side of Edge
			sides[1] = new EdgeSide(
					board.getVertex( (2 * x_coord_edg) + 1, (2 * y_coord_edg) - 1),
					board.getEdge(x_coord_edg + 1, y_coord_edg, ModEdgeDirection.LEFT),
					board.getEdge(x_coord_edg + 1, y_coord_edg, ModEdgeDirection.UP)
					);
		}
		return sides;
	}
	
	/**
	 * Returns whether the Edge has a road
	 * 
	 * @pre None
	 * 
	 * @post Return value says whether the edge has a road
	 */
	public boolean hasRoad() {
		if(road == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Assigns the edge a road
	 * 
	 * @pre road == null, this check should happen at the board level
	 * 
	 * @post road = (Road)Object, the edge now has a player's road
	 */
	public void buildRoad(Road road) {
		this.road = road;
	}

	public Road getRoad() {
		return road;
	}

	public int getX_coord_edg() {
		return x_coord_edg;
	}

	public int getY_coord_edg() {
		return y_coord_edg;
	}

	public int getTheirX_coord() {
		return x_coord_edg - 3;
	}

	public int getTheirY_coord() {
		return y_coord_edg - 3;
	}

	public ModEdgeDirection getDirection_edg() {
		return direction_edg;
	}
	
	public EdgeDirection getTheirEdgeDirection() {
		if(direction_edg == ModEdgeDirection.LEFT) {
			return EdgeDirection.NorthWest;
		} else if(direction_edg == ModEdgeDirection.RIGHT) {
			return EdgeDirection.NorthEast;
		} else if(direction_edg == ModEdgeDirection.UP) {
			return EdgeDirection.North;
		}
		return null;
	}
	
	
	
}

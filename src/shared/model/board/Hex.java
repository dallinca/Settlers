package shared.model.board;

import shared.definitions.HexType;
import shared.definitions.ResourceType;

/**
 * The Hex class is used to store what kind of resource can be gained
 * through it, what roll value is necessary to gain the resources, and
 * whether the resources will be stolen by the robber
 * 
 * Operations are provided for
 * (1) checking for the robber, and placing him or taking him away from the hex <br
 * (2) Getting and setting the rollValue<br>
 * (3) Getting and setting the hexType<br>
 * (4) Retrieving the Hex location
 * 
 *
 */
public class Hex {

	private boolean hasRobber;
	private HexType hexType;
	private int rollValue;

	private Board board;
	private int x_coord_hex;
	private int y_coord_hex;

	public Hex(Board board, int x_coord_hex, int y_coord_hex, HexType hexType, int rollValue) {
		this.board = board;
		this.x_coord_hex = x_coord_hex;
		this.y_coord_hex = y_coord_hex;
		this.hexType = hexType;
		this.rollValue = rollValue;
	}
	

	public Hex(int x_coord_hex, int y_coord_hex, HexType hexType, int rollValue, boolean hasRobber) {
		this.board = board;
		this.x_coord_hex = x_coord_hex;
		this.y_coord_hex = y_coord_hex;
		this.hexType = hexType;
		this.rollValue = rollValue;
		this.hasRobber = hasRobber;
	}
	
	/**
	 * Retrieve the 6 adjacent Vertices to the Hex
	 * 
	 * @pre Valid Hex on the Map
	 * @return Vertex[6]
	 */
	public Vertex[] getAdjacentVertices() {
		Vertex[] adjacentVertices = new Vertex[6];
		adjacentVertices[0] = board.getVertex( (x_coord_hex * 2) - 1, 	(y_coord_hex * 2) - 1); // Top Left Vertex
		adjacentVertices[1] = board.getVertex(  x_coord_hex * 2, 		(y_coord_hex * 2) - 2); // Top Right Vertex
		adjacentVertices[2] = board.getVertex( (x_coord_hex * 2) + 1, 	(y_coord_hex * 2) - 1); // Right Vertex
		adjacentVertices[3] = board.getVertex(  x_coord_hex * 2, 		y_coord_hex * 2); // Bottom Right Vertex
		adjacentVertices[4] = board.getVertex( (x_coord_hex * 2) - 1, 	(y_coord_hex * 2) + 1); // Bottom Left Vertex
		adjacentVertices[5] = board.getVertex( (x_coord_hex * 2) - 2, 	y_coord_hex * 2); // Left Vertex
		return adjacentVertices;
	}
	
	/**
	 * Looks at the HexType of this hex and converts it to ResourceType, and 
	 * return the ResourceType for collection purposes
	 * @throws Exception 
	 * 
	 * @pre Hex has HexType
	 * 
	 * @post return value contains the Resource type that can be collected from
	 * this Hex, returns null if there is no resource to be collected from this Hex
	 */
	public ResourceType getHexResourceType() throws Exception {
		// If the Hex has no hex type, bail early
		if(hexType == null) {
			throw new Exception("Cannot determine the ResourceType of a hexx that does not have a HexType");
		}
		
		// Check all cases for HexTypes
		if(hexType == HexType.BRICK) {
			return ResourceType.BRICK;
		} else if(hexType == HexType.ORE) {
			return ResourceType.ORE;
		} else if(hexType == HexType.SHEEP) {
			return ResourceType.SHEEP;
		} else if(hexType == HexType.WHEAT) {
			return ResourceType.WHEAT;
		} else if(hexType == HexType.WOOD) {
			return ResourceType.WOOD;
		} else {
			return null;
		}
	}
	/**
	 * Returns whether the Hex currently has the robber
	 * 
	 * @pre None
	 * 
	 * @post Return value says whether the hex currently has the robber
	 */
	public boolean checkIfHasRobber() {
		return hasRobber;
	}
	
	/**
	 * Assigns the hex the robber
	 * 
	 * @pre hasRobber == false, this check should happen at the board level
	 * 
	 * @post hasRobber = true
	 */
	public void giveRobber() {
		hasRobber = true;
	}
	
	/**
	 * Take the robber from the hex
	 * 
	 * @pre hasRobber == true, this check should happen at the board level
	 * 
	 * @post hasRobber = false
	 */
	public void takeRobber() {
		hasRobber = false;
	}

	public HexType getHexType() {
		return hexType;
	}

	public void setHexType(HexType hexType) {
		this.hexType = hexType;
	}

	public int getRollValue() {
		return rollValue;
	}

	/**
	 * Set the RollValue of the hex to compare against die roll results
	 * 
	 * @pre 1 <= rollValue <= 11
	 * 
	 * @post rollValue holds the integer value to compare against die roll results
	 */
	public void setRollValue(int rollValue) {
		this.rollValue = rollValue;
	}

	public int getX_coord_hex() {
		return x_coord_hex;
	}

	public int getY_coord_hex() {
		return y_coord_hex;
	}
	
	/**
	 * For use when retrieving the coordinate in the Gui
	 * 
	 * @pre none
	 * @return
	 */
	public int getTheirX_coord_hex() {
		return x_coord_hex - 3;
	}

	/**
	 * For use when retrieving the coordinate in the Gui
	 * 
	 * @pre none
	 * @return
	 */
	public int getTheirY_coord_hex() {
		return y_coord_hex - 3;
	}


	public Board getBoard() {
		return board;
	}


	public void setBoard(Board board) {
		this.board = board;
	}
	
	
	
	
	
}

package shared.model.board;

import shared.definitions.HexType;
import shared.locations.HexLocation;

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
	private HexLocation location;
	private HexType hexType;
	private int rollValue;

	public Hex() {}
	
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
	
	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
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
	
	
	
	
}

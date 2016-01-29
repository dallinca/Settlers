package shared.model.turn;

/**
 * All action methods associated with making purchases.
 *
 */
public class Purchase {

	
	/**
	 * Purchases a development card for the player.
	 * 
	 * @pre Player must have prerequisite resources, and the development deck must not be empty.
	 * @post Player gains a development card from the development deck, and loses cost.
	 */
	public static void purchaseDevelopmentCard(){

	}

	/**
	 * Purchases a settlement for the player and places it on the board.
	 * 
	 * @pre Player must have prerequisite resources. Player must have access to legal build location. 
	 * Player must have a settlement available to build.
	 * @post Player places settlement on the board, loses cost.
	 */
	public static void purchaseSettlement(){

	}

	/**
	 * Replaces a settlement for a city on the board.
	 * 
	 * @pre Player must have prerequisite resources. Player must have settlement on the board. 
	 * Player must have a city available to build
	 * @post Player replaces a settlement with a city. player loses cost. Player gains available settlement.
	 */
	public static void purchaseCity(){

	}

	/**
	 * Purchases a road for the player and places it on the board.
	 * 
	 * @pre Player must have prerequisite resources. Player must have access to legal build location.
	 * Player must have a road available to build.
	 * @post Player places road on the board, loses cost.
	 */
	public static void purchaseRoad(){

	}

}


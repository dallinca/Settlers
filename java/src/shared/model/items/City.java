package shared.model.items;

import shared.model.player.Player;

/**
 * Class stores the information about where cities are stored
 * 
 * @author scottgv
 *
 */
public class City extends Municipal {

	
	/**
	 * Initializes City
	 * 
	 * @pre A Player object exists
	 * 
	 */
	public City(Player player){
		setPlayer(player);
	}
	
	public void hexesCityIsOn(){}

}

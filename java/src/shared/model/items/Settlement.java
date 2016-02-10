package shared.model.items;

import shared.model.player.Player;

/**
 * Class stores the information about where cities are stored
 * 
 * @author scottgv
 *
 */
public class Settlement extends Municipal {
	
	/**
	 * Initializes Settlement
	 * 
	 * @pre A Player object exists
	 * 
	 */
	public Settlement (Player player){
		setPlayer(player);
	}
	
	public void hexesSettlementIsOn(){
		
	}
	
}

package shared.model.turn;

import client.Client;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.Game;

/**
 * All action methods associated with making purchases.
 *
 */
public class Purchase {

	private Client client;
	
	Purchase(Client client) {
		this.client = client;
	}
	/**
	 * Purchases a development card for the player.
	 * 
	 * @pre Player must have prerequisite resources, and the development deck must not be empty.
	 * @post Player gains a development card from the development deck, and loses cost.
	 */
	public void purchaseDevelopmentCard(){

	}

	public void canDoPurchaseDevelopmentCard(){
		client.getGame().canDoCurrentPlayerBuyDevelopmentCard(client.getUserId());
	}

	/**
	 * Purchases a settlement for the player and places it on the board.
	 * @param location 
	 * 
	 * @pre Player must have prerequisite resources. Player must have access to legal build location. 
	 * Player must have a settlement available to build.
	 * @post Player places settlement on the board, loses cost.
	 */
	public void purchaseSettlement(Object l){
		VertexLocation location = (VertexLocation) l;
		
	}
	

	public void canDoPurchaseSettlement(){
	}

	/**
	 * Replaces a settlement for a city on the board.
	 * @param location 
	 * 
	 * @pre Player must have prerequisite resources. Player must have settlement on the board. 
	 * Player must have a city available to build
	 * @post Player replaces a settlement with a city. player loses cost. Player gains available settlement.
	 */
	public void purchaseCity(Object l){
		VertexLocation location = (VertexLocation) l;
	}

	public void canDoPurchaseCity(){

	}
	
	/**
	 * Purchases a road for the player and places it on the board.
	 * @param location 
	 * 
	 * @pre Player must have prerequisite resources. Player must have access to legal build location.
	 * Player must have a road available to build.
	 * @post Player places road on the board, loses cost.
	 */
	public void purchaseRoad(Object l){
		EdgeLocation location = (EdgeLocation) l;
		//if(client.getUserID()==client.getCurrentPlayerID()){
	//}
		///player.canDoPlaceRoad???
		//Player.placeRoad
		//ClientFacade.placeRoadRequest
		//
		//
		//
		//

	}
	
	public void canDoPurchaseRoad(){

	}

}


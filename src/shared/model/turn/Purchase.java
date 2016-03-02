package shared.model.turn;

import client.Client;
import client.ClientException;
import client.ClientFacade;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.Game;

/**
 * All action methods associated with making purchases.
 *
 */
public class Purchase {


	
	Purchase() {
		System.out.println("Purchase Purchase()");

	}
	/**
	 * Purchases a development card for the player.
	 * 
	 * @pre Player must have prerequisite resources, and the development deck must not be empty.
	 * @post Player gains a development card from the development deck, and loses cost.
	 */
	public void purchaseDevelopmentCard(){
		System.out.println("Purchase purchaseCity()");
		if (canDoPurchaseDevelopmentCard()) {
			ClientFacade.getInstance().buyDevCard();
		}
	}

	public boolean canDoPurchaseDevelopmentCard(){
		System.out.println("Purchase canDoPurchaseDevelopmentCard()");
		return Client.getInstance().getGame().canDoPlayerBuyDevelopmentCard(Client.getInstance().getUserId());
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
		System.out.println("Purchase purchaseSettlement()");
		VertexLocation location = (VertexLocation) l;
		ClientFacade.getInstance().buildSettlement(location);
	}
	

	public boolean canDoPurchaseSettlement(){
		System.out.println("Purchase canDoPurchaseSettlement()");
		return Client.getInstance().getGame().canDoPlayerBuildSettlement(Client.getInstance().getUserId());
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
		System.out.println("Purchase purchaseCity()");
		if (canDoPurchaseCity()) {
			VertexLocation location = (VertexLocation) l;
		}
	}

	public boolean canDoPurchaseCity(){
		System.out.println("Purchase canDoPurchaseCity()");
		return Client.getInstance().getGame().canDoPlayerBuildCity(Client.getInstance().getUserId());
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
		System.out.println("Purchase purchaseRoad()");
		//if (canDoPurchaseRoad()) {
			ClientFacade.getInstance().buildRoad((EdgeLocation)l );
			System.out.println("Purchase purchaseRoad() Could Purchase");
			return;
		//}
		//System.out.println("Purchase purchaseRoad() Could Not Purchase");
		//return;
	}
	
	public boolean canDoPurchaseRoad(){
		System.out.println("Purchase canDoPurchaseRoad()");
		return Client.getInstance().getGame().canDoPlayerBuildRoad(Client.getInstance().getUserId());
	}

}


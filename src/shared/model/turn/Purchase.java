package shared.model.turn;

import client.Client;
import client.ClientException;
import client.ClientFacade;
import shared.definitions.ResourceType;
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
		
		Game g = Client.getInstance().getGame();
		int ID = Client.getInstance().getUserId();
		
		if (!g.canDoPlayerBuyDevelopmentCard(ID)){
			return false;
		}
		
		int ore = g.getCurrentPlayer().getNumberResourcesOfType(ResourceType.ORE);
		int sheep = g.getCurrentPlayer().getNumberResourcesOfType(ResourceType.SHEEP);
		int wheat = g.getCurrentPlayer().getNumberResourcesOfType(ResourceType.WHEAT);
		
		if (ore>0&&sheep>0&&wheat>0){
			return true;
		}
		else{
			return false;
		}
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
	

	public boolean canDoPurchaseSettlement(Object l){
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
		if (canDoPurchaseCity(l)) {
			VertexLocation location = (VertexLocation) l;
			ClientFacade.getInstance().buildCity(location);
		}
	}

	public boolean canDoPurchaseCity(Object l){
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
		Game game = Client.getInstance().getGame();
		System.out.println("Purchase purchaseRoad()");
		if (canDoPurchaseRoad(l)) {
			ClientFacade.getInstance().buildRoad((EdgeLocation)l );
			System.out.println("Purchase purchaseRoad() Could Purchase");
			return;
		}
		System.out.println("Purchase purchaseRoad() Could Not Purchase");
		return;
	}
	
	public boolean canDoPurchaseRoad(Object l){
		System.out.println("Purchase canDoPurchaseRoad()");
		return Client.getInstance().getGame().canDoPlayerBuildRoad(Client.getInstance().getUserId());
	}

}


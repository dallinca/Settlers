package shared.model.turn;

import client.Client;
import client.ClientFacade;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.Game;


/**
 * All action methods associated with playing development cards.
 *
 */
public class PlayCard {


	
	PlayCard() {
		System.out.println("PlayCard PlayCard()");
	}
	
	/**
	 * Plays an monument development card.
	 * @throws Exception 
	 * 
	 * @pre Player must have a monument development card.
	 * @post Player takes associated action.
	 */
	public void playMonument() throws Exception{
		System.out.println("PlayCard playMonument()");
		if (canDoPlayMonument()) {	
			try {
				ClientFacade.getInstance().playMonument();
				//Client.getInstance().getGame().useDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONUMENT);
			} catch (Exception e) {
				System.out.println("There was an Error in playing a Monument Card");
			}
		} else {
			throw new Exception("You should not have been authorized to call playMonument");
		}
	}
	/**
	 * Checks and sees if the player can play a monument card.
	 * @return whether or not they have a monument that can be played
	 */
	public boolean canDoPlayMonument() {
		System.out.println("PlayCard canDoPlayMonument()");
		return Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONUMENT);
		
	}
	
	/**
	 * Plays an army development card.
	 * @throws Exception 
	 * 
	 * @pre Player must have an army development card.
	 * @post Player takes associated action.
	 */
	public void playKnight(HexLocation hex, int victimIndex) throws Exception{
		System.out.println("PlayCard playKnight()");
		if (canDoPlayKnight()) {
			try {
				ClientFacade.getInstance().playSoldier(hex, victimIndex);
				//Client.getInstance().getGame().useDevelopmentCard(Client.getInstance().getUserId(), DevCardType.SOLDIER);
			} catch (Exception e) {
				System.out.println("Something went wrong while trying to play a Soldier Card.");
			}
		} else {
			throw new Exception("You should not have been authorized to call playKnight");
		}
	}
	
	public boolean canDoPlayKnight() {
		System.out.println("PlayCard canDoPlayKnight()");
		return Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.SOLDIER);
	}

	/**
	 * Plays a year of plenty development card.
	 * @throws Exception 
	 * 
	 * @pre Player must have year of plenty development card.
	 * @post Player takes associated action.
	 */
	public void playYearOfPlenty(ResourceType[] toPassIn) throws Exception{
		System.out.println("PlayCard playYearOfPlenty()");
		if (canDoPlayYearOfPlenty(toPassIn)) {
			
			ResourceType resource1 = null;
			ResourceType resource2 = null;
			
			if (toPassIn.length == 1) {
				resource1 = toPassIn[0];
				resource2 = resource1;
			} else if (toPassIn.length == 2) {
				resource1 = toPassIn[0];
				resource2 = toPassIn[1];
			} else throw new Exception("Array Too large or non-existant!");
			
			try {
				ClientFacade.getInstance().playYearOfPlenty(resource1, resource2);
				//We do not need to update the model if Facade is called...
				//Client.getInstance().getGame().useDevelopmentCard(Client.getInstance().getUserId(), DevCardType.YEAR_OF_PLENTY, toPassIn);
			} catch (Exception e) {
				System.out.println("Something went wrong while trying to play Year of Plenty Card.");
			}
		} else {
			throw new Exception("You should not have been authorized to call playYearOfPlenty.");
		}
	}
	
	public boolean canDoPlayYearOfPlenty(ResourceType[] toPassIn) {
		System.out.println("PlayCard canDoPlayYearOfPlenty()");
		return Client.getInstance().getGame().canDoPlayerUseYearOfPlenty(toPassIn, Client.getInstance().getUserId());
	}
	
	/**
	 * Plays a monopoly card.
	 * @throws Exception 
	 * 
	 * @pre Player must have monopoly development card.
	 * @post Player takes associated action.
	 */
	public void playMonopoly(ResourceType[] toPassIn) throws Exception{
		System.out.println("PlayCard playMonopoly()");
		if (canDoPlayMonopoly(toPassIn)) {
			try {
				//Array size is only 1 and will only ever be 1.
				ClientFacade.getInstance().playMonopoly(toPassIn[0]);
				//Client.getInstance().getGame().useDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONOPOLY, toPassIn);
			} catch (Exception e) {
				System.out.println("Something went wrong when trying to play a Monopoly Card");
			}
		} else {
			throw new Exception("You should not have been authorized to call playMonopoly");
		}
	}
	
	public boolean canDoPlayMonopoly(ResourceType[] toPassIn) {		
		System.out.println("PlayCard canDoPlayMonopoly()");
		return Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONOPOLY);
	}

	/**
	 * Plays a build roads card.
	 * @throws Exception 
	 * 
	 * @pre Player must have build roads development card.
	 * @post Player takes associated action.
	 */
	public void playBuildRoads(EdgeLocation roadLocation1, EdgeLocation roadLocation2) throws Exception{
		System.out.println("PlayCard playBuildRoads()");
		if (canDoPlayBuildRoads()) {
			try {
				ClientFacade.getInstance().playRoadBuilding(roadLocation1, roadLocation2);
				//Client.getInstance().getGame().useDevelopmentCard(Client.getInstance().getUserId(), DevCardType.ROAD_BUILD);
			} catch (Exception e) {
				System.out.println("Something went wrong when trying to play a Road Builder card.");
			} 
		} else {
			throw new Exception("You should not have been authorized to call playBuildRoads");
		}
	}
	
	public boolean canDoPlayBuildRoads() {
		System.out.println("PlayCard canDoPlayBuildRoads()");
		return Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.ROAD_BUILD);
	}
	
}

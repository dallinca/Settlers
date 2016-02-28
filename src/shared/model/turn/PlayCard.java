package shared.model.turn;

import client.Client;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Game;


/**
 * All action methods associated with playing development cards.
 *
 */
public class PlayCard {


	
	PlayCard() {

	}
	
	/**
	 * Plays an monument development card.
	 * 
	 * @pre Player must have a monument development card.
	 * @post Player takes associated action.
	 */
	public void playMonument(){

	}
	/**
	 * Checks and sees if the player can play a monument card.
	 * @return whether or not they have a monument that can be played
	 */
	public boolean canDoPlayMonument() {
		Client.getInstance().getGame().canDoCurrentPlayerBuyDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONUMENT);
		
	}
	
	/**
	 * Plays an army development card.
	 * 
	 * @pre Player must have an army development card.
	 * @post Player takes associated action.
	 */
	public void playKnight(){

	}
	
	public boolean canDoPlayKnight() {
		return Client.getInstance().getGame().canDoCurrentPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.SOLDIER);
	}

	/**
	 * Plays a year of plenty development card.
	 * 
	 * @pre Player must have year of plenty development card.
	 * @post Player takes associated action.
	 */
	public void playYearOfPlenty(ResourceType[] toPassIn){

	}
	
	public boolean canDoPlayYearOfPlenty(ResourceType[] toPassIn) {
		return Client.getInstance().getGame().canDoCurrentPlayerUseYearOfPlenty(toPassIn, Client.getInstance().getUserId());
	}
	
	/**
	 * Plays a monopoly card.
	 * 
	 * @pre Player must have monopoly development card.
	 * @post Player takes associated action.
	 */
	public void playMonopoly(ResourceType[] toPassIn){

	}
	
	public boolean canDoPlayMonopoly(ResourceType[] toPassIn) {		
		return Client.getInstance().getGame().canDoCurrentPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONOPOLY);
	}

	/**
	 * Plays a build roads card.
	 * 
	 * @pre Player must have build roads development card.
	 * @post Player takes associated action.
	 */
	public void playBuildRoads(){

	}
	
	public boolean canDoPlayBuildRoads() {
		
	}
	
}

package server.facade;

import java.util.ArrayList;
import java.util.List;

import shared.model.Game;

/**
 * TODO -- Javadoc
 * 
 * @author Dallin
 *
 */
public interface IServerFacade {
	
	List<Game> liveGames = new ArrayList<Game>();

	public boolean acceptTrade();
	public boolean buildCity();
	public boolean buildRoad();
	public boolean buildSettlement();
	public boolean buyDevCard();
	public boolean discardCards();
	public boolean finishTurn();
	public boolean maritimeTrade();
	public boolean offerTrade();
	public boolean robPlayer();
	public boolean rollNumber();
	public boolean sendChat();
	
	// Dev Card play actions
	public boolean playMonopoly();
	public boolean playMonument();
	public boolean playRoadBuilding();
	public boolean playSoldier();
	public boolean playYearOfPlenty();
	
	// Return the game that the command is meant to operate on
	Game findGameForCommand(); 
	
}

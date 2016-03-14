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

	// TODO determine what the facade functions need to return for the handlers to package
	
	
	// Command pattern actions (move actions)
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
	public boolean playMonopoly();
	public boolean playMonument();
	public boolean playRoadBuilding();
	public boolean playSoldier();
	public boolean playYearOfPlenty();
	
	// Non Command pattern actions (nonmove actions)
	public boolean login();
	public boolean register();
	public boolean list();
	public boolean create();
	public boolean join();
	public boolean model();
	public boolean addAI();
	public boolean listAI();
	
	// Return the game that the command is meant to operate on
	Game findGameForCommand(); 
	
}

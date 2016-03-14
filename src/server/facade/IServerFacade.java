package server.facade;

import java.util.ArrayList;
import java.util.List;

import server.commands.Command;
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
	public boolean acceptTrade(Command command);
	public boolean buildCity(Command command);
	public boolean buildRoad(Command command);
	public boolean buildSettlement(Command command);
	public boolean buyDevCard(Command command);
	public boolean discardCards(Command command);
	public boolean finishTurn(Command command);
	public boolean maritimeTrade(Command command);
	public boolean offerTrade(Command command);
	public boolean robPlayer(Command command);
	public boolean rollNumber(Command command);
	public boolean sendChat(Command command);
	public boolean playMonopoly(Command command);
	public boolean playMonument(Command command);
	public boolean playRoadBuilding(Command command);
	public boolean playSoldier(Command command);
	public boolean playYearOfPlenty(Command command);
	
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

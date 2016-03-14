package server.facade;

import java.util.ArrayList;
import java.util.List;
import server.commands.Command;
import shared.model.Game;


/**
 * Validates input from handlers, executes commands, and packages results for handler.
 * 
 * 
 */

public class ServerFacade implements IServerFacade {

	private List<Game> liveGames = new ArrayList<Game>();

	public ServerFacade() {}
	
	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Accept Trade action on the game model.
	 * 
	 * @pre none
	 * @post Accept Trade action will be performed on the correct model, or nothing
	 * @return whether the Accept Trade action was performed
	 * 
	 */
	@Override
	public boolean acceptTrade(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Build City action on the game model.
	 * 
	 * @pre none
	 * @post Build City action will be performed on the correct model, or nothing
	 * @return whether the Build City action was performed
	 * 
	 */
	@Override
	public boolean buildCity(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Build Road action on the game model.
	 * 
	 * @pre none
	 * @post Build Road action will be performed on the correct model, or nothing
	 * @return whether the Build Road action was performed
	 * 
	 */
	@Override
	public boolean buildRoad(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Build Settlement action on the game model.
	 * 
	 * @pre none
	 * @post Build Settlement action will be performed on the correct model, or nothing
	 * @return whether the Build Settlement action was performed
	 * 
	 */
	@Override
	public boolean buildSettlement(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Buy Dev Card action on the game model.
	 * 
	 * @pre none
	 * @post Buy Dev Card action will be performed on the correct model, or nothing
	 * @return whether the Buy Dev Card action was performed
	 * 
	 */
	@Override
	public boolean buyDevCard(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Discard Cards action on the game model.
	 * 
	 * @pre none
	 * @post Discard Cards action will be performed on the correct model, or nothing
	 * @return whether the Discard Cards action was performed
	 * 
	 */
	@Override
	public boolean discardCards(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Finish Turn action on the game model.
	 * 
	 * @pre none
	 * @post Finish Turn action will be performed on the correct model, or nothing
	 * @return whether the Finish Turn action was performed
	 * 
	 */
	@Override
	public boolean finishTurn(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Maritime Trade action on the game model.
	 * 
	 * @pre none
	 * @post Maritime Trade action will be performed on the correct model, or nothing
	 * @return whether the Maritime Trade action was performed
	 * 
	 */
	@Override
	public boolean maritimeTrade(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Offer Trade action on the game model.
	 * 
	 * @pre none
	 * @post Offer Trade action will be performed on the correct model, or nothing
	 * @return whether the Offer Trade action was performed
	 * 
	 */
	@Override
	public boolean offerTrade(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Rob Player action on the game model.
	 * 
	 * @pre none
	 * @post Rob Player action will be performed on the correct model, or nothing
	 * @return whether the Rob Player action was performed
	 * 
	 */
	@Override
	public boolean robPlayer(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Roll Number action on the game model.
	 * 
	 * @pre none
	 * @post Roll Number action will be performed on the correct model, or nothing
	 * @return whether the Roll Number action was performed
	 * 
	 */
	@Override
	public boolean rollNumber(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Send Chat action on the game model.
	 * 
	 * @pre none
	 * @post Send Chat action will be performed on the correct model, or nothing
	 * @return whether the Send Chat action was performed
	 * 
	 */
	@Override
	public boolean sendChat(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Play Monopoly action on the game model.
	 * 
	 * @pre none
	 * @post Play Monopoly action will be performed on the correct model, or nothing
	 * @return whether the Play Monopoly action was performed
	 * 
	 */
	@Override
	public boolean playMonopoly(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Play Monument action on the game model.
	 * 
	 * @pre none
	 * @post Play Monument action will be performed on the correct model, or nothing
	 * @return whether the Play Monument action was performed
	 * 
	 */
	@Override
	public boolean playMonument(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Play Road Building action on the game model.
	 * 
	 * @pre none
	 * @post Play Road Building action will be performed on the correct model, or nothing
	 * @return whether the Play Road Building action was performed
	 * 
	 */
	@Override
	public boolean playRoadBuilding(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Play Soldier action on the game model.
	 * 
	 * @pre none
	 * @post Play Soldier action will be performed on the correct model, or nothing
	 * @return whether the Play Soldier action was performed
	 * 
	 */
	@Override
	public boolean playSoldier(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Verifies which Game model the command is for.<br>
	 * Verifies that the client/player performing the action belongs to the game.<br>
	 * Verifies that the command is a valid action for that game.<br>
	 * Performs the Play Year Of Plenty action on the game model.
	 * 
	 * @pre none
	 * @post Play Year Of Plenty action will be performed on the correct model, or nothing
	 * @return whether the Play Year Of Plenty action was performed
	 * 
	 */
	@Override
	public boolean playYearOfPlenty(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	// Non Command pattern actions (nonmove actions)
	
	
	
	
	
	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean list() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean join() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean model() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean listAI() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	// Return the game that the command is meant to operate on
	
	
	
	@Override
	public Game findGameForCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

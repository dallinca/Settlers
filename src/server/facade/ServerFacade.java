package server.facade;

import java.util.ArrayList;
import java.util.List;

import server.commands.Command;
import shared.communication.User;
import shared.communication.params.move.devcard.*;
import shared.communication.params.move.*;
import shared.communication.params.nonmove.AddAI_Params;
import shared.communication.params.nonmove.Create_Params;
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.params.nonmove.Join_Params;
import shared.communication.params.nonmove.ListAI_Params;
import shared.communication.params.nonmove.List_Params;
import shared.communication.params.nonmove.Login_Params;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.move.AcceptTrade_Result;
import shared.communication.results.move.BuildCity_Result;
import shared.communication.results.move.BuildRoad_Result;
import shared.communication.results.move.BuildSettlement_Result;
import shared.communication.results.move.BuyDevCard_Result;
import shared.communication.results.move.DiscardCards_Result;
import shared.communication.results.move.FinishTurn_Result;
import shared.communication.results.move.MaritimeTrade_Result;
import shared.communication.results.move.OfferTrade_Result;
import shared.communication.results.move.RobPlayer_Result;
import shared.communication.results.move.RollNumber_Result;
import shared.communication.results.move.SendChat_Result;
import shared.communication.results.move.devcard.PlayMonopoly_Result;
import shared.communication.results.move.devcard.PlayMonument_Result;
import shared.communication.results.move.devcard.PlayRoadBuilding_Result;
import shared.communication.results.move.devcard.PlaySoldier_Result;
import shared.communication.results.move.devcard.PlayYearOfPlenty_Result;
import shared.communication.results.nonmove.AddAI_Result;
import shared.communication.results.nonmove.Create_Result;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.communication.results.nonmove.Join_Result;
import shared.communication.results.nonmove.ListAI_Result;
import shared.communication.results.nonmove.List_Result;
import shared.communication.results.nonmove.Login_Result;
import shared.communication.results.nonmove.Register_Result;
import shared.model.Game;


/**
 * Validates input from handlers, executes commands, and packages results for handler.
 * 
 * 
 */

public class ServerFacade implements IServerFacade {

	private List<Game> liveGames = new ArrayList<Game>();
	
	/**
	 * Singleton pattern for serverfacade
	 */
	private static ServerFacade singleton = new ServerFacade( );
	private ServerFacade() { }
	public static IServerFacade getInstance() {
		 return singleton;
	}
	
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
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public BuildCity_Result buildCity(BuildCity_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public BuildRoad_Result buildRoad(BuildRoad_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public DiscardCards_Result discardCards(DiscardCards_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public FinishTurn_Result finishTurn(FinishTurn_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public OfferTrade_Result offerTrade(OfferTrade_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public RobPlayer_Result robPlayer(RobPlayer_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public Game rollNumber(RollNumber_Params params, int gameID, int userID) {

		for(Game currentGame: liveGames){
			if(currentGame.getGameId() ==  gameID){
				if(currentGame.canDoRollDice(userID)){
					return currentGame;
				}
			}
		}
		return null;
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
	public SendChat_Result sendChat(SendChat_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public PlayMonument_Result playMonument(PlayMonument_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public PlayRoadBuilding_Result playRoadBuilding(PlayRoadBuilding_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public PlaySoldier_Result playSoldier(PlaySoldier_Params params) {
		// TODO Auto-generated method stub
		return null;
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
	public PlayYearOfPlenty_Result playYearOfPlenty(PlayYearOfPlenty_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	// Non Command pattern actions (nonmove actions)
	
	
	
	

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the login action.
	 * 
	 * @pre params != null
	 * @post Login Action performed
	 * 
	 * @return Login_Result Action
	 * 
	 */
	@Override
	public Login_Result login(Login_Params params) {
		
		/*if(params.getUsername().equals() && params.getPassword().equals())
		 * 	return true;
		 */
		return null;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the Register action.
	 * 
	 * @pre params != null
	 * @post Register Action performed
	 * 
	 * @return Register_Result Action
	 * 
	 */
	@Override
	public Register_Result register(Register_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the Result action.
	 * 
	 * @pre params != null
	 * @post List Games information retrieved
	 * 
	 * @return List_Result Action
	 * 
	 */
	@Override
	public List_Result list(List_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the Create Game action.
	 * 
	 * @pre params != null
	 * @post Create Game Action performed
	 * 
	 * @return Create_Result Action
	 * 
	 */
	@Override
	public Create_Result create(Create_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the Join Game action.
	 * 
	 * @pre params != null
	 * @post Join Game Action performed
	 * 
	 * @return Join_Result Action
	 * 
	 */
	@Override
	public Join_Result join(Join_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the Get Game Version action.
	 * 
	 * @pre params != null
	 * @post Game model retrieved
	 * 
	 * @return GetVersion_Result Action
	 * 
	 */
	@Override
	public GetVersion_Result model(GetVersion_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the Add AI action.
	 * 
	 * @pre params != null
	 * @post AI added to game
	 * 
	 * @return AddAI_Result Action
	 * 
	 */
	@Override
	public AddAI_Result addAI(AddAI_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the list AI action.
	 * 
	 * @pre params != null
	 * @post AI information retrieved for the game
	 * 
	 * @return ListAI_Result Action
	 * 
	 */
	@Override
	public ListAI_Result listAI(ListAI_Params params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns true if the user as specified exists in server memory, false if otherwise.
	 * Used to check cookie validity.
	 * @param user
	 * @return
	 */
	public boolean validateUser(User user) {
		if(user==null){
			return false;
		}
		// TODO Auto-generated method stub
		//Check to ensure that username, password, and playerID triple exists in server.
		
		return false;
		
	}
	
	/**
	 * Checks to see if the given user exists in the given game. 
	 * 
	 * @param user
	 * @param gameID
	 * @return
	 */

	public boolean validateGame(User user, int gameID) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Game canDoPlayMonopoly(int gameID, int userID) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
	
	// Return the game that the command is meant to operate on
	
	/*
	/**
	 * Each of the concrete command classes need to have the correct game to act on
	 * 
	 * 
	 *//*
	@Override
	public Game findGameForCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
}

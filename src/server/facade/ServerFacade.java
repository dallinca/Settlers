package server.facade;

import java.util.ArrayList;
import java.util.List;

import client.ClientFacade;
import client.proxy.ServerProxy;
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
import shared.definitions.ResourceType;
import shared.model.Game;


/**
 * Validates input from handlers, executes commands, and packages results for handler.
 * 
 * 
 */

public class ServerFacade implements IServerFacade {

	private List<Game> liveGames = new ArrayList<Game>();
	private List<User> users = new ArrayList<User>();

	/**
	 * Singleton pattern for serverfacade
	 */
	private static ServerFacade SINGLETON = null;
	private ServerFacade() { }
	public static ServerFacade getInstance() {
		if(SINGLETON == null){
			SINGLETON = new ServerFacade();
		}
		return SINGLETON;
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
	public Game acceptTrade(AcceptTrade_Params params) {
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
	public Game buildCity(BuildCity_Params params, int gameID, int userID) {

		Game game = findGame(gameID);
		if(game.canDoPlayerBuildCity(userID)){
			return game; 
		}
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
	public Game buildRoad(BuildRoad_Params params, int gameID, int userID) {
		
		Game game = findGame(gameID);
		if(game.canDoPlayerBuildRoad(userID)){
			return game;
		}
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
	public Game buildSettlement(BuildSettlement_Params params, int gameID, int userID) {

		Game game = findGame(gameID);
		if(game.canDoPlayerBuildSettlement(userID)){
			return game; 
		}
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
	public Game buyDevCard(BuyDevCard_Params params, int gameID, int userID) {
		
		Game game = findGame(gameID);	
		if(game.canDoPlayerBuyDevelopmentCard(userID)){
			return game;
		}
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
	public Game discardCards(DiscardCards_Params params, int gameID, int userID) {
		Game game = findGame(gameID);

		if(!game.canDoDiscardNumberOfResourceType(userID, params.getDiscardedCards().getBrick(), ResourceType.BRICK)){
			return null;
		}
		if(!game.canDoDiscardNumberOfResourceType(userID, params.getDiscardedCards().getOre(), ResourceType.ORE)){
			return null;
		}
		if(!game.canDoDiscardNumberOfResourceType(userID, params.getDiscardedCards().getSheep(), ResourceType.SHEEP)){
			return null;
		}
		if(!game.canDoDiscardNumberOfResourceType(userID, params.getDiscardedCards().getWheat(), ResourceType.WHEAT)){
			return null;
		}
		if(!game.canDoDiscardNumberOfResourceType(userID, params.getDiscardedCards().getWood(), ResourceType.WOOD)){
			return null;
		}
		return game;		
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
	public Game finishTurn(FinishTurn_Params params, int gameID, int userID) {

		Game game = findGame(gameID);
		if(game.canDoPlayerEndTurn(userID)){
			return game;
		}
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
	public Game maritimeTrade(MaritimeTrade_Params params, int gameID, ResourceType tradeIn, ResourceType receive) {

		Game game = findGame(gameID);
		if(game.canDoPlayerDoMaritimeTrade(tradeIn, receive)){
			return game;
		}
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
	public Game offerTrade(OfferTrade_Params params, int gameID,int userID) {

		Game game = findGame(gameID);
		if(game.canDoPlayerDoDomesticTrade(userID, p1resources, params.getReceiver(), p2resources)){
			return game;
		}
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
	public Game robPlayer(RobPlayer_Params params, int gameID, int userID) {

		Game game = findGame(gameID);
		if(game.canDoMoveRobberToHex(userID, params.getLocation())){
			if(game.canDoStealPlayerResource(userID, params.getVictimIndex() )){
				return game;
			}
		}
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

		Game game = findGame(gameID);
		if(game.canDoRollDice(userID)){
			return game;
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
	public Game sendChat(SendChat_Params params, int gameID, int userID) {
		Game game = findGame(gameID);
		return game;
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
	public Game playMonopoly(PlayMonopoly_Params params) {
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
	public Game playMonument(PlayMonument_Params params) {
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
	public Game playRoadBuilding(PlayRoadBuilding_Params params) {
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
	public Game playSoldier(PlaySoldier_Params params) {
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
	public Game playYearOfPlenty(PlayYearOfPlenty_Params params) {
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
		
		String username = params.getUsername();
		String password = params.getPassword();
		
		Login_Result result = new Login_Result();
		
		for (User current : users){
			if (current.getName().equals(username)){
				if (current.getPassword().equals(password)){
					
					result.setWasLoggedIn(true);
					//name
					//password
					//playerID
					
					String userCookie = ("{\"name\":\"" + current.getName()
							+ "\",\"password\":\""+current.getPassword()
							+ "\",\"playerID\":"+ current.getPlayerID() +"}");
					
					result.setUserCookie(userCookie);
				}
			}
		}

		return result;
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

	private Game findGame(int gameID){

		for(Game currentGame: liveGames){
			if(currentGame.getGameID() ==  gameID){
				return currentGame;
			}
		}
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

		for (User current : users){			
			if (current.equals(user)){
				return true; //The given user exists in the system.
			}			
		}

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
		Game g = findGame(gameID);

		if (g==null || user==null){
			return false;
		}else if (g.getPlayerByID(user.getPlayerID())==null){
			return false;
		}		

		return true;
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

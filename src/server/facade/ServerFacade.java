package server.facade;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import client.ClientFacade;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.data.TradeInfo;
import client.proxy.ServerProxy;
import server.commands.Command;
import shared.communication.User;
import shared.communication.params.move.devcard.*;
import shared.communication.params.move.*;
import shared.communication.params.move.OfferTrade_Params.Offer;
import shared.communication.params.nonmove.AddAI_Params;
import shared.communication.params.nonmove.Create_Params;
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.params.nonmove.Join_Params;
import shared.communication.params.nonmove.ListAI_Params;
import shared.communication.params.nonmove.List_Params;
import shared.communication.params.nonmove.Login_Params;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.ClientModel.ResourceList;
import shared.communication.results.JsonConverter;
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
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.Bank;
import shared.model.Game;
import shared.model.board.Board;
import shared.model.player.Player;


/**
 * Validates input from handlers, executes commands, and packages results for handler.
 * 
 * 
 */

public class ServerFacade implements IServerFacade {

	private int gameTracker = 0;
	private List<Game> liveGames = new ArrayList<Game>();
	private List<User> users = new ArrayList<User>();
	private JsonConverter jc;

	/**
	 * Singleton pattern for serverfacade
	 */
	private static ServerFacade SINGLETON = null;

	private ServerFacade() {		
		this.jc = new JsonConverter();


		//TESTING REASON
		User user1 = new User("scott","scott", users.size());
		users.add(user1);
		User user2 = new User("thomas", "thomas", users.size());
		users.add(user2);
		User user3 = new User("chris", "chris", users.size());
		users.add(user3);
		User user4 = new User("dallin", "dallin", users.size());
		users.add(user4);

		Player[] players = new Player[4];		
		Board board = new Board(true, true, true);
		Game game = new Game(players, board, new Bank());

		game.setTitle("TESTGame");		
		game.setGameID(gameTracker++);

		game.addPlayer(0, CatanColor.RED);
		Player p = game.getPlayerByID(0);
		User u = users.get(0);
		p.setPlayerName(u.getName());		

		game.addPlayer(1, CatanColor.BLUE);
		p = game.getPlayerByID(1);
		u = users.get(1);
		p.setPlayerName(u.getName());

		game.addPlayer(2, CatanColor.WHITE);
		p = game.getPlayerByID(2);
		u = users.get(2);
		p.setPlayerName(u.getName());

		game.addPlayer(3, CatanColor.GREEN);
		p = game.getPlayerByID(3);
		u = users.get(3);
		p.setPlayerName(u.getName());

		liveGames.add(game);
	}

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
	@Deprecated
	@Override
	public Game canDoAcceptTrade(AcceptTrade_Params params) {
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
	public Game canDoBuildCity(BuildCity_Params params, int gameID, int userID) {

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
	public Game canDoBuildRoad(BuildRoad_Params params, int gameID, int userID) {
		System.out.println("canDoBuildRoad SErver facade");
		Game game = findGame(gameID);
		System.out.println("canDoBuildRoad SErver facade1");
		if(game.canDoPlayerBuildRoad(userID)){
			System.out.println("canDoBuildRoad SErver facade2");
			return game;
		}
		System.out.println("canDoBuildRoad SErver facade3");
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
	public Game canDoBuildSettlement(BuildSettlement_Params params, int gameID, int userID) {

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
	public Game canDoBuyDevCard(BuyDevCard_Params params, int gameID, int userID) {

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
	public Game canDoDiscardCards(DiscardCards_Params params, int gameID, int userID) {
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
	public Game canDoFinishTurn(FinishTurn_Params params, int gameID, int userID) {

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
	public Game canDoMaritimeTrade(MaritimeTrade_Params params, int gameID, ResourceType tradeIn, ResourceType receive) {

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
	public Game canDoOfferTrade(OfferTrade_Params params, int gameID,int userID) {

		Game game = findGame(gameID);

		ClientModel clientModel = new ClientModel();
		ClientModel.ResourceList resourceList = clientModel.new ResourceList();
		resourceList.brick = params.getOffer().getBrick();
		resourceList.wood = params.getOffer().getWood();
		resourceList.wheat = params.getOffer().getWheat();
		resourceList.ore = params.getOffer().getOre();
		resourceList.sheep = params.getOffer().getSheep();


		TradeInfo tradeInfo = new TradeInfo(params.getPlayerIndex(), params.getReceiver(), resourceList);

		ResourceList o = tradeInfo.getOffer();
		int[] offer = new int[5];
		int[] receive = new int[5];

		if (o.getBrick() >= 0) {
			offer[0] = o.getBrick();
			receive[0] = 0;
		} else {
			receive[0] = o.getBrick() * -1;
			offer[0] = 0;
		}

		if (o.getWood() >= 0) {
			offer[1] = o.getWood();
			receive[1] = 0;
		} else {
			receive[1] = o.getWood() * -1;
			offer[1] = 0;
		}

		if (o.getWheat() >= 0) {
			offer[2] = o.getWheat();
			receive[2] = 0;
		} else {
			receive[2] = o.getWheat() * -1;
			offer[2] = 0;
		}

		if (o.getOre() >= 0) {
			offer[3] = o.getOre();
			receive[3] = 0;
		} else {
			receive[3] = o.getOre() * -1;
			offer[3] = 0;
		}

		if (o.getBrick() >= 0) {
			offer[4] = o.getSheep();
			receive[4] = 0;
		} else {
			receive[4] = o.getSheep() * -1;
			offer[4] = 0;
		}

		if(game.canDoPlayerDoDomesticTrade(userID, offer, params.getReceiver(), receive)){
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
	public Game canDoRobPlayer(RobPlayer_Params params, int gameID, int userID) {

		Game game = findGame(gameID);
		//System.out.println("canDoRobPlayer 1");
		Player stealer = game.getAllPlayers()[params.getPlayerIndex()];
		//System.out.println("canDoRobPlayer 2");
		//System.out.println("canDoRobPlayer 3");
		
		
		if(game.canDoMoveRobberToHex(stealer.getPlayerId(), params.getLocation())){ // MOVER Id

			//System.out.println("canDoRobPlayer 4");
			if (params.getVictimIndex() == -1){
				//System.out.println("canDoRobPlayer 5");
				game.setVersionNumber(game.getVersionNumber() + 1);
				game.setStatus("Playing");
				//System.out.println("canDoRobPlayer 6");
				return game;
			}

			Player victim = game.getAllPlayers()[params.getVictimIndex()];
			if(game.canDoStealPlayerResource(stealer.getPlayerId(), victim.getPlayerId() )){ // USER Id, VICTIM Id

				//System.out.println("canDoRobPlayer 7");
				return game;
			}

		}


		//System.out.println("canDoRobPlayer 8");
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
	public Game canDoRollNumber(RollNumber_Params params, int gameID, int userID) {

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
	public Game canDoSendChat(SendChat_Params params, int gameID, int userID) {
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
	public Game canDoPlayMonopoly(PlayMonopoly_Params params, int gameID, int userID) {
		Game game = findGame(gameID);

		if (!game.canDoPlayerUseDevelopmentCard(userID, DevCardType.MONOPOLY)) {
			game = null;
		}

		return game;
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
	public Game canDoPlayMonument(int gameID, int userID) {
		Game game = findGame(gameID);

		//The functionality to retrieve the gameID from the game objects is required to figure out which game this person belongs to.
		if (!game.canDoPlayerUseDevelopmentCard(userID, DevCardType.MONUMENT)) {
			game = null;
		}

		return game;
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
	public Game canDoPlayRoadBuilding(PlayRoadBuilding_Params params, EdgeLocation edge1, EdgeLocation edge2, int gameID, int userID) {
		Game game = findGame(gameID);

		if (!game.canDoPlaceRoadOnEdge(userID, edge1) && !game.canDoPlaceRoadOnEdge(userID, edge2)) {
			game = null;
		}

		return game;
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
	public Game canDoPlaySoldier(PlaySoldier_Params params, int gameID, int userID) {
		Game game = findGame(gameID);


		if (!game.canDoStealPlayerResource(userID, params.getVictimIndex()) || !game.canDoMoveRobberToHex(userID, params.getLocation()) || !game.canDoPlayerUseDevelopmentCard(userID, DevCardType.SOLDIER)) {
			game = null;
		} 

		return game;
	}
	// Resource: 
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
	public Game canDoPlayYearOfPlenty(ResourceType[] resourceType, int gameID, int userID) {

		Game game = findGame(gameID);

		if (resourceType.length == 1) {
			if (!game.canDoPlayerTake2OfResource(resourceType[0])) {
				return null;
			}
		} else {

			for (int i = 0; i < resourceType.length; i++) {
				if (!game.canDoPlayerTakeResource(resourceType[i])) {
					return null;
				}
			}
		} 
		return game;
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
		//System.out.println("ServerFacade.login");

		String username = params.getUsername();
		String password = params.getPassword();

		Login_Result result = new Login_Result();

		for (User current : users) {
			if (current.getName().equals(username)) {
				if (current.getPassword().equals(password)) {

					result.setWasLoggedIn(true);

					/*String userCookie = ("{\"name\":\"" + current.getName()
							+ "\",\"password\":\""+current.getPassword()
							+ "\",\"playerID\":"+ current.getPlayerID() +"}");*/

					JsonObject json = new JsonObject();

					json.addProperty("name", current.getName());
					json.addProperty("password", current.getPassword());
					json.addProperty("playerID", current.getPlayerID());	

					String userCookie = json.toString();

					StringBuilder sb = new StringBuilder();
					sb.append("catan.user=");

					sb.append(URLEncoder.encode(userCookie));
					sb.append(";Path=/;");
					userCookie = sb.toString();

					result.setUserCookie(userCookie);

					//System.out.println("ServerFacade.login result");
					//System.out.println(result);
					return result;
				}
			}
		}
		//System.out.println("ServerFacade.login result");
		//System.out.println(result);
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
	@SuppressWarnings("deprecation")
	@Override
	public Register_Result register(Register_Params params) {
		//System.out.println("ServerFacade.register");

		//System.out.println("users Size: " + users.size());
		String username = params.getUsername();
		String password = params.getPassword();


		Register_Result result = new Register_Result();

		for (User current : users){
			if (current.getName().equals(username)){
				//System.out.println("Username already exists in system");
				return result;
				//no duplicate names allowed.
			}
		}

		result.setValid(true);

		User user = new User(username, password, users.size());

		//System.out.println("Creating new user");
		users.add(user); // add new user to roster

		//System.out.println("Creating user cookie");
		//generate user cookie
		/*String userCookie = ("{\"name\":\"" + username
				+ "\",\"password\":\""+password
				+ "\",\"playerID\":"+ user.getPlayerID() +"}");*/

		JsonObject json = new JsonObject();

		json.addProperty("name", username);
		json.addProperty("password", password);
		json.addProperty("playerID", user.getPlayerID());		

		//System.out.println("Unencoded cookie: "+json.toString());
		String userCookie = json.toString();

		StringBuilder sb = new StringBuilder();
		sb.append("catan.user=");

		sb.append(URLEncoder.encode(userCookie));
		sb.append(";Path=/;");
		userCookie = sb.toString();
		//System.out.println("Encoded cookie: "+userCookie);


		result.setUserCookie(userCookie);

		//System.out.println("users Size: " + users.size());
		//System.out.println(users);
		return result;
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

		//System.out.println("Server.ServerFacade.list");

		List_Result result = new List_Result();
		if (params==null){
			System.out.println("Null parameters");
			return result;
		}

		//System.out.println("Getting game info list");
		GameInfo[] list = new GameInfo[liveGames.size()];


		for (int i = 0 ; i < liveGames.size(); i++){ 

			Game g = liveGames.get(i);

			GameInfo info = new GameInfo();
			info.setId(g.getGameID());
			info.setTitle(g.getTitle());

			Player[] players = g.getAllPlayers();

			for (int x = 0; x < g.getPlayerCount(); x++){

				PlayerInfo pi = new PlayerInfo();
				Player p = players[x];

				pi.setId(p.getPlayerId());
				pi.setName(p.getPlayerName());
				pi.setPlayerIndex(p.getPlayerIndex());				
				pi.setColor(p.getPlayerColor());

				//System.out.println("PLAYER INFO---"+x);
				//System.out.println(pi.getName());
				//System.out.println(pi.getColor());				



				info.addPlayer(pi);
			}


			list[i] = info;					
		}

		result.setGames(list);
		result.setValid(true);

		//System.out.println("Result to string");

		//System.out.println(result.toString());

		return result;
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
	public Create_Result create(Create_Params params, int userID) {
		//System.out.println("Creating game.");
		String name = params.getName();
		boolean numbers = params.isRandomNumbers();
		boolean ports = params.isRandomPorts();
		boolean tiles = params.isRandomTiles();

		Create_Result result = new Create_Result();
		result.setID(gameTracker);

		Player[] players = new Player[4];		
		Board board = new Board(tiles, numbers, ports);
		Game game = new Game(players, board, new Bank());

		game.setTitle(name);		
		game.setGameID(gameTracker++);

		//System.out.println("Adding creator to game.");
		game.addPlayer(userID, null);
		Player p = game.getPlayerByID(userID);
		User u = users.get(userID);
		p.setPlayerName(u.getName());

		//System.out.println("Game created successfully.");

		liveGames.add(game);

		result.setTitle(name);
		result.setValid(true);

		//System.out.println("Returning game result from server facade.");
		return result;
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
	public Join_Result join(Join_Params params, int userID) {
		//System.out.println("ServerFacade.join");
		int gameID = params.getGameID();
		Join_Result result = new Join_Result();

		//System.out.println("Finding game.");
		Game g = findGame(gameID);

		if (g==null){
			//System.out.println("Attempted to join null game");
			return result;
		}

		//System.out.println("Getting all players from game.");
		Player[] players = g.getAllPlayers();

		if (players == null){
			//	System.out.println("Creating new player set.");
			players = new Player[4];
		}

		boolean joinable = false;
		for (int i = 0; i < 4; i ++){
			if (players[i]==null){ //Check for vacancy in game roster.
				//	System.out.println("Vacancy exists.");				
				joinable = true;

				break;
			}
			else if (players[i].getPlayerId()==userID){ //Check if player has already joined game previously
				//System.out.println("Player already exists in game.");
				joinable = true;
				break;
			}
		}

		if (!joinable){
			//System.out.println("Game cannot be joined.");
			return result;			
		}

		//System.out.println("Getting player by ID");
		Player p = g.getPlayerByID(userID);
		//System.out.println("Converting color");
		CatanColor playerColor = params.convertColor();

		if (playerColor==null){
			//System.out.println("No color given. Aborting.");
			return result;
		}

		if (p!=null){
			//System.out.println("Existing player being added to game");		

			p.setPlayerColor(playerColor);
		}else{
			//System.out.println("New player being added to game");
			g.addPlayer(userID, playerColor);
			p = g.getPlayerByID(userID);

			User u = users.get(userID);
			p.setPlayerName(u.getName());
		}		

		result.setValid(true);

		String gameCookie = ("catan.game="+ gameID +";Path=/;");

		//System.out.println("Game cookie: "+gameCookie);

		result.setGameCookie(gameCookie);

		//System.out.println("Converting game to client model");

		result.setModel(jc.toClientModel(g));

		//System.out.println("ServerFacade.join completed");

		boolean start = true;

		players = g.getAllPlayers();

		for (int y = 0; y < 4; y++){
			if (players[y]==null){
				start = false;
				break;
			}
		}

		if (start && g.getStatus().equals("")){
			startGame(g);
		}


		return result;
	}

	private void startGame(Game g) {	

		g.setStatus("FirstRound");
		g.setCurrentPlayer(g.getAllPlayers()[0]);

		return;		
	}

	/**
	 * To be called from the Handlers.<br>
	 * Attempts the Get Game Version action.
	 * @param i 
	 * 
	 * @pre params != null
	 * @post Game model retrieved
	 * 
	 * @return GetVersion_Result Action
	 * 
	 */
	@Override
	public GetVersion_Result model(GetVersion_Params params, int gameID) {
		//	System.out.println("ServerFacade.GetVersion");

		GetVersion_Result result = new GetVersion_Result();

		int clientVersion = params.getVersionNumber();
		Game g = findGame(gameID);

		if (g.getVersionNumber()>clientVersion){

			result.setGame(g);
			result.setModel(jc.toClientModel(g));

			//System.out.println(result.getModel().toString());			

			result.setUpToDate(false);
			result.setValid(true);
		}
		else if (clientVersion == g.getVersionNumber()){
			result.setUpToDate(true);
			result.setValid(true);
		}else{
			//Critical error, user polling from the future (client's version is ahead of current version
			//should be impossible
			result.setValid(false);
		}

		return result;
	}



	/**EXTRA CREDIT, IGNORE
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




	private Game findGame(int gameID){

		for(Game currentGame: liveGames){
			if(currentGame.getGameID() ==  gameID){
				return currentGame;
			}
		}
		return null;		
	}


}

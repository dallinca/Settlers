package client;

import java.util.ArrayList;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.proxy.IServerProxy;
import client.proxy.ServerProxy;
import shared.communication.params.move.AcceptTrade_Params;
import shared.communication.params.move.BuildCity_Params;
import shared.communication.params.move.BuildRoad_Params;
import shared.communication.params.move.BuildSettlement_Params;
import shared.communication.params.move.BuyDevCard_Params;
import shared.communication.params.move.DiscardCards_Params;
import shared.communication.params.move.FinishTurn_Params;
import shared.communication.params.move.MaritimeTrade_Params;
import shared.communication.params.move.OfferTrade_Params;
import shared.communication.params.move.RobPlayer_Params;
import shared.communication.params.move.RollNumber_Params;
import shared.communication.params.move.SendChat_Params;
import shared.communication.params.move.devcard.PlayMonopoly_Params;
import shared.communication.params.move.devcard.PlayMonument_Params;
import shared.communication.params.move.devcard.PlayRoadBuilding_Params;
import shared.communication.params.move.devcard.PlaySoldier_Params;
import shared.communication.params.move.devcard.PlayYearOfPlenty_Params;
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
import shared.definitions.CatanColor;

/**
 * Sends all to-server requests to the client communicator for packaging
 * Interprets responses, handles errors from requests when responses are invalid.
 * 
 * @author jchrisw
 *
 */
public class MockClientFacade {
	
	private static MockClientFacade SINGLETON;
	
	// Dummy info for tab
	private ArrayList<GameInfo> gamesList = new ArrayList<GameInfo>();
	private int gameId = 55;
	// END Dummy info
	
	private IServerProxy sp;

	/**
	 * Creates fascade, specifying the location of the master server.
	 * @param serverName
	 * @param portNumber
	 * 
	 * @pre Server name and port number specifiy an existing server.
	 * @post Client will be able to communicate with server.
	 */
	
	private MockClientFacade() {
		//this.c = Client.getInstance();
	}
	
	public static MockClientFacade getInstance() {
		if(SINGLETON == null) {
			SINGLETON = new MockClientFacade();
		}
		return SINGLETON;
	}

	/**
	 * Parses JSON data and maps it onto client model.
	 * 
	 * @pre Given JSONdata from server
	 * @post Data will be mapped onto client model.
	 */
	public void parseJSONData(Object JSONdata){

	}

	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		return null;
	}

	public Create_Result createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) throws ClientException {
		Create_Result result = new Create_Result(); 
		result.setValid(true);
		GameInfo newGame = new GameInfo();
			newGame.setId(gameId);
			gameId++;
			newGame.setTitle(name);
			PlayerInfo player = new PlayerInfo();
				player.setColor(CatanColor.WHITE);
				if(Client.getInstance() == null) {
					System.out.println("c is null");
				}
				System.out.println(Client.getInstance().getUserId());
				player.setId(Client.getInstance().getUserId());
				player.setName(Client.getInstance().getName());
			newGame.addPlayer(player);
		gamesList.add(newGame);

		return result;
	}

	/**
	 * Gets the current game state from the server.
	 * @param username
	 * @return
	 * 
	 * @pre Client is validated and participating in a game.
	 * @post Communicator will return usable PollServer_Result.
	 */
	public GetVersion_Result getVersion(GetVersion_Params request) throws ClientException {
		return null;
	}

	public Join_Result joinGame(int gameID, CatanColor color) throws ClientException {
		Join_Result result = new Join_Result();
		result.setJoined(true);
		System.out.println("gameID: " + gameID + "::: color: " + color);
		return result;
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public List_Result listGames() throws ClientException {
		// START -- DUMMY INFORMATION
		GameInfo[] games = getCurrentGames();

		// END -- DUMMY INFORMATION
		List_Result result = new List_Result(games);
		return result;
	}

	/**
	 * EXTRA CREDIT!!!! and stuff...... :)
	 * TODO - Javadoc and Implement
	 * 
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public ListAI_Result listAI(ListAI_Params request) throws ClientException {
		return null;
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public Login_Result login(String username, String password) throws ClientException {
		Login_Result result = new Login_Result();
		if(username.equals("mack")) {
			result.setWasLoggedIn(true);
			result.setName("mack");
			result.setId(24);
		} else if (username.equals("Chewy")) {
			result.setWasLoggedIn(true);
			result.setName("Chewy");
			result.setId(1);
		} else if (username.equals("bummer")) {
			result.setWasLoggedIn(true);
			result.setName("bummer");
			result.setId(1111);
		} else {
			result.setWasLoggedIn(false);
		}
		return result;
	}

	/**
	 * TODO - Javadoc and Implement
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public Register_Result register(String username, String password) throws ClientException {
		Register_Result result = new Register_Result();
		if(username.equals("puppy")) {
			result.setWasRegistered(true);
			result.setName("puppy");
			result.setId(44);
		} else {
			result.setWasRegistered(false);
		}
		return result;
	}

	//move

	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request) throws ClientException {
		return null;
	}

	public BuildCity_Result buildCity(BuildCity_Params request) throws ClientException {
		return null;
	}

	public BuildRoad_Result buildRoad(BuildRoad_Params request) throws ClientException {
		return null;
	}

	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request) throws ClientException {
		return null;
	}

	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request) throws ClientException {
		return null;
	}

	public DiscardCards_Result discardCards(DiscardCards_Params request) throws ClientException {
		return null;
	}

	public FinishTurn_Result finishTurn(FinishTurn_Params request) throws ClientException {
		return null;
	}

	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request) throws ClientException {
		return null;
	}

	public OfferTrade_Result offerTrade(OfferTrade_Params request) throws ClientException {
		return null;
	}

	public RobPlayer_Result robPlayer(RobPlayer_Params request) throws ClientException {
		return null;
	}

	public RollNumber_Result rollNumber(RollNumber_Params request) throws ClientException {
		return null;
	}

	public SendChat_Result sendChat(SendChat_Params request) throws ClientException {
		return null;
	}	

	//dev card play

	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params request) throws ClientException {
		return null;
	}

	public PlayMonument_Result playMonument(PlayMonument_Params request) throws ClientException {
		return null;
	}

	public PlayRoadBuilding_Result playRoadBuilding(PlayRoadBuilding_Params request) throws ClientException {
		return null;
	}

	public PlaySoldier_Result playSolder(PlaySoldier_Params request) throws ClientException {
		return null;
	}

	public PlayYearOfPlenty_Result playYearOfPlenty(PlayYearOfPlenty_Params result) throws ClientException {
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	private void initGamesArrayList() {
		PlayerInfo p1 = new PlayerInfo();
		p1.setColor(CatanColor.BLUE);
		p1.setId(1);
		p1.setName("Chewy");
		p1.setPlayerIndex(0);
		
		PlayerInfo p2 = new PlayerInfo();
		p2.setColor(CatanColor.BROWN);
		p2.setId(44);
		p2.setName("puppy");
		p2.setPlayerIndex(1);
		
		PlayerInfo p3 = new PlayerInfo();
		p3.setColor(CatanColor.GREEN);
		p3.setId(24);
		p3.setName("mack");
		p3.setPlayerIndex(2);
		
		PlayerInfo p4 = new PlayerInfo();
		p4.setColor(CatanColor.PURPLE);
		p4.setId(13);
		p4.setName("Manndi");
		p4.setPlayerIndex(3);
		
		gamesList.add(new GameInfo());
		gamesList.get(0).setId(1);
		gamesList.get(0).setTitle("MAASLDKF");
		gamesList.get(0).addPlayer(p1);
		gamesList.get(0).addPlayer(p2);
		gamesList.get(0).addPlayer(p3);
		gamesList.get(0).addPlayer(p4);
		
		
		p3.setPlayerIndex(3);
		p4.setPlayerIndex(2);

		gamesList.add(new GameInfo());
		gamesList.get(1).setId(13);
		gamesList.get(1).setTitle("MAASsdfLDKF");
		//gamesList.get(1).addPlayer(p1);
		gamesList.get(1).addPlayer(p2);
		gamesList.get(1).addPlayer(p3);

	}
	
	
	private GameInfo[] getCurrentGames() {
		if(gamesList.size() == 0) {
			initGamesArrayList();
		}
		GameInfo[] games = new GameInfo[gamesList.size()];
		return gamesList.toArray(games);
	}



}
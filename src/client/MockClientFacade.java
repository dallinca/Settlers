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
import shared.model.Bank;
import shared.model.Game;
import shared.model.board.Board;
import shared.model.board.ModEdgeDirection;
import shared.model.player.Player;
import shared.model.player.exceptions.AllPiecesPlayedException;

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
		Bank bank = new Bank();
		Board board = new Board(false, false, false);
		// Players
		PlayerInfo pi0 = Client.getInstance().getGameInfo().getPlayers().get(0);
		Player p0 = new Player(pi0.getPlayerIndex(), bank);
		p0.setPlayerName(pi0.getName());
		p0.setPlayerColor(pi0.getColor());
		p0.setPlayerId(pi0.getId());
		p0.setPlayersTurn(true);

		PlayerInfo pi1 = Client.getInstance().getGameInfo().getPlayers().get(1);
		Player p1 = new Player(pi1.getPlayerIndex(), bank);
		p1.setPlayerName(pi1.getName());
		p1.setPlayerColor(pi1.getColor());
		p1.setPlayerId(pi1.getId());

		PlayerInfo pi2 = Client.getInstance().getGameInfo().getPlayers().get(2);
		Player p2 = new Player(pi2.getPlayerIndex(), bank);
		p2.setPlayerName(pi2.getName());
		p2.setPlayerColor(pi2.getColor());
		p2.setPlayerId(pi2.getId());

		PlayerInfo pi3 = Client.getInstance().getGameInfo().getPlayers().get(3);
		Player p3 = new Player(pi3.getPlayerIndex(), bank);
		p3.setPlayerName(pi3.getName());
		p3.setPlayerColor(pi3.getColor());
		p3.setPlayerId(pi3.getId());

		if(p0.getPlayerId() == Client.getInstance().getUserId()) {
			p0.setPlayerColor(color);
		} else if(p1.getPlayerId() == Client.getInstance().getUserId()) {
			p1.setPlayerColor(color);
		} else if(p2.getPlayerId() == Client.getInstance().getUserId()) {
			p2.setPlayerColor(color);
		} else if(p3.getPlayerId() == Client.getInstance().getUserId()) {
			p3.setPlayerColor(color);
		}

		try {
			// ROADS
			p0.getPlayerPieces().placeRoad(board.getEdge(3, 3, ModEdgeDirection.LEFT));
			//p0.getPlayerPieces().placeRoad(board.getEdge(4, 4, ModEdgeDirection.LEFT));
			p1.getPlayerPieces().placeRoad(board.getEdge(2, 2, ModEdgeDirection.UP));
			p2.getPlayerPieces().placeRoad(board.getEdge(3, 5, ModEdgeDirection.RIGHT));
			p3.getPlayerPieces().placeRoad(board.getEdge(1, 6, ModEdgeDirection.UP));
			// SETTLEMENTS
			p0.getPlayerPieces().placeSettlement(board.getVertex(4, 6));
			//p0.getPlayerPieces().placeSettlement(board.getVertex(4, 4));
			//p0.getPlayerPieces().placeSettlement(board.getVertex(5, 5));
			p2.getPlayerPieces().placeSettlement(board.getVertex(9, 1));
			// CITIES
			//p0.getPlayerPieces().placeCity(board.getVertex(3, 3));
			p2.getPlayerPieces().placeCity(board.getVertex(9, 1));
			//p3.getPlayerPieces().placeCity(board.getVertex(6, 6));
			//p1.getPlayerPieces().placeCity(board.getVertex(8, 8));
			//p2.getPlayerPieces().placeCity(board.getVertex(0, 8));
			
		} catch (AllPiecesPlayedException e) {
			// Didn't place a sample road on the board
			e.printStackTrace();
		}
		
		Game game = new Game(bank, p0, p1, p2, p3, board);
		Client.getInstance().setGame(game);
		
		
		
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
			result.setID(24);
		} else if (username.equals("Chewy")) {
			result.setWasLoggedIn(true);
			result.setName("Chewy");
			result.setID(1);
		} else if (username.equals("bummer")) {
			result.setWasLoggedIn(true);
			result.setName("bummer");
			result.setID(1111);
		} else if (username.equals("manndi")) {
			result.setWasLoggedIn(true);
			result.setName("manndi");
			result.setID(13);
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
			result.setValid(true);
			result.setName("puppy");
			result.setId(44);
		} else {
			result.setValid(false);
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
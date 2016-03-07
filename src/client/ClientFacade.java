package client;

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
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.Game;

/**
 * Sends all to-server requests to the client communicator for packaging
 * Interprets responses, handles errors from requests when responses are invalid.
 * 
 * @author jchrisw
 *
 */
public class ClientFacade {
	
	private static ClientFacade SINGLETON = null;
	
	
	protected ClientFacade(){
		//System.out.println("ClientFacade ClientFacade()");
		sp = new ServerProxy();
		//this.c = Client.getInstance();
	}
	
	public static ClientFacade getInstance(){
	//	System.out.println("ClientFacade ClientFacade()");
		if (SINGLETON == null){
			SINGLETON = new ClientFacade();
		}
		return SINGLETON;
	}

	private IServerProxy sp;
	//private Client c; 

	/**
	 * Creates facade, specifying the location of the master server.
	 * @param serverName
	 * @param portNumber
	 * 
	 * @pre Server name and port number specifiy an existing server.
	 * @post Client will be able to communicate with server.
	 */
	ClientFacade(IServerProxy proxy){
		System.out.println("ClientFacade ClientFacade()");
		this.sp = proxy;
	}

	public ServerProxy getProxy() {
		return (ServerProxy)sp;
	}
	
	/*public ClientFacade(){		
		this.sp = new ServerProxy();				
	}*/

	/**
	 * Validates the given user with the server database.
	 * @param username
	 * @param password
	 * @return
	 * 
	 * @pre None
	 * @post Communicator will return usable ValidateUser_Result
	 */
	public Login_Result login(String username, String password) {
		System.out.println("ClientFacade login()");		

		Login_Result result; 
		Login_Params request = new Login_Params(username, password);		

		try {

			result = sp.login(request);	
			
			Client.getInstance().setUserId(result.getID());

		} catch (ClientException e) {			
			result = new Login_Result();			

			e.printStackTrace();
		}		

		return result;
	}

	/**
	 * Parses JSON data and maps it onto client model.
	 * 
	 * @pre Given JSONdata from server
	 * @post Data will be mapped onto client model.
	 */
	public void parseJSONData(Object JSONdata){
		System.out.println("ClientFacade parseJSONData()");

	}

	/**
	 * extra credit
	 * @param request
	 * @return
	 * @
	 */
	public AddAI_Result addAI(AddAI_Params request)  {
		System.out.println("ClientFacade addAI()");
		return null;
	}

	public Create_Result createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)  {
		Create_Result result; 
		Create_Params request = new Create_Params(name, randomTiles, randomNumbers, randomPorts);		

		try {

			result = sp.createGame(request);

		} catch (ClientException e) {			
			result = new Create_Result();

			e.printStackTrace();
		}		

		return result;
	}

	/**
	 * Gets the current game state from the server.
	 * @param 
	 * @return
	 * 
	 * @pre Client is validated and participating in a game.
	 * @post Communicator will return usable PollServer_Result.
	 */
	public GetVersion_Result getVersion()  {
		//System.out.println("ClientFacade getVersion()");
		GetVersion_Params request;
		GetVersion_Result result; 
		
		if(Client.getInstance().getGame() == null) {
			request = new GetVersion_Params(-1);
		} else {
			request = new GetVersion_Params(Client.getInstance().getGame().getVersionNumber());		
		}
		
		try {

			result = sp.getVersion(request);
			if(!result.isUpToDate()){
				if(result.getGame() != null) {
					System.out.println("--------------------------UPDATING GAME---------------------------");
					System.out.println("-------------------------------------------------------------------");
					System.out.println("--------------------------------------------------------------------");
					updateGame(result.getGame());
				}
			}
			else{
				//System.out.println("Up to date.");
			}

		} catch (ClientException e) {			
			result = new GetVersion_Result();

			e.printStackTrace();
		}

		return result;
	}

	public Join_Result joinGame(int gameID, CatanColor color)  {
		System.out.println("ClientFacade joinGame()");
		Join_Result result; 
		Join_Params request = new Join_Params(gameID, color);		

		try {

			result = sp.joinGame(request);

		} catch (ClientException e) {			
			result = new Join_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public List_Result listGames()  {
		System.out.println("ClientFacade listGames()");
		List_Result result; 
		List_Params request = new List_Params();		

		try {

			result = sp.listGames(request);

		} catch (ClientException e) {			
			result = new List_Result();

			e.printStackTrace();
		}		

		return result;
	}

	/**
	 * Extra credit. Not implemented.
	 * 
	 * @param request
	 * @return
	 * @
	 */
	public ListAI_Result listAI()  {
		System.out.println("ClientFacade listAI()");
		return null;
	}

	public Register_Result register(String username, String password)  {
		System.out.println("ClientFacade register()");
		Register_Result result; 
		Register_Params request = new Register_Params(username, password);		

		try {

			result = sp.register(request);

		} catch (ClientException e) {			
			result = new Register_Result();

			e.printStackTrace();
		}		

		return result;
	}

	//misc commands


	public SendChat_Result sendChat(String content)  {
		System.out.println("ClientFacade sendChat()");
		int playerIndex = Client.getInstance().getPlayerIndex();

		SendChat_Result result; 
		SendChat_Params request = new SendChat_Params(playerIndex, content);		

		try {

			result = sp.sendChat(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new SendChat_Result();

			e.printStackTrace();
		}		

		return result;
	}	

	public AcceptTrade_Result acceptTrade(boolean willAccept)  {
		System.out.println("ClientFacade acceptTrade()");
		AcceptTrade_Result result; 
		AcceptTrade_Params request = new AcceptTrade_Params(0, willAccept);		

		try {

			result = sp.acceptTrade(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new AcceptTrade_Result();

			e.printStackTrace();
		}		

		return result;
	}


	public RollNumber_Result rollNumber(int number)  {
		System.out.println("ClientFacade rollNumber()");
		int playerIndex = Client.getInstance().getPlayerIndex();
		
		RollNumber_Result result; 
		RollNumber_Params request = new RollNumber_Params(playerIndex, number);		

		try {

			result = sp.rollNumber(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new RollNumber_Result();

			e.printStackTrace();
		}		

		return result;
	}


	public DiscardCards_Result discardCards(int brick, int ore, int sheep, int wheat, int wood)  {
		System.out.println("ClientFacade discardCards()");
		int playerIndex = Client.getInstance().getPlayerIndex();

		DiscardCards_Result result; 
		DiscardCards_Params request = new DiscardCards_Params(playerIndex, brick, ore, sheep, wheat, wood);		

		try {

			result = sp.discardCards(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new DiscardCards_Result();

			e.printStackTrace();
		}		

		return result;
	}

	//move

	public BuildCity_Result buildCity(VertexLocation location)  {
		System.out.println("ClientFacade buildCity()");
		
		int playerIndex = Client.getInstance().getPlayerIndex();

		BuildCity_Result result; 

		BuildCity_Params request = new BuildCity_Params(playerIndex, location);		
		try {

			result = sp.buildCity(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new BuildCity_Result();

			e.printStackTrace();
		}	

		return result;
	}

	public BuildRoad_Result buildRoad(EdgeLocation roadLocation)  {
		System.out.println("ClientFacade buildRoad()");
		
		int playerIndex = Client.getInstance().getPlayerIndex();
		System.out.println("\n\nPlayer Index: " + playerIndex + "\n\n");
		boolean free = Client.getInstance().getGame().isInSetUpPhase();
		
		BuildRoad_Result result; 
		BuildRoad_Params request = new BuildRoad_Params(playerIndex, roadLocation, free);		

		try {

			result = sp.buildRoad(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new BuildRoad_Result();

			e.printStackTrace();
		}		

		return result;	
	}

	public BuildSettlement_Result buildSettlement(VertexLocation location)  {
		System.out.println("ClientFacade buildSettlement()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		boolean free = Client.getInstance().getGame().isInSetUpPhase();

		BuildSettlement_Result result; 

		BuildSettlement_Params request = new BuildSettlement_Params(playerIndex, location, free);		
		try {

			result = sp.buildSettlement(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new BuildSettlement_Result();

			e.printStackTrace();
		}	

		return result;
	}

	public BuyDevCard_Result buyDevCard()  {
		System.out.println("ClientFacade buyDevCard()");
		int playerIndex = Client.getInstance().getPlayerIndex();
		BuyDevCard_Result result; 
		BuyDevCard_Params request = new BuyDevCard_Params(playerIndex);		

		try {

			result = sp.buyDevCard(request);
			updateGame(result.getGame());
			

		} catch (ClientException e) {			
			result = new BuyDevCard_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public FinishTurn_Result finishTurn()  {
		System.out.println("ClientFacade finishTurn()");
		int playerIndex = Client.getInstance().getPlayerIndex();
		FinishTurn_Result result; 
		FinishTurn_Params request = new FinishTurn_Params(playerIndex);		

		try {

			result = sp.finishTurn(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new FinishTurn_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public MaritimeTrade_Result maritimeTrade(int ratio, ResourceType inputResource, 
			ResourceType outputResource)  {
		System.out.println("ClientFacade maritimeTrade()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		MaritimeTrade_Result result; 
		MaritimeTrade_Params request = new MaritimeTrade_Params(playerIndex, ratio, inputResource, outputResource);		

		try {

			result = sp.maritimeTrade(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new MaritimeTrade_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public OfferTrade_Result offerTrade(int brick, int ore, int sheep, int wheat, int wood, int receiver)  {
		System.out.println("ClientFacade offerTrade()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		OfferTrade_Result result; 
		OfferTrade_Params request = new OfferTrade_Params(playerIndex, receiver, brick, ore, sheep, wheat, wood);		

		try {

			result = sp.offerTrade(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new OfferTrade_Result();

			e.printStackTrace();
		}		

		return result;

	}

	public RobPlayer_Result robPlayer(HexLocation hex, int victimIndex)  {
		System.out.println("ClientFacade robPlayer()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		RobPlayer_Result result; 
		RobPlayer_Params request = new RobPlayer_Params(playerIndex, hex, victimIndex);		

		try {

			result = sp.robPlayer(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new RobPlayer_Result();

			e.printStackTrace();
		}		

		return result;
	}

	//dev card play

	public PlayMonopoly_Result playMonopoly(ResourceType type)  {
		System.out.println("ClientFacade playMonopoly()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		PlayMonopoly_Result result; 
		PlayMonopoly_Params request = new PlayMonopoly_Params(playerIndex, type);		

		try {

			result = sp.playMonopoly(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new PlayMonopoly_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public PlayMonument_Result playMonument()  {
		System.out.println("ClientFacade playMonument()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		PlayMonument_Result result; 
		PlayMonument_Params request = new PlayMonument_Params(playerIndex);		

		try {

			result = sp.playMonument(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new PlayMonument_Result();

			e.printStackTrace();
		}		

		return result;

	}

	public PlayRoadBuilding_Result playRoadBuilding(EdgeLocation roadLocation1, 
			EdgeLocation roadLocation2)  {
		System.out.println("ClientFacade playRoadBuilding()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		PlayRoadBuilding_Result result; 
		PlayRoadBuilding_Params request = new PlayRoadBuilding_Params(playerIndex, roadLocation1, roadLocation2);		

		try {

			result = sp.playRoadBuilding(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new PlayRoadBuilding_Result();

			e.printStackTrace();
		}		

		return result;		
	}

	public PlaySoldier_Result playSoldier(HexLocation hex, int victimIndex)  {
		System.out.println("ClientFacade playSoldier()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		PlaySoldier_Result result; 
		PlaySoldier_Params request = new PlaySoldier_Params(playerIndex, victimIndex, hex);		

		try {

			result = sp.playSoldier(request);
			updateGame(result.getGame());

		} catch (ClientException e) {			
			result = new PlaySoldier_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public PlayYearOfPlenty_Result playYearOfPlenty(ResourceType resource1, ResourceType resource2)  {
		System.out.println("ClientFacade playYearOfPlenty()");

		int playerIndex = Client.getInstance().getPlayerIndex();
		PlayYearOfPlenty_Result result; 
		PlayYearOfPlenty_Params request = new PlayYearOfPlenty_Params(playerIndex, resource1, resource2);		

		try {

			result = sp.playYearOfPlenty(request);
			System.out.println(result.getGame().getCurrentPlayer().getPlayerName() + "In the Facade after talking to the proxy.");
			updateGame(result.getGame());
			
		} catch (ClientException e) {			
			result = new PlayYearOfPlenty_Result();

			e.printStackTrace();
		}		
		
		return result;
	}
	
	private void updateGame(Game game){
		System.out.println("ClientFacade updateGame()");
		
		Client.getInstance().setGame(game);
		return;
	}
}
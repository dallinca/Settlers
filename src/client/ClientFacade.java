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
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Sends all to-server requests to the client communicator for packaging
 * Interprets responses, handles errors from requests when responses are invalid.
 * 
 * @author jchrisw
 *
 */
public class ClientFacade {

	private IServerProxy sp;
	private Client c;

	/**
	 * Creates fascade, specifying the location of the master server.
	 * @param serverName
	 * @param portNumber
	 * 
	 * @pre Server name and port number specifiy an existing server.
	 * @post Client will be able to communicate with server.
	 */
	ClientFacade(IServerProxy proxy, Client client){
		sp = proxy;
		c = client;
	}

	public ClientFacade(){		
		sp = new ServerProxy();				
	}

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

		Login_Result result; 
		Login_Params request = new Login_Params(username, password);		

		try {

			result = sp.login(request);

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

	}

	/**
	 * extra credit
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		return null;
	}

	public Create_Result createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) throws ClientException {
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
	public GetVersion_Result getVersion() throws ClientException {

		GetVersion_Result result; 
		GetVersion_Params request = new GetVersion_Params(c.getGame().getVersionNumber());		

		try {

			result = sp.getVersion(request);

		} catch (ClientException e) {			
			result = new GetVersion_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public Join_Result joinGame(int gameID, CatanColor color) throws ClientException {
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

	public List_Result listGames() throws ClientException {
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
	 * @throws ClientException
	 */
	public ListAI_Result listAI() throws ClientException {
		return null;
	}

	public Register_Result register(String username, String password) throws ClientException {
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


	public SendChat_Result sendChat(String content) throws ClientException {
		int playerIndex = c.getPlayerIndex();

		SendChat_Result result; 
		SendChat_Params request = new SendChat_Params(playerIndex, content);		

		try {

			result = sp.sendChat(request);

		} catch (ClientException e) {			
			result = new SendChat_Result();

			e.printStackTrace();
		}		
		
		return result;
	}	

	public AcceptTrade_Result acceptTrade(boolean willAccept) throws ClientException {
		AcceptTrade_Result result; 
		AcceptTrade_Params request = new AcceptTrade_Params(willAccept);		

		try {

			result = sp.acceptTrade(request);

		} catch (ClientException e) {			
			result = new AcceptTrade_Result();

			e.printStackTrace();
		}		

		return result;
	}


	public RollNumber_Result rollNumber(int number) throws ClientException {
		RollNumber_Result result; 
		RollNumber_Params request = new RollNumber_Params(number);		

		try {

			result = sp.rollNumber(request);

		} catch (ClientException e) {			
			result = new RollNumber_Result();

			e.printStackTrace();
		}		

		return result;
	}


	public DiscardCards_Result discardCards(int brick, int ore, int sheep, int wheat, int wood) throws ClientException {

		int playerIndex = c.getPlayerIndex();

		DiscardCards_Result result; 
		DiscardCards_Params request = new DiscardCards_Params(playerIndex, brick, ore, sheep, wheat, wood);		

		try {

			result = sp.discardCards(request);

		} catch (ClientException e) {			
			result = new DiscardCards_Result();

			e.printStackTrace();
		}		

		return result;
	}

	//move

	public BuildCity_Result buildCity(BuildCity_Params request) throws ClientException {
		return null;
	}

	public BuildRoad_Result buildRoad(BuildRoad_Params request) throws ClientException {
		return null;
	}

	public BuildSettlement_Result buildSettlement(VertexLocation location) throws ClientException {

		int playerIndex = c.getPlayerIndex();
		boolean free = c.getGame().isInSetUpPhase();

		BuildSettlement_Result result; 
		BuildSettlement_Params request = new BuildSettlement_Params(playerIndex, location, free);		

		try {

			result = sp.buildSettlement(request);

		} catch (ClientException e) {			
			result = new BuildSettlement_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public BuyDevCard_Result buyDevCard() throws ClientException {
		BuyDevCard_Result result; 
		BuyDevCard_Params request = new BuyDevCard_Params(c.getPlayerIndex());		

		try {

			result = sp.buyDevCard(request);

		} catch (ClientException e) {			
			result = new BuyDevCard_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public FinishTurn_Result finishTurn() throws ClientException {
		FinishTurn_Result result; 
		FinishTurn_Params request = new FinishTurn_Params(c.getPlayerIndex());		

		try {

			result = sp.finishTurn(request);

		} catch (ClientException e) {			
			result = new FinishTurn_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public MaritimeTrade_Result maritimeTrade(int ratio, ResourceType inputResource, 
			ResourceType outputResource) throws ClientException {
		
		MaritimeTrade_Result result; 
		MaritimeTrade_Params request = new MaritimeTrade_Params(c.getPlayerIndex(), ratio, inputResource, outputResource);		

		try {

			result = sp.maritimeTrade(request);

		} catch (ClientException e) {			
			result = new MaritimeTrade_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public OfferTrade_Result offerTrade(int brick, int ore, int sheep, int wheat, int wood, int receiver) throws ClientException {
		
		OfferTrade_Result result; 
		OfferTrade_Params request = new OfferTrade_Params(c.getPlayerIndex(), receiver, brick, ore, sheep, wheat, wood);		

		try {

			result = sp.offerTrade(request);

		} catch (ClientException e) {			
			result = new OfferTrade_Result();

			e.printStackTrace();
		}		

		return result;
		
	}

	public RobPlayer_Result robPlayer(HexLocation hex, int victimIndex) throws ClientException {
		
		RobPlayer_Result result; 
		RobPlayer_Params request = new RobPlayer_Params(c.getPlayerIndex(), hex, victimIndex);		

		try {

			result = sp.robPlayer(request);

		} catch (ClientException e) {			
			result = new RobPlayer_Result();

			e.printStackTrace();
		}		

		return result;
	}

	//dev card play

	public PlayMonopoly_Result playMonopoly(ResourceType type) throws ClientException {
		
		PlayMonopoly_Result result; 
		PlayMonopoly_Params request = new PlayMonopoly_Params(c.getPlayerIndex(), type);		

		try {

			result = sp.playMonopoly(request);

		} catch (ClientException e) {			
			result = new PlayMonopoly_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public PlayMonument_Result playMonument() throws ClientException {
		
		PlayMonument_Result result; 
		PlayMonument_Params request = new PlayMonument_Params(c.getPlayerIndex());		

		try {

			result = sp.playMonument(request);

		} catch (ClientException e) {			
			result = new PlayMonument_Result();

			e.printStackTrace();
		}		

		return result;
		
	}

	public PlayRoadBuilding_Result playRoadBuilding(EdgeLocation spot1, EdgeLocation spot2) throws ClientException {
	
		PlayRoadBuilding_Result result; 
		PlayRoadBuilding_Params request = new PlayRoadBuilding_Params(c.getPlayerIndex(), spot1, spot2);		

		try {

			result = sp.playRoadBuilding(request);

		} catch (ClientException e) {			
			result = new PlayRoadBuilding_Result();

			e.printStackTrace();
		}		

		return result;		
	}

	public PlaySoldier_Result playSoldier(HexLocation hex, int victimIndex) throws ClientException {
		
		PlaySoldier_Result result; 
		PlaySoldier_Params request = new PlaySoldier_Params(c.getPlayerIndex(), victimIndex, hex);		

		try {

			result = sp.playSoldier(request);

		} catch (ClientException e) {			
			result = new PlaySoldier_Result();

			e.printStackTrace();
		}		

		return result;
	}

	public PlayYearOfPlenty_Result playYearOfPlenty(ResourceType type1, ResourceType type2) throws ClientException {
		
		PlayYearOfPlenty_Result result; 
		PlayYearOfPlenty_Params request = new PlayYearOfPlenty_Params(c.getPlayerIndex(), type1, type2);		

		try {

			result = sp.playYearOfPlenty(request);

		} catch (ClientException e) {			
			result = new PlayYearOfPlenty_Result();

			e.printStackTrace();
		}		

		return result;
	}



}
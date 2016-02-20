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

/**
 * Sends all to-server requests to the client communicator for packaging
 * Interprets responses, handles errors from requests when responses are invalid.
 * 
 * @author jchrisw
 *
 */
public class ClientFacade {

	private IServerProxy sp;

	/**
	 * Creates fascade, specifying the location of the master server.
	 * @param serverName
	 * @param portNumber
	 * 
	 * @pre Server name and port number specifiy an existing server.
	 * @post Client will be able to communicate with server.
	 */
	ClientFacade(IServerProxy proxy){
		sp = proxy;
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

	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		return null;
	}

	public Create_Result createGame(Create_Params request) throws ClientException {
		return null;
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

	public Join_Result joinGame(Join_Params request) throws ClientException {
		return null;
	}

	public List_Result listGames(List_Params request) throws ClientException {
		return null;
	}

	public ListAI_Result listAI(ListAI_Params request) throws ClientException {
		return null;
	}

	/**
	 * TODO
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public Login_Result login(Login_Params request) throws ClientException {
		Login_Result result = new Login_Result();
		if(request.getUsername().equals("mack")) {
			result.setWasLoggedIn(true);
		} else {
			result.setWasLoggedIn(false);
		}
		return result;
	}

	/**
	 * TODO
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public Register_Result register(Register_Params request) throws ClientException {
		Register_Result result = new Register_Result();
		if(request.getUsername().equals("puppy")) {
			result.setWasRegistered(true);
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



}
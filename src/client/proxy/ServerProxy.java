package client.proxy;

import client.ClientException;
import shared.communication.params.move.*;
import shared.communication.params.move.devcard.*;
import shared.communication.params.nonmove.*;
import shared.communication.results.move.*;
import shared.communication.results.move.devcard.*;
import shared.communication.results.nonmove.*;

/**
 * 
 * Communication object for server.
 * 
 */
public class ServerProxy implements IServerProxy {

	private static String SERVER_HOST;
	private static int SERVER_PORT;
	private static String URL_PREFIX;
	private static final String HTTP_POST = "POST";
	
	
	/**
	 * 
	 * @param serverHost Name of server
	 * @param serverPort Port number on which to connect to server.
	 * 
	 * @pre serverPort and serverHost specify an existing server.
	 * @post The client communicator will know how to communicate with the server.
	 */
	
	public ServerProxy(String serverHost, int serverPort){
		SERVER_HOST = serverHost;
		SERVER_PORT = serverPort;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}

	public ServerProxy(){
		SERVER_HOST = "localhost";
		SERVER_PORT = 39640;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	

	public Object doPost(String urlString, Object request) throws ClientException
	{
		Object result = null;

		return result;		
	}

	@Override
	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeLogLevel_Result changeLogLevel(ChangeLogLevel_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Create_Result createGame(Create_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetCommands_Result getCommands(GetCommands_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetVersion_Result getVersion(GetVersion_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Join_Result joinGame(Join_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List_Result listGames(List_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListAI_Result listAI(ListAI_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Load_Result loadGame(Load_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Login_Result login(Login_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostCommands_Result postCommands(PostCommands_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register_Result register(Register_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Save_Result saveGame(Save_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildCity_Result buildCity(BuildCity_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildRoad_Result buildRoad(BuildRoad_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscardCards_Result discardCards(DiscardCards_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinishTurn_Result finishTurn(FinishTurn_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfferTrade_Result offerTrade(OfferTrade_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RobPlayer_Result robPlayer(RobPlayer_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RollNumber_Result rollNumber(RollNumber_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SendChat_Result sendChat(SendChat_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayMonument_Result playMonument(PlayMonument_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayRoadBuilding_Result playRoadBuilding(
			PlayRoadBuilding_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaySoldier_Result playSolder(PlaySoldier_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayYearOfPlenty_Result playYearOfPlenty(
			PlayYearOfPlenty_Params result) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}
}

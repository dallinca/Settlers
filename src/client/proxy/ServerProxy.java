package client.proxy;

import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

	private String userCookie;
	private String gameCookie;
	
	//private java.net.CookieManager manager;
	
	private CloseableHttpClient httpClient; 

	private static String SERVER_HOST;
	private static int SERVER_PORT;
	private static String URL_PREFIX;
	private static final String HTTP_POST = "POST";
	private String URL_SUFFIX;
	
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
		
		httpClient = HttpClientBuilder.create().build();
		
		//manager = new java.net.CookieManager();
	}

	public ServerProxy(){
		SERVER_HOST = "localhost";
		SERVER_PORT = 8081;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		
		httpClient = HttpClientBuilder.create().build();
		//manager = new java.net.CookieManager();
	}
	
	@Override
	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		URL_SUFFIX = "/game/addAI";
		
		return (AddAI_Result) doPost(URL_SUFFIX, request);
	}

/*	@Override
	public ChangeLogLevel_Result changeLogLevel(ChangeLogLevel_Params request)
			throws ClientException {
		URL_SUFFIX = "/util/changeLogLevel";
		
		return (ChangeLogLevel_Result) doPost(URL_SUFFIX, request);
	}*/

	@Override
	public Create_Result createGame(Create_Params request)
			throws ClientException {
		URL_SUFFIX = "/games/create";
		return (Create_Result) doPost(URL_SUFFIX, request);
	}

/*	@Override
	public GetCommands_Result getCommands(GetCommands_Params request)
			throws ClientException {
		URL_SUFFIX = "/game/commands[get]";
		return (GetCommands_Result) doPost(URL_SUFFIX, request);
	}*/

	@Override
	public GetVersion_Result getVersion(GetVersion_Params request)
			throws ClientException {
		URL_SUFFIX = "/game/model?version=N";
		return (GetVersion_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public Join_Result joinGame(Join_Params request) throws ClientException {
		URL_SUFFIX = "/games/join";
		return (Join_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public List_Result listGames(List_Params request) throws ClientException {
		URL_SUFFIX = "/games/list";
		return (List_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public ListAI_Result listAI(ListAI_Params request) throws ClientException {
		URL_SUFFIX = "/game/listAI";
		return (ListAI_Result) doPost(URL_SUFFIX, request);
	}
/*
	@Override
	public Load_Result loadGame(Load_Params request) throws ClientException {
		URL_SUFFIX = "/games/load";
		return (Load_Result) doPost(URL_SUFFIX, request);
	}*/

	@Override
	public Login_Result login(Login_Params request) throws ClientException {
		URL_SUFFIX = "/user/login";
		return (Login_Result) doPost(URL_SUFFIX, request);
	}

/*	@Override
	public PostCommands_Result postCommands(PostCommands_Params request)
			throws ClientException {
		URL_SUFFIX = "/game/commands[post]";
		return (PostCommands_Result) doPost(URL_SUFFIX, request);
	}*/

	@Override
	public Register_Result register(Register_Params request)
			throws ClientException {
		URL_SUFFIX = "/user/register";
		return (Register_Result) doPost(URL_SUFFIX, request);
	}

/*	@Override
	public Save_Result saveGame(Save_Params request) throws ClientException {
		URL_SUFFIX = "/games/save";
		return (Save_Result) doPost(URL_SUFFIX, request);
	}*/
	
	/*	@Override
	public Reset_Result resetGame(Reset_Params request) throws ClientException {
		URL_SUFFIX = "/game/reset";
		return (Reset_Result) doPost(URL_SUFFIX, request);
	}*/
	
	//moves

	@Override
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/acceptTrade";
		return (AcceptTrade_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuildCity_Result buildCity(BuildCity_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/buildCity";
		return (BuildCity_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuildRoad_Result buildRoad(BuildRoad_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/buildRoad";
		return (BuildRoad_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/buildSettlement";
		return (BuildSettlement_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/buyDevCard";
		return (BuyDevCard_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public DiscardCards_Result discardCards(DiscardCards_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/discardCards";
		return (DiscardCards_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public FinishTurn_Result finishTurn(FinishTurn_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/finishTurn";
		return (FinishTurn_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/maritimeTrade";
		return (MaritimeTrade_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public OfferTrade_Result offerTrade(OfferTrade_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/offerTrade";
		return (OfferTrade_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public RobPlayer_Result robPlayer(RobPlayer_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/robPlayer";
		return (RobPlayer_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public RollNumber_Result rollNumber(RollNumber_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/rollNumber";
		return (RollNumber_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public SendChat_Result sendChat(SendChat_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/sendChat";
		return (SendChat_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/playMonopoly";
		return (PlayMonopoly_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public PlayMonument_Result playMonument(PlayMonument_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/playMonument";
		return (PlayMonument_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public PlayRoadBuilding_Result playRoadBuilding(
			PlayRoadBuilding_Params request) throws ClientException {
		URL_SUFFIX = "/moves/playRoadBuilding";
		return (PlayRoadBuilding_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public PlaySoldier_Result playSolder(PlaySoldier_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/playSoldier";
		return (PlaySoldier_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public PlayYearOfPlenty_Result playYearOfPlenty(
			PlayYearOfPlenty_Params request) throws ClientException {
		URL_SUFFIX = "/moves/playYearOfPlenty";
		return (PlayYearOfPlenty_Result) doPost(URL_SUFFIX, request);
	}
	
	@Override
	public Object doPost(String urlString, Object request)
			throws ClientException {
		
		urlString = URL_SUFFIX;
		
		try {
			Gson gson = new Gson();

			URL url = new URL(URL_PREFIX + URL_SUFFIX);
			
			/*HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);			
			connection.connect();			
			gson.toJson(request, connection.getOutputStream());			
			connection.getOutputStream().close();*/			

			HttpPost post = new HttpPost(url.toString());
			StringEntity params = new StringEntity(gson.toJson(request));
		//	post.addHeader("Cookie", clientCookie);
			post.setEntity(params);
			HttpResponse response = httpClient.execute(post);
			
			
			Header[] headers = response.getHeaders("set-cookie");
						
			if (URL_SUFFIX.equals("/user/login") ){ //Cache cookies
				userCookie = headers[0].toString();
			}
			else if (URL_SUFFIX.equals("/games/join")){
				gameCookie = headers[0].toString();				
			}
						
			HttpEntity e = response.getEntity();					
			ObjectInputStream in = new ObjectInputStream(e.getContent()); //Java			
			JsonObject jsonResult = (JsonObject)in.readObject();			
			Object result = gson.fromJson(jsonResult, request.getClass());
			
			return result;			
			
		}	 
		catch (Exception ex) {
			// handle exception here
		} finally {
			//finally stuff
		}
		return null;
	}

	
}

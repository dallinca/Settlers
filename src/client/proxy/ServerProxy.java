package client.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import netscape.javascript.JSObject;

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

		userCookie = "";
		gameCookie = "";
	}

	public ServerProxy(){
		SERVER_HOST = "localhost";
		SERVER_PORT = 8081;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;

		userCookie = "";
		gameCookie = "";
	}
	
	public String getUserCookie(){
		return userCookie;
	}
	public String getGameCookie(){
		return gameCookie;
	}

	@Override
	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		URL_SUFFIX = "/game/addAI";

		return (AddAI_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public Create_Result createGame(Create_Params request)
			throws ClientException {
		URL_SUFFIX = "/games/create";
		return (Create_Result) doPost(URL_SUFFIX, request);
	}

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

	@Override
	public Login_Result login(Login_Params request) throws ClientException {
		URL_SUFFIX = "/user/login";
		return (Login_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public Register_Result register(Register_Params request)
			throws ClientException {
		URL_SUFFIX = "/user/register";
		return (Register_Result) doPost(URL_SUFFIX, request);
	}

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
	public PlaySoldier_Result playSoldier(PlaySoldier_Params request)
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
		//System.out.println("Doing post!");

		URL_SUFFIX = urlString;

		try {

			URL url = new URL(URL_PREFIX + URL_SUFFIX);

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);	

			Gson gson = new Gson();
			String job = gson.toJson(request);		

			//Append those cookies client already has--------------------
			if (!gameCookie.equals("")){
				connection.addRequestProperty("Cookie", userCookie+"; "+gameCookie);
			}
			else if (!userCookie.equals("")){
				connection.addRequestProperty("Cookie", userCookie);
			}

		//	System.out.println(url);
		//	System.out.println(connection.toString());
			//System.out.println("Job before: "+job);
			
			connection.connect(); // sends cookies


			ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			out.writeObject(job);
			out.close();
			
			//System.out.println("Receiving response from server.");
			
			//connection.getOutputStream().close();
			
			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			job = (String) in.readObject();
			in.close();
			
			//System.out.println("Job after: "+job);

			//System.out.println("Caching cookies.");
			//Cookie cacher----------------------------------
			Map<String, List<String>> headers = connection.getHeaderFields();

			if (URL_SUFFIX.equals("/user/login")){
				userCookie = headers.get("Set-cookie").get(0);

				if (userCookie.endsWith("Path=/;")) {
					userCookie = userCookie.substring(0, userCookie.length() - 7);
				}
			}
			else if (URL_SUFFIX.equals("/games/join")){
				gameCookie = headers.get("Set-cookie").get(0);

				if (gameCookie.endsWith("Path=/;")) {
					gameCookie = gameCookie.substring(0, gameCookie.length() - 7);
				}
			}

			//String decodedUserData = URLDecoder.decode(userCookie, "UTF-8");

			return job;

		}	 
		catch (Exception ex) {
			//System.out.print("An exception occurred: ");
			//System.out.println(ex.getMessage());
		} finally {
			//finally stuff
		}
		return null;
	}


}

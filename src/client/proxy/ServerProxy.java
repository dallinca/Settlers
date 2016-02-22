package client.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

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
import client.ClientException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
	private int playerID;


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
		playerID = -1;
	}

	public ServerProxy(){
		SERVER_HOST = "localhost";
		SERVER_PORT = 8081;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;

		userCookie = "";
		gameCookie = "";
		playerID = -1;
	}

	public String getUserCookie(){
		return userCookie;
	}
	public String getGameCookie(){
		return gameCookie;
	}
	
	public int getPlayerID(){
		return playerID;
	}

	@Override
	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		System.out.println("Add AI.");
		URL_SUFFIX = "/game/addAI";

		return (AddAI_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public Create_Result createGame(Create_Params request)
			throws ClientException {
		System.out.println("Create game.");
		URL_SUFFIX = "/games/create";
		return (Create_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public GetVersion_Result getVersion(GetVersion_Params request)
			throws ClientException {
		System.out.println("Get version.");
		URL_SUFFIX = "/game/model?version=N";
		return (GetVersion_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public Join_Result joinGame(Join_Params request) throws ClientException {
		System.out.println("Join game.");
		URL_SUFFIX = "/games/join";
		return (Join_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public List_Result listGames(List_Params request) throws ClientException {
		System.out.println("List games.");
		URL_SUFFIX = "/games/list";
		return (List_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public ListAI_Result listAI(ListAI_Params request) throws ClientException {
		System.out.println("List AI.");
		URL_SUFFIX = "/game/listAI";
		return (ListAI_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public Login_Result login(Login_Params request) throws ClientException {
		System.out.println("Login.");
		URL_SUFFIX = "/user/login";
		
		return new Login_Result((String) doPost(URL_SUFFIX, request), playerID, request.getUsername());
	}

	@Override
	public Register_Result register(Register_Params request)
			throws ClientException {
		System.out.println("Register.");
		URL_SUFFIX = "/user/register";
		return new Register_Result((String) doPost(URL_SUFFIX, request), playerID, request.getUsername());
	}

	//moves

	@Override
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request)
			throws ClientException {
		System.out.println("Accept trade.");
		URL_SUFFIX = "/moves/acceptTrade";
		return (AcceptTrade_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuildCity_Result buildCity(BuildCity_Params request)
			throws ClientException {
		System.out.println("Build city.");
		URL_SUFFIX = "/moves/buildCity";
		return (BuildCity_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuildRoad_Result buildRoad(BuildRoad_Params request)
			throws ClientException {
		System.out.println("Build road.");
		URL_SUFFIX = "/moves/buildRoad";
		return (BuildRoad_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request)
			throws ClientException {
		System.out.println("Build settlement.");
		URL_SUFFIX = "/moves/buildSettlement";
		return (BuildSettlement_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request)
			throws ClientException {
		System.out.println("Buy dev card.");
		URL_SUFFIX = "/moves/buyDevCard";
		return (BuyDevCard_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public DiscardCards_Result discardCards(DiscardCards_Params request)
			throws ClientException {
		System.out.println("Discard cards.");
		URL_SUFFIX = "/moves/discardCards";
		return (DiscardCards_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public FinishTurn_Result finishTurn(FinishTurn_Params request)
			throws ClientException {
		System.out.println("Finish turn.");
		URL_SUFFIX = "/moves/finishTurn";
		return (FinishTurn_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request)
			throws ClientException {
		System.out.println("Maritime trade.");
		URL_SUFFIX = "/moves/maritimeTrade";
		return (MaritimeTrade_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public OfferTrade_Result offerTrade(OfferTrade_Params request)
			throws ClientException {
		System.out.println("Offer trade.");
		URL_SUFFIX = "/moves/offerTrade";
		return (OfferTrade_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public RobPlayer_Result robPlayer(RobPlayer_Params request)
			throws ClientException {
		System.out.println("Rob player.");
		URL_SUFFIX = "/moves/robPlayer";
		return (RobPlayer_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public RollNumber_Result rollNumber(RollNumber_Params request)
			throws ClientException {
		System.out.println("Roll number.");
		URL_SUFFIX = "/moves/rollNumber";
		return (RollNumber_Result) doPost(URL_SUFFIX, request);
	}

	@Override
	public SendChat_Result sendChat(SendChat_Params request)
			throws ClientException {
		System.out.println("Sending chat.");
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
		
		HttpURLConnection connection = null;

		try {	

			URL url = new URL(URL_PREFIX + URL_SUFFIX);
			
			connection = (HttpURLConnection)url.openConnection();

			Gson gson = new Gson();
			String job = gson.toJson(request);	

			//Append those cookies client already has--------------------
			if (!gameCookie.equals("")){
				System.out.println("Adding cookies");
				connection.setRequestProperty("Cookie", userCookie+"; "+gameCookie);
			}
			else if (!userCookie.equals("")){
				System.out.println("Adding user cookie");
				connection.setRequestProperty("Cookie", userCookie);
			}

			//System.out.println(url);Login_Params
			//System.out.println(connection.toString());
			System.out.println("Job before: "+job);

			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);	
			
			connection.connect(); // sends cookies
			
			//ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			OutputStreamWriter sw = new OutputStreamWriter(connection.getOutputStream());
			//out.writeObject(job);
			sw.write(job);
			sw.flush();
			//System.out.println("Input stream: " + connection.getOutputStream().toString());
			//sw.close();			
			
			//connection.getOutputStream().close();
			 
			//System.out.println("Receiving response from server.");
			//connection.getOutputStream().close();
			
			System.out.println("Response code: "+connection.getResponseCode());

			InputStream in = connection.getInputStream();

			int len = 0;
			
			//System.out.println("Input stream received.");
			
			byte[] buffer = new byte[1024];
			
			StringBuilder sb = new StringBuilder();

			//in.close();
			while (-1 != (len = in.read(buffer))){
				sb.append(new String(buffer, 0, len));
			}
			
			/*if (sb.length()==0){
				return null;
			}
			if (sb.charAt(0)=='['){
				sb = new StringBuilder("{\"list\":"+ sb + ")");
			}
			else if (sb.charAt(0) != '{'){
				return null;
			}*/
			
			job = sb.toString();
			
			in.close();
			
			//System.out.println("Stream closed.");

			System.out.println("Job after: "+job);

			//System.out.println("Caching cookies=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=.");
			//Cookie cacher----------------------------------
			Map<String, List<String>> headers = connection.getHeaderFields();

			if (URL_SUFFIX.equals("/user/login") || URL_SUFFIX.equals("/user/register")){
				userCookie = headers.get("Set-cookie").get(0);
				
				if (userCookie.endsWith("Path=/;")) {
					userCookie = userCookie.substring(0, userCookie.length() - 8);
					userCookie = userCookie.substring(11, userCookie.length());
				}
				
				System.out.println("Ammended cookie: "+ userCookie);
				String decodedUserData = URLDecoder.decode(userCookie, "UTF-8");				
				//System.out.println("Decoded Cookie: "+decodedUserData);				
				JsonObject jobj = new Gson().fromJson(decodedUserData, JsonObject.class);
				playerID = Integer.parseInt(jobj.get("playerID").toString());	
				
			}
			else if (URL_SUFFIX.equals("/games/join")){
				gameCookie = headers.get("Set-cookie").get(0);
				
				System.out.println("Game cookie: "+ gameCookie);

				/*if (gameCookie.endsWith("Path=/;")) {
					gameCookie = gameCookie.substring(0, gameCookie.length() - 8);
					gameCookie = gameCookie.substring(11, gameCookie.length());
				}*/
			}

			//String decodedUserData = URLDecoder.decode(userCookie, "UTF-8");
			
			return job;

		}	 
		catch (Exception ex) {
			System.out.print("An exception occurred: ");
			System.out.println(ex.getMessage());
			InputStream is = connection.getErrorStream();
			
			InputStreamReader error = new InputStreamReader(is);
			BufferedReader bre = new BufferedReader(error);
			String eline;
			try {
				while ((eline = bre.readLine()) != null) {
				        System.out.println(eline);
				    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
			
			
		} finally {
			//finally stuff
		}
		return null;
	}


}

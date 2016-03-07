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
import shared.communication.results.nonmove.Reset_Result;
import client.Client;
import client.ClientException;
import client.ClientFacade;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * Communication object for server.
 * 
 */
public class ServerProxy implements IServerProxy {

	private String userCookie;
	private String gameCookie;
	private String unfixedUserCookie;
	private String unfixedGameCookie;

	private static String SERVER_HOST;
	private static int SERVER_PORT;
	private static String URL_PREFIX;
	private static final String HTTP_POST = "POST";
	private String URL_SUFFIX;
	private int playerID;
	private int gameID;

	/**
	 * 
	 * @param serverHost Name of server
	 * @param serverPort Port number on which to connect to server.
	 * 
	 * @pre serverPort and serverHost specify an existing server.
	 * @post The client communicator will know how to communicate with the server.
	 */

	/*private ServerProxy SINGLETON = null;
	private ServerProxy SINGLETON2 = null;
	private ServerProxy SINGLETON3 = null;
	private ServerProxy SINGLETON4 = null;*/

	public ServerProxy(){
		SERVER_HOST = "localhost";
		SERVER_PORT = 8081;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;

		userCookie = "";
		gameCookie = "";
		playerID = -1;
	}
	
	/*private ServerProxy(ServerProxy s){
		s = new ServerProxy();		
	}

	public static ServerProxy getInstance(){
		if (SINGLETON == null){
			SINGLETON = new ServerProxy(SINGLETON);
		}
		return SINGLETON;
	}
	public static ServerProxy getInstance2(){
		if (SINGLETON2 == null){
			SINGLETON2 = new ServerProxy(SINGLETON2);
		}
		return SINGLETON2;
	}
	public static ServerProxy getInstance3(){
		if (SINGLETON3 == null){
			SINGLETON3 = new ServerProxy(SINGLETON3);
		}
		return SINGLETON3;
	}
	public static ServerProxy getInstance4(){
		if (SINGLETON4 == null){
			SINGLETON4 = new ServerProxy(SINGLETON4);
		}
		return SINGLETON4;
	}*/
	
	public ServerProxy(String serverHost, int serverPort){
		userCookie = "";
		gameCookie = "";
		playerID = -1;
		setupProxy(serverHost, serverPort);		
	}
	
	public void setupProxy(String serverHost, int serverPort){
		SERVER_HOST = serverHost;
		SERVER_PORT = serverPort;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		return;
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

	public int getGameID(){
		return gameID;
	}	

	@Override
	/**
	 * Creates a new game on the server.
	 * 
	 * 
	 * @param request Create_Params
	 * @return result Create_Result
	 * @throws ClientException
	 * 
	 * @pre name!=null
	 * randomTiles, randomNumbers, and randomPorts contain valid boolean values. 
	 * @post If the operation succeeds,
	 * 			1. A new game with specified properties has been created
	 * 			2. The server returns an HTTP 200 success response.
	 * 			3. The body contains a JSon object describing the newly created game.
	 * 
	 * 		If the operation fails
	 * 			1. The server returns and HTTP 400 error response, and the body contains an error
	 * 		message.
	 */

	public Create_Result createGame(Create_Params request)
			throws ClientException {
		//System.out.println("Create game.");
		URL_SUFFIX = "/games/create";

		String j = (String) doPost(URL_SUFFIX, request);

		JsonObject jobj = new Gson().fromJson(j, JsonObject.class);

		return new Create_Result(jobj);
	}

	/**
	 * Returns the current state of the game in JSON format.
	 * 
	 * In addition to the current game state, the returned JSON also includes a "version" number for
	 * the client model. The next time /game/model is called, the version number from the
	 * previously retrieved model may optionally be included as a query parameter in the request
	 * (/game/model?version=N). The server will only  return the full JSON game state if its version
	 * number is not equal to N. If it is equal to N, the server returns "true" to indicate that the caller
	 * already has the latest game state. This is merely an optimization. if the version number is 
	 * not included in the request URL, the server will return the full game state.
	 * 
	 * @param request GetVersion_Params
	 * @return result GetVersion_Result
	 * @throws ClientException
	 * 
	 * @pre 1. The caller has previously logged in to the server and joined a game (i.e., they have
	 * 	valid catan.user and catan.game HTTP cookies).
	 * 		2. If specified, the version number is included as the "version" query parameter in the
	 * 	request URL, and its value is a valid integer.
	 * @post If the operation succeeds,
	 * 		1. The server returns an HTTP 200 success response.
	 * 		2. The response body contains JSON data
	 * 			a. The full client model JSON is returned if the caller does not provide a version
	 * 				number, or the provided version number does not match the version on the server
	 * 			b. "true" (true in double quotes) is returned if the caller provided a version number,
	 * 				and the version number matched the version number on the server.
	 * 	
	 * If the operation fails,
	 * 		1. The server returns an HTTP 400 error response, and the body contains an error message.
	 * 
	 */
	@Override
	public GetVersion_Result getVersion(GetVersion_Params request)
			throws ClientException {
		//	System.out.println("Get version.");
		StringBuilder sb = new StringBuilder();
		sb.append("/game/model?version=");
		if (Client.getInstance().getGame()==null){
			sb.append(-1);
		}
		else{
			sb.append(Client.getInstance().getGame().getVersionNumber());
		}
		URL_SUFFIX = sb.toString();

		return new GetVersion_Result((String) doPost(URL_SUFFIX, request));
	}


	public Reset_Result reset(Reset_Params request)
			throws ClientException {

		URL_SUFFIX = "/game/reset";

		System.out.println("Game reset: "+(String)doPost(URL_SUFFIX, request));
		return new Reset_Result();
	}

	/**
	 * Adds the player to the specified game and sets their catan.game cookie. (See the document
	 * titled "How the Catan Server USes HTTP Cookies" for more details on cookies.)
	 *  
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 * 
	 * @pre 1. The user has previously logged in to the server (i.e., they have a valid catan.user HTTP
	 * cookie).
	 * 		2. The player may join the game because
	 * 			2a. They are already in the game, OR
	 * 			2b. There is space in the game to add a new player
	 * 		3. The specified game ID is valid
	 * 		4. The specified color is valid (red, green, blue, yellow, puce, brown, white, purple,
	 * orange)
	 * @post If the operation succeeds,
	 * 			1. The server returns an HTTP 200 success reponse with "Success" in the body.
	 * 			2. The player is in the game with the specified color (i.e. calls to /games/list method wil
	 * 		show the player in the game with the chosen color).
	 * 			3. The server response includes the "Set-cookie" response header setting the catan.game
	 * 		HTTP cookie
	 * 
	 * 		If the operation fails,
	 * 			1. The server returns an HTTP 400 error response, and the body contains an error
	 * 		message.
	 */
	@Override
	public Join_Result joinGame(Join_Params request) throws ClientException {
		//System.out.println("Joining game.");
		URL_SUFFIX = "/games/join";
		return new Join_Result((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Returns information about all of the current games on the server.
	 * 
	 * 
	 * @param request List_Params
	 * @return result List_Result
	 * @throws ClientException
	 * 
	 * @pre None.
	 * @post If the operation succeeds,
	 * 			1. The server returns an HTTP 200 success response.
	 * 			2. The body contains a JSON array containing a list of objects that contain information
	 * 		about the server's games.
	 * 	
	 * 		If the operation fails,
	 * 			1. The server returns an HTTP 400 response, and the body contains an error
	 * 		message.
	 */
	@Override
	public List_Result listGames(List_Params request) throws ClientException {
		//System.out.println("List games.");
		URL_SUFFIX = "/games/list";

		String j = (String) doPost(URL_SUFFIX, request);

		JsonArray jobj = new Gson().fromJson(j, JsonArray.class);

		return new List_Result(jobj);
	}



	/**
	 * Logs the caller in to the server, and sets their catan.user HTTP cookie.
	 * 
	 * 
	 * @param request Login_Params
	 * @return result Login_Result
	 * @throws ClientException
	 * 
	 * @pre Username and password are not null.
	 * @post If passed-in (username, password) pair is valid,
	 * 		1. The server reutnrs a HTTP 200 success response with "Success" in the body.
	 * 		2. The HTTP response headers set the catan.user cookie to contain the identity of the
	 * 			logged-in player. The cookie uses "path=/", and its value contains a url-encoded JSON object of
	 * 			the following form: ("name": STRING, "password": STRING, "playerID": INTEGER}. For
	 * 			example, {"name":"Rick","passowrd":"secret","playerID": 14}.
	 * 
	 * 		If the passed-in (username, password) pair is not valid, or the operation fails for any other
	 * 		reason,
	 * 				1. The server reutnrs an HTTP 400 error response, and the body contains and error
	 * 		message.
	 * 
	 */
	@Override
	public Login_Result login(Login_Params request) throws ClientException {
		//System.out.println("Login.");
		URL_SUFFIX = "/user/login";

		return new Login_Result((String) doPost(URL_SUFFIX, request), playerID, request.getUsername());
	}

	/**
	 * This method does two things:
	 * 1) Creates a new user account
	 * 2) Logs the caller in to the server as the new user, and sets their catan.user HTTP cookie.
	 * 
	 * @param request Register_Params
	 * @return result Register_Result
	 * @throws ClientException
	 * 
	 * @pre username is not null, password is not null, the specified username is not already in use.
	 * 
	 * @post If there is no existing user with the specified username,
	 * 			1. A new user accoutn has been created with the specified username and password.
	 * 			2. The server returns an HTTP 200 success response with "Success" in the body.
	 * 			3. The HTTP response headers set the catan.user cokoie to contain the identity of the
	 * 		logged-in player. The cookie uses "Path=/", and its value contains a url-encoded JSON object of
	 * 		the following form: {"name":STRING,"password":STRING,"playerID":INTEGER}. For
	 * 		example, {"name":"Rick","password":"secret","playerID":14}.
	 * 
	 * 		If there is already an existing user with the specified name, or the operation fails for any other
	 * 		reason,
	 * 			1. The server returns an HTTP 400 error response, and the body contains an error
	 * 		message.
	 * 
	 * 		(There is no method for changing passwords)
	 */
	@Override
	public Register_Result register(Register_Params request)
			throws ClientException {
		//System.out.println("Register.");
		URL_SUFFIX = "/user/register";
		return new Register_Result((String) doPost(URL_SUFFIX, request), playerID, request.getUsername());
	}

	//moves

	/**
	 * 
	 * Accepts or declines a trade request from another player.
	 * 
	 * @param request AcceptTrade_Params 
	 * (willAccept: boolean [Whether or not you accept the offered trade])
	 * @return result AcceptTrade_Result
	 * @throws ClientException
	 * 
	 * @pre You have been offered a domestic trade.
	 * To accept the offered trade, you have teh required resources.
	 * @post If you accepted, you and the player who offered swap the specified resources.
	 * If you declined no resources are exchanged.
	 * The offer is removed.
	 */
	@Override
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request)
			throws ClientException {
		//System.out.println("Accept trade.");
		URL_SUFFIX = "/moves/acceptTrade";
		return new AcceptTrade_Result((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Build a city over one of your preexisting settlements.
	 * 
	 * 
	 * @param request BuildCity_Params
	 * vertextLocation: VertextLocation [the location of the city]
	 * @return result BuildCity_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * The city location is where you currently have a settlement
	 * You have the required resources (2 wheat, 3 ore; 1 city)
	 * 
	 * @post You lost the resources required to build a city (2 wheat, 3 ore; 1 city)
	 * The city is on the map at the specified location
	 * You got a settlement back.
	 * 
	 */
	@Override
	public BuildCity_Result buildCity(BuildCity_Params request)
			throws ClientException {
		//System.out.println("Build city.");
		URL_SUFFIX = "/moves/buildCity";
		return new BuildCity_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Build a road at the specified legal location.
	 * 
	 * 
	 * @param request BuildRoad_Params
	 * free: boolean [whether or not you get thisp iece for free (i.e., in setup)]
	 * road location: EdgeLocation [the new road's location]
	 * @return result BuildRoad_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * The road location is open. The road location is connected to another road owned by the player.
	 * The road location is not on water.
	 * You have the required resources (1 wood, 1 brick; 1 road)
	 * Setup round: Must be placed by settlement owned by the player with no adjacent road
	 * 
	 * @post You lost the resources required to build a road (1 wood, 1 brick; 1 road)
	 * The road is on the map at the specified location
	 * If applicable, "longest road" has been awarded to the player with the longest road 
	 */
	@Override
	public BuildRoad_Result buildRoad(BuildRoad_Params request)
			throws ClientException {
		//System.out.println("Build road.");
		URL_SUFFIX = "/moves/buildRoad";
		return new BuildRoad_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Build a settlement at the specified legal location.
	 * 
	 * 
	 * @param request BuildSettlement_Params
	 * free: boolean [whether or not you get this piece for free (i.e. in setup)]
	 * vertexLocation: VertexLocation [in the location of the settlement]
	 * @return result BuildSettlement_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * The settlement location is open. The settlement location is not on water.
	 * The settlement location is connected to one of your roads except during setup.
	 * you have the rqeuired resources (1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
	 * The settlement cannot be placed adjacent to another settlement
	 * 
	 * @post You lost the resources required to build a settlement (1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
	 * The settlement is on the map at the specified location.
	 * 
	 */
	@Override
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request)
			throws ClientException {
		//System.out.println("Build settlement.");
		URL_SUFFIX = "/moves/buildSettlement";
		return new BuildSettlement_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Buy a random development card.
	 * 
	 * 
	 * @param request BuyDevCard_Params
	 * @return result BuyDevCard_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * You have the required resources (1 ore, 1 wheat, 1 sheep)
	 * There are dev cards left in the deck
	 * 
	 * @post You have a new card.
	 * If it is a monument card, it has been added to your old devcard hand.
	 * If it is a non-monumnet card, it iahs been added to your new devcard hand (unplayable this turn)
	 */
	@Override
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request)
			throws ClientException {
		//System.out.println("Buy dev card.");
		URL_SUFFIX = "/moves/buyDevCard";
		return new BuyDevCard_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * 
	 * Forces you to discard half of your cards 
	 * as a result of robber if you have over 7 cards. 
	 * 
	 * @param request DiscardCards_Params
	 * @return result DiscardCards_Result
	 * @throws ClientException
	 * 
	 * @pre The status of the client model is 'Discarding'
	 * You have over 7 cards.
	 * You gave up the specified resources.
	 * If you're the last one to discard, the client model status changes to 'Robbing'
	 */
	@Override
	public DiscardCards_Result discardCards(DiscardCards_Params request)
			throws ClientException {
		//System.out.println("Discard cards.");
		URL_SUFFIX = "/moves/discardCards";
		return new DiscardCards_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Finishes current turn.
	 * 
	 * 
	 * @param request FinishTurn_Params
	 * @return result FinishTurn_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * 
	 * @post The cards in your new dev card hand have been transferred to your old dev card hand.
	 * It is the next player's turn.
	 * 
	 */
	@Override
	public FinishTurn_Result finishTurn(FinishTurn_Params request)
			throws ClientException {
		//System.out.println("Finish turn.");
		URL_SUFFIX = "/moves/finishTurn";
		return new FinishTurn_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Perform a trade with the bank at fixed ratios.
	 * 
	 * @param request MaritimeTrade_Params
	 * ratio: integer [2, 3, 4]
	 * inputResource: resource [what you are giving]
	 * outputResource: Resource [what you are getting]
	 * @return result MaritimeTrade_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * You have the resources you are giving.
	 * For ratios less than 4, you have the correct port for the trade.
	 * @post The trade has been executed (the offered resources are in the bank,
	 * and the requested resource has been received.
	 * 
	 */
	@Override
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request)
			throws ClientException {
		//System.out.println("Maritime trade.");
		URL_SUFFIX = "/moves/maritimeTrade";
		return new MaritimeTrade_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Offer a specified trade of cards with another player.
	 * 
	 * 
	 * @param request OfferTrade_Params
	 * offer: ResourceHand [negative numbers you get those cards]
	 * receiver: playerIndex [the recipient of the trade offer]
	 * @return result OfferTrade_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * You have the resources you are offering.
	 * @post The trade is offered to the other player (stored in the server model)
	 * 
	 */
	@Override
	public OfferTrade_Result offerTrade(OfferTrade_Params request)
			throws ClientException {
		//System.out.println("Offer trade.");
		URL_SUFFIX = "/moves/offerTrade";
		return new OfferTrade_Result((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Rob another player of a card as a result of moving the robber.
	 * 
	 * 
	 * @param request RobPlayer_Params
	 * location: HexLocation [the new robber location]
	 * victimIndex: playerIndex, or -1 if you are not robbing anyone [the player you are robbing]
	 * @return result RobPlayer_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn. The client model's status is 'Playing'
	 * The robber is not being kept in the same location
	 * If a player is being robbed (i.e., victimIndex != -1), the player being robbed has resource cards.
	 * 
	 * @post The robber is in the new location.
	 * The player being robbed (if any) gave you one of his resource cards (randomly selected)
	 */
	@Override
	public RobPlayer_Result robPlayer(RobPlayer_Params request)
			throws ClientException {
		//System.out.println("Rob player.");
		URL_SUFFIX = "/moves/robPlayer";
		return new RobPlayer_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Roll a dice to trigger resource gathering on your turn.
	 * 
	 * 
	 * @param request RollNumber_Params
	 * (number: integer in the range of 2-12 [the number you rolled])
	 * @return result RollNumber_Result
	 * @throws ClientException
	 * 
	 * @pre It is your turn.
	 * The client model's status is 'Rolling'
	 * @post The client model's status is now in 'Discarding' or 'Robbing' or 'Playing'
	 * 
	 */
	@Override
	public RollNumber_Result rollNumber(RollNumber_Params request)
			throws ClientException {
		//System.out.println("Roll number.");
		URL_SUFFIX = "/moves/rollNumber";
		return new RollNumber_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Send a message to other players.
	 * 
	 * @param request
	 * @return
	 * @throws ClientException
	 * 
	 * @pre content: string [the message you want to send]
	 * @post The chat contains your message at the end.
	 */
	@Override
	public SendChat_Result sendChat(SendChat_Params request)
			throws ClientException {
		//System.out.println("Sending chat.");
		URL_SUFFIX = "/moves/sendChat";
		return new SendChat_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * 
	 * Steal one specified resource type from all other players and place those cards in hand.
	 * 
	 * @param request PlayMonopoly_Params
	 * @return result PlayMonopoly_Result
	 * @throws ClientException
	 * 
	 * @pre It is client's turn. Client model status is 'playing'.
	 * You have the specific card you want to play in your dev card hand.
	 * You have not yet played a non-monument dev-card this turn.
	 * @post Typed card is removed from hand.
	 * All of the other players have given you all of their resource cards of the specified type.
	 */
	@Override
	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/Monopoly";
		return new PlayMonopoly_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Play a monument card when doing so triggers a victory.
	 * 
	 * 
	 * @param request PlayMonument_Params
	 * @return result PlayMonument_Result
	 * @throws ClientException
	 * 
	 * @pre It is client's turn. Client model status is 'playing'.
	 * You have the specific card you want to play in your dev card hand.
	 * You have not yet played a non-monument dev-card this turn.
	 * You have enough monument cards to win the game (i.e. 10 victory points)
	 * @post Typed card is removed from hand.
	 * All of the other players have given you all of their resource cards of the specified type.
	 * You gain a victory point.
	 */
	@Override
	public PlayMonument_Result playMonument(PlayMonument_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/Monument";
		return new PlayMonument_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Specify any two legal locations to build new roads, free of cost.
	 * 
	 * @param request PlayRoadBuilding_Params
	 * @return result PlayRoadBuilding_Result
	 * @throws ClientException
	 * 
	 * @pre It is client's turn. Client model status is 'playing'.
	 * You have the specific card you want to play in your dev card hand.
	 * You have not yet played a non-monument dev-card this turn.
	 * The first road location (spot 1) is connected to one of your roads.
	 * The second road location (spot2) is connected to one of your roads or to the first road location (spot1)
	 * Neither road location is on water
	 * You have at least two unused roads
	 * @post Typed card is removed from hand.
	 * You have two fewer unused roads.
	 * Two new roads appear on the map at the specified locations
	 * If applicable, "longest road" has been awarded to the player with the longest road.
	 * 
	 */
	@Override
	public PlayRoadBuilding_Result playRoadBuilding(
			PlayRoadBuilding_Params request) throws ClientException {
		URL_SUFFIX = "/moves/Road_Building";
		return new PlayRoadBuilding_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Move robber to a new location and choose an adjacent player (or none) to rob.
	 * 
	 * 
	 * 
	 * @param request PlaySoldier_Params
	 * @return result PlaySoldier_Result
	 * @throws ClientException
	 * 
	 * @pre It is client's turn. Client model status is 'playing'.
	 * You have specific card you want to play in your dev card hand.
	 * You have not yet played a non-monument dev-card this turn.
	 * Robber is not being kept in the same location.
	 * If there is a player being robbed, that player must have a resource card.
	 * @post Typed card is removed from hand. Robber is in new location.
	 * Player being robbed (if any) gives you one of his resource cards (randomly selected)
	 * If applicable, "largest army" ahs been awarded to the player who has played the most soldier cards.
	 * You are not allowed to play other development cards this turn (except monument cards).
	 */

	@Override
	public PlaySoldier_Result playSoldier(PlaySoldier_Params request)
			throws ClientException {
		URL_SUFFIX = "/moves/Soldier";
		return new PlaySoldier_Result ((String)doPost(URL_SUFFIX, request));
	}

	/**
	 * Specify two resources of any type and receive them immediately.
	 * 
	 * @param request PlayYearOfPlenty_Params 
	 * @return result PlayYearOfPlenty_Result
	 * @throws ClientException
	 * 
	 * @pre It is client's turn. Client model status is 'playing'.
	 * You have specific card you want to play in your dev card hand.
	 * You have not yet played a non-monument dev-card this turn.
	 * The two specified resources are in the bank.
	 * @post Typed card is removed from hand.
	 * You gain the two specified resources.
	 */
	@Override
	public PlayYearOfPlenty_Result playYearOfPlenty(
			PlayYearOfPlenty_Params request) throws ClientException {
		URL_SUFFIX = "/moves/Year_of_Plenty";
		return new PlayYearOfPlenty_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * Doesn't work, extra credit.
	 */
	@Override
	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		//System.out.println("Add AI.");
		URL_SUFFIX = "/game/addAI";

		return new AddAI_Result((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * TODO
	 * EXTRA CREDIT
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	@Override
	public ListAI_Result listAI(ListAI_Params request) throws ClientException {
		System.out.println("List AI.");
		URL_SUFFIX = "/game/listAI";
		return new ListAI_Result ((String) doPost(URL_SUFFIX, request));
	}

	/**
	 * 	Posts data to the server for operation and receives response object.	 
	 * 
	 * @param url Location to be posted to
	 * @param request Object to be sent by post
	 * @return result object from post.
	 * @throws ClientException 
	 * 
	 * @pre URL belongs to the correct server, and request is a known communication object.
	 * @post server will perform specified operation on given object.
	 */
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
			if (unfixedGameCookie!=null){
				//System.out.println("Adding cookies");
				connection.setRequestProperty("Cookie", unfixedUserCookie+"; "+unfixedGameCookie);
			}
			else if (unfixedUserCookie!=null){
				//System.out.println("Adding user cookie: "+unfixedUserCookie);
				connection.setRequestProperty("Cookie", unfixedUserCookie);
			}

			System.out.println("Test.");
			
			if (request.getClass()==MaritimeTrade_Params.class){
				System.out.print("Call: "+job);
			}
			//System.out.print("Call: "+job);

			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);	

			connection.connect(); // sends cookies

			System.out.println("Test 1");

			OutputStreamWriter sw = new OutputStreamWriter(connection.getOutputStream());
			sw.write(job);
			sw.flush();

			
			System.out.println("Test 2");
			
			if (request.getClass()==MaritimeTrade_Params.class){
				System.out.println(" |||| Response code: "+connection.getResponseCode());
			}
			//System.out.println(" |||| Response code: "+connection.getResponseCode());

			InputStream in = connection.getInputStream();

			int len = 0;

			//System.out.println("Input stream received.");
			System.out.println("Test 2.5");
			byte[] buffer = new byte[1024];

			StringBuilder sb = new StringBuilder();
			System.out.println("Test 2.6");
			while (-1 != (len = in.read(buffer))){
				sb.append(new String(buffer, 0, len));
			}

			System.out.println("Test 3");
			
			job = sb.toString();

			in.close();

			//System.out.println("Response: "+job+'\n');

			if (request.getClass()==MaritimeTrade_Params.class){
				System.out.println("Response: "+job+'\n');
			}

			//Cookie cacher----------------------------------
			Map<String, List<String>> headers = connection.getHeaderFields();

			if (URL_SUFFIX.equals("/user/login") || URL_SUFFIX.equals("/user/register")){
				unfixedUserCookie = headers.get("Set-cookie").get(0);
				userCookie = unfixedUserCookie;

				if (userCookie.endsWith("Path=/;")) {
					userCookie = userCookie.substring(0, userCookie.length() - 8);
					unfixedUserCookie = userCookie;
					userCookie = userCookie.substring(11, userCookie.length());
				}

				//	System.out.println("Amended cookie: "+ userCookie);
				String decodedUserData = URLDecoder.decode(userCookie, "UTF-8");				
				//System.out.println("Decoded Cookie: "+decodedUserData);				
				JsonObject jobj = gson.fromJson(decodedUserData, JsonObject.class);
				playerID = Integer.parseInt(jobj.get("playerID").toString());	

			}
			else if (URL_SUFFIX.equals("/games/join")){
				//System.out.println("Getting join game cookie");
				unfixedGameCookie = headers.get("Set-cookie").get(0);

				gameCookie = unfixedGameCookie;


				if (gameCookie.endsWith("Path=/;")) {
					gameCookie = gameCookie.substring(0, gameCookie.length() - 8);
					unfixedGameCookie = gameCookie;
					gameCookie = gameCookie.substring(11, gameCookie.length());
				}

				//System.out.println("Game cookie: "+ gameCookie);
				gameID = Integer.parseInt(gameCookie);
			}

			//String decodedUserData = URLDecoder.decode(userCookie, "UTF-8");
			System.out.println("Test 4");
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
				e.printStackTrace();
			}
			System.out.println();


		} finally {
			//finally stuff
		}
		return null;
	}


}

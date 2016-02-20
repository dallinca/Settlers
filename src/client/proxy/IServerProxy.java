package client.proxy;

import client.ClientException;
import shared.communication.params.move.*;
import shared.communication.params.move.devcard.*;
import shared.communication.params.nonmove.*;
import shared.communication.results.move.*;
import shared.communication.results.move.devcard.*;
import shared.communication.results.nonmove.*;

/**
 * A class which packages data from a client and sends it through the internet to the server.
 * Receives results from the server.
 *
 */

public interface IServerProxy {

	//nonmove

	
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
	
	public Create_Result createGame(Create_Params request) throws ClientException;

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
	public GetVersion_Result getVersion(GetVersion_Params request) throws ClientException;

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
	public Join_Result joinGame(Join_Params request) throws ClientException;

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
	public List_Result listGames(List_Params request) throws ClientException;

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
	public Login_Result login(Login_Params request) throws ClientException;

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
	public Register_Result register(Register_Params request) throws ClientException;

	/**
	 * extra credit
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public ListAI_Result listAI(ListAI_Params request) throws ClientException;

	/**
	 * extra credit
	 * @param request
	 * @return
	 * @throws ClientException
	 */
	public AddAI_Result addAI(AddAI_Params request) throws ClientException;
	
	//Anytime commands
	
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
	public SendChat_Result sendChat(SendChat_Params request) throws ClientException;	
	
	//misc commands
	
	
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
	
	public RollNumber_Result rollNumber(RollNumber_Params request) throws ClientException;
	
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
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request) throws ClientException;
	
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
	public DiscardCards_Result discardCards(DiscardCards_Params request) throws ClientException;
	
	//move

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
	 * The c ity location is where you currently have a settlement
	 * You have the required resources (2 wheat, 3 ore; 1 city)
	 * 
	 * @post You lost the resources required to build a city (2 wheat, 3 ore; 1 city)
	 * The city is on the map at the specified location
	 * You got a settlement back.
	 * 
	 */
	
	public BuildCity_Result buildCity(BuildCity_Params request) throws ClientException;

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
	
	public BuildRoad_Result buildRoad(BuildRoad_Params request) throws ClientException;

	/**
	 * Build a settlement at the specified legal location.
	 * 
	 * 
	 * @param request BuildSettlement_Params
	 * free: boolean [whether or not you get this piece for free (i.e. in setup)]
	 * vertexLocation: VertextLocation [in the location of the settlement]
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
	
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request) throws ClientException;

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
	
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request) throws ClientException;

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
	
	public FinishTurn_Result finishTurn(FinishTurn_Params request) throws ClientException;

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
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request) throws ClientException;

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
	
	public OfferTrade_Result offerTrade(OfferTrade_Params request) throws ClientException;


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
	
	public RobPlayer_Result robPlayer(RobPlayer_Params request) throws ClientException;

	//dev card play

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

	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params request) throws ClientException;

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
	public PlayMonument_Result playMonument(PlayMonument_Params request) throws ClientException;

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

	public PlayRoadBuilding_Result playRoadBuilding(PlayRoadBuilding_Params request) throws ClientException;

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

	public PlaySoldier_Result playSoldier(PlaySoldier_Params request) throws ClientException;

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

	public PlayYearOfPlenty_Result playYearOfPlenty(PlayYearOfPlenty_Params request) throws ClientException;


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

	public Object doPost(String urlString, Object request) throws ClientException;

}
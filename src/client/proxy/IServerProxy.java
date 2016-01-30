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
 * @author jchrisw
 *
 */

public interface IServerProxy {
	
	
	//nonmove
	
	public AddAI_Result addAI(AddAI_Params request) throws ClientException;
	
	public ChangeLogLevel_Result changeLogLevel(ChangeLogLevel_Params request) throws ClientException;
	
	public Create_Result createGame(Create_Params request) throws ClientException;
	
	public GetCommands_Result getCommands(GetCommands_Params request) throws ClientException;	
	
	/**
	 * Packages a poll server request from the client.
	 * 
	 * @return
	 * @throws ClientException
	 * 
	 * @pre None
	 * @post PollServer response will be obtained from server.
	 */
	public GetVersion_Result getVersion(GetVersion_Params request) throws ClientException;
	
	public Join_Result joinGame(Join_Params request) throws ClientException;
	
	public List_Result listGames(List_Params request) throws ClientException;
	
	public ListAI_Result listAI(ListAI_Params request) throws ClientException;
	
	public Load_Result loadGame(Load_Params request) throws ClientException;
	
	public Login_Result login(Login_Params request) throws ClientException;
	
	public PostCommands_Result postCommands(PostCommands_Params request) throws ClientException;
	
	public Register_Result register(Register_Params request) throws ClientException;
	
	public Save_Result saveGame(Save_Params request) throws ClientException;
	
	//move
	
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request) throws ClientException;
	
	public BuildCity_Result buildCity(BuildCity_Params request) throws ClientException;
	
	public BuildRoad_Result buildRoad(BuildRoad_Params request) throws ClientException;
	
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request) throws ClientException;
	
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request) throws ClientException;
	
	public DiscardCards_Result discardCards(DiscardCards_Params request) throws ClientException;
	
	public FinishTurn_Result finishTurn(FinishTurn_Params request) throws ClientException;
	
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request) throws ClientException;
	
	public OfferTrade_Result offerTrade(OfferTrade_Params request) throws ClientException;
	
	public RobPlayer_Result robPlayer(RobPlayer_Params request) throws ClientException;
	
	public RollNumber_Result rollNumber(RollNumber_Params request) throws ClientException;
	
	public SendChat_Result sendChat(SendChat_Params request) throws ClientException;	
	
	//dev card play
	
	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params request) throws ClientException;
	
	public PlayMonument_Result playMonument(PlayMonument_Params request) throws ClientException;
	
	public PlayRoadBuilding_Result playRoadBuilding(PlayRoadBuilding_Params request) throws ClientException;
	
	public PlaySoldier_Result playSolder(PlaySoldier_Params request) throws ClientException;
	
	public PlayYearOfPlenty_Result playYearOfPlenty(PlayYearOfPlenty_Params result) throws ClientException;
	
		
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
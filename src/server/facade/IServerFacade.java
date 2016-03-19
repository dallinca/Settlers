package server.facade;

import java.util.ArrayList;
import java.util.List;

import server.commands.Command;
import shared.communication.params.nonmove.*;
import shared.communication.params.move.*;
import shared.communication.params.move.devcard.*;
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
import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * TODO -- Javadoc
 * 
 * @author Dallin
 *
 */
public interface IServerFacade {

	// TODO determine what the facade functions need to return for the handlers to package
	
	
	// Command pattern actions (move actions)
	public Game acceptTrade(AcceptTrade_Params params);
	public Game buildCity(BuildCity_Params params, int gameID, int userID);
	public Game buildRoad(BuildRoad_Params params, int gameID, int userID);
	public Game buildSettlement(BuildSettlement_Params params, int gameID, int userID);
	
	public Game buyDevCard(BuyDevCard_Params params, int gameID, int userID);
	public Game discardCards(DiscardCards_Params params, int gameID, int userID);
	public Game finishTurn(FinishTurn_Params params, int gameID, int userID);
	public Game maritimeTrade(MaritimeTrade_Params params, int gameID, ResourceType tradeIn, ResourceType receive);
	public Game offerTrade(OfferTrade_Params params, int gameID, int userID);
	public Game robPlayer(RobPlayer_Params params, int gameID, int userID);
	public Game rollNumber(RollNumber_Params params, int gameID, int userID);
	public Game sendChat(SendChat_Params params, int gameID, int userID);
	public Game playMonopoly(PlayMonopoly_Params params);
	public Game playMonument(PlayMonument_Params params);
	public Game playRoadBuilding(PlayRoadBuilding_Params params);
	public Game playSoldier(PlaySoldier_Params params);
	public Game playYearOfPlenty(PlayYearOfPlenty_Params params);
	
	// Non Command pattern actions (nonmove actions)
	public Login_Result login(Login_Params params);
	public Register_Result register(Register_Params params);
	public List_Result list(List_Params params);
	public Create_Result create(Create_Params params);
	public Join_Result join(Join_Params params);
	public GetVersion_Result model(GetVersion_Params params);
	public AddAI_Result addAI(AddAI_Params params);
	public ListAI_Result listAI(ListAI_Params params);
	public Game canDoPlayMonopoly(int gameID, int userID);
	
	// Return the game that the command is meant to operate on
	//Game findGameForCommand(); 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

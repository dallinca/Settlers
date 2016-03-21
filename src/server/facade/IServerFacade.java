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
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params params);
	public BuildCity_Result buildCity(BuildCity_Params params);
	public BuildRoad_Result buildRoad(BuildRoad_Params params);
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params params);
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params params);
	public DiscardCards_Result discardCards(DiscardCards_Params params);
	public FinishTurn_Result finishTurn(FinishTurn_Params params);
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params params);
	public OfferTrade_Result offerTrade(OfferTrade_Params params);
	public RobPlayer_Result robPlayer(RobPlayer_Params params);
	public RollNumber_Result rollNumber(RollNumber_Params params);
	public SendChat_Result sendChat(SendChat_Params params);
	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params params);
	public PlayMonument_Result playMonument(PlayMonument_Params params);
	public PlayRoadBuilding_Result playRoadBuilding(PlayRoadBuilding_Params params);
	public PlaySoldier_Result playSoldier(PlaySoldier_Params params);
	public PlayYearOfPlenty_Result playYearOfPlenty(PlayYearOfPlenty_Params params);
	
	// Non Command pattern actions (nonmove actions)
	public Login_Result login(Login_Params params);
	public Register_Result register(Register_Params params);
	public List_Result list(List_Params params);
	public Create_Result create(Create_Params params);
	public Join_Result join(Join_Params params);
	public GetVersion_Result model(GetVersion_Params params);
	public AddAI_Result addAI(AddAI_Params params);
	public ListAI_Result listAI(ListAI_Params params);
	
	// Return the game that the command is meant to operate on
	//Game findGameForCommand(); 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

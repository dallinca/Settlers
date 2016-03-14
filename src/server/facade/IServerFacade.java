package server.facade;

import java.util.ArrayList;
import java.util.List;

import server.commands.Command;
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
	public AcceptTrade_Result acceptTrade(Command command);
	public BuildCity_Result buildCity(Command command);
	public BuildRoad_Result buildRoad(Command command);
	public BuildSettlement_Result buildSettlement(Command command);
	public BuyDevCard_Result buyDevCard(Command command);
	public DiscardCards_Result discardCards(Command command);
	public FinishTurn_Result finishTurn(Command command);
	public MaritimeTrade_Result maritimeTrade(Command command);
	public OfferTrade_Result offerTrade(Command command);
	public RobPlayer_Result robPlayer(Command command);
	public RollNumber_Result rollNumber(Command command);
	public SendChat_Result sendChat(Command command);
	public PlayMonopoly_Result playMonopoly(Command command);
	public PlayMonument_Result playMonument(Command command);
	public PlayRoadBuilding_Result playRoadBuilding(Command command);
	public PlaySoldier_Result playSoldier(Command command);
	public PlayYearOfPlenty_Result playYearOfPlenty(Command command);
	
	// Non Command pattern actions (nonmove actions)
	public Login_Result login();
	public Register_Result register();
	public List_Result list();
	public Register_Result create();
	public Join_Result join();
	public GetVersion_Result model();
	public AddAI_Result addAI();
	public ListAI_Result listAI();
	
	// Return the game that the command is meant to operate on
	//Game findGameForCommand(); 
	
}

package client.communication;

import static org.junit.Assert.*;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import shared.communication.params.move.*;
import shared.communication.params.nonmove.*;
import shared.communication.params.move.devcard.*;
import client.ClientException;
import client.proxy.*;

/**
 * Tests functions of the server proxy. Ant server must be running beforehand.
 *
 */
public class ServerProxyTest {

	private ServerProxy prox;

	@Before
	public void setUp(){

		prox = new ServerProxy();
	}

	@AfterClass
	public static void tearDownAfterClass(){		

	}

	@Test
	public void testConnection() throws ClientException {
		//System.out.println("Testing connection.");
		
		Object result = prox.doPost("/user/login", new Login_Params("Sam", "sam"));
	
		//System.out.println(result.toString());
	}
	
	@Test
	public void testCommands() throws ClientException {
		//Ensure ant server is running before this test goes.
		
		//System.out.println("Testing all commands.");
		//At this stage, all commands return 400's because of their empty states.
		
		try{
			
		prox.register(new Register_Params("Tiger", "Shark"));
		prox.login(new Login_Params("Tiger", "Shark"));
			
		prox.sendChat(new SendChat_Params());
		prox.rollNumber(new RollNumber_Params());
		prox.robPlayer(new RobPlayer_Params());
		prox.playYearOfPlenty(new PlayYearOfPlenty_Params());
		prox.playSoldier(new PlaySoldier_Params());
		prox.playRoadBuilding(new PlayRoadBuilding_Params());
		prox.playMonument(new PlayMonument_Params());
		prox.playMonopoly(new PlayMonopoly_Params());
		prox.offerTrade(new OfferTrade_Params());
		prox.maritimeTrade(new MaritimeTrade_Params());
		prox.login(new Login_Params());
		prox.listGames(new List_Params());
		prox.listAI(new ListAI_Params());
		prox.joinGame(new Join_Params());
		prox.getVersion(new GetVersion_Params());
		prox.finishTurn(new FinishTurn_Params());
		prox.discardCards(new DiscardCards_Params());
		prox.createGame(new Create_Params());
		prox.buyDevCard(new BuyDevCard_Params());
		prox.buildSettlement(new BuildSettlement_Params());
		prox.buildRoad(new BuildRoad_Params());
		prox.buildCity(new BuildCity_Params());
		prox.addAI(new AddAI_Params());
		prox.acceptTrade(new AcceptTrade_Params());
		}
		
		catch (Exception e){
			fail();
		}
	}

	@Test
	public void testCookies() throws ClientException {

		prox.login(new Login_Params("Sam", "sam"));
		String userCookie = prox.getUserCookie();
				
	}
}
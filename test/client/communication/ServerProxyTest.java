package client.communication;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import shared.communication.params.move.*;
import shared.communication.params.nonmove.*;
import shared.communication.params.move.devcard.*;
import shared.communication.results.nonmove.Login_Result;
import shared.communication.results.nonmove.Register_Result;
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
	public void testLogin() throws ClientException {		
		Login_Result result = prox.login(new Login_Params("Sam", "sam"));
		
		
		assertTrue(result.isValid());
		assertEquals("Sam", result.getName());
		assertEquals(0, result.getId());
		
		result = prox.login(new Login_Params("sam", "sam"));
		
		assertFalse(result.isValid());
	}
	
	@Test
	public void testRegister() throws ClientException {

		Register_Result regResult = prox.register(new Register_Params("Tiger", "Shark"));
		
		assertTrue(regResult.isValid());
		assertEquals("Tiger", regResult.getName());
		assertEquals(12, regResult.getId());
		
		regResult = prox.register(new Register_Params("Tiger", "Shark"));
		
		assertFalse(regResult.isValid());
		
		Login_Result result = prox.login(new Login_Params("Tiger", "Shark"));
		
		assertTrue(result.isValid());
		assertEquals("Tiger", result.getName());
		assertEquals(12, result.getId());
	}
	
	@Test
	public void testCommands() throws ClientException {
		//Ensure ant server is running before this test goes.
		
		//System.out.println("Testing all commands.");
		//At this stage, all commands return 400's because of their empty states.
		ServerProxy prox1 = new ServerProxy();
		ServerProxy prox2 = new ServerProxy();
		ServerProxy prox3 = new ServerProxy();
		ServerProxy prox4 = new ServerProxy();
			
		try{
			//Sam, Brooke, Pete, Mark
		System.out.println("Testing commands.");
		
		prox1.login(new Login_Params("Sam", "sam"));
		prox2.login(new Login_Params("Brooke", "brooke"));
		prox3.login(new Login_Params("Pete", "pete"));
		prox4.login(new Login_Params("Mark", "mark"));
		
		
		
		
		prox.joinGame(new Join_Params());
			
		prox.sendChat(new SendChat_Params(0, "Yo bro how's it going???"));
		prox.rollNumber(new RollNumber_Params(8));
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
		//prox.listAI(new ListAI_Params());
		
		prox.getVersion(new GetVersion_Params());
		prox.finishTurn(new FinishTurn_Params());
		prox.discardCards(new DiscardCards_Params());
		prox.createGame(new Create_Params());
		prox.buyDevCard(new BuyDevCard_Params());
		prox.buildSettlement(new BuildSettlement_Params());
		prox.buildRoad(new BuildRoad_Params());
		prox.buildCity(new BuildCity_Params());
		//prox.addAI(new AddAI_Params());
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
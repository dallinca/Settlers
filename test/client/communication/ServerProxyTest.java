package client.communication;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.communication.params.move.*;
import shared.communication.params.nonmove.*;
import shared.communication.params.move.devcard.*;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.ClientModel.MessageLine;
import shared.communication.results.move.*;
import shared.communication.results.nonmove.*;
import shared.communication.results.move.SendChat_Result;
import shared.communication.results.move.devcard.PlayMonopoly_Result;
import shared.communication.results.move.devcard.PlayMonument_Result;
import shared.communication.results.move.devcard.PlayRoadBuilding_Result;
import shared.communication.results.move.devcard.PlaySoldier_Result;
import shared.communication.results.move.devcard.PlayYearOfPlenty_Result;
import shared.communication.results.nonmove.Create_Result;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.communication.results.nonmove.Join_Result;
import shared.communication.results.nonmove.List_Result;
import shared.communication.results.nonmove.List_Result.Game;
import shared.communication.results.nonmove.Login_Result;
import shared.communication.results.nonmove.Register_Result;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.Game.Line;
import client.Client;
import client.ClientException;
import client.proxy.*;

/**
 * Tests functions of the server proxy. Ant server must be running beforehand.
 *
 */
public class ServerProxyTest {

	private static ServerProxy prox;

	@BeforeClass
	public static void setUp(){

		prox = ServerProxy.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass(){		

	}

	@Before
	public void tearDown(){
	}

	//@Test
	public void testLogin() throws ClientException {		
		Login_Result result = prox.login(new Login_Params("Sam", "sam"));


		assertTrue(result.isValid());
		assertEquals("Sam", result.getName());
		assertEquals(0, result.getId());

		result = prox.login(new Login_Params("Sam", "sam"));


		assertTrue(result.isValid());
		assertEquals("Sam", result.getName());
		assertEquals(0, result.getId());

		result = prox.login(new Login_Params("sam", "sam"));

		assertFalse(result.isValid());

		prox.reset(new Reset_Params());
	}

	//@Test
	public void testRegister() throws ClientException {

		Register_Result regResult = prox.register(new Register_Params("Tiger", "Shark"));

		assertTrue(regResult.isValid());
		assertEquals("Tiger", regResult.getName());
		assertEquals(13, regResult.getId());

		regResult = prox.register(new Register_Params("Tiger", "Shark"));

		assertFalse(regResult.isValid());

		Login_Result result = prox.login(new Login_Params("Tiger", "Shark"));

		assertTrue(result.isValid());
		assertEquals("Tiger", result.getName());
		assertEquals(13, result.getId());

		prox.reset(new Reset_Params());
	}

	//@Test
	public void testCreate() throws ClientException {

		@SuppressWarnings("unused")//It is used.
		Register_Result regResult = prox.register(new Register_Params("Tigera", "Sharka"));

		Login_Result result = prox.login(new Login_Params("Tigera", "Sharka"));
		assertTrue(result.isValid());

		Create_Result createResult = prox.createGame(new Create_Params("TestGame", false, false, false));

		assertTrue(createResult.isValid());
		assertEquals("\"TestGame\"", createResult.getTitle());
		assertEquals(createResult.getID(), 4);

		prox.reset(new Reset_Params());
	}

	//@Test
	public void testList() throws ClientException {
		prox.login(new Login_Params("Sam", "sam"));

		List_Result lResult = prox.listGames(new List_Params());
		//System.out.println(lResult.toString());

		assertTrue(lResult.isValid());

		//System.out.println("End list test.");	

	}


	//@Test
	public void testJoin() throws ClientException {

		Register_Result rResult = prox.register(new Register_Params("Tiger0", "Shark0"));
		assertTrue(rResult.isValid());

		Login_Result lResult = prox.login(new Login_Params("Tiger0", "Shark0"));		
		assertTrue(lResult.isValid());

		Create_Result createResult = prox.createGame(new Create_Params("TestGame2", false, false, false));
		assertTrue(createResult.isValid());
		//assertEquals(3, createResult.getID());

		List_Result listResult = prox.listGames(new List_Params());

		LinkedList<Game> games = listResult.getListedGames();

		int joinGameID = 0;

		for (Game g: games){
			if (g.getTitle().equals("\"TestGame2\"")){
				joinGameID = g.getID();
				break;
			}
		}


		Join_Result jResult = prox.joinGame(new Join_Params(joinGameID, CatanColor.RED));
		assertNotNull(jResult);
		assertTrue(jResult.isValid());
	}

	//@Test
	public void testGetVersion() throws ClientException {

		Login_Result lResult = prox.login(new Login_Params("Sam", "sam"));		
		assertTrue(lResult.isValid());

		Join_Result jResult = prox.joinGame(new Join_Params(0, CatanColor.ORANGE));
		assertTrue(jResult.isValid());
		
		prox.reset(new Reset_Params());
		//assertEquals(3, createResult.getID());

		GetVersion_Result vResult = prox.getVersion(new GetVersion_Params());
		assertTrue(vResult.isValid());
		assertFalse(vResult.isUpToDate());

		ClientModel model = vResult.getModel();
		ClientModel.MDevCardList deck = model.getDeck();

		assertEquals(2, deck.getYearOfPlenty());
		assertEquals(2, deck.getMonopoly());
		assertEquals(14, deck.getSoldier());
		assertEquals(2, deck.getRoadBuilding());
		assertEquals(5, deck.getMonument());

		//model.getChat().getLines()[0];		

		/*System.out.println("Model chat length: "+model.getChat().getLines().length);

		for (MessageLine l : model.getChat().getLines()){
			System.out.println(l);
		}

		System.out.println("Model log length: "+model.getLog().getLines().length);

		for (MessageLine l : model.getLog().getLines()){
			System.out.println(l);
		}*/

		assertEquals(0, model.getChat().getLines().length);
		//assertEquals(27, model.getLog().getLines().length);

		//GetVersion_Result.ClientModel.Map map = model.getMap();
		//System.out.println(model.toString());

		//ClientModel.MBank bank = model.getBank();

		shared.model.Game game = vResult.getGame();

		//{"resources":{"brick":0,"wood":1,"sheep":1,"wheat":1,"ore":0},

		assertEquals("Sam", model.getPlayers()[0].getName());
		assertEquals(0, model.getPlayers()[0].getResources().getBrick());
		assertEquals(1, model.getPlayers()[0].getResources().getWood());
		assertEquals(1, model.getPlayers()[0].getResources().getSheep());
		assertEquals(1, model.getPlayers()[0].getResources().getWheat());
		assertEquals(0, model.getPlayers()[0].getResources().getOre());

		assertEquals("Sam", game.getAllPlayers()[0].getPlayerName());
		assertEquals(0, game.getAllPlayers()[0].getNumberResourcesOfType(ResourceType.BRICK));
		assertEquals(1, game.getAllPlayers()[0].getNumberResourcesOfType(ResourceType.WOOD));
		assertEquals(3, game.getAllPlayers()[0].getNumberResourcesOfType(ResourceType.SHEEP));
		assertEquals(1, game.getAllPlayers()[0].getNumberResourcesOfType(ResourceType.WHEAT));
		assertEquals(0, game.getAllPlayers()[0].getNumberResourcesOfType(ResourceType.ORE));

		//System.out.println(model.toString());

		//GetVersion_Result.ClientModel.Bank bank = model.getBank();


		ServerProxy prox2 = ServerProxy.getInstance2();		
		ServerProxy prox3 = ServerProxy.getInstance3();
		ServerProxy prox4 = ServerProxy.getInstance4();

		prox2.login(new Login_Params("Brooke", "brooke"));
		prox2.joinGame(new Join_Params(0, CatanColor.BLUE));

		prox3.login(new Login_Params("Pete", "pete"));
		prox3.joinGame(new Join_Params(0, CatanColor.RED));

		prox4.login(new Login_Params("Mark", "mark"));
		prox4.joinGame(new Join_Params(0, CatanColor.GREEN));

		vResult = prox.getVersion(new GetVersion_Params());
		assertTrue(vResult.isValid());
		assertFalse(vResult.isUpToDate());

		vResult = prox2.getVersion(new GetVersion_Params());
		assertTrue(vResult.isValid());
		assertFalse(vResult.isUpToDate());

		vResult = prox3.getVersion(new GetVersion_Params());
		assertTrue(vResult.isValid());
		assertFalse(vResult.isUpToDate());

		vResult = prox4.getVersion(new GetVersion_Params());
		assertTrue(vResult.isValid());
		assertFalse(vResult.isUpToDate());		
	}

	@Test
	public void basicCommands() throws ClientException {

		ServerProxy prox2 = ServerProxy.getInstance2();		
		ServerProxy prox3 = ServerProxy.getInstance3();
		ServerProxy prox4 = ServerProxy.getInstance4();

		Login_Result lResult = prox.login(new Login_Params("Sam", "sam"));		
		assertTrue(lResult.isValid());				
		Join_Result jResult = prox.joinGame(new Join_Params(0, CatanColor.ORANGE));
		assertTrue(jResult.isValid());		

		prox2.login(new Login_Params("Brooke", "brooke"));
		prox2.joinGame(new Join_Params(0, CatanColor.BLUE));		
		prox3.login(new Login_Params("Pete", "pete"));
		prox3.joinGame(new Join_Params(0, CatanColor.RED));		
		prox4.login(new Login_Params("Mark", "mark"));
		prox4.joinGame(new Join_Params(0, CatanColor.GREEN));

		RollNumber_Result rnResult = prox.rollNumber(new RollNumber_Params(0, 10));
		assertTrue(rnResult.isValid());

		SendChat_Result scResult = prox.sendChat(new SendChat_Params(0, "Yo bro how's it going???"));
		assertTrue(scResult.isValid());

		OfferTrade_Result otResult = prox.offerTrade(new OfferTrade_Params(0, 1, 0, 1, 1, 1, -1));
		assertTrue(otResult.isValid());

		AcceptTrade_Result atResult = prox2.acceptTrade(new AcceptTrade_Params(1, false));
		assertTrue(atResult.isValid());

		assertEquals(0, atResult.getModel().getTurnTracker().getCurrentTurn());
		FinishTurn_Result ftResult = prox.finishTurn(new FinishTurn_Params(0));
		assertTrue(ftResult.isValid());
		assertEquals(1, ftResult.getModel().getTurnTracker().getCurrentTurn());

		HexLocation robHex = new HexLocation(0, 0);
		prox2.rollNumber(new RollNumber_Params(1, 7));
		prox2.robPlayer(new RobPlayer_Params(2, robHex, 0));		// rob from player 2.
		prox2.finishTurn(new FinishTurn_Params(1));

		for (int i = 0; i < 3; i++){
			//2,4,5,6,8,9,10,11
			prox3.rollNumber(new RollNumber_Params(2, 2));
			prox3.finishTurn(new FinishTurn_Params(2));

			prox4.rollNumber(new RollNumber_Params(3, 4));
			prox4.finishTurn(new FinishTurn_Params(3));

			prox.rollNumber(new RollNumber_Params(0, 5));
			prox.finishTurn(new FinishTurn_Params(0));

			prox2.rollNumber(new RollNumber_Params(1, 6));
			prox2.finishTurn(new FinishTurn_Params(1));

			prox3.rollNumber(new RollNumber_Params(2, 8));
			prox3.finishTurn(new FinishTurn_Params(2));

			prox4.rollNumber(new RollNumber_Params(3, 9));
			prox4.finishTurn(new FinishTurn_Params(3));

			prox.rollNumber(new RollNumber_Params(0, 5));
			prox.finishTurn(new FinishTurn_Params(0));

			prox2.rollNumber(new RollNumber_Params(1, 10));
			prox2.finishTurn(new FinishTurn_Params(1));			
		}
		
		prox3.rollNumber(new RollNumber_Params(2, 11));
		prox3.finishTurn(new FinishTurn_Params(2));

		rnResult= prox4.rollNumber(new RollNumber_Params(3, 11));
		prox4.finishTurn(new FinishTurn_Params(3));
		
		
		otResult = prox.offerTrade(new OfferTrade_Params(0, 1, -4, -7, 1, -3, -4));
		assertTrue(otResult.isValid());

		atResult = prox2.acceptTrade(new AcceptTrade_Params(1, true));
		assertTrue(atResult.isValid());
		
		/*BuyDevCard_Result bdcResult = prox.buyDevCard(new BuyDevCard_Params(1));
		assertTrue(bdcResult.isValid());*/
		
		//System.out.println(atResult.getModel().toString());
		
		/*if (!(atResult.getModel().getPlayers()[0].getResources().getOre()>0)){
			System.out.println("Insufficient ore: "+ rnResult.getModel().getPlayers()[0].getResources().getOre());
			fail();
		}else if (!(atResult.getModel().getPlayers()[0].getResources().getWheat()>0)){
			System.out.println("Insufficient wheat: "+ rnResult.getModel().getPlayers()[0].getResources().getWheat());
			fail();
		}else if (!(atResult.getModel().getPlayers()[0].getResources().getSheep()>0)){
			System.out.println("Insufficient sheep: "+ rnResult.getModel().getPlayers()[0].getResources().getSheep());
			fail();
		}*/
		BuyDevCard_Result bdcResult = prox.buyDevCard(new BuyDevCard_Params(0));
		assertTrue(bdcResult.isValid());
		
		//System.out.println(bdcResult.getModel().toString());
		
		if (!(bdcResult.getModel().getPlayers()[0].getResources().getWood()>0)){
			fail();
		}else if (!(bdcResult.getModel().getPlayers()[0].getResources().getBrick()>0)){
			fail();
		}		
		EdgeLocation el = new EdgeLocation(new HexLocation(1,0), EdgeDirection.SouthEast);
		BuildRoad_Result brResult = prox.buildRoad(new BuildRoad_Params(0, el, false));
		assertTrue(brResult.isValid());
		
		
		/*if (!(brResult.getModel().getPlayers()[0].getResources().getBrick()>0)){
			fail();
		}else if (!(brResult.getModel().getPlayers()[0].getResources().getWheat()>0)){
			fail();
		}else if (!(brResult.getModel().getPlayers()[0].getResources().getWood()>0)){
			fail();
		}else if (!(brResult.getModel().getPlayers()[0].getResources().getSheep()>0)){
			fail();
		}*/
		VertexLocation vl = new VertexLocation(new HexLocation(2,-1), VertexDirection.SouthWest);
		BuildSettlement_Result bsResult = prox.buildSettlement(new BuildSettlement_Params(0, vl, false));
		assertTrue(bsResult.isValid());
		
		
		
		/*if (!(brResult.getModel().getPlayers()[0].getResources().getWheat()>=2)){
			fail();
		}else if (!(brResult.getModel().getPlayers()[0].getResources().getOre()>=3)){
			fail();
		}*/
		BuildCity_Result bcResult = prox.buildCity(new BuildCity_Params(0, vl));
		assertTrue(bcResult.isValid());
		
		/*System.out.println("Sheep before: "+bcResult.getModel().getPlayers()[0].getResources().getSheep());
		System.out.println("Wood before: "+bcResult.getModel().getPlayers()[0].getResources().getWood());*/
		MaritimeTrade_Result mtResult = prox.maritimeTrade(new MaritimeTrade_Params(0, 3, ResourceType.WOOD, ResourceType.SHEEP));
		assertTrue(mtResult.isValid());
		/*System.out.println("Sheep after: "+mtResult.getModel().getPlayers()[0].getResources().getSheep());
		System.out.println("Wood after: " +mtResult.getModel().getPlayers()[0].getResources().getWood());*/
		
		//System.out.println(bcResult.getModel().toString());
		

		PlayYearOfPlenty_Result yopResult = prox.playYearOfPlenty(new PlayYearOfPlenty_Params(0, ResourceType.ORE, ResourceType.BRICK));
		PlaySoldier_Result psResult = prox.playSoldier(new PlaySoldier_Params(0, 2, robHex));
		PlayRoadBuilding_Result prResult = prox.playRoadBuilding(new PlayRoadBuilding_Params(0, el, el));
		PlayMonument_Result pmResult = prox.playMonument(new PlayMonument_Params(0));
		PlayMonopoly_Result pmoResult = prox.playMonopoly(new PlayMonopoly_Params(0, ResourceType.BRICK));
		
		DiscardCards_Result dcResult = prox.discardCards(new DiscardCards_Params(0, 0, 0, 0, 0, 0));
		/*
		 * 
		 * 
		 * private Game game;
	private ClientModel model;

	public ClientModel getModel() {
		return model;
	}

	public void setModel(ClientModel model) {
		this.model = model;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	public Game getGame(){
		return game;
	}

	public RobPlayer_Result(String post) {

		if (post==null){
			setValid(false);
		}
		else if (post.equals("\"true\"")){
			setValid(true);
			game = null;
			model = null;
		}
		else{
			setValid(true);
			JsonConverter converter = new JsonConverter();
			game = converter.parseJson(post);
			model = converter.getModel();
		}
	}
		 */
	}

	//@Test
	public void testCommands() throws ClientException {
		//Ensure ant server is running before this test goes.

		//System.out.println("Testing all commands.");
		//At this stage, all commands return 400's because of their empty states.
		ServerProxy prox2 = ServerProxy.getInstance2();		
		ServerProxy prox3 = ServerProxy.getInstance3();		
		ServerProxy prox4 = ServerProxy.getInstance4();

		try{
			/*

			prox.robPlayer(new RobPlayer_Params());
			prox.playYearOfPlenty(new PlayYearOfPlenty_Params());
			prox.playSoldier(new PlaySoldier_Params());
			prox.playRoadBuilding(new PlayRoadBuilding_Params());
			prox.playMonument(new PlayMonument_Params());
			prox.playMonopoly(new PlayMonopoly_Params());
			prox.maritimeTrade(new MaritimeTrade_Params());
			prox.login(new Login_Params());
			prox.listGames(new List_Params());
			//prox.listAI(new ListAI_Params());

			prox.finishTurn(new FinishTurn_Params());
			prox.discardCards(new DiscardCards_Params());
			prox.createGame(new Create_Params());
			prox.buyDevCard(new BuyDevCard_Params());
			prox.buildSettlement(new BuildSettlement_Params());
			prox.buildRoad(new BuildRoad_Params());
			prox.buildCity(new BuildCity_Params());
			prox.acceptTrade(new AcceptTrade_Params());*/
		}

		catch (Exception e){
			fail();
		}
	}

	//@Test
	public void testCookies() throws ClientException {

		prox.login(new Login_Params("Sam", "sam"));
		String userCookie = prox.getUserCookie();

		assertNotNull(userCookie);

	}
}
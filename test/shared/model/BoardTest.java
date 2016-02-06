package shared.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shared.model.board.*;
import shared.model.player.Player;
import shared.definitions.ResourceType;
import shared.locations.*;

public class BoardTest {
	
	private Board board1, board2, board3;
	private boolean randomHexType, randomHexRollValues,  randomPorts;
	
	private HexLocation hexlocation;
	private VertexLocation vertexLocation;
	private VertexDirection vertexdirection;
	
	private Bank bank;
	private Player player;
	
	
	@Before//called before each test
	public void setup(){
		
		//all true
		randomHexType = true;
		randomHexRollValues = true;
		randomPorts = true;
		board1 = new Board(randomHexType, randomHexRollValues,  randomPorts);
		
		//all false
		randomHexType = false;
		randomHexRollValues = false;
		randomPorts = false;
		board2 = new Board(randomHexType, randomHexRollValues,  randomPorts);
		
		//mix true and false
		randomHexType = true;
		randomHexRollValues = false;
		randomPorts = false;
		board3 = new Board(randomHexType, randomHexRollValues,  randomPorts);
		
		bank = new Bank();
	}
	
	
	/*
	@After
	public void tearDown(){
	}*/

	@Test
	public void testCanDoMoveRobberToHex(){
		
		//Each Hex Location x and y has 3 added to it to adjust to our modification of the board
		//Board1: Test position (3,6) which is null
		hexlocation = new HexLocation(0,3);
		boolean test = board1.canDoMoveRobberToHex(hexlocation);
		assertEquals(false, test);
		
		//Board1: Test position (4,4) which is a hex on the board
		hexlocation = new HexLocation(1,1);
		test = board1.canDoMoveRobberToHex(hexlocation);
		assertEquals(true, test);
		
		//Board3: Test position (2,3) which is a hex on the board
		hexlocation = new HexLocation(-1,0);
		test = board3.canDoMoveRobberToHex(hexlocation);
		assertEquals(true, test);
	}
	
	@Test
	public void moveRobberToHex(){
		//Board3: Test position (2,5). Should properly move robber to (2,5)
		hexlocation = new HexLocation(-1,2);
		try {
			board3.moveRobberToHex(hexlocation);
		} catch (Exception e) {
			fail();
		}
		
		/*
		 * Board3: re-test position (2,5). Should not allow robber to be placed on (2,5) because it is
		 * already there
		 */
		try {
			board3.moveRobberToHex(hexlocation);
			fail();
		} catch (Exception e) {}
		
		/*
		 * Board2: Test position (5,1). When the Hexes aren't randomized, they are alphabetical. 
		 * Thus, the robber is by default placed on (5,1).
		*/
		hexlocation = new HexLocation(-2,5);
		try {
			board2.moveRobberToHex(hexlocation);
			fail();
		} catch (Exception e) {}
	}
	
	@Test
	public void testPlaceRoadOnEdge() {
		//Board1: test position(5,1). Test placing a road on the ocean border
		player = new Player(2, bank);
		EdgeDirection edgedirection = null;
		HexLocation hexlocation = new HexLocation(2,-2);
		EdgeLocation edgeLocation = new EdgeLocation(hexlocation, edgedirection.SouthEast);
		try {
			board1.placeRoadOnEdge(player, edgeLocation);
		} catch (Exception e) {
			fail();
		}
		
		//Board1: test position(5,1). Cannot, place road on a road
		try {
			board1.placeRoadOnEdge(player, edgeLocation);
			fail();
		} catch (Exception e) {}
		
		/*
		 * Board1: Test position (3,1) and place a road adjacent to it on position (3,2)
		 */
		hexlocation = new HexLocation(0,-2);
		edgeLocation = new EdgeLocation(hexlocation, edgedirection.SouthEast);
		try {
			board1.placeRoadOnEdge(player, edgeLocation);
		} catch (Exception e) {
			System.out.println("Exception was thrown in testPlaceRoadOnEdge()");
		}
		
		hexlocation = new HexLocation(0,-1);
		edgeLocation = new EdgeLocation(hexlocation, edgedirection.NorthEast);
		try {
			board1.placeRoadOnEdge(player, edgeLocation);
		} catch (Exception e) {
			System.out.println("Exception was thrown in testPlaceRoadOnEdge()");
			fail();
		}
	}
	
	@Test
	public void testplaceSettlementOnVertex() throws Exception{
		//build a road at (3,3)
		player = new Player(1, bank);
		EdgeDirection edgedirection = null;
		hexlocation = new HexLocation(0,0);
		EdgeLocation edgeLocation = new EdgeLocation(hexlocation, edgedirection.SouthEast);
		try {
			board1.placeRoadOnEdge(player, edgeLocation);
		} catch (Exception e) {
			fail();
		}
		
		//Board1: Test position (3,3)
		vertexdirection = null;
		vertexLocation = new VertexLocation(hexlocation, vertexdirection.SouthEast);
		board1.placeSettlementOnVertex(player, vertexLocation);
	}
	
	@Test
	public void testPlaceCityOnVertex(){
		//Setup in order to buy a City
		player = new Player(1, bank);
		Bank playerBank = player.getResourceCardHand().getBank();
		try {
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.ORE));
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.ORE));
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.ORE));
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.WHEAT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//build a road at (4,4). Special case: builds on ocean border
		EdgeDirection edgedirection = null;
		hexlocation = new HexLocation(1,1);
		EdgeLocation edgeLocation = new EdgeLocation(hexlocation, edgedirection.SouthEast);
		try {
			board1.placeRoadOnEdge(player, edgeLocation);
		} catch (Exception e) {
			fail();
		}
		
		//Board1: Test position (4,4). Special case: builds on ocean border
		vertexdirection = null;
		vertexLocation = new VertexLocation(hexlocation, vertexdirection.SouthEast);
		try {
			board1.placeSettlementOnVertex(player, vertexLocation);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Builds on (4,4). Special case: builds on ocean border
		VertexDirection vertexdirection = null;
		vertexLocation = new VertexLocation(hexlocation, vertexdirection.SouthEast);
		try {
			board1.placeCityOnVertex(player,  vertexLocation);
		} catch (Exception e) {
			fail();
		}
	}

}

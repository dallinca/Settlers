package shared.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shared.model.board.*;
import shared.model.player.Player;
import shared.locations.*;

public class BoardTest {
	
	private Board board1, board2, board3;
	private boolean randomHexType, randomHexRollValues,  randomPorts;
	
	private HexLocation hexlocation;
	private VertexLocation vertexLocation;
	
	private Bank bank;
	private Player player;
	
	
	@Before
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
		hexlocation = new HexLocation(2,-2);
		try {
			board2.moveRobberToHex(hexlocation);
			fail();
		} catch (Exception e) {}
	}
	
	@Test
	public void testPlaceRoadOnEdge(){
		//Board1: test position(4,4)
		player = new Player(2, bank);
		EdgeDirection edgedirection = null;
		HexLocation hexlocation = new HexLocation(1,1);
		EdgeLocation edgeLocation = new EdgeLocation(hexlocation,edgedirection.NorthWest);
		try {
			board1.placeRoadOnEdge(player, edgeLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testplaceSettlementOnVertex() throws Exception{
		bank = new Bank();
		player = new Player(1, bank);
		//Board1: Test position (4,4).
		hexlocation = new HexLocation(1,1);
		VertexDirection vertexdirection = null;
		VertexLocation vertexLocation = new VertexLocation(hexlocation, vertexdirection.NorthWest);
		//There is no roads, so it wont let me place settlements. We need to beable to place 2 settlements before
		//placing any roads
		board1.placeSettlementOnVertex(player, vertexLocation);
	}
	
	@Test
	public void testPlaceCityOnVertex(){
		/*
		player = new Player(1, bank);
		VertexDirection vertexdirection = null;
		vertexLocation = new VertexLocation(hexlocation, vertexdirection.SouthWest);
		try {
			board1.placeCityOnVertex(player,  vertexLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}

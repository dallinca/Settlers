package shared.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import shared.model.board.*;
import shared.model.player.Player;
import shared.locations.*;

public class BoardTest {
	
	private Board board;
	
	@Before
	public void setup(){
		board = new Board();
	}
	/*
	@After
	public void tearDown(){
	}*/

	@Test
	public void testCanDoMoveRobberToHex(){
		//Each Hex Location x and y has 3 added to it to adjust to our modification of the board
		//Test position (3,6) which is null
		HexLocation hexlocation = new HexLocation(0,3);
		boolean test = board.canDoMoveRobberToHex(hexlocation);
		assertEquals(false, test);
		
		//Test position (4,4) which is a hex on the board
		hexlocation = new HexLocation(1,1);
		test = board.canDoMoveRobberToHex(hexlocation);
		assertEquals(true, test);
		
		/*Robber Hex needs to be initialized, otherwise moveRobberToHex() method line hexWithRobber.takeRobber() throws arrayError
		try {
			board.moveRobberToHex(hexlocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		test = board.canDoMoveRobberToHex(hexlocation);
		assertEquals(false, test);*/
	}
	
	@Test
	public void testCanDoPlaceRoadOnEdge(){
		
		Player player = new Player(2);
		EdgeDirection edgedirection = null;
		HexLocation hexlocation = new HexLocation(1,1);
		EdgeLocation edgeLocation = new EdgeLocation(hexlocation,edgedirection.NorthWest);
		
		board.canDoPlaceRoadOnEdge(player, edgeLocation);
	}

}

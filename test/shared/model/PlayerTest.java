package shared.model;

import static org.junit.Assert.*;

import org.junit.*;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.board.Board;
import shared.model.player.Player;

public class PlayerTest {
	
	private static Board board;
	private static Game game;
	private static boolean randomHexType, randomHexRollValues,  randomPorts;
	
	private static Player player1, player2, player3, player4;
	private HexLocation hexlocation;
	private VertexLocation vertexLocation;
	private VertexDirection vertexdirection;
	
	private static Bank bank;
	
	@BeforeClass 
	 public static void setupBeforeClass(){
		//all true
		randomHexType = false;
		randomHexRollValues = false;
		randomPorts = true;
		board = new Board(randomHexType, randomHexRollValues,  randomPorts);
		
		bank = new Bank();
		player1 = new Player(1, bank);
		player2 = new Player(2, bank);
		player3 = new Player(3, bank);
		player4 = new Player(4, bank);
		
	    game = new Game(player1, player2, player3, player4, board);
	}
	
	@Test
	public void testCollectResources(){
		//Special Test: build settlement on desert and on coast at 1,5
		EdgeDirection edgedirection = null;
		//(1,5)
		hexlocation = new HexLocation(-2,2);
		EdgeLocation edgeLocation = new EdgeLocation(hexlocation, edgedirection.SouthEast);
		try {
			board.placeRoadOnEdge(player1, edgeLocation);
		} catch (Exception e) {
			fail();
		}
		
		//Board1: Test position (1,5)
		vertexdirection = null;
		vertexLocation = new VertexLocation(hexlocation, vertexdirection.SouthEast);
		try {
			board.placeSettlementOnVertex(player1, vertexLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Bank playerBank = player1.getResourceCardHand().getBank();
		int rollValue = 8;
		try {
			player1.collectResources(rollValue, playerBank);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

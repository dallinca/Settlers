package shared.model;

import static org.junit.Assert.*;

import org.junit.*;

import shared.locations.HexLocation;
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
	
	private static Bank bank;
	
	@BeforeClass 
	 public static void setupBeforeClass(){
		//all true
		randomHexType = true;
		randomHexRollValues = true;
		randomPorts = true;
		board = new Board(randomHexType, randomHexRollValues,  randomPorts);
		
		bank = new Bank();
		player1 = new Player(1, bank);
		player2 = new Player(2, bank);
		player3 = new Player(3, bank);
		player4 = new Player(4, bank);
		
	    game = new Game(player1, player2, player3, player4, board);
	}
	
	@Before
	public void setup(){
		player = new Player(3);
	}
	/*
	@After
	public void tearDown(){
	}*/
	
	@Test
	public void testBuyRoad(){
		player.BuyRoad(edge);
	}
	
	@Test
	public void testBuySettlement(){
		player.BuySettlement(vertex);
	}
	
	@Test
	public void testBuyCity(){
		player.BuyCity(vertex);
	}
	
	@Test
	public void testCollectResources(){
		int rollValue = 4;
		player.collectResources(rollValue);
	}
}

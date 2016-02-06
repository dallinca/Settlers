package shared.model;

import static org.junit.Assert.*;

import org.junit.*;

import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.board.Board;
import shared.model.player.Player;

public class PlayerTest {
	
	
	private static Board board;
	private static game game;
	private static boolean randomHexType, randomHexRollValues,  randomPorts;
	
	private Player player1, player2, player3, player4;
	private HexLocation hexlocation;
	private VertexLocation vertexLocation;
	
	private Bank bank;
	
	@BeforeClass 
	 public static void setupBeforeClass(){
		//all true
		randomHexType = true;
		randomHexRollValues = true;
		randomPorts = true;
		board = new Board(randomHexType, randomHexRollValues,  randomPorts);
		player1, player2, player3, player4;
		 game = new game(Player one, Player two, Player three, Player four, Board board1)
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

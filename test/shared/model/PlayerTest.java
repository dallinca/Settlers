package shared.model;

import static org.junit.Assert.*;

import org.junit.*;

import shared.model.player.Player;

public class PlayerTest {
	
	private Player player;
	
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

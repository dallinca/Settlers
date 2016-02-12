package shared.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.definitions.ResourceType;
import shared.model.board.TradePort;
import shared.model.items.ResourceCard;
import shared.model.player.Player;

public class BankTest {

	private static Bank bank;
	private static Player player1,player2; 
	
	@BeforeClass 
	 public static void setupBeforeClass(){
		bank = new Bank();
		
		player1 = new Player(1, bank);
		player2 = new Player(2, bank);
		Bank player1Bank = player1.getResourceCardHand().getBank();
		try {
			player1.getResourceCardHand().addCard(player1Bank.playerTakeResource(ResourceType.ORE));
			player1.getResourceCardHand().addCard(player1Bank.playerTakeResource(ResourceType.ORE));
			player1.getResourceCardHand().addCard(player1Bank.playerTakeResource(ResourceType.ORE));
			player1.getResourceCardHand().addCard(player1Bank.playerTakeResource(ResourceType.WHEAT));
			
			//player.getResourceCardHand().
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	@Test
	public void testPlayerTrade(){
		player1.getResourceCardHand().;
		ResourceCard playerTrade(ResourceCard one, ResourceCard two, ResourceCard three, ResourceType resourceType, TradePort tradePort)
		bank.playerTrade(one, two, resourceType, tradePort);
	}*/
}

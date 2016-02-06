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
	private static Player player; 
	
	@BeforeClass 
	 public static void setupBeforeClass(){
		bank = new Bank();
		player = new Player(1, bank);
		Bank playerBank = player.getResourceCardHand().getBank();
		try {
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.ORE));
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.ORE));
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.ORE));
			player.getResourceCardHand().addCard(playerBank.playerTakeResource(ResourceType.WHEAT));
			
			//player.getResourceCardHand().
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	@Test
	public void testPlayerTrade(){
		
		ResourceCard playerTrade(ResourceCard one, ResourceCard two, ResourceCard three, ResourceType resourceType, TradePort tradePort)
		bank.playerTrade(one, two, resourceType, tradePort);
	}*/
}

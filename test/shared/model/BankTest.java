package shared.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankTest {

	private static Bank bank;
	
	@BeforeClass 
	 public static void setupBeforeClass(){
		bank = new Bank();
	}
	
	@Test
	public void testPlayerTrade(){
		bank.playerTrade(one, two, resourceType, tradePort);
	}
}

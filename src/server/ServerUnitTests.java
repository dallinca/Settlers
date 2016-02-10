package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}
	
	 public static void main(String[] args){
		 String[] testCases = new String[] {
				 "shared.model.PlayerTest",
				 "shared.model.BoardTest",
				 "client.communication.ServerPollerTest",
				 "client.communication.ServerProxyTest"
		 };
		 org.junit.runner.JUnitCore.main(testCases);
	 }
}

package client.communication;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import shared.communication.params.move.*;
import shared.communication.params.nonmove.*;
import shared.communication.params.move.devcard.*;
import client.Client;
import client.ClientException;
import client.ServerPoller;
import client.proxy.*;

/**
 * Tests functions of the server proxy. Ant server must be running beforehand.
 *
 */
public class ServerPollerTest {

	private MockServerProxy prox;
	private ServerPoller poller;
	private Client client;

	@Before
	public void setUp(){

	}

	@AfterClass
	public static void tearDownAfterClass(){		

	}

	@Test
	public void PollTest() throws ClientException {
		
		poller = new ServerPoller();
		
		assertTrue(poller.start());
		assertTrue(poller.stop());
		assertTrue(poller.start());		
		
		try {
		    Thread.sleep(4000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		assertTrue(poller.stop());
				
	}
}
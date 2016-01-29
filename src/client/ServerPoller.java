package client;

import shared.communication.results.PollServer_Result;

/**
 * Polls server every few seconds to grab new information 
 * and synchronize with game state of all players.
 * @author jchrisw
 *
 */
public class ServerPoller {
	
	/**
	 * 
	 * Server poller may be created with either mock server or real server in mind.
	 * 
	 * @param serverExists
	 * 
	 * @pre None
	 * @post Server poller is created.
	 */
	ServerPoller(boolean serverExists){
		
		
	}
	
	/**
	 * Internally keeps track of time since last poll, to keep game synchronized.
	 * 
	 * @pre None
	 * @post triggers poll at regular time intervals.
	 */
	private static void timer(){		
		
	}
	
	/**
	 * Posts a call to the server to find any new developments in game state.
	 * 
	 * @pre Must be validated and participating in game.
	 * @post Current game state will be obtained from server.
	 */	
	private static void pollServer(){
		
	}
	

	/**
	 * Takes result from server poll and synchronizes current game with the new information.
	 * 
	 * @pre Client is participating in a game
	 * @post Client's game state will be synchronized with server's.
	 */
	private static void synchronizeGameState(PollServer_Result result){
				
	}
	
	
}

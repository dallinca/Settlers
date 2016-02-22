package client;

import java.util.Timer;
import java.util.TimerTask;

import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.model.Game;
import client.proxy.IServerProxy;


/**
 * Polls server every few seconds to grab new information 
 * and synchronize with game state of all players.
 * @author jchrisw
 *
 */
public class ServerPoller {
	
	private Timer pollTimer;
	private IServerProxy proxy;
	private Client client;
	
	/**
	 * 
	 * Server poller may be created with either mock server or real server in mind.
	 * 
	 * @param IServerProxy proxy, Client client
	 * 
	 * @pre None
	 * @post Server poller is created.
	 */
	public ServerPoller(IServerProxy proxy, Client client){
		this.client = client;
		this.proxy = proxy;			
	}
	
	public boolean start(){
		pollTimer = new Timer();
		pollTimer.schedule(new timedPoll(), 0, 1500);	//every 1.5 seconds
		return true;
	}
	
	public boolean stop(){
		pollTimer.cancel();
		return true;
	}
	
	/**
	 * Internally keeps track of time since last poll, to keep game synchronized.
	 * 
	 * @pre None
	 * @post triggers poll at regular time intervals.
	 */
	class timedPoll extends TimerTask {
	    public void run() {
	       pollServer();
	    }
	 }	 
	
	/**
	 * Posts a call to the server to find any new developments in game state.
	 * 
	 * @pre Must be validated and participating in game.
	 * @post Current game state will be obtained from server.
	 */	
	private void pollServer(){
		GetVersion_Params pollRequest = new GetVersion_Params(client.getGame().getVersionNumber());
		GetVersion_Result pollResult = null;
		
		try {
			pollResult = proxy.getVersion(pollRequest);
		} catch (ClientException e) {
			System.out.println("Server poll failed.");
			e.printStackTrace();
		}
		
		if (pollResult!=null){
			
			Game update = pollResult.getGame();
			
			Game clientGame = client.getGame();
			
			if (clientGame == null){
				
				client.setGame(pollResult.getGame());	
			} 
			
			else if (update.getVersionNumber() > client.getGame().getVersionNumber()){
				
				client.setGame(pollResult.getGame());				
			}		
			
		}	
		
	}
	

	/**
	 * Takes result from server poll and synchronizes current game with the new information.
	 * 
	 * @pre Client is participating in a game
	 * @post Client's game state will be synchronized with server's.
	 */
	//private static void synchronizeGameState(PollServer_Result result){
	//			
	//}
	
	
}

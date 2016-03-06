package client;

import java.util.Timer;
import java.util.TimerTask;

import shared.communication.results.nonmove.GetVersion_Result;
import shared.model.Game;


/**
 * Polls server every few seconds to grab new information 
 * and synchronize with game state of all players.
 * @author jchrisw
 *
 */
public class ServerPoller {

	private Timer pollTimer;
	private int interval;

	/**
	 * 
	 * Server poller may be created with either mock server or real server in mind.
	 * 
	 * @param IServerProxy proxy, Client client
	 * 
	 * @pre None
	 * @post Server poller is created.
	 */
	public ServerPoller(){		
		this.interval = 5000;
	}

	public boolean start(){
		pollTimer = new Timer();
		pollTimer.schedule(new timedPoll(), 0, interval);	//every 1.5 seconds
		return true;
	}

	public boolean stop(){
		pollTimer.cancel();
		return true;
	}

	public void setTimer(int interval){
		this.interval = interval;
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
		System.out.print(" Poll.");

		GetVersion_Result pollResult = ClientFacade.getInstance().getVersion();

		if (pollResult.isValid()){

			if (!pollResult.isUpToDate()){				

				Game update = pollResult.getGame();
				Client.getInstance().setGame(update);	

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

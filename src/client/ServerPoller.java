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
	 * Creates a new server poller with default 5 second polling time.
	 * 
	 */
	public ServerPoller(){		
		this.interval = 5000;
	}

	/**
	 * Starts the polling timer.
	 * 
	 * 
	 * @return true if timer triggered successfully.
	 */
	public boolean start(){
		pollTimer = new Timer();
		pollTimer.schedule(new timedPoll(), 0, interval);	//every 1.5 seconds
		return true;
	}

	/**
	 * Stops polling timer.
	 * 
	 * 
	 * @return true if timer stopped successfully.
	 */
	public boolean stop(){
		pollTimer.cancel();
		return true;
	}

	public void setTimer(int interval){
		this.interval = interval;
	}

	/**
	 * Internally keeps track of time since last poll, and keeps game synchronized.
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
}

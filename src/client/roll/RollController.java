package client.roll;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import shared.model.Game;
import client.Client;
import client.ClientFacade;
import client.base.*;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;
	private Timer rollTimer;
	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		System.out.println("RollController RollController()");

		setResultView(resultView);
		this.rollTimer = new Timer();
		Client.getInstance().addObserver(this);
	}

	public IRollResultView getResultView() {
		System.out.println("RollController getResultView()");
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		System.out.println("RollController setResultView()");
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		System.out.println("RollController getRollView()");
		return (IRollView)getView();
	}

	/**
	 * Gets the game to roll the dice, then sends the result to the client facade.
	 * Displays result view and closes roll view when successful.
	 * 
	 */
	@Override
	public void rollDice() {
		rollTimer.cancel();
		rollTimer = new Timer();

		System.out.println("RollController rollDice()");
		int rollValue = 0;
		try {
			rollValue = Client.getInstance().getGame().RollDice(Client.getInstance().getUserId());

			this.resultView.setRollValue(rollValue);
			getRollView().closeModal();
			ClientFacade.getInstance().rollNumber(rollValue);
			getResultView().showModal();



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the roll controller to reflect current situation in game object.
	 * --Triggers only when in rolling, and client's turn.
	 * --Displays roll view modal, and sets an automatic roll timer after 5 secodns.
	 * 
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("RollController update()");

		Game game = Client.getInstance().getGame();
		int userid = Client.getInstance().getUserId();

		// If the game is null just return
		if(game == null) {
			return;
		} else if (game.getStatus().equals("First round") || game.getStatus().equals("Second round")){
			return;
		}
		else if (game.getStatus().equals("Robbing")||game.getStatus().equals("Discarding")){
			return;
		} else if(game.canDoRollDice(userid)&&game.getStatus().equals("Rolling")){
			//System.out.println("Drawing modal and setting roll timer.----------------------------");
			getRollView().showModal();
			rollTimer.schedule(new timedRollDice(), 5000);
			getRollView().setMessage("Rolling automatically in 5 seconds");			
		}
	}

	/**
	 * Timer to roll the dice after a set interval of time.
	 */
	class timedRollDice extends TimerTask {
		public void run() {
			rollDice();
		}
	}
}


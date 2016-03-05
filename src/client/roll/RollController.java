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

	@Override
	public void rollDice() {
		rollTimer.cancel();
		System.out.println("RollController rollDice()");
		int rollValue = 0;
		try {
			rollValue = Client.getInstance().getGame().RollDice(Client.getInstance().getUserId());
			ClientFacade.getInstance().rollNumber(rollValue);
			this.resultView.setRollValue(rollValue);
			getResultView().showModal();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("RollController update()");
		// If the game is null just return
		Game game = Client.getInstance().getGame();
		if(game == null) {
			return;
		}

		//Client client = (Client) o;
		int userid = Client.getInstance().getUserId();
		if(game.canDoRollDice(userid)){

			getRollView().showModal();
			getRollView().setMessage("Rolling automatically in... 10 seconds");
			int interval = 10000;
			rollTimer.scheduleAtFixedRate(new timedPoll(), interval, 1000);
		}
	}

	class timedPoll extends TimerTask {
		public void run() {
			getRollView().closeModal();
			rollDice();
		}
	}
}


package client.roll;

import java.util.Observable;
import java.util.Observer;

import client.base.*;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;

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
		System.out.println("RollController rollDice()");
		getResultView().showModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("RollController update()");
	}

}


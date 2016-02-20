package client.turntracker;

import shared.definitions.CatanColor;

import java.util.Observable;
import java.util.Observer;

import client.base.*;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		System.out.println("TurnTrackerController TurnTrackerController()");
		
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		System.out.println("TurnTrackerController getView()");
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		System.out.println("TurnTrackerController endTurn()");
	}
	
	private void initFromModel() {
		System.out.println("TurnTrackerController initFromModel()");
		//<temp>
		getView().setLocalPlayerColor(CatanColor.RED);
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("TurnTrackerController update()");
		// TODO Auto-generated method stub
		
	}

}


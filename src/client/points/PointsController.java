package client.points;

import java.util.Observable;
import java.util.Observer;

import client.base.*;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		System.out.println("PointsController PointsController()");
		setFinishedView(finishedView);
		
		initFromModel();
	}
	
	public IPointsView getPointsView() {
		System.out.println("PointsController getPointsView()");
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		System.out.println("PointsController getFinishedView()");
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		System.out.println("PointsController setFinishedView()");
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		System.out.println("PointsController initFromModel()");
		//<temp>		
		getPointsView().setPoints(5);
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("PointsController update()");
		// TODO Auto-generated method stub
		
	}
	
}


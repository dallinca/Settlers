package client.devcards;

import shared.definitions.ResourceType;

import java.util.Observable;
import java.util.Observer;

import client.base.*;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		System.out.println("DevCardController DevCardController()");
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		System.out.println("DevCardController getPlayCardView()");
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		System.out.println("DevCardController getBuyCardView()");
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		System.out.println("DevCardController startBuyCard()");
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		System.out.println("DevCardController cancelBuyCard()");
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		System.out.println("DevCardController buyCard()");
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		System.out.println("DevCardController startPlayCard()");
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		System.out.println("DevCardController cancelPlayCard()");
		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		System.out.println("DevCardController playMonopolyCard()");
	}

	@Override
	public void playMonumentCard() {
		System.out.println("DevCardController playMonumentCard()");
	}

	@Override
	public void playRoadBuildCard() {
		System.out.println("DevCardController playRoadBuildCard()");
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		System.out.println("DevCardController playSoldierCard()");
		
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		System.out.println("DevCardController playYearOfPlentyCard()");
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DevCardController update()");
		// TODO Auto-generated method stub
		
		
		
		
		
		
	}

}


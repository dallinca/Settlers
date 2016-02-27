package client.devcards;

import shared.definitions.ResourceType;
import shared.model.turn.ActionManager;
import shared.model.turn.ActionType;

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
		
		boolean canBuy = ActionManager.getInstance().canDoPurchase(ActionType.PURCHASE_DEVELOPMENT);
		
		if (canBuy) {
			getBuyCardView().showModal();
		} else {
			 //throw new Exception
		}		
	}

	@Override
	public void cancelBuyCard() {
		System.out.println("DevCardController cancelBuyCard()");
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		System.out.println("DevCardController buyCard()");	
			//How does this whole thing work in getting the player's index and identifying if the specific person can buy it
			boolean result = ActionManager.getInstance().canDoPurchase(ActionType.PURCHASE_DEVELOPMENT);
			 
			 if (result) {
				 try {	
					 ActionManager.getInstance().doPurchase(ActionType.PURCHASE_DEVELOPMENT);
				 } catch {
						//throw new ClientException e;
				 }
				 getBuyCardView().closeModal();
			 } else {
				 //throw new Exception
			 }
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
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_MONOPOLY);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_MONOPOLY);
			} catch {
				
			}
		}
		
		
	}

	@Override
	public void playMonumentCard() {
		System.out.println("DevCardController playMonumentCard()");
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_MONUMENT);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_MONUMENT);
			} catch {
				
			}
		}
	}

	@Override
	public void playRoadBuildCard() {
		System.out.println("DevCardController playRoadBuildCard()");
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_BUILDROADS);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_BUILDROADS);
				roadAction.execute();
			} catch {
				
			}
		}
		
	}

	@Override
	public void playSoldierCard() {
		System.out.println("DevCardController playSoldierCard()");
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_KNIGHT);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_KNIGHT);
				soldierAction.execute();
			} catch {
				
			}
		}
		
		
		
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		System.out.println("DevCardController playYearOfPlentyCard()");
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_YEAROFPLENTY);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_YEAROFPLENTY);
			} catch {
				
			}
		}
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DevCardController update()");
		// TODO Auto-generated method stub
		
		
		
		
		
		
	}

}


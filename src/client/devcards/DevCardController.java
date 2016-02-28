package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.turn.ActionManager;
import shared.model.turn.ActionType;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	
			//This is probably the place to first start out everything at 0, and then initialize them as we pull them from the model.
		
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
					 
					 try {
						 	//This means you bought a card and it worked ;)
					        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Purchased.wav").getAbsoluteFile());
					        Clip clip = AudioSystem.getClip();
					        clip.open(audioInputStream);
					        clip.start();
					    } catch(Exception ex) {
					        System.out.println("Error with playing sound.");
					        ex.printStackTrace();
					    }
				 } catch(Exception ex) {
				        System.out.println("Error with Buying a Card.");
				        ex.printStackTrace();
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
		ResourceType toPassIn[] = new ResourceType[1];
		toPassIn[0] = resource;
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_MONOPOLY, toPassIn);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_MONOPOLY, toPassIn);
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
			ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_KNIGHT);
			soldierAction.execute();			
		}
		
		
		
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		System.out.println("DevCardController playYearOfPlentyCard()");
		
		ResourceType toPassIn[] = null;
		
		if (resource1 == resource2) {
			toPassIn = new ResourceType[1];
			toPassIn[0] = resource1;			
		} else {
			toPassIn = new ResourceType[2];
			toPassIn[0] = resource1;
			toPassIn[1] = resource2;
		}
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_YEAROFPLENTY, toPassIn);
		if (result) {
			ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_YEAROFPLENTY, toPassIn);
		}
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DevCardController update()");
		// TODO Auto-generated method stub
		
		//This method needs to figure out how many cards the current player has, how many are playable, and what types they are. We need to get those three things from the server.
		//So it would appear that it would follow that we should create three lists that have this information in them, or check and see if the Facade has the functionality to send them to us at this point
		
		
		//And as we check to see if any are playable, if we find at least one that is, we pass the view that information and the type it is. So we have to iterate through all of these cards.
		//If we find none, then our boolean in the method call will be false: setCardEnabled(DevCardType cardType, boolean enabled)
		//And if we find any at all, regardless of playable or not, we will call this method setCardAmount(DevCardType cardType, int amount)
		//
		
		
		
		
	}

}


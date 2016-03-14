package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.turn.ActionManager;
import shared.model.turn.ActionType;

import java.io.File;
//import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import client.Client;
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

	/**
	 * Gets the playCard view
	 * @pre view != null
	 * @return the view
	 */
	public IPlayDevCardView getPlayCardView() {
		System.out.println("DevCardController getPlayCardView()");
		return (IPlayDevCardView)super.getView();
	}

	/**
	 * Gets the buyCardView
	 * @pre view != null
	 * @return
	 */
	public IBuyDevCardView getBuyCardView() {
		System.out.println("DevCardController getBuyCardView()");
		return buyCardView;
	}

	/**
	 * This method calls the action manager to see if you have the resources to buy a card and if it is your turn or not.
	 * @pre the game isn't null and that the Client exists
	 */
	@Override
	public void startBuyCard() {
		System.out.println("DevCardController startBuyCard()");
		
		//for the test: 
		//getBuyCardView().showModal();
		
		boolean canBuy = ActionManager.getInstance().canDoPurchase(ActionType.PURCHASE_DEVELOPMENT);
		
		if (canBuy) {
			buyCardView.showModal();
		} else {
			 //throw new Exception
		}		
	}

	/**
	 * Closes the modal when you click you dont want to buy a card
	 * @pre the modal was open prior to calling this method
	 */
	@Override
	public void cancelBuyCard() {
		System.out.println("DevCardController cancelBuyCard()");
		buyCardView.closeModal();
	}

	/**
	 * This method calls the ClientFacade through calling the action manager to purchase a development card for this particular instance of the client
	 * @pre the ClientFacade is not null.
	 */
	@Override
	public void buyCard() {
		System.out.println("DevCardController buyCard()");	
			//How does this whole thing work in getting the player's index and identifying if the specific person can buy it
			boolean result = ActionManager.getInstance().canDoPurchase(ActionType.PURCHASE_DEVELOPMENT);
			 
			 if (result) {
				 try {	
					 ActionManager.getInstance().doAction(ActionType.PURCHASE_DEVELOPMENT);
					 					 
					try {
					 		//for later hahaha
						 	//This means you bought a card and it worked ;)
					        /*AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Purchased.wav").getAbsoluteFile());
					        Clip clip = AudioSystem.getClip();
					        clip.open(audioInputStream);
					        clip.start();*/
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

	/**
	 * In this function we check and see how many cards each player has, and enable the ones they have if they can play them, which takes into account the turn they were played, and who's turn it is.
	 * It also displays that number
	 * @pre
	 */
	@Override
	public void startPlayCard() {
		
		//And as we check to see if any are playable, if we find at least one that is, we pass the view that information and the type it is. So we have to iterate through all of these cards.
		//If we find none, then our boolean in the method call will be false: setCardEnabled(DevCardType cardType, boolean enabled)
		//And if we find any at all, regardless of playable or not, we will call this method setCardAmount(DevCardType cardType, int amount)
		//this is done in the start play
		
		System.out.println("DevCardController startPlayCard()");
		DevCardType[] types = {DevCardType.YEAR_OF_PLENTY, DevCardType.SOLDIER, DevCardType.MONOPOLY, DevCardType.MONUMENT, DevCardType.ROAD_BUILD};
		
		for (int i = 0; i < types.length; i++) {
			boolean enableOrNot = Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), types[i]);
			
			
			System.out.println("Have I played a dev card this turn? " + Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).isHasPlayedDevCardThisTurn());
			
			getPlayCardView().setCardAmount(types[i], Client.getInstance().getGame().numberUnplayedDevCards(Client.getInstance().getUserId(), types[i]));
			
			System.out.println(Client.getInstance().getName());
			
			if (Client.getInstance().getGame().getCurrentPlayer().getPlayerId() == Client.getInstance().getUserId()) {
				getPlayCardView().setCardEnabled(types[i], enableOrNot);
			} else {
				getPlayCardView().setCardEnabled(types[i], false);
			}
			
			//forSoldiertest:
			//getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
		}
		getPlayCardView().showModal();
	}

	/**
	 * In case you don't want to play a card, this method is called when you click the cancel button
	 * @pre the modal to be showing prior to this being called.
	 */
	@Override
	public void cancelPlayCard() {
		System.out.println("DevCardController cancelPlayCard()");
		getPlayCardView().closeModal();
	}

	/**
	 * This method calls the action manager to talk to the ClientFacade based on the type of development card this method uses.
	 * @pre its your turn and you are able to play a monopoly card
	 */
	@Override
	public void playMonopolyCard(ResourceType resource) {
		System.out.println("DevCardController playMonopolyCard()");
		ResourceType toPassIn[] = new ResourceType[1];
		toPassIn[0] = resource;
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_MONOPOLY, toPassIn);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_MONOPOLY, toPassIn);
			} catch (Exception e) {
				System.out.println("Something went wrong while trying to play a monopoly card");
			}
		}
	}
	
	
	/**
	 * Very similar to all of these "play methods" It eventually gets the data to the ClientFacade which creates a params object and sends it to the server.
	 * This one is unique to monument cards
	 * @pre its your turn and you actually have a monument card 
	 */
	@Override
	public void playMonumentCard() {
		System.out.println("DevCardController playMonumentCard()");
		
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_MONUMENT);
		if (result) {
			try {
				ActionManager.getInstance().doAction(ActionType.PLAYCARD_MONUMENT);
			} catch (Exception e) {
				System.out.println("Something went wrong while trying to play a monument card");
			}
		}
	}
	
	/**
	 * This method allows you to play two roads for free because of the development card.
	 * @pre you have to own the card and it has to be your turn 
	 */
	@Override
	public void playRoadBuildCard() {
		System.out.println("DevCardController playRoadBuildCard()");
		Client.getInstance().setInRoadBuilding(true);
		boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_BUILDROADS);
		if (result) {
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_BUILDROADS);
			} catch (Exception e) {
				System.out.println("Something went wrong while trying to play a road build card");
			}
			roadAction.execute();
		}
		
	}

	/**
	 * This used to do more but with the application we found within the MapController, we didn't need to do as much.
	 * @pre you need to own a soldier card and it needs to be your turn.
	 */
	@Override
	public void playSoldierCard() {
		System.out.println("DevCardController playSoldierCard()");
		
		getPlayCardView().closeModal();
		soldierAction.execute();
		
		
		/*boolean result = ActionManager.getInstance().canDoPlay(ActionType.PLAYCARD_KNIGHT);
		if (result) {
			//soldierAction.execute();
			
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_KNIGHT);
			} catch (Exception e) {
				System.out.println("Something went wrong while trying to play a Soldier card");
			}			
		}*/
		
		
		
	}
	
	/**
	 * This method allows you to play your year of plenty card and request two resources, however it assumes it is your turn and you actually have and can play that card.
	 * @pre its your turn and you have owned the card for at least a turn.
	 */
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
			try {
				ActionManager.getInstance().playDevelopmentCard(ActionType.PLAYCARD_YEAROFPLENTY, toPassIn);
				System.out.println("We just called the action manager to play a year of Plenty Card");
			} catch (Exception e) {
				System.out.println("Something went wrong while trying to play a year of plenty card");
			}
		}
		
		
	}
	
	/**
	 * After much deliberation, this method really has no purpose. Everything about this function that would need to be updated occurs when you click on the button to bring up your view
	 * @pre the ClientFacade must exist
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("DevCardController update()");
		// If the game is null just return
		if(Client.getInstance().getGame() == null) {
			return;
		}	

		/*getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, Client.getInstance().getGame().numberUnplayedDevCards(Client.getInstance().getUserId(), DevCardType.YEAR_OF_PLENTY));
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, Client.getInstance().getGame().numberUnplayedDevCards(Client.getInstance().getUserId(), DevCardType.SOLDIER));
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, Client.getInstance().getGame().numberUnplayedDevCards(Client.getInstance().getUserId(), DevCardType.MONOPOLY));
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, Client.getInstance().getGame().numberUnplayedDevCards(Client.getInstance().getUserId(), DevCardType.MONUMENT));
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, Client.getInstance().getGame().numberUnplayedDevCards(Client.getInstance().getUserId(), DevCardType.ROAD_BUILD));

		getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.YEAR_OF_PLENTY));
		getPlayCardView().setCardEnabled(DevCardType.SOLDIER, Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.SOLDIER));
		getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONOPOLY));
		getPlayCardView().setCardEnabled(DevCardType.MONUMENT, Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.MONUMENT));
		getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, Client.getInstance().getGame().canDoPlayerUseDevelopmentCard(Client.getInstance().getUserId(), DevCardType.ROAD_BUILD));
		*/
	}

}


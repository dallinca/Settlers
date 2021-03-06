package client.join;

import shared.communication.results.nonmove.Create_Result;
import shared.communication.results.nonmove.Join_Result;
import shared.communication.results.nonmove.List_Result;
import shared.definitions.CatanColor;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.Client;
import client.ClientException;
import client.ClientFacade;
import client.base.*;
import client.data.*;
import client.misc.*;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, Observer {

	private GameInfo gameInfo; // For storing the GameInfo for the desired game to join
	private Client clientInfo;
	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);
		System.out.println("JoinGameController JoinGameController()");

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		
	}
	
	public IJoinGameView getJoinGameView() {
		System.out.println("JoinGameController getJoinGameView()");
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		System.out.println("JoinGameController getJoinAction()");
		return joinAction;
	}
	
	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		System.out.println("JoinGameController setJoinAction()");
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		System.out.println("JoinGameController getNewGameView()");
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		System.out.println("JoinGameController setNewGameView()");
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		System.out.println("JoinGameController getSelectColorView()");
		
		return selectColorView;
	}
	
	public void setSelectColorView(ISelectColorView selectColorView) {
		System.out.println("JoinGameController setSelectColorView()");
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		System.out.println("JoinGameController getMessageView()");
		
		return messageView;
	}
	
	public void setMessageView(IMessageView messageView) {
		System.out.println("JoinGameController setMessageView()");
		
		this.messageView = messageView;
	}

	/**
	 * Asks the client Facade for the current listing of games
	 * 
	 * @pre None
	 * @post the JoinGameView will be showing the listing of games that was received back from the client Facade
	 * 
	 */
	@Override
	public void start() {
		System.out.println("JoinGameController start()");
		List_Result result = null;
		result = ClientFacade.getInstance().listGames();
		GameInfo[] games = new GameInfo[result.getGames().length];
		games = result.getGames();
		PlayerInfo localPlayer = new PlayerInfo();
		localPlayer.setId(Client.getInstance().getUserId());
		getJoinGameView().setGames(games, localPlayer);
		if(!getJoinGameView().isModalShowing()) {
			getJoinGameView().showModal();
		}
	}

	/**
	 * Makes the create game view visible, and makes the JoinGameHub view not visible
	 * 
	 * @pre None
	 * @post the create game view visible. the JoinGameHub view not visible
	 * 
	 */
	@Override
	public void startCreateNewGame() {
		System.out.println("JoinGameController startCreateNewGame()");
		
		getNewGameView().showModal();
	}

	/**
	 * Makes the create game view not visible, and makes the JoinGameHub view visible, not creating a game
	 * 
	 * @pre None
	 * @post the create game view not visible. the JoinGameHub view visible
	 * 
	 */
	@Override
	public void cancelCreateNewGame() {
		System.out.println("JoinGameController cancelCreateNewGame()");
		
		getNewGameView().closeModal();
	}

	/**
	 * Makes the create game view not visible, and makes the JoinGameHub view visible, creating a game
	 * 
	 * @pre None
	 * @post the create game view not visible. the JoinGameHub view visible. A game will have been created
	 * 
	 */
	@Override
	public void createNewGame() {
		System.out.println("JoinGameController createNewGame()");
		Create_Result create_result = null;
		create_result = ClientFacade.getInstance().createGame(getNewGameView().getTitle(), 
				getNewGameView().getRandomlyPlaceHexes(), 
				getNewGameView().getRandomlyPlaceNumbers(), 
				getNewGameView().getUseRandomPorts());
		// Check to see if the creation of the game failed
		if(create_result.isValid() == false) {
			getNewGameView().closeModal();
			getMessageView().setMessage("Game could not be created");
			getMessageView().showModal();
		}
		// Continue on with the creation of the game
		start();

		getNewGameView().closeModal();
	}

	/**
	 * Brings up the Select Color View with the option to cancel joining the game
	 * 
	 * @pre None
	 * @post the JoinGameHub will not be visible, the color view will be visible
	 * 
	 */
	@Override
	public void startJoinGame(GameInfo game) {
		System.out.println("JoinGameController startJoinGame()");
		this.gameInfo = game; // We momentarily store the game information so that if we choose to attempt joining the game, we have access to the game id
		resetColorSelection();
		
		List<PlayerInfo> players = game.getPlayers();
		for(PlayerInfo player: players) {
			// allow the user to select a different color
			if (player==null){
				
			}
			else if(player.getId() != Client.getInstance().getUserId()) {
				System.out.println(player.getId());
				if(player.getColor() != null) {
					getSelectColorView().setColorEnabled(player.getColor(), false);
				}
			}
		}
		
		getSelectColorView().showModal();
	}
	
	/**
	 * Resets the buttons in the color selection to all-available
	 * 
	 * @pre None
	 * @post Resets the buttons in the color selection to all-available
	 */
	private void resetColorSelection() {
		for(CatanColor color : CatanColor.values()) {
			getSelectColorView().setColorEnabled(color, true);
		}
	}

	/**
	 * Takes you from the Color Picker back to the game hub without adding the player to the game
	 * 
	 * @pre None
	 * @post the color view will not be visible, the JoinGameHub will be visible
	 */
	@Override
	public void cancelJoinGame() {
		System.out.println("JoinGameController cancelJoinGame()");
	
		getJoinGameView().closeModal();
	}

	/**
	 * Officially joins the game with a color
	 * 
	 * @pre None
	 * @post the Color View and JoinGameHub Modals will be closed and the PlayerWait Modal opened
	 * 
	 */
	@Override
	public void joinGame(CatanColor color) {
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		System.out.println("JoinGameController joinGame()");
		Client.getInstance().setGameInfo(gameInfo);
		Join_Result join_result = null;
		join_result = ClientFacade.getInstance().joinGame(gameInfo.getId(), color);

		// If something went wrong with joining the game
		if(join_result == null || join_result.isValid() == false) {
			getSelectColorView().closeModal();
			getMessageView().setMessage("Game could not be joined");
			getMessageView().showModal();
			return;
		}
		// If join succeeded, check if the player already exists in the game
		Client.getInstance().setColor(color);
		boolean playerAlreadyInGame = false;
		for(PlayerInfo player: gameInfo.getPlayers()) {
			// If we find a player in the page who has the same id as the currently logged in user
			if(player.getId() == Client.getInstance().getUserId()) {
				playerAlreadyInGame = true;
				player.setColor(color); // Change the color of the user to the new selection
			}
		}
		if(playerAlreadyInGame == false) {
			PlayerInfo joiningPlayer = new PlayerInfo();
			joiningPlayer.setColor(color);
			joiningPlayer.setId(Client.getInstance().getUserId());
			joiningPlayer.setName(Client.getInstance().getName());
			gameInfo.addPlayer(joiningPlayer);
		}
		joinAction.execute();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("JoinGameController update()");
	}

	public GameInfo getGameInfo() {
		System.out.println("JoinGameController getGameInfo()");
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo) {
		System.out.println("JoinGameController setGameInfo()");
		this.gameInfo = gameInfo;
	}
	
	

}


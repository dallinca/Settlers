package client.join;

import shared.communication.results.nonmove.Join_Result;
import shared.communication.results.nonmove.List_Result;
import shared.definitions.CatanColor;

import java.util.Observable;
import java.util.Observer;

import client.Client;
import client.ClientException;
import client.ClientFacade;
import client.MockClientFacade;
import client.base.*;
import client.data.*;
import client.misc.*;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, Observer {

	private GameInfo game; // For storing the GameInfo for the desired game to join
	
	private MockClientFacade mockClientFacade;
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
								ISelectColorView selectColorView, IMessageView messageView, MockClientFacade mockClientFacade, Client clientInfo) {

		super(view);
		System.out.println("JoinGameController JoinGameController()");

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		this.mockClientFacade = mockClientFacade;
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
	 * @post the JoinGameView will be showing the listing of games that was recieved back from the client Facade
	 * 
	 */
	@Override
	public void start() {
		System.out.println("JoinGameController start()");
		List_Result result = null;
		try {
			result = mockClientFacade.listGames();
			GameInfo[] games = new GameInfo[result.getGames().length];
			games = result.getGames();
			getJoinGameView().setGames(games, new PlayerInfo());
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		getJoinGameView().showModal();
	}

	/**
	 * TODO -Javadoc and Implement
	 * 
	 */
	@Override
	public void startCreateNewGame() {
		System.out.println("JoinGameController startCreateNewGame()");
		
		getNewGameView().showModal();
	}

	/**
	 * TODO -Javadoc and Implement
	 * 
	 */
	@Override
	public void cancelCreateNewGame() {
		System.out.println("JoinGameController cancelCreateNewGame()");
		
		getNewGameView().closeModal();
	}

	/**
	 * TODO -Javadoc and Implement
	 * 
	 */
	@Override
	public void createNewGame() {
		System.out.println("JoinGameController createNewGame()");
		
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
		this.game = game; // We momentarily store the game information so that if we choose to attempt joining the game, we have access to the game id
		
		
		getSelectColorView().showModal();
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
	 * TODO -Javadoc and Implement
	 * 
	 * Officially joins the game with a color
	 * 
	 * @pre None
	 * @post the Color View and JoinGameHub Modals will be closed and the PlayerWait Modal opened
	 * 
	 */
	@Override
	public void joinGame(CatanColor color) {
		System.out.println("JoinGameController joinGame()");
		Join_Result join_result = null;
		try {
			join_result = mockClientFacade.joinGame(game.getId(), color);
		} catch (ClientException e) {
			getSelectColorView().closeModal();
			getMessageView().setMessage("Game could not be joined, threw Exception");
			getMessageView().showModal();
			e.printStackTrace();
		}

		// If something went wrong with joining the game
		if(join_result == null || join_result.isJoined() == false) {
			getSelectColorView().closeModal();
			getMessageView().setMessage("Game could not be joined");
			getMessageView().showModal();
			return;
		}
		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		joinAction.execute();
	}

	/**
	 * TODO -Javadoc and Implement
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("JoinGameController update()");
		// TODO Auto-generated method stub
		
	}

}


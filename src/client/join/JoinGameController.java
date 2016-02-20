package client.join;

import shared.communication.results.nonmove.List_Result;
import shared.definitions.CatanColor;

import java.util.Observable;
import java.util.Observer;

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
								ISelectColorView selectColorView, IMessageView messageView, MockClientFacade mockClientFacade) {

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

	@Override
	public void startCreateNewGame() {
		System.out.println("JoinGameController startCreateNewGame()");
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		System.out.println("JoinGameController cancelCreateNewGame()");
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		System.out.println("JoinGameController createNewGame()");
		
		getNewGameView().closeModal();
	}

	@Override
	public void startJoinGame(GameInfo game) {
		System.out.println("JoinGameController startJoinGame()");

		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
		System.out.println("JoinGameController cancelJoinGame()");
	
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		System.out.println("JoinGameController joinGame()");
		
		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		joinAction.execute();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("JoinGameController update()");
		// TODO Auto-generated method stub
		
	}

}


package client.communication;

import java.util.*;
import client.Client;
import client.base.*;
import shared.definitions.*;
import shared.model.Game;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		System.out.println("GameHistoryController GameHistoryController()");
		//Client.getInstance().addObserver(this);
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		System.out.println("GameHistoryController getView()");
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		if (Client.getInstance().getGame() != null) {
			Game.Line[] history = Client.getInstance().getGame().getHistory();
				
			for (int i = 0; i < history.length; i++) {  
				//Get color or user, if user than we can call the color method in the client or something...?
				 String user = history[i].getSource();
				 String action = history[i].getMessage(); 
							 
				 CatanColor color = null;
							 
				 //Now we have to acertain the color of the player based on the sender:
				 for (int g = 0; g < Client.getInstance().getGame().getAllPlayers().length; g++) {
					 if (Client.getInstance().getGame().getAllPlayers()[g].getPlayerName().equals(user)) {
						 color = Client.getInstance().getGame().getAllPlayers()[g].getPlayerColor();
					 }
				 }
							 
				// String message = user + " " + action;
				 LogEntry entry = new LogEntry(color, action);
				 //Then add it to our list we will set in the view:
				 entries.add(entry); 
			}
		}
		
		if (entries != null) {
			getView().setEntries(entries);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("GameHistoryController initFromModel()");
		// If the game is null just return
		if(Client.getInstance().getGame() == null) {
			return;
		}
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		
		Game.Line[] history = Client.getInstance().getGame().getHistory();
		
		for (int i = 0; i < history.length; i++) {  
				//Get color or user, if user than we can call the color method in the client or something...?
				 String user = history[i].getSource();
				 String action = history[i].getMessage(); 
				 
				 CatanColor color = null;
				 
				 //Now we have to acertain the color of the player based on the sender:
				 for (int g = 0; g < Client.getInstance().getGame().getAllPlayers().length; g++) {
					 if (Client.getInstance().getGame().getAllPlayers()[g].getPlayerName().equals(user)) {
						 color = Client.getInstance().getGame().getAllPlayers()[g].getPlayerColor();
					 }
				 }
				 
				 String message = /*user +*/ " " + action;
				 LogEntry entry = new LogEntry(color, message);
				 
				 //Then add it to our list we will set in the view:
				 entries.add(entry); 
				
		}
		if (entries != null) {
			getView().setEntries(entries);
		}
	}
	
}


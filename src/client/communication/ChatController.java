package client.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import shared.definitions.CatanColor;
import shared.model.Game;

import client.Client;
import client.ClientException;
import client.ClientFacade;
import client.base.*;

/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {

	//private PlaceholderTextField messageBox;
	
	public ChatController(IChatView view) {
		
		super(view);
		System.out.println("ChatController ChatController()");
		//messageBox = new PlaceholderTextField();
	}
	
	@Override
	public IChatView getView() {
		System.out.println("ChatController getView()");
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		System.out.println("ChatController sendMessage()");
		//We need to format message into a SendChat_Params object, which probably requires who sent it.
		//We need to tell the Observable that a message has been sent
		//We call the client to call the clients facade. When that happens the Client also notifies all the other observers that an update occurred
			
		//testing the GUI
		
		//Put this in the update function, it works!!! Hooray! but we need to iterate through all the messages and write them all, so put it in a for loop
		List<LogEntry> entries = new ArrayList<LogEntry>();
		int myId = Client.getInstance().getUserId();
		System.out.println(myId);
		int index = -1;
		
		for (int i = 0; i < Client.getInstance().getGame().getAllPlayers().length; i++) {
			if (myId == Client.getInstance().getGame().getAllPlayers()[i].getPlayerId()) {
				index = i;
			}
		}
		
		CatanColor color = Client.getInstance().getGame().getAllPlayers()[index].getPlayerColor();
		LogEntry entry = new LogEntry(color, message); 
		entries.add(entry);
		this.getView().setEntries(entries);
		
		
		/*String bip = "duelOfTheFates.mp3";
		Media hit = new Media(bip);
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		*/
		
			ClientFacade.getInstance().sendChat(message);
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// So we know in the console which method was called:
		System.out.println("ChatController update()");
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		
		//We need  a list of messages, and who they came from. Who they came from is important so we can ascertain the color of the player.
		Game.Line[] chat = Client.getInstance().getGame().getChat();
		 
		 //Client.getInstance().getGame().getMessages();
		 
		 for (int i = 0; i < chat.length; i++) {
		  
			 
			 String message = chat[i].getMessage();
			 String source = chat[i].getSource();
			 CatanColor color = null;
			 
			 //Now we have to acertain the color of the player based on the sender:
			 for (int g = 0; g < Client.getInstance().getGame().getAllPlayers().length; g++) {
				 if (Client.getInstance().getGame().getAllPlayers()[g].getPlayerName() == source) {
					 color = Client.getInstance().getGame().getAllPlayers()[g].getPlayerColor();
				 }
			 }
			 //Compile the two together and send them off to the client!
			  
			 //edit: I had the message and color reversed in the constructor. Whoops.
			 LogEntry entry = new LogEntry(color, message);
			 
			 //Then add it to our list we will set in the view:
			 entries.add(entry);
			  
		 }	
		/* We need to send the messages off to the ChatView. To do so we need to previously have compiled who said what and who they were and what they said, then we should in that above for loop
		 * add them to a list that we can send to setEntries that needs to be of type: "final List<LogEntry> entries" as stated in ChatView.java 
		*/ 
		this.getView().setEntries(entries);
		 
		 	}

}
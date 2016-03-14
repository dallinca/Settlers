package client.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import shared.definitions.CatanColor;
import shared.model.Game;
import shared.model.player.*;
import client.Client;
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
		Client.getInstance().addObserver(this);
	}
	
	/**
	 * Gets the view, pretty self-explanatory
	 */
	@Override
	public IChatView getView() {
		System.out.println("ChatController getView()");
		return (IChatView)super.getView();
	}

	/**
	 * This method takes in the message you write in the box and sends it off to the ClientFacade which handles it. It is triggered by either clicking the 'send' button or pressing the enter key
	 * 
	 * @pre message exists
	 * 
	 * I did no checking for sequel injection, cross scripting or any type of bad input, so this is assuming that the message is just a message. It will probably change depending on the funcitonality of the server we create.
	 */
	@Override
	public void sendMessage(String message) {
		System.out.println("ChatController sendMessage()");
		//We need to format message into a SendChat_Params object, which probably requires who sent it. - The Client Facade now does that, just send it the message that was written.
		//We need to tell the Observable that a message has been sent
		//The following call will trigger an update which then will display to all users the message that was sent.
		
		ClientFacade.getInstance().sendChat(message);
	}

	/**
	 * This method is called every time the game changes, or as the Observable deems fit.
	 * Whenever update is called it checks to see what messages the new game holds and displays them.
	 * @pre The game has changed and the Client as well
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("ChatController update()");
		//Note to self: This Class in of itself represents how I would want my life to be: I don't really care what other people are doing, I don't really want it to affect me, but in the event that somebody changes, I check my personal affairs. This method does the same thing. 
		
		// If the game is null just return
		if(Client.getInstance().getGame() == null) {
			return;
		}
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		
		//We need  a list of messages, and who they came from. Who they came from is important so we can ascertain the color of the player.
		Game.Line[] chat = Client.getInstance().getGame().getChat();
		
		//For testing purposes:
		System.out.println("Chat messages-------------------------------");
		for (int j = 0; j < chat.length; j++){
			System.out.println("Chat message: "+chat[j].getMessage() + "\n");
			System.out.println("Chat source: " +chat[j].getSource() + "\n");
		}
		
		//Now we read through the list of messages that the game has
		 for (int i = 0; i < chat.length; i++) {
			 String message = chat[i].getMessage();
			 String source = chat[i].getSource();
			 CatanColor color = null;
			 
			Player[] players =  Client.getInstance().getGame().getAllPlayers();
			 
			 //Now we have to acertain the color of the player based on the sender:
			 for (int g = 0; g < players.length; g++) {
				 if (players[g].getPlayerName().equals(source)) {
					 color = players[g].getPlayerColor();
					 System.out.println(color);
				 }
			 }
			 
			 //Compile the two together and send them off to the client!
			 LogEntry entry = new LogEntry(color, message);
			 
			 //Then add it to our list we will set in the view:
			 entries.add(entry);
			  
		 }
		 
		//We need to send the messages off to the ChatView. To do so we need to previously have compiled who said what and who they were and what they said, then we should in that above for loop
		//add them to a list that we can send to setEntries that needs to be of type: "final List<LogEntry> entries" as stated in ChatView.java. -- Done  
		this.getView().setEntries(entries);
		 
	}

}
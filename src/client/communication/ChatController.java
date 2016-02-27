package client.communication;

import java.awt.event.ActionEvent;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import shared.communication.params.move.SendChat_Params;
import shared.communication.results.move.SendChat_Result;
import shared.definitions.CatanColor;

import client.Client;
import client.ClientFacade;
import client.base.*;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;


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
		LogEntry entry = new LogEntry(CatanColor.BLUE, message); 
		entries.add(entry);
		this.getView().setEntries(entries);
		
		
		/*String bip = "duelOfTheFates.mp3";
		Media hit = new Media(bip);
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
				
		/*try {
			SendChat_Result result = ClientFacade.sendChat(message);
		} catch (ServerException e) {
			
			e.printStackTrace();
		}*/
		
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// So we know in the console which method was called:
		System.out.println("ChatController update()");
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		//We need  a list of messages, and who they came from. Who they came from is important so we can ascertain the color of the player.
		 
		 //These next two will actually have a definition they acquire from the client, not a brand new list, for obvious reasons
		 List<String> listOfMessages = new ArrayList<String>();
		 List<String> listOfSenders = new ArrayList<String>();
		 for (int i = 0; i < listOfMessages.size(); i++) {
		  
		 String message = listOfMessages.get(i);
		 String sender = listOfSenders.get(i);
		  
		 //Now we have to acertain the color of the player based on the sender:
		 //CatanColor color = "Call a method in the client that takes in the sender and returns the color they are"; 
		  
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
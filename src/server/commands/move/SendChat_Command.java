package server.commands.move;

import server.commands.Command;
import shared.communication.params.move.SendChat_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.SendChat_Result;
import shared.model.Game;
import shared.model.Game.Line;

/**
 * Concrete command implementing the Command interface.
 * Issues the Send Chat action on the server facade.
 * 
 * @author Dallin
 *
 */
public class SendChat_Command implements Command {
	
	private SendChat_Params params;
	private SendChat_Result result;
	private int gameID, userID;
	
	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public SendChat_Command() {}
	
	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public SendChat_Command(SendChat_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the send chat action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Roll Number action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("SendChat_Command");
		Game game = null;
		game = facade.canDoSendChat(params, gameID, userID);
		result = new SendChat_Result();

		System.out.println("SendChat_Command1");
		if (game==null){
			System.out.println("SendChat_Command2");
			return;
		}

		System.out.println("SendChat_Command3");
		
		Line message = game.new Line(); 
		message.setMessage(params.getContent());
		message.setSource(game.getAllPlayers()[params.getPlayerIndex()].getPlayerName());
		
		Line[] chat = game.getChat();
		Line[] newChat;
		 
		int length = 1;
		//initial chat
		if(chat != null){
				length = chat.length+1;
			 
				
				
				 
			newChat = new Line[length];
			
			for (int i =0;i<chat.length;i++){
				newChat[i]=chat[i];	
			}
			newChat[newChat.length-1]=message;
		}else{
			newChat = new Line[length];
			newChat[0] = message;
		}
		
		game.setChat(newChat);
		result.setValid(true);

		System.out.println("SendChat_Command4");
		
		game.setVersionNumber(game.getVersionNumber()+1);
		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		System.out.println("SendChat_Command5");
		result.setModel(cm);
		facade.storeCommand(gameID, this);
	}
	
	public SendChat_Result getResult(){
		return result;
	}
}
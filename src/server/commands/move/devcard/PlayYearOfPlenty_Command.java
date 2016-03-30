package server.commands.move.devcard;

import server.commands.Command;
import shared.communication.params.move.devcard.PlayYearOfPlenty_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.devcard.PlayYearOfPlenty_Result;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Year of Plenty Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayYearOfPlenty_Command implements Command {
	private PlayYearOfPlenty_Params params;
	private PlayYearOfPlenty_Result result; 
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayYearOfPlenty_Command() {}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public PlayYearOfPlenty_Command(PlayYearOfPlenty_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Play Year Of Plenty Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Year Of Plenty Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;
		
		System.out.println("PlayYearOfPlenty_command beginning");
		
		String r1 = params.getResource1();
		String r2 = params.getResource2();

		ResourceType[] resourceType = new ResourceType[2];

		try {
			System.out.println("PlayYearOfPlenty_command evaluating resource types");
			if (r1.equals(r2)) {
				resourceType[0] = formatForArray(r1);
			} else {
				resourceType[0] = formatForArray(r1);
				resourceType[1] = formatForArray(r2);
			}		

		} catch (Exception e) {
			System.out.println("Invalid resource type");
			e.printStackTrace();
		}

		
	game = facade.canDoPlayYearOfPlenty(resourceType, gameID, userID);
	result = new PlayYearOfPlenty_Result();
	System.out.println("PlayYearOfPlenty_command got game");
	
	if (game != null) {
		try {
			game.useDevelopmentCard(userID, DevCardType.YEAR_OF_PLENTY, resourceType);
			
			Game.Line[] history = game.getHistory();
			Game.Line[] newHistory = new Game.Line[history.length+1];
			
			for (int i = 0; i < history.length; i++) {
				newHistory[i] = history[i];
			}
			
			//Just a round-about way to create an object of type Game.Line without too much difficulty
			Game.Line newEntry = history[history.length-1];
			newEntry.setMessage(game.getPlayerByID(userID).getPlayerName() + " played a year of plenty card.");
			newEntry.setSource(game.getPlayerByID(userID).getPlayerName());
			newHistory[history.length] = newEntry;
			
			game.setHistory(newHistory);
			
			System.out.println("PlayYearOfPlenty_command just operated on the game");
		} catch (Exception e) {
			new PlayYearOfPlenty_Result();
			e.printStackTrace();
			return;
		}
	} else {
		return;
	}

	result.setValid(true);	

	game.setVersionNumber(game.getVersionNumber()+1);

	JsonConverter converter = new JsonConverter();
	ClientModel cm = converter.toClientModel(game);

	result.setModel(cm);
	System.out.println("PlayYearOfPlenty_command end of execute");

}

/**
 * This method just converts strings from the params into ResourceType objects
 * @param type
 * @return
 * @throws Exception 
 */
private ResourceType formatForArray(String type) throws Exception {
	ResourceType resourceType = null;

	if (type.equals(ResourceType.BRICK.toString().toLowerCase())) {
		resourceType = ResourceType.BRICK;
	} else if (type.equals(ResourceType.WOOD.toString().toLowerCase())) {
		resourceType = ResourceType.WOOD;
	} else if (type.equals(ResourceType.WHEAT.toString().toLowerCase())) {
		resourceType = ResourceType.WHEAT;
	} else if (type.equals(ResourceType.ORE.toString().toLowerCase())) {
		resourceType = ResourceType.ORE;
	} else if (type.equals(ResourceType.SHEEP.toString().toLowerCase())) {
		resourceType = ResourceType.SHEEP;
	} else {
		throw new Exception("Invalid Resource Type!");
	}
	return resourceType;
}

public PlayYearOfPlenty_Result getResult(){
	return result;
}
}

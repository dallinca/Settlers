package server.commands.move;

import server.commands.Command;
import server.facade.IServerFacade;
import shared.communication.params.move.BuildRoad_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.BuildRoad_Result;
import shared.model.Game;
import shared.model.player.Player;

/**
 * Concrete command implementing the Command interface.
 * Issues the Build Road action on the server facade.
 * 
 * @author Dallin
 *
 */
public class BuildRoad_Command implements Command {
	//private IServerFacade facade;

	private BuildRoad_Params params;
	private BuildRoad_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public BuildRoad_Command() {}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	/*public BuildRoad_Command(IServerFacade facade) {
		this.facade = facade;
	}*/

	public BuildRoad_Command(BuildRoad_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Build Road action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Build Road action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		System.out.println("BuildRoad_Command");
		Game game = null;
		
		System.out.println("Can do build road.");
		game = facade.canDoBuildRoad(params, gameID, userID);
		System.out.println("Can do build road.2");
		result = new BuildRoad_Result();

		System.out.println("BuildRoad_Command1");
		if (game != null) {
			System.out.println("BuildRoad_Command2");
			try {
				System.out.println("Placing road on edge.");
				
				game.placeRoadOnEdge(userID, params.getCmdEdgeLocation() );
								
				Player longest = game.getLongestRoad();
				Player p = game.getPlayerByID(userID);					
				int playedRoads = 15 - p.getNumberUnplayedRoads();
				
				if (longest==null){					
					if (playedRoads>4){
						game.setLongestRoad(p);
						p.setHasLongestRoad(true);
						p.setTotalVictoryPoints(p.getTotalVictoryPoints() + 2);
					}
				}
				// Verify that the builder now has the most roads, and that he doesn't already own the longest road.
				else if (playedRoads > (15 - longest.getNumberUnplayedRoads()) && longest.getPlayerId() != p.getPlayerId()){
					game.setLongestRoad(p);
					p.setTotalVictoryPoints(p.getTotalVictoryPoints() + 2);
					p.setHasLongestRoad(true);
					longest.setTotalVictoryPoints(longest.getTotalVictoryPoints() - 2);
					longest.setHasLongestRoad(false);
				}
				game.getLongestRoad();
				
				
				
			} catch (Exception e) {
				new BuildRoad_Result();
				e.printStackTrace();
				return;
			}
		} else {
			return;
		}
		System.out.println("Valid command.");
		result.setValid(true);

		System.out.println("BuildRoad_Command4");
		
		game.setVersionNumber(game.getVersionNumber()+1);

		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		System.out.println("Setting model.");
		result.setModel(cm);
	}

	public BuildRoad_Result getResult(){
		return result;
	}
}
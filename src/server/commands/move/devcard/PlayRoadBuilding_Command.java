package server.commands.move.devcard;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import server.commands.Command;
import shared.communication.params.move.devcard.PlayRoadBuilding_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.move.devcard.PlayRoadBuilding_Result;
import shared.locations.HexLocation;
import shared.model.Game;

/**
 * Concrete command implementing the Command interface.
 * Issues the Play Road Building Dev Card action on the server facade.
 * 
 * @author Dallin
 *
 */
public class PlayRoadBuilding_Command implements Command {
	private PlayRoadBuilding_Params params;
	private PlayRoadBuilding_Result result;
	private int gameID, userID;

	/**
	 * Non-standard command pattern constructor instantiation without the facade.
	 * The facade will be determined after original command instantiation.
	 * 
	 */
	public PlayRoadBuilding_Command() {}

	/**
	 * Standard Command pattern constructor instantiation with the facade
	 * 
	 * @param game
	 */
	public PlayRoadBuilding_Command(PlayRoadBuilding_Params params, int gameID, int userID) {
		this.params = params;
		this.gameID = gameID;
		this.userID = userID;
	}

	/**
	 * Issues the Play Road Building Dev Card action on the given game server game model.
	 * Should only be triggered by the games models Command History class.
	 * 
	 * @pre game != null
	 * @pre game given is the correct game for this command
	 * @pre command is a valid action on the game model
	 * 
	 * @post the Play Road Building Dev Card action will be correctly implemented on the game model
	 */
	@Override
	public void execute() {
		Game game = null;

		//We have to build EdgeLocation objects from the params and then send them off to the facade.
		//To build them we need to ascertain their hex locations and directions. 
		//This gets lengthy because the Direction is in string format and must be compared to the enums. 
		
		System.out.println("PlayRoadBuilding_Command beginning");
		HexLocation hex1 = new HexLocation(params.getSpot1().getX(), params.getSpot1().getY());
		HexLocation hex2 = new HexLocation(params.getSpot2().getX(), params.getSpot2().getY()); 

		//call the get direction method:
		EdgeDirection dir1 = getDirection(params.getSpot1().getDirection());
		EdgeDirection dir2 =  getDirection(params.getSpot1().getDirection());

		EdgeLocation edge1 = new EdgeLocation(hex1, dir1);
		EdgeLocation edge2 = new EdgeLocation(hex2, dir2);
		
		System.out.println("PlayRoadBuilding_Command created edges and hexloc, etc.");
		if (edge1 != null && edge2 != null) {
			game = facade.canDoPlayRoadBuilding(params, edge1, edge2, gameID, userID);
			result = new PlayRoadBuilding_Result();
			System.out.println("PlayRoadBuilding_Command operated on the game");

			if (game != null) {
				try {
					game.placeRoadOnEdge(userID, edge1, true);
					System.out.println("PlayRoadBuilding_Command placed one road");
					game.placeRoadOnEdge(userID, edge2, true);
					System.out.println("PlayRoadBuilding_Command placed second road");
				} catch (Exception e) {
					new PlayRoadBuilding_Result();
					e.printStackTrace();
					return;
				} 
			} else {
				return;
			}
		} else 
			return;

		result.setValid(true);

		game.setVersionNumber(game.getVersionNumber()+1);
		
		JsonConverter converter = new JsonConverter();
		ClientModel cm = converter.toClientModel(game);

		result.setModel(cm);
		System.out.println("PlayRoadBuilding_Command end of execute");

	}

	/**
	 * This method calculates which EdgeLocation the string passed in corresponds to.
	 * @return
	 */
	public EdgeDirection getDirection(String dir) {
		//Possible values: NorthWest, North, NorthEast, SouthEast, South, SouthWest
		EdgeDirection edgeDirection;

		if (dir.equals(EdgeDirection.North.toString().toLowerCase())) {
			edgeDirection = EdgeDirection.North;
		} else if (dir.equals(EdgeDirection.NorthEast.toString().toLowerCase())) {
			edgeDirection = EdgeDirection.NorthEast;
		} else if (dir.equals(EdgeDirection.NorthWest.toString().toLowerCase())) {
			edgeDirection = EdgeDirection.NorthWest;
		} else if (dir.equals(EdgeDirection.South.toString().toLowerCase())) {
			edgeDirection = EdgeDirection.South;
		} else if (dir.equals(EdgeDirection.SouthEast.toString().toLowerCase())) {
			edgeDirection = EdgeDirection.SouthEast;
		} else if (dir.equals(EdgeDirection.SouthWest.toString().toLowerCase())) {
			edgeDirection = EdgeDirection.SouthWest;
		} else {
			edgeDirection = null;
		}
		return edgeDirection;
	}

	public PlayRoadBuilding_Result getResult() {
		return result;
	}

}

package shared.communication.results.nonmove;

import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import shared.model.Game;

public class GetVersion_Result {

	Game currentVersion;	
	boolean valid;
	boolean upToDate;

	public GetVersion_Result(){
		currentVersion = null;
	}

	GetVersion_Result(Game current){
		currentVersion = current;

	}

	public GetVersion_Result(String post) {

		if (post.equals("\"true\"")){
			upToDate = true;
			valid = true;

		}
		else{
			parseJson(post);		
		}
	}


	public Game getGame() {
		return currentVersion;
	}

	/**
	 * @TODO
	 * @param post
	 */

	private void parseJson(String post){

		Gson gson = new Gson();



		JsonObject clientModel;
		JsonObject bank;
		JsonObject chat;
		JsonObject log;
		JsonObject map;
		JsonArray players;
		JsonObject tradeOffer = null;
		JsonObject turnTracker;
		JsonObject version;
		JsonObject winner;		

		clientModel = gson.fromJson(post, JsonObject.class);

		bank = clientModel.getAsJsonObject("bank");
		chat = clientModel.getAsJsonObject("chat");
		log = clientModel.getAsJsonObject("log");
		map = clientModel.getAsJsonObject("map");		
		players = clientModel.getAsJsonArray("players");
		if (clientModel.has("tradeOffer"))tradeOffer = clientModel.getAsJsonObject("tradeOffer");
		turnTracker = clientModel.getAsJsonObject("turnTracker");
		version = clientModel.getAsJsonObject("version");
		winner = clientModel.getAsJsonObject("winner");


		JsonObject ResourceList = bank.getAsJsonObject("ResourceList");


		JsonObject MessageList = chat.getAsJsonObject("MessageList");		
	}




	

	/*Player {
			cities (number): How many cities this player has left to play,
			color (string): The color of this player.,
			discarded (boolean): Whether this player has discarded or not already this discard phase.,
			monuments (number): How many monuments this player has played.,
			name (string),
			newDevCards (DevCardList): The dev cards the player bought this turn.,
			oldDevCards (DevCardList): The dev cards the player had when the turn started.,
			playerIndex (index): What place in the array is this player? 0-3. It determines their turn order. 
					This is used often everywhere.,
			playedDevCard (boolean): Whether the player has played a dev card this turn.,
			playerID (integer): The unique playerID. This is used to pick the client player apart from the 
					others. This is only used here and in your cookie.,
			resources (ResourceList): The resource cards this player has.,
			roads (number),
			settlements (integer),
			soldiers (integer),
			victoryPoints (integer)
		}*/

	/*
		DevCardList {
			monopoly (number),
			monument (number),
			roadBuilding (number),
			soldier (number),
			yearOfPlenty (number)
		}
	 */

	/*
		TradeOffer {
			sender (integer): The index of the person offering the trade,
			receiver (integer): The index of the person the trade was offered to.,
			offer (ResourceList): Positive numbers are resources being offered. Negative are resources 
									being asked for.
		}*/


	/*
		TurnTracker {
			currentTurn (index): Who's turn it is (0-3),
			status (string) = ['Rolling' or 'Robbing' or 'Playing' or 'Discarding' or 'FirstRound' or 
							     'SecondRound']: What's happening now,
			longestRoad (index): The index of who has the longest road, -1 if no one has it largestArmy
			 (index): The index of who has the biggest army (3 or more), -1 if no one has it
		}*/

	private class ClientModel{

		/*
		ClientModel {
			bank (ResourceList): The cards available to be distributed to the players.,
			chat (MessageList): All the chat messages.,
			log (MessageList): All the log messages.,
			map (Map),
			players (array[Player]),
			tradeOffer(	TradeOffer, optional): The current trade offer, if there is one.,
			turnTracker (TurnTracker): This tracks who's turn it is and what action's being done.,
			version (index): The version of the model. This is incremented whenever anyone makes a move.,	
			winner (index): This is -1 when nobody's won yet. When they have, it's their order index [0-3]			
		}
		 */


		private class Bank{
			/*
		ResourceList {
			brick (integer),
			ore (integer),
			sheep (integer),
			wheat (integer),
			wood (integer)
		}
			 */
			public class ResourceList{
				int brick;
				int ore;
				int sheep;
				int wheat;
				int wood;
			}
		}	

		private class Chat{
			/*
		MessageList {
			lines (array[MessageLine])
		}

		MessageLine {message (string),
		source (string)
		}
			 */
			public class MessageList{
				LinkedList<MessageLine> lines;

				public class MessageLine{
					String message;
					String source;				
				}
			}
		}

		private class Log{

		}

		private class Map{
			LinkedList<Hex> hexes;
			LinkedList<Port> ports;
			LinkedList<Road> roads;
			LinkedList<Settlement> settlements;
			LinkedList<City> cities;
			int radius;
			Hex.HexLocation robber;
			/*
			Map {
			hexes (array[Hex]): A list of all the hexes on the grid - it's only land tiles,
			ports (array[Port]),
			roads (array[Road]),
			settlements (array[VertexObject]),
			cities (array[VertexObject]),
			radius (integer): The radius of the map (it includes the center hex, and the ocean hexes; pass 
				this into the hexgrid constructor),
			robber (HexLocation): The current location of the robber
		}
			 */	
			
			public class Settlement{
				int owner;
				int x;
				int y;
				String direction;
			}
			
			public class City{
				int owner;
				int x;
				int y;
				String direction;
			}
			
			public class Road{
				
				int owner;
				int x;
				int y;
				String direction;				

				/*EdgeValue {
						owner (index): The index (not id) of the player who owns this piece (0-3),
							location (EdgeLocation): The location of this road.
					}*/
				/*EdgeLocation {
						x (integer),
						y (integer),
						direction (string) = ['NW' or 'N' or 'NE' or 'SW' or 'S' or 'SE']
					}*/
				
			}
			public class Port{
				String resource;
				Hex.HexLocation location;				
				String direction;
				int ratio;

				/*Port {
				resource(string, optional) = ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']: What type 
						resource this port trades for. If it's omitted, then it's for any resource.,
				location (HexLocation): Which hex this port is on. This shows the (ocean/non-existent) hex to 
						draw the port on.,
				direction (string) = ['NW' or 'N' or 'NE' or 'E' or 'SE' or 'SW']: Which edge this port is on.,
				ratio (integer): The ratio for trade in (ie, if this is 2, then it's a 2:1 port.
			}*/
			}

			public class Hex{
				HexLocation location;
				Resource resource;

				/*
			Hex {
				location (HexLocation),
				resource (string, optional) = ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']: What resource 
					this tile gives - it's only here if the tile is not desert., 
				number(integer, optional): What number is on this tile. It's omitted if this is a desert hex.
			}*/

				public class Resource{
					String type;				
				}

				public class Number{
					int tileNumber;				
				}			

				public class HexLocation{
					int x;
					int y;

					/*HexLocation {
					x (integer),
					y (integer)
				}*/
				}
			}


		}

		private class Player{

		}

		private class TradeOffer{

		}

		private class TurnTracker{

		}

		private class Version{

		}

		private class Winner{

		}
	}
}

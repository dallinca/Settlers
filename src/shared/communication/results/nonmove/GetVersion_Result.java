package shared.communication.results.nonmove;

import java.util.Arrays;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import shared.model.Game;

public class GetVersion_Result {

	private Game currentVersion;	
	private boolean valid;
	private boolean upToDate;
	private ClientModel model;

	public GetVersion_Result(){
		currentVersion = null;
	}

	GetVersion_Result(Game current){
		currentVersion = current;

	}

	public GetVersion_Result(String post) {

		if (post==null){
			valid = false;
		}
		else if (post.equals("\"true\"")){
			setUpToDate(true);
			valid = true;
			
		}
		else{
			//model = new ClientModel();
			parseJson(post);		
		}
	}

	public boolean isValid() {		
		return valid;
	}

	public Game getGame() {
		return currentVersion;
	}

	/**
	 * @TODO
	 * @param post
	 */

	private void parseJson(String post){
		System.out.println("Entering gauntlet.");
		Gson gson = new Gson();

		model = gson.fromJson(post, ClientModel.class);

		valid = true;
		upToDate = false;

		System.out.println("Gauntlet passed.");

		/*
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

		//Read all bank stuff in.
		JsonObject rList = bank.getAsJsonObject("ResourceList");
		model.bank.resourceList.brick = rList.get("brick").getAsInt();
		model.bank.resourceList.ore = rList.get("ore").getAsInt();
		model.bank.resourceList.sheep = rList.get("sheep").getAsInt();
		model.bank.resourceList.wheat = rList.get("wheat").getAsInt();
		model.bank.resourceList.wood = rList.get("wood").getAsInt();

		JsonArray messages =  chat.getAsJsonObject("MessageList").get("MessageLine").getAsJsonArray();

		//Read all messages in.
		for (JsonElement m : messages){		

			ClientModel.Chat.MessageList.MessageLine msg = model.chat.list.new MessageLine();
			msg.message = m.getAsJsonObject().get("message").getAsString();
			msg.source = m.getAsJsonObject().get("source").getAsString();
			model.chat.list.lines.add(msg); //Add to model.
		}

		JsonArray logs =  log.getAsJsonObject("MessageList").get("MessageLine").getAsJsonArray();

		//Read all logs in.
		for (JsonElement l : logs){	

			ClientModel.Chat.MessageList.MessageLine msg = model.chat.list.new MessageLine();
			msg.message = l.getAsJsonObject().get("message").getAsString();
			msg.source = l.getAsJsonObject().get("source").getAsString();
			model.log.list.lines.add(msg); //Add to model.				
		}

		//Build map.

		JsonArray hexes = map.getAsJsonObject("hexes").getAsJsonArray();

		for (JsonElement h : hexes){

			ClientModel.Map.Hex hex = model.map.new Hex();
			hex.location.x = h.getAsJsonObject().get("location").get("x").getAsInt();
			hex.location.y = h.getAsJsonObject().get("location").get("y").getAsInt();
			hex.resource.type = h.getAsJsonObject().

		}*/
	}


	public boolean isUpToDate() {
		return upToDate;
	}

	public void setUpToDate(boolean upToDate) {
		this.upToDate = upToDate;
	}


	private class ClientModel{

		Bank bank;
		Chat chat;
		Log log;
		Map map;
		Player[] players;
		TradeOffer tradeOffer;
		TurnTracker turnTracker;
		int version;
		int winner;

		/*public ClientModel(){

			bank = new Bank();
			chat = new Chat();
			log = new Log();
			map = new Map();
			tradeOffer = new TradeOffer();
			turnTracker = new TurnTracker(); 
		}

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

		public class Bank{
			ResourceList resourceList;

			/*public Bank(ResourceList resourceList){
				this.resourceList = resourceList;
			}

			public Bank(){
				resourceList = new ResourceList();
			}*/
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

				/*	public ResourceList(){

				}
				public ResourceList(int brick, int ore, int sheep,
						int wheat, int wood) {
					super();
					this.brick = brick;
					this.ore = ore;
					this.sheep = sheep;
					this.wheat = wheat;
					this.wood = wood;
				}				
			}*/
			}	
		}

		private class Chat{
			MessageList list;

			/*public Chat(){
				list = new MessageList();
			}*/
			/*
	MessageList {
		lines (array[MessageLine])
	}

	MessageLine {message (string),
	source (string)
	}
			 */
			public class MessageList{
				MessageLine[] lines;

				/*public MessageList(){
					lines = new LinkedList<MessageLine>();
				}

				public MessageList(MessageLine[] messages){

					lines = new LinkedList<MessageLine>();
					lines.addAll(Arrays.asList(messages));
				}*/
			}

			public class MessageLine{
				String message;
				String source;			
				/*public MessageLine(){						
				}
				public MessageLine(String message, String source){
					this.message = message;
					this.source = source;

				}*/
			}
		}

		private class Log{
			ClientModel.Chat.MessageList list;
		}

		private class Map{
			Hex[] hexes;
			Port[] ports;
			EdgeValue[] roads;
			VertexObject[] settlements;
			VertexObject[] cities;
			int radius;
			HexLocation robber;
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
			/*	public Map(Hex[] hexes, Port[] ports, Road[] roads, 
					VertextObject[] settlements, VertextObject[] cities
					int radius, Hex.HexLocation robber){
				this.hexes = Arrays.asList(hexes);


			}*/

			/*private class Settlement{
				int owner;
				int x;
				int y;
				String direction;
			}

			private class City{
				int owner;
				int x;
				int y;
				String direction;
			}*/

			private class VertexObject{
				int owner;
				VertexLocation location;
			}

			/*private class Road{
				int owner;
				int x;
				int y;
				String direction;
			}*/
			private class VertexLocation{
				int x;
				int y;
				String direction;				
			}

			private class EdgeValue{
				int owner;
				EdgeLocation location;
				/*EdgeValue {
				owner (index): The index (not id) of the player who owns this piece (0-3),
					location (EdgeLocation): The location of this road.
			}*/
			}

			private class EdgeLocation{
				int x;
				int y;
				String direction;

				/*EdgeLocation {
						x (integer),
						y (integer),
						direction (string) = ['NW' or 'N' or 'NE' or 'SW' or 'S' or 'SE']
					}*/
			}

			private class Port{
				String resource;
				HexLocation location;				
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

			private class Hex{
				HexLocation location;
				String resource;
				int number;
				/*
				Hex {
				location (HexLocation),
				resource (string, optional) = ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']: What resource 
					this tile gives - it's only here if the tile is not desert., 
				number(integer, optional): What number is on this tile. It's omitted if this is a desert hex.
				}*/
			}
			private class HexLocation{
				int x;
				int y;
				/*HexLocation {
				x (integer),
				y (integer)
				}*/
			}
		}

		private class Player{
			int cities;
			String color;
			boolean discarded;
			int monuments;
			String name;
			DevCardList newDevCards;
			DevCardList oldDevCards;
			int playerIndex;
			boolean playedDevCard;
			int playerID;
			Bank.ResourceList resources;
			int roads;
			int settlements;
			int soldiers;
			int victoryPoints;

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
			private class DevCardList{

				int monopoly;
				int monument;
				int roadBuilding;
				int soldier;
				int yearOfPlenty;				

				/*
				DevCardList {
					monopoly (number),
					monument (number),
					roadBuilding (number),
					soldier (number),
					yearOfPlenty (number)
				}
				 */
			}
		}

		private class TradeOffer{
			int sender;
			int receiver;
			Bank.ResourceList offer;

			/*
			TradeOffer {
				sender (integer): The index of the person offering the trade,
				receiver (integer): The index of the person the trade was offered to.,
				offer (ResourceList): Positive numbers are resources being offered. Negative are resources 
										being asked for.
			}*/

		}

		private class TurnTracker{
			int currentTurn;
			String status;
			int longestRoad;
			int largestArmy;

			/*
		TurnTracker {
			currentTurn (index): Who's turn it is (0-3),
			status (string) = ['Rolling' or 'Robbing' or 'Playing' or 'Discarding' or 'FirstRound' or 
							     'SecondRound']: What's happening now,
			longestRoad (index): The index of who has the longest road, -1 if no one has it 
			largestArmy (index): The index of who has the biggest army (3 or more), -1 if no one has it
		}*/

		}

	}


	
}

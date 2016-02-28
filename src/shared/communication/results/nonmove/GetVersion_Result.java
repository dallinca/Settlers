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
		
		Game game = new Game();		
		
		return currentVersion;
	}

	/**
	 * @TODO
	 * @param post
	 */

	private void parseJson(String post){
		//System.out.println("Entering gauntlet.");
		Gson gson = new Gson();

		setModel(gson.fromJson(post, ClientModel.class));

		valid = true;
		upToDate = false;

		//System.out.println("Gauntlet passed.");
	}


	public boolean isUpToDate() {
		return upToDate;
	}

	public void setUpToDate(boolean upToDate) {
		this.upToDate = upToDate;
	}


	public ClientModel getModel() {
		return model;
	}

	public void setModel(ClientModel model) {
		this.model = model;
	}


	public class ClientModel{

		private DevCardList deck;
		private Bank bank;
		private Chat chat;
		private Log log;
		private Map map;
		private Player[] players;
		private TradeOffer tradeOffer;
		private TurnTracker turnTracker;
		private int version;
		private int winner;


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


		@Override
		public String toString() {
			return "ClientModel [deck=" + deck + ", \nbank=" + bank + ", \nchat="
					+ chat + ", \nlog=" + log + ", \nmap=" + map + ", \nplayers="
					+ Arrays.toString(players) + ", \ntradeOffer=" + tradeOffer
					+ ", \nturnTracker=" + turnTracker + ", \nversion=" + version
					+ ", \nwinner=" + winner + "]";
		}


		public DevCardList getDeck() {
			return deck;
		}


		public Bank getBank() {
			return bank;
		}

		public Chat getChat() {
			return chat;
		}

		public Log getLog() {
			return log;
		}

		public Map getMap() {
			return map;
		}

		public Player[] getPlayers() {
			return players;
		}

		public TradeOffer getTradeOffer() {
			return tradeOffer;
		}

		public TurnTracker getTurnTracker() {
			return turnTracker;
		}

		public int getVersion() {
			return version;
		}

		public int getWinner() {
			return winner;
		}

		public class Bank{
			int brick;
			int ore;
			int sheep;
			int wheat;
			int wood;
			public int getBrick() {
				return brick;
			}
			public int getOre() {
				return ore;
			}
			public int getSheep() {
				return sheep;
			}
			public int getWheat() {
				return wheat;
			}
			public int getWood() {
				return wood;
			}
			@Override
			public String toString() {
				return "Bank [brick=" + brick + ", ore=" + ore + ", sheep="
						+ sheep + ", wheat=" + wheat + ", wood=" + wood + "]";
			}


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

		}

		public class Chat{
			private MessageLine[] lines;


			@Override
			public String toString() {

				StringBuilder sb = new StringBuilder();

				for (int i = 0 ; i < lines.length; i++){					
					sb.append(lines[i].toString());
					if (i!=lines.length){
						sb.append(", ");
					}
				}


				return "Chat [list=" + sb.toString() + "]";
			}

			public MessageLine[] getLines() {
				return lines;
			}

			/*public Chat(){
				list = new MessageList();
			}*/

		}

		public class Log{
			private MessageLine[] lines;

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();

				for (int i = 0 ; i < lines.length; i++){					
					sb.append(lines[i].toString());
					if (i!=lines.length){
						sb.append(", ");
					}
				}

				return "Log [list=" + sb.toString() + "]";
			}

			public MessageLine[] getLines() {
				return lines;
			}
		}

		public class MessageList{
			private MessageLine[] lines;

			@Override
			public String toString() {
				return "MessageList [lines=" + Arrays.toString(lines) + "]";
			}

			public MessageLine[] getLines() {
				return lines;
			}
		}

		public class MessageLine{
			private String message;
			private String source;			
			/*public MessageLine(){						
			}
			public MessageLine(String message, String source){
				this.message = message;
				this.source = source;

			}*/

			public String getMessage() {
				return message;
			}

			public String getSource() {
				return source;
			}

			@Override
			public String toString() {
				return "MessageLine [message=" + message + ", source="
						+ source + "]";
			}
		}

		public class Map{
			private Hex[] hexes;
			private Port[] ports;
			private EdgeValue[] roads;
			private VertexObject[] settlements;
			private VertexObject[] cities;
			private int radius;
			private HexLocation robber;


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

			@Override
			public String toString() {
				return "Map [hexes=" + Arrays.toString(hexes) + ", ports="
						+ Arrays.toString(ports) + ", roads="
						+ Arrays.toString(roads) + ", settlements="
						+ Arrays.toString(settlements) + ", cities="
						+ Arrays.toString(cities) + ", radius=" + radius
						+ ", robber=" + robber + "]";
			}
			public Hex[] getHexes() {
				return hexes;
			}
			public Port[] getPorts() {
				return ports;
			}
			public EdgeValue[] getRoads() {
				return roads;
			}
			public VertexObject[] getSettlements() {
				return settlements;
			}
			public VertexObject[] getCities() {
				return cities;
			}
			public int getRadius() {
				return radius;
			}
			public HexLocation getRobber() {
				return robber;
			}
			public class VertexObject{
				private int owner;
				private VertexLocation location;


				@Override
				public String toString() {
					return "VertexObject [owner=" + owner + ", location="
							+ location + "]";
				}
				public int getOwner() {
					return owner;
				}
				public VertexLocation getLocation() {
					return location;
				}

			}

			/*private class Road{
				int owner;
				int x;
				int y;
				String direction;
			}*/
			public class VertexLocation{
				private int x;
				private int y;
				private String direction;


				@Override
				public String toString() {
					return "VertexLocation [x=" + x + ", y=" + y
							+ ", direction=" + direction + "]";
				}

				public int getX() {
					return x;
				}

				public int getY() {
					return y;
				}

				public String getDirection() {
					return direction;
				}

			}

			private class EdgeValue{
				private int owner;
				private EdgeLocation location;
				/*EdgeValue {
				owner (index): The index (not id) of the player who owns this piece (0-3),
					location (EdgeLocation): The location of this road.
			}*/
				public int getOwner() {
					return owner;
				}
				public EdgeLocation getLocation() {
					return location;
				}
				@Override
				public String toString() {
					return "EdgeValue [owner=" + owner + ", location="
							+ location + "]";
				}


			}

			private class EdgeLocation{
				private int x;
				private int y;
				private String direction;
				public int getX() {
					return x;
				}
				public int getY() {
					return y;
				}
				public String getDirection() {
					return direction;
				}
				@Override
				public String toString() {
					return "EdgeLocation [x=" + x + ", y=" + y + ", direction="
							+ direction + "]";
				}


				/*EdgeLocation {
						x (integer),
						y (integer),
						direction (string) = ['NW' or 'N' or 'NE' or 'SW' or 'S' or 'SE']
					}*/

			}

			private class Port{
				private String resource;
				private HexLocation location;				
				private String direction;
				private int ratio;


				@Override
				public String toString() {
					return "Port [resource=" + resource + ", location="
							+ location + ", direction=" + direction
							+ ", ratio=" + ratio + "]";
				}
				public String getResource() {
					return resource;
				}
				public HexLocation getLocation() {
					return location;
				}
				public String getDirection() {
					return direction;
				}
				public int getRatio() {
					return ratio;
				}

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
				private HexLocation location;
				private String resource;
				private int number;
				/*
				Hex {
				location (HexLocation),
				resource (string, optional) = ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']: What resource 
					this tile gives - it's only here if the tile is not desert., 
				number(integer, optional): What number is on this tile. It's omitted if this is a desert hex.
				}*/
				public HexLocation getLocation() {
					return location;
				}
				public String getResource() {
					return resource;
				}
				public int getNumber() {
					return number;
				}
				@Override
				public String toString() {
					return "Hex [location=" + location + ", resource="
							+ resource + ", number=" + number + "]";
				}


			}
			public class HexLocation{
				private int x;
				private int y;
				/*HexLocation {
				x (integer),
				y (integer)
				}*/
				public int getX() {
					return x;
				}
				public int getY() {
					return y;
				}
				@Override
				public String toString() {
					return "HexLocation [x=" + x + ", y=" + y + "]";
				}



			}
		}



		public class Player{
			private int cities;
			private String color;
			private boolean discarded;
			private int monuments;
			private String name;
			private DevCardList newDevCards;
			private DevCardList oldDevCards;
			private int playerIndex;
			private boolean playedDevCard;
			private int playerID;
			private ResourceList resources;
			private int roads;
			private int settlements;
			private int soldiers;
			private int victoryPoints;

			public int getCities() {
				return cities;
			}

			public String getColor() {
				return color;
			}

			public boolean isDiscarded() {
				return discarded;
			}

			public int getMonuments() {
				return monuments;
			}

			public String getName() {
				return name;
			}

			public DevCardList getNewDevCards() {
				return newDevCards;
			}

			public DevCardList getOldDevCards() {
				return oldDevCards;
			}

			public int getPlayerIndex() {
				return playerIndex;
			}

			public boolean isPlayedDevCard() {
				return playedDevCard;
			}

			public int getPlayerID() {
				return playerID;
			}

			public ResourceList getResources() {
				return resources;
			}

			public int getRoads() {
				return roads;
			}

			public int getSettlements() {
				return settlements;
			}

			public int getSoldiers() {
				return soldiers;
			}

			public int getVictoryPoints() {
				return victoryPoints;
			}

			@Override
			public String toString() {
				return "Player [cities=" + cities + ", color=" + color
						+ ", discarded=" + discarded + ", monuments="
						+ monuments + ", name=" + name + ", newDevCards="
						+ newDevCards + ", oldDevCards=" + oldDevCards
						+ ", playerIndex=" + playerIndex + ", playedDevCard="
						+ playedDevCard + ", playerID=" + playerID
						+ ", resources=" + resources + ", roads=" + roads
						+ ", settlements=" + settlements + ", soldiers="
						+ soldiers + ", victoryPoints=" + victoryPoints + "]";
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

		}

		public class TradeOffer{
			private int sender;
			private int receiver;
			private ResourceList offer;
			public int getSender() {
				return sender;
			}
			public int getReceiver() {
				return receiver;
			}
			public ResourceList getOffer() {
				return offer;
			}
			@Override
			public String toString() {
				return "TradeOffer [sender=" + sender + ", receiver="
						+ receiver + ", offer=" + offer + "]";
			}

			/*
			TradeOffer {
				sender (integer): The index of the person offering the trade,
				receiver (integer): The index of the person the trade was offered to.,
				offer (ResourceList): Positive numbers are resources being offered. Negative are resources 
										being asked for.
			}*/

		}

		public class TurnTracker{
			private int currentTurn;
			private String status;
			private int longestRoad;
			private int largestArmy;

			public int getCurrentTurn() {
				return currentTurn;
			}
			public String getStatus() {
				return status;
			}
			public int getLongestRoad() {
				return longestRoad;
			}
			public int getLargestArmy() {
				return largestArmy;
			}
			@Override
			public String toString() {
				return "TurnTracker [currentTurn=" + currentTurn + ", status="
						+ status + ", longestRoad=" + longestRoad
						+ ", largestArmy=" + largestArmy + "]";
			}

			/*
		TurnTracker {
			currentTurn (index): Who's turn it is (0-3),
			status (string) = ['Rolling' or 'Robbing' or 'Playing' or 'Discarding' or 'FirstRound' or 
							     'SecondRound']: What's happening now,
			longestRoad (index): The index of who has the longest road, -1 if no one has it 
			largestArmy (index): The index of who has the biggest army (3 or more), -1 if no one has it
		}*/

		}

		public class DevCardList{

			private int monopoly;
			private int monument;
			private int roadBuilding;
			private int soldier;
			private int yearOfPlenty;
			public int getMonopoly() {
				return monopoly;
			}
			public int getMonument() {
				return monument;
			}
			public int getRoadBuilding() {
				return roadBuilding;
			}
			public int getSoldier() {
				return soldier;
			}
			public int getYearOfPlenty() {
				return yearOfPlenty;
			}
			@Override
			public String toString() {
				return "DevCardList [monopoly=" + monopoly + ", monument="
						+ monument + ", roadBuilding=" + roadBuilding
						+ ", soldier=" + soldier + ", yearOfPlenty="
						+ yearOfPlenty + "]";
			}				

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

		public class ResourceList{		

			private int brick;
			private int ore;
			private int sheep;
			private int wheat;
			private int wood;
			public int getBrick() {
				return brick;
			}
			public int getOre() {
				return ore;
			}
			public int getSheep() {
				return sheep;
			}
			public int getWheat() {
				return wheat;
			}
			public int getWood() {
				return wood;
			}
			@Override
			public String toString() {
				return "ResourceList [brick=" + brick + ", ore=" + ore
						+ ", sheep=" + sheep + ", wheat=" + wheat
						+ ", wood=" + wood + "]";
			}

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



}

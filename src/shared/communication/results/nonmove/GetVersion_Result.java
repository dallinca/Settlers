package shared.communication.results.nonmove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.Bank;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import shared.model.Game;
import shared.model.board.Board;
import shared.model.board.Hex;
import shared.model.board.Vertex;
import shared.model.items.DevelopmentCard;
import shared.model.player.Player;

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
		// Init the BANK with the current amounts of resources and development cards
		int wheat = model.bank.getWheat();
		int brick = model.bank.getBrick();
		int wood = model.bank.getWood();
		int sheep = model.bank.getSheep();
		int ore = model.bank.getOre();
		int soldiers = model.deck.getSoldier();
		int monopoly = model.deck.getMonopoly();
		int yearOfPlenty = model.deck.getYearOfPlenty();
		int roadBuilder = model.deck.getRoadBuilding();
		int monument = model.deck.getMonument();
		Bank bank = new Bank(wheat, brick, wood, sheep, ore, soldiers, monopoly, yearOfPlenty, roadBuilder, monument);
		
		// Init the PLAYERS
		Player[] players = new Player[4];
		ClientModel.MPlayer[] mPlayers = model.players;
		for(ClientModel.MPlayer mPlayer: mPlayers) {
			// Get the resources the player should have right now
			wheat = mPlayer.getResources().getWheat();
			brick = mPlayer.getResources().getBrick();
			wood = mPlayer.getResources().getWood();
			sheep = mPlayer.getResources().getSheep();
			ore = mPlayer.getResources().getOre();
			// Prep the Dev Cards
			ArrayList<DevelopmentCard> soldierCards = new ArrayList<DevelopmentCard>();
			ArrayList<DevelopmentCard> monopolyCards = new ArrayList<DevelopmentCard>();
			ArrayList<DevelopmentCard> yearOfPlentyCards = new ArrayList<DevelopmentCard>();
			ArrayList<DevelopmentCard> roadBuilderCards = new ArrayList<DevelopmentCard>();
			ArrayList<DevelopmentCard> monumentCards = new ArrayList<DevelopmentCard>();
			
			// Make the Dev cards that are played that we keep track of
			for(int i = 0; i < mPlayer.getMonuments(); i++) { monumentCards.add(new DevelopmentCard(DevCardType.MONUMENT, 2, true)); }
			for(int i = 0; i < mPlayer.getSoldiers(); i++) 	{ soldierCards.add(new DevelopmentCard(DevCardType.SOLDIER, 2, true));}
			// Make the Dev cards that are not played that were bought this turn
			for(int i = 0; i < mPlayer.getNewDevCards().getSoldier(); i++) 		{ soldierCards.add(new DevelopmentCard(DevCardType.SOLDIER, 3, false)); }
			for(int i = 0; i < mPlayer.getNewDevCards().getMonopoly(); i++) 	{ monopolyCards.add(new DevelopmentCard(DevCardType.MONOPOLY, 3, false)); }
			for(int i = 0; i < mPlayer.getNewDevCards().getYearOfPlenty(); i++) { yearOfPlentyCards.add(new DevelopmentCard(DevCardType.YEAR_OF_PLENTY, 3, false)); }
			for(int i = 0; i < mPlayer.getNewDevCards().getRoadBuilding(); i++) { roadBuilderCards.add(new DevelopmentCard(DevCardType.ROAD_BUILD, 3, false)); }
			for(int i = 0; i < mPlayer.getNewDevCards().getMonopoly(); i++) 	{ monumentCards.add(new DevelopmentCard(DevCardType.MONOPOLY, 3, false)); }
			// Make the Dev cards that are not played that were bought on a previous turn
			for(int i = 0; i < mPlayer.getOldDevCards().getSoldier(); i++) 		{ soldierCards.add(new DevelopmentCard(DevCardType.SOLDIER, 2, false)); }
			for(int i = 0; i < mPlayer.getOldDevCards().getMonopoly(); i++) 	{ monopolyCards.add(new DevelopmentCard(DevCardType.MONOPOLY, 2, false)); }
			for(int i = 0; i < mPlayer.getOldDevCards().getYearOfPlenty(); i++) { yearOfPlentyCards.add(new DevelopmentCard(DevCardType.YEAR_OF_PLENTY, 2, false)); }
			for(int i = 0; i < mPlayer.getOldDevCards().getRoadBuilding(); i++) { roadBuilderCards.add(new DevelopmentCard(DevCardType.ROAD_BUILD, 2, false)); }
			for(int i = 0; i < mPlayer.getOldDevCards().getMonopoly(); i++) 	{ monumentCards.add(new DevelopmentCard(DevCardType.MONOPOLY, 2, false)); }
			
			// Create the player giving him his index, bank, and the cards he starts with
			Player player = new Player(mPlayer.getPlayerIndex(), bank, brick, wheat, ore, sheep, wood, soldierCards, monopolyCards, yearOfPlentyCards, roadBuilderCards, monumentCards);
			// Set other primitive Player data types
			player.setPlayerColor(getCatanColorFromString(mPlayer.getColor())); // TODO figure out color
			player.setHasDiscarded(mPlayer.isDiscarded());
			player.setPlayerName(mPlayer.getName());
			player.setHasPlayedDevCardThisTurn(mPlayer.isPlayedDevCard());
			player.setPlayerId(mPlayer.getPlayerID());
			player.setTotalVictoryPoints(mPlayer.getVictoryPoints());
			players[player.getPlayerIndex()] = player;
		}
		
		// Init the BOARD
		// First with the Hexes, their types, their roll numbers
		ArrayList<Hex> newHexes = new ArrayList<Hex>();
		for(ClientModel.MMap.MHex mHex: model.getMap().getHexes()) {
			boolean hasRobber = false;
			if(model.map.getRobber() == mHex.getLocation()) {
				hasRobber = true;
			}
			// Do our 3 offset, find the HexType of the String TODO (Verify this part is working), and the roll value
			newHexes.add( new Hex(mHex.getLocation().getX() + 3, mHex.getLocation().getY() + 3, getHexTypeFromString(mHex.getResource()), mHex.getNumber(), hasRobber) );
		}
		// Make the Board
		Board board = new Board(newHexes);
		// Setup Ports, vertices, edges
		for(ClientModel.MMap.Port port: model.map.ports) {
			// Get hex Locations
			HexLocation hexLoc = new HexLocation(port.getLocation().getX(), port.getLocation().getY());
			EdgeDirection ed = getEdgeDirectionFromString(port.getDirection());
			PortType pt = getPortTypeFromString(port.getResource());
			// One by one setup the array of PortTypes for the vertex initialization of the Map
			board.initPortTypesFromServer(hexLoc, pt);
		}
		board.initBordersAndVertices();
		
		// Use the Board to place all pieces Settlements, Cities, Roads
		// First Settlements
		for(ClientModel.MMap.VertexObject settlement: model.map.settlements) {
			// Get hex location
			HexLocation hexLoc = new HexLocation(settlement.getLocation().getX(),settlement.getLocation().getY());
			VertexDirection vd = getVertexDirectionFromString(settlement.location.getDirection());
			try {
				players[settlement.getOwner()].getPlayerPieces().placeSettlement(board.getVertex(new VertexLocation(hexLoc, vd )));
			} catch (Exception e) {
				System.out.println("something went screwy and we couldn't placy the settlement");
				e.printStackTrace();
			}
		}
		for(ClientModel.MMap.VertexObject city: model.map.cities) {
			// Get hex location
			HexLocation hexLoc = new HexLocation(city.getLocation().getX(),city.getLocation().getY());
			VertexDirection vd = getVertexDirectionFromString(city.location.getDirection());
			try {
				players[city.getOwner()].getPlayerPieces().placeCity(board.getVertex(new VertexLocation(hexLoc, vd )));
			} catch (Exception e) {
				System.out.println("something went screwy and we couldn't placy the settlement");
				e.printStackTrace();
			}
		}
		for(ClientModel.MMap.EdgeValue road: model.map.roads) {
			// Get hex location
			HexLocation hexLoc = new HexLocation(road.getLocation().getX(),road.getLocation().getY());
			EdgeDirection ed = getEdgeDirectionFromString(road.location.getDirection());
			try {
				players[road.getOwner()].getPlayerPieces().placeRoad(board.getEdge(new EdgeLocation(hexLoc, ed)));
			} catch (Exception e) {
				System.out.println("something went screwy and we couldn't placy the settlement");
				e.printStackTrace();
			}
		}
		
		
		
		
		// Init the GAME
		Game game = new Game();	
		return currentVersion;
	}

	/**
	 * 
	 * TODO Verify that these are the string that are being passed from the server!
	 * 
	 * ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']
	 * 
	 * @param resource
	 * @return
	 */
	private HexType getHexTypeFromString(String resource) {
		if(resource == "wood") {
			return HexType.WOOD;
		} else if(resource == "brick") {
			return HexType.BRICK;
		} else if(resource == "sheep") {
			return HexType.SHEEP;
		} else if(resource == "qheat") {
			return HexType.WHEAT;
		} else if(resource == "ore") {
			return HexType.ORE;
		} else {
			return HexType.DESERT;
		}
	}

	/**
	 * 
	 * TODO Verify that these are the string that are being passed from the server!
	 * 
	 * ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']
	 * 
	 * @param resource
	 * @return
	 */
	private PortType getPortTypeFromString(String resource) {
		if(resource == "wood") {
			return PortType.WOOD;
		} else if(resource == "brick") {
			return PortType.BRICK;
		} else if(resource == "sheep") {
			return PortType.SHEEP;
		} else if(resource == "qheat") {
			return PortType.WHEAT;
		} else if(resource == "ore") {
			return PortType.ORE;
		} else {
			return PortType.THREE;
		}
	}
	
	/**
	 * TODO Verify that these are the string that are being passed from the server!
	 * 
	 * @param color
	 * @return
	 */
	private CatanColor getCatanColorFromString(String color) {
		if(color == "blue") {
			return CatanColor.BLUE;
		} else if(color == "brown") {
			return CatanColor.BROWN;
		} else if(color == "green") {
			return CatanColor.GREEN;
		} else if(color == "orange") {
			return CatanColor.ORANGE;
		} else if(color == "puce") {
			return CatanColor.PUCE;
		} else if(color == "purple") {
			return CatanColor.PURPLE;
		} else if(color == "red") {
			return CatanColor.RED;
		} else if(color == "white") {
			return CatanColor.WHITE;
		} else if(color == "yellow") {
			return CatanColor.YELLOW;
		}
		return null;
	}
	
	/**
	 * TODO Verify this
	 * 
	 * @param direction
	 * @return
	 */
	private EdgeDirection getEdgeDirectionFromString(String direction) {
		if(direction == "NW") {
			return EdgeDirection.NorthWest;
		} else if(direction == "N") {
			return EdgeDirection.North;
		} else if(direction == "NE") {
			return EdgeDirection.NorthEast;
		} else if(direction == "SW") {
			return EdgeDirection.SouthWest;
		} else if(direction == "S") {
			return EdgeDirection.South;
		} else if(direction == "SE") {
			return EdgeDirection.SouthEast;
		}
		return null;
	}
	
	/**
	 * TODO Verify this
	 * 
	 * @param direction
	 * @return
	 */
	private VertexDirection getVertexDirectionFromString(String direction) {
		if(direction == "NW") {
			return VertexDirection.NorthWest;
		} else if(direction == "W") {
			return VertexDirection.West;
		} else if(direction == "SW") {
			return VertexDirection.SouthWest;
		} else if(direction == "SE") {
			return VertexDirection.SouthEast;
		} else if(direction == "E") {
			return VertexDirection.East;
		} else if(direction == "NE") {
			return VertexDirection.NorthEast;
		}
		return null;
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

		private MDevCardList deck;
		private MBank bank;
		private MChat chat;
		private MLog log;
		private MMap map;
		private MPlayer[] players;
		private MTradeOffer tradeOffer;
		private MTurnTracker turnTracker;
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


		public MDevCardList getDeck() {
			return deck;
		}


		public MBank getBank() {
			return bank;
		}

		public MChat getChat() {
			return chat;
		}

		public MLog getLog() {
			return log;
		}

		public MMap getMap() {
			return map;
		}

		public MPlayer[] getPlayers() {
			return players;
		}

		public MTradeOffer getTradeOffer() {
			return tradeOffer;
		}

		public MTurnTracker getTurnTracker() {
			return turnTracker;
		}

		public int getVersion() {
			return version;
		}

		public int getWinner() {
			return winner;
		}

		public class MBank{
			int brick;
			int ore;
			int sheep;
			int wheat;
			int wood;
			public MBank(int wheat2, int brick2, int wood2, int sheep2, int ore2) {
				// TODO Auto-generated constructor stub
			}
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

		public class MChat{
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

		public class MLog{
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

		public class MMap{
			private MHex[] hexes;
			private Port[] ports;
			private EdgeValue[] roads;
			private VertexObject[] settlements;
			private VertexObject[] cities;
			private int radius;
			private MHexLocation robber;


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
			public MHex[] getHexes() {
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
			public MHexLocation getRobber() {
				return robber;
			}
			public class VertexObject{
				private int owner;
				private MVertexLocation location;


				@Override
				public String toString() {
					return "VertexObject [owner=" + owner + ", location="
							+ location + "]";
				}
				public int getOwner() {
					return owner;
				}
				public MVertexLocation getLocation() {
					return location;
				}

			}

			/*private class Road{
				int owner;
				int x;
				int y;
				String direction;
			}*/
			public class MVertexLocation{
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
				private MEdgeLocation location;
				/*EdgeValue {
				owner (index): The index (not id) of the player who owns this piece (0-3),
					location (EdgeLocation): The location of this road.
			}*/
				public int getOwner() {
					return owner;
				}
				public MEdgeLocation getLocation() {
					return location;
				}
				@Override
				public String toString() {
					return "EdgeValue [owner=" + owner + ", location="
							+ location + "]";
				}


			}

			private class MEdgeLocation{
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
				private MHexLocation location;				
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
				public MHexLocation getLocation() {
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

			public class MHex{
				private MHexLocation location;
				private String resource;
				private int number;
				/*
				Hex {
				location (HexLocation),
				resource (string, optional) = ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']: What resource 
					this tile gives - it's only here if the tile is not desert., 
				number(integer, optional): What number is on this tile. It's omitted if this is a desert hex.
				}*/
				public MHexLocation getLocation() {
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
			public class MHexLocation{
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



		public class MPlayer{
			private int cities;
			private String color;
			private boolean discarded;
			private int monuments;
			private String name;
			private MDevCardList newDevCards;
			private MDevCardList oldDevCards;
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

			public MDevCardList getNewDevCards() {
				return newDevCards;
			}

			public MDevCardList getOldDevCards() {
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

		public class MTradeOffer{
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

		public class MTurnTracker{
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

		public class MDevCardList{

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

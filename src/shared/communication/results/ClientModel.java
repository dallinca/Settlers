package shared.communication.results;

import java.util.Arrays;

import shared.model.Game.Line;
import client.data.TradeInfo;

/**
 * A full model of the game for JSON deserialization.
 * 
 */
public class ClientModel {

	String title;
	MDevCardList deck;
	MBank bank;
	MChat chat;
	MLog log;
	MMap map;
	MPlayer[] players;
	MTradeOffer tradeOffer;
	MTurnTracker turnTracker;
	int version;
	int winner;

	public ClientModel(MDevCardList deck, MBank bank, MChat chat, MLog log,
			MMap map, MPlayer[] players, MTradeOffer tradeOffer,
			MTurnTracker turnTracker, int version, int winner) {
		super();
		this.deck = deck;
		this.bank = bank;
		this.chat = chat;
		this.log = log;
		this.map = map;
		this.players = players;
		this.tradeOffer = tradeOffer;
		this.turnTracker = turnTracker;
		this.version = version;
		this.winner = winner;
	}

	public ClientModel() {
		super();
		this.deck = new MDevCardList();
		this.bank = new MBank();
		this.chat = new MChat();
		this.log = new MLog();
		this.map = new MMap();
		this.turnTracker = new MTurnTracker();
	}


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
	}

	public class MChat{
		MessageLine[] lines;


		public MChat(Line[] lines) {
			if (lines==null){
				this.lines = new MessageLine[0];
				return;
			}
			this.lines = new MessageLine[lines.length];

			for (int i = 0; i< lines.length; i++){
				this.lines[i] = new MessageLine(lines[i]);
			}
		}

		public MChat() {
			lines = new MessageLine[0];
		}

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
	}

	public class MLog{
		MessageLine[] lines;

		public MLog(Line[] logs) {
			if (logs == null){
				this.lines = new MessageLine[0];
				return;
			}
			this.lines = new MessageLine[logs.length];

			for (int i = 0; i< logs.length; i++){
				this.lines[i] = new MessageLine(logs[i]);
				//System.out.println("-----------------" + this.lines[i].getMessage());
			}
		}

		public MLog() {
			// TODO Auto-generated constructor stub
		}

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
		MessageLine[] lines;

		@Override
		public String toString() {
			return "MessageList [lines=" + Arrays.toString(lines) + "]";
		}

		public MessageLine[] getLines() {
			return lines;
		}
	}

	public class MessageLine{
		String message;
		String source;	

		public MessageLine(Line line) {
			message = line.getMessage();
			source = line.getSource();
		}

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
		MHex[] hexes;
		Port[] ports;
		EdgeValue[] roads;
		VertexObject[] settlements;
		VertexObject[] cities;
		int radius;
		MHexLocation robber;


		public MMap(){
			robber = new MHexLocation();

		}
		@Override
		public String toString() {
			return "Map [hexes=" + Arrays.toString(hexes) + ",\n ports="
					+ Arrays.toString(ports) + ",\n roads="
					+ Arrays.toString(roads) + ",\n settlements="
					+ Arrays.toString(settlements) + ",\n cities="
					+ Arrays.toString(cities) + ",\n radius=" + radius
					+ ",\n robber=" + robber + "]";
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
			int owner;
			MVertexLocation location;

			public VertexObject() {}
			public VertexObject(int owner, MVertexLocation location) {
				this.owner = owner;
				this.location = location;
			}

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

		public class MVertexLocation{

			String direction;
			int x;
			int y;

			public MVertexLocation() {}
			public MVertexLocation(String direction, int x, int y) {
				this.direction = direction;
				this.x = x;
				this.y = y;
			}

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

		class EdgeValue{

			int owner;
			MEdgeLocation location;

			public EdgeValue() {

				location = new MEdgeLocation();
			}
			public EdgeValue(int owner, MEdgeLocation location) {
				this.owner = owner;
				this.location = location;
			}

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

		class MEdgeLocation{

			String direction;
			int x;
			int y;

			public MEdgeLocation() {}
			public MEdgeLocation(String direction, int x, int y) {
				this.direction = direction;
				this.x = x;
				this.y = y;
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
			@Override
			public String toString() {
				return "EdgeLocation [x=" + x + ", y=" + y + ", direction="
						+ direction + "]";
			}

		}

		class Port{
			String resource;
			MHexLocation location;				
			String direction;
			int ratio;

			public Port() {
				location = new MHexLocation();


			}
			public Port(String resource, MHexLocation location, String direction, int ratio) {
				this.resource = resource;
				this.location = location;
				this.direction = direction;
				this.ratio = ratio;
			}

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

		}

		public class MHex{
			MHexLocation location;
			String resource;
			int number;

			public MHex(){
				location = new MHexLocation();
			}
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
			int x;
			int y;

			public MHexLocation() {}
			public MHexLocation(int x, int y) {
				this.x = x;
				this.y = y;
			}

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
		int cities;
		String color;
		boolean discarded;
		int monuments;
		String name;
		MDevCardList newDevCards;
		MDevCardList oldDevCards;
		int playerIndex;
		boolean playedDevCard;
		int playerID;
		ResourceList resources;
		int roads;
		int settlements;
		int soldiers;
		int victoryPoints;




		public MPlayer() {
			super();

			this.resources = new ResourceList();
			this.newDevCards = new MDevCardList();
			this.oldDevCards = new MDevCardList();
		}

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
			return "Player [cities=" + cities + ",\n color=" + color
					+ ",\n discarded=" + discarded + ",\n monuments="
					+ monuments + ",\n name=" + name + ",\n newDevCards="
					+ newDevCards + ",\n oldDevCards=" + oldDevCards
					+ ",\n playerIndex=" + playerIndex + ",\n playedDevCard="
					+ playedDevCard + ",\n playerID=" + playerID
					+ ",\n resources=" + resources + ",\n roads=" + roads
					+ ",\n settlements=" + settlements + ",\n soldiers="
					+ soldiers + ",\n victoryPoints=" + victoryPoints + "]";
		}
	}

	public class MTradeOffer{
		int sender;
		int receiver;
		ResourceList offer;

		public MTradeOffer(TradeInfo offer) {
			this.sender = offer.getSender();
			this.receiver = offer.getReceiver();
			this.offer = offer.getOffer();			
		}
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

	}

	public class MTurnTracker{
		int currentTurn;
		String status;
		int longestRoad;
		int largestArmy;

		public MTurnTracker(){
			status = "";
		}

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

	}

	public class MDevCardList{

		int yearOfPlenty;
		int monopoly;
		int soldier;
		int roadBuilding;
		int monument;




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

	}

	public class ResourceList{		

		public int brick;
		public int ore;
		public int sheep;
		public int wheat;
		public int wood;

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
	}	

}

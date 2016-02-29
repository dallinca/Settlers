package shared.communication.results;

import java.util.Arrays;


public class ClientModel {


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
			 int owner;
			 MVertexLocation location;


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
			 int x;
			 int y;
			 String direction;


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
			 int x;
			 int y;
			 String direction;
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
	}

	public class MTradeOffer{
		 int sender;
		 int receiver;
		 ResourceList offer;
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

		 int monopoly;
		 int monument;
		 int roadBuilding;
		 int soldier;
		 int yearOfPlenty;
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
			return "ResourceList [brick=" + brick + ", ore=" + ore
					+ ", sheep=" + sheep + ", wheat=" + wheat
					+ ", wood=" + wood + "]";
		}
	}	

}
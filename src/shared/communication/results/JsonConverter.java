package shared.communication.results;

import java.util.ArrayList;

import shared.communication.results.ClientModel.MTradeOffer;
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
import client.Client;
import client.data.PlayerInfo;
import client.data.TradeInfo;

import com.google.gson.Gson;

import shared.model.Game;
import shared.model.board.Board;
import shared.model.board.Hex;
import shared.model.items.DevelopmentCard;
import shared.model.player.Player;


public class JsonConverter {


	private Game version;	
	private ClientModel model;

	public ClientModel getModel() {
		return model;
	}

	public void setModel(ClientModel model) {
		this.model = model;
	}

	public Game parseJson(String jsonGame){
		Gson gson = new Gson();

		setModel(gson.fromJson(jsonGame, ClientModel.class));

		return getGame();
	}	

	private Game getGame() {
		// check to see if we are going to be updating the entire game, or just the player waiting screen
		int counter = 0;
		ArrayList<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
		for(ClientModel.MPlayer mPlayer: model.getPlayers()) {
			if(mPlayer != null) {
				PlayerInfo playerInfo= new PlayerInfo();
				playerInfo.setColor(getCatanColorFromString(mPlayer.color));
				playerInfo.setId(mPlayer.playerID);
				playerInfo.setName(mPlayer.name);
				playerInfo.setPlayerIndex(mPlayer.playerIndex);
				playersInfo.add(playerInfo);
				counter++;
			}
		}
		// Not enough players to start the real game
		if(counter < 4) {
			Client.getInstance().updatePlayersInGameInfo(playersInfo);
			return null;
		}
		
		// Init the BANK with the current amounts of resources and development cards

		ClientModel.MBank modelbank = model.getBank();
		ClientModel.MDevCardList modelDeck = model.getDeck();

		int wheat = modelbank.getWheat();
		int brick = modelbank.getBrick();
		int wood = modelbank.getWood();
		int sheep = modelbank.getSheep();
		int ore = modelbank.getOre();

		int soldiers = modelDeck.getSoldier();
		int monopoly = modelDeck.getMonopoly();
		int yearOfPlenty = modelDeck.getYearOfPlenty();
		int roadBuilder = modelDeck.getRoadBuilding();
		int monument = modelDeck.getMonument();
		Bank bank = new Bank(wheat, brick, wood, sheep, ore, soldiers, monopoly, yearOfPlenty, roadBuilder, monument);

		// Init the PLAYERS
		Player[] players = new Player[4];
		ClientModel.MPlayer[] mPlayers = model.getPlayers();
		
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
			for(int i = 0; i < mPlayer.getNewDevCards().getMonument(); i++) 	{ monumentCards.add(new DevelopmentCard(DevCardType.MONUMENT, 3, false)); }
			// Make the Dev cards that are not played that were bought on a previous turn
			for(int i = 0; i < mPlayer.getOldDevCards().getSoldier(); i++) 		{ soldierCards.add(new DevelopmentCard(DevCardType.SOLDIER, 2, false)); }
			for(int i = 0; i < mPlayer.getOldDevCards().getMonopoly(); i++) 	{ monopolyCards.add(new DevelopmentCard(DevCardType.MONOPOLY, 2, false)); }
			for(int i = 0; i < mPlayer.getOldDevCards().getYearOfPlenty(); i++) { yearOfPlentyCards.add(new DevelopmentCard(DevCardType.YEAR_OF_PLENTY, 2, false)); }
			for(int i = 0; i < mPlayer.getOldDevCards().getRoadBuilding(); i++) { roadBuilderCards.add(new DevelopmentCard(DevCardType.ROAD_BUILD, 2, false)); }
			
			//found a bug!!!! this used to be for monopoly and monument. #typos. ooooohhhh yeeeeeaaaaaahhhh!!!!!!!
			for(int i = 0; i < mPlayer.getOldDevCards().getMonument(); i++) 	{ monumentCards.add(new DevelopmentCard(DevCardType.MONUMENT, 2, false)); }

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
			// Set the turn of the current player
			if(mPlayer.getPlayerIndex() == model.turnTracker.currentTurn) {
				player.setPlayersTurn(true);
			}
			if(mPlayer.getPlayerID() == Client.getInstance().getUserId()) {
				Client.getInstance().setPlayerIndex(mPlayer.getPlayerIndex());
			}
		}

		// Init the BOARD
		// First with the Hexes, their types, their roll numbers
		ClientModel.MMap modelMap = model.getMap();
		ArrayList<Hex> newHexes = new ArrayList<Hex>();
		for(ClientModel.MMap.MHex mHex: modelMap.getHexes()) {
			boolean hasRobber = false;
			if(model.getMap().getRobber().getX() == mHex.getLocation().getX() &&
					model.getMap().getRobber().getY() == mHex.getLocation().getY())
			{
				System.out.println("Dreams came true");
				hasRobber = true;
			}
			// Do our 3 offset, find the HexType of the String TODO (Verify this part is working), and the roll value
			newHexes.add( new Hex(mHex.getLocation().getX() + 3, mHex.getLocation().getY() + 3, getHexTypeFromString(mHex.getResource()), mHex.getNumber(), hasRobber) );
		}
		// Make the Board
		Board board = new Board(newHexes);
		board.setHexWithRobber(board.getHex(model.map.robber.x + 3, model.map.robber.y + 3));
		// Setup Ports, vertices, edges
		for(ClientModel.MMap.Port port: modelMap.getPorts()) {
			// Get hex Locations
			HexLocation hexLoc = new HexLocation(port.getLocation().getX(), port.getLocation().getY());
			//EdgeDirection ed = getEdgeDirectionFromString(port.getDirection());
			PortType pt = getPortTypeFromString(port.getResource());
			// One by one setup the array of PortTypes for the vertex initialization of the Map
			board.initPortTypesFromServer(hexLoc, pt);
		}
		board.initBordersAndVertices();

		// Use the Board to place all pieces Settlements, Cities, Roads
		// First Settlements
		for(ClientModel.MMap.VertexObject settlement: modelMap.getSettlements()) {
			// Get hex location
			HexLocation hexLoc = new HexLocation(settlement.getLocation().getX(),settlement.getLocation().getY());
			VertexDirection vd = getVertexDirectionFromString(settlement.getLocation().getDirection());
			try {
				players[settlement.getOwner()].getPlayerPieces().placeSettlement(board.getVertex(new VertexLocation(hexLoc, vd )));
			} catch (Exception e) {
				System.out.println("something went screwy and we couldn't place the settlement");
				e.printStackTrace();
			}
		}
		for(ClientModel.MMap.VertexObject city: modelMap.getCities()) {
			// Get hex location
			HexLocation hexLoc = new HexLocation(city.getLocation().getX(),city.getLocation().getY());
			VertexDirection vd = getVertexDirectionFromString(city.getLocation().getDirection());
			try {
				new VertexLocation(hexLoc, vd );
				board.getVertex(new VertexLocation(hexLoc, vd ));
				players[city.getOwner()].getPlayerPieces().placeInitialCity(board.getVertex(new VertexLocation(hexLoc, vd )));
			} catch (Exception e) {
				System.out.println("something went screwy and we couldn't place the city");
				e.printStackTrace();
			}
		}
		for(ClientModel.MMap.EdgeValue road: modelMap.getRoads()) {
			// Get hex location
			HexLocation hexLoc = new HexLocation(road.getLocation().getX(),road.getLocation().getY());
			EdgeDirection ed = getEdgeDirectionFromString(road.location.getDirection());
			try {
				players[road.getOwner()].getPlayerPieces().placeRoad(board.getEdge(new EdgeLocation(hexLoc, ed)));
			} catch (Exception e) {
				System.out.println("something went screwy and we couldn't place the road");
				e.printStackTrace();
			}
		}


		// Init the GAME//-===========================================================
		version = new Game(players, board, bank);
		
		//Get trade offer (if exists)
		if (model.getTradeOffer()!=null){			
			MTradeOffer mto = model.getTradeOffer();
			TradeInfo tradeInfo = new TradeInfo(mto.getSender(), mto.getReceiver(), mto.getOffer());
			
			version.setTradeOffer(tradeInfo);
		}

		Game.Line[] historyLines = new Game.Line[model.getLog().getLines().length];

		int i = 0;

		for(ClientModel.MessageLine ml : model.getLog().getLines()){

			Game.Line current = version.new Line();
			current.setSource(ml.getSource());
			current.setMessage(ml.getMessage());		
			historyLines[i] = current;
			i++;
		}

		Game.Line[] chatLines = new Game.Line[model.getChat().getLines().length];

		i = 0;

		for(ClientModel.MessageLine ml : model.getChat().getLines()){

			Game.Line current = version.new Line();
			current.setSource(ml.getSource());
			current.setMessage(ml.getMessage());		
			chatLines[i] = current;
			i++;
		}


		version.setWinner(model.getWinner());
		version.setVersionNumber(model.getVersion());
		version.setCurrentPlayer(players[model.turnTracker.getCurrentTurn()]);
		version.setStatus(model.turnTracker.getStatus());
		if(model.turnTracker.getStatus().equals("FirstRound")) {
			version.setTurnNumber(0);
		} else if(model.turnTracker.getStatus().equals("SecondRound")) {
			version.setTurnNumber(1);
		} else {
			version.setTurnNumber(3);
		}
		
		if (model.turnTracker.largestArmy!=-1){

			version.setLargestArmy(players[model.turnTracker.largestArmy]);
		}
		if (model.turnTracker.longestRoad!=-1){

			version.setLongestRoad((players[model.turnTracker.longestRoad]));
		}		

		version.setChat(chatLines);
		version.setHistory(historyLines);
	

		return version;
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
		if(resource==null){
			return HexType.DESERT;
		}	else if(resource.equals("wood")) {
			return HexType.WOOD;
		} else if(resource.equals("brick")) {
			return HexType.BRICK;
		} else if(resource.equals("sheep")) {
			return HexType.SHEEP;
		} else if(resource.equals("wheat")) {
			return HexType.WHEAT;
		} else if(resource.equals("ore")) {
			return HexType.ORE;
		}
		return null; 		
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
		if(resource==null){
			return PortType.THREE;
		} else if(resource.equals("wood")) {
			return PortType.WOOD;
		} else if(resource.equals("brick")) {
			return PortType.BRICK;
		} else if(resource.equals("sheep")) {
			return PortType.SHEEP;
		} else if(resource.equals("wheat")) {
			return PortType.WHEAT;
		} else if(resource.equals("ore")) {
			return PortType.ORE;
		}
		return null;
	}

	/**
	 * TODO Verify that these are the string that are being passed from the server!
	 * 
	 * @param color
	 * @return
	 */
	private CatanColor getCatanColorFromString(String color) {
		if(color.equals("blue")) {
			return CatanColor.BLUE;
		} else if(color.equals("brown")) {
			return CatanColor.BROWN;
		} else if(color.equals("green")) {
			return CatanColor.GREEN;
		} else if(color.equals("orange")) {
			return CatanColor.ORANGE;
		} else if(color.equals("puce")) {
			return CatanColor.PUCE;
		} else if(color.equals("purple")) {
			return CatanColor.PURPLE;
		} else if(color.equals("red")) {
			return CatanColor.RED;
		} else if(color.equals("white")) {
			return CatanColor.WHITE;
		} else if(color.equals("yellow")) {
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
		if(direction.equals("NW")) {
			return EdgeDirection.NorthWest;
		} else if(direction.equals("N")) {
			return EdgeDirection.North;
		} else if(direction.equals("NE")) {
			return EdgeDirection.NorthEast;
		} else if(direction.equals("SW")) {
			return EdgeDirection.SouthWest;
		} else if(direction.equals("S")) {
			return EdgeDirection.South;
		} else if(direction.equals("SE")) {
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

		if(direction.equals("NW")) {
			return VertexDirection.NorthWest;
		} else if(direction.equals("W")) {
			return VertexDirection.West;
		} else if(direction.equals("SW")) {
			return VertexDirection.SouthWest;
		} else if(direction.equals("SE")) {
			return VertexDirection.SouthEast;
		} else if(direction.equals("E")) {
			return VertexDirection.East;
		} else if(direction.equals("NE")) {
			return VertexDirection.NorthEast;
		}
		return null;
	}

	/**
	 * @TODO
	 * @param post
	 */


}

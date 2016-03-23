package shared.communication.results;

import java.util.ArrayList;

import shared.communication.results.ClientModel.MTradeOffer;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
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
import shared.model.board.TradePort;
import shared.model.items.City;
import shared.model.items.DevelopmentCard;
import shared.model.items.Road;
import shared.model.items.Settlement;
import shared.model.player.Player;


/**
 * Converts a JSON version of the current game into a Game object, and constructs that object.
 * 
 */
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
		
		// Setup Ports
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
	 * 
	 * ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']
	 * 
	 * @param resource
	 * @return
	 */
	private HexType getHexTypeFromString(String resource) {
		if(resource==null){
			return HexType.DESERT;
		} else if(resource.equals("wood")) {
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
	 * TODO -- Test and verify as working
	 * 
	 * ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']
	 * 
	 * @param resource
	 * @return
	 */
	private String putHexTypeIntoString(HexType resource) {
		if(resource == HexType.DESERT){
			return null;
		} else if(resource == HexType.WOOD) {
			return "wood";
		} else if(resource == HexType.BRICK) {
			return "brick";
		} else if(resource == HexType.SHEEP) {
			return "sheep";
		} else if(resource == HexType.WHEAT) {
			return "wheat";
		} else if(resource == HexType.ORE) {
			return "ore";
		}
		return null; 		
	}

	/**
	 * 
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
	 * TODO -- Test and verify as working
	 * 
	 * ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']
	 * 
	 * @param resource
	 * @return
	 */
	private String putPortTypeIntoString(PortType resource) {
		if(resource == PortType.THREE){
			return null;
		} else if(resource == PortType.WOOD) {
			return "wood";
		} else if(resource == PortType.BRICK) {
			return "brick";
		} else if(resource == PortType.SHEEP) {
			return "sheep";
		} else if(resource == PortType.WHEAT) {
			return "wheat";
		} else if(resource == PortType.ORE) {
			return "ore";
		}
		return null;
	}

	/**
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
	 * TODO -- Test and verify as working
	 * @param color
	 * @return
	 */
	private String putCatanColorIntoString(CatanColor color) {
		if(color == CatanColor.BLUE) {
			return "blue";
		} else if(color == CatanColor.BROWN) {
			return "brown";
		} else if(color == CatanColor.GREEN) {
			return "green";
		} else if(color == CatanColor.ORANGE) {
			return "orange";
		} else if(color == CatanColor.PUCE) {
			return "puce";
		} else if(color == CatanColor.PURPLE) {
			return "purple";
		} else if(color == CatanColor.RED) {
			return "red";
		} else if(color == CatanColor.WHITE) {
			return "white";
		} else if(color == CatanColor.YELLOW) {
			return "yellow";
		}
		return null;
	}

	/**
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
	 * TODO -- Test and verify as working
	 * @param direction
	 * @return
	 */
	private String putEdgeDirectionIntoString(EdgeDirection direction) {
		if(direction == EdgeDirection.NorthWest) {
			return "NW";
		} else if(direction == EdgeDirection.North) {
			return "N";
		} else if(direction == EdgeDirection.NorthEast) {
			return "NE";
		} else if(direction == EdgeDirection.SouthWest) {
			return "SW";
		} else if(direction == EdgeDirection.South) {
			return "S";
		} else if(direction == EdgeDirection.SouthEast) {
			return "SE";
		}
		return null;
	}

	/**
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
	 * TODO -- Test and verify as working
	 * @param direction
	 * @return
	 */
	private String putVertexDirectionIntoString(VertexDirection direction) {

		if(direction == VertexDirection.NorthWest) {
			return "NW";
		} else if(direction == VertexDirection.West) {
			return "W";
		} else if(direction == VertexDirection.SouthWest) {
			return "SW";
		} else if(direction == VertexDirection.SouthEast) {
			return "SE";
		} else if(direction == VertexDirection.East) {
			return "E";
		} else if(direction == VertexDirection.NorthEast) {
			return "NE";
		}
		return null;
	}
	
	private int getTradePortRatio(PortType port) {
		if(port == PortType.THREE) {
			return 3;
		} else {
			return 2;
		}
	}

	public ClientModel toClientModel(Game game) {
		// Init client Model with general information
		ClientModel clientModel = new ClientModel();
		clientModel.version = game.getVersionNumber();
		clientModel.winner = game.getWinner();
		
		// Init the BANK with the current amounts of resources and development cards
		Bank bank = game.getBank();
		
		ClientModel.MBank modelbank = clientModel.new MBank();
		ClientModel.MDevCardList modelDeck = clientModel.new MDevCardList();

		modelbank.wheat = bank.getWheatDeck().size();
		modelbank.brick = bank.getBrickDeck().size();
		modelbank.wood = bank.getLumberDeck().size();
		modelbank.sheep = bank.getSheepDeck().size();
		modelbank.ore = bank.getOreDeck().size();

		modelDeck.monopoly = bank.numberOfDevCardType(DevCardType.MONOPOLY);
		modelDeck.monument = bank.numberOfDevCardType(DevCardType.MONUMENT);
		modelDeck.roadBuilding = bank.numberOfDevCardType(DevCardType.ROAD_BUILD);
		modelDeck.soldier = bank.numberOfDevCardType(DevCardType.SOLDIER);
		modelDeck.yearOfPlenty = bank.numberOfDevCardType(DevCardType.YEAR_OF_PLENTY);
		
		clientModel.bank = modelbank;
		clientModel.deck = modelDeck;
		
		// Init the PLAYERS

		ArrayList<ClientModel.MPlayer> players = new ArrayList<ClientModel.MPlayer>();
		for(Player player: game.getAllPlayers()) {
			ClientModel.MPlayer mPlayer = clientModel.new MPlayer();
			
			// Player General information
			mPlayer.color = putCatanColorIntoString(player.getPlayerColor());
			mPlayer.playerID = player.getPlayerId();
			mPlayer.name = player.getPlayerName();
			mPlayer.playerIndex = player.getPlayerIndex();
			mPlayer.victoryPoints = player.getVictoryPoints();
			mPlayer.discarded = player.isHasDiscarded();
			mPlayer.playedDevCard = player.isHasPlayedDevCardThisTurn();
			
			// Player resource cards
			mPlayer.resources.brick = player.getNumberResourcesOfType(ResourceType.BRICK);
			mPlayer.resources.ore = player.getNumberResourcesOfType(ResourceType.ORE);
			mPlayer.resources.wheat = player.getNumberResourcesOfType(ResourceType.WHEAT);
			mPlayer.resources.sheep = player.getNumberResourcesOfType(ResourceType.SHEEP);
			mPlayer.resources.wood = player.getNumberResourcesOfType(ResourceType.WOOD);
			
			// Player Development Cards
			mPlayer.newDevCards.monopoly 		= player.getDevelopmentCardHand().numberUnplayedNEWDevCards(DevCardType.MONOPOLY, game.getTurnNumber());
			mPlayer.newDevCards.monument 		= player.getDevelopmentCardHand().numberUnplayedNEWDevCards(DevCardType.MONUMENT, game.getTurnNumber());
			mPlayer.newDevCards.roadBuilding 	= player.getDevelopmentCardHand().numberUnplayedNEWDevCards(DevCardType.ROAD_BUILD, game.getTurnNumber());
			mPlayer.newDevCards.soldier 		= player.getDevelopmentCardHand().numberUnplayedNEWDevCards(DevCardType.SOLDIER, game.getTurnNumber());
			mPlayer.newDevCards.yearOfPlenty	= player.getDevelopmentCardHand().numberUnplayedNEWDevCards(DevCardType.YEAR_OF_PLENTY, game.getTurnNumber());
			
			mPlayer.oldDevCards.monopoly 		= player.getDevelopmentCardHand().numberUnplayedOLDDevCards(DevCardType.MONOPOLY, game.getTurnNumber());
			mPlayer.oldDevCards.monument 		= player.getDevelopmentCardHand().numberUnplayedOLDDevCards(DevCardType.MONUMENT, game.getTurnNumber());
			mPlayer.oldDevCards.roadBuilding 	= player.getDevelopmentCardHand().numberUnplayedOLDDevCards(DevCardType.ROAD_BUILD, game.getTurnNumber());
			mPlayer.oldDevCards.soldier 		= player.getDevelopmentCardHand().numberUnplayedOLDDevCards(DevCardType.SOLDIER, game.getTurnNumber());
			mPlayer.oldDevCards.yearOfPlenty 	= player.getDevelopmentCardHand().numberUnplayedOLDDevCards(DevCardType.YEAR_OF_PLENTY, game.getTurnNumber());
			
			mPlayer.soldiers = player.getNumberOfSoldiersPlayed();
			mPlayer.monuments = player.getDevelopmentCardHand().getNumberOfMonumentsPlayed();
			
			// Player pieces left to play
			mPlayer.cities = player.getNumberUnplayedCities();
			mPlayer.settlements = player.getNumberUnplayedSettlements();
			mPlayer.roads = player.getNumberUnplayedRoads();
			
			// Record the player into the clientModel
			players.add(mPlayer);
		}
		
		// Init the TURNTRACKER
		ClientModel.MTurnTracker modelTT = clientModel.new MTurnTracker();
		clientModel.turnTracker = modelTT;
		modelTT.currentTurn = game.getIndexOfPlayer(game.getCurrentPlayer());
		modelTT.largestArmy = game.getIndexOfPlayer(game.getLargestArmy());
		modelTT.longestRoad = game.getIndexOfPlayer(game.getLongestRoad());
		modelTT.status = game.getStatus();	
				
		// Init the BOARD
		ClientModel.MMap modelMap = clientModel.new MMap();
		clientModel.map = modelMap;
		Board board = game.getBoard();
		
			// HEXES
		Hex[][] allLandHexes = board.getMapHexes();
		ArrayList<ClientModel.MMap.MHex> modelHexes = new ArrayList<ClientModel.MMap.MHex>();
		
		Hex curHex = null;
		for(int Y = 0; Y < allLandHexes.length; Y++) {
			for(int X = 0; X <allLandHexes.length; X++) {
				curHex = allLandHexes[Y][X];
				if(curHex != null) {
					ClientModel.MMap.MHex mHex = clientModel.map.new MHex();
					mHex.location.x = curHex.getTheirX_coord_hex();
					mHex.location.y = curHex.getTheirY_coord_hex();
					mHex.number = curHex.getRollValue();
					mHex.resource = putHexTypeIntoString(curHex.getHexType());
					modelHexes.add(mHex);
				}
			}
		}
		modelMap.hexes = new ClientModel.MMap.MHex[modelHexes.size()];
		modelMap.hexes = modelHexes.toArray(modelMap.hexes);
		
			// PORTS
		PortType[] allPorts = board.getMapPorts();
		ArrayList<ClientModel.MMap.Port> modelPorts = new ArrayList<ClientModel.MMap.Port>();
		ClientModel.MMap.Port mPort = clientModel.map.new Port();
		if(allPorts != null) {
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[0]),clientModel.map.new MHexLocation(0,3),  putEdgeDirectionIntoString(EdgeDirection.North),		getTradePortRatio(allPorts[0])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[1]),clientModel.map.new MHexLocation(2,1),  putEdgeDirectionIntoString(EdgeDirection.NorthWest),	getTradePortRatio(allPorts[1])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[2]),clientModel.map.new MHexLocation(3,-1), putEdgeDirectionIntoString(EdgeDirection.NorthWest),	getTradePortRatio(allPorts[2])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[3]),clientModel.map.new MHexLocation(3,-3), putEdgeDirectionIntoString(EdgeDirection.SouthWest),	getTradePortRatio(allPorts[3])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[4]),clientModel.map.new MHexLocation(1,-3), putEdgeDirectionIntoString(EdgeDirection.South),		getTradePortRatio(allPorts[4])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[5]),clientModel.map.new MHexLocation(-1,-2),putEdgeDirectionIntoString(EdgeDirection.South),		getTradePortRatio(allPorts[5])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[6]),clientModel.map.new MHexLocation(-3,0), putEdgeDirectionIntoString(EdgeDirection.SouthEast),	getTradePortRatio(allPorts[6])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[7]),clientModel.map.new MHexLocation(-3,2), putEdgeDirectionIntoString(EdgeDirection.NorthEast),	getTradePortRatio(allPorts[7])));
			modelPorts.add(clientModel.map.new Port(putPortTypeIntoString(allPorts[8]),clientModel.map.new MHexLocation(-2,3), putEdgeDirectionIntoString(EdgeDirection.NorthEast),	getTradePortRatio(allPorts[8])));		
		}
		
		
			// ROADS
		Road[] allRoads = new Road[9];
		ArrayList<ClientModel.MMap.EdgeValue> modelRoads = new ArrayList<ClientModel.MMap.EdgeValue>();
		
		
			// SETTLEMENTS
		Settlement[] allSettlements = new Settlement[9];
		ArrayList<ClientModel.MMap.VertexObject> modelSettlement = new ArrayList<ClientModel.MMap.VertexObject>();
		
			// CITIES
		City[] allCities = new City[9];
		ArrayList<ClientModel.MMap.VertexObject> modelCities = new ArrayList<ClientModel.MMap.VertexObject>();
		

		// Init the TRADE OFFER
				// TODO
		

		// Init the CHAT
				// TODO
		

		// Init the LOG
				// TODO
		
		// Return the clientModel
		return clientModel;
	}
	

	public void toClientModel() {
	}


}

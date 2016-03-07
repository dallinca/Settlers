package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import shared.model.board.Hex;
import shared.model.board.Vertex;
import shared.model.items.City;
import shared.model.items.Municipal;
import shared.model.items.Road;
import shared.model.items.Settlement;
import shared.model.player.Player;
import shared.model.turn.ActionManager;
import shared.model.turn.ActionType;
import client.Client;
import client.ClientFacade;
import client.base.*;
import client.data.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {

	private boolean init = true;
	private IRobView robView;
	private Game game;
	private boolean robVictimChosen, robHexChosen;
	private HexLocation robHex;


	public MapController(IMapView view, IRobView robView) {

		super(view);
		System.out.println("MapController MapController()");

		setRobView(robView);

		initFromModel();
		Client.getInstance().addObserver(this);

	}

	public IMapView getView() {
		System.out.print(".");

		return (IMapView)super.getView();
	}

	private IRobView getRobView() {
		System.out.println("MapController getRobView()");
		return robView;
	}
	private void setRobView(IRobView robView) {
		System.out.println("MapController setRobView()");
		this.robView = robView;
	}

	/**
	 * This function initializes the map from the game model.
	 * 
	 * @pre Client.getInstance().getGame() != null
	 * @post The GUI map will be a direct reflection game map model for hexes, ports, and player pieces
	 * @return none
	 */
	protected void initFromModel() {
		System.out.println("MapController initFromModel()");

		// Check if there is a game to initialize from
		if(Client.getInstance().getGame() == null) {
			return;
		}

		initWaterHexes();
		initLandHexes();
		initPorts();
		initPlayerPieces();

	}

	/**
	 * Extension of initFromModel. Initializes the water hexes.
	 * 
	 * @pre should only be called from initFromModel
	 * @post GUI water Hexes Initialized
	 */
	private void initWaterHexes() {
		getView().addHex(new HexLocation(0, -3), HexType.WATER);
		getView().addHex(new HexLocation(1, -3), HexType.WATER);
		getView().addHex(new HexLocation(2, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -2), HexType.WATER);
		getView().addHex(new HexLocation(3, -1), HexType.WATER);
		getView().addHex(new HexLocation(3, -0), HexType.WATER);
		getView().addHex(new HexLocation(2, 1), HexType.WATER);
		getView().addHex(new HexLocation(1, 2), HexType.WATER);
		getView().addHex(new HexLocation(0, 3), HexType.WATER);
		getView().addHex(new HexLocation(-1, 3), HexType.WATER);
		getView().addHex(new HexLocation(-2, 3), HexType.WATER);
		getView().addHex(new HexLocation(-3, 3), HexType.WATER);
		getView().addHex(new HexLocation(-3, 2), HexType.WATER);
		getView().addHex(new HexLocation(-3, 1), HexType.WATER);
		getView().addHex(new HexLocation(-3, 0), HexType.WATER);
		getView().addHex(new HexLocation(-2, -1), HexType.WATER);
		getView().addHex(new HexLocation(-1, -2), HexType.WATER);
	}

	/**
	 * Extension of initFromModel. Initializes the land hexes.
	 * 
	 * @pre should only be called from initFromModel
	 * @post GUI land Hexes Initialized
	 */
	private void initLandHexes() {
		Hex[][] allLandHexes = Client.getInstance().getGame().getMapHexes();
		Hex robberHex = Client.getInstance().getGame().getBoard().getHexWithRobber();
		Hex curHex = null;
		for(int i = 0; i < allLandHexes.length; i++) {
			for(int j = 0; j < allLandHexes.length; j++) {
				// Check each hex in the array and check to see if it is real
				curHex = allLandHexes[i][j];
				if(curHex != null) {
					getView().addHex(new HexLocation(curHex.getTheirX_coord_hex(), curHex.getTheirY_coord_hex()), curHex.getHexType());
					//System.out.println("X: "+curHex.getTheirX_coord_hex() + " Y: " +curHex.getTheirY_coord_hex() + " TYPE: "+curHex.getHexType());
					if(curHex.getRollValue() > 0) {
						getView().addNumber(new HexLocation(curHex.getTheirX_coord_hex(), curHex.getTheirY_coord_hex()), curHex.getRollValue());
						//System.out.println(" Roll value: " + curHex.getRollValue());
					}
					if(robberHex.getX_coord_hex() == curHex.getX_coord_hex() && robberHex.getY_coord_hex() == curHex.getY_coord_hex()) {
						getView().placeRobber(new HexLocation(curHex.getTheirX_coord_hex(), curHex.getTheirY_coord_hex()));
					}
				}
			}
		}
	}

	/**
	 * Extension of initFromModel. Initializes the ports.
	 * 
	 * @pre should only be called from initFromModel
	 * @post GUI ports Initialized
	 */
	private void initPorts() {
		// Init ports! and stuff :)
		PortType[] allPorts = Client.getInstance().getGame().getMapPorts();
		if(allPorts != null) {
			getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), allPorts[0]);
			getView().addPort(new EdgeLocation(new HexLocation(2, 1), EdgeDirection.NorthWest), allPorts[1]);
			getView().addPort(new EdgeLocation(new HexLocation(3, -1), EdgeDirection.NorthWest), allPorts[2]);
			getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), allPorts[3]);
			getView().addPort(new EdgeLocation(new HexLocation(1, -3), EdgeDirection.South), allPorts[4]);
			getView().addPort(new EdgeLocation(new HexLocation(-1, -2), EdgeDirection.South), allPorts[5]);
			getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), allPorts[6]);
			getView().addPort(new EdgeLocation(new HexLocation(-3, 2), EdgeDirection.NorthEast), allPorts[7]);
			getView().addPort(new EdgeLocation(new HexLocation(-2, 3), EdgeDirection.NorthEast), allPorts[8]);
		}
	}

	/**
	 * Extension of initFromModel. Initializes the playerPieces.
	 * 
	 * @pre should only be called from initFromModel
	 * @post GUI playerPieces Initialized
	 */
	private void initPlayerPieces() {
		// Init all the roads, settlements, and cities!
		for(Player player: Client.getInstance().getGame().getAllPlayers()) {
			for(Road road: player.getPlayerPieces().getRoads()) {
				// Only place the piece on the map on the gui if it is assigned a location in the model
				if(road.getEdge() != null) {
					HexLocation hx = new HexLocation(road.getEdge().getTheirX_coord(), road.getEdge().getTheirY_coord());
					EdgeLocation el = new EdgeLocation(hx, road.getEdge().getTheirEdgeDirection());
					getView().placeRoad(el, road.getPlayer().getPlayerColor());
				}
			}
			for(Settlement settlement: player.getPlayerPieces().getSettlements()) {
				if(settlement.getVertex() != null) {
					// Only place the piece on the map on the gui if it is assigned a location in the model
					HexLocation hx = new HexLocation(settlement.getVertex().getTheirX_coord_ver(), settlement.getVertex().getTheirY_coord_ver());
					VertexLocation vl = new VertexLocation(hx, settlement.getVertex().getTheirVertexDirection());
					getView().placeSettlement(vl, settlement.getPlayer().getPlayerColor());
				}

			}
			for(City city: player.getPlayerPieces().getCities()) {
				if(city.getVertex() != null) {
					// Only place the piece on the map on the gui if it is assigned a location in the model
					HexLocation hx = new HexLocation(city.getVertex().getTheirX_coord_ver(), city.getVertex().getTheirY_coord_ver());
					VertexLocation vl = new VertexLocation(hx, city.getVertex().getTheirVertexDirection());
					getView().placeCity(vl, city.getPlayer().getPlayerColor());
				}
			}
		}
	}
	
	/**
	 * Called by the GUI when the edge is hovered over in the place road overlay.
	 * 
	 * @pre edgeLoc != null
	 * @post none
	 * @return Whether the current player can place a road at the specified edge Location
	 */
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		System.out.println("MapController canPlaceRoad()");
		boolean canDo = Client.getInstance().getGame().canDoPlaceRoadOnEdge( Client.getInstance().getUserId(), edgeLoc);
		return canDo;
	}

	/**
	 * Called by the GUI when the vertex is hovered over in the place settlement overlay.
	 * 
	 * @pre vertLoc != null
	 * @post none
	 * @return Whether the current player can place a settlement at the specified vertex Location
	 */
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		System.out.println("MapController canPlaceSettlement()");
		return Client.getInstance().getGame().canDoPlaceSettlementOnVertex( Client.getInstance().getUserId(), vertLoc);
	}

	/**
	 * Called by the GUI when the vertex is hovered over in the place city overlay.
	 * 
	 * @pre vertLoc != null
	 * @post none
	 * @return Whether the current player can place a city at the specified vertex Location
	 */
	public boolean canPlaceCity(VertexLocation vertLoc) {
		System.out.println("MapController canPlaceCity()");
		return Client.getInstance().getGame().canDoPlaceCityOnVertex(vertLoc);
	}

	/**
	 * Called by the GUI when the hex is hovered over in the place robber overlay.
	 * 
	 * @pre hecLoc != null
	 * @post none
	 * @return Whether the current player can place the robber at the specified hex Location
	 */
	public boolean canPlaceRobber(HexLocation hexLoc) {
		System.out.println("MapController canPlaceRobber()");
		return Client.getInstance().getGame().canDoMoveRobberToHex(Client.getInstance().getUserId(), hexLoc);
		}

	/**
	 * Called by GUI when the user selects a valid edge location to build a road.
	 * However if we are using road builder then we keep track of the roads before sending off the request to the server.
	 * 
	 * @pre canPlaceRoad(edgeLoc) != false && edgeLoc != null
	 * @post Road will be placed on the specified location. Place Road overlay will be closed.
	 * 
	 */
	public void placeRoad(EdgeLocation edgeLoc) {
		System.out.println("MapController placeRoad()");
		// if we are doing road builder
		if(Client.getInstance().isInRoadBuilding()) {
			// If we are choosing the first road
			if(!Client.getInstance().isChosenFirstRoadBuildingRoad()) {
				Client.getInstance().setFirstRoadBuildLocation(edgeLoc);
				Client.getInstance().setChosenFirstRoadBuildingRoad(true);
				try {
					Client.getInstance().getGame().placeRoadOnEdge(Client.getInstance().getUserId(), edgeLoc, true);
					getView().placeRoad(edgeLoc, Client.getInstance().getGame().getPlayerByID(Client.getInstance().getUserId()).getPlayerColor());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// If we are choosing the second road
			else {
				ClientFacade.getInstance().playRoadBuilding(Client.getInstance().getFirstRoadBuildLocation(), edgeLoc);
				Client.getInstance().setFirstRoadBuildLocation(null);
				Client.getInstance().setChosenFirstRoadBuildingRoad(false);
				Client.getInstance().setInRoadBuilding(false);
			}
		} else {
			try {
				ActionManager.getInstance().doBuild(ActionType.PURCHASE_ROAD, edgeLoc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Called by GUI when the user selects a valid vertex location to build a settlement.
	 * 
	 * @pre canPlaceSettlement(vertLoc) != false && vertLoc != null
	 * @post Settlement will be placed on the specified location. Place Settlement overlay will be closed.
	 * 
	 */
	public void placeSettlement(VertexLocation vertLoc) {
		System.out.println("MapController placeSettlement()");
		try {
			ActionManager.getInstance().doBuild(ActionType.PURCHASE_SETTLEMENT, vertLoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getView().placeSettlement(vertLoc, Client.getInstance().getColor());
	}

	/**
	 * Called by GUI when the user selects a valid vertex location to build a city.
	 * 
	 * @pre canPlaceCity(vertLoc) != false && vertLoc != null
	 * @post City will be placed on the specified location. Place city overlay will be closed.
	 * 
	 */
	public void placeCity(VertexLocation vertLoc) {
		System.out.println("MapController placeCity()");
		try {
			ActionManager.getInstance().doBuild(ActionType.PURCHASE_CITY, vertLoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getView().placeCity(vertLoc, Client.getInstance().getColor());
	}

	/**
<<<<<<< HEAD
	 * Replaces the robber on a given hex. Used for both robbing and soldier plays.
	 * Determines which players may be robbed around specified hex and displays appropriate windows.
	 * State must be robbing or playing in order to initiate.
=======
	 * TODO - Javadoc
>>>>>>> 64dc12e52c0b5707608af1a6e0a70a56b17dc93a
	 * 
	 */
	public void placeRobber(HexLocation hexLoc) {
		System.out.println("MapController placeRobber()");

		Game game = Client.getInstance().getGame();

		if( game.canDoMoveRobberToHex(Client.getInstance().getUserId(), hexLoc)){
			System.out.println("Robber placed successfully.");
			robHexChosen=true;
			robHex = hexLoc;

			LinkedList<RobPlayerInfo> victims = new LinkedList<RobPlayerInfo>();

			Vertex[] vertices;
			try {
				vertices = game.getBoard().getHex(hexLoc).getAdjacentVertices();


				for (int i = 0; i < vertices.length; i++){
					Municipal m = vertices[i].getMunicipal();

					if ( m !=null){
						Player p = m.getPlayer();

						RobPlayerInfo rbi = new RobPlayerInfo();
						rbi.setColor(p.getPlayerColor());
						rbi.setId(p.getPlayerId());
						rbi.setName(p.getPlayerName());
						int numCards = p.getNumberResourcesOfType(ResourceType.BRICK)+p.getNumberResourcesOfType(ResourceType.ORE)+
								p.getNumberResourcesOfType(ResourceType.SHEEP)+p.getNumberResourcesOfType(ResourceType.WHEAT)+
								p.getNumberResourcesOfType(ResourceType.WOOD);
						rbi.setNumCards(numCards);
						rbi.setPlayerIndex(p.getPlayerIndex());

						boolean accountedFor = false;
						for (RobPlayerInfo current : victims){
							if (rbi.getColor() == current.getColor()){
								accountedFor = true;
								break;
							}
						}

						if (!accountedFor && rbi.getId()!=Client.getInstance().getUserId()){
							victims.add(rbi);
						}
					}
				}

				RobPlayerInfo[] targets = new RobPlayerInfo[victims.size()];

				int j = 0;

				for (RobPlayerInfo current : victims){
					targets[j] = current;
					System.out.println("Potential victim: "+current.getName());
					j++;
				}
				if (targets.length==0){
					System.out.println("No victims in vicinity.");
				}

				getRobView().setPlayers(targets);				
				getRobView().showModal();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Function that is called when a move is started. Depending on which stated we are in, 
	 * different overlays are used.
	 * 
	 * @pre state variable is set
	 * @post Correct overlays will be displayed based off game state
	 * 
	 */
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		System.out.println("MapController startMove()");
		boolean canCancel = true;
		if(Client.getInstance().getGame().getStatus().equals("FirstRound") ||
				Client.getInstance().getGame().getStatus().equals("SecondRound") ||
				Client.getInstance().getGame().getStatus().equals("Robbing")) {
			canCancel = false;
		}
		System.out.println("END of STARTMOVE");
		getView().startDrop(pieceType, Client.getInstance().getColor(), canCancel);
	}

	/**
	 * Ends a move sequence.
	 * 
	 */
	public void cancelMove() {
		System.out.println("MapController cancelMove()");
	}

	/**
	 * Called when the Soldier development card is used. Allows the robbing action dictated by current player.
	 * 
	 * @pre Soldier Development Card legally played
	 * @post Player with be able to move robber to chosen place, rob adjacent municipal owners, and stop income from chosen hex.
	 * 
	 */
	public void playSoldierCard() {	
		System.out.println("MapController playSoldierCard()");
		startMove(PieceType.ROBBER, true, true);
	}

	/**
	 * Called when the RoadBuilding development card is used. Allows the building of two free roads.
	 * 
	 * @pre RoadBuilding Dev Card legally played
	 * @post Player will have choice to place two roads on the map free of charge.
	 * 
	 */
	public void playRoadBuildingCard() {
		System.out.println("MapController playRoadBuildingCard()");	
		ActionManager.getInstance().doAction(ActionType.PLAYCARD_BUILDROADS);
		CatanColor color = Client.getInstance().getGame().getAllPlayers()[Client.getInstance().getPlayerIndex()].getPlayerColor();
    	
		//Build two roads
		getView().startDrop(PieceType.ROAD, color, false);
    	getView().startDrop(PieceType.ROAD, color, false);
	}

	/**
	 * Robs the specified player, closes the modal, and sends information to the clientfacade.
	 */
	public void robPlayer(RobPlayerInfo victim) {	
		System.out.println("MapController robPlayer()");

		robVictimChosen = true;
		int victimIndex;
		
		if (victim==null){
			victimIndex = -1;
		}else{
			victimIndex = victim.getPlayerIndex();
		}

		if (robVictimChosen == true && robHexChosen == true
				&& Client.getInstance().getGame().getStatus().equals("Robbing")){
			ClientFacade.getInstance().robPlayer(robHex, victimIndex);
			getRobView().closeModal();
		}else if (robVictimChosen == true && robHexChosen == true 
				&& Client.getInstance().getGame().getStatus().equals("Playing")){
			ClientFacade.getInstance().playSoldier(robHex, victimIndex);
		}
	}
	/**
<<<<<<< HEAD
	 * Updates map controller based on current state of the game.
	 * --Redraws map.
	 * --Triggers robbing sequence.
=======
	 * Every time time there is an updated version of the Game model. This function will fire
	 * and update the map controller with the updated information.
	 * 
	 * @pre Game object changed in Client
	 * @post GUI map is updated to reflect current Game model state.
	 * 
>>>>>>> 64dc12e52c0b5707608af1a6e0a70a56b17dc93a
	 */
	@Override
	public void update(Observable o, Object arg) {
		Game game = Client.getInstance().getGame();
		System.out.println("MapController update()");
		// If the game is null just return
		if(game == null) {
			return;
		}

		initFromModel();

		if (game.getStatus().equals("Robbing")&&game.isPlayersTurn(Client.getInstance().getUserId())){
			System.out.println("Robber time!");
			startMove(PieceType.ROBBER, true, false);
		}
		return;
	}

}


package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import shared.model.board.Hex;
import shared.model.items.City;
import shared.model.items.Road;
import shared.model.items.Settlement;
import shared.model.player.Player;
import shared.model.turn.ActionManager;
import shared.model.turn.ActionType;
import client.Client;
import client.base.*;
import client.data.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {
	
	private boolean init = true;
	private IRobView robView;
	private Game game;
	
	private Client client;
	private ActionManager actionManger;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		System.out.println("MapController MapController()");
		
		setRobView(robView);
		
		initFromModel();
		actionManger = ActionManager.getInstance();
		client = Client.getInstance();
		client.addObserver(this);
		
	}
	
	public IMapView getView() {
		System.out.println("MapController getView()");
		
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
	
	protected void initFromModel() {
		System.out.println("MapController initFromModel()");
		
		// Check if there is a game to initialize from
		if(Client.getInstance().getGame() == null) {
			return;
			//Client.getInstance().setGame(new Game());
		}

		// Init the water Hexes
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

		
		// Init the land hexes
		Hex[][] allLandHexes = Client.getInstance().getGame().getMapHexes();

		Hex curHex = null;
		for(int i = 0; i < allLandHexes.length; i++) {
			for(int j = 0; j < allLandHexes.length; j++) {
				// Check each hex in the array and check to see if it is real
				curHex = allLandHexes[i][j];
				if(curHex != null) {
					getView().addHex(new HexLocation(curHex.getTheirX_coord_hex(), curHex.getTheirY_coord_hex()), curHex.getHexType());
					if(curHex.getRollValue() != -1) {
						getView().addNumber(new HexLocation(curHex.getTheirX_coord_hex(), curHex.getTheirY_coord_hex()), curHex.getRollValue());
					} else {
						getView().placeRobber(new HexLocation(curHex.getTheirX_coord_hex(), curHex.getTheirY_coord_hex()));
					}
				}
			}
		}

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
		
		// Init all the roads, settlements, and cities!
		for(Player player: Client.getInstance().getGame().getAllPlayers()) {
			for(Road road: player.getPlayerPieces().getRoads()) {
				// Only place the peice on the map on the gui if it is assigned a location in the model
				if(road.getEdge() != null) {
					HexLocation hx = new HexLocation(road.getEdge().getTheirX_coord(), road.getEdge().getTheirY_coord());
					EdgeLocation el = new EdgeLocation(hx, road.getEdge().getTheirEdgeDirection());
					getView().placeRoad(el, road.getPlayer().getPlayerColor());
				}
			}
			for(Settlement settlement: player.getPlayerPieces().getSettlements()) {
				if(settlement.getVertex() != null) {
					// Only place the peice on the map on the gui if it is assigned a location in the model
					HexLocation hx = new HexLocation(settlement.getVertex().getTheirX_coord_ver(), settlement.getVertex().getTheirY_coord_ver());
					VertexLocation vl = new VertexLocation(hx, settlement.getVertex().getTheirVertexDirection());
					getView().placeSettlement(vl, settlement.getPlayer().getPlayerColor());
				}
				
			}
			for(City city: player.getPlayerPieces().getCities()) {
				if(city.getVertex() != null) {
					// Only place the peice on the map on the gui if it is assigned a location in the model
					HexLocation hx = new HexLocation(city.getVertex().getTheirX_coord_ver(), city.getVertex().getTheirY_coord_ver());
					VertexLocation vl = new VertexLocation(hx, city.getVertex().getTheirVertexDirection());
					getView().placeCity(vl, city.getPlayer().getPlayerColor());
				}
			}
		}
		
		//
		
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		System.out.println("MapController canPlaceRoad()");
		client.getGame().canDoPlaceRoadOnEdge( client.getUserId(), edgeLoc);
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		System.out.println("MapController canPlaceSettlement()");
		client.getGame().canDoPlaceSettlementOnVertex( client.getUserId(), vertLoc);
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceCity(VertexLocation vertLoc) {
		System.out.println("MapController canPlaceCity()");
		client.getGame().canDoPlaceCityOnVertex(vertLoc);
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceRobber(HexLocation hexLoc) {
		System.out.println("MapController canPlaceRobber()");
		client.getGame().canDoMoveRobberToHex( client.getUserId(), hexLoc);
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public void placeRoad(EdgeLocation edgeLoc) {
		System.out.println("MapController placeRoad()");
		try {
			client.getGame().placeRoadOnEdge( client.getUserId(), edgeLoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getView().placeRoad(edgeLoc, client.getColor());
	}

	/**
	 * TODO
	 * 
	 */
	public void placeSettlement(VertexLocation vertLoc) {
		System.out.println("MapController placeSettlement()");
		try {
			client.getGame().placeSettlementOnVertex(client.getUserId(), vertLoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getView().placeSettlement(vertLoc, client.getColor());
	}

	/**
	 * TODO
	 * 
	 */
	public void placeCity(VertexLocation vertLoc) {
		System.out.println("MapController placeCity()");
		try {
			client.getGame().placeCityOnVertex(vertLoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getView().placeCity(vertLoc, client.getColor());
	}

	/**
	 * TODO
	 * 
	 */
	public void placeRobber(HexLocation hexLoc) {
		System.out.println("MapController placeRobber()");
		try {
			client.getGame().moveRobberToHex(client.getUserId(), hexLoc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}

	/**
	 * TODO
	 * 
	 */
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		System.out.println("MapController startMove()");

		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}

	/**
	 * TODO
	 * 
	 */
	public void cancelMove() {
		System.out.println("MapController cancelMove()");
		
	}

	/**
	 * TODO
	 * 
	 */
	public void playSoldierCard() {	
		System.out.println("MapController playSoldierCard()");
		actionManger.doAction(ActionType.PLAYCARD_KNIGHT);
	}

	/**
	 * TODO
	 * 
	 */
	public void playRoadBuildingCard() {
		System.out.println("MapController playRoadBuildingCard()");	
		actionManger.doAction(ActionType.PLAYCARD_BUILDROADS);
	}

	/**
	 * TODO
	 * 
	 */
	public void robPlayer(RobPlayerInfo victim) {	
		System.out.println("MapController robPlayer()");

		try {
			client.getGame().stealPlayerResource(client.getUserId(), victim.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * TODO
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("MapController update()");
		//store current Game in controller
		this.game = (Game)arg;
		if(init) {
			initFromModel();
			init = false;
			return;
		}
		
	}
	
}


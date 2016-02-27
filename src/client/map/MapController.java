package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import client.base.*;
import client.data.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {
	
	private IRobView robView;
	private Game game;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		System.out.println("MapController MapController()");
		
		setRobView(robView);
		
		initFromModel();
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
		
		//<temp>
		
		Random rand = new Random();

		for (int x = 0; x <= 3; ++x) {
			
			int maxY = 3 - x;			
			for (int y = -3; y <= maxY; ++y) {				
				int r = rand.nextInt(HexType.values().length);
				HexType hexType = HexType.values()[r];
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
						CatanColor.RED);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
						CatanColor.BLUE);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
						CatanColor.ORANGE);
				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
			}
			
			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					int r = rand.nextInt(HexType.values().length);
					HexType hexType = HexType.values()[r];
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
							CatanColor.RED);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
							CatanColor.BLUE);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
							CatanColor.ORANGE);
					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
				}
			}
		}
		
		PortType portType = PortType.BRICK;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);
		
		getView().placeRobber(new HexLocation(0, 0));
		
		getView().addNumber(new HexLocation(-2, 0), 2);
		getView().addNumber(new HexLocation(-2, 1), 3);
		getView().addNumber(new HexLocation(-2, 2), 4);
		getView().addNumber(new HexLocation(-1, 0), 5);
		getView().addNumber(new HexLocation(-1, 1), 6);
		getView().addNumber(new HexLocation(1, -1), 8);
		getView().addNumber(new HexLocation(1, 0), 9);
		getView().addNumber(new HexLocation(2, -2), 10);
		getView().addNumber(new HexLocation(2, -1), 11);
		getView().addNumber(new HexLocation(2, 0), 12);
		
		//</temp>
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		System.out.println("MapController canPlaceRoad()");
		
		
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		System.out.println("MapController canPlaceSettlement()");
		
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceCity(VertexLocation vertLoc) {
		System.out.println("MapController canPlaceCity()");
		
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public boolean canPlaceRobber(HexLocation hexLoc) {
		System.out.println("MapController canPlaceRobber()");
		
		return true;
	}

	/**
	 * TODO
	 * 
	 */
	public void placeRoad(EdgeLocation edgeLoc) {
		System.out.println("MapController placeRoad()");
		
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	/**
	 * TODO
	 * 
	 */
	public void placeSettlement(VertexLocation vertLoc) {
		System.out.println("MapController placeSettlement()");
		
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	/**
	 * TODO
	 * 
	 */
	public void placeCity(VertexLocation vertLoc) {
		System.out.println("MapController placeCity()");
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	/**
	 * TODO
	 * 
	 */
	public void placeRobber(HexLocation hexLoc) {
		System.out.println("MapController placeRobber()");
		
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
		
	}

	/**
	 * TODO
	 * 
	 */
	public void playRoadBuildingCard() {
		System.out.println("MapController playRoadBuildingCard()");	
		
	}

	/**
	 * TODO
	 * 
	 */
	public void robPlayer(RobPlayerInfo victim) {	
		System.out.println("MapController robPlayer()");
		
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
		
	}
	
}


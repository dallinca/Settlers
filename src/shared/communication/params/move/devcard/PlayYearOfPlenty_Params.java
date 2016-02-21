package shared.communication.params.move.devcard;

import shared.definitions.ResourceType;

public class PlayYearOfPlenty_Params {

	private int playerIndex;
	private String resource1;
	private String resource2;



	public PlayYearOfPlenty_Params(int playerIndex, ResourceType type1, ResourceType type2) {
		this.playerIndex = playerIndex;

		switch (type1){

		case BRICK: resource1 = "Brick";
		break;

		case ORE: resource1 = "Ore";
		break;

		case SHEEP: resource1 = "Sheep";
		break;

		case WHEAT: resource1 = "Wheat";
		break;

		case WOOD: resource1 = "Wood";
		break;	

		default:
			break;

		}

		switch (type2){

		case BRICK: resource2 = "Brick";
		break;

		case ORE: resource2 = "Ore";
		break;

		case SHEEP: resource2 = "Sheep";
		break;

		case WHEAT: resource2 = "Wheat";
		break;

		case WOOD: resource2 = "Wood";
		break;	

		default:
			break;

		}
	}



	public int getPlayerIndex() {
		return playerIndex;
	}



	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}



	public String getResource1() {
		return resource1;
	}



	public void setResource1(String resource1) {
		this.resource1 = resource1;
	}



	public String getResource2() {
		return resource2;
	}



	public void setResource2(String resource2) {
		this.resource2 = resource2;
	}
	
	

}

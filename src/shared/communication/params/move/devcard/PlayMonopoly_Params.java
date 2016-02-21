package shared.communication.params.move.devcard;

import shared.definitions.ResourceType;

public class PlayMonopoly_Params {

	private int playerIndex;
	private String resource;

	public PlayMonopoly_Params(int playerIndex, ResourceType type) {
		this.playerIndex = playerIndex;

		switch (type){

		case BRICK: resource = "Brick";
		break;

		case ORE: resource = "Ore";
		break;

		case SHEEP: resource = "Sheep";
		break;

		case WHEAT: resource = "Wheat";
		break;

		case WOOD: resource = "Wood";
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

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}



}

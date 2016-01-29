package shared.model.items;

import shared.model.board.Vertex;
import shared.model.player.Player;

public class Municipal {

	private Player player;
	private Vertex vertex;
	
	public Municipal() {
		// Figure out how to make a constructor that is inherited by children
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}


}

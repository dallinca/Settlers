package shared.model.items;

import shared.model.board.Edge;
import shared.model.player.Player;

public class Road {
	
	private Edge edge;
	private Player player;
	
	public Road(Player player) {
		this.player = player;
	}

	public Edge getEdge() {
		return edge;
	}

	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public Player getPlayer() {
		return player;
	}

	
}

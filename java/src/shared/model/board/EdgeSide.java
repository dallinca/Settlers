package shared.model.board;

public class EdgeSide {
	
	private Vertex vertex;
	private Edge[] edges;
	
	public EdgeSide(Vertex vertex, Edge edge1, Edge edge2) {
		this.vertex = vertex;
		edges = new Edge[2];
		edges[0] = edge1;
		edges[1] = edge2;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public Edge[] getEdges() {
		return edges;
	}

	
}

package shared.model.board.exceptions;

@SuppressWarnings("serial")
public class PlaceRoadOnEdgeException extends Exception {
	public PlaceRoadOnEdgeException(String message) {
        super(message);
    }
}

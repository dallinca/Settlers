package shared.model.player.exceptions;

@SuppressWarnings("serial")
public class AllPiecesPlayedException extends Exception {
    public AllPiecesPlayedException(String message) {
        super(message);
    }
}
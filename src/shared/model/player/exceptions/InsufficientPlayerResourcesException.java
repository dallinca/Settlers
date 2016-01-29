package shared.model.player.exceptions;

@SuppressWarnings("serial")
public class InsufficientPlayerResourcesException extends Exception {
    public InsufficientPlayerResourcesException(String message) {
        super(message);
    }
}

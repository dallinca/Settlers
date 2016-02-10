package shared.model.player.exceptions;

@SuppressWarnings("serial")
public class CannotBuyException extends Exception {
    public CannotBuyException(String message) {
        super(message);
    }
}

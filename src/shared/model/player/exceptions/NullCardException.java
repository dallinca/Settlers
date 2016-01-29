package shared.model.player.exceptions;

@SuppressWarnings("serial")
public class NullCardException extends Exception {
    public NullCardException(String message) {
        super(message);
    }
}
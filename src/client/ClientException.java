package client;

@SuppressWarnings("serial")
/**
 * An exception thrown by client when it encounters an error.
 *
 */
public class ClientException extends Exception {

	public ClientException() {
		return;
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable throwable) {
		super(throwable);
	}

	public ClientException(String message, Throwable throwable) {
		super(message, throwable);
	}

}


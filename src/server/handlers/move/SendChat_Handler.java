package server.handlers.move;

import java.io.IOException;

import server.handlers.SettlersOfCatanHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to send a chat message
 */
public class SendChat_Handler extends SettlersOfCatanHandler {

	/**
	 * Receives the SendChat Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}

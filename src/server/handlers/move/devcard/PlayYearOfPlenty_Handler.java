package server.handlers.move.devcard;

import java.io.IOException;

import server.handlers.SettlersOfCatanHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


/**
 * Handles calls from a client to Play a soldier development card
 */
public class PlayYearOfPlenty_Handler extends SettlersOfCatanHandler{
	
	/**
	 * Handles the PlayYearOfPlenty Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}

package server.handlers.move.devcard;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to Play a road building development card
 */
public class PlayRoadBuilding_Handler implements HttpHandler{

	
	/**
	 * Handles the PlayRoadBuilding Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}

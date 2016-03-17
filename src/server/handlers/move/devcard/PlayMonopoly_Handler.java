package server.handlers.move.devcard;

import java.io.IOException;

import server.handlers.SettlersOfCatanHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


/**
 * Handles calls from a client to Play a monopoly development card 
**/
public class PlayMonopoly_Handler extends SettlersOfCatanHandler{
	
	/**
	 * Handles the PlayMonopoly Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
		
		Command playMonopoly = new Command();
	}

}

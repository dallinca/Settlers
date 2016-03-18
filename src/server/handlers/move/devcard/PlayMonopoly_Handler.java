package server.handlers.move.devcard;

import java.io.IOException;
import java.util.logging.Logger;

import server.commands.move.devcard.PlayMonopoly_Command;
import server.handlers.SettlersOfCatanHandler;
import shared.communication.params.move.devcard.PlayMonopoly_Params;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


/**
 * Handles calls from a client to Play a monopoly development card 
**/
public class PlayMonopoly_Handler extends SettlersOfCatanHandler{
	
	private Logger logger = Logger.getLogger("settlers-of-catan");
	
	/**
	 * Handles the PlayMonopoly Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
		String job = getExchangeBody(exchange);
		
		PlayMonopoly_Params theParams = gson.fromJson(job, PlayMonopoly_Params.class);
		PlayMonopoly_Command monopolyCommand = new PlayMonopoly_Command(theParams);
		
		//Check the cookie
		
		monopolyCommand.execute();
	}

}

package server.handlers.nonmove;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.handlers.SettlersOfCatanHandler;
import shared.communication.params.nonmove.Create_Params;
import shared.communication.results.nonmove.Create_Result;

import com.sun.net.httpserver.HttpExchange;

/**
 * Handles calls from a client to create a game
 */
public class CreateGame_Handler extends SettlersOfCatanHandler{
	
	private Logger logger = Logger.getLogger("settlers-of-catan"); 	
	
	/**
	 * Receives the Create Params to create a game and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		logger.entering("server.handlers.CreateHandler", "handle");
		//Handling Login http exchange.

		String job;	
		Create_Params request;
		Create_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		String check = validateCookies(cookies);

		if (check.equals("VALID")){

			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, Create_Params.class); //deserialize request from json		
			result = facade.create(request);//Call facade to perform operation with request
			
			writeResult(exchange, result);

		}else{

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //Cookies invalid

		}			

		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.CreateHandler", "handle");		
		
	}

}

package server.handlers.nonmove;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
import shared.communication.params.nonmove.List_Params;
import shared.communication.params.nonmove.Login_Params;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.nonmove.List_Result;
import shared.communication.results.nonmove.Register_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to list the games
 */
public class List_Handler extends SettlersOfCatanHandler {
	private Logger logger = Logger.getLogger("settlers-of-catan"); 	
	/**
	 * Receives the List games Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		logger.entering("server.handlers.ListHandler", "handle");
		//Handling Login http exchange.

		String job;	
		List_Params request;
		List_Result result;
		
		LinkedList<String> cookies = extractCookies(exchange);
		
		User user = gson.fromJson(cookies.getFirst(), User.class);		
		
		if (facade.validateUser(user)){
			
			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, List_Params.class); //deserialize request from json		
			result = facade.list(request);//Call facade to perform operation with request
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
			job = gson.toJson(result);	//serialize result to json
			
			OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
			sw.write(job);//Write result to stream.
			sw.flush();	
			
		}else{
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid
			
		}			

		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.ListHandler", "handle");			
	}

}

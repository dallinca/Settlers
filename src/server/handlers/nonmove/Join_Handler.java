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
import shared.communication.params.nonmove.Join_Params;
import shared.communication.params.nonmove.List_Params;
import shared.communication.results.nonmove.Join_Result;
import shared.communication.results.nonmove.List_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to join a game
 */
public class Join_Handler extends SettlersOfCatanHandler {

	private Logger logger = Logger.getLogger("settlers-of-catan"); 	

	/**
	 * Receives the Join_Handler Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("Handling join");
		
		logger.entering("server.handlers.JoinHandler", "handle");
		//Handling Login http exchange.

		String job;	
		Join_Params request;
		Join_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		User user = gson.fromJson(cookies.getFirst(), User.class);		

		if (facade.validateUser(user)){

			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, Join_Params.class); //deserialize request from json		
			result = facade.join(request, user.getPlayerID());//Call facade to perform operation with request

			if (result.isValid()){ //Set game cookie in response
				Map<String, List<String>> headers = exchange.getResponseHeaders();
				List<String> gameCookie = new LinkedList<String>();
				gameCookie.add(result.getGameCookie());
				headers.put("Set-cookie", gameCookie);


				job = "Success";	//serialize result to json
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
			}
			else{ 

				job = "Failure";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //Game invalid
			}			

			OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
			sw.write(job);//Write result to stream.
			sw.flush();	

		}else{

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid

		}			

		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.JoinHandler", "handle");
	}
}

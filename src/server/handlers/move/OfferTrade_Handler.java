package server.handlers.move;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.commands.move.OfferTrade_Command;
import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
import shared.communication.params.move.OfferTrade_Params;
import shared.communication.results.move.OfferTrade_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to offer a trade
 */
public class OfferTrade_Handler extends SettlersOfCatanHandler{

	//private Logger logger = Logger.getLogger("settlers-of-catan"); 	
	/**
	 * Receives the OfferTrade Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//logger.entering("server.handlers.OfferTrade", "handle");
		//Handling Login http exchange.

		String job;	
		OfferTrade_Params request;
		OfferTrade_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		String check = validateCookies(cookies);		

		if (check.equals("VALID")){

			User user = gson.fromJson(cookies.getFirst(), User.class);	
			int gameID = Integer.parseInt(cookies.get(1));

			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, OfferTrade_Params.class); //deserialize request from json

			OfferTrade_Command command = new OfferTrade_Command(request, gameID, user.getPlayerID());

			command.execute();//Execute the command	

			result = command.getResult(); //Get result from command			

			if (result.isValid()){
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
				job = gson.toJson(result);	//serialize result to json	
			}else{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				job = "COMMAND FAILURE";	
			}			
		}else{
			job = check;			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid			
		}		

		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();	

		exchange.getResponseBody().close();		
		//logger.exiting("server.handlers.OfferTrade", "handle");	
		
	}

}

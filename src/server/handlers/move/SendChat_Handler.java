package server.handlers.move;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.commands.Command;
import server.commands.move.SendChat_Command;
import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
import shared.communication.params.move.SendChat_Params;
import shared.communication.params.nonmove.List_Params;
import shared.communication.results.move.SendChat_Result;
import shared.communication.results.nonmove.List_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to send a chat message
 */
public class SendChat_Handler extends SettlersOfCatanHandler {

	private Logger logger = Logger.getLogger("settlers-of-catan"); 	
	
	/**
	 * Receives the SendChat Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
			
		logger.entering("server.handlers.SendChat", "handle");
		//Handling Login http exchange.

		String job;	
		SendChat_Params request;
		SendChat_Result result;
		
		LinkedList<String> cookies = extractCookies(exchange);
		
		String check = validateCookies(cookies);		
		
		if (check.equals("VALID")){
			
			User user = gson.fromJson(cookies.getFirst(), User.class);	
			int gameID = Integer.parseInt(cookies.get(1));
			
			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, SendChat_Params.class); //deserialize request from json				
			SendChat_Command command = new SendChat_Command(request, gameID, user.getPlayerID());
			
			command
			
				
			result = facade.list(request);//Call facade to perform operation with request
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
			job = gson.toJson(result);	//serialize result to json			
			
		}else{
			job = check;			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid			
		}		
		
		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();	

		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.SendChat", "handle");	
		
		
		
		
		
		
		
		
	}

}

package server.handlers.move;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.commands.move.RobPlayer_Command;
import server.commands.move.RollNumber_Command;
import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
import shared.communication.params.move.RobPlayer_Params;
import shared.communication.results.move.RobPlayer_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to rob a player
 */
public class RobPlayer_Handler extends SettlersOfCatanHandler {

	//private Logger logger = Logger.getLogger("settlers-of-catan"); 	
	/**
	 * Receives the RobPlayer Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//logger.entering("server.handlers.RobPlayer", "handle");
		//Handling Login http exchange.

		String job;	
		RobPlayer_Params request;
		RobPlayer_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		String check = validateCookies(cookies);		
		
		swaggerize(exchange);

		if (check.equals("VALID")){

			User user = gson.fromJson(cookies.getFirst(), User.class);	
			int gameID = Integer.parseInt(cookies.get(1));

			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, RobPlayer_Params.class); //deserialize request from json

			RobPlayer_Command command = new RobPlayer_Command(request, gameID, user.getPlayerID());

			command.execute();//Execute the command	

			result = command.getResult(); //Get result from command			

			if (result.isValid()){
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
				job = gson.toJson(result.getModel());	//serialize result to json	
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
		//logger.exiting("server.handlers.RobPlayer", "handle");	
		
	}

	
}

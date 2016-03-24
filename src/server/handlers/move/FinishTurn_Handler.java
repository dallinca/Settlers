package server.handlers.move;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.commands.move.FinishTurn_Command;
import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
import shared.communication.params.move.FinishTurn_Params;
import shared.communication.results.move.FinishTurn_Result;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to finish a players turn
 */
public class FinishTurn_Handler extends SettlersOfCatanHandler{

	//private Logger logger = Logger.getLogger("settlers-of-catan"); 
	/**
	 * Receives the DiscardCards Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//logger.entering("server.handlers.FinishTurn", "handle");
		//Handling Login http exchange.

		System.out.println("Finish turn handler");
		
		String job;	
		FinishTurn_Params request;
		FinishTurn_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		String check = validateCookies(cookies);		

		if (check.equals("VALID")){

			System.out.println("Finish turn check is valid");
			User user = gson.fromJson(cookies.getFirst(), User.class);	
			int gameID = Integer.parseInt(cookies.get(1));

			job = getExchangeBody(exchange); //get json string from exchange.
			
			request = gson.fromJson(job, FinishTurn_Params.class); //deserialize request from json
			System.out.println("Creating command");
			FinishTurn_Command command = new FinishTurn_Command(request, gameID, user.getPlayerID());
			System.out.println("Executing command");
			command.execute();//Execute the command	
			System.out.println("Command executed");
			result = command.getResult(); //Get result from command			

			if (result.isValid()){
				System.out.println("Finish turn result is valid");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
				job = gson.toJson(result.getModel());	//serialize result to json	
			}else{
				System.out.println("Command failure");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				job = "COMMAND FAILURE";	
			}			
		}else{
			System.out.println("invalid user");
			job = check;			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid			
		}		

		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();	

		exchange.getResponseBody().close();		
		//logger.exiting("server.handlers.FinishTurn", "handle");	
		
	}

}

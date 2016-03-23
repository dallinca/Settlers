package server.handlers.nonmove;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
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
		System.out.println("Handling create game");

		logger.entering("server.handlers.CreateHandler", "handle");
		//Handling Login http exchange.

		String job;	
		Create_Params request;
		Create_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		User user = gson.fromJson(cookies.getFirst(), User.class);	
		System.out.println("Temp handler user created.");

		if (facade.validateUser(user)){
			System.out.println("User is valid.");

			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, Create_Params.class); //deserialize request from json		
						
			result = facade.create(request, user.getPlayerID());//Call facade to perform operation with request

			if (result.isValid()){
				System.out.println("Create success.");
				job = gson.toJson(result);	//serialize result to json			
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);				
			}
			else {
				System.out.println("Create failed.");
				job = "Failed";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //Cookies invalid
			}

		}else{
			System.out.println("Invalid user.");
			job = "Failed";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //Cookies invalid
		}			

		System.out.println("Writing response.");
		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();		

		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.CreateHandler", "handle");		

	}

}

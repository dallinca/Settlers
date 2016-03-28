package server.handlers.move;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.commands.move.BuildCity_Command;
import server.commands.move.BuildRoad_Command;
import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
import shared.communication.params.move.BuildCity_Params;
import shared.communication.params.move.BuildRoad_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.move.BuildCity_Result;
import shared.communication.results.move.BuildRoad_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to build a road
 */
public class BuildRoad_Handler extends SettlersOfCatanHandler{
	
	//private Logger logger = Logger.getLogger("settlers-of-catan");
	
	/**
	 * Receives the BuildRoad Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
	//	logger.entering("server.handlers.BuildRoad", "handle");
		//Handling build road http exchange.
		
		System.out.println("Build road handler");

		String job;	
		BuildRoad_Params request;
		BuildRoad_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		String check = validateCookies(cookies);
		
		swaggerize(exchange);
		
		if (check.equals("VALID")){
			
			System.out.println("Valid credentials");

			User user = gson.fromJson(cookies.getFirst(), User.class);	
			int gameID = Integer.parseInt(cookies.get(1));

			job = getExchangeBody(exchange); //get json string from exchange.
			
			request = gson.fromJson(job, BuildRoad_Params.class); //deserialize request from json

			System.out.println("Creating and executing command");
			BuildRoad_Command command = new BuildRoad_Command(request, gameID, user.getPlayerID());

			command.execute();//Execute the command	

			result = command.getResult(); //Get result from command			

			if (result.isValid()){
				System.out.println("Valid build road result.");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
				
				ClientModel cm = result.getModel();
				job = gson.toJson(cm);	//serialize result to json	
			}else{
				System.out.println("Build road result invalid.");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				job = "COMMAND FAILURE";	
			}			
		}else{
			System.out.println("Invalid user.");
			job = check;			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid			
		}		

		
		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();	
		
		System.out.println("Returning to client");

		exchange.getResponseBody().close();		
		//logger.exiting("server.handlers.BuildRoad", "handle");
		
	}

}

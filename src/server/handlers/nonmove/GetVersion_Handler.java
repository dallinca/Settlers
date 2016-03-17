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
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.params.nonmove.Join_Params;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.communication.results.nonmove.Join_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to get the version number
 */
public class GetVersion_Handler extends SettlersOfCatanHandler {

	private Logger logger = Logger.getLogger("settlers-of-catan"); 	

	/**
	 * Receives the GetVersion Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		logger.entering("server.handlers.JoinHandler", "handle");
		//Handling Login http exchange.

		String job;	
		GetVersion_Params request;
		GetVersion_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		String check = validateCookies(cookies);

		if (check.equals("VALID")){

			job = getExchangeBody(exchange); //get json string from exchange.
			request = gson.fromJson(job, GetVersion_Params.class); //deserialize request from json		
			result = facade.model(request);//Call facade to perform operation with request
			
			writeResult(exchange, result.getClass(), result);

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
			job = gson.toJson(result);	//serialize result to json

			OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
			sw.write(job);//Write result to stream.
			sw.flush();					


		}else{

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //Cookies invalid

		}			

		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.JoinHandler", "handle");

	}

}

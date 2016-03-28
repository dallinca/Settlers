package server.handlers.nonmove;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import server.handlers.SettlersOfCatanHandler;
import shared.communication.params.nonmove.Login_Params;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.nonmove.Login_Result;
import shared.communication.results.nonmove.Register_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


/**
 * Handles calls from a client to register a user
 */
public class Register_Handler extends SettlersOfCatanHandler {

//	private Logger logger = Logger.getLogger("settlers-of-catan"); 	
	/**
	 * Receives the Register Params and constructs the appropriate 
	 * command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("Handling register");
		//logger.entering("server.handlers.RegisterHandler", "handle");
		//Handling Login http exchange.

		String job;	
		Register_Params request;
		Register_Result result;

		job = getExchangeBody(exchange); //get json string from exchange.
		request = gson.fromJson(job, Register_Params.class); //deserialize request from json		
		result = facade.register(request);//Call facade to perform operation with request
		
		swaggerize(exchange);

		if (result.isValid()){ //Set user cookie in response
			System.out.println("Register result is valid");
			Map<String, List<String>> headers = exchange.getResponseHeaders();
			List<String> cookieList = new LinkedList<String>();			
			cookieList.add(result.getUserCookie());
			headers.put("Set-cookie", cookieList);
			job = "Success";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); 			
		}else{
			System.out.println("Register failed!");
			job = "Failure";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //Everything's okay
		}

		System.out.println("Returning register information.");

		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();		

		exchange.getResponseBody().close();		
	//	logger.exiting("server.handlers.RegisterHandler", "handle");		
	}

}

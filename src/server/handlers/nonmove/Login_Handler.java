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
import shared.communication.results.nonmove.Login_Result;

import com.sun.net.httpserver.HttpExchange;
/**
 * Handles calls from a client to validate the current user.
 * @author jchrisw
 *
 */
public class Login_Handler extends SettlersOfCatanHandler{

	private Logger logger = Logger.getLogger("settlers-of-catan"); 	

	/**
	 * Receives the Login Params and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("Handling login");
		
		logger.entering("server.handlers.LoginHandler", "handle");
		//Handling Login http exchange.

		String job;	
		Login_Params request;
		Login_Result result;

		job = getExchangeBody(exchange); //get json string from exchange.
		request = gson.fromJson(job, Login_Params.class); //deserialize request from json		
		result = facade.login(request);//Call facade to perform operation with request

		if (result.isValid()) {
			System.out.println("Login Valid");
			Map<String, List<String>> headers = exchange.getResponseHeaders();
			List<String> cookieList = new LinkedList<String>();
			cookieList.add(result.getUserCookie());
			headers.put("Set-cookie", cookieList);
			job = "Success";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
		} else {
			System.out.println("Login Not Valid");
			job = "Failure";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //Everything's okay
		}		

		System.out.println("Returning login information.");
		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();		

		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.LoginHandler", "handle");

	}
}

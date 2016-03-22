package server.handlers.nonmove;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.logging.Logger;

import server.handlers.SettlersOfCatanHandler;
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.results.ClientModel;
import shared.communication.results.nonmove.GetVersion_Result;

import com.sun.net.httpserver.HttpExchange;

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
		System.out.println("Handling get version");
		
		logger.entering("server.handlers.GetVersion", "handle");
		//Handling Login http exchange.

		String job;	
		GetVersion_Params request;
		GetVersion_Result result;

		LinkedList<String> cookies = extractCookies(exchange);

		String check = validateCookies(cookies);		

		if (check.equals("VALID")){

			request = new GetVersion_Params();			
			job = exchange.getRequestURI().toString();			
			job = job.substring(job.lastIndexOf('=') + 1);//Get version number from URI

			request.setVersionNumber(Integer.parseInt(job));
			result = facade.model(request);
	
			if (result.isValid()){
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay
				
				if (result.isUpToDate()){
					job = "True";
				}
				else{
					ClientModel cm = result.getModel();
					job = gson.toJson(cm);	//serialize result to json	
				}
				
			}else{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				job = "MODEL REQUEST FAILURE";	
			}			
		}else{
			job = check;			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid			
		}		

		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();	

		exchange.getResponseBody().close();				
		logger.exiting("server.handlers.GetVersion", "handle");

	}

}

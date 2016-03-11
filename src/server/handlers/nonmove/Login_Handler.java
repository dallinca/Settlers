package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.ServerException;
import server.facade.ServerFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Handles calls from a client to validate the current user.
 * @author jchrisw
 *
 */
public class Login_Handler implements HttpHandler{
	
	private Logger logger = Logger.getLogger("record-indexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.entering("server.handlers.ValidateUserHandler", "handle");
		//Handling Validate User http exchange.
		
		ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
		ValidateUser_Result result = null;
		
		try {
			result = ServerFacade.ValidateUserCall(params);
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();		
		logger.exiting("server.handlers.ValidateUserHandler", "handle");
	}

}

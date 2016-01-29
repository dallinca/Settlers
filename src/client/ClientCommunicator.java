package client;

import shared.communication.params.*;
import shared.communication.results.*;

/**
 * A class which packages data from a client and sends it through the internet to the server.
 * Receives results from the server.
 * @author jchrisw
 *
 */

public class ClientCommunicator {

	private static String SERVER_HOST;
	private static int SERVER_PORT;
	private static String URL_PREFIX;
	private static final String HTTP_POST = "POST";

	/**
	 * 
	 * @param serverHost Name of server
	 * @param serverPort Port number on which to connect to server.
	 * 
	 * @pre serverPort and serverHost specify an existing server.
	 * @post The client communicator will know how to communicate with the server.
	 */
	public ClientCommunicator(String serverHost, int serverPort){
		SERVER_HOST = serverHost;
		SERVER_PORT = serverPort;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}

	public ClientCommunicator(){
		SERVER_HOST = "localhost";
		SERVER_PORT = 39640;				
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}		

	/**
	 * Packages a validate user request from the client.
	 * 
	 * @param username A string username
	 * @param password A string password
	 * @return ValidateUser_Result
	 * @throws ClientException 
	 * 
	 * @pre none
	 * @post ValidateUser response will be obtained from server.
	 */

	public ValidateUser_Result validateUser(String username, String password) throws ClientException	
	{	
		return (ValidateUser_Result)doPost("/ValidateUser", new ValidateUser_Params(username, password));
	}
	
	
	/**
	 * Packages a poll server request from the client.
	 * 
	 * @return
	 * @throws ClientException
	 * 
	 * @pre None
	 * @post PollServer response will be obtained from server.
	 */
	public PollServer_Result pollServer() throws ClientException
	{
		return (PollServer_Result)doPost("/PollServer", new PollServer_Params());
	}

	/**
	 * 	Posts data to the server for operation and receives response object.	 
	 * 
	 * @param url Location to be posted to
	 * @param request Object to be sent by post
	 * @return result object from post.
	 * @throws ClientException 
	 * 
	 * @pre URL belongs to the correct server, and request is a known communication object.
	 * @post server will perform specified operation on given object.
	 */

	public Object doPost(String urlString, Object request) throws ClientException
	{
		Object result = null;

		return result;		
	}


}
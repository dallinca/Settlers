package client;

import shared.communication.results.*;

/**
 * Sends all to-server requests to the client communicator for packaging
 * Interprets responses, handles errors from requests when responses are invalid.
 * 
 * @author jchrisw
 *
 */
public class ClientFacade {

	private ClientCommunicator cc;

	/**
	 * Creates fascade, specifying the location of the master server.
	 * @param serverName
	 * @param portNumber
	 * 
	 * @pre Server name and port number specifiy an existing server.
	 * @post Client will be able to communicate with server.
	 */
	ClientFacade(String serverName, int portNumber){
		cc = new ClientCommunicator(serverName, portNumber);
	}

	ClientFacade(){		
		cc = new ClientCommunicator();				
	}

	/**
	 * Gets the current game state from the server.
	 * @param username
	 * @return
	 * 
	 * @pre Client is validated and participating in a game.
	 * @post Communicator will return usable PollServer_Result.
	 */
	public PollServer_Result pollServer(String username){

		PollServer_Result result;

		try {
			
			result = cc.pollServer();
			
		} catch (ClientException e) {
			
			result = new PollServer_Result();
			
			e.printStackTrace();			
		}

		return result;		
	}

	/**
	 * Validates the given user with the server database.
	 * @param username
	 * @param password
	 * @return
	 * 
	 * @pre None
	 * @post Communicator will return usable ValidateUser_Result
	 */
	public ValidateUser_Result validateUser(String username, String password) {		

		ValidateUser_Result result; 

		try {

			result = cc.validateUser(username, password);

		} catch (ClientException e) {			
			result = new ValidateUser_Result();

			e.printStackTrace();
		}		

		return result;
	}
	
	/**
	 * Parses JSON data and maps it onto client model.
	 * 
	 * @pre Given JSONdata from server
	 * @post Data will be mapped onto client model.
	 */
	public void parseJSONData(Object JSONdata){
				
	}
	


}
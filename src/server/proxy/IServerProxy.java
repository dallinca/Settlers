package server.proxy;

import shared.communication.results.*;
import shared.communication.params.*;

/**
 * Interface for communicating with server.
 *
 */
public interface IServerProxy {
	/**
	 *@param request
	 *@return
	 * 
	 *@pre Request is not null.
	 *@post ValidateUser_Result will be returned.
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params request);
	
	/**
	 * 
	 *@param request
	 *@return
	 * 
	 *@pre Request is not null.
	 *@post PollServer_Result will be returned.
	 */
	public PollServer_Result pollRequest(PollServer_Params request);
	
	/**
	 * 
	 *@param request
	 *@return 
	 * 
	 *@pre Request is not null.
	 *@post JoinGame_Result will be returned.
	 */
	public JoinGame_Result joinGame(JoinGame_Params request);
	
	/**
	 * 
	 *@param request
	 *@return
	 * 
	 *@pre Request is not null.
	 *@post PerformAction_Result will be returned.
	 */
	public PerformAction_Result doAction(PerformAction_Params request);
	
	/**
	 * 
	 *@param request
	 *@return
	 *
	 *@pre Request is not null.
	 *@post ExitGame_Result will be returned.
	 */
	public ExitGame_Result exitGame(ExitGame_Params request);
}

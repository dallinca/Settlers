package server.proxy;

import shared.communication.params.ExitGame_Params;
import shared.communication.params.JoinGame_Params;
import shared.communication.params.PerformAction_Params;
import shared.communication.params.PollServer_Params;
import shared.communication.params.ValidateUser_Params;
import shared.communication.results.ExitGame_Result;
import shared.communication.results.JoinGame_Result;
import shared.communication.results.PerformAction_Result;
import shared.communication.results.PollServer_Result;
import shared.communication.results.ValidateUser_Result;

/**
 * 
 * Communication object for server.
 * 
 */
public class ServerProxy implements IServerProxy {

	@Override
	public ValidateUser_Result validateUser(ValidateUser_Params request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PollServer_Result pollRequest(PollServer_Params request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JoinGame_Result joinGame(JoinGame_Params request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PerformAction_Result doAction(PerformAction_Params request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExitGame_Result exitGame(ExitGame_Params request) {
		// TODO Auto-generated method stub
		return null;
	}

}

package client;

import client.proxy.IServerProxy;
import client.proxy.MockServerProxy;
import shared.model.Game;

public class Client {
	
	private Game game;
	private IServerProxy isp;
	private ClientFacade cf;
	
	public Client() {
		
	}
	
	public Client(IServerProxy isp){
		
		this.isp = isp;
		
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	
}

package server.database;

import java.util.List;

import server.commands.Command;
import shared.communication.User;
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.params.nonmove.Join_Params;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.communication.results.nonmove.Join_Result;
import shared.model.Game;

abstract public interface GameDAOInterface {

	public boolean create(Game g);
	public Game update(Game g);
	public boolean validateGame(User user, int gameID);
	public Game getGame(int gameID);
	public Join_Result join(int gameID);
	public Game read(int gameID);
	public boolean delete(Game game);
	public List<Game> getGames();
	public void joinPlayer(int gameID, int userID);
	public void storeCommand(int gameID, Command command);
	
}
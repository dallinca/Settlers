package server.database;

import java.sql.SQLException;
import java.util.List;

import server.commands.Command;
import shared.communication.User;
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.params.nonmove.Join_Params;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.communication.results.nonmove.Join_Result;
import shared.definitions.CatanColor;
import shared.model.Game;

abstract public interface GameDAOInterface {

	public boolean create(Game g) throws SQLException;
	public Game update(Game g);
	public boolean delete(Game game);
	public List<Game> getGames();
	public void joinPlayer(int gameID, int userID, CatanColor playerColor);
	public void storeCommand(int gameID, Command command);
	public void clearCommands(int gameID);
	public List<Command> getCommands(int gameID);
}

package server.database;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import server.commands.Command;
import shared.communication.User;
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.params.nonmove.Join_Params;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.communication.results.nonmove.Join_Result;
import shared.definitions.CatanColor;
import shared.model.Game;

abstract public interface GameDAOInterface {

	public boolean create(Game game) throws SQLException;
	public boolean update(Game game) throws SQLException;
	public boolean delete(Game game);
	public List<Game> getGames() throws SQLException, DatabaseException;
	public boolean storeCommand(int gameID, Command command) throws SerialException, SQLException;
	public boolean joinPlayer(int gameID, int userID, CatanColor playerColor) throws SQLException;
	public boolean clearCommands(int gameID);
	public List<Command> getCommands(int gameID) throws SQLException;

}

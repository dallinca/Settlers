package server.database;

import java.sql.SQLException;
import java.util.List;

import shared.communication.User;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.nonmove.Register_Result;

abstract public interface UserDAOInterface {

	public boolean create(User user) throws SQLException;
	public boolean clearUsers();
	public List<User> getUsers() throws DatabaseException, SQLException;
	boolean clean() throws SQLException;
	
}

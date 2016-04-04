package server.database;

import shared.communication.User;

abstract public interface UserDAOInterface {

	public boolean validateUser(User user);
	
}

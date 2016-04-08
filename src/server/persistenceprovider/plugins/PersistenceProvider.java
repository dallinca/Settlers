package server.persistenceprovider.plugins;

import server.commands.Command;
import server.database.GameDAO;
import server.database.GameDAOInterface;
import server.database.UserDAO;
import server.database.UserDAOInterface;
import server.persistenceprovider.PersistenceProviderInterface;

public class PersistenceProvider implements PersistenceProviderInterface{
	
	UserDAO userDAO;
	GameDAO gameDAO;
	
	/**
	 * Creates a new persistence provider.
	 * @param users
	 * @param games
	 */
	PersistenceProvider(UserDAO users, GameDAO games){
		gameDAO = new GameDAO();
		userDAO = new UserDAO();		
	}
	
	/**
	 * Begins a database transaction
	 */
	public void startTransaction(){
		
		return;
	}
	
	/**
	 * Ends a database transaction
	 */
	public void endTransaction(){
		
		return;
	}
	
	/**
	 * Stores a command for a game.
	 * @param command
	 */
	public void storeCommand(Command command){
		
		
		return;
	}
	
	/**
	 * Imports a database from a given file.
	 * @param file
	 */
	public void importFromFile(String file){
		
		return;
	}
	
	/**
	 * Saves the current database to a file.
	 * @param file
	 */
	public void saveToFile(String file){
		
		return;
	}
	
	/**
	 * Clears out the current database.
	 */
	public void clearDatabase(){
		return;
	}

	/**
	 * Returns persistence provider's user DAO.
	 * @return
	 */
	@Override
	public UserDAOInterface getUserDAO() {
		return userDAO;
	}

	/**
	 * Returns persistence provider's game DAO.
	 * @return
	 */
	@Override
	public GameDAOInterface getGameDAO() {
		return gameDAO;
	}

}

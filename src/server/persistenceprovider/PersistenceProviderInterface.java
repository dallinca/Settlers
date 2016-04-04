package server.persistenceprovider;

import server.commands.Command;
import server.database.GameDAO;
import server.database.UserDAO;

/**
 * Interface for Persistence Provider plugins.
 * 
 * 
 * @author jchrisw
 *
 */
public abstract interface PersistenceProviderInterface  {
	
	/**
	 * Returns persistence provider's user DAO.
	 * @return
	 */
	abstract UserDAO getUserDAO();
	
	/**
	 * Returns persistence provider's game DAO.
	 * @return
	 */
	abstract GameDAO getGameDAO() ;
	
	/**
	 * Begins a database transaction
	 */
	abstract void startTransaction();	
	
	/**
	 * Ends a database transaction
	 */
	abstract void endTransaction();
	
	/**
	 * Stores a command for a game.
	 * @param command
	 */
	abstract void storeCommand(Command command);
	
	/**
	 * Imports a database from a given file.
	 * @param file
	 */
	abstract void importFromFile(String file);
	
	/**
	 * Saves the current database to a file.
	 * @param file
	 */
	abstract void saveToFile(String file);
	
	/**
	 * Clears out the current database.
	 */
	abstract void clearDatabase();
	

}

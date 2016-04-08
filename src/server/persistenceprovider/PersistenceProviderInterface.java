package server.persistenceprovider;

import server.commands.Command;
import server.database.DatabaseException;
import server.database.GameDAO;
import server.database.GameDAOInterface;
import server.database.UserDAO;
import server.database.UserDAOInterface;

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
	abstract UserDAOInterface getUserDAO();
	
	/**
	 * Returns persistence provider's game DAO.
	 * @return
	 */
	abstract GameDAOInterface getGameDAO() ;
	
	/**
	 * Begins a database transaction
	 * @throws DatabaseException 
	 */
	abstract void startTransaction() throws DatabaseException;	
	
	/**
	 * Ends a database transaction
	 */
	abstract void endTransaction(boolean commit);
	
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

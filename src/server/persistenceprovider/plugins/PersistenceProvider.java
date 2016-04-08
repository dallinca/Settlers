package server.persistenceprovider.plugins;

import server.commands.Command;
import server.database.DatabaseAccess;
import server.database.DatabaseException;
import server.database.GameDAO;
import server.database.GameDAOInterface;
import server.database.UserDAO;
import server.database.UserDAOInterface;
import server.persistenceprovider.PersistenceProviderInterface;

public class PersistenceProvider implements PersistenceProviderInterface{
	
	UserDAO userDAO;
	GameDAO gameDAO;
	DatabaseAccess DA;
	
	/**
	 * Creates a new persistence provider.
	 * @param users
	 * @param games
	 */
	public PersistenceProvider(DatabaseAccess DA){
				
		this.DA = DA;
		gameDAO = new GameDAO(DA);
		userDAO = new UserDAO(DA);		
	}
	
	/**
	 * Begins a database transaction
	 * @throws DatabaseException 
	 */
	public void startTransaction() throws DatabaseException{
		DA.startTransaction();
		return;
	}
	
	/**
	 * Ends a database transaction
	 */
	public void endTransaction(boolean commit){
		DA.endTransaction(commit);
		return;
	}
/*	
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
		try {
			DA.setupDatabase();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
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

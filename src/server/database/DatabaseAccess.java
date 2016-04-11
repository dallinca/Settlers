package server.database;

import java.io.*;
import java.sql.*;

public class DatabaseAccess {

	public static DatabaseAccess singleton = null;


	public static DatabaseAccess getInstance(){

		if (singleton == null){

			singleton = new DatabaseAccess();

		}

		return singleton;
	}


	private DatabaseAccess(){

		connection = null;
	}

	private static final String DATABASE_DIRECTORY = "Database";
	private static final String DATABASE_FILE = "Database.sqlite";
	/*private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
			File.separator + DATABASE_FILE;*/
	
	private static final String DATABASE_URL = "jdbc:sqlite:Database/Database.sqlite";

	private Connection connection;

	public static void initialize() throws DatabaseException {
		System.out.println("DatabaseAccess :: initialize");

		if (singleton==null){

			try {
				final String driver = "org.sqlite.JDBC";
				Class.forName(driver);
			}
			catch(ClassNotFoundException e) {
				DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
				throw serverEx; 
			}
		}

	}

	/**
	 * TODO this needs to be called to setup the db ... and stuff
	 * 
	 */
	public void setupDatabase() throws DatabaseException {
		System.out.println("Database Setup");
		try {
			startTransaction();
			
			Statement stat = connection.createStatement();

			/*ResultSet rs = stat.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Users'");
			
			if(rs.next()){
				rs.close();
				stat.close();
				endTransaction(true);			
				System.out.println("Data tables already exist, exiting.");
				return;
			}*/
			
			System.out.println("Creating data tables");
			
			stat.executeUpdate("drop table if exists Users;");
			stat.executeUpdate("create table Users(userID INTEGER PRIMARY KEY,			" +
					"				   username TEXT,						" +
					"				   password TEXT)");

			stat.executeUpdate("drop table if exists Games;");
			stat.executeUpdate("create table Games(gameID INTEGER PRIMARY KEY,			" +
					"				   game TEXT,							" +
					"				   commands TEXT)");

			endTransaction(true);
		} catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public void startTransaction() throws DatabaseException {	
		System.out.println("DatabaseAccess :: start transaction");

		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL); 
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
					DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}

	public void endTransaction(boolean commit) {
		System.out.println("DatabaseAccess :: end transaction");

		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {

				e.printStackTrace();
			}
			finally {
				connection = null;
			}
		}
	}
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}

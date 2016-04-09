package server.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import server.commands.Command;
import shared.communication.*;
import shared.communication.results.nonmove.Join_Result;
import shared.model.Game;

public class GameDAO implements GameDAOInterface {

	DatabaseAccess db;

	/**
	 * Constructor
	 * takes in a database object
	 */
	public GameDAO(DatabaseAccess db) {
		this.db = db;
	}

	/**
	 * Inserts a new game object into the database
	 * @pre data in the game object is correct
	 * @return a boolean representing whether or not the create was successful
	 * @throws SQLException 
	 */
	@Override
	public boolean create(Game game) throws SQLException { //throws SQLException{
		System.out.println("GameDAO create()");

		PreparedStatement stmt = null;

		try {

			String sql = "INSERT INTO Games (gameID, game, commands) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(sql);
			
			stmt.setInt(1, game.getGameID());
			
			Blob gameBlob;
			ByteArrayOutputStream bos = null;
			
			try {
				
				bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(game);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			byte[] byteArray = bos.toByteArray();

			gameBlob = new SerialBlob(byteArray);

			stmt.setBlob(2, gameBlob);
			
			Blob commandBlob = null; //There exist no commands for a new game. DO NOT TRY TO ADD NONEXISTENT COMMANDS!
			stmt.setBlob(3, commandBlob);

			if (stmt.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * Updates the game object in the database
	 * @pre the given game object is accurately represented
	 * @return The updated game
	 */
	@Override
	public Game update(Game g) { //throws SQLException {
		
		Connection connection = db.getConnection();

		System.out.println("GameDAO update()"); //((Called to save the game))
		
		// Start a transaction
		
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;

		try {
			String sql = "update Games SET game = ?, commands = ? WHERE gameID = ?";

			stmt = connection.prepareStatement(sql);
			stmt.setBlob(1, g /*must be a JSON string first to be stored as a blob*/);
			//stmt.setString(2, g.getGameHistory());
			stmt.setInt(3, g.getGameID());

			if (stmt.executeUpdate() == 1)
				return g;
			else
				System.out.println("Update Game failed.");
		} catch (SQLException e) {

		} finally {
			if (stmt != null)
				stmt.close();
		}
		return g;
		return null;
	}
	/**
	 * Deletes the given corresponding game object from the database
	 * Used to remove the game upon game completion.
	 * @pre the given game is a valid game in the database
	 * @return a boolean depicting whether or not the delete was successful
	 */
	@Override
	public boolean delete(Game game) {
		System.out.println("GameDAO delete()");//
		
		/*Connection connection = db.getConnection();
		PreparedStatement stmt = null;

		try {
			// Start a transaction
			// perhaps if it doesn't work, use title and projectID.
			String sql = "delete from Projects where username = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, f.getUserName());

			int g = stmt.executeUpdate();
			if (g == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.err.println("Could NOT Delete the User");
			return false;
		}*/

		return false;

	}

	/**Used to retrieve list of all game objects.
	 * 
	 */
	@Override
	public List<Game> getGames() {
		System.out.println("GameDAO getGames()");//
		List<Game> games = new ArrayList<Game>();


		return games;
	}

	/**Used to add a player to the given game.
	 * 
	 */
	@Override
	public void joinPlayer(int gameID, int userID) {
		System.out.println("GameDAO joinPlayer()");//
		// TODO Auto-generated method stub

	}

	/**Used to add a command to the given game database object
	 * If there are 10 total commands with the game, save the game, then remove all commands
	 * If there are total 9 or less commands with the game, save the new command with the game.
	 */
	@Override
	public void storeCommand(int gameID, Command command) {
		System.out.println("GameDAO storeCommand()");//

	}

}



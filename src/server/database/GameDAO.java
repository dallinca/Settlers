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
import shared.definitions.CatanColor;
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
	 * @throws SQLException 
	 */
	@Override
	public Game update(Game g) throws SQLException {
		
		Connection connection = db.getConnection();

		System.out.println("GameDAO update()"); //((Called to save the game))
		
		// Start a transaction
		
		PreparedStatement stmt = null;

		Blob gameBlob;
		ByteArrayOutputStream bos = null;
		
		try {
			String sql = "update Games SET game = ? WHERE gameID = ?";

			try {
				
				bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(g);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			byte[] byteArray = bos.toByteArray();

			gameBlob = new SerialBlob(byteArray);
			
			stmt = connection.prepareStatement(sql);
			stmt.setBlob(1, gameBlob);
			//stmt.setString(2, g.getGameHistory());
			stmt.setInt(2, g.getGameID());

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
		
		Connection connection = db.getConnection();
		PreparedStatement stmt = null;

		try {
			// Start a transaction
			String sql = "delete from Games where gameID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, game.getGameID());

			int g = stmt.executeUpdate();
			if (g == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.err.println("Could NOT Delete the Game");
			return false;
		}
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
	public void joinPlayer(int gameID, int userID, CatanColor playerColor) {
		System.out.println("GameDAO joinPlayer()");

	}

	/**Used to add a command to the given game database object
	 * If there are 10 total commands with the game, save the game, then remove all commands
	 * If there are total 9 or less commands with the game, save the new command with the game.
	 */
	@Override
	public void storeCommand(int gameID, Command command) {
		System.out.println("GameDAO storeCommand()");
		
		Connection connection = db.getConnection();
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		String select = "SELECT Commands from Games where gameID = ?";
		
		String sql = "UPDATE Games SET Commands = ? WHERE gameID = ?";
		
		//All your base are belong to us
		
	}
	
	/**
	 * This is used to clear the command list out so the game can be stored again
	 * @pre this is called when the number of commands in the database = 10
	 * @post ability to re-store the game blob in the database
	 */
	@Override
	public boolean clearCommand(int gameID) {
		Connection connection = db.getConnection();
		PreparedStatement stmt = null;

		try {
			// Start a transaction
			String sql = "delete Commands from Games where gameID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, gameID);

			int g = stmt.executeUpdate();
			if (g == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.err.println("Could NOT clear the Commands");
			return false;
		}

	}

	@Override
	public void clearCommands(int gameID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Command> getCommands(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

}



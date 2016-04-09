package server.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import com.google.gson.Gson;

import server.commands.Command;
import shared.communication.*;
import shared.communication.results.ClientModel;
import shared.communication.results.JsonConverter;
import shared.communication.results.nonmove.Join_Result;
import shared.definitions.CatanColor;
import shared.model.Game;

public class GameDAO implements GameDAOInterface {

	private Gson gson;
	private JsonConverter converter;
	/**
	 * Constructor
	 * takes in a database object
	 */
	public GameDAO() {

		gson = new Gson();
		converter = new JsonConverter();
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
			stmt = DatabaseAccess.getInstance().getConnection().prepareStatement(sql);

			stmt.setInt(1, game.getGameID());

			//Blob gameBlob;
			//ByteArrayOutputStream bos = null;

			//try {
			String serialized = gson.toJson(converter.toClientModel(game));

			/*bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);			
				oos.writeObject(serialized);*/

			//} catch (IOException e) {
			// TODO Auto-generated catch block
			//		e.printStackTrace();
			//}			

			//byte[] byteArray = bos.toByteArray();

			//gameBlob = DatabaseAccess.getInstance().getConnection().createBlob();			
			//gameBlob.setBytes(0, byteArray);

			//gameBlob = new SerialBlob(byteArray);

			//stmt.setBlob(2, gameBlob);//crashes here----------
			stmt.setString(2, serialized);


			//	Blob commandBlob = null; //There exist no commands for a new game. DO NOT TRY TO ADD NONEXISTENT COMMANDS!
			//stmt.setBlob(3, commandBlob);\
			stmt.setString(3, "");

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
	public boolean update(Game game) throws SQLException {

		Connection connection = DatabaseAccess.getInstance().getConnection();

		System.out.println("GameDAO update()"); //((Called to save the game))

		// Start a transaction

		PreparedStatement stmt = null;

		//Blob gameBlob;
		//ByteArrayOutputStream bos = null;

		try {
			String sql = "update Games SET game = ? WHERE gameID = ?";

			//try {

			String serialized = gson.toJson(converter.toClientModel(game));

			//bos = new ByteArrayOutputStream();
			//ObjectOutputStream oos = new ObjectOutputStream(bos);			
			//oos.writeObject(serialized);

			//} catch (IOException e) {
			// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}			

			//byte[] byteArray = bos.toByteArray();

			//gameBlob = new SerialBlob(byteArray);

			stmt = connection.prepareStatement(sql);
			//stmt.setBlob(1, gameBlob);
			//stmt.setString(2, g.getGameHistory());
			
			stmt.setString(1, serialized);
			stmt.setInt(2, game.getGameID());		

			if (stmt.executeUpdate() == 1)
				return true;
			else
				System.out.println("Update Game failed.");
		} catch (SQLException e) {

		} finally {
			if (stmt != null)
				stmt.close();
		}
		return true;
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

		Connection connection = DatabaseAccess.getInstance().getConnection();
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
	 * @throws SQLException 
	 * @throws DatabaseException 
	 * 
	 */
	@Override
	public List<Game> getGames() throws SQLException, DatabaseException {

		System.out.println("GameDAO getGames()");

		List<Game> games = new ArrayList<Game>();

		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;

		try {
			String sql = "SELECT * FROM Games";
			stmt = DatabaseAccess.getInstance().getConnection().prepareStatement(sql);
			keyRS = stmt.executeQuery();

			Blob gameBlob = null;
			while (keyRS.next()) {

				gameBlob = keyRS.getBlob(1);
				byte[] byteArray = gameBlob.getBytes(1, (int) gameBlob.length());
				gameBlob.free();

				ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
				ObjectInputStream is = new ObjectInputStream(in);				
				String serialized = (String) is.readObject();				
				Game game = converter.parseJson(serialized);//gson.fromJson(serialized, Game.class);	

				in.close();
				is.close();

				games.add(game);
			}		

		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (stmt != null)
				stmt.close();
			if (keyRS != null)
				keyRS.close();
			if (keyStmt != null)
				keyStmt.close();
		}

		return games;
	}

	/**Used to add a player to the given game.
	 * @throws SQLException 
	 * 
	 */
	@Override
	public boolean joinPlayer(int gameID, int userID, CatanColor playerColor) throws SQLException {
		System.out.println("GameDAO joinPlayer()");

		PreparedStatement stmt = null;
		ResultSet keyRS = null;

		try {
			String sql = "SELECT game FROM Games WHERE gameID = ?";
			stmt = DatabaseAccess.getInstance().getConnection().prepareStatement(sql);					
			stmt.setInt(1, gameID);			
			keyRS = stmt.executeQuery();

			//Blob gameBlob = null;
			while (keyRS.next()) {

				//gameBlob = keyRS.getBlob(2);
				//byte[] byteArray = gameBlob.getBytes(1, (int) gameBlob.length());
				//gameBlob.free();

				//ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
				//ObjectInputStream is = new ObjectInputStream(in);
				String serialized = keyRS.getString(1);//(String) is.readObject();	

				Game game = converter.parseJson(serialized);	

				//in.close();
				//is.close();

				game.addPlayer(userID, playerColor);

				update(game);		
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		finally{

			if (stmt != null)
				stmt.close();
			if (keyRS != null)
				keyRS.close();
		}		

		return true;

	}

	/**Used to add a command to the given game database object
	 * If there are 10 total commands with the game, save the game, then remove all commands
	 * If there are total 9 or less commands with the game, save the new command with the game.
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@Override
	public boolean storeCommand(int gameID, Command command) throws SerialException, SQLException {
		System.out.println("GameDAO storeCommand()");

		Connection connection = DatabaseAccess.getInstance().getConnection();
		PreparedStatement stmt = null;

		List<Command> commands = getCommands(gameID);

		commands.add(command);

		Blob commandBlob;
		ByteArrayOutputStream bos = null;

		try {

			String serialized = gson.toJson(commands);

			bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(serialized);

		} catch (IOException e) {

			e.printStackTrace();
		}			

		byte[] byteArray = bos.toByteArray();

		commandBlob = new SerialBlob(byteArray);


		try {
			// Start a transaction
			String sql = "UPDATE Games SET commands = ? WHERE gameID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setBlob(2, commandBlob);
			stmt.setInt(2, gameID);

			int g = stmt.executeUpdate();
			if (g == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.err.println("Could NOT store commands");
			return false;
		}



		//All your base are belong to us

	}

	/**
	 * This is used to clear the command list out so the game can be stored again
	 * @pre this is called when the number of commands in the database = 10
	 * @post ability to re-store the game blob in the database
	 */
	@Override
	public boolean clearCommands(int gameID) {
		Connection connection = DatabaseAccess.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			// Start a transaction
			String sql = "delete commands from Games where gameID = ?";
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Command> getCommands(int gameID) {

		Connection connection = DatabaseAccess.getInstance().getConnection();
		PreparedStatement stmt = null;

		//Statement keyStmt = null;
		ResultSet keyRS = null;


		try {
			// Start a transaction
			String sql = "SELECT commands from Games where gameID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, gameID);

			keyRS = stmt.executeQuery();
			Blob commandBlob = null;
			while (keyRS.next()) {
				commandBlob = keyRS.getBlob(1);
			}			

			//ObjectInputStream ois = new ObjectInputStream(commandBlob.getBinaryStream());

			byte[] byteArray = commandBlob.getBytes(1, (int) commandBlob.length());
			commandBlob.free();

			try {
				ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
				ObjectInputStream is = new ObjectInputStream(in);
				String serialized = (String) is.readObject();				
				List<Command> commands = gson.fromJson(serialized, List.class);	

				in.close();
				is.close();

				return commands;

			} catch (IOException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}			

		} catch (SQLException e) {
			System.err.println("Could NOT get the Commands");
		}

		return new ArrayList<Command>();

	}

}



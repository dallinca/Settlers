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
import com.google.gson.JsonObject;

import server.commands.Command;
import server.commands.move.AcceptTrade_Command;
import server.commands.move.BuildCity_Command;
import server.commands.move.BuildRoad_Command;
import server.commands.move.BuildSettlement_Command;
import server.commands.move.BuyDevCard_Command;
import server.commands.move.DiscardCards_Command;
import server.commands.move.FinishTurn_Command;
import server.commands.move.MaritimeTrade_Command;
import server.commands.move.OfferTrade_Command;
import server.commands.move.RobPlayer_Command;
import server.commands.move.RollNumber_Command;
import server.commands.move.SendChat_Command;
import server.commands.move.devcard.PlayMonopoly_Command;
import server.commands.move.devcard.PlayMonument_Command;
import server.commands.move.devcard.PlayRoadBuilding_Command;
import server.commands.move.devcard.PlaySoldier_Command;
import server.commands.move.devcard.PlayYearOfPlenty_Command;
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
			if (DatabaseAccess.getInstance().getConnection()==null){
				System.out.println("Database connection is null.");
			}
			System.out.println("GameDAO 1");
			String sql = "INSERT INTO Games (gameID, game, commands) values (?, ?, ?)";
			stmt = DatabaseAccess.getInstance().getConnection().prepareStatement(sql);

			stmt.setInt(1, game.getGameID());
			String serialized = gson.toJson(converter.toClientModel(game));			
			stmt.setString(2, serialized);

			ArrayList<Object> commandList = new ArrayList<Object>();

			serialized = gson.toJson(commandList);
			System.out.println("Commands: " + serialized);
			stmt.setString(3, serialized);

			if (stmt.executeUpdate() == 1) {
				System.out.println("GameDAO 2");
				//stmt.close();

			} else {
				System.out.println("GameDAO 3");
				//stmt.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GameDAO 4");
			return false;
		} finally {
			System.out.println("GameDAO 5");
			if (stmt != null){
				System.out.println("GameDAO 6");
				stmt.close();
			}
		}
		System.out.println("GameDAO Finish create.");
		return true;
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
			System.out.println("GameDAO 1"); 
			String sql = "update Games SET game = ? WHERE gameID = ?";

			String serialized = gson.toJson(converter.toClientModel(game));

			stmt = connection.prepareStatement(sql);

			stmt.setString(1, serialized);
			stmt.setInt(2, game.getGameID());		

			if (stmt.executeUpdate() == 1){
				System.out.println("GameDAO 2"); 
				return true;
			}
			else{
				System.out.println("GameDao 3.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("GameDAO 4");
			return false;			

		} finally {
			if (stmt != null){
				System.out.println("GameDAO 5"); 
				stmt.close();
			}
		}
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

			while (keyRS.next()) {
				String serialized = keyRS.getString(2);	
				Game game = converter.parseServerJson(serialized);
				games.add(game);
			}		

		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		} 
		finally {
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
		Game game = null;

		try {

			System.out.println("GameDAO 1");
			String sql = "SELECT game FROM Games WHERE gameID = ?";
			stmt = DatabaseAccess.getInstance().getConnection().prepareStatement(sql);					
			stmt.setInt(1, gameID);			
			keyRS = stmt.executeQuery();

			//Blob gameBlob = null;
			while (keyRS.next()) {
				System.out.println("GameDAO 2");

				String serialized = keyRS.getString(1);	
				game = converter.parseServerJson(serialized);	
				game.addPlayer(userID, playerColor);

			}

		} catch (SQLException e) {
			System.out.println("GameDAO 3");
			e.printStackTrace();
			return false;
		} 
		finally{
			System.out.println("GameDAO 4");
			if (stmt != null){
				stmt.close();
				System.out.println("GameDAO 5");
			}				
			if (keyRS != null){
				keyRS.close();
				System.out.println("GameDAO 6");
			}		
			if (game!=null){
				System.out.println("GameDAO 7");
				update(game);				
			}			

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

		List<Command> commands = new ArrayList<Command>();

		commands = getCommands(gameID);

		commands.add(command);
		String serialized = gson.toJson(commands);

		try {
			// Start a transaction
			String sql = "UPDATE Games SET commands = ? WHERE gameID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, serialized);
			stmt.setInt(2, gameID);

			int g = stmt.executeUpdate();
			if (g == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.err.println("Could NOT store commands");
			return false;
		}finally{
			if (stmt!=null){
				stmt.close();
			}
		}
	}

	/**
	 * This is used to clear the command list out so the game can be stored again
	 * @throws SQLException 
	 * @pre this is called when the number of commands in the database = 10
	 * @post ability to re-store the game blob in the database
	 */
	@Override
	public boolean clearCommands(int gameID) throws SQLException {
		System.out.println("GameDAO clearCommands()");
		Connection connection = DatabaseAccess.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			System.out.println("GameDAO clear 1");
			// Start a transaction
			//UPDATE Games SET commands = ? WHERE gameID = ?
			String sql = "UPDATE Games SET commands = ? WHERE gameID = ?";
			System.out.println("GameDAO clear 1.01");
			stmt = connection.prepareStatement(sql);
			System.out.println("GameDAO clear 1.02");
			stmt.setString(1, gson.toJson(new ArrayList<Command>()));
			stmt.setInt(2, gameID);
			//System.out.println("GameDAO clear 1.1");
			int g = stmt.executeUpdate();
			//System.out.println("GameDAO clear 1.2");

			if (g == 1){
				System.out.println("GameDAO clear 2");
			}
			else{
				return false;
			}

		} catch (SQLException e) {
			System.out.println("GameDAO clear 3");
			System.err.println("Could NOT clear the Commands");
			return false;
		}finally{
			if (stmt!=null){
				System.out.println("GameDAO clear 4");
				stmt.close();
			}
		}
		System.out.println("GameDAO clear 5");
		return true;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Command> getCommands(int gameID) throws SQLException {
		System.out.println("GameDAO getCommands()");

		ArrayList<Command> commands = new ArrayList<Command>();
		Connection connection = DatabaseAccess.getInstance().getConnection();
		PreparedStatement stmt = null;

		ResultSet keyRS = null;

		try {
			System.out.println("GameDAO 1");
			// Start a transaction
			String sql = "SELECT commands from Games where gameID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, gameID);

			keyRS = stmt.executeQuery();

			String serialized = null;
			while (keyRS.next()) {
				serialized = keyRS.getString(1);
				System.out.println("GameDAO 2");
			}
			System.out.println(serialized);
			ArrayList<Object> tempList = gson.fromJson(serialized, ArrayList.class);	
			System.out.println("GameDAO 3");
			for (int i = 0; i < tempList.size();i++){
				//System.out.println(tempList.get(i).getClass());			
				Command c = null;

				Object o = tempList.get(i);
				System.out.println(o.toString());	

				String indefiniteCommand = o.toString();

				System.out.println("IndefiniteCommand: " + indefiniteCommand);

				JsonObject jobj = gson.fromJson(indefiniteCommand, JsonObject.class);				
				String params =	jobj.get("params").toString();	

				System.out.println("Params: " + params);

				jobj = gson.fromJson(params, JsonObject.class);				
				String type = jobj.get("type").toString();

				type = type.substring(1, type.length()-1);

				System.out.println("Type: " + type);

				if (type.equals("buildRoad")){
					c = gson.fromJson(indefiniteCommand, BuildRoad_Command.class);
					//	System.out.println("Build road cast.");
					//System.out.println(c.toString());
				} else if (type.equals("acceptTrade")){
					c = gson.fromJson(indefiniteCommand, AcceptTrade_Command.class);			
				} else if (type.equals("buildCity")){
					c = gson.fromJson(indefiniteCommand, BuildCity_Command.class);			
				}else if (type.equals("buildRoad")){
					c = gson.fromJson(indefiniteCommand, BuildRoad_Command.class);			
				}else if (type.equals("buildSettlement")){
					c = gson.fromJson(indefiniteCommand, BuildSettlement_Command.class);			
				}else if (type.equals("buyDevCard")){
					c = gson.fromJson(indefiniteCommand, BuyDevCard_Command.class);			
				}else if (type.equals("discardCards")){
					c = gson.fromJson(indefiniteCommand, DiscardCards_Command.class);			
				}else if (type.equals("finishTurn")){
					c = gson.fromJson(indefiniteCommand, FinishTurn_Command.class);			
				}else if (type.equals("maritimeTrade")){
					c = gson.fromJson(indefiniteCommand, MaritimeTrade_Command.class);			
				}else if (type.equals("offerTrade")){
					c = gson.fromJson(indefiniteCommand, OfferTrade_Command.class);			
				}else if (type.equals("robPlayer")){
					c = gson.fromJson(indefiniteCommand, RobPlayer_Command.class);			
				}else if (type.equals("rollNumber")){
					c = gson.fromJson(indefiniteCommand, RollNumber_Command.class);			
				}else if (type.equals("sendChat")){
					c = gson.fromJson(indefiniteCommand, SendChat_Command.class);			
				}else if (type.equals("Monopoly")){
					c = gson.fromJson(indefiniteCommand, PlayMonopoly_Command.class);			
				}else if (type.equals("Monument")){
					c = gson.fromJson(indefiniteCommand, PlayMonument_Command.class);			
				}else if (type.equals("Road_Building")){
					c = gson.fromJson(indefiniteCommand, PlayRoadBuilding_Command.class);			
				}else if (type.equals("Soldier")){
					c = gson.fromJson(indefiniteCommand, PlaySoldier_Command.class);			
				}else if (type.equals("Year_of_Plenty")){
					c = gson.fromJson(indefiniteCommand, PlayYearOfPlenty_Command.class);			
				}

				//Command c = gson.fromJson(o.toString(), Command.class);
				if (c!=null){
					commands.add(c);
				}else {
					System.out.println("This will crash the program.");
					commands.add((Command) o);
				}				
			}

			System.out.println("GameDAO 4");

		}
		catch (SQLException e) {
			System.out.println("GameDAO 5");
			System.err.println("Could NOT get the Commands");
		}
		finally{
			if (stmt!=null){
				System.out.println("GameDAO 6");
				stmt.close();
			}			
		}
		System.out.println("GameDAO 7");
		return commands;
	}
}



package server.database;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

public class GameTxtDAO implements GameDAOInterface {

	private Gson gson;
	private JsonConverter converter;
	/**
	 * Constructor
	 * takes in a database object
	 */
	public GameTxtDAO() {

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
	public boolean create(Game game) {
		initFileSystem(); // Makes the directories
		System.out.println("GameDAO create()");

		int newGameID = game.getGameID();
		String gameSerialized = gson.toJson(converter.toClientModel(game));	
		
		try {
			PrintWriter writer;
			// the name of the folder should be the Id of the Game
			writer = new PrintWriter("FilePersistence/Games/" + newGameID + ".txt", "UTF-8");
			writer.println(gameSerialized);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
	public boolean update(Game game) { 
		initFileSystem(); // Makes the directories

		int newGameID = game.getGameID();
		String gameSerialized = gson.toJson(converter.toClientModel(game));	
		
		try {
			PrintWriter writer;
			// the name of the folder should be the Id of the Game
			writer = new PrintWriter("FilePersistence/Games/" + newGameID + ".txt", "UTF-8");
			writer.println(gameSerialized);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(gameSerialized != null) {
			return true;
		}
		return false;
	}
	/**
	 * Deletes the given corresponding game object from the database
	 * Used to remove the game upon game completion.
	 * @pre the given game is a valid game in the database
	 * @return a boolean depicting whether or not the delete was successful
	 */
	@Override
	public boolean delete(Game game) { 
		initFileSystem(); // Makes the directories
		System.out.println("GameDAO delete()");//


		File gameFile = new File("FilePersistence/Games/" + game.getGameID() + ".txt");
	    if (gameFile.exists()) {
	    	gameFile.delete();
			File commandFile = new File("FilePersistence/Games/" + game.getGameID() + "_Commands.txt");
		    if (commandFile.exists()) {
		    	commandFile.delete();     
		    }
		    return true;
	    }
	    
	    return false;
	}

	/**Used to retrieve list of all game objects.
	 * @throws SQLException 
	 * @throws DatabaseException 
	 * 
	 */
	@Override
	public List<Game> getGames() throws SQLException, DatabaseException { 
		initFileSystem(); // Makes the directories

		System.out.println("GameDAO getGames()");

		List<Game> games = new ArrayList<Game>();


	    System.out.println("\n\nWorking with Games!");
		File gameFolder = new File("FilePersistence/Games/");
		
	    for (File fileEntry : gameFolder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            // Should not have any directories
	        } else {
	        	String fileName = fileEntry.getName();
	            System.out.println(fileName.substring(0, fileName.length() - 4));

	    		try {
	    			File file = new File("FilePersistence/Games/" + fileName);
	    			FileReader fileReader = new FileReader(file);
	    			BufferedReader bufferedReader = new BufferedReader(fileReader);
	    			StringBuffer stringBuffer = new StringBuffer();
	    			String line;
	    			if ((line = bufferedReader.readLine()) != null) { // Only grab the first line
	    				stringBuffer.append(line);
	    			}
	    			fileReader.close();
	    			
	    			// If we are looking at a commands file instead of a game file
	    			if(fileName.contains("_Commands")) {
	    				// A Commands File
	    			} else {
	    				// A Game Server
	    				// here we convert from string to Game Object and add it to "games"
	    				String gameString = stringBuffer.toString();
	    				Game game = converter.parseServerJson(gameString);
	    				games.add(game);
		    			System.out.println(stringBuffer.toString());
	    			}
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        }
	    }

		return games;
	}

	/**Used to add a player to the given game.
	 * @throws SQLException 
	 * 
	 */
	@Override
	public boolean joinPlayer(int gameID, int userID, CatanColor playerColor) throws SQLException { 
		initFileSystem(); // Makes the directories
		System.out.println("GameDAO joinPlayer()");
/*
		try {
			File file = new File("FilePersistence/Games/" + gameID + ".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			if ((line = bufferedReader.readLine()) != null) { // Only grab the first line
				stringBuffer.append(line);
			}
			fileReader.close();
			
			// turn this gameString into a game object. (next two lines)
			String gameString = stringBuffer.toString();
			Game game = converter.parseServerJson(gameString);
			
			game.addPlayer(userID, playerColor);
			
			// Take game into string and store it in the file
			String gameSerialized = gson.toJson(converter.toClientModel(game));	
			PrintWriter writer;
			writer = new PrintWriter("FilePersistence/Games/" + gameID + ".txt", "UTF-8");
			writer.println(gameSerialized);
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	
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
		initFileSystem(); // Makes the directories
		System.out.println("GameDAO storeCommand()");
		
		List<Command> currentCommands = getCommands(gameID);
		currentCommands.add(command);
		
		// serialize the "currentCommands" list and put it into the String "serializedCommandsList"
		String serializedCommandsList = gson.toJson(currentCommands);
		
		try {
			PrintWriter writer;
			writer = new PrintWriter("FilePersistence/Games/" + gameID + "_Commands.txt", "UTF-8");
			writer.println(serializedCommandsList);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * This is used to clear the command list out so the game can be stored again
	 * @pre this is called when the number of commands in the database = 10
	 * @post ability to re-store the game blob in the database
	 */
	@Override
	public boolean clearCommands(int gameID) { 
		initFileSystem(); // Makes the directories

		File commandFile = new File("FilePersistence/Games/" + gameID + "_Commands.txt");
	    if (commandFile.exists()) {
	    	commandFile.delete();
	    	return true;
	    }
	    
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Command> getCommands(int gameID) { 
		initFileSystem(); // Makes the directories
		List<Command> commandList = new ArrayList<Command>();

	    System.out.println("\n\nWorking with Commands!");
		File gameFolder = new File("FilePersistence/Games/");
		
	    for (File fileEntry : gameFolder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            // Should not have any directories
	        } else {
	        	String fileName = fileEntry.getName();
	            System.out.println(fileName.substring(0, fileName.length() - 4));

	    		try {
	    			File file = new File("FilePersistence/Games/" + fileName);
	    			FileReader fileReader = new FileReader(file);
	    			BufferedReader bufferedReader = new BufferedReader(fileReader);
	    			StringBuffer stringBuffer = new StringBuffer();
	    			String line;
	    			if ((line = bufferedReader.readLine()) != null) { // Only grab the first line
	    				stringBuffer.append(line);
	    			}
	    			fileReader.close();
	    			
	    			// If we are looking at a commands file instead of a game file
	    			if(fileName.contains("_Commands")) {
	    				// A Commands File
	    				// here we convert from string to Command and Add it to the Command ArrayList
	    				String commandString = stringBuffer.toString();
	    				ArrayList<Object> tempList = gson.fromJson(commandString, ArrayList.class);	
	    				//System.out.println("GameDAO 3");
	    				for (int i = 0; i < tempList.size();i++){
	    					
	    					Command c = null;
	    					Object o = tempList.get(i);
	    					String indefiniteCommand = o.toString();
	    					JsonObject jobj = gson.fromJson(indefiniteCommand, JsonObject.class);				
	    					String params =	jobj.get("params").toString();	
	    					jobj = gson.fromJson(params, JsonObject.class);				
	    					String type = jobj.get("type").toString();
	    					type = type.substring(1, type.length()-1);
	    					System.out.println("Type: " + type);

	    					if (type.equals("buildRoad")){
	    						c = gson.fromJson(indefiniteCommand, BuildRoad_Command.class);
	    					} else if (type.equals("acceptTrade")){
	    						c = gson.fromJson(indefiniteCommand, AcceptTrade_Command.class);			
	    					} else if (type.equals("buildCity")){
	    						c = gson.fromJson(indefiniteCommand, BuildCity_Command.class);			
	    					} else if (type.equals("buildRoad")){
	    						c = gson.fromJson(indefiniteCommand, BuildRoad_Command.class);			
	    					} else if (type.equals("buildSettlement")){
	    						c = gson.fromJson(indefiniteCommand, BuildSettlement_Command.class);			
	    					} else if (type.equals("buyDevCard")){
	    						c = gson.fromJson(indefiniteCommand, BuyDevCard_Command.class);			
	    					} else if (type.equals("discardCards")){
	    						c = gson.fromJson(indefiniteCommand, DiscardCards_Command.class);			
	    					} else if (type.equals("finishTurn")){
	    						c = gson.fromJson(indefiniteCommand, FinishTurn_Command.class);			
	    					} else if (type.equals("maritimeTrade")){
	    						c = gson.fromJson(indefiniteCommand, MaritimeTrade_Command.class);			
	    					} else if (type.equals("offerTrade")){
	    						c = gson.fromJson(indefiniteCommand, OfferTrade_Command.class);			
	    					} else if (type.equals("robPlayer")){
	    						c = gson.fromJson(indefiniteCommand, RobPlayer_Command.class);			
	    					} else if (type.equals("rollNumber")){
	    						c = gson.fromJson(indefiniteCommand, RollNumber_Command.class);			
	    					} else if (type.equals("sendChat")){
	    						c = gson.fromJson(indefiniteCommand, SendChat_Command.class);			
	    					} else if (type.equals("Monopoly")){
	    						c = gson.fromJson(indefiniteCommand, PlayMonopoly_Command.class);			
	    					} else if (type.equals("Monument")){
	    						c = gson.fromJson(indefiniteCommand, PlayMonument_Command.class);			
	    					} else if (type.equals("Road_Building")){
	    						c = gson.fromJson(indefiniteCommand, PlayRoadBuilding_Command.class);			
	    					} else if (type.equals("Soldier")){
	    						c = gson.fromJson(indefiniteCommand, PlaySoldier_Command.class);			
	    					} else if (type.equals("Year_of_Plenty")){
	    						c = gson.fromJson(indefiniteCommand, PlayYearOfPlenty_Command.class);			
	    					}

	    					//Command c = gson.fromJson(o.toString(), Command.class);
	    					if (c!=null){
	    						commandList.add(c);
	    					} else {
	    						System.out.println("This will crash the program.");
	    						commandList.add((Command) o);
	    					}				
	    				}
		    			System.out.println(stringBuffer.toString());
	    			} else {
	    				// A Game Server
	    			}
	    			System.out.println(stringBuffer.toString());
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        }
	    }

		return commandList;

	}
	
	private void initFileSystem() {
		// Create the base fileSystem that we will be using to store the files of information for persistence
		// FilePersistence
		// -- Games
		// -- -- 12.txt
		// -- -- 12_Commands.txt
		// -- -- 15.txt
		// -- -- 15_Commands.txt
		// -- Users
		String[] fileSystemNames = {"FilePersistence","FilePersistence/Games","FilePersistence/Users"};
		for(int i = 0; i < fileSystemNames.length; i++) {
			File theDir = new File(fileSystemNames[i]);
			// if the directory does not exist, create it
			if (!theDir.exists()) {
			    System.out.println("creating directory: " + fileSystemNames[i]);
			    boolean result = false;
			    try{
			        theDir.mkdir();
			        result = true;
			    } 
			    catch(SecurityException se){
			        //handle it
			    }        
			    if(result) { System.out.println("DIR created"); }
			}
		}
	}

	@Override
	public boolean clean() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}



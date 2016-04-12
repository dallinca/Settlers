package server.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;

import com.google.gson.Gson;

import shared.communication.User;
import shared.communication.params.nonmove.*;
import shared.communication.results.JsonConverter;
import shared.model.Game;

public class UserTxtDAO implements UserDAOInterface {

	private Gson gson;
	private JsonConverter converter;

	public UserTxtDAO() {
		gson = new Gson();
		converter = new JsonConverter();
	}

	/**
	 * Inserts a new Users object into the database
	 * @pre the user object given to create in the database is valid
	 * @return a boolean representing whether or not the create was successful
	 * @throws SQLException 
	 */
	@Override
	public boolean create(User user) { 
		initFileSystem(); // Makes the directories
		System.out.println("UserTxtDAO create()");

		int newUserID = user.getPlayerID();
		String userSerialized = gson.toJson(user);
		
		try {
			PrintWriter writer;
			// the name of the folder should be the Id of the Game
			writer = new PrintWriter("FilePersistence/Users/" + newUserID + ".txt", "UTF-8");
			writer.println(userSerialized);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Get all users object from the database and return a list of them
	 * @pre none
	 * @return Arraylist of User
	 * @throws DatabaseException, SQLException 
	 */
	@Override
	public List<User> getUsers() {
		initFileSystem(); // Makes the directories
		System.out.println("UserTxtDAO getUsers()");
		
		ArrayList<User> users = new ArrayList<User>();
		
		File userFolder = new File("FilePersistence/Users/");
		
	    for (File fileEntry : userFolder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            // Should not have any directories
	        } else {
	        	String fileName = fileEntry.getName();
	            System.out.println(fileName.substring(0, fileName.length() - 4));

	    		try {
	    			File file = new File("FilePersistence/Users/" + fileName);
	    			FileReader fileReader = new FileReader(file);
	    			BufferedReader bufferedReader = new BufferedReader(fileReader);
	    			StringBuffer stringBuffer = new StringBuffer();
	    			String line;
	    			if ((line = bufferedReader.readLine()) != null) { // Only grab the first line
	    				stringBuffer.append(line);
	    			}
	    			fileReader.close();
	    			
	    			// here we new to take the string and instead of system outing, turn it into the User object
	    			String userAsString = stringBuffer.toString();
	    			User user = gson.fromJson(userAsString, User.class);
	    			System.out.println(userAsString);
	    			users.add(user);
	    			
	    			
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        }
	    }
		
		return users;
	}
	
	@Override
	public boolean clearUsers() {
		System.out.println("UserDAO clearUsers()");
		
		
		return false;
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



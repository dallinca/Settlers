package server.database;

import java.sql.*;
import java.util.*;

import shared.communication.User;
import shared.communication.params.nonmove.*;

public class UserDAO implements UserDAOInterface {


	public UserDAO() {
	}

	/**
	 * Inserts a new Users object into the database
	 * @pre the user object given to create in the database is valid
	 * @return a boolean representing whether or not the create was successful
	 * @throws SQLException 
	 */
	@Override
	public boolean create(User user) throws SQLException{ 
		System.out.println("UserDAO create()");
		
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {

			System.out.println("UserDAO 1.");
			String sql = "INSERT INTO Users (userID, username, password) values (?, ?, ?)";
			System.out.println("UserDAO 2.");
			if (DatabaseAccess.getInstance().getConnection()==null){
				System.out.println("Database connection is null.");
			}
			
			//System.out.println("User: " +user.toString());
			stmt = DatabaseAccess.getInstance().getConnection().prepareStatement(sql);
			stmt.setString(3, user.getName());
			//System.out.println("UserDAO 3.");
			stmt.setString(2, user.getPassword());
			//System.out.println("UserDAO 4. Player ID : "+user.getPlayerID());
			stmt.setInt(1, user.getPlayerID());
			//System.out.println("UserDAO 5.");
			
			//System.out.println("Excute: "+stmt.executeUpdate());
			if (stmt.executeUpdate() == 1) {
				System.out.println("UserDAO 5;.");
				keyStmt = DatabaseAccess.getInstance().getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()"); 
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setPlayerID(id);
			} else {
				System.out.println("UserDAO 6.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("UserDAO 7.");
			e.printStackTrace();
			return false;
		} finally {
			System.out.println("UserDAO 8.");
			if (stmt != null){
				stmt.close();				
			}				
			if (keyRS != null)
				keyRS.close();
			if (keyStmt != null)
				keyStmt.close();
		}
		System.out.println("UserDAO Finish create.");
		return true;
	}

	/**
	 * Get all users object from the database and return a list of them
	 * @pre none
	 * @return Arraylist of User
	 * @throws DatabaseException, SQLException 
	 */
	@Override
	public List<User> getUsers() throws DatabaseException, SQLException{
		System.out.println("UserDAO getUsers()");
		
		ArrayList<User> users = new ArrayList<User>();
		
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
				
		try {
			String sql = "SELECT * FROM Users";
			stmt = DatabaseAccess.getInstance().getConnection().prepareStatement(sql);
			keyRS = stmt.executeQuery();
			
			while (keyRS.next()) {
				
				int playerID = keyRS .getInt(1);
				String name = keyRS.getString(2);
				String password = keyRS .getString(3);				
				
				User user = new User(name, password, playerID);
				users.add(user);
			}
			
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
			//e.printStackTrace();
		}finally {
			if (stmt != null)
				stmt.close();
			if (keyRS != null)
				keyRS.close();
			if (keyStmt != null)
				keyStmt.close();
		}
		return users;
	}
	
	@Override
	public boolean clean() throws SQLException {
		System.out.println("Clean the tables Cinderella!");

		Connection connection = DatabaseAccess.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			// Start a transaction
			String sql = "delete * from Users";
			stmt = connection.prepareStatement(sql);

			int g = stmt.executeUpdate();
			if (g == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.err.println("Could NOT Clean out the Users.");
			return false;
		}
		finally {
			if (stmt != null){
				System.out.println("Clean User"); 
				stmt.close();
			}
		}
	}
	
	
	
	
	@Override
	public boolean clearUsers() {
		System.out.println("UserDAO clearUsers()");
		
		
		return false;
	}
}



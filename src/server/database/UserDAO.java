package server.database;

import java.sql.*;
import java.util.*;

import shared.communication.User;
import shared.communication.params.nonmove.*;

public class UserDAO implements UserDAOInterface {

	DatabaseAccess db;

	public UserDAO(DatabaseAccess db) {
		this.db = db;
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
			/*WE NEED TO MAKE SURE IT IS USERS AND NOT User*/
			String sql = "INSERT INTO Users (username, password, userID) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setInt(3, user.getPlayerID());
			
			
			if (stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()"); 
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setPlayerID(id);
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (stmt != null)
				stmt.close();
			if (keyRS != null)
				keyRS.close();
			if (keyStmt != null)
				keyStmt.close();
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
	public List<User> getUsers() throws DatabaseException, SQLException{
		System.out.println("UserDAO getUsers()");
		
		ArrayList<User> users = new ArrayList<User>();
		
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "SELECT * FROM User";
			stmt = db.getConnection().prepareStatement(sql);
			keyRS = stmt.executeQuery();
			
			while (keyRS.next()) {
				String name = keyRS .getString(1);
				String password = keyRS .getString(2);
				int playerID = keyRS .getInt(3);
				
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
	public boolean clearUsers() {
		System.out.println("UserDAO clearUsers()");
		
		
		return false;
	}
}



package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.communication.User;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.nonmove.Register_Result;


public class UserDAO implements UserDAOInterface {

	//Database db;

	public UserDAO(/*Database db*/) {
		//this.db = db;
	}

	/**
	 * Inserts a new Users object into the database
	 * @pre the user object given to create in the database is valid
	 * @return a boolean representing whether or not the create was successful
	 */
	@Override
	public boolean create(User f) { // throws SQLException{
		/*Connection connection = db.getConnection();
				PreparedStatement stmt = null;
				Statement keyStmt = null;
				ResultSet keyRS = null;

				try {
					String sql = "insert into Users (username, password, firstname, lastname, email, userID) values (?, ?, ?, ?, ?, ?)";
					stmt = connection.prepareStatement(sql);
					stmt.setString(1, f.getUserName());
					stmt.setString(2, f.getPassword());
					stmt.setString(3, f.getFirstName());
					stmt.setString(4, f.getLastName());
					stmt.setString(5, f.getEmail());
					stmt.setInt(7, f.getUserID());

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
					if (keyRS != null)
						keyRS.close();
					if (keyStmt != null)
						keyStmt.close();
				}
		 */
		return true;
	}

	/**
	 * Updates the Users object in the database
	 * @pre the user object to update is valid
	 * @return The updated User
	 * 
	 */
	@Override
	public User update(User f) { //throws SQLException {
		/*Connection connection = db.getConnection();
		// Start a transaction

		PreparedStatement stmt = null;

		try {
			String sql = "update Users "
					+ "username = ?, password = ?, firstname = ?, lastname = ?, email = ?, userID = ?";

			stmt = connection.prepareStatement(sql);
			stmt.setString(1, f.getUserName());
			stmt.setString(2, f.getPassword());
			stmt.setString(3, f.getFirstName());
			stmt.setString(4, f.getLastName());
			stmt.setString(5, f.getEmail());
			stmt.setInt(7, f.getUserID());

			if (stmt.executeUpdate() == 1)
				return f;
			else
				System.out.println("Update Project failed.");
		} catch (SQLException e) {

		} finally {
			if (stmt != null)
				stmt.close();
		}
		return f;*/
		return null;
	}
	/**
	 * Deletes the Users object from the database
	 * @pre the user to delete is a valid user
	 * @return a boolean depicting whether or not the delete was successful
	 * 
	 */
	@Override
	public boolean delete(User f) {
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
	
	/**
	 * Queries the database for the specific Users object and returns the result
	 * @pre you are asking for a valid user
	 * @post the user object from the database
	 * 
	 */
	public User read(/*ValidateUserGetFieldsRequest NamePass*/) {
		/*Connection connection = db.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Users user = null;
		// String title, String helphtml, String knowndata, int xcoord,
		// int width, int numFields, int batchid, int fieldID1

		try {
			//String sql = "select * from Users where (username, password) values (?, ?)";
			String sql = "select * from Users where username = ? and password = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, NamePass.getUserName());
			stmt.setString(2, NamePass.getPassword());
			//stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String email = rs.getString("email");
				int indexedrecords = rs.getInt("indexedrecords");
				int userID = rs.getInt("userID");
				int imageID = rs.getInt("imageID");

				user = new Users(username, password, firstname, lastname, email, indexedrecords, userID);
				user.setImageID(imageID);
				return user;
			}
			else
				return null;

		} catch (SQLException e) {
			System.out.println("Read/Search: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 */
		return null;
	}

	/**
	 * Validate the user with the database
	 * @pre user is a valid user
	 * @post user can join, re-join, or create games
	 */
	@Override
	public boolean validateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Creates a new user object assuming the input is correct
	 * @pre valid username and password
	 * @post takes them to the game hub where they can join, re-join, or create games
	 */
	@Override
	public Register_Result register(Register_Params params) {
		// TODO Auto-generated method stub
		return null;
	}


}



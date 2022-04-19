package group825.vetapp2.users;

import group825.vetapp2.database.DatabaseConnection;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository that stores User information
 *
 * @author Aron Saengchan, Jimmy Zhu, Timothy Mok
 * @version 2.0
 * @since December 6, 2021
 */
@Repository("userRepo")
public class UserRepository {
	
	/**
	 * Connection to the SQL database
	 */
	private Connection dao;

	/**
	 * Constructor that initializes the UserRepository
	 */
	public UserRepository() {
		this.dao = DatabaseConnection.getConnection();
	}

    /**
     * Verifies a user's email and password information
     * @param username user's username
     * @param password user's password
     * @return 1 if login was successful, 0 otherwise
     */
	public User loginUser(String username, String password) {
		//empty dummy user to return if user is not found in database
		User singleUser = new User(0, "", "","","","","","",LocalDate.parse("2018-08-15"),true);
		
		try {
			// Create and execute a query to verify username and password
			PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM USERS WHERE Username = ? AND User_Password = ?");
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet results = statement.executeQuery();
			
			while (results.next()) {
				singleUser = new User(results.getInt("User_ID"), results.getString("First_Name"), results.getString("Last_Name"),
						results.getString("User_Type"), results.getString("Username"), results.getString("Email"),
						results.getString("Phone_Number"), results.getString("User_Password"),
						LocalDate.parse(results.getString("Start_Date")), results.getBoolean("Is_Active"));
			}

			return singleUser;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//returns empty user
		return singleUser;
    }

	/**
	 * Finds a user in the database by ID
	 * @param userID a specific user's ID
	 * @return user if found, 'null' otherwise
	 */
	public User selectUserById(int userID) {
		User user = null;

		try {
			// Execute SQL query to retrieve specified user
			PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM USERS WHERE User_ID = ?");

			statement.setInt(1, userID);
			ResultSet results = statement.executeQuery();

			// Extract the user's information
			while (results.next()) {
				user = new User(results.getInt("User_ID"), results.getString("First_Name"), results.getString("Last_Name"),
						results.getString("User_Type"), results.getString("Username"), results.getString("Email"),
						results.getString("Phone_Number"), results.getString("User_Password"),
						LocalDate.parse(results.getString("Start_Date")), results.getBoolean("Is_Active"));
			}

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	/**
	 * Finds a list of users in the database by type
	 * @param userType a type of user
	 * @return a list of users matching the type
	 */
	public ArrayList<User> selectUserByType(String userType) {
		userType.replace("%20", " ");
		ArrayList<User> users = new ArrayList<User>();

		try {
			// Execute SQL query to retrieve specified user
			PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM USERS WHERE User_Type = ?");

			statement.setString(1, userType);
			ResultSet results = statement.executeQuery();

			// Extract the user's information
			while (results.next()) {
				users.add(new User(results.getInt("User_ID"), results.getString("First_Name"), results.getString("Last_Name"),
						results.getString("User_Type"), results.getString("Username"), results.getString("Email"),
						results.getString("Phone_Number"), results.getString("User_Password"),
						LocalDate.parse(results.getString("Start_Date")), results.getBoolean("Is_Active")));
			}

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

    /**
     * Adds a user to the database
     * @param user user to be added
     */
    public void addUser(User user) {
		try {
			PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM USERS;");

			ResultSet result = statement.executeQuery();
			int rows = 0;

			while (result.next()) {
				rows++;
			}

			user.setId(++rows);


			// Execute SQL query to add new user to the database
			statement = this.dao.prepareStatement("INSERT INTO USERS (User_ID, First_Name," +
					"Last_Name, User_Type, Username,  Email, Phone_Number, User_Password, Start_Date, Is_Active) VALUE " +
					"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

			statement.setInt(1, user.getId());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getUserType());
			statement.setString(5, user.getUsername());
			statement.setString(6, user.getEmail());
			statement.setString(7, user.getPhoneNum());
			statement.setString(8, user.getPassword());
			statement.setString(9, user.getStartDate().toString());
			statement.setBoolean(10, true);
			statement.executeUpdate();

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
     * Updates a user's information
     * @param userID user's ID number
     * @param updatedInfo user's updated information
     */
    public void editUser(int userID, User updatedInfo) {
		try {
			// Execute SQL query to update the user's information
			PreparedStatement statement = this.dao.prepareStatement("UPDATE USERS SET First_Name = ?, " +
					"Last_Name = ?, User_Type = ?, Username = ?,  Email = ?, Phone_Number = ?, User_Password = ?, " +
					"Start_Date = ?, Is_Active = ?  WHERE User_ID = ?");

			statement.setString(1, updatedInfo.getFirstName());
			statement.setString(2, updatedInfo.getLastName());
			statement.setString(3, updatedInfo.getUserType());
			statement.setString(4, updatedInfo.getUsername());
			statement.setString(5, updatedInfo.getEmail());
			statement.setString(6, updatedInfo.getPhoneNum());
			statement.setString(7, updatedInfo.getPassword());
			statement.setString(8, updatedInfo.getStartDate().toString());
			statement.setBoolean(9, updatedInfo.isActiveStatus());
			statement.setInt(10, userID);
			statement.executeUpdate();

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Updates a user's information
	 * @param userID user's ID number
	 */
	public boolean blockUser(int userID) {
		try {
			// Execute SQL query to update the user's information
			PreparedStatement statement = this.dao.prepareStatement("UPDATE USERS SET Is_Active = false WHERE User_ID = ?");
			statement.setInt(1, userID);
			statement.executeUpdate();
			statement.close();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	
	
	/**
	 * Get the emails of the selected staff members
	 * @param selectedStaff = ArrayList of Integers for the id of each of the selected staff
	 * @return ArrayList<String> of the user emails
	 */
	public ArrayList<String> getEmails(ArrayList<Integer> selectedStaff){
		ArrayList<String> emailAddresses = new ArrayList<String>();
		try {
			String query = "SELECT Email FROM USERS WHERE ";
			for (Integer userID: selectedStaff) {
				query = query + "User_ID=? OR ";
			}
			query = query.substring(0, query.length()-3);
//			System.out.println("query = "+query);
			
			// Create and execute a query to get all emails
			PreparedStatement statement = this.dao.prepareStatement(query);
			int index = 1;
			for (Integer userID: selectedStaff) {
				statement.setInt(index, userID);
				index++;
			}

			ResultSet results = statement.executeQuery();
			
			while (results.next()) {
				emailAddresses.add(results.getString("Email"));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while getting the email addresses from the database");
		}
		
//		System.out.println("Emails = "+emailAddresses);
		return emailAddresses;
		
		
	}
	
	
	
}
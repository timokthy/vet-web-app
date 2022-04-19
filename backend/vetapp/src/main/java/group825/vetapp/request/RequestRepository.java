package group825.vetapp2.request;

import java.sql.*;
import java.util.ArrayList;


import org.springframework.stereotype.Repository;

import group825.vetapp2.database.DatabaseConnection;

/**
 * Repository that stores Request animal information
 *
 * @author Timothy Mok, Yong Jun (Jimmy) Zhu
 * @version 3.0
 * @since December 13, 2021
 */

@Repository("requestRepo")
public class RequestRepository {
	
	/**
	 * Database connection boundary class
	 */
	private final Connection dao;
	
	/**
     * Results of a query to the database
     */
    private ResultSet results;
	
	
	/**
     * Constructor that initializes the RequestsRepository
     * Update the latestID data member holding the max Request ID from the database
     */
	public RequestRepository() throws Exception {
		this.dao = DatabaseConnection.getConnection();
	}
	
	
	/**
	 * Adding a request to the database
	 * @param request = the new request associated with an animal
	 */
	public void addRequest(Request request) throws Exception{

		try {
			// Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("INSERT INTO REQUEST "
            		+ "(Animal_ID, Request_ID, Requester_ID, Request_Date, Checkout_Date, Return_Date, Reason, Request_Status) "
            		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setInt(1, request.getAnimalID());
			statement.setInt(2, request.getRequestID());
        	statement.setInt(3, request.getRequesterID());
        	statement.setString(4, request.getRequestDate());
        	statement.setString(5, request.getCheckoutDate());
        	statement.setString(6, request.getReturnDate());
        	statement.setString(7, request.getReason());
        	statement.setString(8, request.getRequestStatus());
        	statement.executeUpdate();

            statement.close();

		}catch(SQLException e) {
			 e.printStackTrace();
		}	
	}
	
	
	/**
	 * Returns all requests in the system
	 * @return all requests
	 */
	public ArrayList<Request> selectAllRequest() throws Exception{
		String query = "SELECT R2.Animal_ID, R2.Request_ID, R2.Requester_ID, R2.Request_Date, R2.Checkout_Date, R2.Return_Date, "
				+ "R2.Reason, R2.Request_Status, U.First_Name, U.Last_Name,"
				+ "A.Animal_Name, A.Species  FROM REQUEST AS R2, Users as U, ANIMAL AS A "
				+ " WHERE R2.Requester_ID = U.User_ID AND R2.Animal_ID=A.Animal_ID "
				+" ORDER BY R2.Request_ID ASC"+" ;";
		
		ArrayList<Request> requests = new ArrayList<Request>();

        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement(query);
            results = statement.executeQuery();

            // Process the results set and add entries into treatment
            while (results.next()) {
                requests.add(new Request(results.getInt("Animal_ID"), results.getInt("Request_ID"), 
                		results.getInt("Requester_ID"), results.getString("Request_Date"), 
                		results.getString("Checkout_Date"), results.getString("Return_Date"), 
                		results.getString("Reason"), results.getString("Request_Status"),
                		results.getString("First_Name"), results.getString("Last_Name"), 
                		results.getString("Animal_Name"), results.getString("Species")));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
	}
	
	
	/**
	 * View requests made by one specific user
	 * @param id = the id of the user ID whose requests 
	 * @return ArrayList<String> which contains the requests from a particular user or is empty
	 */
	public ArrayList<Request> selectRequestsById(int userID) throws Exception {
		ArrayList<Request> requests = new ArrayList<Request>();
		String query = "SELECT R2.Animal_ID, R2.Request_ID, R2.Requester_ID, R2.Request_Date, R2.Checkout_Date, R2.Return_Date, "
				+ "R2.Reason, R2.Request_Status, U.First_Name, U.Last_Name,"
				+ "A.Animal_Name, A.Species  FROM REQUEST AS R2, Users as U, ANIMAL AS A "
				+ " WHERE R2.Requester_ID = U.User_ID AND R2.Animal_ID=A.Animal_ID AND Requester_ID = ?"
				+" ORDER BY R2.Request_ID ASC"+" ;";
        try {
            // Execute SQL query
//            PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM REQUEST WHERE Requester_ID = ?;");
            PreparedStatement statement = this.dao.prepareStatement(query);
            statement.setInt(1, userID);
            results = statement.executeQuery();

            // Process the results set and add entries into treatment
            while (results.next()) {
                requests.add(new Request(results.getInt("Animal_ID"), results.getInt("Request_ID"), 
                		results.getInt("Requester_ID"), results.getString("Request_Date"), 
                		results.getString("Checkout_Date"), results.getString("Return_Date"), 
                		results.getString("Reason"), results.getString("Request_Status"),
                		results.getString("First_Name"), results.getString("Last_Name"), 
                		results.getString("Animal_Name"), results.getString("Species")));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
	}
	
	
	/**
	 * Remove a specific request from the system
	 * @param id  = id of the request to be removed
	 */
	public void deleteRequestById(int requestID) throws Exception{
		try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("DELETE FROM REQUEST WHERE Request_ID = ?;");
            statement.setInt(1, requestID);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Update an existing request
	 * @param id = the id of the request to be updated
	 * @param update = the Request object with the updated information
	 */
	public void updateRequestById(int requestID, Request update) throws Exception{
		try {
            // Execute SQL query
			// (, , , , , , , )
            PreparedStatement statement = this.dao.prepareStatement("UPDATE REQUEST SET "
            		+ "Animal_ID=?, Request_ID=?, Requester_ID=?, Request_Date=?, Checkout_Date=?,"
            		+ " Return_Date=?, Reason=?, Request_Status=? WHERE Request_ID=?;");
            statement.setInt(1, update.getAnimalID());
			statement.setInt(2, update.getRequestID());
        	statement.setInt(3, update.getRequesterID());
        	statement.setString(4, update.getRequestDate());
        	statement.setString(5, update.getCheckoutDate());
        	statement.setString(6, update.getReturnDate());
        	statement.setString(7, update.getReason());
        	statement.setString(8, update.getRequestStatus());
        	statement.setInt(9, requestID);
        	statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}	
	
}
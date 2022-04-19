package group825.vetapp2.treatment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import group825.vetapp2.database.DatabaseConnection;

/**
 * Repository that stores Treatment information
 *
 * @author Timothy Mok
 * @version 3.0
 * @since December 12, 2021
 */

@Repository("treatmentRepo")
public class TreatmentRepository {
	
	/**
     * Connector to the database
     */
    private final Connection dao;
	
    /**
     * Results of a query to the database
     */
    private ResultSet results;
	
	
	/**
	 * Constructor for the TreatmentRepository that connects to the database
	 * @throws Exception when there is an SQL Exception
	 */
	public TreatmentRepository() throws Exception {
		this.dao = DatabaseConnection.getConnection();
	}
	
	/**
	 * Adds a treatment to an animal
	 * @param treatment the treatment associated with an animal
	 * @throws Exception when there is an SQL Exception
	 */
	public void addTreatment(Treatment treatment) throws Exception{
		try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("INSERT INTO TREATMENT "
            		+ "(Treatment_ID, Treatment_Date, Treatment, Treatment_Description, Treatment_Status, User_ID, Animal_ID) "
            		+ "VALUE (?, ?, ?, ?, ?, ?, ?);");
            statement.setInt(1, treatment.getTreatmentID());
            statement.setString(2, treatment.getTreatmentDate());
            statement.setString(3, treatment.getTreatment());
            statement.setString(4, treatment.getDescription());
            statement.setString(5, treatment.getTreatmentStatus());
            statement.setInt(6, treatment.getUserID());
            statement.setInt(7, treatment.getAnimalID());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * View all treatments selected for an animal
	 * @param animalID the id of the selected animal
	 * @return the treatments for the animal with the requested id
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Treatment> selectTreatmentByAnimalId(int animalID) throws Exception{
		ArrayList<Treatment> treatments = new ArrayList<Treatment>();

        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM TREATMENT WHERE Animal_ID = ?"
            		+ " AND Treatment_Status = ?;");
            statement.setInt(1, animalID);
            statement.setString(2, "Ongoing");
            results = statement.executeQuery();

            // Process the results set and add entries into treatment
            while (results.next()) {
                treatments.add(new Treatment(results.getInt("Treatment_ID"), results.getString("Treatment_Date"), 
                		results.getString("Treatment"), results.getString("Treatment_Description"), 
                		results.getString("Treatment_Status"), results.getInt("User_ID"), results.getInt("Animal_ID")));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treatments;
	}
	
	/**
	 * Find a specific treatment selected by ID
	 * @param animalID the id of the selected animal
	 * @param treatmentID the ID of the treatment requested
	 * @return the specific treatment for an animal
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Treatment> selectTreatmentByTreatmentId(int treatmentID) throws Exception {
		ArrayList<Treatment> treatment = new ArrayList<Treatment>();

        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM TREATMENT WHERE Treatment_ID = ?;");
            statement.setInt(1, treatmentID);
            results = statement.executeQuery();

            // Process the results set and add entries into treatment
            while (results.next()) {
                treatment.add(new Treatment(results.getInt("Treatment_ID"), results.getString("Treatment_Date"), 
                		results.getString("Treatment"), results.getString("Treatment_Description"), 
                		results.getString("Treatment_Status"), results.getInt("User_ID"), results.getInt("Animal_ID")));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treatment;
	}
	
	/**
	 * Remove a specific treatment from the system
	 * @param id the id of the treatment to be removed
	 * @return 1 if removed successfully, 0 otherwise
	 * @throws Exception when there is an SQL Exception
	 */
	public void deleteTreatmentById(int treatmentID) throws Exception{
		try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("DELETE FROM TREATMENT WHERE Treatment_ID = ?;");
            statement.setInt(1, treatmentID);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Update an existing treatment
	 * @param id the id of the treatment to be updated
	 * @param newTreatment the Treatment object with the updated information
	 * @return 1 if successfully updated, 0 otherwise
	 * @throws Exception when there is an SQL Exception
	 */
	public void updateTreatmentById(int treatmentID, Treatment update) throws Exception{
		try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("UPDATE TREATMENT SET "
            		+ "Treatment_ID=?, Treatment_Date=?, Treatment=?, Treatment_Description=?, Treatment_Status=?,"
            		+ " User_ID=?, Animal_ID=? WHERE Treatment_ID=?;");
            statement.setInt(1, update.getTreatmentID());
            statement.setString(2, update.getTreatmentDate());
            statement.setString(3, update.getTreatment());
            statement.setString(4, update.getDescription());
            statement.setString(5, update.getTreatmentStatus());
            statement.setInt(6, update.getUserID());
            statement.setInt(7, update.getAnimalID());
            statement.setInt(8, treatmentID);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
}



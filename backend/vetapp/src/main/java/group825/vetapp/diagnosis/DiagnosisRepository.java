package group825.vetapp2.diagnosis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import group825.vetapp2.database.DatabaseConnection;

/**
 * Repository that stores Diagnosis information
 *
 * @author Timothy Mok, Yong Jun (Jimmy) Zhu
 * @version 3.0
 * @since December 12, 2021
 */

@Repository("diagnosisRepo")
public class DiagnosisRepository {
	
	/**
	 * Connection to SQL database
	 */
	private final Connection dao;
	
	/**
     * Results of a query to the database
     */
    private ResultSet results;
	
	/**
	 * Constructor for DiagnosisRepository
	 * @throws Exception
	 */
	public DiagnosisRepository() throws Exception {
		this.dao = DatabaseConnection.getConnection();
	}
	
	/**
	 * Adds a diagnosis to an animal
	 * @param diagnosis the diagnosis associated with an animal
	 * @return 1 if successfully added, 0 otherwise
	 * @throws Exception when there is an SQL Exception
	 */
	public void addDiagnosis(Diagnosis diagnosis) throws Exception{
		try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("INSERT INTO DIAGNOSIS "
            		+ "(Diagnosis_ID, Diagnosis_Date, Diagnosis, Diagnosis_Description, Diagnosis_Status, User_ID, Animal_ID) "
            		+ "VALUE (?, ?, ?, ?, ?, ?, ?);");
            statement.setInt(1, diagnosis.getDiagnosisID());
            statement.setString(2, diagnosis.getDiagnosisDate());
            statement.setString(3, diagnosis.getDiagnosis());
            statement.setString(4, diagnosis.getDescription());
            statement.setString(5, diagnosis.getDiagnosisStatus());
            statement.setInt(6, diagnosis.getUserID());
            statement.setInt(7, diagnosis.getAnimalID());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * View all specific diagnosis selected by animal id
	 * @param animalID the id of the selected animal
	 * @return the diagnosis of the requested animal
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Diagnosis> selectDiagnosisByAnimalID(int animalID) throws Exception{
		ArrayList<Diagnosis> diagnosiss = new ArrayList<Diagnosis>();

        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM DIAGNOSIS WHERE Animal_ID = ?"
            		+ " AND Diagnosis_Status = ?;");
            statement.setInt(1, animalID);
            statement.setString(2, "Ongoing");
            results = statement.executeQuery();

            // Process the results set and add entries into diagnosis
            while (results.next()) {
                diagnosiss.add(new Diagnosis(results.getInt("Diagnosis_ID"), results.getString("Diagnosis_Date"), 
                		results.getString("Diagnosis"), results.getString("Diagnosis_Description"), 
                		results.getString("Diagnosis_Status"), results.getInt("User_ID"), results.getInt("Animal_ID")));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diagnosiss;
	}
	
	/**
	 * Find a specific diagnosis selected by ID
	 * @param animalID the id of the selected animal
	 * @param diagnosisID the ID of the diagnosis requested
	 * @return the specific diagnosis for an animal
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Diagnosis> selectDiagnosisByDiagnosisID(int diagnosisID) throws Exception {
		ArrayList<Diagnosis> diagnosiss = new ArrayList<Diagnosis>();

        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM DIAGNOSIS WHERE Diagnosis_ID = ?;");
            statement.setInt(1, diagnosisID);
            results = statement.executeQuery();

            // Process the results set and add entries into diagnosis
            while (results.next()) {
                diagnosiss.add(new Diagnosis(results.getInt("Diagnosis_ID"), results.getString("Diagnosis_Date"), 
                		results.getString("Diagnosis"), results.getString("Diagnosis_Description"), 
                		results.getString("Diagnosis_Status"), results.getInt("User_ID"), results.getInt("Animal_ID")));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diagnosiss;
	}
	
	/**
	 * Remove a specific diagnosis from the system
	 * @param id the id of the diagnosis to be removed
	 * @return 1 if removed successfully, 0 otherwise
	 * @throws Exception when there is an SQL Exception
	 */
	public void deleteDiagnosisById(int diagnosisID) throws Exception{
		try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("DELETE FROM DIAGNOSIS WHERE Diagnosis_ID = ?;");
            statement.setInt(1, diagnosisID);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Update an existing diagnosis
	 * @param id the id of the diagnosis to be updated
	 * @param newDiagnosis the Diagnosis object with the updated information
	 * @return 1 if successfully updated, 0 otherwise
	 * @throws Exception when there is an SQL Exception
	 */
	public void updateDiagnosisById(int diagnosisID, Diagnosis update) throws Exception{
		try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("UPDATE DIAGNOSIS SET "
            		+ "Diagnosis_ID=?, Diagnosis_Date=?, Diagnosis=?, Diagnosis_Description=?, Diagnosis_Status=?,"
            		+ " User_ID=?, Animal_ID=? WHERE Diagnosis_ID=?;");
            statement.setInt(1, update.getDiagnosisID());
            statement.setString(2, update.getDiagnosisDate());
            statement.setString(3, update.getDiagnosis());
            statement.setString(4, update.getDescription());
            statement.setString(5, update.getDiagnosisStatus());
            statement.setInt(6, update.getUserID());
            statement.setInt(7, update.getAnimalID());
            statement.setInt(8, diagnosisID);
            
            System.out.println(statement);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}



package group825.vetapp2.weighthistory;

import group825.vetapp2.database.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Repository that stores WeightHistory information
 *
 * @author Aron Saengchan
 * @version 1.0
 * @since December 6, 2021
 */
@Repository("weightHistoryRepo")
public class WeightHistoryRepository {

    /**
     * Connector to the database
     */
    private final Connection dao;

    /**
     * Class constructor that initializes the WeightHistoryRepository
     */
    public WeightHistoryRepository() {
        this.dao = DatabaseConnection.getConnection();
    }

    /**
     * Searches for an animal's weight history by ID number in the database
     * @param animalID animal's ID number
     * @return list containing the response of the query
     * @throws SQLException error when accessing the database
     */
    public ArrayList<Weight> selectWeightHistoryByID(int animalID) {
        ArrayList<Weight> weightHistory = new ArrayList<Weight>();

        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("SELECT * FROM WEIGHT_HISTORY WHERE Animal_ID = ? ORDER BY Date_Recorded DESC;");
            statement.setInt(1, animalID);

            ResultSet results = statement.executeQuery();

            // Process the results set and add entries into weight history
            while (results.next()) {
                weightHistory.add(new Weight(results.getInt("Animal_ID"), results.getString("Date_Recorded"),
                        results.getDouble("Weight"), results.getInt("User_ID")));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weightHistory;
    }

    /**
     * Adds a weight entry to the database
     * @param weight animal's weight entry
     */
    public void addWeight(Weight weight) {
        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("INSERT INTO WEIGHT_HISTORY (Animal_ID, Date_Recorded, Weight, User_ID) VALUE (?, ?, ?, ?);");
            statement.setInt(1, weight.getAnimalId());
            statement.setString(2, weight.getDate());
            statement.setDouble(3, weight.getWeight());
            statement.setInt(4, weight.getUserId());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an animal's weight entry
     * @param weight animal's weight entry
     */
    public void deleteWeight(Weight weight) {
        try {
            // Execute SQL query
            PreparedStatement statement = this.dao.prepareStatement("DELETE FROM WEIGHT_HISTORY WHERE Animal_ID = ? AND Date_Recorded = ?;");
            statement.setInt(1, weight.getAnimalId());
            statement.setString(2, weight.getDate());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
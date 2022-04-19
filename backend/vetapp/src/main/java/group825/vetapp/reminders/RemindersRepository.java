package group825.vetapp2.reminders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import group825.vetapp2.database.DatabaseConnection;
import group825.vetapp2.database.OldDatabaseConnection;
import group825.vetapp2.photos.Photo;

/**
 * Repository that stores Reminder information
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since November 30, 2021
 */
@Repository("tempRemindersRepo")
public class RemindersRepository {
	/**
	 * Table name from database
	 */
	String tableName = "REMINDERS";
	
	
	/**
	 * Connection to the Database 
	 */	
	Connection dao2;
	
	/**
	 * Any query that is sent to the database
	 */
	String query;
	
	/**
	 * Max Reminder ID currently recorded on the database
	 */
	int latestID;
	
	/**
     * Results of a query to the database
     */
    private ResultSet results;
	
	
	public RemindersRepository() throws Exception {
		dao2 = DatabaseConnection.getConnection();
		getLatestReminderID();
	}
	
	/**
	 * Inserts a reminder in the database
	 * @param reminder = new Reminder object for particular animal
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int insertReminder(Reminder reminder) throws Exception{		
		int responseCheck =0;
		getLatestReminderID();
		
		PreparedStatement statement = this.dao2.prepareStatement("INSERT INTO REMINDERS (Animal_ID, Reminder_ID, Date_Created, Author_ID, Notes) VALUES "
				+ "(?, ?, ?, ?, ?)");
		statement.setInt(1, reminder.getAnimalID());
		statement.setInt(2, (this.latestID+1));
		statement.setString(3, reminder.getDateCreated());
		statement.setInt(4, reminder.getAuthorID());
		statement.setString(5, reminder.getNote());
		
		responseCheck = statement.executeUpdate();
		statement.close();
		return responseCheck;
	}
	
	
	/**
	 * Selects reminders in the database by animal ID number
	 * @param id = id pertaining to specific animal
	 * @return ArrayList<String> object which contains the reminders of particular animal or is empty
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Reminder> selectRemindersByID(int animalID) throws Exception {		
		ArrayList<Reminder> animalReminders = new ArrayList<Reminder>();
		
		PreparedStatement statement = this.dao2.prepareStatement("SELECT R.Animal_ID, R.Reminder_ID, R.Date_Created, R.Author_ID, R.Notes, "
				+"U.First_Name, U.Last_Name, U.User_Type FROM REMINDERS AS R, USERS AS U "
				+"  WHERE R.Author_ID = U.User_ID AND R.Animal_ID=? ORDER BY Date_Created desc;");
		statement.setInt(1, animalID);
		results = statement.executeQuery();
		
		while (results.next()) {
			animalReminders.add(new Reminder(results.getInt("Animal_ID"), results.getInt("Reminder_ID"), results.getString("Date_Created"), 
					results.getInt("Author_ID"), results.getString("Notes"), results.getString("First_Name"), results.getString("Last_Name"),
					results.getString("User_Type")));
        }
		statement.close();
		return animalReminders;
	}
	
	/**
	 * Deletes a reminder from the database by reminder ID number
	 * @param id = id pertaining to specific reminder
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int deleteReminderByID(int reminderID) throws Exception {		
		PreparedStatement statement = this.dao2.prepareStatement("DELETE FROM "+ tableName + " AS R WHERE R.Reminder_ID=?;");
		statement.setInt(1, reminderID);
		int responseCheck = statement.executeUpdate();
		statement.close();
		
		return responseCheck;
	}
	
	
	
	/**
	 * get the latest ID for the primary key for Reminders objects from database
	 * @throws Exception when there is an SQL Exception
	 */
	private void getLatestReminderID() throws Exception{
		PreparedStatement statement = this.dao2.prepareStatement("SELECT MAX(Reminder_ID) FROM REMINDERS");
		results = statement.executeQuery();
		results.next();
		
		this.latestID = results.getInt("Max(Reminder_ID)");
		statement.close();
	}
	

}
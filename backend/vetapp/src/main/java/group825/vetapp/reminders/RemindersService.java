package group825.vetapp2.reminders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * Service that performs Reminder requests
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since November 30, 2021
 */
@Service
public class RemindersService {

	/**
	 * Reminder repository that accesses the database
	 */
	private final RemindersRepository dbReminders;
	
	/**
	 * Reminder repository that accesses the database
	 * @param dbReminders repository denoted with Qualifier annotation storing the Reminder objects
	 */
	@Autowired
	public RemindersService(@Qualifier("tempRemindersRepo") RemindersRepository dbReminders) {
		this.dbReminders = dbReminders;
	}

	/**
	 * Inserts a reminder in the database
	 * @param reminder new Reminder object to be added
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int insertReminder(Reminder reminder ) throws Exception {
		return dbReminders.insertReminder(reminder);
	}

	
	/**
	 * Selects a reminder in the database by ID number
	 * @param id int pertaining to specific animal
	 * @return List<Reminder> either containing the Reminder object for particular animal or is empty
	 * @throws Exception when there is an SQL Exception
	 */
	public List<Reminder> selectRemindersByID(int id) throws Exception {
		ArrayList<Reminder> results =  dbReminders.selectRemindersByID(id);
		return results;
	}
	
	/** Deletes a reminder from the database by ID number
	 * @param id int pertaining to specific animal
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int deleteReminderByID(int id) throws Exception{
		return dbReminders.deleteReminderByID(id);
	}
	


	
	
}
package group825.vetapp2.reminders;

import java.util.List;
import java.util.UUID;

import group825.vetapp2.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles Reminder requests
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since November 30, 2021
 */
@CrossOrigin
@RequestMapping("app/reminders")
@RestController
public class RemindersController {

	/**
	 * Reminder service that performs the request
	 */
	private final RemindersService remindersService;

	/**
	 * Constructor that initializes the ReminderController
	 * @param remindersService RemindersService object which interacts with the "RemindersRepository" Class
	 */
	@Autowired
	public RemindersController(RemindersService remindersService) {
		this.remindersService = remindersService;
	}

	/**
	 * 'POST' mapping that adds a reminder to the database
	 * @param reminder RequestBody JSON object to be passed to the Reminder class where the JSON keys are already mapped to specific data members
	 * @throws Exception when there is an SQL Exception
	 */
	@PostMapping
	public void addReminder(@RequestBody Reminder reminder) throws Exception{
		if (reminder.anyNulls()) {
			throw new ApiRequestException("Data members cannot be null! Check the Request Body being sent.");
		}
		remindersService.insertReminder(reminder);
	}

	
	/**
	 * 'GET' mapping that selects a photo by ID number from the database
	 * @param idStr = String path variable obtained by path denoted inside the GetMapping annotation
	 * @return Reminder object or throw exception
	 */
	@GetMapping(path="/animal/{animalID}") 
	public List<Reminder> selectRemindersById(@PathVariable("animalID") String animalID) {
		try {
			//id of animal
			int id = Integer.valueOf(animalID);
			return remindersService.selectRemindersByID(id);
		} catch (Exception e) {
			e.printStackTrace();
			// Catch if id is not a valid Animal ID from Database
			throw new InvalidIdException();
		}
	}

	/**
	 * 'DELETE' mapping that deletes a reminder by ID number from the database
	 * @param reminderID = String path variable obtained by path denoted inside the DeleteMapping annotation
	 */
	@DeleteMapping(path = "{reminderID}")
	public void deleteReminderById(@PathVariable("reminderID") String reminderID) throws Exception{
		//id of a reminder
		int id = Integer.valueOf(reminderID);
		int numRowsAffected = remindersService.deleteReminderByID(id);
		if (numRowsAffected == 0) {throw new InvalidIdException();}
	}


}
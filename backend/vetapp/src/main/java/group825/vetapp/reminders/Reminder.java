package group825.vetapp2.reminders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

/**
 * Reminder associated with an animal
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since November 30, 2021
 */
@Getter
public class Reminder {

	/**
	 * ID number of an animal
	 */
	private final int animalID;

	/**
	 * ID number of the reminder
	 */
	private final int reminderID;

	/**
	 * Date reminder was created
	 */
	private final String dateCreated;
	
	/**
	 * ID number of the author
	 */
	private final int authorID;

	/**
	 * Information included in the reminder
	 */
	private final String note;
	
	/**
	 * First name of the user who made the reminder
	 */
	private final String firstName;
	
	/**
	 * Last name of the user who made the reminder
	 */
	private final String lastName;
	
	/**
	 * Type of the user who made the reminder
	 */
	private final String userType;

	/**
	 * Constructor that initializes the Reminder
	 * @param animalID = ID of particular animal
	 * @param reminderID = ID number for specific reminder belong to a particular animal
	 * @param status = status recorded in the reminder
	 * @param dateDue = date by which reminder should be completed
	 * @param datePerformed = date that the reminder was created
	 * @param note = information to be included in reminder
	 * @param firstName = first name of the user who made the reminder
	 * @param lastName = last name of the user who made the reminder
	 * @param userType = type of the user who made the reminder
	 */
	public Reminder(@JsonProperty("animalID") int animalID, @JsonProperty("reminderID") int reminderID, 
			@JsonProperty("dateCreated") String dateCreated, @JsonProperty("authorID") int authorID, 
			@JsonProperty("note") String note, @JsonProperty("firstName") String firstName, 
			@JsonProperty("lastName") String lastName, @JsonProperty("userType") String userType) {
		this.animalID = animalID;
		this.reminderID = reminderID;
		this.dateCreated = dateCreated;
		this.authorID = authorID;
		this.note = note;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
	}

	/**
	 * Checks if any data members is null
	 * @return boolean confirming if any data members are null
	 */
	public boolean anyNulls() {
		if (animalID == 0 || reminderID == 0 || dateCreated == null 
				|| authorID ==0 || note == null) {
			return true;
		}
		
		return false;
	}
	
	@Override 
	public String toString() {
		String newString = "{ animalID: " + animalID + ", reminderID: " + reminderID + 
				 ", authorID: " + authorID + ", note: " + note + "}";
	 return newString;
	}
	
}
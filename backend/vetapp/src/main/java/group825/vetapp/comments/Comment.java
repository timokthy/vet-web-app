package group825.vetapp2.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Comment that displays a message for an animal
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since November 28, 2021
 */
@Getter
public class Comment {

	/**
	 * ID number of the associated animal
	 */
	private final int animalID;

	/**
	 * ID number of the comment
	 */
	private final int commentID;

	/**
	 * ID number of the comment author
	 */
	private final int authorID;

	/**
	 * Timestamp of the comment
	 */
	private final String timestamp;

	/**
	 * Message of the comment
	 */
	private final String message;
	
	/**
	 * First name of the user who made the comment
	 */
	private final String firstName;
	
	/**
	 * Last name of the user who made the comment
	 */
	private final String lastName;
	
	/**
	 * The type of user who made the comment
	 */
	private final String userType;
	
	

	/**
	 * Constructor that initializes the Comment
	 * @param animalID = int id of a particular animal
	 * @param commentID = id number for comment
	 * @param authorID = id number for user that made the comment
	 * @param timestamp = timestamp for when the comment was created
	 * @param message = message from the user
	 * @param firstName = first name of the user
	 * @param lastName = last name of the user
	 * @param userType = account type of the user
	 */
	public Comment(@JsonProperty("animalID") int animalID, @JsonProperty("commentID") int commentID,
				   @JsonProperty("authorID") int authorID, @JsonProperty("timestamp") String timestamp,
				   @JsonProperty("message") String message, @JsonProperty("firstName") String firstName,
				   @JsonProperty("lastName") String lastName, @JsonProperty("userType") String userType) {
		this.animalID = animalID;
		this.commentID = commentID;
		this.authorID = authorID;
		this.timestamp = timestamp;
		this.message = message;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
	}

	/**
	 * Checks if any required data members is null
	 * @return boolean confirming if any data members are null
	 */
	public boolean anyNulls() {
		if (commentID==0 || authorID == 0 || timestamp == null || message == null || firstName ==null || lastName==null ||userType==null) {
			return true;
		}
		
		return  false;
	}
	
	 @Override
	    public String toString() {
		 String newString = "{ animalID: " + animalID + ", commentID: " + commentID + ", authorID: " + authorID + 
				 ", timestamp: " + timestamp + ", message: " + message + ", firstName: " + firstName 
				 + ", lastName: " + lastName + ", userType: " + userType + "}";	    	
		 return newString;
	    }
	 
	
}
package group825.vetapp2.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Request to check out an animal
 *
 * @author Timothy Mok, Yong Jun (Jimmy) Zhu
 * @version 3.0
 * @since December 13, 2021
 */

@Getter
@Setter
public class Request {

	/**
	 * ID number of the animal this request pertains to
	 */
	private int animalID;

	/**
	 * ID number for the request
	 */
	private int requestID;
	
	/**
	 * ID number of the user who made the request
	 */
	private int requesterID;
	
	/**
	 * Date the request was made
	 */
	private String requestDate;
	
	/**
	 * Date the animal was checked out
	 */
	private String checkoutDate;
	
	/**
	 * Date the animal was returned
	 */
	private String returnDate;
	
	/**
	 * Reason for the animal request
	 */
	private String reason;
	
	/**
	 * Status of the request
	 */
	private String requestStatus;
	
	/**
	 * Requester first name
	 */
	private String requesterFirstName;
	
	
	/**
	 * Requester Last Name
	 */
	private String requesterLastName;
	
	/**
	 * Animal name
	 */
	private String animalName;
	
	/**
	 * Animal Species
	 */
	private String animalSpecies;
	


	/**
	 * Constructor for Request to check out an animal
	 * @param animalID = ID number of the animal this request pertains to
	 * @param requestID = ID number for the request
	 * @param requesterID = ID number of the user who made the request
	 * @param requestDate = Date the request was made
	 * @param checkoutDate = Date the animal was checked out
	 * @param returnDate = Date the animal was returned
	 * @param reason = Reason for the animal request
	 * @param requestStatus = Status of the request
	 * @param requesterFirstName = First name of the user who made the request
	 * @param requesterLastName = Last name of the user who made the request
	 * @param animalName = Name of the animal being requested
	 * @param animalSpecies = Species of the animal being requested
	 */
	public Request(@JsonProperty("animalID") int animalID, @JsonProperty("requestID") int requestID,
				   @JsonProperty("requesterID") int requesterID, @JsonProperty("requestDate") String requestDate,
				   @JsonProperty("checkoutDate") String checkoutDate, @JsonProperty("returnDate") String returnDate, 
				   @JsonProperty("reason") String reason, @JsonProperty("requestStatus") String requestStatus, 
				   @JsonProperty("requesterFirstName") String requesterFirstName, @JsonProperty("requesterLastName") String requesterLastName,
				   @JsonProperty("animalName") String animalName, @JsonProperty("animalSpecies") String animalSpecies ) {
		this.animalID = animalID;
		this.requestID = requestID;
		this.requesterID = requesterID;
		this.requestDate = requestDate;
		this.checkoutDate = checkoutDate;
		this.returnDate = returnDate;
		this.reason = reason;
		this.requestStatus = requestStatus;
		this.requesterFirstName = requesterFirstName;
		this.requesterLastName = requesterLastName;
		this.animalName = animalName;
		this.animalSpecies = animalSpecies;
	}

	/**
	 * Checks if any of the necessary fields have been left empty
	 * @return true if any of the fields are left empty, false otherwise
	 */
	public boolean anyNulls() {
		return animalID == 0 || requesterID == 0 || requestDate == null ||  requestStatus == null ;
	}
}
package group825.vetapp2.treatment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Treatment of an animal
 *
 * @author Timothy Mok
 * @version 2.0
 * @since December 6, 2021
 */
@Getter
@Setter
public class Treatment {
	
	/**
	 *  An auto-generated ID for each treatment protocol
	 */
	private int treatmentID;
	
	/**
	 * The date the treatment was added to the database
	 */
	private String treatmentDate;
	
	/**
	 * Treatment of the animal
	 */
	private String treatment;
	
	/**
	 * Description of the animal's treatment required
	 */
	private String description;
	
	/**
	 * The status of the treatment - ongoing, complete
	 */
	private String treatmentStatus;
	
	/**
	 * The ID of the user that submitted the treatment
	 */
	private int userID;
	
	/**
	 * ID number of the animal
	 */
	private int animalID;


	/**
	 * Constructor for a Treatment protocol
	 * 
	 * @param treatmentID of the treatment
	 * @param treatmentDate date the treatment was added to the database
	 * @param treatment the ailment associated with the animal
	 * @param description describes the treatment protocol in further detail
	 * @param treatmentStatus status of the treatment - ongoing, complete
	 * @param userID ID of the user that submitted the treatment
	 * @param animalID number of the animal
	 */
	public Treatment(@JsonProperty("treatmentID") int treatmentID,  @JsonProperty("treatmentDate") String treatmentDate,
			@JsonProperty("treatment") String treatment, @JsonProperty("description") String description, 
			@JsonProperty("treatmentStatus") String treatmentStatus, @JsonProperty("userID") int userID,
			@JsonProperty("animalID") int animalID) {
		this.treatmentID = treatmentID;
		this.treatmentDate = treatmentDate;
		this.treatment = treatment;
		this.description = description;
		this.treatmentStatus = treatmentStatus;
		this.userID = userID;
		this.animalID = animalID;
	}

	/**
	 * Checks if any of the fields have been left empty
	 * @return true if any of the fields are left empty, false otherwise
	 */
	public boolean anyNulls() {
		return treatment == null || description == null || treatmentDate==null || userID ==0 || animalID==0 || treatmentStatus==null;
	}
	
	
	
}
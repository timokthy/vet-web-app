package group825.vetapp2.diagnosis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Diagnosis of an animal
 *
 * @author Timothy Mok, Jimmy Zhu
 * @version 2.0
 * @since December 5, 2021
 */
@Getter
@Setter
public class Diagnosis {
	
	/**
	 * ID number of the diagnosis
	 */
	private int diagnosisID;
	
	/**
	 * Diagnosis of the animal
	 */
	private String diagnosis;
	
	/**
	 * Description of the animal's condition
	 */
	private String description;
	
	/**
	 * Date of the diagnosis
	 */
	private String diagnosisDate;
	
	/**
	 * Status of the diagnosis
	 */
	private String diagnosisStatus;
	
	/**
	 * ID number of the animal
	 */
	private int animalID;
	
	/**
	 * ID number of the user who made the diagnosis
	 */
	private int userID;
	



	/**
	 * Constructor for a Diagnosis
	 * @param id the diagnosis UUID
	 * @param animalName the name of the animal being treated
	 * @param diagnosis the ailment associated with the animal
	 * @param description describes the condition in further detail
	 */
	public Diagnosis(@JsonProperty("diagnosisID") int diagnosisID, @JsonProperty("diagnosisDate") String diagnosisDate,
			@JsonProperty("diagnosis") String diagnosis, @JsonProperty("description") String description,  
			@JsonProperty("diagnosisStatus") String diagnosisStatus, @JsonProperty("userID") int userID, @JsonProperty("animalID") int animalID) {
		
		this.diagnosisID = diagnosisID;
		this.diagnosisDate = diagnosisDate;
		this.diagnosis = diagnosis;
		this.description = description;
		this.diagnosisStatus = diagnosisStatus;
		this.userID = userID;
		this.animalID = animalID;
	}

	/**
	 * Checks if any of the fields have been left empty
	 * @return true if any of the fields are left empty, false otherwise
	 */
	public boolean anyNulls() {
		return diagnosis == null || description == null || diagnosisDate==null || userID ==0 || animalID==0 || diagnosisStatus==null;
	}
	
	
	
}
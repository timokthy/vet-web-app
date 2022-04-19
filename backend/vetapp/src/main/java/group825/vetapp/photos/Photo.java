package group825.vetapp2.photos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

/**
 * Photo of an animal
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since Dec 6, 2021
 */
@Getter
public class Photo {

	/**
	 * ID number of the animal
	 */
	private final int animalID;

	/**
	 * ID number of the photo
	 */
	private final int photoID;
	
	/**
	 * ID number of the user who uploaded photo
	 */
	private final int userID;

	/**
	 * Date the photo was uploaded
	 */
	private final String dateUploaded;

	/**
	 * File path of the photo
	 */
	private final String filepath;
	
	



	/**
	 * Constructor that initializes the Photo
	 * @param animalID = id of particular animal
	 * @param photoID = id of the photo belonging to a particular animal
	 * @param userID = id of the user who uploaded the photo
	 * @param filepath file path for where the photo is stored
	 * @param dateUploaded = date when the photo was uploaded
	 */
	public Photo(@JsonProperty("animalID") int animalID, @JsonProperty("photoID") int photoID,
			@JsonProperty("filepath") String filepath, @JsonProperty("userID") int userID,
			@JsonProperty("dateUploaded") String dateUploaded) {
		this.animalID = animalID;
		this.photoID = photoID;
		this.dateUploaded = dateUploaded;
		this.filepath = filepath;
		this.userID = userID;
	}
	
	/**
	 * Checks if any data members is null
	 * @return boolean confirming if any data members are null
	 */
	public boolean anyNulls() {
		if (photoID == 0 || animalID ==0 || userID==0 || dateUploaded == null || filepath == null  ) {
			return true;
		} 
		return  false;
	}
	
	@Override 
	public String toString() { 
		String newString = "{ animalID: " + animalID + ", photoID: " + photoID + ", dateUploaded: " + dateUploaded 
				+ ", filepath: " + filepath  + ", userID: " + userID + "}";
		return newString;
	}
	
}

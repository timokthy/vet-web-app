package group825.vetapp2.animal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Animal registered in the veterinary department
 *
 * @author Aron Saengchan, Jimmy Zhu
 * @version 2.0
 * @since November 30, 2021
 */
@Getter
@Setter
public class Animal {

    /**
     * ID number of the animal for the database
     */
    private int animalID;

    /**
     * Name of the animal
     */
    private String name;

    /**
     * Species of the animal
     */
    private String species;

    /**
     * Breed of animal
     */
    private String breed;

    /**
     * Tattoo of the animal
     */
    private int tattoo;

    /**
     * City tattoo of the animal
     */
    private String cityTattoo;

    /**
     * Date of birth of the animal
     */
    private LocalDate dob;

    /**
     * Sex of the animal
     */
    private char sex;
    
    /**
     * RFID of the Animal
     */
    private String rfid;
    
    /**
     * Microchip number of the animal
     */
    private String microchip;
    
    /**
     * Health status of animal
     */
    private String healthStatus;

    /**
     * Availability status of animal
     */
    private boolean availabilityStatus;
    
    /**
     * Colour of  the animal
     */
    private String colour;
    
    /**
     * Additional information on the animal
     */
    private String additionalInfo;
    
    /**
     * Length of the animal's name
     */
    private int nameLength;

    /**
     * Search Key for the name used in search algorithm
     * Combination of letters and numbers which is used to calculate the similarity
     * based on the presence and quantities of the letters between the user's entered
     * search name and the names of the animals on the database
     */
    private String searchKeyName;

    /**
     * Constructor that initializes the Animal with a search key
     * @param animalID animal's ID number
     * @param name animal's name
     * @param species animal's species
     * @param breed animal's breed
     * @param tattoo animal's tattoo
     * @param cityTattoo animal's city tattoo
     * @param sex animal's sex
     * @param dob animal's date of birth
     * @param rfid animal's RFID
     * @param microchip animal's microchip number
     * @param healthStatus animal's health status
     * @param availabilityStatus animal's availability status
     * @param colour animal's colour
     * @param additionalInfo additional information on the animal
     */
    public Animal(@JsonProperty("animalID") int animalID, @JsonProperty("name") String name,
    		 	  @JsonProperty("species") String species, @JsonProperty("breed") String breed,
                  @JsonProperty("tattoo") int tattoo, @JsonProperty("cityTattoo") String cityTattoo,
                  @JsonProperty("dob") LocalDate dob, @JsonProperty("sex") char sex,
                  @JsonProperty("rfid") String rfid, @JsonProperty("microchip") String microchip,
                  @JsonProperty("healthStatus") String healthStatus, @JsonProperty("availabilityStatus") boolean availabilityStatus,
                  @JsonProperty("colour") String colour, @JsonProperty("additionalInfo") String additionalInfo,
                  @JsonProperty("nameLength") int nameLength, @JsonProperty("searchKeyName") String searchKeyName) {
        this.animalID = animalID;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.tattoo = tattoo;
        this.cityTattoo = cityTattoo;
        this.dob = dob;
        this.sex = sex;
        this.rfid = rfid;
        this.microchip = microchip;
        this.healthStatus = healthStatus;
        this.availabilityStatus = availabilityStatus;
        this.colour = colour;
        this.additionalInfo = additionalInfo;
        this.nameLength = nameLength;
        this.searchKeyName = searchKeyName;

        updateSearchKey();
    }

    /**
     * Checks if any required attributes are null
     * @return true if at least one attribute is null, false otherwise
     */
    public boolean anyNulls() {
        return (this.name == null || this.species == null || this.breed == null || this.tattoo == 0 ||
                this.cityTattoo == null || this.dob == null || this.rfid == "" || this.microchip == "" || this.colour == null);
    }
    
    /**
     * Update the search key name by generating it
     */
    private void updateSearchKey() {
    	if (this.nameLength == 0) {this.nameLength = this.name.length();}
    	if (this.searchKeyName == null) {this.searchKeyName = SearchKey.generateSearchKey(this.name); }
    }
    
    @Override 
    public String toString() {
    	String newString = "{ animalID: " + animalID + ", name: " + name + ", breed: " + breed + ", species: " + species + ", sex: " + sex +  ", tattoo: " + tattoo + ", cityTattoo: " + cityTattoo + ", dob: " + dob 
    + ", rfid: " + rfid + ", microchip: " + microchip 
    + ", nameLength: " + nameLength + ", searchKeyName: " + searchKeyName + "}";
     return newString;
    }
}
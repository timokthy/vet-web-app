package group825.vetapp2.weighthistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Weight of an animal
 *
 * @author Aron Saengchan
 * @version 1.0
 * @since December 6, 2021
 */
@Getter
@Setter
public class Weight {

    /**
     * ID number of the animal
     */
    private int animalId;

    /**
     * Date the weight was logged
     */
    private String date;

    /**
     * Weight of the animal
     */
    private double weight;

    /**
     * ID number of the user who enters the weight
     */
    private int userId;

    /**
     * Constructor that initializes the WeightHistory
     * @param animalId animal's ID number
     * @param date date weight was logged
     * @param weight animal's weight
     */
    public Weight(@JsonProperty("animalId") int animalId, @JsonProperty("date") String date,
                  @JsonProperty("weight") double weight, @JsonProperty("userId") int userId) {
        this.animalId = animalId;
        this.date = date;
        this.weight = weight;
        this.userId = userId;
    }

    /**
     * Checks if any attributes are null
     * @return true if at least one attribute is null, false otherwise
     */
    public boolean anyNulls() {
        return this.animalId == 0 || this.date == null || this.weight == 0.0;
    }
}
package group825.vetapp2.weighthistory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
/**
 * Service that performs WeightHistory requests
 *
 * @author Aron Saengchan
 * @version 1.0
 * @since December 6, 2021
 */
@Service
public class WeightHistoryService {

    /**
     * Weight history repository that accesses the database
     */
    private final WeightHistoryRepository repo;

    /**
     * Constructor that initializes the WeightHistoryService
     * @param repo repository that accesses the database
     */
    public WeightHistoryService(@Qualifier("weightHistoryRepo") WeightHistoryRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves the weight history of a specified animal
     * @return a list containing the weight history of an animal
     */
    public ArrayList<Weight> selectWeightHistoryByID(int animalID) {
        return this.repo.selectWeightHistoryByID(animalID);
    }

    /**
     * Adds a weight entry to the database
     * @param weight animal's weight entry
     */
    public void addWeight(Weight weight) {
        this.repo.addWeight(weight);
    }

    /**
     * Deletes an animal's weight entry
     * @param weight animal's weight entry
     * @throws Exception error when accessing the database
     */
    public void deleteWeight(Weight weight) {
        this.repo.deleteWeight(weight);
    }
}
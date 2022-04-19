package group825.vetapp2.weighthistory;

import group825.vetapp2.exceptions.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller that handles WeightHistoryRequests requests
 *
 * @author Aron Saengchan
 * @version 1.0
 * @since December 6, 2021
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/app/animal/weight-history")
public class WeightHistoryController {

    /**
     * Weight history service that performs the request
     */
    private final WeightHistoryService service;

    /**
     * Constructor that initializes the WeightHistoryController
     * @param weightService service that performs the request
     */
    @Autowired
    public WeightHistoryController(WeightHistoryService weightService) {
        this.service = weightService;
    }

    /**
     * 'GET' request that retrieves the weight history of a specified animal
     * @return weight history of an animal
     */
    @GetMapping(path = "/{animalID}")
    public ArrayList<Weight> selectWeightHistoryById(@PathVariable("animalID") int animalID) {
        return this.service.selectWeightHistoryByID(animalID);
    }

    /**
     * 'POST' request that adds a weight entry to the database
     * @param weight weight entry to be added
     */
    @PostMapping
    public void addWeight(@RequestBody Weight weight) {
        // Checks if any user fields are 'null'
        if (weight.anyNulls()) {
            throw new ApiRequestException("Fields cannot be null");
        }

        this.service.addWeight(weight);
    }

    /**
     * 'DELETE' request that deletes a weight entry
     * @param weight an animal's weight entry
     */
    @DeleteMapping()
    public void deleteWeight(@RequestBody Weight weight) {
        this.service.deleteWeight(weight);
    }
}
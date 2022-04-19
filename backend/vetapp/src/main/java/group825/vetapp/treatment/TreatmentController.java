package group825.vetapp2.treatment;

import java.util.List;

import group825.vetapp2.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles Treatment requests
 *
 * @author Timothy Mok
 * @version 2.0
 * @since December 6, 2021
 */

@CrossOrigin
@RequestMapping("app/treatment/protocol")
@RestController
public class TreatmentController {
	
	/**
	 * The treatment service to be used by the controller
	 */
	private final TreatmentService treatmentService;

	/**
	 * Constructor for TreatmentController
	 * @param treatmentService - service to be used to send updates to Repository
	 */
	@Autowired
	public TreatmentController(TreatmentService treatmentService) {
		this.treatmentService = treatmentService;
	}
	
	/**
	 * Send request to add a treatment for an animal and checks that all necessary fields are filled out
	 * @param treatment the treatment
	 * @throws Exception when there is an SQL Exception
	 */
	@PostMapping(path = "/animalID={animalID}") 
	public void addTreatment(@RequestBody Treatment treatment) throws Exception {
		if (treatment.anyNulls()) {
			throw new ApiRequestException("At least one treatment field is null");
		}
		treatmentService.addTreatment(treatment);
	}
	
	/**
	 * Displays all the treatment associated with an animal
	 * @param animalID the ID of the requested animal
	 * @return the requested animal
	 * @throws Exception when there is an SQL Exception
	 */
	@GetMapping(path = "/animalID={animalID}") 
	public List<Treatment> selectTreatmentByAnimalId(@PathVariable("animalID") String animalID) throws Exception {
		try {
			//id of animal
			int id = Integer.valueOf(animalID);
			System.out.println(treatmentService.selectTreatmentByAnimalId(id));
			return treatmentService.selectTreatmentByAnimalId(id);
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidIdException();
        }
	}
	
	/**
	 * Gets a specific treatment associated with an animal
	 * @param treatmentID the ID of the requested treatment
	 * @return the requested treatment
	 * @throws Exception when there is an SQL Exception
	 */
	@GetMapping(path = "/treatmentID={treatmentID}") 
	public List<Treatment> selectTreatmentByTreatmentId(@PathVariable("treatmentID") String treatmentID) throws Exception {
		try {
			int tID = Integer.valueOf(treatmentID);
			System.out.println(treatmentService.selectTreatmentByTreatmentId(tID));
			return treatmentService.selectTreatmentByTreatmentId(tID);
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidIdException();
        }
	}
	
	/**
	 * Delete an existing treatment in the database and checks that the treatment ID is valid
	 * @param treatmentID the ID of the treatment to be deleted
	 * @throws Exception when there is an SQL Exception
	 */
	@DeleteMapping(path = "/treatmentID={treatmentID}")
	public void deleteTreatmentById(@PathVariable("treatmentID") String treatmentID) throws Exception{
		try {
			int id = Integer.valueOf(treatmentID);
            treatmentService.deleteTreatmentById(id);
        } catch(java.lang.IllegalArgumentException e) {
            throw new InvalidIdException();
        }
	}
	
	/**
	 * Update an existing treatment in the database and checks that the treatment ID is valid
	 * @param treatmentId the ID of the treatment to be updated
	 * @param treatmentToUpdate treatment object with updated information
	 * @throws Exception when there is an SQL Exception
	 */
	@PutMapping(path = "treatmentID={treatmentID}")
	public void updateTreatmentById(@PathVariable("treatmentID") String treatmentId, 
			@RequestBody Treatment treatmentToUpdate) throws Exception{	
		try {
			int id = Integer.valueOf(treatmentId);
            if (treatmentToUpdate.anyNulls()) {
    			throw new ApiRequestException("At least one treatment field is null");
    		}
            treatmentService.updateTreatmentById(id, treatmentToUpdate);
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidIdException();
        }
	}
}
package group825.vetapp2.animal;

import org.springframework.web.bind.annotation.*;

import group825.vetapp2.exceptions.ApiRequestException;
import group825.vetapp2.exceptions.InvalidIdException;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that handles Animal requests
 *
 * @author Aron Saengchan, Jimmy Zhu
 * @version 2.0
 * @since November 30, 2021
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/app/animal")
public class AnimalController {

    /**
     * Animal service that performs the request
     */
    private final AnimalService service;

    /**
     * Constructor that initializes the AnimalController
     * @param service service that performs the request
     */
    public AnimalController(AnimalService service) {
        this.service = service;
    }


    /**
     * 'GET' mapping that retrieves all animals in the database
     */
    @GetMapping
    public ArrayList<Animal> selectAllAnimals() {
        return this.service.selectAllAnimals();
    }
    
    /**
	 * 'GET' mapping that retrieves an animal in the database
	 * @param animalID animal's ID
	 */
	@GetMapping(path="/{animalID}")
	public Animal searchAnimalById(@PathVariable("animalID") int animalID) {
		return this.service.searchAnimalById(animalID);
	}

    /**
     * 'POST' request that adds an animal to the database
     * @param animal animal to be added
     */
    @PostMapping(path = "/add-animal")
    public void addAnimal(@RequestBody Animal animal) {
        this.service.addAnimal(animal);
    }

    /**
     * 'PUT' request that updates an animal's information
     * @param animalID animal's ID number
     * @param updatedInfo animal's updated information
     */
    @PutMapping(path = "/{id}")
    public void updateAnimal(@PathVariable("id") int animalID, @RequestBody Animal updatedInfo) {
		if (updatedInfo.anyNulls()) {
			throw new ApiRequestException("Data members cannot be null.");
		} else {
            this.service.updateAnimal(animalID, updatedInfo);
        }
    }

    /**
     * 'GET' request that searches for animals from the database based on the name and/or species
     * @param name = animal's name
     * @return List of Animal objects which have names similar to the name parameter
     */
    @GetMapping(path = "/searchAvailable")
    public List<Animal> searchAnimalAvailable(@RequestParam(required = true) String name) throws Exception{
        if (name != null) {
            boolean onlyAvailableAnimals = true;
            return this.service.searchAnimalByName(name, onlyAvailableAnimals);
        }
        return null;
    }

    /**
     * 'GET' request that searches for animals from the database based on animalID or based on the name with/without species
     * @param name = animal's name
     * @param species = animal's species
     * @param animalID = animal's ID number
     * @return List<Animal> where the Animal match by animalID, similarity by name, or similarity by name and species
     */
    @GetMapping(path = "/search")
    public List<Animal> searchAnimal(@RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "animalID", required = false) String animalID) throws Exception{
        
    	String species = null;
    	//return list of single animal or none identified by unique animal id
        if (animalID != null) {
            int id = Integer.valueOf(animalID);
            ArrayList<Animal> animalById = new ArrayList<Animal>();
            animalById.add(this.service.searchAnimalById(id));
            return animalById;
        }
        //return list of multiple animals based on similarity by name or by name and species
        else if (name != null) {
        	
            return this.service.searchAnimalByName(name,  false);
        }
        return null;
    }
}
package group825.vetapp2.animal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that performs Animal requests
 *
 * @author Aron Saengchan, Jimmy Zhu
 * @version 2.0
 * @since November 30, 2021
 */
@Service
public class AnimalService {
    /**
     * Animal repository that accesses the database
     */
    private final AnimalRepository repo;

    /**
     * Constructor that initializes the AnimalService
     * @param repo repository that accesses the database
     */
    public AnimalService(@Qualifier("animalRepo") AnimalRepository repo) {
        this.repo = repo;
    }


    /**
     * Selects all animals in the database
     */
    public ArrayList<Animal> selectAllAnimals() {
        return this.repo.selectAllAnimals();
    }

    /**
     * Searches for an animal by ID number in the database
     * @param animalID animal's ID number
     * @return animal if found, null otherwise
     */
    public Animal searchAnimalById(int animalID) {
        return this.repo.searchAnimalById(animalID);
    }

    /**
     * Adds an animal to the database
     * @param animal animal to be added
     */
    public void addAnimal(Animal animal) {
        this.repo.addAnimal(animal);
    }

    /**
     * Updates an animal's information
     * @param animalID animal's ID number
     * @param animalInfo animal's updated information
     */
    public void updateAnimal(int animalID, Animal animalInfo) {
		this.repo.updateAnimal(animalID, animalInfo);
	}

    /**
     * Searches for an animal by name in the database
     * @param name = animal's name
     * @species = species of animal
     * @param onlyAvailableAnimals = whether to check for only available animals or for all animals
     * @return specified animal if found, null otherwise
     */
    public List<Animal> searchAnimalByName(String name, boolean onlyAvailableAnimals) throws Exception{
        ArrayList<Animal> foundResults = this.repo.searchAnimalByName(name, onlyAvailableAnimals);
        return foundResults;
    }


}

package group825.vetapp2.treatment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * Service that performs Treatment requests
 *
 * @author Timothy Mok
 * @version 3.0
 * @since December 12, 2021
 */
@Service
public class TreatmentService {

	/**
	 * Treatment repository that accesses the database
	 */
	private final TreatmentRepository repo;

	/**
	 * Constructor for the TreatmentService to communicate between the Repository and Controller
	 * @param repo the TreatmentRepository
	 */
	public TreatmentService(@Qualifier("treatmentRepo") TreatmentRepository repo) {
		this.repo = repo;
	}

	/**
	 * Service for adding a treatment to the system
	 * @param treatment treatment to be added
	 * @return whether the treatment was successfully added to the Repository
	 * @throws Exception when there is an SQL Exception
	 */
	public void addTreatment(Treatment treatment) throws Exception{
		this.repo.addTreatment(treatment);
	}
	
	/**
	 * Search for treatments for a specific animal in the Repository
	 * @param animalID id of the animal
	 * @return the treatments from the repository
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Treatment> selectTreatmentByAnimalId(int animalID) throws Exception {
		return this.repo.selectTreatmentByAnimalId(animalID);
	}
	
	/**
	 * Search for a specific for a specific animal in the Repository
	 * @param treatmentID the treatment ID requested
	 * @return the single treatment from the repository
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Treatment> selectTreatmentByTreatmentId(int treatmentID) throws Exception {
		return this.repo.selectTreatmentByTreatmentId(treatmentID);
	}
	
	/**
	 * Delete a treatment from the Repository
	 * @param treatmentID treatment to be deleted
	 * @return whether the treatment was successfully deleted from the Repository
	 * @throws Exception when there is an SQL Exception
	 */
	public void deleteTreatmentById(int treatmentID) throws Exception{
		this.repo.deleteTreatmentById(treatmentID);
	}
	
	/**
	 * Update an existing treatment in the Repository
	 * @param treatmentID treatment to be updated
	 * @param treatment treatment object containing new information
	 * @return whether the treatment was successfully updated the Repository
	 * @throws Exception when there is an SQL Exception
	 */
	public void updateTreatmentById(int treatmentID, Treatment treatment) throws Exception{
		this.repo.updateTreatmentById(treatmentID, treatment);
	}
	
}
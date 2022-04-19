package group825.vetapp2.diagnosis;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * Service that performs Diagnosis requests
 *
 * @author Timothy Mok, Yong Jun (Jimmy) Zhu
 * @version 3.0
 * @since December 12, 2021
 */
@Service
public class DiagnosisService {

	/**
	 * Diagnosis repository that accesses the database
	 */
	private final DiagnosisRepository repo;

	/**
	 * Constructor for the DiagnosisService to communicate between the Repository and Controller
	 * @param repo the DiagnosisRepository
	 */
	public DiagnosisService(@Qualifier("diagnosisRepo") DiagnosisRepository repo) {
		this.repo = repo;
	}

	/**
	 * Service for adding a diagnosis to the system
	 * @param diagnosis diagnosis to be added
	 * @return whether the diagnosis was successfully added to the Repository
	 * @throws Exception when there is an SQL Exception
	 */
	public void addDiagnosis(Diagnosis diagnosis) throws Exception{
		this.repo.addDiagnosis(diagnosis);
	}
	
	/**
	 * Search for diagnoses for a specific animal in the Repository
	 * @param animalID id of the animal
	 * @return the diagnoses from the repository
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Diagnosis> selectDiagnosisByAnimalID(int animalID) throws Exception {
		return this.repo.selectDiagnosisByAnimalID(animalID);
	}
	
	/**
	 * Search for a specific for a specific animal in the Repository
	 * @param animalID id of the animal
	 * @param diagnosisID the diagnosis ID requested
	 * @return the single diagnosis from the repository
	 * @throws Exception when there is an SQL Exception
	 */
	public ArrayList<Diagnosis> selectDiagnosisByDiagnosisID(int diagnosisID) throws Exception {
		return this.repo.selectDiagnosisByDiagnosisID(diagnosisID);
	}
	
	/**
	 * Delete a diagnosis from the Repository
	 * @param id diagnosis to be deleted
	 * @return whether the diagnosis was successfully deleted from the Repository
	 * @throws Exception when there is an SQL Exception
	 */
	public void deleteDiagnosisById(int diagnosisID) throws Exception{
		this.repo.deleteDiagnosisById(diagnosisID);
	}
	
	/**
	 * Update an existing diagnosis in the Repository
	 * @param id diagnosis to be updated
	 * @param diagnosis diagnosis object containing new information
	 * @return whether the diagnosis was successfully updated the Repository
	 * @throws Exception when there is an SQL Exception
	 */
	public void updateDiagnosisById(int diagnosisID, Diagnosis diagnosis) throws Exception{
		this.repo.updateDiagnosisById(diagnosisID, diagnosis);
	}
}
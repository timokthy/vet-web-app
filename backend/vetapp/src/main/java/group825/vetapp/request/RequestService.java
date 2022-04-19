package group825.vetapp2.request;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



/**
 * Service that performs Request animal requests
 *
 * @author Timothy Mok, Yong Jun (Jimmy) Zhu
 * @version 3.0
 * @since December 13, 2021
 */
@Service
public class RequestService {

	/**
	 * Request animal repository that accesses the database
	 */
	private final RequestRepository repo;

	/**
	 * Constructor for the RequestService to communicate between the Repository and Controller
	 * @param repo the RequestRepository
	 */
	public RequestService(@Qualifier("requestRepo") RequestRepository repo) {
		this.repo = repo;
	}

	/**
	 * Service for adding a request to the system
	 * @param request = request to be added
	 * @return whether the request was successfully added to the Repository
	 */
	public void addRequest(Request request) throws Exception {
		this.repo.addRequest(request);
	}
	
	/**
	 * List all animal requests in the system
	 * @return a list of all requests associated with an animal
	 */
	public ArrayList<Request> selectAllRequest() throws Exception{
		return this.repo.selectAllRequest();
	}
	
	/**
	 * Search for a specific request in the Repository
	 * @param userID = ID number of user whose requests are being returned
	 * @return the list of requests from the repository
	 */
	public ArrayList<Request> selectRequestsById(int userID) throws Exception{
		return this.repo.selectRequestsById(userID);
	}
	
	/**
	 * Delete a request from the Repository
	 * @param requestID = ID of request to be deleted
	 * @return whether the request was successfully deleted from the Repository
	 */
	public void deleteRequestById(int requestID) throws Exception{
		this.repo.deleteRequestById(requestID);
	}
	
	/**
	 * Update an existing request in the Repository
	 * @param requestID = id of request to be updated
	 * @param request = request object containing new information
	 * @return whether the request was successfully updated the Repository
	 */
	public void updateRequestById(int requestID, Request request) throws Exception{
		this.repo.updateRequestById(requestID, request);
	}
	
}
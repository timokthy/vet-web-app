package group825.vetapp2.request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import group825.vetapp2.exceptions.*;

/**
 * Controller that handles Request animal requests
 *
 * @author Timothy Mok, Yong Jun (Jimmy) Zhu
 * @version 3.0
 * @since December 13 , 2021
 */
@CrossOrigin
@RequestMapping("app/request/")
@RestController
public class RequestController {

	/**
	 * Animal request service that performs the request
	 */
	private final RequestService requestService;

	/**
	 * Constructor for RequestController
	 * @param requestService service to be used to send updates to Repository
	 */
	@Autowired
	public RequestController(RequestService requestService) {
		this.requestService = requestService;
	}
	
	/**
	 * Add a request for an animal and checks that all necessary fields are filled out
	 * @param request = the new request to be added to the database
	 */
	@PostMapping
	public void addRequest(@RequestBody Request request) throws Exception{
		if (request.anyNulls()) {
			throw new ApiRequestException("At least one request field is null");
		}
		requestService.addRequest(request);
	}

	/**
	 * Displays all the requests for all animals
	 * @return a list of all the requests
	 */
	@GetMapping
	public List<Request> selectAllRequest() throws Exception{
		return requestService.selectAllRequest();
	}
	
	/**
	 * Get all requests made by one user
	 * @param userID = the ID number of the user whose requests are being returning
	 * @return list of all requests made by one user
	 */
	@GetMapping(path = "user/{userID}") 
	public List<Request> selectRequestById(@PathVariable("userID") String userID) throws Exception{
		try {
			int id = Integer.valueOf(userID);
			return requestService.selectRequestsById(id);
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidIdException();
        }
	}
	
	/**
	 * Delete an existing request in the database and checks that the request ID is valid
	 * @param requestID = the ID of the request to be deleted
	 */
	@DeleteMapping(path = "{requestID}")
	public void deleteRequestById(@PathVariable("requestID") String requestID) throws Exception{
		try {
            int id = Integer.valueOf(requestID);
			requestService.deleteRequestById(id);
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidIdException();
        }
	}
	
	/**
	 * Update an existing request in the database and checks that the request ID is valid
	 * @param requestID = the ID of the request to be updated
	 * @param requestToUpdate = request object with updated information
	 */
	@PutMapping(path = "{requestID}")
	public void updateRequestById(@PathVariable("requestID") String requestID, @RequestBody Request requestToUpdate) throws Exception {	
		try {
			int id = Integer.valueOf(requestID);

            if (requestToUpdate.anyNulls()) {
    			throw new ApiRequestException("At least one request field is null");
    		}

            requestService.updateRequestById(id, requestToUpdate);
        } catch (java.lang.IllegalArgumentException e) {
            throw new InvalidIdException();
        }
	}
}
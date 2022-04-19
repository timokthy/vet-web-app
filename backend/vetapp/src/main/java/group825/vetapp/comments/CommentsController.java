package group825.vetapp2.comments;

import java.util.List;
import java.util.UUID;

import group825.vetapp2.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles Comment requests
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since November 28, 2021
 */
@CrossOrigin
@RequestMapping("app/comments")
@RestController
public class CommentsController {

	/**
	 * Comments service that performs the request
	 */
	private final  CommentsService commentsService;

	/**
	 * Constructor that initializes the CommentsService
	 * @param commentsService CommentsService object which interacts with the "CommentsRepository" Class
	 */
	@Autowired
	public CommentsController(CommentsService commentsService) {
		this.commentsService = commentsService;
	}

	/**
	 * 'POST' mapping that adds a comment
	 * @param comment = RequestBody JSON object to be passed to the Comments class where the JSON keys are already mapped to specific data members
	 * @throws Exception when there is an SQL Exception
	 */
	@PostMapping
	public void addComment(@RequestBody Comment comment) throws Exception{
		if (comment.anyNulls()) {
			throw new ApiRequestException("Data members cannot be null! Check the Request Body being sent.");
		}
		commentsService.insertComment(comment);
	}


	/**
	 * 'GET' mapping that searches for a comment by animal ID number in the database
	 * @param animalID = String path variable obtained by path denoted inside the GetMapping annotation
	 * @return comment object or throw exception
	 */
	@GetMapping(path="/animal/{animalID}") 
	public List<Comment> selectCommentsByID(@PathVariable("animalID") String animalID) {
		try {
			//id of animal
			int id = Integer.valueOf(animalID);
			System.out.println(commentsService.selectCommentsByID(id));
			return commentsService.selectCommentsByID(id);
		} catch(Exception e) {
			e.printStackTrace();
			// Catch if id is not a valid Animal ID from Database
			throw new InvalidIdException();
		}
	}
	
	
	/**
	 * 'GET' mapping that searches for a comment by animal ID number in the database
	 * @param animalID = String path variable obtained by path denoted inside the GetMapping annotation
	 * @return comment object or throw exception
	 */
	@GetMapping(path="{commentID}") 
	public List<Comment> selectSingleCommentByID(@PathVariable("commentID") String commentID) {
		try {
			//id of animal
			int id = Integer.valueOf(commentID);
			System.out.println(commentsService.selectSingleCommentByID(id));
			return commentsService.selectSingleCommentByID(id);
		} catch(Exception e) {
			e.printStackTrace();
			// Catch if id is not a valid Animal ID from Database
			throw new InvalidIdException();
		}
	}
	

	/**
	 * 'DELETE' mapping that deletes a comment by comment ID number
	 * @param commentID = String path variable obtained by path denoted inside the DeleteMapping annotation
	 */
	@DeleteMapping(path = "{commentID}")
	public void deleteCommentByID(@PathVariable("commentID") String commentID) {
			//id of a comment
			int id = Integer.valueOf(commentID);
			try {	
				int numRowsAffected = commentsService.deleteCommentsByID(id);
				if (numRowsAffected == 0) {throw new InvalidIdException();}
			}catch (Exception e) {
				e.printStackTrace();
				throw new InvalidIdException();
			}
	}


}
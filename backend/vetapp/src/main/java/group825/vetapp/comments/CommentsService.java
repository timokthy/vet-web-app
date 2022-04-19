package group825.vetapp2.comments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * Service that performs Comment requests
 *
 * @author Jimmy Zhu
 * @version 2.0
 * @since November 28, 2021
 */
@Service
public class CommentsService {

	/**
	 * Comment repository that accesses the database
	 */
	private final CommentsRepository repo;
	
	/**
	 * Constructor that initializes the CommentsService
	 * @param dbComments repository denoted with Qualifier annotation storing the Comment objects
	 */
	@Autowired
	public CommentsService(@Qualifier("tempCommentsRepo") CommentsRepository dbComments) {
		this.repo = dbComments;
	}

	/**
	 * Inserts a comment in the database
	 * @param comment = new comment to be added
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int insertComment(Comment comment ) throws Exception {
		return repo.insertComment(comment);
	}

	
	/**
	 * Selects all comments from the database for one animal ID number
	 * @param animalID = int id pertaining to a specific animal
	 * @return List<Comment> either containing the Comment objects for particular animal or is empty
	 * @throws Exception when there is an SQL Exception
	 */
	public List<Comment> selectCommentsByID(int animalID) throws Exception{
		ArrayList<Comment> results =  repo.selectCommentsByID(animalID);
		return results;
	}
	
	/**
	 * Selects single comment from the database 
	 * @param commentID = int id pertaining to a specific comment
	 * @return List<Comment> either containing the Comment object or is empty
	 * @throws Exception when there is an SQL Exception
	 */
	public List<Comment> selectSingleCommentByID(int commentID) throws Exception{
		ArrayList<Comment> results =  repo.selectSingleCommentByID(commentID);
		return results;
	}
	

	/**
	 * Deletes a comment from the database by comment ID number
	 * @param commentID = int id pertaining for one specific comment
	 * @return integer verifying successful code execution
	 * @throws Exception when there is an SQL Exception
	 */
	public int deleteCommentsByID(int commentID) throws Exception {
		return repo.deleteCommentsByID(commentID);
	}
	
	
}
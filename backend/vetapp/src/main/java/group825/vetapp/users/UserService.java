package group825.vetapp2.users;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;


/**
 * Service that performs User requests
 *
 * @author Aron Saengchan, Jimmy Zhu, Timothy Mok
 * @version 2.0
 * @since December 6, 2021
 */
@Service
public class UserService {

    /**
     * User repository that accesses the database
     */
    private final UserRepository repo;

    /**
     * Constructor that initializes the UserService
     * @param repo repository that accesses the database
     */
    public UserService(@Qualifier("userRepo") UserRepository repo) {
        this.repo = repo;
    }

    
    /**
     * Authenticates user's entered username and password against the database.
     * If user was found, return a Json web token
     * 
     * @param username = user's entered username
     * @param password = user's enetered password
     * @return String containing json web token
     * @throws JWTCreationException
     */
    public String authenticateUser(String username, String password) throws Exception{
    	String token = "";
    	int numMins = 60;

    	User result =  this.repo.loginUser(username, password);
    	
    	//if user exists on the database
    	if (result.getFirstName().length() > 0) {
    		String userType = result.getUserType();
    		
    		try {
    		    Algorithm algorithm = Algorithm.HMAC256("secret");
    		    token = JWT.create()
    		    		.withSubject(userType)
    		    		.sign(algorithm);
    		} catch (JWTCreationException exception){
    			System.out.println("Invalid Signing configuration / Couldn't convert Claims.");
    		}
    	}
    	
    	return token;
    }
    
    
    /**
     * Verifies a user's username and password information
     * @param username user's username
     * @param password user's password
     * @return true if login was successful, false otherwise
     */
    public User loginUser(String username, String password) {
    	return this.repo.loginUser(username, password);
    }

    /**
     * Adds a user to the database
     * @param user user to be added
     */
    public void addUser(User user) {
        this.repo.addUser(user);
    }

    /**
     * Returns a user based on ID number
     * @param userID user's ID number
     * @return user if found, 'null' otherwise
     */
    public User selectUserById(int userID) {
        return this.repo.selectUserById(userID);
    }
    
    /**
     * Returns a list of users based on user type
     * @param userType the type of user
     * @return a list of user's matching the type
     */
    public ArrayList<User> selectUserByType(String userType) {
    	return this.repo.selectUserByType(userType);
	}

    /**
     * Updates a user's information
     * @param userID user's ID number
     * @param updatedInfo user's updated information
     */
    public void editUser(int userID, User updatedInfo) {
        this.repo.editUser(userID, updatedInfo);
    }

    /**
     * Blocks a user
     * @param userID user's ID number
     */
    public boolean blockUser(int userID) {
        return this.repo.blockUser(userID);
    }

    /**
     * Sends an email to all the selected users
     * @param selectedStaff = Arraylist of the selected staff user ids
     * @param message = message to be included in the email
     * @param subjectType = whether this email is for an alert or for a request for a treatment
     * @param currUserID = the user id of the user who has sent out the alert or the request for a treatment

     */
    public int sendEmail(ArrayList<Integer> selectedStaff, String message, String subjectType, String currUserID) {
    	ArrayList<String> technicianEmails = new ArrayList<String>();
    	if (selectedStaff.size()>0) {
    		technicianEmails = this.repo.getEmails(selectedStaff);
    	}
    	
    	int userID = Integer.valueOf(currUserID);
    	User sender = this.repo.selectUserById(userID);
    	
    	EmailModel systemEmail = EmailModel.getSingleInstance();

    	String emailSubject = subjectType;
    	
    	message = message +"\n\n"+"Email was created by User #" + sender.getId()+" "+sender.getFirstName() + ", "+sender.getLastName()
    			+" ("+sender.getEmail()+")\n"+ "Please use the UofC vetapp to follow up.";
    	    	
    	systemEmail.sendEmail(technicianEmails, emailSubject, message);
    	
        			
        return 1;
    }
}
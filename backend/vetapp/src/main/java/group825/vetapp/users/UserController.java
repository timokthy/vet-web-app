package group825.vetapp2.users;

import com.fasterxml.jackson.databind.node.ObjectNode;
import group825.vetapp2.exceptions.ApiRequestException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller that handles User requests
 *
 * @author Aron Saengchan, Jimmy Zhu, Timothy Mok
 * @version 2.0
 * @since December 6, 2021
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/app")
public class UserController {

    /**
     * User service that performs the request
     */
    private final UserService service;

    /**
     * Constructor that initializes the UserController
     * @param service implements the request
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Get Json Web Token if user is authenticated properly
     * @param userName = username entered by the user
     * @param password = password entered by the user
     * @return String containing the JWT
     * @throws JWTCreationException
     */
    @GetMapping(path = "/authenticate")
    @ResponseBody
    public String authenticateUser(@RequestParam String userName, @RequestParam String password) throws Exception{
    	String token =  this.service.authenticateUser(userName, password);
    	return token;
    }
    
    /**
     * 'GET' request that verifies user username and password inputs
     * @param userName = username entered by the user
     * @param password = password entered by the user
     * @return the User object if login was successful, otherwise return a dummy User object with blank info
     */
    @GetMapping(path = "/login")
    @ResponseBody
    public User loginUser(@RequestParam String userName, @RequestParam String password) {
        return this.service.loginUser(userName, password);
    }

    /**
     * 'POST' request that adds a new user to the database
     * @param user user to be added
     */
    @PostMapping(path = "/user/add-user")
    public void addUser(@RequestBody User user) {
        // Checks if any user fields are 'null'
        if (user.anyNulls()) {
            throw new ApiRequestException("At least one user field is null");
        }

        this.service.addUser(user);
    }

    /**
     * 'GET' request that returns a user based on ID number
     * @param userID user's ID number
     * @return user if found, 'null' otherwise
     */
    @GetMapping(path = "/user/edit-user/{userID}")
    @ResponseBody
    public User getUserById(@PathVariable("userID") int userID) {
        return this.service.selectUserById(userID);
    }
    
    /**
     * 'GET' request that returns a user based on user type
     * @param userType user's type
     * @return list of users matching the user type
     */
    @GetMapping(path = "/user/userType={userType}")
    @ResponseBody
    public ArrayList<User> getUserByType(@PathVariable("userType") String userType) {
        return this.service.selectUserByType(userType);
    }

    /**
     * 'PUT' request that updates a users' information
     * @param userID user's ID number
     */
    @PutMapping(path = "/user/edit-user/{userID}")
    public void editUser(@PathVariable("userID") int userID, @RequestBody User updatedInfo) {
        this.service.editUser(userID, updatedInfo);
    }

    /**
     * 'PUT' request that blocks a user
     * @param userID user's ID number
     */
    @PutMapping(path = "/user/block-user/{userID}")
    public boolean blockUser(@PathVariable("userID") int userID) {
        return this.service.blockUser(userID);
    }
    
    
    /**
     * 'POST' request that sends emails to the selected users passed in
     * @param selectedStaff = ArrayList of health technician emails to send to 
     * @param message = message that the user entered
     * @param subjectType = string identifying what the subject of the email
     * @param currUserID = id of the user who entered the message
     */
    @PostMapping(path = "/user/send-email")
    public void sendEmail(@RequestParam("selectedStaff") ArrayList<Integer> selectedStaff,
    		@RequestParam("message") String message, @RequestParam("subjectType") String subjectType,
    		@RequestParam("currUserID") String currUserID) {
    	
//        System.out.println("Inside the send-email");
//        System.out.println("selected staff = "+selectedStaff);
//        System.out.println("message = "+message);

        this.service.sendEmail(selectedStaff, message, subjectType, currUserID);
    }
    
    
}
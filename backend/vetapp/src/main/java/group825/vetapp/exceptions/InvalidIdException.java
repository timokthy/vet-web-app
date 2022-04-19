package group825.vetapp2.exceptions;

/**
 * Exception for an invalid ID
 *
 * @author Jimmy Zhu
 * @version 1.0
 * @since November 15, 2021
 */
public class InvalidIdException extends RuntimeException {

	/**
	 *  Constructor that initializes the InvalidIdException and passes the invalid ID message to the RuntimeException superclass
	 */
	public InvalidIdException() {
		super("Invalid Id. Please check that the Id is a valid Id for an existing Animal");
	}
	
	/**
	 * Constructor with a throwable that initializes the InvalidIdException and passes the invalid ID message to the RuntimeException superclass
	 * @param cause Throwable object holding details of what caused this exception
	 */
	public InvalidIdException(Throwable cause) {
		super("Invalid Id. Please check that the Id is a valid Id for an existing Animal", cause);
	}
}

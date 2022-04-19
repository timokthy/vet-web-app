package group825.vetapp2.exceptions;

/**
 * Exception for invalid API request
 *
 * @author Jimmy Zhu
 * @version 1.0
 * @since November 15, 2021
 */
public class ApiRequestException extends RuntimeException {

	/**
	 * Constructor with a message that initializes the ApiRequestException
	 * @param message string to be included in the exception response
	 */
	public ApiRequestException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with a message and cause that initializes the ApiRequestException
	 * @param message string to be included in the exception response
	 * @param cause Throwable object holding details of what caused this exception
	 */
	public ApiRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
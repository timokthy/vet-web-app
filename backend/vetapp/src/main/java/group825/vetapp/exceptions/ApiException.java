package group825.vetapp2.exceptions;

import java.time.ZonedDateTime;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API exception information
 *
 * @author Jimmy Zhu
 * @version 1.0
 * @since November 15, 2021
 */
@Getter
public class ApiException {

	/**
	 * Message associated with the exception
	 */
	private final String message;

	/**
	 * HTTP status of the exception
	 */
	private final HttpStatus httpStatus;

	/**
	 * Timestamp of the exception
	 */
	private final ZonedDateTime timestamp;
	
	/**
	 * Constructor that initializes the ApiExceptionObject
	 * @param message string to be included in the message of the exception
	 * @param httpStatus status of http request
	 * @param timestamp timestamp of when the http request was made
	 */
	public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}
}
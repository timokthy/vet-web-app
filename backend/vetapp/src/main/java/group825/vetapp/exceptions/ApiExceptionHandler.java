package group825.vetapp2.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles an API exception
 *
 * @author Jimmy Zhu
 * @version 1.0
 * @since November 15, 2021
 */
@ControllerAdvice
public class ApiExceptionHandler {
	
	/**
	 * ExceptionHandler for the ApiRequestException class
	 * @param e ApiRequestException object
	 * @return ResponseEntity<object> containing the response for this exception
	 */
	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * ExceptionHandler for the InvalidIdException class
	 * @param e InvalidIdException object
	 * @return ResponseEntity<object> containing the response for this exception
	 */
	@ExceptionHandler(value = {InvalidIdException.class})
	public ResponseEntity<Object> handleInvalidIdException(InvalidIdException e){
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}
}
package group825.vetapp2.exceptions;

import java.util.function.Supplier;

/**
 * Throws the API exception suppliers
 *
 * @author Jimmy Zhu
 * @version 1.0
 * @since November 15, 2021
 */
public class ApiExceptions {
	
    /**
     * Throws the API request exception supplier
     * @param message = String to be passed into the exception
     * @return supplier holding a RuntimeException object
     */
    public static Supplier<RuntimeException> apiRequestException(String message) {
        return () -> new ApiRequestException(message);
    }
    
    /**
     * Throws the invalid ID exception supplier
     * @return supplier holding a RuntimeException object
     */
    public static Supplier<RuntimeException> invalidIdException() {
        return () -> new InvalidIdException();
    }
}
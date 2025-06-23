package exceptions;
/**
 * Exception thrown when a command does not receive the required number of arguments.
 */
public class InsufficientArgumentsException extends Exception {
    /**
     * Constructs a new InsufficientArgumentsException with the specified detail message.
     *
     * @param errorMessage the detail message explaining the exception
     */
public InsufficientArgumentsException(String errorMessage) {
    super(errorMessage);
}
}

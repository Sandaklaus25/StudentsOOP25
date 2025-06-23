package exceptions;

public class InsufficientArgumentsException extends Exception {
public InsufficientArgumentsException(String errorMessage) {
    super(errorMessage);
}
}

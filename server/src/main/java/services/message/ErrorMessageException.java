package services.message;

/**
 * The ErrorMessageException is thrown when a method fails for any reason
 * It is meant to allow a way to return the error message response body that
 * is appropriate for the user based on whatever they were trying to get the
 * server to do.
 */
public class ErrorMessageException extends Exception {
    public ErrorMessageException(String message) {
        super(message);
    }
}

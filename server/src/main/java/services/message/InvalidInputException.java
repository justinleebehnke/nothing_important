package services.message;

public class InvalidInputException extends Exception {

    public InvalidInputException(String message, Exception e) {
        super(message + "\n" + e.toString());
    }
    public InvalidInputException(String message) {
        super (message);
    }
}

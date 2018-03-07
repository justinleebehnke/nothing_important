package services.message;

public class MessageResponse {
    private String message;

    /**
     * MessageResponse this class takes an
     * error message as a parameter.
     * It is the class that handles all error responses
     * @param message
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

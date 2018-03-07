package dao;

/**
 * The DatabaseException is used to indicate that something failed while
 * interacting with the database.
 */

public class DatabaseException extends Exception {

    public DatabaseException(String message, Exception e) {
        super(message + "\n" + e.toString());
    }

    public DatabaseException(String message) {
        super (message);
    }
}

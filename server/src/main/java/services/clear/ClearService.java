package services.clear;

import dao.Database;
import dao.DatabaseException;
import services.message.MessageResponse;

public class ClearService {

    public ClearService() {

    }

    /**
     * This method will call all of the database tables using each of
     * the DAO's clear methods. If everything succeeds it will create
     * and return a success message response. Otherwise it will return
     * an error message response.
     * @return
     */
    public MessageResponse clear() {
        Database db = new Database();
        try {
            db.clear();
        } catch (DatabaseException dbe) {
            return new MessageResponse(dbe.toString());
        }
        return new MessageResponse("Clear succeeded");
    }
}

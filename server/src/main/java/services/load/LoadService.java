package services.load;


import services.message.MessageResponse;

public class LoadService {

    public LoadService() {

    }

    /**
     * The load method will take a Load Request as a parameter and then it will iterate
     * through each of the arrays within the loadRequest object and ask the DAO objects
     * to add them to the database. Upon Success it will return the success message. Otherwise
     * it will return the error message.
     * @param loadRequest
     * @return success or failure message
     */
    public MessageResponse load(LoadRequest loadRequest) {
        return new MessageResponse("Successfully added X users, Y persons and Z events to database.");
    }

}

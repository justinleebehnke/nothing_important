package services.load;


import java.util.ArrayList;

import model_classes.User;

public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<User> persons;
    private ArrayList<User> events;

    /**
     * This method takes 3 arrays as parameters and stores them in a load request object so
     * that the Load Service class can access the data that it needs to be able to fill the
     * database.
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(ArrayList<User> users, ArrayList<User> persons, ArrayList<User> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<User> getPersons() {
        return persons;
    }

    public ArrayList<User> getEvents() {
        return events;
    }
}

package services.load;


import java.util.ArrayList;

import model_classes.Event;
import model_classes.Person;
import model_classes.User;

public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    /**
     * This method takes 3 arrays as parameters and stores them in a load request object so
     * that the Load Service class can access the data that it needs to be able to fill the
     * database.
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}

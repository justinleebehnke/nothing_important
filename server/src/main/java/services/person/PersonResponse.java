package services.person;

import java.util.ArrayList;

import model_classes.Event;
import model_classes.Person;
import model_classes.User;

public class PersonResponse {

    ArrayList<Person> persons;
    ArrayList<User> users;
    ArrayList<Event> events;

    public PersonResponse() {
        //place holder
        //needed for stubbing
    }

    /**
     * Person Response is just a placeholder is for the response of the person service that is
     * not an error, nor a request for a single person. Those return their respective objects.
     * @param persons
     * @param users
     * @param events
     */
    public PersonResponse(ArrayList<Person> persons, ArrayList<User> users, ArrayList<Event> events) {
        this.persons = persons;
        this.users = users;
        this.events = events;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}

package services.fill;

import java.util.UUID;

import dao.DatabaseException;
import dao.EventDAO;
import dao.PersonDAO;
import dao.UserDAO;
import model_classes.Event;
import model_classes.Person;
import model_classes.User;
import services.event.EventLocation;
import services.event.EventLocationGenerator;
import services.message.MessageResponse;

public class FillService {

    public FillService() {

    }

    /**
     * fill will take in a fill request object, look up in the database if there are currently
     * any person and event objects already associated with it and remove them.
     * Then it will generate how ever many generations of Persons and events that are included
     * in the fillRequest object that it holds. Upon success it will return a success body.
     * Otherwise it will return an error.
     * @param fillRequest
     * @return
     */
    public MessageResponse fill(FillRequest fillRequest) {
        return new MessageResponse("Successfully added X persons and Y events to the database");
    }

    public String fill(String username, int numGenerations) throws DatabaseException {

        int numEvents = 6;

        int sumOfPersons = 0;
        for (int i = 0; i <= numGenerations; i++) {
            sumOfPersons += Math.pow(2, i);
        }


        int birthYear = randomBirthYear();
        //go to the database and remove all events and persons associated with this username
        PersonDAO personDAO = new PersonDAO();
        personDAO.clear(username);
        EventDAO eventDAO = new EventDAO();
        eventDAO.clear(username);

        //get the user
        UserDAO userDAO = new UserDAO();
        User user = userDAO.read(username);

        //create a first user, then call recursive fill
        Person person = new Person(user.getUsername(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getGender(),
                "",
                "",
                "");
//        personDAO.add(person);
//
//        addUserEvents(person.getPerson_id(), username,  numEvents, birthYear);
//        //now the user person object is recreated
//        //now we do the recursive fill based on the number of generations
//        recursiveFill(numGenerations, username, person.getPerson_id());

        return "Successfully added " + sumOfPersons + " persons and " + (sumOfPersons * numEvents)
                + " events to the database.";

    }

    private void recursiveFill(int numGenerations, String username, UUID person_id) {
        //we need to create a couple for every person.
        //and that couple will have a matching marriage event
        //so once the husband's event is created then, when we create the wife's event they'll just share that


    }

    public int randomBirthYear() {
        return randBetween(1900, 2010);
    }

    private int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private void addUserEvents(UUID person_id, String username, int numEvents, int birthYear) throws DatabaseException {
        String eventTypes[] = {
                "birth",
                "baptism",
                "marriage",
                "death",
                "residency"
        };
        int j = eventTypes.length;
        //write the first 4 logically, then fill in any extras as residency events
        for (int i = 0; i < numEvents; i++) {
            if (i >= j) {
                createEvent("residency", person_id, birthYear, username);
            }
            else {
                createEvent(eventTypes[i], person_id, birthYear, username);
            }
        }
    }

    private void createEvent(String eventType, UUID person_id, int birthYear, String descendant_userName) throws DatabaseException {
        assert birthYear < 2015;
        switch (eventType) {
            case ("birth"):
                birthYear += 0;
                break;
            case ("baptsim"):
                birthYear += 8;
                break;
            case ("marriage"):
                birthYear += 25;
                break;
            case ("death"):
                birthYear += 85;
                break;
            default: //residency or something else
                birthYear = randBetween(birthYear, birthYear+85);
                break;
        }
        //now we'll create an event that makes sense given a year
        EventLocationGenerator events = new EventLocationGenerator();
        EventLocation eventLocation = events.randomLocation();

        Event event = new Event(descendant_userName,
                person_id,
                eventLocation.getLatitude(),
                eventLocation.getLongitutde(),
                eventLocation.getCountry(),
                eventLocation.getCity(),
                eventType,
                birthYear);
        EventDAO eventDAO = new EventDAO();
        eventDAO.add(event);
    }

    private Person generatePerson(String username) {
        //this creates a person object for a username
        return null;
    }
}

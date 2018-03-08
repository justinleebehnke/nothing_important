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

        int numEvents = 3;

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
        //I can create father and mother now
        //then use those values to build the next
        String parents[] = buildParents(user.getUserName(), numGenerations, birthYear, numEvents);



        Person person = new Person(user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                parents[0],
                parents[1],
                "");
        personDAO.add(person);

        addUserEvents(person.getPersonID(), username,  numEvents, birthYear);
        //now the user person object is recreated
        //now we do the recursive fill based on the number of generations

        return "Successfully added " + sumOfPersons + " persons and " + ((sumOfPersons * numEvents) + sumOfPersons - 1)
                + " events to the database.";

    }

    /**
     * This function returns the two Person ID's for one's parent.
     * 0 = father's id
     * 1 = mother's id
     * @param username
     * @param numGenerations
     * @return
     */
    private String[] buildParents(String username, int numGenerations, int childBirthYear, int numEvents) throws DatabaseException {

        String parents[] = new String[2];

        if (numGenerations == 0) { //at the last generation
            parents[0] = "";
            parents[1] = "";
            return parents;
        }

        numGenerations--; //lower the number before calling again
        //create a father and a mother person
        //we'll two random last names
        String fatherLastName = randomLastName();
        String motherLastName = randomLastName();
        String fatherFirstName = randomMaleName();
        String motherFirstName = randomFemaleName();
        //one random male name
        //one random female name

        //moms birth year
        int momBirthYear = generateParentBirthYear(childBirthYear);
        int dadBirthYear = generateParentBirthYear(childBirthYear);
        //dads birth year

        //create each person
        String dadParents[] = buildParents(username, numGenerations, dadBirthYear, numEvents);
        //father
        Person dad = new Person(username,
                fatherFirstName,
                fatherLastName,
                'm',
                dadParents[0],
                dadParents[1],
                "");
        //set spouse

        String momParents[] = buildParents(username, numGenerations, momBirthYear, numEvents);
        //mother
        Person mom = new Person(username,
                motherFirstName,
                motherLastName,
                'f',
                momParents[0],
                momParents[1],
                "");
        //set spouse
        mom.setSpouse(dad.getPersonID());
        dad.setSpouse(mom.getPersonID());
        //one marriage event
        createMarriageEvent(dad.getPersonID(), mom.getPersonID(), childBirthYear, username);
        //add other events
        addUserEvents(dad.getPersonID(), username, numEvents, dadBirthYear);
        addUserEvents(mom.getPersonID(), username, numEvents, momBirthYear);

        PersonDAO personDAO = new PersonDAO();
        personDAO.add(mom);
        personDAO.add(dad);

        parents[0] = dad.getPersonID();
        parents[1] = mom.getPersonID();
        return parents;
    }

    private void createMarriageEvent(String dadPerson_id, String momPerson_id, int childBirthYear, String username) throws DatabaseException {
        //create two marriage events, one for mom and another for dad
        EventLocationGenerator events = new EventLocationGenerator();
        EventLocation eventLocation = events.randomLocation();
        int eventYear = childBirthYear - ((int)(Math.random() * 1) + 1);

        Event dadMarriage = new Event(username,
                dadPerson_id,
                eventLocation.getLatitude(),
                eventLocation.getLongitude(),
                eventLocation.getCountry(),
                eventLocation.getCity(),
                "marriage",
                eventYear);
        EventDAO eventDAO = new EventDAO();
        eventDAO.add(dadMarriage);

        Event momMarriage = new Event(username,
                momPerson_id,
                eventLocation.getLatitude(),
                eventLocation.getLongitude(),
                eventLocation.getCountry(),
                eventLocation.getCity(),
                "marriage",
                eventYear);
        eventDAO.add(momMarriage);
    }

    private int generateParentBirthYear(int childBirthYear) {
        //parents are generally 17-35 = 18 years older than their children
        int range = 18;
        int min = 17;
        return childBirthYear - ((int)(Math.random() * range) + min);
    }

    private String randomFemaleName() {
        NameGenerator nameGenerator = new NameGenerator();
        return nameGenerator.getFemaleName();
    }

    private String randomMaleName() {
        NameGenerator nameGenerator = new NameGenerator();
        return nameGenerator.getMaleName();
    }

    private String randomLastName() {
        NameGenerator nameGenerator = new NameGenerator();
        return nameGenerator.getLastName();
    }

    public int randomBirthYear() {
        return randBetween(1940, 2010);
    }

    private int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private void addUserEvents(String person_id, String username, int numEvents, int birthYear) throws DatabaseException {
        String eventTypes[] = {
                "birth",
                "baptism",
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

    private void createEvent(String eventType, String person_id, int birthYear, String descendant_userName) throws DatabaseException {
        assert birthYear < 2015;
        int eventYear = birthYear;
        switch (eventType) {
            case ("birth"):
                eventYear += 0;
                break;
            case ("baptism"):
                eventYear += 8;
                break;
            case ("death"):
                eventYear += 85;
                break;
            default: //residency or something else
                eventYear = randBetween(birthYear, birthYear+85);
                break;
        }
        //now we'll create an event that makes sense given a year
        EventLocationGenerator events = new EventLocationGenerator();
        EventLocation eventLocation = events.randomLocation();

        if (eventYear > 2018) {
            eventYear = randBetween(birthYear, 2018); //set the event to occur between birth and 2018
            eventType = "residency";
        }

        Event event = new Event(descendant_userName,
                person_id,
                eventLocation.getLatitude(),
                eventLocation.getLongitude(),
                eventLocation.getCountry(),
                eventLocation.getCity(),
                eventType,
                eventYear);
        EventDAO eventDAO = new EventDAO();
        eventDAO.add(event);
    }
}

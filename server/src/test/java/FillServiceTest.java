import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import dao.Database;
import dao.DatabaseException;
import dao.EventDAO;
import dao.PersonDAO;
import dao.UserDAO;
import model_classes.Event;
import model_classes.Person;
import model_classes.User;
import services.fill.FillService;
import services.message.InvalidInputException;

public class FillServiceTest {

    @Before
    public void clearDB() throws DatabaseException {
        System.out.println("FillService test: BEGIN");
        Database db = new Database();
        db.clear();
    }
    @After
    public void clearAfter() throws DatabaseException {
        System.out.println("FillService test: COMPLETE");
        Database db = new Database();
        db.clear();
    }

    /**
     * Successfully generated 10 generations for two users without any errors
     * 2047 people * 2
     * 8187 events * 2
     * Verified that the database reflects these changes
     * @throws InvalidInputException
     * @throws DatabaseException
     */
    @Test
    public void testFillUserPerson() throws InvalidInputException, DatabaseException {
        FillService fillService = new FillService();
        //add a user to the database
        User user1 = new User("JOHN", "fake", "hello@example.com",
                "Hank", "Henry", 'm');
        User user2 = new User("JACK", "fake3", "hello1@example.com",
                "Habk", "Henry", 'm');

        //then fill that user with fill //automatically they are filled with 4 generations
        UserDAO userDAO = new UserDAO();
        userDAO.add(user1);
        userDAO.add(user2);

        fillService.fill(user1.getUserName(), 4);
        fillService.fill(user2.getUserName(), 4);

        ArrayList<Person> persons1 = new PersonDAO().read(user1.getUserName());
        ArrayList<Person> persons2 = new PersonDAO().read(user2.getUserName());

        //there should be only 31 persons in each array
        Assert.assertEquals(31, persons1.size());
        Assert.assertEquals(31, persons2.size());
//        fill creates the proper ratios of people 1 for 0 3 for 1 etc
//        fill throws error on negative or > 8 generations //Handler does this from website
//        fill defaults to 4 generations when not specified //tested from website Handler does that
        fillService.fill(user1.getUserName(), 0);
        fillService.fill(user2.getUserName(), 1);
        ArrayList<Person> shouldHave1 = new PersonDAO().read(user1.getUserName());
        ArrayList<Person> shouldHave3 = new PersonDAO().read(user2.getUserName());

        Assert.assertEquals(1, shouldHave1.size());
        Assert.assertEquals(3, shouldHave3.size());

        //test that event data is realistic
        //large number of events
        fillService.fill(user1.getUserName(), 6);
        ArrayList<Event> events = new EventDAO().read(user1.getUserName());
        Assert.assertEquals(507, events.size());
        for (Event event : events) {
            //get all the events tied to that person
            for (Event sub : events) {
                if (event.getPersonID().equals(sub.getPersonID())) {
                    //these events belong to the same person
                    //death is last event
                    if (event.getEventType().equals("death")) {
                        Assert.assertTrue(event.getYear() >= sub.getYear());
                    }
                    //birth is first event
                    if (event.getEventType().equals("birth")) {
                        Assert.assertTrue(event.getYear() <= sub.getYear());
                    }
                    //marriage (if present) occurs at a reasonable age
                    if (event.getEventType().equals("marriage") &&
                            sub.getEventType().equals("birth")) {
                        Assert.assertTrue((event.getYear() - 16) >= (sub.getYear()));
                    }
                }
            }



        }
    }


}

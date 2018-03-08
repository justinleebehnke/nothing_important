import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import dao.Database;
import dao.DatabaseException;
import dao.EventDAO;
import model_classes.Event;

public class EventDAOTest {
    @Before
    public void clearDB() throws DatabaseException {
        System.out.println("EventDAO test: BEGIN");
        Database db = new Database();
        db.clear();
    }
    @After
    public void clearAfter() throws DatabaseException {
        System.out.println("EventDAO test: COMPLETE");
        Database db = new Database();
        db.clear();
    }
    @Test
    public void testAddAndReadOneEvent() throws DatabaseException {

        Event event = new Event("justin", UUID.randomUUID().toString(),
                006.342312, 006.3213, "Canada", "Albequrque",
                "Birth", 1990);

        EventDAO eventDAO = new EventDAO();

        eventDAO.add(event);

        Event event1 = eventDAO.readEvent(event.getEventID(), event.getDescendant());

        Assert.assertEquals(event.getEventID(), event1.getEventID());
        Assert.assertEquals(event.getPersonID(), event1.getPersonID());
        Assert.assertEquals(event.getDescendant(), event1.getDescendant());
        Assert.assertEquals(event.getLatitude(), event1.getLatitude(), 0.0001);
        Assert.assertEquals(event.getLongitude(), event1.getLongitude(), 0.0001);
        Assert.assertEquals(event.getCountry(), event1.getCountry());
        Assert.assertEquals(event.getCity(), event1.getCity());
        Assert.assertEquals(event.getEventType(), event1.getEventType());
        Assert.assertTrue(event.getYear() == event1.getYear());

    }

    /**
     * This test makes sure that it only includes those associated with a certain desecendant username
     * and also ignores any that are not.
     * @throws DatabaseException
     */
    @Test
    public void testListOfEventsReturned() throws DatabaseException {
        Event event1 = new Event("justin", UUID.randomUUID().toString(),
                006.342312, 006.3213, "Utah", "Albequrque",
                "Birth", 1990);
        Event event2 = new Event("justin", UUID.randomUUID().toString(),
                006.342312, 006.3213, "Canada", "Albequrque",
                "Death", 2018);
        Event event3 = new Event("justin", UUID.randomUUID().toString(),
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);

        Event event4 = new Event("jack", UUID.randomUUID().toString(),
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);
        Event event5 = new Event("jill", UUID.randomUUID().toString(),
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);

        EventDAO eventDAO = new EventDAO();
        eventDAO.add(event1);
        eventDAO.add(event2);
        eventDAO.add(event3);
        eventDAO.add(event4);
        eventDAO.add(event5);

        ArrayList<Event> events = eventDAO.read(event1.getDescendant());
        Assert.assertTrue(events.size() == 3);

        //make sure that none of the events are from other usernames
        for (Event event : events) {
            Assert.assertTrue(event.getEventID().equals(event1.getEventID()) ||
                    event.getEventID().equals(event2.getEventID()) ||
                    event.getEventID().equals(event3.getEventID()));
            Assert.assertFalse(event.getEventID().equals(event4.getEventID()) ||
                    event.getEventID().equals(event5.getEventID()));
        }
    }
}
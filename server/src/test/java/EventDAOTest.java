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
        Database db = new Database();
        db.clear();
    }
    @After
    public void clearAfter() throws DatabaseException {
        Database db = new Database();
        db.clear();
    }
    @Test
    public void testAddAndReadOneEvent() throws DatabaseException {

        Event event = new Event("justin", UUID.randomUUID(),
                006.342312, 006.3213, "Canada", "Albequrque",
                "Birth", 1990);

        EventDAO eventDAO = new EventDAO();

        eventDAO.add(event);

        Event event1 = eventDAO.read(event.getEvent_id());

        Assert.assertEquals(event.getEvent_id(), event1.getEvent_id());
        Assert.assertEquals(event.getPerson_id(), event1.getPerson_id());
        Assert.assertEquals(event.getDescendant_username(), event1.getDescendant_username());
        Assert.assertEquals(event.getLatitude(), event1.getLatitude(), 0.0001);
        Assert.assertEquals(event.getLongitude(), event1.getLongitude(), 0.0001);
        Assert.assertEquals(event.getCountry(), event1.getCountry());
        Assert.assertEquals(event.getCity(), event1.getCity());
        Assert.assertEquals(event.getEvent_type(), event1.getEvent_type());
        Assert.assertTrue(event.getYear() == event1.getYear());

    }

    /**
     * This test makes sure that it only includes those associated with a certain desecendant username
     * and also ignores any that are not.
     * @throws DatabaseException
     */
    @Test
    public void testListOfEventsReturned() throws DatabaseException {
        Event event1 = new Event("justin", UUID.randomUUID(),
                006.342312, 006.3213, "Utah", "Albequrque",
                "Birth", 1990);
        Event event2 = new Event("justin", UUID.randomUUID(),
                006.342312, 006.3213, "Canada", "Albequrque",
                "Death", 2018);
        Event event3 = new Event("justin", UUID.randomUUID(),
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);

        Event event4 = new Event("jack", UUID.randomUUID(),
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);
        Event event5 = new Event("jill", UUID.randomUUID(),
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);

        EventDAO eventDAO = new EventDAO();
        eventDAO.add(event1);
        eventDAO.add(event2);
        eventDAO.add(event3);
        eventDAO.add(event4);
        eventDAO.add(event5);

        ArrayList<Event> events = eventDAO.read(event1.getDescendant_username());
        Assert.assertTrue(events.size() == 3);

        for (Event event : events) {
            Assert.assertTrue(event.getEvent_id().equals(event1.getEvent_id()) ||
                    event.getEvent_id().equals(event2.getEvent_id()) ||
                    event.getEvent_id().equals(event3.getEvent_id()));
            Assert.assertFalse(event.getEvent_id().equals(event4.getEvent_id()) ||
                    event.getEvent_id().equals(event5.getEvent_id()));
        }
    }
}

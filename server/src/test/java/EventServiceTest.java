import com.google.gson.Gson;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import dao.Database;
import dao.DatabaseException;
import dao.EventDAO;
import model_classes.Event;
import services.event.EventService;

public class EventServiceTest {

    @Before
    public void clearDB() throws DatabaseException {
        System.out.println("EventService test: BEGIN");
        Database db = new Database();
        db.clear();
        //load with user information
        Event event1 = new Event("200", "justin", "10",
                006.342312, 006.3213, "Utah", "Albequrque",
                "Birth", 1990);
        Event event2 = new Event("201","justin", "11",
                006.342312, 006.3213, "Canada", "Albequrque",
                "Death", 2018);
        Event event3 = new Event("202", "justin", "12",
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);

        Event event4 = new Event("203","jack", "13",
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);
        Event event5 = new Event("204","jill", "14",
                006.342312, 006.3213, "Costa Rica", "Albequrque",
                "Residence", 2017);

        EventDAO eventDAO = new EventDAO();
        eventDAO.add(event1);
        eventDAO.add(event2);
        eventDAO.add(event3);
        eventDAO.add(event4);
        eventDAO.add(event5);
    }
    @After
    public void clearAfter() throws DatabaseException {
        System.out.println("EventService test: COMPLETE");
        Database db = new Database();
        db.clear();
    }

    @Test
    public void testIDBasic() {

        //test ID throws error for bad authtoken
        // bad username is caused by bad authToken - tested
        try {
            Event event = new EventService().getSingleEvent("200", "jill");
        } catch (DatabaseException e) {
            Assert.assertEquals("dao.DatabaseException: Event Specified is not owned by user", e.toString());
        }
        //event ID throws an error if no such event id
        try {
            Event event = new EventService().getSingleEvent("199", "jill");
        } catch (DatabaseException e) {
            Assert.assertEquals("dao.DatabaseException: Event_ID not in database", e.toString());
        }
        try {
            Event event = new EventService().getSingleEvent("200", "justin");
            Gson gson = new Gson();
            Assert.assertEquals("{\"eventID\":\"200\",\"descendant\":\"justin\",\"personID\":\"10\",\"latitude\":6.342312,\"longitude\":6.3213,\"country\":\"Utah\",\"city\":\"Albequrque\",\"eventType\":\"Birth\",\"year\":1990}", gson.toJson(event));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

    }


}

package services.event;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import dao.DatabaseException;
import dao.EventDAO;
import model_classes.Event;

public class EventService {

    public EventService() {

    }

    /**
     * This function will figure out the userId of the current
     * user and use that to get all of the events associated
     * with the entire family tree tied to that user id.
     * It will then return them as an array
     * @return
     */
    public ArrayList<Event> getWholeFamilyEvents(String username) throws DatabaseException {
        EventDAO eventDAO = new EventDAO();
        return eventDAO.read(username);
    }

    /**
     * This will return the event that matches the event_id that
     * is passed in as a parameter
     * @param event_id
     * @return
     */
    public Event getSingleEvent(String event_id, String username) throws DatabaseException {
        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.readEvent(event_id, username);
        return event;
    }
}

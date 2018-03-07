package services.event;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

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
    public Set<String> getWholeFamilyEvents() {
        return new TreeSet<>();
    }

    /**
     * This will return the event that matches the event_id that
     * is passed in as a parameter
     * @param event_id
     * @return
     */
    public Event getSingleEvent(UUID event_id) {
        return new Event();
    }
}

package model_classes;

import java.util.UUID;

public class Event {

    public Event() {
        //Deleteme ASAP, needed for function stubbing
    }

    private UUID event_id;
    private String descendant_username;
    private UUID person_id;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String event_type;
    private int year;

    /**
     * This constructor is for reading from the database when the event ID is already known and it
     * will not generate a new one like the other constructor does. this constructor like all
     * other model constructors expects that the parameters passed to it have already been validated.
     *
     * @param event_id
     * @param descendant_username
     * @param person_id
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param event_type
     * @param year
     */
    public Event(UUID event_id, String descendant_username, UUID person_id, double latitude,
                 double longitude, String country, String city, String event_type, int year) {
        this.event_id = event_id;
        this.descendant_username = descendant_username;
        this.person_id = person_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.event_type = event_type;
        this.year = year;
    }

    /**
     * The Event constructor will create a new unique event id when it is called
     * the constructor is read only once initialized.
     * this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     *
     * @param descendant_username
     * @param person_id
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param event_type
     * @param year
     */
    public Event(String descendant_username, UUID person_id, double latitude,
                 double longitude, String country, String city, String event_type, int year) {
        this.event_id = UUID.randomUUID();
        this.descendant_username = descendant_username;
        this.person_id = person_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.event_type = event_type;
        this.year = year;
    }

    public UUID getEvent_id() {
        return event_id;
    }

    public String getDescendant_username() {
        return descendant_username;
    }

    public UUID getPerson_id() {
        return person_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEvent_type() {
        return event_type;
    }

    public int getYear() {
        return year;
    }
}
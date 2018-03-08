package model_classes;

import java.util.UUID;

public class Event {

    public Event() {
        //Deleteme ASAP, needed for function stubbing
    }

    private String eventID;
    private String descendant;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * This constructor is for reading from the database when the event ID is already known and it
     * will not generate a new one like the other constructor does. this constructor like all
     * other model constructors expects that the parameters passed to it have already been validated.
     *
     * @param eventID
     * @param descendant
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String descendant, String personID, double latitude,
                 double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * The Event constructor will create a new unique event id when it is called
     * the constructor is read only once initialized.
     * this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     *
     * @param descendant
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String descendant, String personID, double latitude,
                 double longitude, String country, String city, String eventType, int year) {
        this.eventID = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPersonID() {
        return personID;
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

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
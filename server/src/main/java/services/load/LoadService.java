package services.load;


import java.util.ArrayList;

import dao.Database;
import dao.EventDAO;
import dao.PersonDAO;
import dao.UserDAO;
import model_classes.Event;
import model_classes.Person;
import model_classes.User;
import services.message.ErrorMessageException;
import services.message.MessageResponse;

public class LoadService {

    public LoadService() {

    }

    /**
     * The load method will take a Load Request as a parameter and then it will iterate
     * through each of the arrays within the loadRequest object and ask the DAO objects
     * to add them to the database. Upon Success it will return the success message. Otherwise
     * it will return the error message.
     * @param loadRequest
     * @return success or failure message
     */
    public MessageResponse load(LoadRequest loadRequest) throws ErrorMessageException {
        if (!isValid(loadRequest)) {
            throw new ErrorMessageException("The load request is invalid");
        }
        Database db = new Database();
        int numUsersAdded = 0;
        int numEventsAdded = 0;
        int numPersonsAdded = 0;
        try {
            db.clear();
        } catch (Exception e) {
            throw new ErrorMessageException("Failed to clear database:" + e.toString());
        }
        UserDAO userDAO = new UserDAO();
        PersonDAO personDAO = new PersonDAO();
        EventDAO eventDAO = new EventDAO();

        try {
            //first add all the users
            for (User u : loadRequest.getUsers()) {
                userDAO.add(u);
                numUsersAdded++;
            }
            for (Person p : loadRequest.getPersons()) {
                personDAO.add(p);
                numPersonsAdded++;
            }
            for (Event e : loadRequest.getEvents()) {
                eventDAO.add(e);
                numEventsAdded++;
            }
        } catch (Exception e) {
            throw new ErrorMessageException("Failed to load all elements: " + e.toString());
        }

        return new MessageResponse("Successfully added " + numUsersAdded
                + " users, " + numPersonsAdded + " persons and "+ numEventsAdded +" events to database.");
    }

    private boolean isValid(LoadRequest loadRequest) {

        for (User u : loadRequest.getUsers()) {
            if (u.getUserName().equals("") || u.getUserName().length() < 1) {
                System.out.println(u.getUserName());
                return false;
            }
            if (u.getPassword().length() < 1) {
                System.out.println(u.getPassword());
                return false;
            }
            if (u.getEmail().length() < 1) {
                System.out.println(u.getEmail());
                return false;
            }
            if (u.getFirstName().length() < 1) {
                System.out.println(u.getFirstName());
                return false;
            }
            if (u.getLastName().length() < 1) {
                System.out.println(u.getLastName());
                return false;
            }
            if (u.getGender() != 'm' && u.getGender() != 'f') {
                System.out.println(u.getGender());
                return false;
            }
        }
        for (Person p : loadRequest.getPersons()) {
            if (p.getDescendant().length() < 1) {
                System.out.println(p.getDescendant());
                return false;
            }
            if (p.getPersonID().length() < 1) {
                System.out.println(p.getPersonID());
                return false;
            }
            if (p.getFirstName().length() < 1) {
                System.out.println(p.getFirstName());
                return false;
            }
            if (p.getLastName().length() < 1) {
                return false;
            }
            if (p.getGender() != 'm' && p.getGender() != 'f') {
                return false;
            }
        }
        for (Event e : loadRequest.getEvents()) {
            if (e.getDescendant().length() < 1) {
                return false;
            }
            if (e.getEventID().length() < 1) {
                return false;
            }
            if (e.getPersonID().length() < 1) {
                return false;
            }
            if (e.getLatitude() > 1000 || e.getLatitude() < -1000) {
                return false;
            }
            if (e.getLongitude() > 1000 || e.getLongitude() < -1000) {
                return false;
            }
            if (e.getCountry().length() < 1) {
                return false;
            }
            if (e.getCity().length() < 1) {
                return false;
            }
            if (e.getEventType().length() < 1) {
                return false;
            }
            if (e.getYear() > 2018 || e.getYear() < 0) {
                return false;
            }
        }
        return true;
    }

}

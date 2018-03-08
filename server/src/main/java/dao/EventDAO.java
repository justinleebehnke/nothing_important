package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import model_classes.Event;

public class EventDAO {

    /**
     * This function adds one event object to the database
     * @param event
     * @throws DatabaseException
     */
    public void add(Event event) throws DatabaseException {
        String sql =
                "insert into events (event_id, " +
                        "descendant_username, " +
                        "person_id, " +
                        "latitude, " +
                        "longitude, " +
                        "country, " +
                        "city, " +
                        "event_type, " +
                        "year" +
                        ") values ('" +
                        event.getEvent_id() + "', '" +
                        event.getDescendant_username() + "', '" +
                        event.getPerson_id() + "', '" +
                        event.getLatitude() + "', '" +
                        event.getLongitude() + "', '" +
                        event.getCountry() + "', '" +
                        event.getCity() + "', '" +
                        event.getEvent_type() + "', '" +
                        event.getYear() + "');";
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = connection.prepareStatement(sql);
                if (preparedStatement.executeUpdate() == -1) {
                    throw new DatabaseException("Add Event Failed: Could not add object");
                }
            }
            finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    db.closeConnection(true);
                }
            }
        } catch (SQLException e) {
            System.out.print(sql + "\n");
            throw new DatabaseException("Add Event failed", e);
        }
    }

    /**
     * This read event takes an event id and returns just that
     * event with that unique id.
     * @param event_id
     * @return event object
     * @throws DatabaseException
     */
    public Event read(UUID event_id) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String query = "SELECT * FROM events\n" +
                    "WHERE event_id='" + event_id + "';";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                statement.close();
                db.closeConnection(false);
                throw new DatabaseException("Event_ID not in database");
            } else {

                Event event = new Event(
                        UUID.fromString(resultSet.getString("event_id")),
                        resultSet.getString("descendant_username"),
                        UUID.fromString(resultSet.getString("person_id")),
                        resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("event_type"),
                        resultSet.getInt("year"));

                statement.close();
                db.closeConnection(false);
                return event;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Read Event Failed", e);
        }
    }

    /**
     * This read takes a descendant_username will return
     * all of the events for all of the family members of a
     * given descendant's family tree.
     * @param descendant_username
     * @return Container of event objects
     * @throws DatabaseException
     */
    public ArrayList<Event> read(String descendant_username) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String query = "SELECT * FROM events\n" +
                    "WHERE descendant_username='" + descendant_username + "';";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                statement.close();
                db.closeConnection(false);
                throw new DatabaseException("No events are associated with that descendant_username");
            } else {
                ArrayList<Event> events = new ArrayList<>();
                do {
                    Event event1 = new Event(
                            UUID.fromString(resultSet.getString("event_id")),
                            resultSet.getString("descendant_username"),
                            UUID.fromString(resultSet.getString("person_id")),
                            resultSet.getDouble("latitude"),
                            resultSet.getDouble("longitude"),
                            resultSet.getString("country"),
                            resultSet.getString("city"),
                            resultSet.getString("event_type"),
                            resultSet.getInt("year"));
                    events.add(event1);
                } while (resultSet.next());

                statement.close();
                db.closeConnection(false);
                return events;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Read Event Group Failed", e);
        }
    }

    /**
     * This deletes all data from database associated with a given descendant_username
     * @param username
     * @throws DatabaseException
     */
    public void clear(String username) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();
            String update = "DELETE FROM events\n" +
                    "WHERE descendant_username='" + username + "';";

            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
            db.closeConnection(true);
        }
        catch (SQLException e) {
            throw new DatabaseException("Clear events failed", e);
        }

    }
}

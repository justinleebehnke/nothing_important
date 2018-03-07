package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import model_classes.Person;

public class PersonDAO {

    /**
     * add will add a person object to the person table
     * in the database
     * @param person
     * @throws DatabaseException
     */
    public void add(Person person) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String sql =
                    "insert into persons (person_id, " +
                            "descendant_username, " +
                            "first_name, " +
                            "last_name, " +
                            "gender, " +
                            "father, " +
                            "mother, " +
                            "spouse " +
                            ") values ('" +
                            person.getPerson_id() + "', '" +
                            person.getDescendant_username() + "', '" +
                            person.getFirst_name() + "', '" +
                            person.getLast_name() + "', '" +
                            person.getGender() + "', '" +
                            person.getFather() + "', '" +
                            person.getMother() + "', '" +
                            person.getSpouse() + "');";

            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = connection.prepareStatement(sql);
                if (preparedStatement.executeUpdate() == -1) {
                    throw new DatabaseException("Add Person Failed: Could not add object");
                }
            }
            finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    db.closeConnection(true);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Add Person failed", e);
        }
    }

    /**
     * this read will return the person object from the
     * database that matches the person_id that is passed
     * into it
     * @param person_id
     * @return
     * @throws DatabaseException
     */
    public Person read(UUID person_id) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String query = "SELECT * FROM persons\n" +
                    "WHERE person_id='" + person_id + "';";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                statement.close();
                db.closeConnection(false);
                throw new DatabaseException("Event_ID not in database");
            } else {

                Person person = new Person(
                        UUID.fromString(resultSet.getString("person_id")),
                        resultSet.getString("descendant_username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getString("father"),
                        resultSet.getString("mother"),
                        resultSet.getString("spouse"));

                statement.close();
                db.closeConnection(false);
                return person;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Read Person Failed", e);
        }
    }

    /**
     * This read will return all of the person objects that
     * are matching the descendant username that is passed into
     * it.
     * @param descendant_username
     * @return Container of all person objects
     * @throws DatabaseException
     */
    public ArrayList<Person> read(String descendant_username) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String query = "SELECT * FROM persons\n" +
                    "WHERE descendant_username='" + descendant_username + "';";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                statement.close();
                db.closeConnection(false);
                throw new DatabaseException("No persons are associated with that descendant_username");
            } else {
                ArrayList<Person> persons = new ArrayList<>();
                do {
                    Person person = new Person(
                            UUID.fromString(resultSet.getString("person_id")),
                            resultSet.getString("descendant_username"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("gender").charAt(0),
                            resultSet.getString("father"),
                            resultSet.getString("mother"),
                            resultSet.getString("spouse"));

                    persons.add(person);
                } while (resultSet.next());

                statement.close();
                db.closeConnection(false);
                return persons;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Read Person Group Failed", e);
        }
    }

    /**
     * This will remove all person events associated with a given descendant username
     * @param username
     */
    public void clear(String username) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();
            String update = "DELETE FROM persons\n" +
                    "WHERE descendant_username='" + username + "';";

            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
            db.closeConnection(true);
        }
        catch (SQLException e) {
            throw new DatabaseException("Clear persons failed", e);
        }
    }
}

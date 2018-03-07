package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import model_classes.User;

public class UserDAO {

    /**
     * add will take a user object and add it to the database
     * @param user
     */
    public void add(User user) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String sql =
                    "insert into users (username, password, email, first_name, " +
                    "last_name, gender, person_id) values ('" +
                    user.getUsername() + "', '" +
                    user.getPassword() + "', '" +
                    user.getEmail() + "', '" +
                    user.getFirst_name() + "', '" +
                    user.getLast_name() + "', '" +
                    user.getGender() + "', '" +
                    user.getPerson_id() + "');";

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                if (preparedStatement.executeUpdate() == -1) {
                    throw new DatabaseException("Add User Failed: Could not add object");
                }
            }
            finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    db.closeConnection(true);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Add User Failed", e);
        }
    }

    /**
     * This read a user object based on the username that is passed
     * in as a parameter.
     * @param username
     * @return
     */
    public User read(String username) throws DatabaseException {

        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String query = "SELECT * FROM users\n" +
                    "WHERE username='" + username + "';";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                statement.close();
                db.closeConnection(false);
                throw new DatabaseException("Username not in database");
            } else {
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("gender").charAt(0),
                        UUID.fromString(resultSet.getString("person_id")));
                statement.close();
                db.closeConnection(false);
                return user;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Read User Failed", e);
        }

    }

}

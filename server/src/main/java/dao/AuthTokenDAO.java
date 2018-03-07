package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model_classes.AuthToken;

public class AuthTokenDAO {

    /**
     * add will add the auth object to the data base
     * @param authToken
     * @throws DatabaseException
     */
    public void add(AuthToken authToken) throws DatabaseException {
        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String sql = "insert into auth_tokens (username, authorization_token) values ('" +
                    authToken.getUsername() + "', '" + authToken.getAuth_token() + "');";

            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = connection.prepareStatement(sql);
                if (preparedStatement.executeUpdate() == -1) {
                    throw new DatabaseException("Add AuthToken Failed: Could not add object");
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
            throw new DatabaseException("Add AuthToken Failed", e);
        }
    }

    /**
     * Read will check to see if a given authorization token is present
     * in the system. If it is it will return the username associated with
     * the provided AuthToken. Otherwise it will return an empty string.
     * @param authToken authToken
     * @return String username OR a string reading INVALID AUTH TOKEN
     * @throws DatabaseException
     */
    public String read(AuthToken authToken) throws DatabaseException {

        try {
            Database db = new Database();
            db.openConnection();
            Connection connection = db.getConnection();

            String query = "SELECT username FROM auth_tokens\n" +
                    "WHERE authorization_token='" + authToken.getAuth_token() + "';";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                statement.close();
                db.closeConnection(false);
                return "INVALID AUTH TOKEN";
            } else {
                String username = resultSet.getString("username");
                statement.close();
                db.closeConnection(false);
                return username;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Read AuthToken Failed", e);
        }
    }

}
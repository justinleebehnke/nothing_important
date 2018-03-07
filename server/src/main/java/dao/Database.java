package dao;

import java.sql.*;

public class Database {

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connection;

    /**
     * OpenConnection creates a connection for interfacing with the
     * database.
     * @throws DatabaseException
     */
    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:family_map_server.sqlite";

            connection = DriverManager.getConnection(CONNECTION_URL);

            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * clear method will drop all tables that exist and
     * create them again freshly when called.
     * @throws DatabaseException
     */
    public void clear() throws DatabaseException {
        Database db = new Database();
        try {
            db.openConnection();
            db.resetTables();
            db.closeConnection(true);
        } catch (DatabaseException e) {
            throw e;
        }
    }

    /**
     * CloseConnection will allow a user to end their transaction and decide
     * whether they want any changes in the database to remain permanent by
     * using the commit parameter
     * @param commit
     * @throws DatabaseException
     */
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                connection.commit();
            }
            else {
                connection.rollback();
            }

            connection.close();
            connection = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
    }

    private void resetTables() throws DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                resetUserTable(stmt);
                resetAuthTable(stmt);
                resetPersonTable(stmt);
                resetEventTable(stmt);
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Clear failed", e);
        }
    }

    private void resetEventTable(Statement stmt) throws SQLException {
        String createCommand;
        stmt.executeUpdate("drop table if exists events");
        createCommand = "CREATE TABLE events (\n"
                + "	event_id varchar(255) not null unique,\n"
                + "	descendant_username varchar(255) not null,\n"
                + "	person_id varchar(255) not null,\n"
                + "	latitude real not null,\n"
                + "	longitude real not null,\n"
                + " country varchar(255) not null, \n"
                + "	city varchar(255) not null,\n"
                + "	event_type varchar(255) not null,\n"
                + "	year integer not null\n"
                + ");";
        stmt.executeUpdate(createCommand);
    }

    private void resetPersonTable(Statement stmt) throws SQLException {
        String createCommand;
        stmt.executeUpdate("drop table if exists persons");
        createCommand = "CREATE TABLE persons (\n"
                + "	person_id varchar(255) not null unique,\n"
                + "	descendant_username varchar(255) not null,\n"
                + "	first_name varchar(255) not null,\n"
                + "	last_name varchar(255) not null,\n"
                + "	gender varchar(5) not null,\n"
                + "	father varchar(255),\n"
                + "	mother varchar(255),\n"
                + "	spouse varchar(255)\n"
                + ");";
        stmt.executeUpdate(createCommand);
    }

    private void resetAuthTable(Statement stmt) throws SQLException {
        String createCommand;
        stmt.executeUpdate("drop table if exists auth_tokens");
        createCommand = "CREATE TABLE auth_tokens (\n"
                + "	username varchar(255) not null,\n"
                + "	authorization_token varchar(255) not null primary key\n"
                + ");";
        stmt.executeUpdate(createCommand);
    }

    private void resetUserTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists users");
        String createCommand = "CREATE TABLE users (\n"
                + "	username varchar(255) not null unique,\n"
                + "	password varchar(255) not null,\n"
                + "	email varchar(255) not null,\n"
                + "	first_name varchar(255) not null,\n"
                + "	last_name varchar(255) not null,\n"
                + "	gender varchar(5) not null,\n"
                + "	person_id varchar(255) not null\n"
                + ");";
        stmt.executeUpdate(createCommand);
    }
}
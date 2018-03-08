package model_classes;

import java.util.UUID;

public class User {

     private String userName;
     private String password;
     private String email;
     private String firstName;
     private String lastName;
     private char gender;
     private String personID;

    /**
     * This is the secondary constructor that is used to store a user object that is read
     * from the database. The only difference it this constructor accepts an existing UUID
     * this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public User(String username, String password, String email, String firstName,
                String lastName, char gender, String personID) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * This is strictly a storage object for a User object. It is read only after being initiated.
     * This constructor expects that the data has already been validated by previous handlers before
     * the Object is constructed.
     *
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     */
    public User(String username, String password, String email, String firstName,
                String lastName, char gender) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = UUID.randomUUID().toString();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public String getPersonID() {
        return personID;
    }
}

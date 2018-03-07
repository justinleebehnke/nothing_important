package model_classes;

import java.util.UUID;

public class User {

     private String username;
     private String password;
     private String email;
     private String first_name;
     private String last_name;
     private char gender;
     private UUID person_id;

    /**
     * This is the secondary constructor that is used to store a user object that is read
     * from the database. The only difference it this constructor accepts an existing UUID
     * this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     * @param username
     * @param password
     * @param email
     * @param first_name
     * @param last_name
     * @param gender
     * @param person_id
     */
    public User(String username, String password, String email, String first_name,
                String last_name, char gender, UUID person_id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.person_id = person_id;
    }

    /**
     * This is strictly a storage object for a User object. It is read only after being initiated.
     * This constructor expects that the data has already been validated by previous handlers before
     * the Object is constructed.
     *
     * @param username
     * @param password
     * @param email
     * @param first_name
     * @param last_name
     * @param gender
     */
    public User(String username, String password, String email, String first_name,
                String last_name, char gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.person_id = UUID.randomUUID();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public char getGender() {
        return gender;
    }

    public UUID getPerson_id() {
        return person_id;
    }
}

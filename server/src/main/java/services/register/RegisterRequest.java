package services.register;

public class RegisterRequest {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private char gender;

    /**
     * The constructor needs all of the information from the handler to be able to
     * complete the request. This is just needed to allow passing of objects between
     * places. This information can only be viewed, it cannot be modified.
     * @param username
     * @param password
     * @param email
     * @param first_name
     * @param last_name
     * @param gender
     */
    public RegisterRequest(String username, String password, String email, String first_name, String last_name, char gender) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = first_name;
        this.lastName = last_name;
        this.gender = gender;
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

    @Override
    public String toString() {
        return "Username: " + userName + "\nPassword:" + password;
    }
}

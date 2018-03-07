package services.login;

public class LoginRequest {
    private String username;
    private String password;

    /**
     * This class creates a data structure that LoginService can use to do what it
     * needs to do to get the user the needed authorization token.
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

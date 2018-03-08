package services.login;

public class LoginRequest {
    private String userName;
    private String password;

    /**
     * This class creates a data structure that LoginService can use to do what it
     * needs to do to get the user the needed authorization token.
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

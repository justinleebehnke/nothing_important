package services.login;

import java.util.UUID;

public class LoginResult {
    private UUID auth_token;
    private String username;
    private UUID person_id;

    /**
     * This class is just a data structure to hold the success response body of a login request
     * @param auth_token
     * @param username
     * @param person_id
     */
    public LoginResult(UUID auth_token, String username, UUID person_id) {
        this.auth_token = auth_token;
        this.username = username;
        this.person_id = person_id;
    }

    public UUID getAuth_token() {
        return auth_token;
    }

    public String getUsername() {
        return username;
    }

    public UUID getPerson_id() {
        return person_id;
    }
}

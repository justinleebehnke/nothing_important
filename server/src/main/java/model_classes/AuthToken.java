package model_classes;

import java.util.UUID;

public class AuthToken {
    String username;
    UUID authorization_token;

    /**
     * AuthToken constructor will generate a new auth token to associate with
     * a given username this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     * @param username
     */
    public AuthToken(String username) {
        this.username = username;
        authorization_token = UUID.randomUUID();
    }

    public UUID getAuth_token() {
        return authorization_token;
    }
    public String getUsername() {
        return username;
    }
}

package services.register;

import java.util.UUID;

public class RegisterResult {
    private UUID auth_token;
    private String username;
    private String person_id;

    public RegisterResult() {
        //Stub for easy stubbing other classes
    }

    /**
     * This is just a placeholder for the result body of the Register Service class
     * This is the return object for the RegisterService register method.
     * @param auth_token
     * @param username
     * @param person_id
     */
    public RegisterResult(UUID auth_token, String username, String person_id) {
        this.auth_token = auth_token;
        this.username = username;
        this.person_id = person_id;
    }
}

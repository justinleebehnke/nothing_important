package services.login;

import java.util.UUID;

import services.message.ErrorMessageException;

public class LoginService {

    public LoginService() {

    }

    /**
     * The login method will get the authorization token to allow the user to be able to continue to
     * communicate with the server without sending their username and password.
     * @param loginRequest
     * @return
     * @throws ErrorMessageException
     */
    public LoginResult login(LoginRequest loginRequest) throws ErrorMessageException {
        UUID a = UUID.randomUUID();
        UUID p = a;
        String u = "";
        return new LoginResult(a, u, p);
    }


}

package services.login;

import dao.AuthTokenDAO;
import dao.DatabaseException;
import dao.UserDAO;
import model_classes.AuthToken;
import model_classes.User;
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

        //check that the username and password are in the database
        User user;
        UserDAO userDAO = new UserDAO();
        AuthToken authToken;
        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        LoginResult loginResult;
        boolean valid = false;

        try {
            user = userDAO.read(loginRequest.getUsername());
            if (user.getUsername().equals(loginRequest.getUsername())) {
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    valid = true;
                }
            }
            if (valid) {
                authToken = new AuthToken(user.getUsername());
                authTokenDAO.add(authToken);
                loginResult = new LoginResult(
                        authToken.getAuth_token(),
                        user.getUsername(),
                        user.getPerson_id());
            } else {
                throw new DatabaseException("Login was not valid");
            }
        }
        catch (DatabaseException dba) {
            throw new ErrorMessageException("Failed to login");
        }

        return loginResult;
    }


}

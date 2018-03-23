package services.register;

import dao.DatabaseException;
import dao.UserDAO;
import model_classes.User;
import services.fill.FillService;
import services.login.LoginRequest;
import services.login.LoginResult;
import services.login.LoginService;
import services.message.ErrorMessageException;
import services.message.InvalidInputException;

public class RegisterService {

    public RegisterService() {

    }

    /**
     * This method will need to complete the registration procedure and then
     * find all of the parameters that RegisterResult will need to be able to
     * return that object to the handler
     * @param registerRequest
     * @return
     */
    public RegisterResult register(RegisterRequest registerRequest) throws InvalidInputException, ErrorMessageException {

        if (isValidRequest(registerRequest)) {
            //create new user account
            if (isValidUserName(registerRequest.getUserName())) {
                //create new user account
                User user = new User(registerRequest.getUserName(),
                        registerRequest.getPassword(),
                        registerRequest.getEmail(),
                        registerRequest.getFirstName(),
                        registerRequest.getLastName(),
                        registerRequest.getGender());
                UserDAO userDAO = new UserDAO();
                try {
                    userDAO.add(user);
                } catch (DatabaseException e) {
                    throw new InvalidInputException("Failed to Register User: ", e);
                }
                //generate four generations of familyHistoryInformation
                FillService fillService = new FillService();
                try {
                    fillService.fill(user.getUserName(), 4); //this fill service deletes the database data
                }
                catch (DatabaseException dbe) {
                    throw new InvalidInputException(dbe.toString(), dbe);
                }
                //log the user in
                LoginRequest loginRequest = new LoginRequest(user.getUserName(), user.getPassword());
                LoginService loginService = new LoginService();
                LoginResult loginResult = loginService.login(loginRequest);

                //return the result object
                return new RegisterResult(
                        loginResult.getAuth_token(),
                        user.getUserName(),
                        loginResult.getPerson_id());
            }
            else {
                throw new InvalidInputException("Username is already taken");
            }
        }
        else {
            throw new InvalidInputException("User Register Input was invalid");
        }
    }

    private boolean isValidUserName(String userName) {
        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.read(userName);
            if (user.getUserName().equals(userName)) {
                return false;
            }
        }
        catch (DatabaseException e) {
            return true;
        }
        return false;
    }

    private boolean isValidRequest(RegisterRequest registerRequest) {
        if (registerRequest.getUserName().equals("") || registerRequest.getUserName().length() < 1) {
            return false;
        }
        if (registerRequest.getPassword().equals("") || registerRequest.getPassword().length() < 1) {
            return false;
        }
        if (registerRequest.getEmail().equals("") || registerRequest.getEmail().length() < 1) {
            return false;
        }
        if (registerRequest.getFirstName().equals("") || registerRequest.getFirstName().length() < 1) {
            return false;
        }
        if (registerRequest.getLastName().equals("") || registerRequest.getLastName().length() < 1) {
            return false;
        }
        if (registerRequest.getGender() != 'm' && registerRequest.getGender() != 'f') {
            return false;
        }
        return true;
    }
}

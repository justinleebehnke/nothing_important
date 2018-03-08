import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import dao.AuthTokenDAO;
import dao.Database;
import dao.DatabaseException;
import dao.UserDAO;
import model_classes.AuthToken;
import model_classes.User;
import services.login.LoginRequest;
import services.login.LoginResult;
import services.login.LoginService;
import services.message.ErrorMessageException;

public class LoginServiceTest {

    @Before
    public void clearDB() throws DatabaseException {
        System.out.println("Multiple logins: BEGIN");
        Database db = new Database();
        db.clear();
    }
    @After
    public void clearAfter() throws DatabaseException {
        System.out.println("Multiple logins: COMPLETE");
        Database db = new Database();
        db.clear();
    }

    @Test
    public void testMultipleLogins() throws ErrorMessageException, DatabaseException {

        User user1 = new User("JOHN", "fake", "hello@example.com",
                "Hank", "Henry", 'm');
        UserDAO userDAO = new UserDAO();
        userDAO.add(user1);

        LoginRequest loginRequest = new LoginRequest(user1.getUserName(), user1.getPassword());
        LoginResult loginResult1 = new LoginService().login(loginRequest);
        LoginResult loginResult2 = new LoginService().login(loginRequest);
        LoginResult loginResult3 = new LoginService().login(loginRequest);
        LoginResult loginResult4 = new LoginService().login(loginRequest);

        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        UUID id = loginResult1.getAuth_token();
        String usr = authTokenDAO.read(id);
        Assert.assertEquals(usr, user1.getUserName()); //should return a user name
        id = loginResult2.getAuth_token();
        usr = authTokenDAO.read(id);
        Assert.assertEquals(usr, user1.getUserName()); //should return a user name
        id = loginResult3.getAuth_token();
        usr = authTokenDAO.read(id);
        Assert.assertEquals(usr, user1.getUserName()); //should return a user name
        id = loginResult4.getAuth_token();
        usr = authTokenDAO.read(id);
        Assert.assertEquals(usr, user1.getUserName()); //should return a user name
    }


}

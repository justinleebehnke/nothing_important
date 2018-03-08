import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

import dao.Database;
import dao.DatabaseException;
import dao.EventDAO;
import model_classes.User;
import services.message.ErrorMessageException;
import services.message.InvalidInputException;
import services.register.RegisterRequest;
import services.register.RegisterResult;
import services.register.RegisterService;

public class RegisterServiceTest {
    /*
    register creates 4 generations ie 31 people
    register throws an error when its given an existing username
    throws error when input is invalid
     */
    @Test
    public void testRegistration() throws InvalidInputException, ErrorMessageException, DatabaseException {
        Database db = new Database();
        db.clear();
        RegisterService registerService = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest(
                "user1",
                "password1",
                "fake@byu.edu",
                "first1",
                "last1",
                'f'
                );
        RegisterResult registerResult = registerService.register(registerRequest);
        //the size of the person array should be 31
        EventDAO eventDAO = new EventDAO();
        eventDAO.read("user1");
        //just need to finsih this after!!

        try {
            RegisterRequest registerRequest2 = new RegisterRequest(
                    "user1",
                    "password1",
                    "fake@byu.edu",
                    "first1",
                    "last1",
                    'f'
            );
            RegisterResult reject = registerService.register(registerRequest);
        } catch (Exception e) {
            Assert.assertEquals("services.message.InvalidInputException: Username is already taken", e.toString());
        }

    }
}

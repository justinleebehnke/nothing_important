import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import dao.Database;
import dao.DatabaseException;
import dao.EventDAO;
import dao.PersonDAO;
import model_classes.Event;
import model_classes.Person;
import model_classes.User;
import services.message.ErrorMessageException;
import services.message.InvalidInputException;
import services.register.RegisterRequest;
import services.register.RegisterResult;
import services.register.RegisterService;

public class RegisterServiceTest {

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
        PersonDAO personDAO = new PersonDAO();
        ArrayList<Person> persons = personDAO.read("user1");
        Assert.assertEquals(31, persons.size());


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

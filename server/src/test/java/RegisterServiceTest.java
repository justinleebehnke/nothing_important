import com.google.gson.Gson;

import org.junit.Test;

import dao.Database;
import dao.DatabaseException;
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
        Gson gson = new Gson();

        System.out.print(gson.toJson(registerResult));
    }
}

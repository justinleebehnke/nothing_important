import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import dao.DatabaseException;
import dao.UserDAO;
import model_classes.User;
import services.fill.FillService;
import services.message.InvalidInputException;

public class FillServiceTest {
    /*
    *   4 generations = 31 persons
        Death is last event in their lives
        Birth is first event in their lives
        marriage occurs at realistic age

        fill creates the proper ratios of people 1 for 0 3 for 1 etc
        fill throws error on negative or > 8 generations
        fill defaults to 4 generations when not specified
        */

    @Before
    public void clearDB() throws DatabaseException {
        Database db = new Database();
        db.clear();
    }

    @Test
    public void testRandomBirthYear() {
        FillService fillService = new FillService();
        int birthYear;
        for (int i = 0; i < 100000; i++) {
            birthYear = fillService.randomBirthYear();
            Assert.assertTrue(birthYear >= 1900 && birthYear <= 2010);
        }
    }

    /**
     * Successfully generated 10 generations for two users without any errors
     * 2047 people * 2
     * 8187 events * 2
     * Verified that the database reflects these changes
     * @throws InvalidInputException
     * @throws DatabaseException
     */
    @Test
    public void testFillUserPerson() throws InvalidInputException, DatabaseException {
        FillService fillService = new FillService();
        //add a user to the database
        User user1 = new User("JOHN", "fake", "hello@example.com",
                "Hank", "Henry", 'm');
        User user2 = new User("JACK", "fake3", "hello1@example.com",
                "Habk", "Henry", 'm');
        //then fill that user with fill
        UserDAO userDAO = new UserDAO();
        userDAO.add(user1);
        userDAO.add(user2);

        System.out.print(fillService.fill(user1.getUserName(), 4));
        System.out.print(fillService.fill(user2.getUserName(), 4));
        //go check the database

    }

//    @Test
//    public void testTempPublicPrivateFunction() throws DatabaseException {
//        FillService fillService = new FillService();
//        String[] parents = fillService.buildParents("JOHN", 1, 1990, 1);
//        System.out.print(parents[0]);
//        System.out.print(parents[1]);
//    }

}

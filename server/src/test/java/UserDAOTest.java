import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import dao.DatabaseException;
import dao.UserDAO;
import model_classes.User;

public class UserDAOTest {
    @Before
    public void clearDB() throws DatabaseException {
        System.out.println("UserDAO test: BEGIN");
        Database db = new Database();
        db.clear();
    }
    @After
    public void clearAfter() throws DatabaseException {
        System.out.println("UserDAO test: COMPLETE");
        Database db = new Database();
        db.clear();
    }

    /**
     * This test makes sure that a database object created is identical in every way to
     * an object created from the results set matching the unique username of that original
     * object. Thereby verifying that the database read and add functions work properly.
     * We also see the database exception that occurs when a read is not in the database.
     * Rather than create and return a dummy User Object I decided to throw an exception.
     */
    @Test
    public void testAddAndReadUserObjects() {
        try {
            User user1 = new User("JOHN", "fake", "hello@example.com",
                    "Hank", "Henry", 'm');
            User user2 = new User("JACK", "fake3", "hello1@example.com",
                    "Habk", "Henry", 'm');
            User user3 = new User("JILL", "fake1", "hello3@example.com",
                    "Ha4k", "Henry", 'f');
            UserDAO userDAO = new UserDAO();
            userDAO.add(user1);
            userDAO.add(user2);
            userDAO.add(user3);

            User user4 = userDAO.read(user1.getUserName());
            User user5 = userDAO.read(user2.getUserName());
            User user6 = userDAO.read(user3.getUserName());

            /* TO TEST THE BAD USERNAME */
            try {
                User invalidUserNmae = userDAO.read("fakeUsername");
            } catch (DatabaseException e) {
                Assert.assertEquals("dao.DatabaseException: Username not in database", e.toString());
            }

            Assert.assertEquals(user1.getUserName(), user4.getUserName());
            Assert.assertEquals(user2.getUserName(), user5.getUserName());
            Assert.assertEquals(user3.getUserName(), user6.getUserName());
            Assert.assertEquals(user1.getPassword(), user4.getPassword());
            Assert.assertEquals(user2.getPassword(), user5.getPassword());
            Assert.assertEquals(user3.getPassword(), user6.getPassword());
            Assert.assertEquals(user1.getFirstName(), user4.getFirstName());
            Assert.assertEquals(user2.getFirstName(), user5.getFirstName());
            Assert.assertEquals(user3.getFirstName(), user6.getFirstName());
            Assert.assertEquals(user1.getLastName(), user4.getLastName());
            Assert.assertEquals(user2.getLastName(), user5.getLastName());
            Assert.assertEquals(user3.getLastName(), user6.getLastName());
            Assert.assertEquals(user1.getEmail(), user4.getEmail());
            Assert.assertEquals(user2.getEmail(), user5.getEmail());
            Assert.assertEquals(user3.getEmail(), user6.getEmail());
            Assert.assertEquals(user1.getGender(), user4.getGender());
            Assert.assertEquals(user2.getGender(), user5.getGender());
            Assert.assertEquals(user3.getGender(), user6.getGender());
            Assert.assertEquals(user1.getPersonID(), user4.getPersonID());
            Assert.assertEquals(user2.getPersonID(), user5.getPersonID());
            Assert.assertEquals(user3.getPersonID(), user6.getPersonID());

        } catch (DatabaseException e) {
            System.out.println(e.toString());
        }
    }
}

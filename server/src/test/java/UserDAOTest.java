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
        Database db = new Database();
        db.clear();
    }
    @After
    public void clearAfter() throws DatabaseException {
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

            User user4 = userDAO.read(user1.getUsername());
            User user5 = userDAO.read(user2.getUsername());
            User user6 = userDAO.read(user3.getUsername());

            /* TO TEST THE BAD USERNAME */
            try {
                User invalidUserNmae = userDAO.read("fakeUsername");
            } catch (DatabaseException e) {
                Assert.assertEquals("dao.DatabaseException: Username not in database", e.toString());
            }

            Assert.assertEquals(user1.getUsername(), user4.getUsername());
            Assert.assertEquals(user2.getUsername(), user5.getUsername());
            Assert.assertEquals(user3.getUsername(), user6.getUsername());
            Assert.assertEquals(user1.getPassword(), user4.getPassword());
            Assert.assertEquals(user2.getPassword(), user5.getPassword());
            Assert.assertEquals(user3.getPassword(), user6.getPassword());
            Assert.assertEquals(user1.getFirst_name(), user4.getFirst_name());
            Assert.assertEquals(user2.getFirst_name(), user5.getFirst_name());
            Assert.assertEquals(user3.getFirst_name(), user6.getFirst_name());
            Assert.assertEquals(user1.getLast_name(), user4.getLast_name());
            Assert.assertEquals(user2.getLast_name(), user5.getLast_name());
            Assert.assertEquals(user3.getLast_name(), user6.getLast_name());
            Assert.assertEquals(user1.getEmail(), user4.getEmail());
            Assert.assertEquals(user2.getEmail(), user5.getEmail());
            Assert.assertEquals(user3.getEmail(), user6.getEmail());
            Assert.assertEquals(user1.getGender(), user4.getGender());
            Assert.assertEquals(user2.getGender(), user5.getGender());
            Assert.assertEquals(user3.getGender(), user6.getGender());
            Assert.assertEquals(user1.getPerson_id(), user4.getPerson_id());
            Assert.assertEquals(user2.getPerson_id(), user5.getPerson_id());
            Assert.assertEquals(user3.getPerson_id(), user6.getPerson_id());

        } catch (DatabaseException e) {
            System.out.println(e.toString());
        }
    }
}

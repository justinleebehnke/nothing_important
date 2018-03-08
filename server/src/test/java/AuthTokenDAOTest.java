import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.AuthTokenDAO;
import dao.Database;
import dao.DatabaseException;
import model_classes.AuthToken;

public class AuthTokenDAOTest {

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
     * This test makes sure that both the add function and the
     * read function are working properly by added a few users
     * and leaving a few not added to make sure that the read
     * function is catching both cases.
     * @throws DatabaseException
     */
    @Test
    public void testAddAndReadAuthToken() throws DatabaseException {

        AuthToken authToken1 = new AuthToken("George");
        AuthToken authToken2 = new AuthToken("Jack");
        AuthToken authToken3 = new AuthToken("John");
        AuthToken authToken4 = new AuthToken("Jess");

        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        authTokenDAO.add(authToken1);
        authTokenDAO.add(authToken2);
        authTokenDAO.add(authToken3);
        authTokenDAO.add(authToken4);

        AuthToken authToken5 = new AuthToken("NEVER_ADDED");
        AuthToken authToken6 = new AuthToken("NEVER_ADDED");

        Assert.assertEquals("George", authTokenDAO.read(authToken1.getAuth_token()));
        Assert.assertEquals("Jack", authTokenDAO.read(authToken2.getAuth_token()));
        Assert.assertEquals("John", authTokenDAO.read(authToken3.getAuth_token()));
        Assert.assertEquals("Jess", authTokenDAO.read(authToken4.getAuth_token()));
        Assert.assertEquals("INVALID AUTH TOKEN", authTokenDAO.read(authToken5.getAuth_token()));
        Assert.assertEquals("INVALID AUTH TOKEN", authTokenDAO.read(authToken6.getAuth_token()));

    }
}

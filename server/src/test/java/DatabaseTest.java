import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import dao.DatabaseException;

public class DatabaseTest {

    private Database db;
    @Before
    public void setUp() throws DatabaseException {
        db = new Database();
        db.openConnection();

    }
    @After
    public void tearDown() throws DatabaseException {
        db.closeConnection(true);
        db = null;
    }

    @Test
    public void testClearDatabase() throws DatabaseException {
        db.clear();
    }
}

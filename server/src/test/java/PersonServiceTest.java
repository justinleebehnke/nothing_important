import com.google.gson.Gson;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import dao.DatabaseException;
import dao.EventDAO;
import dao.PersonDAO;
import model_classes.Event;
import model_classes.Person;
import services.person.PersonService;

public class PersonServiceTest {
    /*
    person ID works normally
    person ID throws an error if authtoken is bad
    person ID throws an error if no such person id
    or if the ID is not associated with the user
     */
    @Before
    public void clearDB() throws DatabaseException {
        System.out.println("PersonService test: BEGIN");
        Database db = new Database();
        db.clear();
        //load with user information
        Person person1 = new Person("10", "jbehnke", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");
        Person person2 = new Person("11", "joe", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");

        PersonDAO personDAO = new PersonDAO();
        personDAO.add(person1);
        personDAO.add(person2);
    }

    @After
    public void clearAfter() throws DatabaseException {
        System.out.println("PersonService test: COMPLETE");
        Database db = new Database();
        db.clear();
    }

    @Test
    public void testIDBasic() {

        //test ID throws error for bad authtoken
        // bad username is caused by bad authToken - tested
        try {
            Person person = new PersonService().getSinglePerson("11", "jbehnke");
        } catch (DatabaseException e) {
            Assert.assertEquals("dao.DatabaseException: Person Specified is not associated with user", e.toString());
        }
        //event ID throws an error if no such event id
        try {
            Person person = new PersonService().getSinglePerson("11", "jill");
        } catch (DatabaseException e) {
            Assert.assertEquals("dao.DatabaseException: Person Specified is not associated with user", e.toString());
        }
        try {
            Person person = new PersonService().getSinglePerson("11", "joe");
            Gson gson = new Gson();
            Assert.assertEquals("{\"personID\":\"11\",\"descendant\":\"joe\",\"firstName\":\"John\",\"lastName\":\"Behnke\",\"gender\":\"m\",\"father\":\"BOB\",\"mother\":\"GIN\",\"spouse\":\"megan\"}", gson.toJson(person));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
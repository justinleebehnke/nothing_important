import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import dao.Database;
import dao.DatabaseException;
import dao.PersonDAO;
import model_classes.Person;

public class PersonDAOTest {
    @Before
    public void clearDB() throws DatabaseException {
        System.out.println("PersonDAO test: BEGIN");
        Database db = new Database();
        db.clear();
    }
    @After
    public void clearAfter() throws DatabaseException {
        System.out.println("PersonDAO test: COMPLETE");
        Database db = new Database();
        db.clear();
    }
    @Test
    public void testAddAndReadPerson() throws DatabaseException {
        Person person = new Person("jbehnke", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");

        PersonDAO personDAO = new PersonDAO();
        personDAO.add(person);

        Person person1 = personDAO.read(person.getPersonID(), person.getDescendant());
        Assert.assertEquals(person.getPersonID(), person1.getPersonID());
        Assert.assertEquals(person.getDescendant(), person1.getDescendant());
        Assert.assertEquals(person.getFirstName(), person1.getFirstName());
        Assert.assertEquals(person.getLastName(), person1.getLastName());
        Assert.assertEquals(person.getGender(), person1.getGender());
        Assert.assertEquals(person.getFather(), person1.getFather());
        Assert.assertEquals(person.getMother(), person1.getMother());
        Assert.assertEquals(person.getSpouse(), person1.getSpouse());
    }

    /**
     * This test makes sure that the group of selected by descendant_username are accurate.
     * It makes sure that it only gets the one's that match and it ignores the one's that
     * do not.
     * @throws DatabaseException
     */
    @Test
    public void testGetGroupOfPersons() throws DatabaseException {
        Person person1 = new Person("jbehnke", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");
        Person person2 = new Person("jbehnke", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");
        Person person3 = new Person("jbehnke", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");
        Person person4 = new Person("jim", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");
        Person person5 = new Person("jake", "John",
                "Behnke", 'm', "BOB", "GIN", "megan");

        PersonDAO personDAO = new PersonDAO();
        personDAO.add(person1);
        personDAO.add(person2);
        personDAO.add(person3);
        personDAO.add(person4);
        personDAO.add(person5);

        ArrayList<Person> persons = personDAO.read(person1.getDescendant());

        Assert.assertTrue(persons.size() == 3);

        for (Person person : persons) {
            Assert.assertTrue(person.getPersonID().equals(person1.getPersonID()) ||
                    person.getPersonID().equals(person2.getPersonID()) ||
                    person.getPersonID().equals(person3.getPersonID()));
            Assert.assertFalse(person.getPersonID().equals(person4.getPersonID()) ||
                    person.getPersonID().equals(person5.getPersonID()));
        }
    }

    /**
     * The person object is the only one that can have null values for Spouse
     * father and mother. So we need to make sure that person objects can be
     * read and written with or without any combination of these items.
     * They must have an empty string to allow them to compare properly.
     * This is acceptable until further notice.
     */
    @Test
    public void testHandlingOfNullValuesInRead() throws DatabaseException {
        Person person = new Person("jbehnke", "John",
                "Behnke", 'm', "", "", "");

        PersonDAO personDAO = new PersonDAO();
        personDAO.add(person);
        Person person1 = personDAO.read(person.getPersonID(), person.getDescendant());
        Assert.assertEquals(person.getPersonID(), person1.getPersonID());
        Assert.assertEquals(person.getFather(), person1.getFather());
        Assert.assertEquals(person.getMother(), person1.getMother());
        Assert.assertEquals(person.getSpouse(), person1.getSpouse());
    }
}

package services.person;


import java.util.ArrayList;
import java.util.UUID;

import dao.DatabaseException;
import dao.PersonDAO;
import model_classes.Person;

public class PersonService {
    //handle the person or person/person_id

    public PersonService() {

    }

    /**
     * This method is for will return all of the person objects
     * associated with the current user to the array.
     * @return personResponseObecject
     */
    public ArrayList<Person> getAllPersons(String username) throws DatabaseException {
        PersonDAO personDAO = new PersonDAO();
        return personDAO.read(username);
    }

    /**
     * This method will return a single person object that
     * is associated with a given person id.
     * @param person_id
     * @return Person
     */
    public Person getSinglePerson(String person_id, String username) throws DatabaseException {
        PersonDAO personDAO = new PersonDAO();
        Person person = personDAO.read(person_id, username);
        return person;
    }
}

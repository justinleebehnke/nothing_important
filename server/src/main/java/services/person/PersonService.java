package services.person;


import java.util.UUID;

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
    public PersonResponse getAllPersons() {
        return new PersonResponse();
    }

    /**
     * This method will return a single person object that
     * is associated with a given person id.
     * @param person_id
     * @return Person
     */
    public Person getSinglePersonById(UUID person_id) {
        return new Person();
    }
}

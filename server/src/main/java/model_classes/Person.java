package model_classes;

import java.util.UUID;

public class Person {

    public Person() {
        //Deleteme ASAP, needed for function stubbing
    }

    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private char gender;
    private String father;
    private String mother;
    private String spouse;

    /**
     * This constructor is for storing person objects read from the database where the personID
     * is already known and will therefore not generate a new one.
     * this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     * @param personID
     * @param descendant
     * @param firstName
     * @param lastName
     * @param gender
     * @param father
     * @param mother
     * @param spouse
     */
    public Person(String personID, String descendant, String firstName, String lastName,
                  char gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    /**
     * PersonService Constructor will generate a unique person ID when given
     * all the other parameters needed for a person object.
     * this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     *
     * @param descendant
     * @param firstName
     * @param lastName
     * @param gender
     * @param father
     * @param mother
     * @param spouse
     */
    public Person(String descendant, String firstName, String lastName,
                  char gender, String father, String mother, String spouse) {
        this.personID = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getPersonID() {
        return personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }
}

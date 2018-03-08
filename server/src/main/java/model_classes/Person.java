package model_classes;

import java.util.UUID;

public class Person {

    public Person() {
        //Deleteme ASAP, needed for function stubbing
    }

    private UUID person_id;
    private String descendant_username;
    private String first_name;
    private String last_name;
    private char gender;
    private String father;
    private String mother;
    private String spouse;

    /**
     * This constructor is for storing person objects read from the database where the person_id
     * is already known and will therefore not generate a new one.
     * this constructor like all other model constructors expects
     * that the parameters passed to it have already been validated.
     * @param person_id
     * @param descendant_username
     * @param first_name
     * @param last_name
     * @param gender
     * @param father
     * @param mother
     * @param spouse
     */
    public Person(UUID person_id, String descendant_username, String first_name, String last_name,
                  char gender, String father, String mother, String spouse) {
        this.person_id = person_id;
        this.descendant_username = descendant_username;
        this.first_name = first_name;
        this.last_name = last_name;
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
     * @param descendant_username
     * @param first_name
     * @param last_name
     * @param gender
     * @param father
     * @param mother
     * @param spouse
     */
    public Person(String descendant_username, String first_name, String last_name,
                  char gender, String father, String mother, String spouse) {
        this.person_id = UUID.randomUUID();
        this.descendant_username = descendant_username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public UUID getPerson_id() {
        return person_id;
    }

    public String getDescendant_username() {
        return descendant_username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
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

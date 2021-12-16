package Main.Model;

public class Person {

    private String firstName;
    private String lastName;

    /**
     * returns the String conversion of the Person objects
     * @return String
     */
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "}\n";
    }

    /**
     * Constructor for Person objects
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * getter for Person.firstName
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter for Person.firstName
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter for Person.lastName
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter for Person.lastName
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

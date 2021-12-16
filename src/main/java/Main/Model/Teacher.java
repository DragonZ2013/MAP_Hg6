package Main.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teacher extends Person{
    private int teacherId;


    /**
     * Compares the parameters of 2 Course objects
     * @param o
     * @return true if the parameters are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return teacherId == teacher.teacherId;
    }

    @Override
    public int hashCode() {
        return Objects.hash( teacherId);
    }

    /**
     * Conversion to String for Teacher objects
     * @return String
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", firstName=" + this.getFirstName()+
                ", lastName=" + this.getLastName()+
                '}';
    }



    /**
     * getter for Teacher.teacherId
     * @return int
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * setter for Teacher.teacherId
     * @param teacherId
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * Constructor for Teacher objects
     * @param firstName
     * @param lastName
     * @param teacherId
     */
    public Teacher(String firstName, String lastName, int teacherId) {
        super(firstName, lastName);
        this.teacherId = teacherId;
    }

}

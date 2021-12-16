package Main.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student extends Person{
    private int studentId;
    private int totalCredits;
    private List<Integer> enrolledCourses;

    /**
     * Constructor for Student objects
     * @param firstName
     * @param lastName
     * @param studentId
     * @param totalCredits
     * @param enrolledCourses
     */
    public Student(String firstName, String lastName, int studentId, int totalCredits, List<java.lang.Integer> enrolledCourses) {
        super(firstName, lastName);
        this.studentId = studentId;
        this.totalCredits = totalCredits;
        this.enrolledCourses = enrolledCourses;
    }

    /**
     * Compares the parameters of 2 Student objects
     * @param o
     * @return true if the parameters are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return studentId == student.studentId && totalCredits == student.totalCredits && Objects.equals(enrolledCourses, student.enrolledCourses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, totalCredits, enrolledCourses);
    }

    /**
     * Converstion to String for Student objects
     * @return String
     */
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" + coursesToId() +
                ", firstName=" + this.getFirstName()+
                ", lastName=" + this.getLastName()+
                "}";
    }

    /**
     * Converts the course array to an int array, used to avoid recursive printing
     * @return List<Integer>
     */
    public List<java.lang.Integer> coursesToId(){
        return enrolledCourses;
    }

    /**
     * getter for Student.studentId
     * @return int
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * setter for Student.studentId
     * @param studentId
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * getter for Student.totalCredits
     * @return int
     */
    public int getTotalCredits() {
        return totalCredits;
    }

    /**
     * setter for Student.totalCredits
     * @param totalCredits
     */
    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    /**
     * getter for Student.enrolledCourses
     * @return List<Course>
     */
    public List<Integer> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * setter for Student.enrolledCourses
     * @param enrolledCourses
     */
    public void setEnrolledCourses(List<Integer> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}

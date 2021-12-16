package Main.Model;

import java.util.List;
import java.util.Objects;

public class Course {
    private String name;
    private int teacher;
    private int maxEnrollment;
    private List<Integer> studentsEnrolled;
    private int credits;
    private int courseId;

    /**
     * Compares the parameters of 2 Course objects
     * @param o
     * @return true if the parameters are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return maxEnrollment == course.maxEnrollment && credits == course.credits && courseId == course.courseId && Objects.equals(name, course.name) && Objects.equals(teacher, course.teacher) && Objects.equals(studentsEnrolled, course.studentsEnrolled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teacher, maxEnrollment, studentsEnrolled, credits, courseId);
    }

    /**
     * returns the String conversion of the Course objects
     * @return String
     */
    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                ", courseId=" + courseId +
                "}";
    }


    /**
     * Constructor for Course objects
     * @param name
     * @param teacher
     * @param maxEnrollment
     * @param studentsEnrolled
     * @param credits
     * @param courseId
     */
    public Course(String name, int teacher, int maxEnrollment, List<Integer> studentsEnrolled, int credits, int courseId) {
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
        this.courseId = courseId;
    }

    /**
     * getter for Course.name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * setter for Course.name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for Course.teacher
     * @return Teacher
     */
    public int getTeacher() {
        return teacher;
    }

    /**
     * setter for Course.teacher
     * @param teacher
     */
    public void setTeacher(int teacher) {
        this.teacher = teacher;
    }

    /**
     *getter for Course.maxEnrollment
     * @return int
     */
    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    /**
     * setter for Course.maxEnrollment
     * @param maxEnrollment
     */
    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * getter for Course.studentsEnrolled
     * @return List<Students>
     */
    public List<Integer> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    /**
     * setter for Course.studentsEnrolled
     * @param studentsEnrolled
     */
    public void setStudentsEnrolled(List<Integer> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    /**
     * getter for Course.credits
     * @return int
     */
    public int getCredits() {
        return credits;
    }

    /**
     * setter for Course.credits
     * @param credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * getter for Course.courseId
     * @return int
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Setter for Course.courseId
     * @param courseId
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}

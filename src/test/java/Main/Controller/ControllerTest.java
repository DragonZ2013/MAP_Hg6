package Main.Controller;

import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseSqlRepository;
import Main.Repository.CrudRepository;
import Main.Repository.StudentSqlRepository;
import Main.Repository.TeacherSqlRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    CrudRepository<Teacher> tr = Mockito.mock(TeacherSqlRepository.class);
    CrudRepository<Student> sr = Mockito.mock(StudentSqlRepository.class);
    CrudRepository<Course> cr = Mockito.mock(CourseSqlRepository.class);
    Controller controller = null;


    @BeforeEach
    void setUp() throws SQLException, IOException {
        List<Integer> listInt1 = Arrays.asList(1,2,3);
        List<Integer> listInt2 = Arrays.asList(1,3,4);
        List<Integer> listInt3 = Arrays.asList(2,4);
        List<Integer> listInt4 = List.of(2);
        List<Integer> listInt5 = Arrays.asList(1,2);
        List<Integer> listInt6 = Arrays.asList(1,3,4);
        List<Integer> listInt7 = List.of(2);
        List<Integer> listInt8 = Arrays.asList(2,3);
        Course course1 = new Course("course1",1,10, listInt1,30,1);
        Course course2 = new Course("course2",1,3, listInt2,45,2);//25
        Course course3 = new Course("course3",2,30, listInt3,10,3);//20
        Course course4 = new Course("course4",3,20, listInt4,15,4);
        Teacher teacher1 = new Teacher ("teacherfirstname1","teacherlastname1",1);
        Teacher teacher2 = new Teacher ("teacherfirstname2","teacherlastname2",2);
        Teacher teacher3 = new Teacher ("teacherfirstname3","teacherlastname3",3);
        Student student1 = new Student("e","b",1,75,listInt5);
        Student student2 = new Student("b","a",2,55,listInt6);
        Student student3 = new Student("e","c",3,45,listInt7);
        Student student4 = new Student("a","a",4,65,listInt8);
        List<Course> listCourse1 = Arrays.asList(course1,course2,course3,course4);
        List<Teacher> listTeacher1 = Arrays.asList(teacher1,teacher2,teacher3);
        List<Student> listStudent1 = Arrays.asList(student1,student2,student3,student4);
        Mockito.when(cr.getAll()).thenReturn(listCourse1);
        Mockito.when(tr.getAll()).thenReturn(listTeacher1);
        Mockito.when(sr.getAll()).thenReturn(listStudent1);
        Mockito.when(cr.getObject(1)).thenReturn(course1);
        Mockito.when(cr.getObject(2)).thenReturn(course2);
        Mockito.when(cr.getObject(3)).thenReturn(course3);
        Mockito.when(cr.getObject(4)).thenReturn(course4);
        Mockito.when(tr.getObject(1)).thenReturn(teacher1);
        Mockito.when(tr.getObject(2)).thenReturn(teacher2);
        Mockito.when(tr.getObject(3)).thenReturn(teacher3);
        Mockito.when(sr.getObject(1)).thenReturn(student1);
        Mockito.when(sr.getObject(2)).thenReturn(student2);
        Mockito.when(sr.getObject(3)).thenReturn(student3);
        Mockito.when(sr.getObject(4)).thenReturn(student4);
        controller = new Controller(cr,tr,sr);
    }



    @org.junit.jupiter.api.Test
    void testCourseGet() throws SQLException, MissingIdException {
        assertEquals(4,controller.listCourses().size());

        assertEquals(1,controller.listCourses().get(0).getCourseId());
        assertEquals(30,controller.listCourses().get(0).getCredits());
        assertEquals(3,controller.listCourses().get(0).getStudentsEnrolled().size());

        assertEquals(2,controller.listCourses().get(1).getCourseId());
        assertEquals(45,controller.listCourses().get(1).getCredits());
        assertEquals(3,controller.listCourses().get(1).getStudentsEnrolled().size());

        assertEquals(3,controller.listCourses().get(2).getCourseId());
        assertEquals(10,controller.listCourses().get(2).getCredits());
        assertEquals(2,controller.listCourses().get(2).getStudentsEnrolled().size());

        assertEquals(4,controller.listCourses().get(3).getCourseId());
        assertEquals(15,controller.listCourses().get(3).getCredits());
        assertEquals(1,controller.listCourses().get(3).getStudentsEnrolled().size());


    }

    @org.junit.jupiter.api.Test
    void testCourseFilter() throws SQLException, MissingIdException {
        assertEquals(4,controller.filterCourses(9).size());
        assertEquals(3,controller.filterCourses(14).size());
        assertEquals(2,controller.filterCourses(29).size());
        assertEquals(1,controller.filterCourses(44).size());
        assertEquals(0,controller.filterCourses(46).size());
    }

    @org.junit.jupiter.api.Test
    void testCourseSort() throws SQLException, MissingIdException {
        assertEquals(3,controller.sortCourses().get(0).getCourseId());
        assertEquals(4,controller.sortCourses().get(1).getCourseId());
        assertEquals(1,controller.sortCourses().get(2).getCourseId());
        assertEquals(2,controller.sortCourses().get(3).getCourseId());
    }

    @org.junit.jupiter.api.Test
    void testCourseUpdate() throws SQLException, MissingIdException {
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,() -> controller.updateCourse("tempname1",1,10,30,5));
        assertEquals("Course with given Id doesn't exist",thrownMissingIdException1.getMessage());

        MissingIdException thrownMissingIdException2 = assertThrows(MissingIdException.class,() -> controller.updateCourse("tempname2",4,10,30,1));
        assertEquals("Teacher with given Id doesn't exist",thrownMissingIdException2.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testCourseDelete() throws SQLException, MissingIdException {
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,() -> controller.deleteCourse(5));
        assertEquals("Course with given Id doesn't exist",thrownMissingIdException1.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testCourseCreate() throws SQLException, MissingIdException {
        ExistentIdException existentIdException1 = assertThrows(ExistentIdException.class,() -> controller.createCourse("tempcoursename",1,10,20,1));
        assertEquals("Course with given Id already exist",existentIdException1.getMessage());
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,() -> controller.createCourse("tempteachername",4,10,20,5));
        assertEquals("Teacher with given Id doesn't exist",thrownMissingIdException1.getMessage());
    }


    @org.junit.jupiter.api.Test
    void testTeacherGet() throws SQLException, MissingIdException {
        assertEquals(3,controller.listTeachers().size());

        assertEquals(1,controller.listTeachers().get(0).getTeacherId());

        assertEquals(2,controller.listTeachers().get(1).getTeacherId());

        assertEquals(3,controller.listTeachers().get(2).getTeacherId());
    }

    @org.junit.jupiter.api.Test
    void testTeacherDelete() throws SQLException, MissingIdException {
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,()->controller.deleteTeacher(4));
        assertEquals("Teacher with given Id doesn't exist",thrownMissingIdException1.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testTeacherUpdate() throws SQLException, MissingIdException {
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,()->controller.updateTeacher("teachertestfirstname","teachertestlastname",4));
        assertEquals("Teacher with given Id doesn't exist",thrownMissingIdException1.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testTeacherCreate() throws SQLException, MissingIdException {
        ExistentIdException existentIdException1 = assertThrows(ExistentIdException.class,() -> controller.createTeacher("tempfirstname","templastname",1));
        assertEquals("Teacher with given Id already exist",existentIdException1.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testStudentsGet() throws SQLException, MissingIdException {
        assertEquals(4,controller.listStudents().size());

        assertEquals(1,controller.listStudents().get(0).getStudentId());
        assertEquals(75,controller.listStudents().get(0).getTotalCredits());
        assertEquals(2,controller.listStudents().get(0).getEnrolledCourses().size());

        assertEquals(2,controller.listStudents().get(1).getStudentId());
        assertEquals(55,controller.listStudents().get(1).getTotalCredits());
        assertEquals(3,controller.listStudents().get(1).getEnrolledCourses().size());

        assertEquals(3,controller.listStudents().get(2).getStudentId());
        assertEquals(45,controller.listStudents().get(2).getTotalCredits());
        assertEquals(1,controller.listStudents().get(2).getEnrolledCourses().size());

        assertEquals(4,controller.listStudents().get(3).getStudentId());
        assertEquals(65,controller.listStudents().get(3).getTotalCredits());
        assertEquals(2,controller.listStudents().get(3).getEnrolledCourses().size());
    }

    @org.junit.jupiter.api.Test
    void testStudentFilter() throws SQLException, MissingIdException {
        assertEquals(4,controller.filterStudents(44).size());
        assertEquals(3,controller.filterStudents(54).size());
        assertEquals(2,controller.filterStudents(64).size());
        assertEquals(1,controller.filterStudents(74).size());
        assertEquals(0,controller.filterStudents(76).size());
    }

    @org.junit.jupiter.api.Test
    void testStudentSort() throws SQLException, MissingIdException {
        assertEquals(4,controller.sortStudents().get(0).getStudentId());
        assertEquals(2,controller.sortStudents().get(1).getStudentId());
        assertEquals(1,controller.sortStudents().get(2).getStudentId());
        assertEquals(3,controller.sortStudents().get(3).getStudentId());
    }

    @org.junit.jupiter.api.Test
    void testStudentDelete() throws SQLException,MissingIdException{
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,()->controller.deleteStudent(5));
        assertEquals("Student with given Id doesn't exist",thrownMissingIdException1.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testStudentUpdate() throws SQLException,MissingIdException{
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,()->controller.updateStudent("testfirstname","testlastname",5,85));
        assertEquals("Student with given Id doesn't exist",thrownMissingIdException1.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testStudentCreate() throws SQLException, MissingIdException {
        ExistentIdException existentIdException1 = assertThrows(ExistentIdException.class,() -> controller.createStudent("testfirstname","testlastname",1));
        assertEquals("Student with given Id already exist",existentIdException1.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testRegisterStudent() throws  SQLException,MissingIdException{
        MissingIdException thrownMissingIdException1 = assertThrows(MissingIdException.class,()->controller.registerStudent(5,1));
        assertEquals("Student with given Id doesn't exist",thrownMissingIdException1.getMessage());
        MissingIdException thrownMissingIdException2 = assertThrows(MissingIdException.class,()->controller.registerStudent(1,5));
        assertEquals("Course with given Id doesn't exist",thrownMissingIdException2.getMessage());
        ExistentIdException thrownExistentIdException1 = assertThrows(ExistentIdException.class,()->controller.registerStudent(1,1));
        assertEquals("Student already enrolled to given course",thrownExistentIdException1.getMessage());
        MaxSizeException thrownMaxSizeException1 = assertThrows(MaxSizeException.class,()->controller.registerStudent(2,2));
        assertEquals("Course already hax maximum number of students enrolled",thrownMaxSizeException1.getMessage());

    }
}
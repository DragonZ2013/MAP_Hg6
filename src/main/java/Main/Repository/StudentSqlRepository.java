package Main.Repository;

import Main.Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentSqlRepository implements CrudRepository<Student>{

    private String connUrl;
    private String connUser;
    private String connPassword;

    /**
     * Constructor for StudentSqlRepository
     * @param connUrl
     * @param connUser
     * @param connPassword
     */
    public StudentSqlRepository(String connUrl, String connUser, String connPassword) {
        this.connUrl = connUrl;
        this.connUser = connUser;
        this.connPassword = connPassword;
    }

    /**
     * Adds a student to the MySQL database
     * @param obj
     * @throws SQLException
     */
    @Override
    public void create(Student obj) throws SQLException {
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "insert into students(id,firstname,lastname,totalcredits) values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,obj.getStudentId());
        preparedStatement.setString(2,obj.getFirstName());
        preparedStatement.setString(3,obj.getLastName());
        preparedStatement.setInt(4,obj.getTotalCredits());
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    /**
     * Returns the array of students from the MySQL database
     * @return
     * @throws SQLException
     */
    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> studentList= new ArrayList<>();
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from students");
        while(resultSet.next()){
            Student tempStudent = new Student(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"),resultSet.getInt("totalcredits"),new ArrayList<>());
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where studentid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                tempStudent.getEnrolledCourses().add(enrolledStudents.getInt("courseid"));
            }
            studentList.add(tempStudent);
            enrolledStudents.close();
        }

        statement.close();
        innerStatement.close();
        resultSet.close();
        connection.close();
        return studentList;
    }

    /**
     * Updates an element in the MySQL database with the same id as param student
     * Also used for Student registration
     * @param student
     * @throws SQLException
     */
    @Override
    public void update(Student student) throws SQLException {
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "update students set firstname=?,lastname=?,totalcredits=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(4,student.getStudentId());
        preparedStatement.setString(1,student.getFirstName());
        preparedStatement.setString(2,student.getLastName());
        preparedStatement.setInt(3,student.getTotalCredits());


        preparedStatement.execute();
        preparedStatement.close();


        insertEnrollments(student, connection);
        deleteEnrollments(student, connection);


        connection.close();
    }

    /**
     * Insert course registers to database from student's list
     * @param student
     * @param connection
     * @throws SQLException
     */
    private void insertEnrollments(Student student, Connection connection) throws SQLException {
        String registerQuery = "insert ignore into enrolledstudents(studentid,courseid) values(?,?)";
        PreparedStatement registerPreparedStatement = connection.prepareStatement(registerQuery);
        for(int courseId: student.getEnrolledCourses()){
            registerPreparedStatement.setInt(1, student.getStudentId());
            registerPreparedStatement.setInt(2,courseId);
            registerPreparedStatement.execute();
        }
        registerPreparedStatement.close();
    }


    /**
     * Removes course registers from database not in student's list
     * @param student
     * @param connection
     * @throws SQLException
     */
    private void deleteEnrollments(Student student, Connection connection) throws SQLException {
        ArrayList<Integer> currentEnrollments = (ArrayList<Integer>) extractEnrolled(student);

        ArrayList<Integer> deletableEnrollments = (ArrayList<Integer>) currentEnrollments.stream()
                .filter(enrollment-> !student.getEnrolledCourses().contains(enrollment))
                .toList();
        String deleteQuery = "delete from enrolledstudents where studentid=? and courseid=?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setInt(1, student.getStudentId());
        for(int deleteCourse:deletableEnrollments)
        {
            deleteStatement.setInt(2,deleteCourse);
            deleteStatement.execute();
        }

        deleteStatement.close();
    }

    public List<Integer> extractEnrolled(Student student) throws SQLException{
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        Statement statement = connection.createStatement();
        ResultSet enrolledCourses = statement.executeQuery("select * from enrolledstudents where studentid = "+student.getStudentId());
        ArrayList<Integer> arrayList =  new ArrayList<>();
        while (enrolledCourses.next()){
            arrayList.add(enrolledCourses.getInt("courseid"));
        }
        return arrayList;
    }

    /**
     * removes the student with given id from the MySQL database
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "delete from students where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    /**
     * Returns the student from the database with the given id, returns null if object doesn't exist
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Student getObject(int id) throws SQLException {
        Student retStudent= null;
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from students where id= "+ id);
        while(resultSet.next()){
            retStudent = new Student(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"),resultSet.getInt("totalcredits"),new ArrayList<>());
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where studentid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                retStudent.getEnrolledCourses().add(enrolledStudents.getInt("courseid"));
            }
            enrolledStudents.close();
        }

        resultSet.close();
        statement.close();
        innerStatement.close();
        connection.close();
        return retStudent;
    }

}

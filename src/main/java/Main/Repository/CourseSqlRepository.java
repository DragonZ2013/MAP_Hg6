package Main.Repository;

import Main.Model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseSqlRepository implements CrudRepository<Course>{

    private String connUrl;
    private String connUser;
    private String connPassword;

    /**
     * Constructor for CourseSqlRepository
     * @param connUrl
     * @param connUser
     * @param connPassword
     */
    public CourseSqlRepository(String connUrl, String connUser, String connPassword) {
        this.connUrl = connUrl;
        this.connUser = connUser;
        this.connPassword = connPassword;
    }

    /**
     * Adds a course to the MySQL database
     * @param obj
     * @throws SQLException
     */
    @Override
    public void create(Course obj) throws SQLException {

        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "insert into courses(id,coursename,teacherid,maxenrollment,credits) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,obj.getCourseId());
        preparedStatement.setString(2,obj.getName());
        preparedStatement.setInt(3,obj.getTeacher());
        preparedStatement.setInt(4,obj.getMaxEnrollment());
        preparedStatement.setInt(5,obj.getCredits());
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    /**
     * Returns the array of courses from the MySQL database
     * @return List<Course>
     * @throws SQLException
     */
    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courseList= new ArrayList<>();
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from courses");
        while(resultSet.next()){
            Course tempCourse = new Course(resultSet.getString("coursename"),resultSet.getInt("teacherid"),resultSet.getInt("maxenrollment"),new ArrayList<>(),resultSet.getInt("credits"),resultSet.getInt("id"));
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where courseid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                tempCourse.getStudentsEnrolled().add(enrolledStudents.getInt("studentid"));
            }
            courseList.add(tempCourse);
            enrolledStudents.close();
        }

        statement.close();
        innerStatement.close();
        resultSet.close();
        connection.close();
        return courseList;
    }

    /**
     * Updates an element in the MySQL database with the same id as param course
     * @param obj
     * @throws SQLException
     */
    @Override
    public void update(Course obj) throws SQLException {

        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "update courses set coursename=?,teacherid=?,maxenrollment=?,credits=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(5,obj.getCourseId());
        preparedStatement.setString(1,obj.getName());
        preparedStatement.setInt(2,obj.getTeacher());
        preparedStatement.setInt(3,obj.getMaxEnrollment());
        preparedStatement.setInt(4,obj.getCredits());
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    /**
     * removes the course with given id from the MySQL database, returns null if object doesn't exist
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "delete from courses where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    /**
     * Returns the course from the database with the given id
     * @param id
     * @return Course
     * @throws SQLException
     */
    @Override
    public Course getObject(int id) throws SQLException {
        Course retCourse = null;
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        Statement statement = connection.createStatement();
        Statement innerStatement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from courses where id= "+ id);
        while(resultSet.next()){
            retCourse = new Course(resultSet.getString("coursename"),resultSet.getInt("teacherid"),resultSet.getInt("maxenrollment"),new ArrayList<>(),resultSet.getInt("credits"),resultSet.getInt("id"));
            ResultSet enrolledStudents = innerStatement.executeQuery("select * from enrolledstudents where courseid = "+resultSet.getInt("id") );
            while(enrolledStudents.next()){
                retCourse.getStudentsEnrolled().add(enrolledStudents.getInt("studentid"));
            }
            enrolledStudents.close();
        }

        statement.close();
        innerStatement.close();
        resultSet.close();
        connection.close();
        return retCourse;
    }

}

package Main.Repository;


import Main.Model.Teacher;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherSqlRepository implements CrudRepository<Teacher>{

    private String connUrl;
    private String connUser;
    private String connPassword;

    /**
     * Constructor for TeacherSqlRepository
     * @param connUrl
     * @param connUser
     * @param connPassword
     */
    public TeacherSqlRepository(String connUrl, String connUser, String connPassword) {
        this.connUrl = connUrl;
        this.connUser = connUser;
        this.connPassword = connPassword;
    }

    /**
     * Adds a teacher to the MySQL database
     * @param obj
     * @throws SQLException
     */
    @Override
    public void create(Teacher obj) throws SQLException {
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "insert into teachers(id,firstname,lastname) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,obj.getTeacherId());
        preparedStatement.setString(2,obj.getFirstName());
        preparedStatement.setString(3,obj.getLastName());
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    /**
     * Returns the array of teachers from the MySQL database
     * @return
     * @throws SQLException
     */
    @Override
    public List<Teacher> getAll() throws SQLException {
        List<Teacher> teacherList= new ArrayList<>();
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from teachers");
        while(resultSet.next()){
            Teacher tempTeacher = new Teacher(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"));
            teacherList.add(tempTeacher);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return teacherList;
    }

    /**
     * Updates the teacher in the MySQL database with the same id as param teacher
     * @param obj
     * @throws SQLException
     */
    @Override
    public void update(Teacher obj) throws SQLException {
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "update teachers set firstname=?,lastname=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(3,obj.getTeacherId());
        preparedStatement.setString(1,obj.getFirstName());
        preparedStatement.setString(2,obj.getLastName());
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    /**
     * removes the teacher with given id from the MySQL database
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        String query = "delete from teachers where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    /**
     * Returns the teacher from the database with the given id, returns null if object doesn't exist
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Teacher getObject(int id) throws SQLException {
        Teacher retTeacher = null;
        Connection connection = DriverManager.getConnection(connUrl,connUser,connPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from teachers where id= "+ id);
        while(resultSet.next()){
            retTeacher = new Teacher(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getInt("id"));
        }

        statement.close();
        resultSet.close();
        connection.close();
        return retTeacher;
    }

}
